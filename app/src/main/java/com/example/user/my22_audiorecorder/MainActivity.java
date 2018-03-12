package com.example.user.my22_audiorecorder;

import android.media.MediaActionSound;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static String RECORD_FILE;

    MediaRecorder recorder;
    MediaPlayer player;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard,"record1.mp4");
        RECORD_FILE = file.getAbsolutePath();

        Button btnRecord = findViewById(R.id.bt1);
        Button btnRecordStop = findViewById(R.id.bt2);
        Button btnPlay = findViewById(R.id.bt3);
        Button btnPlayStop = findViewById(R.id.bt4);


        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(recorder != null){
                    recorder.stop();
                    recorder.release();
                    recorder = null;

                }
                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                recorder.setOutputFile(RECORD_FILE);

                try {
                    Toast.makeText(MainActivity.this, "녹음시작함", Toast.LENGTH_SHORT).show();
                    recorder.prepare();
                    recorder.start();
                }catch (Exception e){
                    Log.d("recorder Player","Exception = "+e.getMessage());
                }
            }
        });

        btnRecordStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(recorder == null){
                    return;
                                    }
                recorder.stop();
                recorder.release();
                recorder=null;
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(player != null){
                    player.stop();
                    player.release();
                    player = null;
                }
                Toast.makeText(MainActivity.this, "녹음된 파일을 재생", Toast.LENGTH_SHORT).show();

                player = new MediaPlayer();

                try {
                    player.setDataSource(RECORD_FILE);
                    player.prepare();
                    player.start();

                } catch (Exception e) {
                    Log.d("player Player","Exception = "+e.getMessage());
                }

            }
        });

        btnPlayStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(player != null ){
                    Toast.makeText(MainActivity.this, "재생중지", Toast.LENGTH_SHORT).show();
                    player.stop();
                    player.release();
                    player = null;
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (recorder != null){
            recorder.release();
            recorder = null;
        }

        if (player != null){
            player.release();
            player = null;
        }
    }
}
