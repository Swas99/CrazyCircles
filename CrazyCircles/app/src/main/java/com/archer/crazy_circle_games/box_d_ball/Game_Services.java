package com.archer.crazy_circle_games.box_d_ball;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.archer.crazy_circle_games.box_d_ball.GameServices.BaseGameUtils;
import com.archer.crazy_circle_games.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;

import java.lang.ref.WeakReference;

public class Game_Services
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    MainActivity mContext;

    // Client used to interact with Google APIs
    public GoogleApiClient mGoogleApiClient;

    // Are we currently resolving a connection failure?
    public boolean mResolvingConnectionFailure = false;

    // Has the user clicked the sign-in button?
    public boolean mSignInClicked = false;

    // Automatically start the sign-in flow when the Activity starts
    public boolean mAutoStartSignInFlow = true;

    // request codes we use when invoking an external activity
    public static final int RC_RESOLVE = 5000;
    public static final int RC_UNUSED = 5001;
    public static final int RC_SIGN_IN = 9001;


    public Game_Services(WeakReference<MainActivity> m_context)
    {
        mContext=m_context.get();
        // Create the Google API Client with access to Plus and Games
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();
    }


    public void connectToServices() {
        mGoogleApiClient.connect();
    }

    public void disconnectServices() {
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    public boolean isSignedIn() {
        return (mGoogleApiClient != null && mGoogleApiClient.isConnected());
    }

    @Override
    public void onConnected(Bundle bundle) {
//         Show sign-out button on main menu
//
//         Show "you are signed in" message on win screen, with no sign in button.
//
//         Set the greeting appropriately on main menu

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
//        Log.d(TAG, "onConnectionFailed(): attempting to resolve");
        if (mResolvingConnectionFailure) {
//            Log.d(TAG, "onConnectionFailed(): already resolving");
            return;
        }
        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = BaseGameUtils.resolveConnectionFailure(mContext,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, mContext.getString(R.string.signin_other_error));
        }
    }

    public void onShowLeaderBoardRequested(String leader_board_id) {
        if (isSignedIn())
        {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
            if(prefs.getBoolean(String.valueOf(Helper.TOP_SCORE_UPDATE_REQUIRED),false))
            {
                long prev_data = Long.parseLong(Helper.readFromFile(new WeakReference<>(mContext), Helper.UPDATE_FILE));
                submitScore(leader_board_id, prev_data);
            }

            mContext.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                    leader_board_id), RC_UNUSED);
        }
        else
            BaseGameUtils.makeSimpleDialog(mContext, mContext.getString(R.string.leaderboards_not_available)).show();

    }

    public void submitScore(String leader_board_id,long score)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = prefs.edit();
        WeakReference<MainActivity> m_context = new WeakReference<>(mContext);

        if (isSignedIn())
        {
            Games.Leaderboards.submitScore(mGoogleApiClient,leader_board_id,score);
            editor.putBoolean(String.valueOf(Helper.TOP_SCORE_UPDATE_REQUIRED), false);
            Helper.writeToFile(m_context, Helper.UPDATE_FILE,"0");

            if(score>5)
            {
                Games.Achievements.unlock(mGoogleApiClient,
                        mContext.getString(R.string.achievement_pfftt_noob__bdb));
            }

            if(score>10)
            {
                Games.Achievements.unlock(mGoogleApiClient,
                        mContext.getString(R.string.achievement_at_least_you_are_trying__bdb));
            }
            if(score<=5)
            {
                Games.Achievements.unlock(mGoogleApiClient,
                        mContext.getString(R.string.achievement_you_can_do_better__bdb));
            }

            if(score>=50)
            {
                Games.Achievements.unlock(mGoogleApiClient,
                        mContext.getString(R.string.achievement_getting_used_to_things__bdb));
            }
            if(score>=120)
            {
                Games.Achievements.unlock(mGoogleApiClient,
                        mContext.getString(R.string.achievement_you_are_becoming_a_pro__bdb));
            }
            if(score>=200)
            {
                Games.Achievements.unlock(mGoogleApiClient,
                        mContext.getString(R.string.achievement_you_are_becoming_a_pro__bdb));
            }
        }
        else
        {
            editor.putBoolean(String.valueOf(Helper.TOP_SCORE_UPDATE_REQUIRED), true);
            long prev_data = Long.parseLong(Helper.readFromFile(m_context, Helper.UPDATE_FILE));
            if(prev_data<score)
                Helper.writeToFile(m_context,Helper.UPDATE_FILE,String.valueOf(score));
        }

        editor.apply();

    }

    public void onSignInButtonClicked() {
        // start the sign-in flow
        mSignInClicked = true;
        mGoogleApiClient.connect();
    }

    public void onSignOutButtonClicked() {
        mSignInClicked = false;
        Games.signOut(mGoogleApiClient);
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

}
