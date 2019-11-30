package com.zeidex.eldalel;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zeidex.eldalel.adapters.MainViewPagerAdapter;
import com.zeidex.eldalel.utils.Constants;
import com.zeidex.eldalel.utils.CustomViewPager;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

import static com.zeidex.eldalel.utils.Constants.SELECTED_ITEM;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bottom_nav)
    BottomNavigationView mBottomNav;
    @BindView(R.id.main_viewpager)
    CustomViewPager mainViewPager;
    private int mSelectedItem;
    public boolean login;
    Fragment frag = null;
    private MainViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainViewPager.setPagingEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        setupViewPager();

//        frag = new FragmentFooterView();
//        if (frag != null) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
//            ft.replace(R.id.fooFragment, frag, "footer");
//            ft.commit();
//        }


//        disableShiftMode(mBottomNav);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.basket_fragment) {
                    if (!PreferenceUtils.getUserLogin(MainActivity.this) && !PreferenceUtils.getCompanyLogin(MainActivity.this)) {
                        Toasty.error(MainActivity.this, getString(R.string.please_login_first), Toast.LENGTH_LONG).show();
                        return false;
                    } else {
                        selectFragment(item);
                        return true;
                    }
                } else {
                    selectFragment(item);
                    return true;
                }
            }
        });

        MenuItem selectedItem;

        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
            selectedItem = mBottomNav.getMenu().findItem(mSelectedItem);
        } else {
            selectedItem = mBottomNav.getMenu().getItem(0);
            selectedItem.setChecked(true);
        }
        selectFragment(selectedItem);
    }


    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
//                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            //Timber.e(e, "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            //Timber.e(e, "Unable to change value of shift mode");
        }
    }

    private void setupViewPager() {
        mViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mainViewPager.setAdapter(mViewPagerAdapter);
//        mainViewPager.setOffscreenPageLimit(5);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        outState.putString(Constants.KEY_LOCALE, PreferenceUtils.getLocaleKey(this));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
//        if (mSelectedItem != homeItem.getItemId()) {
//            // select home item
//            selectFragment(homeItem);
//            homeItem.setChecked(true);
//
////        } else if (mSelectedItem == homeItem.getItemId()) {
////            boolean isDetail = mViewPagerAdapter.onBackPressed();
////            if (!isDetail) {
////                super.onBackPressed();
////                Animatoo.animateSwipeRight(this);
////            }
//        } else {
//            super.onBackPressed();
//            Animatoo.animateSwipeRight(this);
//        }
    }

    public void navigateToCategories(int categoryId) {
        CategoriesFragment categoriesFragment = new CategoriesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("category_id", categoryId);
        categoriesFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
        ft.replace(R.id.container_activity, categoriesFragment, "categorie_fragment");
        ft.commit();
        MenuItem categoriesItem = mBottomNav.getMenu().getItem(1);
        mSelectedItem = categoriesItem.getItemId();
        categoriesItem.setChecked(true);
    }

    String tag;

    public void selectFragment(MenuItem item) {
        Fragment frag = null;
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.main_fragment:
//                frag = mMainFragment;
//                tag = MAIN_FRAGMENT_TAG;
                mainViewPager.setCurrentItem(0);
                break;

            case R.id.categories_fragment:
//                frag = mCategoriesFragment;
//                tag = CATEGORIE_FRAGMENT_TAG;
                mainViewPager.setCurrentItem(1);
                break;

            case R.id.account_fragment:
                login = PreferenceUtils.getUserLogin(this);
                if (!login && !PreferenceUtils.getCompanyLogin(this)) {
//                    frag = mAccountFragment;
//                    tag = ACCOUNT_FRAGMENT_TAG;
                    mainViewPager.setCurrentItem(4);
                } else {
//                    frag = new AccountAfterLoginFragment();
//                    tag = ACCOUNTAFTERLOGIN_FRAGMENT_TAG;
                    mainViewPager.setCurrentItem(5);
                }
                break;

            case R.id.basket_fragment:
//                frag = mBasketFragment;
//                tag = BASKET_FRAGMENT_TAG;
                mainViewPager.setCurrentItem(3);
                break;

            case R.id.offers_fragment:
//                frag = mOffersFragment;
//                tag = OFFERS_FRAGMENT_TAG;
                mainViewPager.setCurrentItem(2);
                break;
        }

//         update selected item
        mSelectedItem = item.getItemId();
//        mFragmentMenuItem.push(mSelectedItem);

//        if (mFragmentsStack.contains(tag)) {
//            mFragmentsStack.remove(tag);
//            mFragmentMenuItem.remove(mSelectedItem);
//        }
//        mFragmentsStack.push(tag);
//        mFragmentMenuItem.push(mSelectedItem);

//
//        pushFragments(tag, frag);

//        updateToolbarText(item.getTitle());

//        if (frag != null) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
//            ft.add(R.id.nav_host_fragment, frag, tag);
//            ft.addToBackStack(null);
//            ft.commit();
//        }

    }

    public void navigateToHomeFragment() {
        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
        if (mSelectedItem != homeItem.getItemId()) {
            // select home item
            selectFragment(homeItem);
            homeItem.setChecked(true);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        String token = PreferenceUtils.getDeviceToken(this);
//
////
////
////
////
//        // [START subscribe_topics]
//        FirebaseMessaging.getInstance().subscribeToTopic("weather")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = "subscribed";
//                        if (!task.isSuccessful()) {
//                            msg = "subscribed failed";
//                        }
////                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
//                    }
//                });
////         [END subscribe_topics]
    }


}
