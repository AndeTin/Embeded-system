package com.example.lab04;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    VideoView videoView;
    MediaPlayer player = null,mediaPlayer;
    private TextView Moutput,Routput;
    private static int MICROPHONE_PERMISSION_CODE = 200;
    MediaRecorder recorder;
    Button draw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Moutput = (TextView) findViewById(R.id.mOUT);

        videoView = findViewById(R.id.video_view);

        Routput = (TextView) findViewById(R.id.rOUT);
        if (isMicrophonePresent()){
            getMicrophonePermission();
        }

        draw = findViewById(R.id.draw);
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DrawActivity.class);
                startActivity(intent);
            }
        });

    }

    public void startRecord(View v) {
        try {
            if (recorder != null) {
                recorder.release();
            }
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 改用 MPEG_4 與 AAC 以獲得更好的相容性與音質
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            recorder.setOutputFile(getRecordingFilePath());
            recorder.prepare();
            recorder.start();
            Routput.setText("錄音中...");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "錄音失敗: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            recorder = null;
        }
    }

    public void stopRecord(View v) {
        if (recorder != null) {
            try {
                recorder.stop();
                recorder.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            recorder = null;
            Routput.setText("停止錄音...");
        }
    }

    public void playRecord(View v) {
        try {
            stopPlay(); // 確保先停止之前的播放
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(getRecordingFilePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlay();
                    Routput.setText("錄音播放結束");
                }
            });
            Routput.setText("播放錄音中...");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "播放失敗: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isMicrophonePresent(){
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return true;
        }
        else{
            return false;
        }
    }

    public void stopRecordPlayback(View v) {
        stopPlay();
        Routput.setText("錄音已經停止播放...");
    }

    private void stopPlay() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }

    private void getMicrophonePermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},MICROPHONE_PERMISSION_CODE);
        }
    }

    private String getRecordingFilePath(){
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicDirectory,"testRecordingFile" + ".mp3");
        return file.getPath();
    }

    public void playVideo(View v) {
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.videotest;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();
    }

    public void playMusic(View v) {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.musictest);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        player.start();
        Moutput.setText("音樂播放中...");
    }

    public void pauseMusic(View v) {
        if (player != null) {
            player.pause();
            Moutput.setText("音樂暫停中...");
        }
    }

    public void stopMusic(View v) {
        stopPlayer();
        Moutput.setText("音樂已經停止播放...");
    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
        stopPlay();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }
    }
}