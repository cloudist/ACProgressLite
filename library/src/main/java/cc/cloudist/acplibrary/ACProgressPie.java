package cc.cloudist.acplibrary;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;

import java.util.Timer;
import java.util.TimerTask;

import cc.cloudist.acplibrary.views.PieView;

public class ACProgressPie extends ACProgressBaseDialog {

    private Builder mBuilder;
    private PieView mPieView;

    private Timer mTimer;
    private int mSpinCount = 0;

    private ACProgressPie(Builder builder) {
        super(builder.mContext);
        mBuilder = builder;
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mTimer != null) {
                    mTimer.cancel();
                    mTimer = null;
                }
                mSpinCount = 0;
                mPieView = null;
            }
        });
    }

    public void show() {
        if (mPieView == null) {
            int size = (int) (getMinimumSideOfScreen(mBuilder.mContext) * mBuilder.mSizeRatio);
            mPieView = new PieView(mBuilder.mContext, size, mBuilder.mBackgroundColor, mBuilder.mBackgroundAlpha, mBuilder.mBackgroundCornerRadius
                    , mBuilder.mRingBorderPadding, mBuilder.mPieRingDistance
                    , mBuilder.mRingThickness, mBuilder.mRingColor, mBuilder.mRingAlpha
                    , mBuilder.mPieColor, mBuilder.mPieAlpha);
        }
        super.setContentView(mPieView);
        super.show();
        if (mBuilder.mUpdateType == ACProgressConstant.PIE_AUTO_UPDATE) {
            long delay = (long) (1000 / mBuilder.mSpeed);
            mTimer = new Timer();
            mTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    int result = mSpinCount % (mBuilder.mPieces + 1);
                    mPieView.updateAngle(360f / mBuilder.mPieces * result);
                    if (result == 0) {
                        mSpinCount = 1;
                    } else {
                        mSpinCount++;
                    }
                }
            }, delay, delay);
        }
    }

    public void setPiePercentage(float percentage) {
        if (mBuilder.mUpdateType == ACProgressConstant.PIE_MANUAL_UPDATE && mPieView != null) {
            mPieView.updateAngle(360 * percentage);
        }
    }

    public static class Builder {

        private Context mContext;

        private float mSizeRatio = 0.25f;

        private int mBackgroundColor = Color.BLACK;
        private float mBackgroundCornerRadius = 20f;
        private float mBackgroundAlpha = 0.5f;

        private int mRingColor = Color.WHITE;
        private float mRingAlpha = 0.9f;

        private float mRingBorderPadding = 0.2f;
        private int mRingThickness = 3;

        private int mPieColor = Color.WHITE;
        private float mPieAlpha = 0.9f;

        private float mPieRingDistance = 0.08f;

        private float mSpeed = 6.67f;
        private int mPieces = 100;

        private int mUpdateType = ACProgressConstant.PIE_AUTO_UPDATE;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder sizeRatio(float ratio) {
            mSizeRatio = ratio;
            return this;
        }

        public Builder bgColor(int color) {
            mBackgroundColor = color;
            return this;
        }

        public Builder bgAlpha(float alpha) {
            mBackgroundAlpha = alpha;
            return this;
        }

        public Builder bgCornerRadius(float cornerRadius) {
            mBackgroundCornerRadius = cornerRadius;
            return this;
        }

        public Builder ringColor(int color) {
            mRingColor = color;
            return this;
        }

        public Builder ringAlpha(float alpha) {
            mRingAlpha = alpha;
            return this;
        }

        public Builder ringBorderPadding(float padding) {
            mRingBorderPadding = padding;
            return this;
        }

        public Builder ringThickness(int thickness) {
            mRingThickness = thickness;
            return this;
        }

        public Builder pieColor(int color) {
            mPieColor = color;
            return this;
        }

        public Builder pieAlpha(float alpha) {
            mPieAlpha = alpha;
            return this;
        }

        public Builder pieRingDistance(float distance) {
            mPieRingDistance = distance;
            return this;
        }

        public Builder speed(float speed) {
            mSpeed = speed;
            return this;
        }

        public Builder pieces(int pieces) {
            mPieces = pieces;
            return this;
        }

        public Builder updateType(int updateType) {
            mUpdateType = updateType;
            return this;
        }

        public ACProgressPie build() {
            return new ACProgressPie(this);
        }

    }
}
