package cc.cloudist.acplibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("ViewConstructor")
public class ACProgressCustom extends ACProgressBase {

    private List<Bitmap> mBitmaps;
    private RectF mRect;

    private float mSpeed;

    private int mImageCount = 0;
    private long mSpinCount = 0;
    private int mCurrentIndex = 0;

    public ACProgressCustom(Builder builder) {
        super(builder.mContext, builder.mSizeRatio);

        mRect = new RectF(0, 0, mSize, mSize);
        mBitmaps = new ArrayList<>();

        mSpeed = builder.mSpeed;

        if (builder.mFilePaths.size() != 0) {
            mImageCount = builder.mFilePaths.size();
            for (int i = 0; i < mImageCount; i++) {
                mBitmaps.add(BitmapFactory.decodeFile(builder.mFilePaths.get(i)));
            }
        } else {
            mImageCount = builder.mResources.size();
            for (int i = 0; i < mImageCount; i++) {
                mBitmaps.add(getBitmap(builder.mContext, builder.mResources.get(i)));
            }
        }

    }

    public static class Builder {
        private Context mContext;

        private float mSizeRatio = 0.2f;

        private List<Integer> mResources = new ArrayList<Integer>();
        private List<String> mFilePaths = new ArrayList<String>();

        private float mSpeed = 6.67f;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder sizeRatio(float ratio) {
            this.mSizeRatio = ratio;
            return this;
        }

        public Builder speed(float speed) {
            this.mSpeed = speed;
            return this;
        }

        public Builder useImages(int... imageIds) {
            mFilePaths.clear();
            for (int id : imageIds) {
                mResources.add(id);
            }
            return this;
        }

        public Builder useFiles(String... paths) {
            mResources.clear();
            for (String path : paths) {
                mFilePaths.add(path);
            }
            return this;
        }

        public ACProgressCustom build() {
            return new ACProgressCustom(this);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mSize, mSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmaps.get(mCurrentIndex), null, mRect, null);
    }

    private Bitmap getBitmap(Context context, int resId) {
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is);
    }

    @Override
    public void show() {
        super.show();
        long delay = (long) (1000 / mSpeed);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int result = (int) (mSpinCount % mImageCount);
                mCurrentIndex = mImageCount - 1 - result;
                mHandler.sendEmptyMessage(0);
                mSpinCount++;
            }
        }, delay, delay);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mBitmaps != null) {
            mBitmaps.clear();
        }
        mBitmaps = null;
        mRect = null;
    }

}
