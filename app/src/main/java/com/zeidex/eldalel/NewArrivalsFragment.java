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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeidex.eldalel.adapters.NewArrivalsCategoriesAdapter;
import com.zeidex.eldalel.models.Subcategory;
import com.zeidex.eldalel.models.Subsubcategory;
import com.zeidex.eldalel.response.GetOffersCategories;
import com.zeidex.eldalel.services.NewArrivalsAPI;
import com.zeidex.eldalel.utils.APIClient;
import com.zeidex.eldalel.utils.Animatoo;
import com.zeidex.eldalel.utils.ChangeLang;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zeidex.eldalel.CategoriesFragment.NO_PRODUCTS_STATUS;
import static com.zeidex.eldalel.OffersFragment.CATEGORY_ID_INTENT_EXTRA_KEY;
import static com.zeidex.eldalel.utils.Constants.SERVER_API_TEST;

public class NewArrivalsFragment extends androidx.fragment.app.Fragment implements NewArrivalsCategoriesAdapter.CategoryOperation {
    public static final String SUBCATEGORIES_INTENT_EXTRA_KEY = "subcategories";
    public static final String CATEGORY_NAME_INTENT_EXTRA = "category_name";
    public static final String NEW_ARRIVAL = "new_arrival";

    @BindView(R.id.new_arrival_recycler)
    RecyclerView new_arrival_recycler;
    NewArrivalsCategoriesAdapter newArrivalsCategoriesAdapter;

    Dialog reloadDialog;
    private List<GetOffersCategories.Category> categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_arrival, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        findViews();
        initializeRecycler();
    }

    private void initializeRecycler() {
        new_arrival_recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        new_arrival_recycler.setItemAnimator(new DefaultItemAnimator());
    }

    private void findViews() {
        showDialog();
        onLoadPage();
    }

    private void onLoadPage() {
        reloadDialog.show();
        NewArrivalsAPI offersCategoriesAPI = APIClient.getClient(SERVER_API_TEST).create(NewArrivalsAPI.class);
        offersCategoriesAPI.getNewArrivalsCategories(NO_PRODUCTS_STATUS, NEW_ARRIVAL).enqueue(new Callback<GetOffersCategories>() {
            @Override
            public void onResponse(Call<GetOffersCategories> call, Response<GetOffersCategories> response) {
                int code = response.body().getCode();
                if (code == 200) {
                    categories = response.body().getData().getCategories();
                    newArrivalsCategoriesAdapter = new NewArrivalsCategoriesAdapter(getContext(), categories);
                    newArrivalsCategoriesAdapter.setCategoryOperation(NewArrivalsFragment.this);
                    new_arrival_recycler.setAdapter(newArrivalsCategoriesAdapter);
                }
                reloadDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetOffersCategories> call, Throwable t) {
                Toasty.error(getContext(), getString(R.string.confirm_internet), Toast.LENGTH_LONG).show();
                reloadDialog.dismiss();
            }
        });
    }

    private void showDialog() {
        reloadDialog = new Dialog(getContext());
        reloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reloadDialog.setContentView(R.layout.reload_layout);
        reloadDialog.setCancelable(false);
        reloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    ArrayList<Subsubcategory> subsubcategories;
    @Override
    public void onClickCategory(int position) {
        subsubcategories = new ArrayList<>();
        Intent intent = new Intent(getContext(), NewArrivalSubcategoriesActivity.class);

        GetOffersCategories.Category category = categories.get(position);
        List<GetOffersCategories.Subcategory> subCategories = category.getSubcategories();

        Locale locale = ChangeLang.getLocale(getContext().getResources());
        String loo = locale.getLanguage();
        if (loo.equalsIgnoreCase("ar")) {
            intent.putExtra(CATEGORY_NAME_INTENT_EXTRA, category.getNameAr());
        } else {
            intent.putExtra(CATEGORY_NAME_INTENT_EXTRA, category.getName());
        }

        if (subCategories != null && subCategories.size() > 0) {
            ArrayList<Subcategory> subCategoriesModel = new ArrayList<>();
            for (int i = 0; i < subCategories.size(); i++) {
                for (GetOffersCategories.Subsubcategory subsubcategory : subCategories.get(i).getSubsubcategories()){
                    subsubcategories.add(new Subsubcategory(subsubcategory.getId() , subsubcategory.getName() , subsubcategory.getNameAr()));
                }
                subCategoriesModel.add(new Subcategory(subCategories.get(i).getId(), subCategories.get(i).getNameAr(),
                        subCategories.get(i).getName(), subCategories.get(i).getPhoto(),subsubcategories));
            }
            intent.putParcelableArrayListExtra(SUBCATEGORIES_INTENT_EXTRA_KEY, subCategoriesModel);
        }
        intent.putExtra(CATEGORY_ID_INTENT_EXTRA_KEY, category.getId());
        startActivity(intent);
        Animatoo.animateSwipeLeft(getContext());
    }
}