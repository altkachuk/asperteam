package com.cyplay.atproj.asperteam.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyplay.atproj.asperteam.R;
import atproj.cyplay.com.asperteamapi.model.Situation;
import com.cyplay.atproj.asperteam.ui.viewholder.ProblemCategoryViewHolder;

import java.util.List;

/**
 * Created by andre on 21-Apr-18.
 */

public class ProblemCategoryAdapter extends ItemAdapter<Situation, ProblemCategoryViewHolder> {


    public ProblemCategoryAdapter(Context context, List<Situation> situations) {
        super(context);
        _items = situations;
    }

    @Override
    public ProblemCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_problem_category, parent, false);
        return new ProblemCategoryViewHolder(view);
    }
}
