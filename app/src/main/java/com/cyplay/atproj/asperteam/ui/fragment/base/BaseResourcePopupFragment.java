package com.cyplay.atproj.asperteam.ui.fragment.base;

import android.view.View;
import android.widget.RelativeLayout;

import com.cyplay.atproj.asperteam.R;

import butterknife.BindView;

/**
 * Created by andre on 14-Apr-18.
 */

public class BaseResourcePopupFragment extends BasePopupFragment {

    @BindView(R.id.progressBar)
    RelativeLayout progressBar;

    public void load() {
        ;
    }

    public void showPreloader() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hidePreloader() {
        progressBar.setVisibility(View.GONE);
    }
}
