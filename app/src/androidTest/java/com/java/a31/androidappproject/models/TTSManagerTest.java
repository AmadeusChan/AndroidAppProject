package com.java.a31.androidappproject.models;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by amadeus on 9/10/17.
 */
public class TTSManagerTest {
    Context context;
    TTSManager ttsManager;

    @Before
    public void setUp() throws Exception {
        context= InstrumentationRegistry.getTargetContext();
        ttsManager=new TTSManager(context);
    }

    @Test
    public void speak() throws Exception {
        ttsManager.speak("你好世界");
        Thread.sleep(10000);
    }

}