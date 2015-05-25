package cc.cloudist.cpllibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("ViewConstructor")
public class CircleProgressPie extends CircleProgressBase {

    private RectF bgRectF, pieRectF;
    private Paint bgPaint, ringPaint, piePaint;

    private float bgCornerRadius;
    private float ringBorderPadding;

    private long spinCount = 0;
    private int currentFocusIndex = 0;

    private int slides = 20;

    public CircleProgressPie(Builder builder) {
        super(builder.context, builder.sizeRatio);

        bgCornerRadius = builder.bgCornerRadius;
        ringBorderPadding = builder.ringBorderPadding;

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(builder.bgColor);
        bgPaint.setAlpha((int) (builder.bgAlpha * 255));

        ringPaint = new Paint();
        ringPaint.setAntiAlias(true);
        ringPaint.setStrokeWidth(builder.ringThickness);
        ringPaint.setColor(builder.ringColor);
        ringPaint.setAlpha((int) (builder.ringAlpha * 255));
        ringPaint.setStyle(Paint.Style.STROKE);

        piePaint = new Paint();
        piePaint.setAntiAlias(true);
        piePaint.setColor(builder.pieColor);
        piePaint.setAlpha((int) (builder.pieAlpha * 255));

        float piePaddingValue = (builder.ringBorderPadding + builder.pieRingDistance) * size;
        bgRectF = new RectF(0, 0, size, size);
        pieRectF = new RectF(0 + piePaddingValue / 2, 0 + piePaddingValue / 2, size - piePaddingValue / 2, size - piePaddingValue / 2);

    }

    public static class Builder {

        public static final int DIRECT_CLOCKWISE = 0;
        public static final int DIRECT_ANTI_CLOCKWISE = 1;

        private Context context;

        private float sizeRatio = 0.2f;

        private int bgColor = Color.BLACK;
        private float bgAlpha = 0.5f;
        private float bgCornerRadius = 20f;

        private int ringColor = Color.WHITE;
        private float ringAlpha = 0.9f;
        private float ringBorderPadding = 0.2f;
        private int ringThickness = 3;

        private int pieColor = Color.WHITE;
        private float pieAlpha = 0.9f;
        private float pieRingDistance = 0.08f;

        private int direction = DIRECT_CLOCKWISE;
        private float speed = 6.67f;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder bgColor(int color) {
            this.bgColor = color;
            return this;
        }

        public Builder bgAlpha(float alpha) {
            this.bgAlpha = alpha;
            return this;
        }

        public Builder bgCornerRadius(float cornerRadius) {
            this.bgCornerRadius = cornerRadius;
            return this;
        }

        public Builder ringColor(int color) {
            this.ringColor = color;
            return this;
        }

        public Builder ringAlpha(float alpha) {
            this.ringAlpha = alpha;
            return this;
        }

        public Builder ringBorderPadding(float padding) {
            this.ringBorderPadding = padding;
            return this;
        }

        public Builder ringThickness(int thickness) {
            this.ringThickness = thickness;
            return this;
        }

        public Builder pieColor(int color) {
            this.pieColor = color;
            return this;
        }

        public Builder pieAlpha(float alpha) {
            this.pieAlpha = alpha;
            return this;
        }

        public Builder pieRingDistance(float distance) {
            this.pieRingDistance = distance;
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

        public CircleProgressPie build() {
            return new CircleProgressPie(this);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(bgRectF, bgCornerRadius, bgCornerRadius, bgPaint);
        canvas.drawCircle(size / 2, size / 2, (size * (1 - ringBorderPadding)) / 2, ringPaint);
        canvas.drawArc(pieRectF, -90, (float) (360.0 / (slides - 1) * currentFocusIndex), true, piePaint);
    }

    @Override
    public void show() {
        super.show();
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentFocusIndex = (int) (spinCount % slides);
                handler.sendEmptyMessage(0);
                spinCount++;
            }
        }, 120, 120);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        bgRectF = null;
        pieRectF = null;
        bgPaint = null;
        ringPaint = null;
        piePaint = null;
    }

    public void showAndUpdate(int percentage) {
        slides = 100;
        if (currentFocusIndex >= 0 && currentFocusIndex < 100) {
            currentFocusIndex = percentage;
        }
        invalidate();
    }

}
