package com.zeidex.eldalel;

//public class FragmentFooterView extends Fragment {
//    @BindView(R.id.bottom_nav)
//    BottomNavigationView mBottomNav;
//    private int mSelectedItem;
//    public boolean login;
//    int id;
//
//    public FragmentFooterView(int id) {
//        this.id = id;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_footer_view, container, false);
//
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        ButterKnife.bind(this, view);
//        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if (item.getItemId() == R.id.basket_fragment) {
//                    if (!PreferenceUtils.getUserLogin(getContext()) && !PreferenceUtils.getCompanyLogin(getContext())) {
//                        Toasty.error(getContext(), getString(R.string.please_login_first), Toast.LENGTH_LONG).show();
//                        return false;
//                    } else {
//                        selectFragment(item);
//                        return true;
//                    }
//                } else {
//                    selectFragment(item);
//                    return true;
//                }
//            }
//        });
//        MenuItem selectedItem;
//        if (id == R.id.container_activity) {
//            if (savedInstanceState != null) {
//                mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
//                selectedItem = mBottomNav.getMenu().findItem(mSelectedItem);
//            } else {
//                selectedItem = mBottomNav.getMenu().getItem(0);
//                selectedItem.setChecked(true);
//            }
//            selectFragment(selectedItem);
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putInt(SELECTED_ITEM, mSelectedItem);
//        outState.putString(Constants.KEY_LOCALE, PreferenceUtils.getLocaleKey(getContext()));
//        super.onSaveInstanceState(outState);
//    }
//
////    @Override
////    public void onBackPressed() {
////        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
////        if (mSelectedItem != homeItem.getItemId()) {
////            // select home item
////            selectFragment(homeItem);
////            homeItem.setChecked(true);
////
////        } else {
////            getContext().onBackPressed();
////            Animatoo.animateSwipeRight(getContext());
////        }
////    }
//
//    String tag;
//
//    public void selectFragment(MenuItem item) {
//        Fragment frag = null;
//        // init corresponding fragment
//        switch (item.getItemId()) {
//            case R.id.main_fragment:
//                frag = new MainFragment();
//                tag = "main_fragment";
//                break;
//
//            case R.id.categories_fragment:
//                frag = new CategoriesFragment();
//                tag = "categorie_fragment";
//                break;
//
//            case R.id.account_fragment:
//                login = PreferenceUtils.getUserLogin(getContext());
//                if (!login && !PreferenceUtils.getCompanyLogin(getContext())) {
//                    frag = new AccountFragment();
//                    tag = "account_fragment";
//                } else {
//                    frag = new AccountAfterLoginFragment();
//                    tag = "accountafterlogin_fragment";
//                }
//                break;
//
//            case R.id.basket_fragment:
//                frag = new BasketFragment();
//                tag = "basket_fragment";
//                break;
//
//            case R.id.offers_fragment:
//                frag = new OffersFragment();
//                tag = "offers_fragment";
//                break;
//        }
//
////         update selected item
//        mSelectedItem = item.getItemId();
////
//
//
////        updateToolbarText(item.getTitle());
//
//        if (frag != null) {
//                FragmentTransaction ft = getContext().getSupportFragmentManager().beginTransaction();
//                ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
//                ft.replace(R.id.container_activity, frag, tag);
//                ft.commit();
//        }
//    }
//}
