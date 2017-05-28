package com.libtest.ead.libtesto;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;

import java.io.IOException;

import jp.co.recruit_lifestyle.android.widget.PlayPauseButton;


public class MainActivity extends AppCompatActivity {
    String title = "Mary did you know";
    ImageView next, previous;
    MediaPlayer mediaPlayer;
    TextView songTitle;
    PlayPauseButton playPauseButton;
    Toolbar toolbar;
    ImageView album_art;
    RelativeLayout parentRelativeLayout;
    int count = 0;
    String[] URLs, titles, media_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //view
        parentRelativeLayout = (RelativeLayout) findViewById(R.id.audio_player_parent_relative_layout);
        songTitle = (TextView) findViewById(R.id.song_name);
        album_art = (ImageView) findViewById(R.id.album_art_image);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        playPauseButton = (PlayPauseButton) findViewById(R.id.main_play_pause_button);
        next = (ImageView) findViewById(R.id.next);
        previous = (ImageView) findViewById(R.id.prev);

        //data
        initData();

        playPauseButton.setOnControlStatusChangeListener(new PlayPauseButton.OnControlStatusChangeListener() {
            @Override public void onStatusChange(View view, boolean state) {
                if(state) {
                    playAudio(media_url[count]);
                } else {
                    pauseAudio();
                }
            }
        });

        callGlide(this, URLs[count],titles[count]);
    }

    private void initData() {
        URLs = new String[2];
        URLs[0] = "https://firebasestorage.googleapis.com/v0/b/odm-android-app-eb43e.appspot.com/o/media%2Faudio%2Fmusic.jpg?alt=media&token=f88d18cf-3932-4b53-9abf-b1975280176b";
        URLs[1] = "https://firebasestorage.googleapis.com/v0/b/odm-android-app-eb43e.appspot.com/o/media%2Faudio%2Fmusic-4.jpg?alt=media&token=a70c6d8d-637a-4374-9401-a93519e17c41";

        titles = new String[2];
        titles[0] = "Mary did you know";
        titles[1] = "You raise me up";

        media_url = new String[2];
        media_url[0] = "https://firebasestorage.googleapis.com/v0/b/odm-android-app-eb43e.appspot.com/o/media%2Faudio%2FMary%2C%20Did%20You%20Know%20with%20Lyrics.mp3?alt=media&token=803a8445-ea88-4afe-b7d7-910f1d8a2568";
        media_url[1] = "https://firebasestorage.googleapis.com/v0/b/odm-android-app-eb43e.appspot.com/o/media%2Faudio%2FYou%20Raise%20Me%20Up%20-%20Selah%20(Best%20Inspiring%20Christian%20Song).mp3?alt=media&token=feb237cc-a3f9-4e07-aff9-72533939dede";

    }

    private void callGlide(Context context, String url,String title) {
        //load album art image
        //Glide.with(c).load(url).into(album_art);

        songTitle.setText(title);
        //change bg color
        Glide.with(context).load(url)
                .listener(GlidePalette.with(url)
                        .use(GlidePalette.Profile.VIBRANT)
                        .intoBackground(parentRelativeLayout, GlidePalette.Swatch.RGB)
                        .intoBackground(toolbar, GlidePalette.Swatch.RGB)
                        .intoTextColor(songTitle, GlidePalette.Swatch.BODY_TEXT_COLOR)
                        .crossfade(true)
                )
                .into(album_art);
    }

    private void playAudio(String media_url) {

        try {
            mediaPlayer.setDataSource(media_url);
            mediaPlayer.prepare();
        }catch(IllegalArgumentException e) {

        }catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    private void pauseAudio() {

        mediaPlayer.pause();
    }

    public void previousSong(View view) {
        mediaPlayer.stop();

        count--;
        callGlide(this, URLs[count],titles[count]);
    }

    public void nextSong(View view) {
        mediaPlayer.stop();

        count++;
        callGlide(this, URLs[count],titles[count]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
