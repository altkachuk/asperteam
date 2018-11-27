package com.cyplay.atproj.asperteam.ui.activity;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseMenuActivity;
import com.cyplay.atproj.asperteam.utils.ApplicationUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.OnClick;

/**
 * Created by andre on 30-Mar-18.
 */

public class DashboardActivity extends BaseMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @OnClick(R.id.dayHistoryItem)
    public void onClickMyIndicatorsItem() {
        HashMap<String, Integer> extra = new HashMap<>();
        extra.put("period", HistoryActivity.DAY_PERIOD);
        ApplicationUtil.<Integer>startActivityWithIgnoreBatterOptimization(this, HistoryActivity.class,  extra);
    }

    @OnClick(R.id.weekHistoryItem)
    public void onClickMyHistoryItem() {
        HashMap<String, Integer> extra = new HashMap<>();
        extra.put("period", HistoryActivity.WEEK_PERIOD);
        ApplicationUtil.<Integer>startActivityWithIgnoreBatterOptimization(this, HistoryActivity.class,  extra);
    }

    @OnClick(R.id.monthHistoryItem)
    public void onClickMyDataItem() {
        HashMap<String, Integer> extra = new HashMap<>();
        extra.put("period", HistoryActivity.MONTH_PERIOD);
        ApplicationUtil.<Integer>startActivityWithIgnoreBatterOptimization(this, HistoryActivity.class,  extra);
    }

    @OnClick(R.id.relaxItem)
    public void onClickRelaxItem() {
        Intent relaxIntent = new Intent(getApplicationContext(), RelaxActivity.class);
        TaskStackBuilder.create(this)
                .addParentStack(RelaxActivity.class)
                .addNextIntent(relaxIntent)
                .startActivities();
    }


}
