package cc.cloudist.acplibrary;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cc.cloudist.acplibrary.views.CustomView;

public final class ACProgressCustom extends ACProgressBaseDialog {

    private static final int NO_TYPE = -1;
    private static final int RESOURCE_TYPE = 0;
    private static final int FILE_TYPE = 1;

    private Builder mBuilder;
    private CustomView mCustomView;

    private Timer mTimer;
    private int mSpinCount = 0;
    private int mResourceCount;

    private List<Bitmap> mBitmaps;

    private ACProgressCustom(Builder builder) {
        super(builder.mContext);
        mBuilder = builder;
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mTimer != null) {
                    mTimer.cancel();
                    mTimer = null;
                }
                if (mBitmaps != null) {
                    mBitmaps.clear();
                    mBitmaps = null;
                }
                mSpinCount = 0;
                mResourceCount = 0;
                mCustomView = null;
            }
        });
    }

    public void show() {
        if (mBuilder.mType != NO_TYPE) {
            if (mCustomView == null) {
                mBitmaps = new ArrayList<>();
                int size = (int) (getMinimumSideOfScreen(mBuilder.mContext) * mBuilder.mSizeRatio);
                if (mBuilder.mType == RESOURCE_TYPE) {
                    mResourceCount = mBuilder.mResources.size();
                    for (int i = 0; i < mResourceCount; i++) {
                        mBitmaps.add(BitmapFactory.decodeResource(mBuilder.mContext.getResources(), mBuilder.mResources.get(i)));
                    }
                } else {
                    mResourceCount = mBuilder.mFilePaths.size();
                    for (int i = 0; i < mResourceCount; i++) {
                        mBitmaps.add(BitmapFactory.decodeFile(mBuilder.mFilePaths.get(i)));
                    }
                }
                mCustomView = new CustomView(mBuilder.mContext, size, mBitmaps);
            }
            super.setContentView(mCustomView);
            super.show();

            long delay = (long) (1000 / mBuilder.mSpeed);
            mTimer = new Timer();
            mTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    int result = mSpinCount % mResourceCount;
                    mCustomView.updateIndex(result);
                    if (result == 0) {
                        mSpinCount = 1;
                    } else {
                        mSpinCount++;
                    }
                }
            }, delay, delay);
        } else {
            Log.d(ACProgressCustom.class.toString(), "you must assign the image source in Builder");
        }
    }

    public static class Builder {

        private Context mContext;

        private float mSizeRatio = 0.2f;

        private List<Integer> mResources = new ArrayList<>();
        private List<String> mFilePaths = new ArrayList<>();

        private int mType = NO_TYPE;

        private float mSpeed = 6.67f;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder sizeRatio(float ratio) {
            mSizeRatio = ratio;
            return this;
        }

        public Builder speed(float speed) {
            mSpeed = speed;
            return this;
        }

        public Builder useImages(Integer... imageIds) {
            if (imageIds != null && imageIds.length != 0) {
                mResources.clear();
                Collections.addAll(mResources, imageIds);
                mType = RESOURCE_TYPE;
            }
            return this;
        }

        public Builder useFiles(String... paths) {
            if (paths != null && paths.length != 0) {
                mFilePaths.clear();
                Collections.addAll(mFilePaths, paths);
                mType = FILE_TYPE;
            }
            return this;
        }

        public ACProgressCustom build() {
            return new ACProgressCustom(this);
        }

    }
}
