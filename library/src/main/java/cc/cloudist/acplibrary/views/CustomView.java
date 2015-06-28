package cc.cloudist.acplibrary.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.List;

public class CustomView extends View {

    private int mSize;

    private List<Bitmap> mBitmaps;
    private RectF mRect;
    private int mCurrentIndex = 0;

    private Handler mHandler;

    public CustomView(Context context, int size, List<Bitmap> bitmaps) {
        super(context);

        mHandler = new CustomUpdateHandler(this);

        mSize = size;
        mRect = new RectF(0, 0, size, size);
        mBitmaps = bitmaps;
    }

    public void updateIndex(int index) {
        mCurrentIndex = index;
        mHandler.sendEmptyMessage(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mSize, mSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("23232","draw");
        canvas.drawBitmap(mBitmaps.get(mCurrentIndex), null, mRect, null);
    }

    private static class CustomUpdateHandler extends Handler {
        WeakReference<CustomView> reference;

        public CustomUpdateHandler(CustomView customView) {
            reference = new WeakReference<>(customView);
        }

        @Override
        public void handleMessage(Message message) {
            CustomView customView = reference.get();
            if (customView != null) {
                customView.invalidate();
            }
        }
    }

}
