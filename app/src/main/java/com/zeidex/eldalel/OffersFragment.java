package com.zeidex.eldalel;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.zeidex.eldalel.adapters.OffersAdapter;
import com.zeidex.eldalel.models.Subcategory;
import com.zeidex.eldalel.response.GetOffersCategories;
import com.zeidex.eldalel.services.OffersCategoriesAPI;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.ChangeLang;
import com.zeidex.eldalel.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class OffersFragment extends androidx.fragment.app.Fragment implements OffersAdapter.OffersOperation {
    public static final String OFFER = "offer";
    public static final String SUBCATEGORIES_INTENT_EXTRA_KEY = "subcategories";
    public static final String CATEGORY_ID_INTENT_EXTRA_KEY = "category_id";
    public static final String CATEGORY_NAME_INTENT_EXTRA = "category_name";
    @BindView(R.id.ofeers_recycler)
    RecyclerView ofeers_recycler;
    OffersAdapter offersAdapter;

    Dialog reloadDialog;
    private List<GetOffersCategories.Category> categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offers, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        findViews();
        initializeRecycler();

    }

    private void initializeRecycler() {
        int spanCount = 2; // 3 columns
        int spacing = 20; // 50px
        boolean includeEdge = true;

        ofeers_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        ofeers_recycler.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        ofeers_recycler.setItemAnimator(new DefaultItemAnimator());
//        offersAdapter = new OffersAdapter(getActivity());
//        offersAdapter.setOffersOperation(this);
//        ofeers_recycler.setAdapter(offersAdapter);
    }

    private void findViews() {
        showDialog();
        onLoadPage();
    }

    private void onLoadPage() {
        reloadDialog.show();
        OffersCategoriesAPI offersCategoriesAPI = APIClient.getClient(SERVER_API_TEST).create(OffersCategoriesAPI.class);
        offersCategoriesAPI.getOffersCategories(OFFER).enqueue(new Callback<GetOffersCategories>() {
            @Override
            public void onResponse(Call<GetOffersCategories> call, Response<GetOffersCategories> response) {
                int code = response.body().getCode();
                if (code == 200) {
                    categories = response.body().getData().getCategories();
                    offersAdapter = new OffersAdapter(getActivity(), categories);
                    offersAdapter.setOffersOperation(OffersFragment.this);
                    ofeers_recycler.setAdapter(offersAdapter);

                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetOffersCategories> call, Throwable t) {
                Toasty.error(getActivity(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    private void showDialog() {
        reloadDialog = new Dialog(getActivity());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onClickOffer(int position) {
        GetOffersCategories.Category category = categories.get(position);
        List<GetOffersCategories.Subcategory> subCategories = category.getSubcategories();

        ArrayList<Subcategory> subCategoriesModel = new ArrayList<>();
        for (int i = 0; i < subCategories.size(); i++) {
            subCategoriesModel.add(new Subcategory(subCategories.get(i).getId(), subCategories.get(i).getNameAr(),
                    subCategories.get(i).getName(), ""));
        }


        Intent intent = new Intent(getActivity(), OfferItemActivity.class);
        intent.putParcelableArrayListExtra(SUBCATEGORIES_INTENT_EXTRA_KEY, subCategoriesModel);
        intent.putExtra(CATEGORY_ID_INTENT_EXTRA_KEY, category.getId());

        Locale locale = ChangeLang.getLocale(getResources());
        String loo = locale.getLanguage();
        if (loo.equalsIgnoreCase("ar")) {
            intent.putExtra(CATEGORY_NAME_INTENT_EXTRA, category.getNameAr());
        } else {
            intent.putExtra(CATEGORY_NAME_INTENT_EXTRA, category.getName());
        }

        startActivity(intent);
        Animatoo.animateSwipeLeft(getActivity());
    }
}
