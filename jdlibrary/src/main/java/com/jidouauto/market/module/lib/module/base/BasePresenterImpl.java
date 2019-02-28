package com.jidouauto.market.module.lib.module.base;


import rx.Subscription;

public class BasePresenterImpl<E extends BaseView> implements BasePresenter {
    public E mView;
    public Subscription mSubscription;

    public BasePresenterImpl(E view) {
        mView = view;
    }

    @Override
    public void start() {
    }

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
