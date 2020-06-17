package com.example.myapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ArticleAdapter;
import com.example.myapplication.adapter.ClickDataListener;
import com.example.myapplication.bean.ArticleBean;
import com.example.myapplication.bean.ArticleChangeEvent;
import com.example.myapplication.manager.bd.UserDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class ExpertMainActivity extends BaseActivity {

    private RecyclerView rv;
    private ArticleAdapter adapter;

    @Override
    protected Object onCreateRootView() {
        return R.layout.activity_expert_main;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);//注册
        getSupportActionBar().hide();
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getWindow().setStatusBarColor(0xffffff);
        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        View view = findViewById(R.id.aem_view);
        ConstraintLayout.LayoutParams linearParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        linearParams.height = (height);
        view.setLayoutParams(linearParams);
        rv = findViewById(R.id.aem_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArticleAdapter(new ArrayList<>(), this);
        rv.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener((ClickDataListener<ArticleBean>) (articleBean, position) -> {
            Intent intent = new Intent(this, ArticleDetailActivity.class);
            intent.putExtra("article", articleBean);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        getData();
    }

    public void getData(){
        adapter.addAllData(UserDatabase.getInstance(this).articleDao().queryNoApproval());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeUser(ArticleChangeEvent messageEvent) {
        getData();
    }

}
