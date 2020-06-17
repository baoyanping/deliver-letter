package com.example.myapplication.adapter;

public interface ClickDataListener<T> {
    void onClick(T t,int position);
}
//监听事件，只有一个点击事件更新时