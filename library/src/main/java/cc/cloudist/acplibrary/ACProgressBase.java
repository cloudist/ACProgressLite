package cc.cloudist.acplibrary;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

import cc.cloudist.acplibrary.components.RemoveACPLException;

public abstract class ACProgressBase extends View {

    protected WindowManager mWindowManager;

    protected int mSize;

    protected Handler mHandler;

    private float mDimAmount = 0.3f;

    private boolean mCancelable = false;

    public ACProgressBase(Context context, float sizeRatio) {
        super(context);

        mHandler = new UpdateHandler(this);

        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = mWindowManager.getDefaultDisplay();
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
            mSize = (int) (screenWidth * sizeRatio);
        } else {
            mSize = (int) (screenHeight * sizeRatio);
        }
    }

    public void setBackgroundAlpha(float alpha) {
        this.mDimAmount = alpha;
    }

    public void setCancelable(boolean mCancelable) {
        this.mCancelable = mCancelable;
    }

    public void show() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER;
        params.dimAmount = mDimAmount;

        mWindowManager.addView(ACProgressBase.this, params);
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            mWindowManager.removeView(ACProgressBase.this);
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
        if (mCancelable) {
            int[] location = new int[2];
            getLocationInWindow(location);
            int endX = location[0] + getWidth();
            int endY = location[1] + getHeight();

            float x = event.getX();
            float y = event.getY();

            if (x < location[0] || x > endX || y < location[1] || y > endY) {
                mWindowManager.removeView(ACProgressBase.this);
            }
            return true;
        } else {
            return true;
        }
    }

    public void dismiss() {
        try {
            mWindowManager.removeView(ACProgressBase.this);
        } catch (IllegalArgumentException e) {
            throw new RemoveACPLException();
        }
    }

    private static class UpdateHandler extends Handler {
        WeakReference<ACProgressBase> mReference;

        public UpdateHandler(ACProgressBase base) {
            mReference = new WeakReference<>(base);
        }

        @Override
        public void handleMessage(Message message) {
            ACProgressBase base = mReference.get();
            if (base != null) {
                base.invalidate();
            }
        }
    }

}
