package kai.kaiprivate.func.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import kai.kaiprivate.R;

/**
 * Created by Administrator on 2015/6/17.
 */
public class LovelyView extends View {

    private int mCircleColor;
    private int mLabelColor;
    private String mCircleText;
    private Paint mCirclePaint;

    public LovelyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mCirclePaint = new Paint();
        TypedArray _typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable
                .LovelyView, 0, 0);

        try {
            mCircleText = _typedArray.getString(R.styleable.LovelyView_circleLabel);
            mCircleColor = _typedArray.getInteger(R.styleable.LovelyView_circleColor, 0);
            mLabelColor = _typedArray.getInteger(R.styleable.LovelyView_labelColor, 0);
        } finally {
            _typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        int _viewWidthHalf = this.getMeasuredWidth() / 2;
        int _viewHeightHalf = this.getMeasuredHeight() / 2;
        int _radius = 100;

        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
        canvas.drawCircle(_viewWidthHalf, _viewHeightHalf, _radius, mCirclePaint);

        mCirclePaint.setColor(mLabelColor);
        mCirclePaint.setTextAlign(Paint.Align.CENTER);
        mCirclePaint.setTextSize(50);
        canvas.drawText(mCircleText, _viewWidthHalf, _viewHeightHalf, mCirclePaint);

    }

    public int getCircleColor() {
        return mCircleColor;
    }

    public void setCircleColor(int pColor) {
        mCircleColor = pColor;
        invalidate();
        requestLayout();
    }

    public int getLabelColor() {
        return mLabelColor;
    }

    public void setLabelColor(int pColor) {
        mLabelColor = pColor;
        invalidate();
        requestLayout();
    }

    public String getCircleText() {
        return mCircleText;
    }

    public void setCircleText(String pText) {
        mCircleText = pText;
        invalidate();
        requestLayout();
    }
}
