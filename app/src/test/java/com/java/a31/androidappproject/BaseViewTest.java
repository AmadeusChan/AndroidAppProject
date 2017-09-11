package com.java.a31.androidappproject;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zwei on 2017/9/11.
 */
public class BaseViewTest {
    @Test
    public void setPresenter() throws Exception {
        BaseView<BasePresenter> baseView = new BaseView<BasePresenter>() {
            private BasePresenter mPresenter;

            @Override
            public void setPresenter(BasePresenter presenter) {
                mPresenter = presenter;
            }
        };

        BasePresenter basePresenter = new BasePresenter() {
            @Override
            public void start() {
                System.out.println("Hello World!");
            }
        };

        baseView.setPresenter(basePresenter);
    }

}