package cc.cloudist.acplibrary.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.List;

import cc.cloudist.acplibrary.components.FlowerDataCalc;
import cc.cloudist.acplibrary.components.PetalCoordinate;

public final class FlowerView extends View {

    private int mSize;

    private int mFinalSize;// The width when a text string is assigned. The flower is drawn by original ratio, but horizontal center at mFinalSize

    private int mPetalCount;
    private float mBackgroundCornerRadius;

    private RectF mBackgroundRect;
    private Paint mBackgroundPaint, mPetalPaint, mTextPaint;

    private List<PetalCoordinate> mPetalCoordinates;
    private int[] mPetalColors;

    private Handler mHandler;
    private int mCurrentFocusIndex;

    private String mText;
    private int mTextHeight, mTextWidth;
    private int mTextMarginTop;

    private boolean mIsExpandWidth;

    public FlowerView(
            Context context, int size, int bgColor, float bgAlpha, float bgCornerRadius
            , int petalThickness, int petalCount, float petalAlpha, float borderPadding, float centerPadding
            , int themeColor, int fadeColor
            , String text, float textSize, int textColor, float textAlpha, int textMarginTop, boolean textExpandWidth) {
        super(context);
        mHandler = new FlowerUpdateHandler(this);
        mTextMarginTop = textMarginTop;
        init(size, bgColor, bgAlpha, bgCornerRadius,
                petalThickness, petalCount, petalAlpha, borderPadding, centerPadding, themeColor, fadeColor,
                text, textSize, textColor, textAlpha, textExpandWidth);
    }

    private void init(
            int size, int bgColor, float bgAlpha, float bgCornerRadius
            , int petalThickness, int petalCount, float petalAlpha, float borderPadding, float centerPadding
            , int themeColor, int fadeColor
            , String text, float textSize, int textColor, float textAlpha, boolean textExpandWidth) {

        mIsExpandWidth = (text != null && text.length() != 0 && textExpandWidth);

        mSize = size;
        mPetalCount = petalCount;
        mBackgroundCornerRadius = bgCornerRadius;

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(bgColor);
        mBackgroundPaint.setAlpha((int) (bgAlpha * 255));

        mPetalPaint = new Paint();
        mPetalPaint.setAntiAlias(true);
        mPetalPaint.setStrokeWidth(petalThickness);
        mPetalPaint.setStrokeCap(Paint.Cap.ROUND);

        if (text != null && text.length() != 0) {
            mText = text;
            mTextPaint = new Paint();
            mTextPaint.setAntiAlias(true);
            mTextPaint.setColor(textColor);
            mTextPaint.setAlpha((int) (textAlpha * 255));
            mTextPaint.setTextSize(textSize);
            Rect textMeasure = new Rect();
            mTextPaint.getTextBounds(text, 0, text.length(), textMeasure);
            mTextHeight = textMeasure.bottom - textMeasure.top;
            mTextWidth = textMeasure.right - textMeasure.left;
        } else {
            mTextMarginTop = 0;
        }

        if (mIsExpandWidth) {
            mBackgroundRect = new RectF(0, 0, mSize + mTextHeight + mTextMarginTop, mSize + mTextHeight + mTextMarginTop);
            mFinalSize = mSize + mTextHeight + mTextMarginTop;
        } else {
            mBackgroundRect = new RectF(0, 0, mSize, mSize + mTextHeight + mTextMarginTop);
            mFinalSize = mSize;
        }

        FlowerDataCalc calc = new FlowerDataCalc(petalCount);
        mPetalCoordinates = calc.getSegmentsCoordinates(mSize, (int) (borderPadding * mSize), (int) (centerPadding * mSize), petalCount, mFinalSize);
        mPetalColors = calc.getSegmentsColors(themeColor, fadeColor, petalCount, (int) (petalAlpha * 255));

    }

    public void updateFocusIndex(int index) {
        mCurrentFocusIndex = index;
        mHandler.sendEmptyMessage(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mIsExpandWidth) {
            setMeasuredDimension(mSize + mTextHeight + mTextMarginTop, mSize + mTextHeight + mTextMarginTop);
        } else {
            setMeasuredDimension(mSize, mSize + mTextHeight + mTextMarginTop);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(mBackgroundRect, mBackgroundCornerRadius, mBackgroundCornerRadius, mBackgroundPaint);
        PetalCoordinate coordinate;
        for (int i = 0; i < mPetalCount; i++) {
            coordinate = mPetalCoordinates.get(i);
            int index = (mCurrentFocusIndex + i) % mPetalCount;
            mPetalPaint.setColor(mPetalColors[index]);
            canvas.drawLine(coordinate.getStartX(), coordinate.getStartY(), coordinate.getEndX(), coordinate.getEndY(), mPetalPaint);
        }
        if (mText != null) {
            canvas.drawText(mText, mFinalSize / 2 - mTextWidth / 2, mSize, mTextPaint);
        }
    }

    private static class FlowerUpdateHandler extends Handler {
        WeakReference<FlowerView> reference;

        public FlowerUpdateHandler(FlowerView flowerView) {
            reference = new WeakReference<>(flowerView);
        }

        @Override
        public void handleMessage(Message message) {
            FlowerView flowerView = reference.get();
            if (flowerView != null) {
                flowerView.invalidate();
            }
        }
    }

}
