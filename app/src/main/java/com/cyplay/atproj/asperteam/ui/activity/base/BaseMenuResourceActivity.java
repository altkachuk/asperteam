package com.cyplay.atproj.asperteam.ui.activity.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.cyplay.atproj.asperteam.ui.RequestCode;

/**
 * Created by andre on 16-Apr-18.
 */

public class BaseMenuResourceActivity extends BaseMenuActivity {

    protected boolean _updated = false;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        load();
    }

    protected void load() {
        ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.RELOAD_REQUEST && resultCode == Activity.RESULT_OK) {
            _updated = true;
            load();
        }
    }
}
