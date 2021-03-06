package com.archer.crazy_circle_games.survival_circle;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.KeyEvent;

import com.archer.crazy_circle_games.R;
import com.archer.crazy_circle_games.survival_circle.GameServices.BaseGameUtils;

import java.lang.ref.WeakReference;

public class MainActivity extends Activity {
    public SoundManager objSoundManager;
    public Game_Services objGameServices;
    public MainViewManager objMainViewManager;


    public Vibrator objVibrator;
    public boolean vibration_on;

    public long BestScore;
    public long TotalGames;
    public long TotalScore;

    public int game_duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WeakReference<MainActivity> m_context = new WeakReference<>(this);
        objSoundManager = new SoundManager(m_context);
        objGameServices = new Game_Services(m_context);
        objMainViewManager = new MainViewManager(m_context);

        objVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        load_vibration_settings();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(objGameServices!=null)
            objGameServices.connectToServices();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(objGameServices!=null)
            objGameServices.disconnectServices();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CollectGarbage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == objGameServices.RC_SIGN_IN) {
            objGameServices.mSignInClicked = false;
            objGameServices.mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                objGameServices.mGoogleApiClient.connect();
            } else {
                BaseGameUtils.showActivityResultError(this, requestCode, resultCode, R.string.signin_other_error);
            }
        }

    }


    private void load_vibration_settings() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean defValue = objVibrator.hasVibrator();
        vibration_on = preferences.getBoolean(String.valueOf(Helper.VIBRATION_VALUE),defValue);
    }

    public void toggle_vibration()
    {
        if(!objVibrator.hasVibrator())
            return;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        vibration_on=!vibration_on;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(String.valueOf(Helper.VIBRATION_VALUE), vibration_on);
        editor.apply();

        if(vibration_on)
            findViewById(R.id.btn_vibration_settings)
                .setBackgroundResource(R.drawable.double_circle_white);
        else
            findViewById(R.id.btn_vibration_settings)
                    .setBackgroundResource(R.drawable.hollow_white_circle);
    }


    public long getAverageScore()
    {
        if(TotalGames != 0)
            return TotalScore/TotalGames;

        return 0;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if(objSoundManager !=null)
        objSoundManager.ResumeBackGroundMusic();
    }

    @Override
    public void onPause()
    {
        super.onPause();

        if(objSoundManager!=null)
        objSoundManager.StopBackGroundMusic();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            objMainViewManager.onBackPress();
        }
        else
            super.onKeyDown(keyCode, event);

        return false;
    }


    public void CollectGarbage() {
        Runnable myRunnable = new Runnable(){
            public void run(){
                System.gc();
            }
        };
        Thread thread = new Thread(myRunnable);
        thread.start();
    }



}
