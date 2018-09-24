package com.cyplay.atproj.asperteam.ui.viewholder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.cyplay.atproj.asperteam.R;
import atproj.cyplay.com.asperteamapi.model.Situation;

import butterknife.BindView;


/**
 * Created by andre on 21-Apr-18.
 */

public class ProblemCategoryViewHolder extends ItemViewHolder<Situation> {

    @Nullable
    @BindView(R.id.titleText)
    TextView titleText;


    public ProblemCategoryViewHolder(View view) {
        super(view);
    }

    @Override
    public void setItem(Situation situation) {
        if (titleText != null) {
            if (situation != null)
                titleText.setText(situation.getTitle());
            else
                titleText.setText(null);
        }
    }

}
