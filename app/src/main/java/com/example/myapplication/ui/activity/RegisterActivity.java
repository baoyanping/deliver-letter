package com.example.myapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.bean.UserBean;
import com.example.myapplication.databinding.ActivityRegisterBinding;
import com.example.myapplication.manager.bd.UserDatabase;

public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding binding;
    private int identity=2;
    private RadioGroup rg;

    @Override
    protected Object onCreateRootView() {
        binding= DataBindingUtil.setContentView(this,R.layout.activity_register);
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
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) binding.arView.getLayoutParams();
        linearParams.height = (height);
        binding.arView.setLayoutParams(linearParams);
        rg=findViewById(R.id.ar_rg);
    }


    @Override
    protected void initListener() {
        rg.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.ar_rb_2) {
                identity=2;
            } else if (checkedId == R.id.ar_rb_3) {
                identity=3;
            }
        });
        binding.arReturn.setOnClickListener(v -> finish());
        binding.alBtnRegister.setOnClickListener(v -> {
            if (binding.getPassword().equals(binding.getSurePassword())){
                if(UserDatabase.getInstance(RegisterActivity.this).userDao().judgeUserAccount(binding.getAccount())==0){
                    UserDatabase.getInstance(RegisterActivity.this).userDao().insertAll(new UserBean(binding.getUsername(),binding.getAccount(),binding.getPassword(),identity,0));
                    Toast.makeText(RegisterActivity.this, "注册申请已提交，等待管理员审核", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(RegisterActivity.this, "账号已存在，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(RegisterActivity.this, "两次密码不一致请重新输入", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initData() {

    }

}
