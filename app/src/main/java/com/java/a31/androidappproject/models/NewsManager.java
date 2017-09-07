package com.java.a31.androidappproject.models;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.java.a31.androidappproject.models.database.MyDBHelper;

import java.io.IOException;
import java.util.List;

/**
 * Created by amadeus on 9/5/17.
 * 负责数据操作的类，使用方法为调用getInstance(Context)获得实例，然后调用类中相应的方法即可
 */

public class NewsManager {
    private static NewsManager instance=null;
    private Context context; // This reference should points to you MainActivity.getApplicationContext()
    private MediaPlayer mediaPlayer;
    private MyDBHelper myDBHelper;

    private NewsManager(Context context) {
        this.context=context;
        myDBHelper=new MyDBHelper(context);
    }

    /**
     * 该方法的功能是返回一个NewsManager类的实例
     * ATTENTION! In order to avoid possible memory leaking, the argument context here should be Context.getApplicationContext()
     */
    public static synchronized NewsManager getInstance(Context context) {
        if (instance==null) {
            return instance=new NewsManager(context);
        } else {
            return instance;
        }
    }

    /**
     * 该方法的功能是返回一个NewsManager实例
     * 注意：需要在调用过NewsManager.getInstance(Context)之后(为了正确设置Context)才能调用该方法，否则会返回null
     * @return
     */
    public static synchronized NewsManager getInstance() throws NewsManagerNotInitializedException {
        if (instance==null) throw new NewsManagerNotInitializedException();
        return instance;
    }

    /**
     * 表示获取一个新闻列表的方法
     * @param mode mode=INews.TEXT_ONLY表示文字模式，即调用INewsList中的借口返回的数据中不包含图片;mode=INews.NORMAL_MODE表示正常模式
     * @return 返回值为一个INewsList接口，表示某一个新闻列表，具体方法可见该类的注释
     */
    public INewsList getLatestNews(int mode) {
        return new NewsList(context, mode);
    }

    /**
     * 获取已知ID的新闻的详细信息的方法
     * 注意：如果出现所给ID不正确等错误则将null传给listener.getResult()
     * 使用方法举例说明如下:
     * NewsManager.getInstance(context).getNewsDetails(ID, mode, new INewsListener<INewsDetail>() {
     *       @Override
     *       public void getResult(INewsDetail result) {
     *          // Instructions to update your UI
     *       }
     *   });
     *
     * @param ID 要查询详细信息的新闻ID
     * @param mode mode=INews.TEXT_ONLY表示文字模式；mode=INews.NORMAL_MODE表示普通模式（带图片）
     * @param listener 在获取新闻详细信息的操作完成之后，将数据以INewsDetail接口的信息传递给listener.getResult方法执行
     */
    public void getNewsDetails(String ID, int mode, INewsListener<INewsDetail> listener) {
        myDBHelper.insertReadNews(ID);
        NewsDetailFetcherFromInternet.fetchDetail(ID, context, mode, listener);
    }

    /**
     * 收藏某条新闻
     * @param newsDetail 要收藏的新闻
     */
    public void setAsFavorite(INewsDetail newsDetail) {
        myDBHelper.insertFavoriteNews(newsDetail);
    }

    /**
     * 取消某条新闻的收藏
     * @param newsDetail 要被取消收藏的新闻
     */
    public void setAsNotFavorite(INewsDetail newsDetail) {
        myDBHelper.deleteFavoriteNews(newsDetail.getID());
    }

    /**
     * 获得收藏的新闻列表
     * @return 收藏的新闻列表
     */
    public INewsList getFavoriteNews() {
        return myDBHelper.getFavoriteNewsList();
    }

    public boolean isFavoriteNews(String ID) {
        return myDBHelper.isInFavoriteList(ID);
    }

    /**
     * 该方法将调用在线的TTS API将文字转成语音进行播放
     * @param text 要转成语音的文字
     */
    public void speakText(String text) {
        String url = "http://api.voicerss.org/?key=812c49b5fc504ea59f257d3f120a822f&hl=zh-cn&src="+text;
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    public List<String> getCategoryList() {
        return myDBHelper.getCategoryList();
    }

    public void addCategory(String category) {
        myDBHelper.insertCategory(category);
    }

    public void deleteCategory(String category) {
        myDBHelper.deleteCategory(category);
    }

    boolean addReadNews(String ID) {
        return myDBHelper.insertReadNews(ID);
    }

    boolean isReadNews(String ID) {
        return myDBHelper.isReadNews(ID);
    }

}
