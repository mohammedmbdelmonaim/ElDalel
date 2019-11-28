package com.zeidex.eldalel.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.zeidex.eldalel.AccountAfterLoginFragment;
import com.zeidex.eldalel.AccountFragment;
import com.zeidex.eldalel.BasketFragment;
import com.zeidex.eldalel.CategoriesFragment;
import com.zeidex.eldalel.DetailItemActivity;
import com.zeidex.eldalel.MainFragment;
import com.zeidex.eldalel.OffersFragment;
import com.zeidex.eldalel.listeners.FirstPageFragmentListener;

import java.util.ArrayList;
import java.util.List;

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> mFragmentList;
    FragmentManager mFragmentManager;
    FirstPageListener listener = new FirstPageListener();
    private Fragment mFragmentAtPos0;
    MainFragment mMainFragment;
    AccountFragment mAccountFragment;
    BasketFragment mBasketFragment;
    CategoriesFragment mCategoriesFragment;
    OffersFragment mOffersFragment;
    AccountAfterLoginFragment mAccountAfterLoginFragment;

    public MainViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        mFragmentList = new ArrayList<>();
        mFragmentManager = fm;
        mMainFragment = new MainFragment(listener);

        mAccountFragment = new AccountFragment();
        mBasketFragment = new BasketFragment();
        mCategoriesFragment = new CategoriesFragment();
        mOffersFragment = new OffersFragment();
        mAccountAfterLoginFragment = new AccountAfterLoginFragment();
        addFragment(mMainFragment);
        addFragment(mCategoriesFragment);
        addFragment(mOffersFragment);
        addFragment(mBasketFragment);
        addFragment(mAccountFragment);
        addFragment(mAccountAfterLoginFragment);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            if (mFragmentAtPos0 == null) {
                mFragmentAtPos0 = new MainFragment(listener);
            }
            return mFragmentAtPos0;
        } else
            return mFragmentList.get(position);
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    private final class FirstPageListener implements FirstPageFragmentListener {
        public void onSwitchToNextFragment(Bundle bundle) {
            mFragmentManager.beginTransaction().remove(mFragmentAtPos0)
                    .commitNow();
            if (mFragmentAtPos0 instanceof MainFragment) {
                mFragmentAtPos0 = new DetailItemActivity();
            } else { // Instance of NextFragment
                mFragmentAtPos0 = new MainFragment(listener);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (object instanceof MainFragment &&
                mFragmentAtPos0 instanceof DetailItemActivity) {
            return POSITION_NONE;
        }
        if (object instanceof DetailItemActivity &&
                mFragmentAtPos0 instanceof MainFragment) {
            return POSITION_NONE;
        }
        return POSITION_UNCHANGED;
    }
}

