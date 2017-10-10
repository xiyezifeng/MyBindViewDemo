package com.example;

/**
 * Created by xigua on 2017/9/26.
 */

public interface Binder<T> {
    void inject(T host);
    void unBind(T host);
}
