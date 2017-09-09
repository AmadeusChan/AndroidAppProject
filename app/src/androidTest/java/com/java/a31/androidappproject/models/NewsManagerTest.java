package com.java.a31.androidappproject.models;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by amadeus on 9/5/17.
 */
public class NewsManagerTest {

    @Before
    public void setUp() {
        NewsManager.getInstance(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void getInstance() throws Exception {

    }

    @Test
    public void getLatestNews() throws Exception {
        Context context= InstrumentationRegistry.getContext();
        INewsList newsList=NewsManager.getInstance(context).getLatestNews(INews.NORMAL_MODE);
        newsList.getMore(50, 1, 12, new INewsListener<List<INewsIntroduction>>() {
            @Override
            public void getResult(List<INewsIntroduction> result) {
                Log.d("getResult:", "result!");
                Log.d("getResult:", ""+result.size());
                if (result==null) {
                    System.out.println("BOMB!");
                } else
                    for (int i=0; i<result.size(); ++i) {
                        Log.d("title:", result.get(i).getTitle());
                    }
            }
        });
        Thread.sleep(1000);

    }

    @Test
    public void getNewsDetails() throws Exception {
        Log.d("go: ", "getNewsDetails()");
        Context context=InstrumentationRegistry.getContext();
        String ID="2016091304131c1376b3db72473fa06b633c0a3d1140";
        int mode=INews.NORMAL_MODE;
        NewsManager.getInstance(context).getNewsDetails(ID, mode, new INewsListener<INewsDetail>() {
            @Override
            public void getResult(INewsDetail result) {
                Log.d("go: ","result got!");
                if (result!=null) {
                    Log.d("detail persons: ", result.getPersons().toString());
                    Log.d("detail locations: ", result.getLocations().toString());
                    Log.d("detail keyWords: ", result.getKeyWords().toString());
                    Log.d("detail images: ", result.getImages().toString());
                    Log.d("detail Content: ", result.getContent());
                }
            }
        });
        Thread.sleep(2000);
    }

    @Test
    public void setAsFavorite() throws Exception {

    }

    @Test
    public void setAsNotFavorite() throws Exception {

    }

    @Test
    public void getFavoriteNews() throws Exception {

    }

    @Test
    public void speakText() throws Exception {
        Context context=InstrumentationRegistry.getContext();
        String text="你好，世界";
        NewsManager.getInstance(context).speakText(text);
        text="\"乐视921邀请函曝光 乐Pro 3悬疑即将揭晓 2016-09-12 13:15:00 环球网 分享 参与 　　今天上午，@超级手机官方微博正式发布了新品发布会邀请函，宣布将于9月21日下午14:00-16:30在北京举办以“生态之刃 无舰不摧”为主题的新品发布会。如果不出意外，传闻中的乐视全新系列乐Pro以及该系列的首款新品乐Pro3届时将正式亮相。 　　乐视用“无舰不摧”来形容即将亮相的新品，而且海报中加入了大量金属切割的效果，从中我们感受到了弄弄的“杀气”。从摧毁一切的气势来看，乐视对即将发布的新品可谓信心满满。此前网上曝光了不少新机的信息，那么这些悬念能否在9月21日发布会中最终揭晓呢？ 　　一、首发骁龙821 打造旗舰新标杆 　　在此之前，安兔兔曝光了名为乐Pro 3的安兔兔跑分，显示该机跑分超过16万，超过了市面上大部分智能手机。并且一同曝光了该机的配置，显示其搭载的是骁龙821处理器，并搭配6GB运存+64GB存储空间组合。据悉，乐Pro3是乐视即将推出的一个全新系列，定位高端市场，主要年轻时尚人群。 　　据高通介绍，骁龙821相比于骁龙820在CPU、GPU性能上都有提升。目前，包括三星Galaxy Note7在内的年度旗舰机型都在使用骁龙820。如果乐视新机搭载骁龙821，那么乐视手机性能势必再次超越其他品牌手机，最终能杀出怎么样的价格让人更加期待 　　除乐Pro3外，根据此前曝光的消息还存在一款命名乐2s Pro的机型，该机将搭载骁龙820+4G RAM的组合，其它配置与乐Pro3基本一致。从目前的情况来看，乐视在本月21日至少要发布两款新品。 　　二、相机性能再度提升 　　日前，乐视移动冯幸在微博中曝光出乐Pro 3的样张，从样张来看龙壁的纹理细节清晰可见，同时样张保证稳定的白平衡，没有出现过曝色彩漂移等问题，整体来说这样的效果已经可以跻身目前手机拍照排行前列。之前的乐2就配置了1600万有效像素，F2.0超大光圈、PDAF、滚珠式闭环马达，快速对焦等给力设置。而这次新机或将推出搭载单摄像头和双摄像头两个版本，主打拍照，想必会更加令人期待。 　　三、5000mAh大电池+7mm机身 　　续航一直是智能手机最难突破的瓶颈，更久的续航能够为用户带来安心使用体验。有消息称，乐视新机将会采用5000mAh大容量电池并且支持24W快充，机身厚度也控制于7mm之内，让大电池与机身厚度不再矛盾。 　　四、全新ID设计 　　据坊间消息，新一代超级手机将采用全新的ID设计，即全金属机身+冠状天线;同时新品还将新增磨砂材质的黑色机身。所谓冠状天线，目的在于把传统的全金属三段式横置天线条改为紧贴上下边轮廓，从而让手机拥有更好的完整性。 　　另外，乐视ID无边框的设计在新品上也将得到延续和进化。据爆料，乐Pro3将新增2.5D玻璃，拥有更极致的超窄边框，让整机的屏占比达到了相当高的水平。此外，乐视新机还将采用金属拉丝设计，将提供更好的触感，工艺方面将有大幅提升。 　　五、音质再突破 　　第二代乐视超级手机革命性的取消了3.5mm耳机接口，近日最新发布的苹果iPhone 7/7Plus也取消了耳机接口。不难看出，未来将会有更多旗舰进行跟随取消3.5mm模拟接口。第二代乐视手机均率先采用了CDLA标准，最低支持96K/24bit采样率，该音频是CD音质数据量的3.3倍，通过Type-C数字化传输音频，串扰轻松做到89dB，比传统3.5mm耳机高19dB，声场效果更开阔。相信乐视即将发布的新旗舰乐Pro 3在音质方面还会有全新的提升。 　　六、新机搭载全新生态圈 　　在不久前，B20峰会上乐视创始人、乐视控股CEO贾跃亭LeEco人工智能(LeAI)生态手机不久面市，那么这次能否就与用户见面呢？据悉，新机将打造全新的移动生态服务圈，涵盖社交、娱乐、视频内容。并在高性能终端的支持承载下，为用户提供更为便捷、人性化一站式的智能化机制服务。 　　乐视控股高级副总裁、乐视移动总裁冯幸曾表示：“乐视手机以生态之名重新定义了手机的意义，而生态手机正是未来完整生态生活圈的连接中心。当生态手机与乐生活化反，即会出现乐视手机与生活场景的联动。未来，手机将连接各种各样的生活场景，成为生态生活圈的连接中心。”乐Pro3的用户拥有iphone7用户体验不到的互联网生态系统，相信乐Pro3将会把这个杀手锏发挥到淋漓尽致。 　　9月21日，乐视即将发布新机，届时这些曝光信息终将得到揭晓。如果这些信息大部分得到确认，那么价格将成为最后悬念。按照乐视手机以往硬件免费的理念，价格方面或许再次给乐迷带来惊喜。 相关新闻 华为乐视荣耀嘲笑苹果：iPhone 7真落后？2016-09-09 08:08 乐视旗舰新乐Pro 3机曝光：软硬件大升级2016-09-07 16:02 乐视919再掀免费狂欢：买会员送硬件最高省2815元2016-09-07 10:01 乐视手机乐2四个月单品销售超600万台2016-09-06 11:33 AI化反！贾跃亭：乐视将推首部人工智能生态手机2016-09-04 11:57 责编：陈健 版权作品，未经环球网Huanqiu.com书面授权，严禁转载，违者将被追究法律责任。 获取授权 有这8个特征的女人千万别碰！ 马来正妹太火辣 一张比基尼照吸粉8000 美国萌娃搞笑睡姿走红网络 环球新闻 国际| 国内| 社会| 军事| 财经| 科技| 汽车| 旅行| 时尚| 娱乐| 体育| 图片| 海外看中国\"";
        NewsManager.getInstance(context).speakText(text);
        Thread.sleep(1000000);
    }

    @Test
    public void getCategoryList() throws Exception {
        Context context=InstrumentationRegistry.getContext();
        //NewsManager.getInstance(context);
        List<String> list=NewsManager.getInstance(context).getCategoryList();
        for (String str: list) {
            Log.d("category", str);
        }
    }

    @Test
    public void addCategory() throws Exception {

    }

    @Test
    public void deleteCategory() throws Exception {

    }

    @Test
    public  void searchNews() throws Exception {
        INewsList newsList=NewsManager.getInstance().searchNews("北京广东", INews.NORMAL_MODE);
        newsList.getMore(10, 1, new INewsListener<List<INewsIntroduction>>() {
            @Override
            public void getResult(List<INewsIntroduction> result) {
                Log.d("search", ""+result.size());
                for (INewsIntroduction i: result) {
                    Log.d("search", i.getTitle());
                    Log.d("search", i.getIntroduction());
                }
            }
        });
        Thread.sleep(1000);
    }

    @Test
    public void jump2Baike() throws Exception {
        //NewsManager.getInstance().jump2Baike("中国");
        //NewsManager.getInstance().jump2Baike("北京");
        //NewsManager.getInstance().jump2Baike("上海");
        //NewsManager.getInstance().jump2Baike("村上春树");
        NewsManager.getInstance().jump2Baike("海明威");
    }

}