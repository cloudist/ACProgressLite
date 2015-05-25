package cc.cloudist.cpllibrary;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

import cc.cloudist.cpllibrary.components.RemoveCPLException;

public abstract class CircleProgressBase extends View {

    protected WindowManager windowManager;

    protected int size;

    protected Handler handler;

    private float dimAmount = 0.3f;

    private boolean cancelable = false;

    public CircleProgressBase(Context context, float sizeRatio) {
        super(context);

        handler = new UpdateHandler(this);

        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int screenHeight;
        int screenWidth;
        if (Build.VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
            screenHeight = size.y;
        } else {
            screenWidth = display.getWidth();
            screenHeight = display.getHeight();
        }
        if (screenHeight > screenWidth) {
            size = (int) (screenWidth * sizeRatio);
        } else {
            size = (int) (screenHeight * sizeRatio);
        }
    }

    public void setBackgroundAlpha(float alpha) {
        this.dimAmount = alpha;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public void show() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER;
        params.dimAmount = dimAmount;

        windowManager.addView(CircleProgressBase.this, params);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            windowManager.removeView(CircleProgressBase.this);
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int[] l = new int[2];
        getLocationInWindow(l);
        int endX = l[0] + getWidth();
        int endY = l[1] + getHeight();

        float x = event.getX();
        float y = event.getY();

        if (x > l[0] && x < endX && y > l[1] && y < endY) {
//            return super.dispatchTouchEvent(event);
            return true;
        } else if (cancelable) {
            windowManager.removeView(CircleProgressBase.this);
            return true;
        } else {
            return true;
//            return super.dispatchTouchEvent(event);
        }
    }

    public void dismiss() {
        try {
            windowManager.removeView(CircleProgressBase.this);
        } catch (IllegalArgumentException e) {
            throw new RemoveCPLException();
        }
    }

    private static class UpdateHandler extends Handler {
        WeakReference<CircleProgressBase> reference;

        public UpdateHandler(CircleProgressBase base) {
            reference = new WeakReference<CircleProgressBase>(base);
        }

        @Override
        public void handleMessage(Message message) {
            CircleProgressBase base = reference.get();
            if (base != null) {
                base.invalidate();
            }
        }
    }


}
