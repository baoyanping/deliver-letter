package com.example.myapplication.ui.activity;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.R;
import com.example.myapplication.bean.ArticleBean;
import com.example.myapplication.bean.ArticleChangeEvent;
import com.example.myapplication.bean.UserBean;
import com.example.myapplication.databinding.ActivityArticleDetailBinding;
import com.example.myapplication.manager.bd.UserDatabase;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ArticleDetailActivity extends BaseActivity {

    ActivityArticleDetailBinding binding;
    ArticleBean bean;
    int userIdentity;
    int userId;

    @Override
    protected Object onCreateRootView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article_detail);
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
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) binding.aadView.getLayoutParams();
        linearParams.height = (height);
        binding.aadView.setLayoutParams(linearParams);
        bean = (ArticleBean) getIntent().getSerializableExtra("article");
        binding.setBean(bean);
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);//创建键值读取对象
        userIdentity = pref.getInt("userIdentity", 0);
        userId = pref.getInt("userId", 0);
    }

    @Override
    protected void initListener() {
        binding.aadReturn.setOnClickListener(v -> finish());
        binding.aadIvDelete.setOnClickListener(v -> {
            UserDatabase.getInstance(ArticleDetailActivity.this).articleDao().deleteArticle(bean);
            finishDetail();
        });
        binding.audTvAgree.setOnClickListener(v -> {

            bean.setExamineTime(getTima());
            bean.setExpertId(userId);
            bean.setApproval(1);
            bean.setExpertName(UserDatabase.getInstance(this).userDao().queryName(userId));
            UserDatabase.getInstance(ArticleDetailActivity.this).articleDao().updataArticel(bean);
            finishDetail();
        });
        binding.audTvDisagree.setOnClickListener(v -> {
            if (binding.getReason() == null || binding.getReason().length() == 0) {
                Toast.makeText(this, "请输入不同意的理由", Toast.LENGTH_SHORT).show();
            } else {

                bean.setExamineTime(getTima());
                bean.setExpertId(userId);
                bean.setApproval(2);
                bean.setRemarks(binding.getReason());
                bean.setExpertName(UserDatabase.getInstance(this).userDao().queryName(userId));
                UserDatabase.getInstance(ArticleDetailActivity.this).articleDao().updataArticel(bean);
                finishDetail();
            }
        });
        binding.aadTvSure.setOnClickListener(v -> {
            ArticleBean articleBean = new ArticleBean(binding.getTitle(), binding.getContent(), userId, getTima(), 0);
            articleBean.setAuthorName(UserDatabase.getInstance(this).userDao().queryName(userId));
            UserDatabase.getInstance(ArticleDetailActivity.this).articleDao().insertArticle(articleBean);
            finishDetail();
        });
    }

    @Override
    protected void initData() {

        binding.setIdentity(userIdentity);
        if (bean != null) {
            binding.setBean(bean);
            binding.setContent(bean.getContent());
            binding.setTitle(bean.getTitle());
        } else {
            binding.setIsNew(true);
        }
        switch (userIdentity) {
            case 1:
            case 2: {
                binding.aadTvTitle.setFocusable(false);
                binding.aadTvTitle.setFocusableInTouchMode(false);
                binding.aadTvContent.setFocusable(false);
                binding.aadTvContent.setFocusableInTouchMode(false);
                break;
            }
            case 3: {
                binding.aadTvTitle.setFocusable(true);
                binding.aadTvTitle.setFocusableInTouchMode(true);
                break;
            }
        }
    }

    private String getTima() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public void finishDetail() {
        EventBus.getDefault().post(new ArticleChangeEvent());
        finish();
    }

}
