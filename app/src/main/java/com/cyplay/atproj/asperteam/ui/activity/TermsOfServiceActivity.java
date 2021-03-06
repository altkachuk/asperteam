package com.cyplay.atproj.asperteam.ui.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseMenuActivity;

import butterknife.BindView;

/**
 * Created by andre on 18-Jun-18.
 */

public class TermsOfServiceActivity extends BaseMenuActivity {

    @BindView(R.id.termsOfServiceText)
    TextView termsOfServiceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_service);
        Spanned description = Html.fromHtml(
                getString(R.string.terms_of_condition_text1) +
                        getString(R.string.terms_of_condition_text2) +
                        getString(R.string.terms_of_condition_text3)
        );
        termsOfServiceText.setText(description);
    }

}
