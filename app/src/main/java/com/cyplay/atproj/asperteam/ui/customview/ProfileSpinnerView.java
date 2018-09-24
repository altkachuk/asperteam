package com.cyplay.atproj.asperteam.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.ui.customview.base.BaseResourceView;

import butterknife.BindView;
import butterknife.OnItemSelected;

/**
 * Created by andre on 11-May-18.
 */

public class ProfileSpinnerView extends BaseResourceView {

    @BindView(R.id.titleText)
    TextView titleText;

    @BindView(R.id.spinnerView)
    Spinner spinnerView;

    private String[] _items;
    private Object[] _values;

    private OnValueChangedListener _listener;

    public ProfileSpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyFragment);
        int titleRes = a.getResourceId(R.styleable.MyFragment_title_res, -1);

        titleText.setText(titleRes);
    }

    public void setOnValueChangedListener(OnValueChangedListener listener) {
        _listener = listener;
    }

    public void setItems(String[] texts, Object[] values) {
        _items = texts;
        _values = values;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, _items);
        spinnerView.setAdapter(adapter);
    }

    public void setSelectedItem(Object item) {
        for (int i = 0; i < _values.length; i++) {
            if (_values[i] == item)
                spinnerView.setSelection(i);
        }
    }

    @OnItemSelected(R.id.spinnerView)
    public void onItemSelectedMonthSpinnerView() {
        if (_listener != null)
            _listener.onChanged(_values[spinnerView.getSelectedItemPosition()]);
    }

    public interface OnValueChangedListener {
        void onChanged(Object value);
    }
}
