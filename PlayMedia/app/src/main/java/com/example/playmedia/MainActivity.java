package com.example.playmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Button playButton;
    private SeekBar seekBar;

    //https://buildappswithpaulo.com/music/watch_me.mp3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = findViewById(R.id.playButton);
        seekBar = findViewById(R.id.seekBarId);
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("https://buildappswithpaulo.com/music/watch_me.mp3");

        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int duration = mp.getDuration(); //miliseconds
                Toast.makeText(MainActivity.this, String.valueOf((duration/1000)/60)
                        ,Toast.LENGTH_SHORT)
                        .show();
            }
        });

        MediaPlayer.OnPreparedListener preparedListener=new MediaPlayer.OnPreparedListener() {
            @Override
                public void onPrepared(final MediaPlayer mp) {
                seekBar.setMax(mediaPlayer.getDuration());

                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mp.isPlaying()){
                            //Stop and give user option to start again.
                            if(mp != null){
                                mp.pause();
                                playButton.setText("Play");
                            }
                        } else {
                            mp.start();
                            if(mp != null){
                                mp.start();
                                playButton.setText("Pause");
                            }
                        }
                    }
                });
            }
        };
        mediaPlayer.setOnPreparedListener(preparedListener);
        mediaPlayer.prepareAsync();
        //mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.apna_time);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void pauseMusic(MediaPlayer mediaPlayer){
        if(mediaPlayer != null){
            mediaPlayer.pause();
            playButton.setText("Play");
        }
    }

    public void playMusic(MediaPlayer mediaPlayer){
        if(mediaPlayer != null){
            mediaPlayer.start();
            playButton.setText("Pause");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.pause();
            mediaPlayer.release();
        }
    }
}
