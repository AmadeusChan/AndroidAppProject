package com.java.a31.androidappproject.models.sharing.weibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.share.WbShareTransActivity;

import org.w3c.dom.Text;

/**
 * Created by amadeus on 9/9/17.
 */

public class Share2Weibo {
    private static WbShareHandler wbShareHandler;
    private static Context context;
    private static WeiboMultiMessage weiboMultiMessage;

    public static void init(Context context) {
        Share2Weibo.context=context;
        WbSdk.install(context, new AuthInfo(context, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE));
    }

    public static void share(final Activity activity, String url, String introduction, final String image) {
        wbShareHandler=new WbShareHandler(activity);
        wbShareHandler.registerApp();
        wbShareHandler.setProgressColor(0xff33b5e5);
        weiboMultiMessage=new WeiboMultiMessage();
        weiboMultiMessage.textObject=getTextObject(url, introduction);

        if (image!=null && !image.equals("")) {
            ImageLoader.getInstance().loadImage(image, new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    ImageObject imageObject=new ImageObject();
                    imageObject.setImageObject(loadedImage);
                    weiboMultiMessage.imageObject=imageObject;
                    send();
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    super.onLoadingFailed(imageUri, view, failReason);
                    send();
                }
            });
        } else {
            send();
        }
    }

    private static void send() {
        wbShareHandler.shareMessage(weiboMultiMessage, false);
    }

    private static TextObject getTextObject(String url, String introduction) {
        TextObject textObject=new TextObject();
        textObject.text=url+" "+introduction;
        textObject.title="news sharing";
        textObject.actionUrl=url;
        return textObject;
    }

}
