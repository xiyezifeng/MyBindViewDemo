package com.example;

/**
 * Created by xigua on 2017/9/20.
 */

public interface ViewBinder<T> {
    void bind(Finder finder, T target, Object source);
}
