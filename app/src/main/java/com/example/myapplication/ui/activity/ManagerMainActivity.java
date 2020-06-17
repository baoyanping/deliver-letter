package com.example.myapplication.ui.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.blankj.utilcode.util.FragmentUtils;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityManagerMainBinding;
import com.example.myapplication.ui.fragment.ManageArticleFragment;
import com.example.myapplication.ui.fragment.ManageUserFragment;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ManagerMainActivity extends BaseActivity {

    private ActivityManagerMainBinding binding;
    private List<Fragment>fragments;
    @Override
    protected Object onCreateRootView() {
        binding= DataBindingUtil.setContentView(this,R.layout.activity_manager_main);
        return binding.getRoot();
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getWindow().setStatusBarColor(0xffffff);
        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        ConstraintLayout.LayoutParams linearParams = (ConstraintLayout.LayoutParams) binding.ammView.getLayoutParams();
        linearParams.height = (height);
        binding.ammView.setLayoutParams(linearParams);
        binding.setFlag(0);
        fragments=new ArrayList<>();
        fragments.add(new ManageUserFragment());
        fragments.add(new ManageArticleFragment());
        FragmentUtils.add(getSupportFragmentManager(),fragments,R.id.amm_fl,0);
    }

    @Override
    protected void initListener() {
        binding.ammClUser.setOnClickListener(view -> {
            if (binding.getFlag()==1){
                binding.setFlag(0);
                FragmentUtils.showHide(0,fragments);
            }
        });
        binding.ammClArticle.setOnClickListener(view -> {
            if (binding.getFlag()==0){
                binding.setFlag(1);
                FragmentUtils.showHide(1,fragments);
            }
        });
    }

    @Override
    protected void initData() {

    }

}
