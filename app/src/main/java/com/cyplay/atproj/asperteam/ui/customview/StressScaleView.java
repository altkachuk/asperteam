package com.cyplay.atproj.asperteam.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cyplay.atproj.asperteam.R;

/**
 * Created by andre on 18-Apr-18.
 */

public class StressScaleView extends RelativeLayout {

    private ImageView scaleView;

    private Bitmap _scaleBitmap;
    private Bitmap _scaleEmptyBitmap;

    private int _minimum = 0;
    private int _newMinimum = 0;
    private boolean _hasMinimum = false;
    private int _maximum = 100;
    private int _newMaximum = 0;
    private boolean _hasMaximum = false;
    private int _stress = 50;

    public StressScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyFragment);
        int stressSrc = a.getResourceId(R.styleable.MyFragment_stressSrc, R.mipmap.ic_launcher);
        _scaleBitmap = BitmapFactory.decodeResource(getResources(), stressSrc);
        int stressEmptySrc = a.getResourceId(R.styleable.MyFragment_stressEmptySrc, R.mipmap.ic_launcher);
        _scaleEmptyBitmap = BitmapFactory.decodeResource(getResources(), stressEmptySrc);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_stress_scale, this, true);

        scaleView = (ImageView) getChildAt(0);
    }



    public void setMinimum(int value) {
        value = (int)((float)value * 0.8f);
        if (_hasMaximum && value <= _newMaximum) {
            _minimum = value;
            _maximum = _newMaximum;
            _hasMaximum = false;
        } else if (_minimum <= _maximum) {
            _minimum = value;
        } else {
            _newMinimum = value;
            _hasMinimum = true;
        }

        if (_stress < _minimum)
            _stress = _minimum;
    }

    public void setMaximum(int value) {
        value = (int)((float)value * 1.2f);
        if (_hasMinimum && value >= _newMinimum) {
            _maximum = value;
            _minimum = _newMinimum;
            _hasMinimum = false;
        } else if (_maximum >= _minimum) {
            _maximum = value;
        } else {
            _newMaximum = value;
            _hasMaximum = true;
        }

        if (_stress > _maximum)
            _stress = _maximum;
    }

    public void setStress(int value) {
        if (value >= _minimum && value <= _maximum) {
            _stress = value;
        }

        update();
    }

    private void update() {
        int local = (_maximum - _minimum) - (_maximum - _stress);
        float x = 0;
        if (_maximum > _minimum)
            x = (float)local / (float)(_maximum - _minimum);
        x *= (float)_scaleBitmap.getWidth();

        draw((int)x);
    }

    private void draw(int x) {
        Bitmap result = Bitmap.createBitmap(_scaleBitmap.getWidth(), _scaleBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Rect scaleRect = new Rect(0, 0, x, _scaleBitmap.getHeight());
        Rect scaleEmptyRect = new Rect(x, 0, _scaleBitmap.getWidth(), _scaleBitmap.getHeight());

        canvas.drawBitmap(_scaleBitmap, scaleRect, scaleRect, paint);
        canvas.drawBitmap(_scaleEmptyBitmap, scaleEmptyRect, scaleEmptyRect, paint);

        scaleView.setImageBitmap(result);
    }
}
