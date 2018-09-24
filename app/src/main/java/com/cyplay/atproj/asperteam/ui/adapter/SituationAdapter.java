package com.cyplay.atproj.asperteam.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyplay.atproj.asperteam.R;

import atproj.cyplay.com.asperteamapi.model.SituationResource;

import com.cyplay.atproj.asperteam.ui.viewholder.SituationViewHolder;

import java.util.List;

/**
 * Created by andre on 21-Apr-18.
 */

public class SituationAdapter extends ItemAdapter<SituationResource, SituationViewHolder> {


    public SituationAdapter(Context context, List<SituationResource> resources) {
        super(context);
        _items = resources;
    }

    @Override
    public SituationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_situation, parent, false);
        return new SituationViewHolder(view);
    }
}
