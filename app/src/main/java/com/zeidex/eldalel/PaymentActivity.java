package com.zeidex.eldalel;

import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.shuhart.stepview.StepView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentActivity extends BaseActivity {
    @BindView(R.id.step_view)
    StepView step_view;

    @BindView(R.id.activity_payment_steps_linear)
    LinearLayoutCompat activity_payment_steps_linear;

    @BindView(R.id.activity_payment_header_item)
    LinearLayoutCompat activity_payment_header_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ButterKnife.bind(this);

        Fragment fragment = new ShoopingListAddressesFragment();
        Bundle args = new Bundle();
        args.putString("from", "payment");
        fragment.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
        ft.replace(R.id.payment_constrant, fragment, fragment.getTag());
        ft.commit();
        step_view.getState()
                // You should specify only stepsNumber or steps array of strings.
                // In case you specify both steps array is chosen.
                .steps(new ArrayList<String>() {{
                    add("العناوين");
                    add("الدفع");
                    add("الطلب");
                }})
                // You should specify only steps number or steps array of strings.
                // In case you specify both steps array is chosen.
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .typeface(ResourcesCompat.getFont(this, R.font.cairo_bold))
                // other state methods are equal to the corresponding xml attributes
                .commit();
//        step_view.done(true);

    }

    @OnClick(R.id.item_activity_payment_back)
    public void onBack(){
        onBackPressed();
    }

    public void goToPayidFragment(){
        step_view.go(1 , true);
        Fragment fragment = new PayidFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
        ft.replace(R.id.payment_constrant, fragment, fragment.getTag());
        ft.commit();
    }
    public void goToOrderFragment(){
//        step_view.done(true);
        step_view.go(2 , true);
//        step_view.go(3 , true);
        step_view.done(true);
        Fragment fragment = new CongrateFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
        ft.replace(R.id.payment_constrant, fragment, fragment.getTag());
        ft.commit();
    }
}
