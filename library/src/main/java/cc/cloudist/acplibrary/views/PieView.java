package cc.cloudist.acplibrary.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;


public final class PieView extends View {

    private RectF mBackgroundRect, mPieRect;
    private Paint mBackgroundPaint, mRingPaint, mPiePaint;

    private int mSize;
    private float mBackgroundCornerRadius;
    private float mRingBorderPadding;

    private float mAngle;
    private Handler mHandler;

    public PieView(
            Context context, int size, int bgColor, float bgAlpha, float bgCornerRadius
            , float ringBorderPadding, float pieRingDistance
            , int ringThickness, int ringColor, float ringAlpha
            , int pieColor, float pieAlpha) {
        super(context);

        mHandler = new PieUpdateHandler(this);

        mSize = size;
        mBackgroundCornerRadius = bgCornerRadius;
        mRingBorderPadding = ringBorderPadding;

        mBackgroundRect = new RectF(0, 0, size, size);

        float piePaddingValue = (ringBorderPadding + pieRingDistance) * size;
        mPieRect = new RectF(0 + piePaddingValue / 2, 0 + piePaddingValue / 2, size - piePaddingValue / 2, size - piePaddingValue / 2);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(bgColor);
        mBackgroundPaint.setAlpha((int) (bgAlpha * 255));

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setStrokeWidth(ringThickness);
        mRingPaint.setColor(ringColor);
        mRingPaint.setAlpha((int) (ringAlpha * 255));
        mRingPaint.setStyle(Paint.Style.STROKE);

        mPiePaint = new Paint();
        mPiePaint.setAntiAlias(true);
        mPiePaint.setColor(pieColor);
        mPiePaint.setAlpha((int) (pieAlpha * 255));
    }

    public void updateAngle(float angle) {
        mAngle = angle;
        mHandler.sendEmptyMessage(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mSize, mSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(mBackgroundRect, mBackgroundCornerRadius, mBackgroundCornerRadius, mBackgroundPaint);
        canvas.drawCircle(mSize / 2, mSize / 2, (mSize * (1 - mRingBorderPadding)) / 2, mRingPaint);
        Log.d("23232", "--" + mAngle);
        canvas.drawArc(mPieRect, -90, mAngle, true, mPiePaint);
    }

    private static class PieUpdateHandler extends Handler {
        WeakReference<PieView> reference;

        public PieUpdateHandler(PieView pieView) {
            reference = new WeakReference<>(pieView);
        }

        @Override
        public void handleMessage(Message message) {
            PieView pieView = reference.get();
            if (pieView != null) {
                pieView.invalidate();
            }
        }
    }

}
