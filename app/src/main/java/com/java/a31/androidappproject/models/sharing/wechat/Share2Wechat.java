package com.java.a31.androidappproject.models.sharing.wechat;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.util.Util;
import com.java.a31.androidappproject.models.sharing.weibo.*;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by amadeus on 9/10/17.
 */

public class Share2Wechat {

    public static final int SHARE_TO_TIMELINE=0;
    public static final int SHARE_TO_FRIEND=1;

    private static IWXAPI iwxapi;

    public static void init(Context context) {
        iwxapi= WXAPIFactory.createWXAPI(context, Constants.APP_ID, true);
        iwxapi.registerApp(Constants.APP_ID);
    }

    private static Bitmap getWxShareBitmap(Bitmap targetBitmap) {
        float scale = Math.min((float) 150 / targetBitmap.getWidth(), (float) 150 / targetBitmap.getHeight());
        Bitmap fixedBmp = Bitmap.createScaledBitmap(targetBitmap, (int) (scale * targetBitmap.getWidth()), (int) (scale * targetBitmap.getHeight()), false);
        return fixedBmp;
    }

    private static void sendMessage(String url, String introduction, Bitmap image, int mode) {
        WXWebpageObject wxWebpageObject=new WXWebpageObject();
        wxWebpageObject.webpageUrl=url;

        WXMediaMessage wxMediaMessage=new WXMediaMessage();
        wxMediaMessage.mediaObject=wxWebpageObject;
        wxMediaMessage.description=introduction;

        if (image!=null) {
            Bitmap thumbBmp=Bitmap.createScaledBitmap(image, 150, 150, true);
            image.recycle();
            wxMediaMessage.thumbData= bmpToByteArray(thumbBmp, true);
        }

        SendMessageToWX.Req req=new SendMessageToWX.Req();
        req.transaction=buildTransaction("News");
        req.message=wxMediaMessage;
        req.scene=(mode==SHARE_TO_TIMELINE?SendMessageToWX.Req.WXSceneTimeline:SendMessageToWX.Req.WXSceneSession);

        Log.d("wechat", "go");

        iwxapi.sendReq(req);
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void share2Wechat(final String url, final String introduction, String image, final int mode) {
        Log.d("wechat", "share to wechat");
        if (image!=null && !image.equals("")) {
            ImageLoader.getInstance().loadImage(image, new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    Log.d("wechat", "load finished!");
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    sendMessage(url, introduction, loadedImage, mode);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    super.onLoadingFailed(imageUri, view, failReason);
                    sendMessage(url, introduction, null, mode);
                }
            });
        } else {
            sendMessage(url, introduction, null, mode);
        }
    }

}
