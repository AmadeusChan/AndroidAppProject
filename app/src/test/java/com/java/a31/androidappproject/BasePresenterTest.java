package com.java.a31.androidappproject;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zwei on 2017/9/11.
 */
public class BasePresenterTest {
    @Test
    public void start() throws Exception {
        BasePresenter basePresenter = new BasePresenter() {
            @Override
            public void start() {
                System.out.println("Hello World!");
            }
        };

        basePresenter.start();
    }

}