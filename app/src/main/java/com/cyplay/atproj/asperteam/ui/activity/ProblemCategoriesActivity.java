package com.cyplay.atproj.asperteam.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cyplay.atproj.asperteam.R;
import atproj.cyplay.com.asperteamapi.domain.interactor.SituationInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.Situation;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseMenuResourceActivity;
import com.cyplay.atproj.asperteam.ui.adapter.ProblemCategoryAdapter;
import com.cyplay.atproj.asperteam.ui.decorator.DividerItemDecoration;
import com.cyplay.atproj.asperteam.ui.listener.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by andre on 20-Apr-18.
 */

public class ProblemCategoriesActivity extends BaseMenuResourceActivity {

    @Inject
    SituationInteractor situationInteractor;

    @BindView(R.id.categoryListRecyclerView)
    RecyclerView recyclerView;

    private ProblemCategoryAdapter _adapter;
    private List<Situation> _situations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_categories);

        _adapter = new ProblemCategoryAdapter(getApplicationContext(), new ArrayList<Situation>());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupRecyclerView();
    }

    @Override
    protected void onPostMenuCreated() {
        getSituations();
    }

    private void getSituations() {
        showPreloader();
        situationInteractor.getSituations(Situation.STRESS_TYPE, new ResourceRequestCallback<List<Situation>>() {
            @Override
            public void onSucess(List<Situation> situations) {
                hidePreloader();
                _situations = situations;
                _adapter.setItems(_situations);
            }

            @Override
            public void onError(BaseException e) {
                hidePreloader();
            }
        });
    }

    private void setupRecyclerView() {
        // optimization
        recyclerView.setHasFixedSize(true);
        // set layout
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(_adapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.divider_problem_category));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new CategoryClick()));
    }

    @OnClick(R.id.newButtonLayout)
    public void onClickNewButton() {
        openAddSituationActivity();
    }

    private class CategoryClick implements RecyclerItemClickListener.OnItemClickListener {
        @Override
        public void onItemClick(View view, int position) {
            openSituationsActivity(_situations.get(position).getId());
        }
    }

    private void openSituationsActivity(int id) {
        Intent situationsIntent = new Intent(getApplicationContext(), SituationsActivity.class);
        situationsIntent.putExtra("situation_id", id);
        startActivity(situationsIntent);
    }

    private void openAddSituationActivity() {
        Intent addSituationIntent = new Intent(getApplicationContext(), AddSituationActivity.class);
        startActivity(addSituationIntent);
    }
}
