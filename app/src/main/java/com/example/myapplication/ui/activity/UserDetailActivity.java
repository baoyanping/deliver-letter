package com.example.myapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.bean.UserBean;
import com.example.myapplication.bean.UserChangeEvent;
import com.example.myapplication.databinding.ActivityUserDetailBinding;
import com.example.myapplication.manager.bd.UserDatabase;

import org.greenrobot.eventbus.EventBus;

public class UserDetailActivity extends BaseActivity {

    ActivityUserDetailBinding binding;
    UserBean user;

    @Override
    protected Object onCreateRootView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail);
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
        ConstraintLayout.LayoutParams linearParams = (ConstraintLayout.LayoutParams) binding.audView.getLayoutParams();
        linearParams.height = (height);
        binding.audView.setLayoutParams(linearParams);
        user = (UserBean) getIntent().getSerializableExtra("user");
        binding.setBean(user);
    }

    @Override
    protected void initListener() {
        binding.audReturn.setOnClickListener(v -> finish());
        binding.audTvAgree.setOnClickListener(v -> {
            updataUser(1);
        });
        binding.audTvDisagree.setOnClickListener(v -> {
            updataUser(2);
        });
        binding.audIvDelete.setOnClickListener(v -> {
            if (user.getIdentity() != 1) {
                if (UserDatabase.getInstance(UserDetailActivity.this).userDao().delete(user) != 0) {
                    EventBus.getDefault().post(new UserChangeEvent());
                    finish();
                }
            } else {
                Toast.makeText(UserDetailActivity.this, "管理员不能删除哟", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initData() {

    }

    public void updataUser(int flag) {
        user.setApproval(flag);
        if (UserDatabase.getInstance(UserDetailActivity.this).userDao().updateApproval(user) != 0) {
            EventBus.getDefault().post(new UserChangeEvent());
            finish();
        }
    }
}
