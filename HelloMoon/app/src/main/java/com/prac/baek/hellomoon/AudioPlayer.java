package com.prac.baek.hellomoon;

import android.media.MediaPlayer;
import android.content.Context;
import android.util.Log;

/**
 * Created by BAEK on 7/15/2015.
 */
public class AudioPlayer {
    private MediaPlayer mPlayer;
    private boolean isPause = false;
    private boolean isFirst = true;


    public void stop() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
            isFirst = true;
        }
    }

    public void play(Context c) {
        if(isFirst){
            stop();

            mPlayer = MediaPlayer.create(c, R.raw.one_small_step);
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    stop();
                }
            });
            mPlayer.start();
            isFirst = false;
            Log.d("ISFIRST", "is false");
            return;
        }
        if(!isFirst){
            if(isPause){
                mPlayer.start();
                isPause = false;
                return;
            }
            if(!isPause){
                mPlayer.pause();
                isPause = true;
                return;
            }
        }
    }
}