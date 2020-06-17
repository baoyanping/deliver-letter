package com.example.myapplication.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ArticleAdapter;
import com.example.myapplication.adapter.ClickDataListener;
import com.example.myapplication.bean.ArticleBean;
import com.example.myapplication.bean.ArticleChangeEvent;
import com.example.myapplication.manager.bd.UserDatabase;
import com.example.myapplication.ui.activity.ArticleDetailActivity;
import com.example.myapplication.ui.activity.UserDetailActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class ManageArticleFragment extends Fragment {
    private RecyclerView rv;
    private ArticleAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);//注册
        View view = inflater.inflate(R.layout.fragment_manage_article, container, false);
        rv = view.findViewById(R.id.fmu_rv);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ArticleAdapter(new ArrayList<>(), getActivity());
        adapter.setOnItemClickListener((ClickDataListener<ArticleBean>) (articleBean, position) -> {
            Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
            intent.putExtra("article", articleBean);
            startActivity(intent);
        });
        rv.setAdapter(adapter);
        getData();
    }
    public void getData() {
        adapter.addAllData(UserDatabase.getInstance(getActivity()).articleDao().queryManagerAll());
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
