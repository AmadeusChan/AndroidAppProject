package com.java.a31.androidappproject.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.java.a31.androidappproject.models.Recommendation.NewsRecommendationManager;
import com.java.a31.androidappproject.models.database.FavoriteNewsList;
import com.java.a31.androidappproject.models.database.ImprovedFavoriteNewsList;
import com.java.a31.androidappproject.models.database.MyDBHelper;
import com.java.a31.androidappproject.models.sharing.wechat.Share2Wechat;
import com.java.a31.androidappproject.models.sharing.weibo.Share2Weibo;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by amadeus on 9/5/17.
 * 负责数据操作的类，使用方法为调用getInstance(Context)获得实例，然后调用类中相应的方法即可
 */

public class NewsManager {
    private static NewsManager instance=null;
    private Context context; // This reference should points to you MainActivity.getApplicationContext()
    private MediaPlayer mediaPlayer;
    private MyDBHelper myDBHelper;
    private TtsTask ttsTask=null;

    private class TtsTask extends AsyncTask<String, Void ,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            //Pattern pattern=Pattern.compile("[^\\s，。]+");
            //Pattern pattern=Pattern.compile("(.{50}.*?(\\z|\\n|。|\\r\\n)|.{1,50}\\z|.{300,})");
            Pattern pattern=Pattern.compile("\\S+");
            Matcher matcher=pattern.matcher(strings[0]);
            while (matcher.find()) {
                if (isCancelled()) break;
                String text=matcher.group();
                Log.d("tts", "len"+text.length()+": "+text);
                MediaPlayer mediaPlayer=new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                String url = "http://api.voicerss.org/?key=812c49b5fc504ea59f257d3f120a822f&hl=zh-cn&src="+text;
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
                do {
                    if (isCancelled()) break;
                } while (mediaPlayer.isPlaying());
                mediaPlayer.release();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    };

    private void InitImageLoader() {
        ImageLoaderConfiguration imageLoaderConfiguration=new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800)
                .build();
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(imageLoaderConfiguration);
    }

    private NewsManager(Context context) {
        this.context=context;
        myDBHelper=new MyDBHelper(context);
        InitImageLoader();
        Share2Weibo.init(context);
        Share2Wechat.init(context);
    }

    public boolean isConnectToInternet() {
        ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
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
     * 如果当前没有网络连接，则返回NewsManager.getCachedNewsList()
     * @param mode mode=INews.TEXT_ONLY表示文字模式，即调用INewsList中的借口返回的数据中不包含图片;mode=INews.NORMAL_MODE表示正常模式
     * @return 返回值为一个INewsList接口，表示某一个新闻列表，具体方法可见该类的注释
     */
    public INewsList getLatestNews(int mode) {
        /*
        if (!isConnectToInternet()) return getCachedNewsList();
        Log.d("internet", "online");
        return new NewsList(context, mode, NewsList.BASIC_URL_FOR_RAW_QUERY);
        */
        return new GeneralNewsList(mode, context);
    }

    /**
     * 获取已知ID的新闻的详细信息的方法
     * 如果调用该方法时没有网络连接，则返回结果取决于该新闻是否被收藏，如果被收藏则返回存在本地的详细信息，否则返回null
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
        if (!isConnectToInternet()) {
            NewsDetail newsDetail=(NewsDetail) myDBHelper.getFavoriteNewsDetails(ID);
            if (mode==INews.TEXT_ONLY_MODE && newsDetail!=null) {
                newsDetail.setImages(new ArrayList<String>());
            }
            listener.getResult(newsDetail);
            return ;
        }
        Log.d("detail","online!");
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
        Log.d("locate", "setAsNotFavorite");
        myDBHelper.deleteFavoriteNews(newsDetail.getID());
    }

    /**
     * 获得收藏的新闻列表
     * 对于返回的INewsList对象，建议使用getMore(size, listener)和reset()两个方法来获取其中的INewsIntroduction对象
     * @return 收藏的新闻列表
     */
    public INewsList getFavoriteNews() {
        return new ImprovedFavoriteNewsList(getSimpleFavoriteNews());
        //return myDBHelper.getFavoriteNewsList();
    }

    public FavoriteNewsList getSimpleFavoriteNews() {
        return (FavoriteNewsList) myDBHelper.getFavoriteNewsList();
    }

    public boolean isFavoriteNews(String ID) {
        return myDBHelper.isInFavoriteList(ID);
    }

    /**
     * 该方法将调用在线的TTS API将文字转成语音进行播放
     * @param text 要转成语音的文字
     */
    public void speakText(String text) {
        stopSpeaking();
        ttsTask= new TtsTask();
        ttsTask.execute(text);
    }

    public void stopSpeaking() {
        if (ttsTask!=null) ttsTask.cancel(true);
    }

    /*
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
     */

    /**
     * 该方法返回记录的分类列表设定
     * @return
     */
    public List<String> getCategoryList() {
        return myDBHelper.getCategoryList();
    }

    /**
     * 添加分类列表，分类名称必须是"科技"、"国内"...等12个分类之一
     * @param category
     */
    public void addCategory(String category) {
        myDBHelper.insertCategory(category);
    }

    /**
     * 删除分类列表，分类名称必须是"科技"、"国内"...等12个分类之一
     * @param category
     */
    public void deleteCategory(String category) {
        myDBHelper.deleteCategory(category);
    }

    /**
     * 该方法返回所有缓存下来的新闻列表，离线状态下该方法也可以使用，缓存下来的新闻指所有在getLatestNews中网络访问获得的新闻
     * @return
     */
    /*
    public INewsList getCachedNewsList() {
        Log.d("locate", "getCachedNewsList()");
        return myDBHelper.getCachedNewsList();
    }
    */

    public INewsList getCachedNewsList(int mode) {
        Log.d("locate", "getCachedNewsList()");
        return myDBHelper.getCachedNewsList(mode);
    }

    public INewsList searchNews(String keyWord, int mode) {
        return new NewsList(context, mode, NewsList.BASIC_URL_PREFIX_FOR_SEARCH+keyWord+"&");
    }

    /**
     * 调用该方法会在浏览器中打开keyWord相关的百科页面（互动百科）
     * @param keyWord 词条名字
     */
    public void jump2Baike(String keyWord) {
        String url="http://www.baike.com/wiki/"+keyWord;
        Uri uri=Uri.parse(url);
        context.startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    public void share2Weibo(Activity activity, String url, String introduction, String image) {
        Share2Weibo.share(activity, url, introduction, image);
    }

    public void share2Wechat(String url, String introduction, String image, int mode) {
        Share2Wechat.share2Wechat(url, introduction, image, mode);
    }

    public INewsList getRecommendedNewsList(int mode) {
        Log.d("keyword", "locate: getRecommendedNewsList");
        //return getCachedNewsList(mode);
        //return null;
        try {
            //INewsList newsList=NewsManager.getInstance().getLatestNews(mode);
            return NewsRecommendationManager.getRecommendedNewsList(mode, context);
            //return NewsManager.getInstance().getLatestNews(mode);
            //INewsList newsList=searchNews(NewsRecommendationManager.getRecommendedKeywords(mode), mode);
        } catch (NewsManagerNotInitializedException e) {
            e.printStackTrace();
            return getLatestNews(mode);
        }
    }

    // package-private
    boolean addReadNews(String ID) {
        return myDBHelper.insertReadNews(ID);
    }

    // package-private
    boolean isReadNews(String ID) {
        return myDBHelper.isReadNews(ID);
    }

    boolean add2CachedNewsList(INewsIntroduction newsIntroduction) {
        return myDBHelper.add2CachedNewsList(newsIntroduction);
    }

    public void addReadKeyword(String keyword) {
        Log.d("keyword", "addReadKeyword "+keyword);
        myDBHelper.addReadKeyword(keyword);
    }

    public List<String> getReadKeywords() {
        return myDBHelper.getReadKeywords();
    }

}
