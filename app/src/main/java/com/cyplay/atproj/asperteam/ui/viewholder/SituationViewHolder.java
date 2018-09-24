package com.cyplay.atproj.asperteam.ui.viewholder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyplay.atproj.asperteam.R;

import atproj.cyplay.com.asperteamapi.model.SituationResource;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * Created by andre on 21-Apr-18.
 */

public class SituationViewHolder extends ItemViewHolder<SituationResource> {

    @Inject
    Picasso picasso;

    @Nullable
    @BindView(R.id.titleText)
    TextView titleText;

    @BindView(R.id.imageView)
    ImageView imageView;


    public SituationViewHolder(View view) {
        super(view);
    }

    @Override
    public void setItem(SituationResource resource) {
        if (imageView != null)
            picasso.load(resource.getImage()).into(imageView);

        if (titleText != null) {
            if (resource != null)
                titleText.setText(resource.getTitle());
            else
                titleText.setText(null);
        }
    }

}
