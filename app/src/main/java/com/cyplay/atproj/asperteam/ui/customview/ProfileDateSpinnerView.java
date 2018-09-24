package com.cyplay.atproj.asperteam.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.ui.customview.base.BaseResourceView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.OnItemSelected;

/**
 * Created by andre on 11-May-18.
 */

public class ProfileDateSpinnerView extends BaseResourceView {

    private final int MAX_YEARS = 100;

    @BindView(R.id.titleText)
    TextView titleText;

    @BindView(R.id.yearSpinnerView)
    Spinner yearSpinnerView;

    @BindView(R.id.monthSpinnerView)
    Spinner monthSpinnerView;

    @BindView(R.id.daySpinnerView)
    Spinner daySpinnerView;

    private OnValueChangedListener _listener;

    public ProfileDateSpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyFragment);
        int titleRes = a.getResourceId(R.styleable.MyFragment_title_res, -1);

        titleText.setText(titleRes);

        int year = generateYears();
        int month = generateMonths();
        generateDays(year, month);
    }

    public void setOnValueChangedListener(OnValueChangedListener listener) {
        _listener = listener;
    }

    private int generateYears() {
        String[] years = new String[MAX_YEARS];
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < MAX_YEARS; i++) {
            int y = currentYear - i;
            years[i] = String.valueOf(y);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, years);
        yearSpinnerView.setAdapter(adapter);

        return Integer.parseInt(years[0]);
    }

    private int generateMonths() {
        String[] months = new String[12];
        for (int i = 0; i < 12; i++) {
            String sMonth = String.valueOf(i+1);
            if (sMonth.length() == 1)
                sMonth = "0"+ sMonth;
            months[i] = sMonth;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, months);
        monthSpinnerView.setAdapter(adapter);

        return Integer.parseInt(months[0]);
    }

    private int generateDays(int year, int month) {
        // Create a calendar object and set year and month
        Calendar mycal = new GregorianCalendar(year, month-1, 1);

        // Get the number of days in that month
        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
        String[] days = new String[daysInMonth];
        for (int i = 0; i < daysInMonth; i++) {
            String sDay = String.valueOf(i+1);
            if (sDay.length() == 1)
                sDay = "0"+ sDay;
            days[i] = sDay;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, days);
        daySpinnerView.setAdapter(adapter);

        return daysInMonth;
    }

    @OnItemSelected(R.id.yearSpinnerView)
    public void onItemSelectedYearSpinnerView() {
        updateDays();
        if (_listener != null)
            _listener.onChanged(getDate());
    }

    @OnItemSelected(R.id.monthSpinnerView)
    public void onItemSelectedMonthSpinnerView() {
        updateDays();
        if (_listener != null)
            _listener.onChanged(getDate());
    }

    @OnItemSelected(R.id.daySpinnerView)
    public void onItemSelectedDaySpinnerView() {
        if (_listener != null)
            _listener.onChanged(getDate());
    }

    private void updateDays() {
        int year = Integer.parseInt(yearSpinnerView.getSelectedItem().toString());
        int month = Integer.parseInt(monthSpinnerView.getSelectedItem().toString());
        int day = Integer.parseInt(daySpinnerView.getSelectedItem().toString());

        int daysInMonth = generateDays(year, month);
        if (day <= daysInMonth)
            daySpinnerView.setSelection(day - 1);
    }

    public void setDate(String birthDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Calendar tCal = Calendar.getInstance();
        Calendar bCal = Calendar.getInstance();
        try {
            bCal.setTime(format.parse(birthDate));
        } catch (ParseException e) {
            ;
        }

        yearSpinnerView.setSelection(tCal.get(Calendar.YEAR) - bCal.get(Calendar.YEAR));
        monthSpinnerView.setSelection(bCal.get(Calendar.MONTH));
        generateDays(bCal.get(Calendar.YEAR), bCal.get(Calendar.MONTH));
        daySpinnerView.setSelection(bCal.get(Calendar.DAY_OF_MONTH) - 1);
    }

    private String getDate() {
        Calendar tCal = Calendar.getInstance();

        String date = yearSpinnerView.getSelectedItem().toString() + "-"
                + monthSpinnerView.getSelectedItem().toString() + "-"
                +daySpinnerView.getSelectedItem().toString()
                +"T00:00:00Z";
        return date;
    }

    public interface OnValueChangedListener {
        void onChanged(String value);
    }
}
