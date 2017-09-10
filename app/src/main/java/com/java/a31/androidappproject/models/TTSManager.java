package com.java.a31.androidappproject.models;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.voicerss.tts.AudioCodec;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.Languages;
import com.voicerss.tts.SpeechDataEvent;
import com.voicerss.tts.SpeechDataEventListener;
import com.voicerss.tts.SpeechErrorEvent;
import com.voicerss.tts.SpeechErrorEventListener;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static com.facebook.FacebookSdk.getCacheDir;

/**
 * Created by amadeus on 9/10/17.
 */

public class TTSManager {
    private MediaPlayer mp = null;

    private String kVoiceRssServer = "http://api.voicerss.org";
    private String kVoiceRSSAppKey = "812c49b5fc504ea59f257d3f120a822f";

    private Context context;

    TTSManager(Context context) {
        this.context=context;
    }

    public void speak(String text) {

        try
        {
            final MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener()
            {
                @Override
                public void onPrepared(MediaPlayer mp)
                {
                    mp.setVolume(1, 1);
                    mp.start();
                    try {
                        Thread.sleep(100000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            final MediaPlayer.OnErrorListener onErrorListener = new MediaPlayer.OnErrorListener()
            {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra)
                {
                    return false;
                }
            };

            final MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener()
            {
                @Override
                public void onCompletion(MediaPlayer mp)
                {
                    mp.release();
                    mp = null;
                }
            };

            String url = buildSpeechUrl(text, "zh-cn");

            AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

            try
            {
                if (mp != null)
                    mp.release();

                mp = new MediaPlayer();
                mp.setDataSource(url);
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mp.setOnErrorListener(onErrorListener);
                mp.setOnCompletionListener(onCompletionListener);
                mp.setOnPreparedListener(onPreparedListener);
                mp.prepare();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private String buildSpeechUrl(String words, String language)
    {
        String url = "";

        url = kVoiceRssServer + "/?key=" + kVoiceRSSAppKey + "&t=text&hl=" + language + "&src=" + words;

        return url;
    }
}
