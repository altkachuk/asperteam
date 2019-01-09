package com.cyplay.atproj.asperteam.dagger;

import atproj.cyplay.com.asperteamapi.dagger.component.ProfileAdminInteractorComponent;
import com.cyplay.atproj.asperteam.dagger.component.BandComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.CrossknowledgeComponent;
import com.cyplay.atproj.asperteam.dagger.component.FacebookComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.InteractorComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.NetComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.PicassoComponent;
import atproj.cyplay.com.asperteamapi.dagger.component.UserSettingsComponent;
import atproj.cyplay.com.asperteamapi.dagger.scope.RuntimeScope;

import com.cyplay.atproj.asperteam.dagger.component.NotificationComponent;
import com.cyplay.atproj.asperteam.service.BandService;
import com.cyplay.atproj.asperteam.ui.activity.AddSituationActivity;
import com.cyplay.atproj.asperteam.ui.activity.BandActivity;
import com.cyplay.atproj.asperteam.ui.activity.DashboardActivity;
import com.cyplay.atproj.asperteam.ui.activity.FaqActivity;
import com.cyplay.atproj.asperteam.ui.activity.HistoryActivity;
import com.cyplay.atproj.asperteam.ui.activity.EditMyProfileActivity;
import com.cyplay.atproj.asperteam.ui.activity.EditStaffProfileActivity;
import com.cyplay.atproj.asperteam.ui.activity.HelpActivity;
import com.cyplay.atproj.asperteam.ui.activity.HomeActivity;
import com.cyplay.atproj.asperteam.ui.activity.MyProfileActivity;
import com.cyplay.atproj.asperteam.ui.activity.MySuggestionsActivity;
import com.cyplay.atproj.asperteam.ui.activity.ParametersActivity;
import com.cyplay.atproj.asperteam.ui.activity.ProblemCategoriesActivity;
import com.cyplay.atproj.asperteam.ui.activity.ProfileActivity;
import com.cyplay.atproj.asperteam.ui.activity.RelaxActivity;
import com.cyplay.atproj.asperteam.ui.activity.SituationsActivity;
import com.cyplay.atproj.asperteam.ui.activity.StaffProfileActivity;
import com.cyplay.atproj.asperteam.ui.activity.TermsOfServiceActivity;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseActivity;
import com.cyplay.atproj.asperteam.ui.activity.SplashActivity;
import com.cyplay.atproj.asperteam.ui.activity.StartActivity;
import com.cyplay.atproj.asperteam.ui.customview.StressHistoryFragment;
import com.cyplay.atproj.asperteam.ui.fragment.AddSituationFragment;
import com.cyplay.atproj.asperteam.ui.fragment.AdviserHomeFragment;
import com.cyplay.atproj.asperteam.ui.fragment.BottomMenuFragment;
import com.cyplay.atproj.asperteam.ui.fragment.GeolocationPermissionFragment;
import com.cyplay.atproj.asperteam.ui.fragment.LoginFragment;
import com.cyplay.atproj.asperteam.ui.fragment.MyProfileItemFragment;
import com.cyplay.atproj.asperteam.ui.fragment.NavigationFragment;
import com.cyplay.atproj.asperteam.ui.fragment.RmssdHistoryFragment;
import com.cyplay.atproj.asperteam.ui.fragment.StaffProfileItemFragment;
import com.cyplay.atproj.asperteam.ui.fragment.StressHomeFragment;
import com.cyplay.atproj.asperteam.ui.fragment.base.BaseFragment;
import com.cyplay.atproj.asperteam.ui.fragment.base.BasePopupFragment;
import com.cyplay.atproj.asperteam.ui.viewholder.ProblemCategoryViewHolder;
import com.cyplay.atproj.asperteam.ui.viewholder.SituationViewHolder;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by andre on 30-Mar-18.
 */

@RuntimeScope
@Component(dependencies = {
        NetComponent.class,
        UserSettingsComponent.class,
        PicassoComponent.class,
        FacebookComponent.class,
        InteractorComponent.class,
        ProfileAdminInteractorComponent.class,
        BandComponent.class,
        CrossknowledgeComponent.class,
        NotificationComponent.class})

@Singleton
public interface AsperTeamComponent {
    void inject(BaseActivity activity);

    // activities
    void inject(SplashActivity splashActivity);
    void inject(BandActivity bandActivity);
    void inject(StartActivity startActivity);
    void inject(ProfileActivity profileActivity);
    void inject(MyProfileActivity myProfileActivity);
    void inject(EditMyProfileActivity editMyProfileActivity);
    void inject(StaffProfileActivity staffProfileActivity);
    void inject(EditStaffProfileActivity editStaffProfileActivity);
    void inject(ProblemCategoriesActivity problemCategoriesActivity);
    void inject(AddSituationActivity addSituationActivity);
    void inject(SituationsActivity situationsActivity);
    void inject(HomeActivity homeActivity);
    void inject(HelpActivity helpActivity);
    void inject(RelaxActivity relaxActivity);
    void inject(DashboardActivity dashboardActivity);
    void inject(ParametersActivity parametersActivity);
    void inject(TermsOfServiceActivity termsOfServiceActivity);
    void inject(HistoryActivity dayHistoryActivity);
    void inject(MySuggestionsActivity mySuggestionsActivity);
    void inject(FaqActivity faqActivity);

    // fragments
    void inject(BaseFragment baseFragment);
    void inject(BasePopupFragment basePopupFragment);
    void inject(LoginFragment loginFragment);
    void inject(MyProfileItemFragment myProfileItemFragment);
    void inject(StaffProfileItemFragment staffProfileItemFragment);
    void inject(NavigationFragment navigationFragment);
    void inject(StressHomeFragment stressHomeFragment);
    void inject(AdviserHomeFragment adviserHomeFragment);
    void inject(AddSituationFragment addSituationFragment);
    void inject(StressHistoryFragment stressHistoryFragment);
    void inject(RmssdHistoryFragment dayRmssdHistoryFragment);
    void inject(GeolocationPermissionFragment geolocationPermissionFragment);
    void inject(BottomMenuFragment bottomMenuFragment);

    // View Holder
    void inject(ProblemCategoryViewHolder problemCategoryViewHolder);
    void inject(SituationViewHolder situationViewHolder);

    // Services
    void inject(BandService bandService);

}
