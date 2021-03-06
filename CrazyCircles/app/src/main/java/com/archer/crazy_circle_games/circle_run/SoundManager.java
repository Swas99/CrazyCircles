package com.archer.crazy_circle_games.circle_run;

import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.preference.PreferenceManager;

import com.archer.crazy_circle_games.R;

import java.lang.ref.WeakReference;

/**
 * Created by Swastik on 22-12-2015.
 */
public class SoundManager {

    MainActivity mContext;
    SoundPool soundPool;
    MediaPlayer player;
    public int POP;
    public int YAY;
    public int CLICK;
    public int CLICK_2;
    public int PLING;

    boolean muteGameSounds;
    boolean muteBackgroundMusic;
    float MAX_BACKGROUND_MUSIC_VOLUME = 0.5f;
    float MAX_GAME_SOUND_VOLUME = 0.99f;


    public SoundManager(WeakReference<MainActivity> m_context)
    {
        mContext=m_context.get();
        InitializeSoundPool();
    }
    public  void Play(int id)
    {
        float vol_adjustment = 0;
        if(id == YAY)
            vol_adjustment = -.4f;

        if(!muteGameSounds)
            soundPool.play(id,
                    MAX_GAME_SOUND_VOLUME+vol_adjustment,
                    MAX_GAME_SOUND_VOLUME+vol_adjustment,
                    1,0,1);
    }

    public void InitializeSoundPool()
    {
        loadDefaultSettings();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
        {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
        }
        else {
            soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        }
        YAY = soundPool.load(mContext, R.raw.yaaayyy, 1);
        CLICK = soundPool.load(mContext, R.raw.click, 1);
        CLICK_2 = soundPool.load(mContext, R.raw.click_2, 1);
        POP = soundPool.load(mContext, R.raw.pop, 1);
        PLING = soundPool.load(mContext, R.raw.pling, 1);
//
    }

    private void loadDefaultSettings() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        muteBackgroundMusic = preferences.getBoolean(String.valueOf(Helper.BACKGROUND_MUSIC), false);
        muteGameSounds = preferences.getBoolean(String.valueOf(Helper.GAME_SOUND), false);
    }

    private void StoreData(String tag,boolean value)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(tag,value);
        editor.apply();
    }

    public void toggleBackgroundMusic()
    {
        muteBackgroundMusic = !muteBackgroundMusic;
        StoreData(String.valueOf(Helper.BACKGROUND_MUSIC), muteBackgroundMusic);
        if(muteBackgroundMusic)
            StopBackGroundMusic();
        else
            player.setVolume(MAX_BACKGROUND_MUSIC_VOLUME, MAX_BACKGROUND_MUSIC_VOLUME);
    }
    public void setMusicButtonBackground(int[] res_id)
    {
        if(mContext.objMainViewManager!=null &&
                mContext.objMainViewManager.CurrentScreen == R.id.settings_screen)
        {
            if (muteBackgroundMusic)
                mContext.findViewById(R.id.btn_music_settings)
                        .setBackgroundResource(R.drawable.hollow_white_circle);
            else
                mContext.findViewById(R.id.btn_music_settings)
                        .setBackgroundResource(R.drawable.double_circle_white);
        }
        else
        {
            if(muteBackgroundMusic)
                mContext.findViewById(R.id.img_music)
                        .setBackgroundResource(res_id[0]);
            else
                mContext.findViewById(R.id.img_music)
                        .setBackgroundResource(res_id[1]);
        }

    }

    public void toggleGameSounds()
    {
        muteGameSounds=!muteGameSounds;
        StoreData(String.valueOf(Helper.GAME_SOUND), muteGameSounds);
    }

    public void setSoundButtonBackground(int[] res_id)
    {

        if(mContext.objMainViewManager!=null &&
                mContext.objMainViewManager.CurrentScreen == R.id.settings_screen)
        {
            if (muteGameSounds)
                mContext.findViewById(R.id.btn_sound_settings).setBackgroundResource(R.drawable.hollow_white_circle);
            else
                mContext.findViewById(R.id.btn_sound_settings).setBackgroundResource(R.drawable.double_circle_white);
        }
        else
        {
            if (muteGameSounds)
                mContext.findViewById(R.id.img_sound)
                        .setBackgroundResource(res_id[0]);
            else
                mContext.findViewById(R.id.img_sound)
                        .setBackgroundResource(res_id[1]);
        }
    }
    public void ResumeBackGroundMusic()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(player==null)
                {
                    player = MediaPlayer.create(mContext, R.raw.background);
                    player.setLooping(true); // Set looping
                    player.start();
                }
                if (muteBackgroundMusic)
                    player.setVolume(0,0);
                else
                    player.setVolume(MAX_BACKGROUND_MUSIC_VOLUME, MAX_BACKGROUND_MUSIC_VOLUME);
            }
        }).start();
    }

    public void StopBackGroundMusic()
    {
        if(player!=null)
            player.setVolume(0, 0);
    }

}
