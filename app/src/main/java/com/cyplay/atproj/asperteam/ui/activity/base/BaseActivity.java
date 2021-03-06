package com.cyplay.atproj.asperteam.ui.activity.base;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.cyplay.atproj.asperteam.R;
import com.cyplay.atproj.asperteam.dagger.App;
import com.cyplay.atproj.asperteam.ui.fragment.BottomMenuFragment;
import com.cyplay.atproj.asperteam.ui.fragment.base.BasePopupFragment;

import javax.inject.Inject;

import atproj.cyplay.com.asperteamapi.domain.interactor.StressInteractor;
import atproj.cyplay.com.asperteamapi.util.UserSettingsUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andre on 29-Mar-18.
 */

public class BaseActivity extends AppCompatActivity {

    @Inject
    UserSettingsUtil userSettings;

    @Inject
    StressInteractor stressInteractor;

    BasePopupFragment popupFragment;

    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    private ProgressDialog _progressDialog;

    BottomMenuFragment bottomMenuFragment;

    private BaseActivity _activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _activity = this;

        injectDependencies();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (getFragmentManager().findFragmentById(R.id.bottomMenuFragment) != null) {
            bottomMenuFragment = (BottomMenuFragment) getFragmentManager().findFragmentById(R.id.bottomMenuFragment);
            bottomMenuFragment.setActiveActivityClass(this.getClass());
        }

        initPopup();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        injectViews();
        getActionBarToolbar();
        updateStatusBarColor();
        initPreloader();
    }

    private void initPreloader() {
        _progressDialog = new ProgressDialog(this);

        _progressDialog.setTitle(getString(R.string.loading));
        _progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        _progressDialog.setCancelable(false);
        _progressDialog.setCanceledOnTouchOutside(false);
    }

    public void showPreloader() {
        _progressDialog.show();
    }

    public void hidePreloader() {
        _progressDialog.dismiss();
    }

    protected void injectDependencies() {
        App.get(this).inject(this);
    }

    protected void injectViews() {
        ButterKnife.bind(this);
    }

    protected Toolbar getActionBarToolbar() {
        if (toolbar != null) {
            // Depending on which version of Android you are on the Toolbar or the ActionBar may be
            // active so the a11y description is set here.
            //DesignUtils.setBackgroundColor(toolbar, Color.WHITE);
            setSupportActionBar(toolbar);
        }
        return toolbar;
    }

    private void updateStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void initPopup() {
        try {
            popupFragment = (BasePopupFragment) getFragmentManager().findFragmentById(R.id.popupFragment);
            popupFragment.hide();
        } catch (Exception e) {
            throw new RuntimeException(getClass().getName()
                    + " is not find BasePopupFragment in XML file");
        }

        popupFragment.setOnNegativeClickListener(onNegativeClickListener);
        popupFragment.setOnPositiveClickListener(onPositiveClickListener);
    }

    public BasePopupFragment getPopup() {
        return popupFragment;
    }

    private BasePopupFragment.OnNegativeClickListener onNegativeClickListener = new BasePopupFragment.OnNegativeClickListener() {
        @Override
        public void onClick(int requestCode) {
            popupFragment.hide();
            onPopupNegativeClick(requestCode);

        }
    };

    protected void onPopupNegativeClick(int requestCode) {
        ;
    }

    private BasePopupFragment.OnPositiveClickListener onPositiveClickListener = new BasePopupFragment.OnPositiveClickListener() {
        @Override
        public void onClick(int requestCode) {
            popupFragment.hide();
            onPopupPositiveClick(requestCode);
        }
    };

    protected void onPopupPositiveClick(int requestCode) {
        ;
    }

    protected void onPermissionDenied() {
        /*getPopup().initPopup(RequestCode.BAND_PERMISSION_DENIED_REQUEST,
                "Permission Denied",
                "Band Permission Denied");
        getPopup().setImage(R.drawable.img_braccelet_not);
        getPopup().setPositive(getString(R.string.ok_button));
        getPopup().show();*/
    }
}
