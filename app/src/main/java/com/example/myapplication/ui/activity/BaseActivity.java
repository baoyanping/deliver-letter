package com.example.myapplication.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.noober.background.BackgroundLibrary;

public abstract class BaseActivity extends AppCompatActivity {
    protected abstract Object onCreateRootView();

    protected abstract void onInitView(Bundle savedInstanceState);

    protected abstract void initListener();

    protected abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackgroundLibrary.inject(this);
        initContentView();
        onInitView(savedInstanceState);
        initListener();
        initData();
    }

    private void initContentView() {
        Object crv = onCreateRootView();
        if (crv == null) {
        } else {
            if (crv instanceof Integer) {
                setContentView((int) crv);
            } else if (crv instanceof View){
                setContentView((View) crv);
            }
        }
    }
}
