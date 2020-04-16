package com.iamthaoly.appchoinhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView lvSongs;
    ArrayAdapter<String> songAdapter;
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
//        xuLyNgheNhac();
    }

//    @Override
//    protected void onPause() {
//
//    }

    private void addEvents() {
        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songName = songAdapter.getItem(position);
                xuLyNgheNhac(songName);
            }
        });
    }

    private void addControls() {
        lvSongs = findViewById(R.id.lvSongs);
        songAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1);
        lvSongs.setAdapter(songAdapter);

        try {
            AssetManager assetManager = getAssets();
            String[] arrSong = assetManager.list("music");
            songAdapter.addAll(arrSong) ;
        }
        catch (Exception e)
        {
            Log.e("Error!", e.toString());
        }
    }

    private void xuLyNgheNhac(String song) {
        try {
            AssetFileDescriptor afd = getAssets().openFd("music/" + song);
            stopPlaying();
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();

            player.prepare();
            player.start();
        }
        catch (Exception e)
        {
            Log.e("Loi", e.toString());
        }
    }
    private void stopPlaying() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    public void stopPlaying(View view) {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }
}
