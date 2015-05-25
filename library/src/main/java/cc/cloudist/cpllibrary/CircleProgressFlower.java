package cc.cloudist.cpllibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cc.cloudist.cpllibrary.components.FlowerDataCalc;
import cc.cloudist.cpllibrary.components.PetalCoordinate;

@SuppressLint("ViewConstructor")
public class CircleProgressFlower extends CircleProgressBase {

    private RectF bgRectF;
    private Paint bgPaint, petalPaint;

    private float bgCornerRadius;
    private int petalCount;

    private List<PetalCoordinate> petalCoordinates;
    private List<Integer> petalColors;

    private int focusPetalIndex = 0;

    private long spinCount = 0;

    private int direction;

    private float speed;

    private CircleProgressFlower(Builder builder) {
        super(builder.context, builder.sizeRatio);

        bgCornerRadius = builder.bgCornerRadius;
        petalCount = builder.petalCount;
        direction = builder.direction;
        speed = builder.speed;

        bgRectF = new RectF(0, 0, size, size);

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(builder.bgColor);
        bgPaint.setAlpha((int) (builder.bgAlpha * 255));

        petalPaint = new Paint();
        petalPaint.setAntiAlias(true);
        petalPaint.setStrokeWidth(builder.petalThickness);
        petalPaint.setStrokeCap(Paint.Cap.ROUND);

        FlowerDataCalc calc = new FlowerDataCalc(builder.petalCount);
        petalCoordinates = calc.getLineCoordinates(size, (int) (builder.borderPadding * size), (int) (builder.centerPadding * size), builder.petalCount);
        petalColors = calc.getLineColors(builder.themeColor, builder.fadeColor, builder.petalCount, (int) (builder.petalAlpha * 255));
    }

    public static class Builder {

        public static final int DIRECT_CLOCKWISE = 0;
        public static final int DIRECT_ANTI_CLOCKWISE = 1;

        private Context context;

        private int petalCount = 12;
        private float sizeRatio = 0.2f;

        private int themeColor = Color.RED;
        private int fadeColor = Color.WHITE;
        private int bgColor = Color.BLACK;

        private float petalAlpha = 0.5f;
        private float bgAlpha = 0.5f;

        private int direction = DIRECT_CLOCKWISE;
        private float speed = 6.67f;

        private float borderPadding = 0.4f;
        private float centerPadding = 0.4f;

        private int petalThickness = 8;

        private float bgCornerRadius = 20f;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder petalThickness(int thickness) {
            this.petalThickness = thickness;
            return this;
        }

        public Builder borderPadding(float padding) {
            this.borderPadding = padding;
            return this;
        }

        public Builder centerPadding(float padding) {
            this.centerPadding = padding;
            return this;
        }

        public Builder petalCount(int petalCount) {
            this.petalCount = petalCount;
            return this;
        }

        public Builder bgCornerRadius(float cornerRadius) {
            this.bgCornerRadius = cornerRadius;
            return this;
        }

        public Builder themeColor(int themeColor) {
            this.themeColor = themeColor;
            return this;
        }

        public Builder fadeColor(int fadeColor) {
            this.fadeColor = fadeColor;
            return this;
        }

        public Builder bgColor(int bgColor) {
            this.bgColor = bgColor;
            return this;
        }

        public Builder petalAlpha(float alpha) {
            this.petalAlpha = alpha;
            return this;
        }

        public Builder bgAlpha(float alpha) {
            this.bgAlpha = alpha;
            return this;
        }

        public Builder direction(int direction) {
            this.direction = direction;
            return this;
        }

        public Builder speed(float speed) {
            this.speed = speed;
            return this;
        }

        public Builder sizeRatio(float ratio) {
            this.sizeRatio = ratio;
            return this;
        }

        public CircleProgressFlower build() {
            return new CircleProgressFlower(this);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(bgRectF, bgCornerRadius, bgCornerRadius, bgPaint);
        for (PetalCoordinate coordinate : petalCoordinates) {
            int index = petalCoordinates.indexOf(coordinate) - focusPetalIndex;
            if (index < 0) {
                index = petalCount + index;
            }
            petalPaint.setColor(petalColors.get(index));
            canvas.drawLine(coordinate.getStartX(), coordinate.getStartY(), coordinate.getEndX(), coordinate.getEndY(), petalPaint);
        }
    }

    @Override
    public void show() {
        super.show();
        long delay = (long) (1000 / speed);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int result = (int) (spinCount % petalCount);
                if (direction == Builder.DIRECT_CLOCKWISE) {
                    focusPetalIndex = petalCount - 1 - result;
                } else {
                    focusPetalIndex = result;
                }
                handler.sendEmptyMessage(0);
                spinCount++;
            }
        }, delay, delay);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        bgPaint = null;
        petalPaint = null;
        bgRectF = null;
        if (petalCoordinates != null) {
            petalCoordinates.clear();
        }
        if (petalColors != null) {
            petalColors.clear();
        }
        petalCoordinates = null;
        petalColors = null;
    }


}
