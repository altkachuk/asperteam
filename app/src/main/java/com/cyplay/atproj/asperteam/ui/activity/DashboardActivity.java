package com.cyplay.atproj.asperteam.ui.activity;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseMenuActivity;

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
        Intent dayHistoryIntent = new Intent(getApplicationContext(), HistoryActivity.class);
        dayHistoryIntent.putExtra("period", HistoryActivity.DAY_PERIOD);
        this.startActivity(dayHistoryIntent);
    }

    @OnClick(R.id.weekHistoryItem)
    public void onClickMyHistoryItem() {
        Intent weekHistoryIntent = new Intent(getApplicationContext(), HistoryActivity.class);
        weekHistoryIntent.putExtra("period", HistoryActivity.WEEK_PERIOD);
        this.startActivity(weekHistoryIntent);
    }

    @OnClick(R.id.monthHistoryItem)
    public void onClickMyDataItem() {
        Intent monthHistoryIntent = new Intent(getApplicationContext(), HistoryActivity.class);
        monthHistoryIntent.putExtra("period", HistoryActivity.MONTH_PERIOD);
        this.startActivity(monthHistoryIntent);
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
