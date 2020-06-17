package com.example.myapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.bean.UserBean;
import com.example.myapplication.databinding.ActivityLoginBinding;
import com.example.myapplication.manager.bd.UserDatabase;
import com.example.myapplication.ui.MyApplication;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;


    @Override
    protected Object onCreateRootView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        return binding.getRoot();
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        getSupportActionBar().hide();// 隐藏ActionBar
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        ConstraintLayout.LayoutParams linearParams = (ConstraintLayout.LayoutParams) binding.alView.getLayoutParams();
        linearParams.height = (height);
        binding.alView.setLayoutParams(linearParams);
        UserDatabase.getInstance(LoginActivity.this).userDao().insertAll(new UserBean("老王", "123456", "aaa123456", 1, 1));
    }

    @Override
    protected void initListener() {
        binding.aplIvPwd.setOnClickListener(v -> {
            if (binding.getFlag()) {
                binding.alEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                binding.alEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            binding.alEtPassword.setSelection(binding.getPassword().length());
            binding.setFlag(!binding.getFlag());
        });
        binding.alBtnLogin.setOnClickListener(v -> {
            if (UserDatabase.getInstance(LoginActivity.this).userDao().judgeUserAccount(binding.getName()) == 0) {
                Toast.makeText(LoginActivity.this, "账号不存在", Toast.LENGTH_SHORT).show();
            } else {
                UserBean userBean = UserDatabase.getInstance(LoginActivity.this).userDao().judgeUserExistence(binding.getName(), binding.getPassword());
                if (userBean != null) {
                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                    editor.putInt("userId", userBean.getId());
                    editor.putInt("userIdentity", userBean.getIdentity());
                    editor.apply();
                    System.out.println(userBean.toString());
                    switch (userBean.getApproval()) {
                        case 0: {
                            Toast.makeText(LoginActivity.this, "管理员审批中，请等待", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case 1: {
                            switch (userBean.getIdentity()) {
                                case 1:
                                    startActivity(new Intent(LoginActivity.this, ManagerMainActivity.class));
                                    break;
                                case 2:
                                    startActivity(new Intent(LoginActivity.this, ExpertMainActivity.class));
                                    break;
                                case 3:
                                    startActivity(new Intent(LoginActivity.this, AuthorMainActivity.class));
                                    break;
                            }

                            break;
                        }
                        case 2: {
                            Toast.makeText(LoginActivity.this, "您的账号申请被拒绝", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "账号密码不匹配", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.alTvRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    @Override
    protected void initData() {
        binding.setFlag(false);
    }
}