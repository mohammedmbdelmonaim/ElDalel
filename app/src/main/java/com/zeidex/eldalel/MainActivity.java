package com.zeidex.eldalel;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zeidex.eldalel.adapters.MainViewPagerAdapter;
import com.zeidex.eldalel.hostfragments.AccountAfterLoginHostFragment;
import com.zeidex.eldalel.hostfragments.BasketHostFragment;
import com.zeidex.eldalel.hostfragments.CategoriesHostFragment;
import com.zeidex.eldalel.hostfragments.MainHostFragment;
import com.zeidex.eldalel.hostfragments.OffersHostFragment;
import com.zeidex.eldalel.response.GetRatingResponse;
import com.zeidex.eldalel.services.AddRatingApi;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.Constants;
import com.zeidex.eldalel.utils.KeyboardUtils;
import com.zeidex.eldalel.utils.PreferenceUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SELECTED_ITEM;
import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bottom_nav)
    BottomNavigationView mBottomNav;
//    @BindView(R.id.main_viewpager)
//    CustomViewPager mainViewPager;
    private int mSelectedItem;
    public boolean login;
    Fragment frag = null;
    private MainViewPagerAdapter mViewPagerAdapter;
    private TextView mBadgeCount;
    private boolean isBadgeInitialized;

    Dialog rate_dialog;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        if (getIntent().getBooleanExtra("from_notification" , false)){
            rate_dialog = new Dialog(this);
            rate_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            rate_dialog.setContentView(R.layout.dialog_rating);
            rate_dialog.setCancelable(true);
            RatingBar rating = rate_dialog.findViewById(R.id.rating);
            AppCompatEditText comment_rating = rate_dialog.findViewById(R.id.comment_rating);
            AppCompatButton btn_rating = rate_dialog.findViewById(R.id.btn_rating);
            if (PreferenceUtils.getUserLogin(this)) {
                token = PreferenceUtils.getUserToken(this);
            } else if (PreferenceUtils.getCompanyLogin(this)) {
                token = PreferenceUtils.getCompanyToken(this);
            } else if (PreferenceUtils.getSalesmanLogin(this)){
                token = PreferenceUtils.getSalesmanToken(this);
            }
            btn_rating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    convertJson(rating.getRating()+"" , comment_rating.getText().toString());
                    AddRatingApi addRatingApi = APIClient.getClient(SERVER_API_TEST).create(AddRatingApi.class);
                    Call<GetRatingResponse> getRatingResponseCall;
                    if (PreferenceUtils.getCompanyLogin(MainActivity.this)) {
                        getRatingResponseCall = addRatingApi.getCompanyRating(Integer.parseInt(getIntent().getStringExtra("product_id")) , post);
                    } else {
                        getRatingResponseCall = addRatingApi.getUserRating(Integer.parseInt(getIntent().getStringExtra("product_id")) , post);
                    }
                    getRatingResponseCall.enqueue(new Callback<GetRatingResponse>() {
                        @Override
                        public void onResponse(Call<GetRatingResponse> call, Response<GetRatingResponse> response) {
                            String status = response.body().getStatus();
                            if (status.equalsIgnoreCase("success")){
                                Toasty.info(MainActivity.this , "Thank you" , Toast.LENGTH_LONG).show();
                                KeyboardUtils.hideKeyboard(MainActivity.this);
                                rate_dialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetRatingResponse> call, Throwable t) {
                            Toasty.error(MainActivity.this, getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            rate_dialog.show();
        }

        if(PreferenceUtils.getSalesmanLogin(this)){
            startActivity(new Intent(this, SalesmanActivity.class));
            finish();
        }

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
                if (item.getItemId() == R.id.basket_fragment){
                    if (!PreferenceUtils.getUserLogin(MainActivity.this) &&  !PreferenceUtils.getCompanyLogin(MainActivity.this)) {
                        Toasty.error(MainActivity.this,getString(R.string.please_login_first), Toast.LENGTH_LONG).show();
                        return false;
                    }else{
                        selectFragment(item);
                        return true;
                    }
                }else {
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

        initializeBasketBadge();
    }

    private void initializeBasketBadge() {
        int cartCount = PreferenceUtils.getCountOfItemsBasket(this);
        if (cartCount > 0) {
            BottomNavigationMenuView bottomNavigationMenuView =
                    (BottomNavigationMenuView) mBottomNav.getChildAt(0);
            View v = bottomNavigationMenuView.getChildAt(3);
            BottomNavigationItemView itemView = (BottomNavigationItemView) v;

            View badge = LayoutInflater.from(this)
                    .inflate(R.layout.badge_layout, itemView, true);

            mBadgeCount = badge.findViewById(R.id.notificationsBadgeTextView);
            mBadgeCount.setText(cartCount + "");
            isBadgeInitialized = true;
        }
    }

    public void updateBasketBadge() {
        int basketCount = PreferenceUtils.getCountOfItemsBasket(this);
        if (basketCount > 0) {
            if (isBadgeInitialized)
                incrementBasketBadgeCount(basketCount);
            else
                initializeBasketBadge();
        } else {
            removeBasketBadge();
        }
    }

    public void incrementBasketBadgeCount(int basketCount) {
        mBadgeCount.setText(basketCount + "");
    }

    public void removeBasketBadge() {
        if (isBadgeInitialized) {
            BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) mBottomNav.getChildAt(0);
            View v = bottomNavigationMenuView.getChildAt(3);
            BottomNavigationItemView itemView = (BottomNavigationItemView) v;
            itemView.removeViewAt(itemView.getChildCount() - 1);
            isBadgeInitialized = false;
        }
    }
    Map<String, String> post;

    private void convertJson(String rate, String comment) {
        post = new HashMap<>();
        post.put("rate", rate);
        post.put("comment", comment);
        post.put("token", token);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        outState.putString(Constants.KEY_LOCALE, PreferenceUtils.getLocaleKey(this));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateBasketBadge();
    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
//        if (mSelectedItem != homeItem.getItemId()) {
//            // select home item
//            selectFragment(homeItem);
//            homeItem.setChecked(true);
//
//        } else {
            super.onBackPressed();
//            Animatoo.animateSwipeRight(this);
//        }
    }

    public void navigateToOffers() {
//        CategoriesFragment categoriesFragment = new CategoriesFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt("category_id", categoryId);
//        categoriesFragment.setArguments(bundle);
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
//        ft.replace(R.id.container_activity, categoriesFragment, "categorie_fragment");
//        ft.commit();
        MenuItem offersItem = mBottomNav.getMenu().getItem(2);
        offersItem.setChecked(true);
        selectFragment(offersItem);
//        mSelectedItem = categoriesItem.getItemId();
//        categoriesItem.setChecked(true);
    }

    String tag;
    public void selectFragment(MenuItem item) {
        Fragment frag = null;
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.main_fragment:
                frag = new MainHostFragment();
                tag = "main_fragment";
                break;

            case R.id.categories_fragment:
                frag = new CategoriesHostFragment();
                tag = "categorie_fragment";
                break;

            case R.id.account_fragment:
                login = PreferenceUtils.getUserLogin(this);
                if (!login && !PreferenceUtils.getCompanyLogin(this)){
                    frag = new AccountFragment();
                    tag = "account_fragment";
                }else {
                    frag = new AccountAfterLoginHostFragment();
                    tag = "accountafterlogin_fragment";
                }
                break;

            case R.id.basket_fragment:
                frag = new BasketHostFragment();
                tag = "basket_fragment";
                break;

            case R.id.offers_fragment:
                frag = new OffersHostFragment();
                tag = "offers_fragment";
                break;
        }

//         update selected item
        mSelectedItem = item.getItemId();
//


//        updateToolbarText(item.getTitle());

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
            ft.replace(R.id.container_activity, frag, tag);
            ft.commit();
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


    public void navigateToHomeFragment() {
        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
        if (mSelectedItem != homeItem.getItemId()) {
            // select home item
            selectFragment(homeItem);
            homeItem.setChecked(true);
        }
    }


}
