package com.jidouauto.market.module.lib.module.base;


//public interface BaseView<T> {
//
//    void setPresenter(T presenter);
//
//}

public interface BaseView {

    void before();

    void error(Throwable throwable);

    void tokenInvalid(Throwable throwable);
}
