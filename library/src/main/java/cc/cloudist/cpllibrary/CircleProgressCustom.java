package cc.cloudist.cpllibrary;

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
public class CircleProgressCustom extends CircleProgressBase {

    private List<Bitmap> bitmaps;
    private RectF rectF;

    private float speed;

    private int imageCount = 0;

    private long spinCount = 0;

    private int currentIndex = 0;

    public CircleProgressCustom(Builder builder) {
        super(builder.context, builder.sizeRatio);

        rectF = new RectF(0, 0, size, size);
        bitmaps = new ArrayList<Bitmap>();

        speed = builder.speed;

        if (builder.filePaths.size() != 0) {
            imageCount = builder.filePaths.size();
            for (int i = 0; i < imageCount; i++) {
                bitmaps.add(BitmapFactory.decodeFile(builder.filePaths.get(i)));
            }
        } else {
            imageCount = builder.resources.size();
            for (int i = 0; i < imageCount; i++) {
                bitmaps.add(getBitmap(builder.context, builder.resources.get(i)));
            }
        }

    }

    public static class Builder {
        private Context context;

        private float sizeRatio = 0.2f;

        private List<Integer> resources = new ArrayList<Integer>();
        private List<String> filePaths = new ArrayList<String>();

        private float speed = 6.67f;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder sizeRatio(float ratio) {
            this.sizeRatio = ratio;
            return this;
        }

        public Builder speed(float speed) {
            this.speed = speed;
            return this;
        }

        public Builder useImages(int... imageIds) {
            filePaths.clear();
            for (int id : imageIds) {
                resources.add(id);
            }
            return this;
        }

        public Builder useFiles(String... paths) {
            resources.clear();
            for (String path : paths) {
                filePaths.add(path);
            }
            return this;
        }

        public CircleProgressCustom build() {
            return new CircleProgressCustom(this);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmaps.get(currentIndex), null, rectF, null);
    }

    private Bitmap getBitmap(Context context, int resId) {
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is);
    }

    @Override
    public void show() {
        super.show();
        long delay = (long) (1000 / speed);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int result = (int) (spinCount % imageCount);
                currentIndex = imageCount - 1 - result;
                handler.sendEmptyMessage(0);
                spinCount++;
            }
        }, delay, delay);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (bitmaps != null) {
            bitmaps.clear();
        }
        bitmaps = null;
        rectF = null;
    }

}
