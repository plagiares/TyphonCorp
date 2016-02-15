package csf.ca.typhonplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import csf.ca.utilities.Utilities;

/**
 * Created by Alexis on 2016-01-26.
 */
public class AndroidMusicPlayerActivity extends Activity
implements OnCompletionListener , SeekBar.OnSeekBarChangeListener
{
    private static final String[] STAR = {"*"};
    private static final String AUDIO_FILE_CRITERIA_SELECTION = " != 0";

     private static final int DEFAULT_FORWARD_TIME = 5000;
     private static final int DEFAULT_BACKWARD_TIME = 5000;
     private static final int INITIAL_SONG_TIME = 0;

     private static final int RETURN_CODE_PARAM = 100;

     private static final int DEFAULT_SONG_INDEX = 0;
     private static final int INDEX_DIFFERENCE = 1;

     private static final int DEFAULT_PERCENTAGE_SONG_COMPLETION = 0;
     private static final int FINAL_PERCENTAGE_SONG_COMPLETION = 100;

     private static final int THREAD_POST_DELAYED_TIME = 100;

     private static String SONG_PATH_KEY = "songPath";
     private static String SONG_TITLE_KEY = "songTitle";

     private ImageButton btnPlay;
     private ImageButton btnNext;
     private ImageButton btnForward;
     private ImageButton btnBackward;
     private ImageButton btnPrevious;
     private ImageButton btnPlayList;
     private ImageButton btnRepeat;
     private ImageButton btnShuffle;

     private ImageView albumArtThumbnail;
     private SeekBar songSeekBar;
     private SeekBar volumeSeekBar;

     private TextView lblSongTitle;
     private TextView lblSongProgression;
     private TextView lblSongTotalDuration;

     private MediaPlayer mediaPlayer;
     private AudioManager audioManager;

     private Handler handler = new Handler();

     private Utilities utilities;

     private int seekForwardTime = DEFAULT_FORWARD_TIME;
     private int seekBackwardTime = DEFAULT_BACKWARD_TIME;
     private int currentSongIndex = DEFAULT_SONG_INDEX;

     private boolean onShuffleMode = false;
     private boolean onRepeatMode = false;
     
    private ArrayList<Song> songList = new ArrayList<Song>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        initSongList();
        initView();
        initControlListener();
    }

    public void initView()
    {
          btnPlay = (ImageButton) findViewById(R.id.btnPlay);
          btnNext = (ImageButton) findViewById(R.id.btnNext);
          btnBackward = (ImageButton) findViewById(R.id.btnBackward);
          btnForward = (ImageButton) findViewById(R.id.btnForward);
          btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
          btnPlayList = (ImageButton) findViewById(R.id.btnPlaylist);
          btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
          btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
          
        albumArtThumbnail = (ImageView) findViewById(R.id.albumArt);
           
            songSeekBar = (SeekBar) findViewById(R.id.songProgressBar);
          volumeSeekBar = (SeekBar) findViewById(R.id.volumeSeekBar);

          lblSongTitle = (TextView) findViewById(R.id.songTitle);
          lblSongProgression = (TextView) findViewById(R.id.songCurrentDurationLabel);
          lblSongTotalDuration = (TextView) findViewById(R.id.songTotalDurationLabel);

          mediaPlayer = new MediaPlayer();
          audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

          utilities = new Utilities();

          songSeekBar.setOnSeekBarChangeListener(this);

          mediaPlayer.setOnCompletionListener(this);
    }

    public void initSongList()
    {
        Cursor c;
        Uri externalContentPath = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selectMusicCriteria = MediaStore.Audio.Media.IS_MUSIC + AUDIO_FILE_CRITERIA_SELECTION;

        c = getContentResolver().query(externalContentPath, STAR, selectMusicCriteria, null, null);

        if(c != null)
        {
            if(c.moveToFirst())
            {
                do
                {
                    String type = c.getString(c.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE));

                    if (type.equals("audio/mpeg"))
                    {
                        String songName = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                        String songArtist = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                        String songAlbum = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                        String songPath = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA));
                        String albumArtPath= "";
                        try {
                            albumArtPath = c.getString(c.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                        }
                        catch (Exception e) {

                        }

                        Song newSong = new Song(songName, songPath);

                        newSong.album = songAlbum;
                        newSong.artist = songArtist;
                        newSong.albumArtPath = albumArtPath;
                        songList.add(newSong);
                    }

                }
                while(c.moveToNext());
            }
        }
    }
    
    public void initControlListener()
    {
        btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // check for already playing
                if(mediaPlayer.isPlaying()){
                    if(mediaPlayer!=null){
                        mediaPlayer.pause();
                        // Changing button image to play button
                        btnPlay.setImageResource(R.drawable.btn_play);
                    }
                }else{
                    // Resume song
                    if(mediaPlayer!=null){
                        mediaPlayer.start();
                        // Changing button image to pause button
                        btnPlay.setImageResource(R.drawable.btn_pause);
                    }
                }

            }
        });


          btnPlayList.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View arg0) {

                  Intent intent = new Intent(getApplicationContext(), LibraryDisplay.class);
                  startActivityForResult(intent, RETURN_CODE_PARAM);
              }
          });

          btnNext.setOnClickListener(new View.OnClickListener()
          {
              @Override
              public void onClick(View arg0)
              {
                  if(currentSongIndex < (songList.size() - INDEX_DIFFERENCE))
                  {
                      currentSongIndex = currentSongIndex + INDEX_DIFFERENCE;
                  }
                  else
                  {
                      currentSongIndex = DEFAULT_SONG_INDEX;
                  }

                  playSong(currentSongIndex);
              }
          });

          btnPrevious.setOnClickListener(new View.OnClickListener()
          {
              @Override
              public void onClick(View arg0)
              {
                  if(currentSongIndex != DEFAULT_SONG_INDEX)
                  {
                      currentSongIndex = currentSongIndex - INDEX_DIFFERENCE;
                  }
                  else
                  {
                      currentSongIndex = DEFAULT_SONG_INDEX;
                  }

                  playSong(currentSongIndex);
              }
          });

         btnForward.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View arg0)
             {
                 int currentProgression = mediaPlayer.getCurrentPosition();

                 if(currentProgression + seekForwardTime <= mediaPlayer.getDuration())
                 {
                     mediaPlayer.seekTo(currentProgression + seekForwardTime);
                 }
                 else
                 {
                     mediaPlayer.seekTo(mediaPlayer.getDuration());
                 }
             }
         });

         btnBackward.setOnClickListener(new View.OnClickListener()
         {
            @Override
            public void onClick(View arg0)
            {
                int currentProgression = mediaPlayer.getCurrentPosition();

                if(currentProgression - seekBackwardTime >= INITIAL_SONG_TIME)
                {
                    mediaPlayer.seekTo(currentProgression - seekBackwardTime);
                }
                else
                {
                    mediaPlayer.seekTo(INITIAL_SONG_TIME);
                }
            }
         });

        btnRepeat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                if(onRepeatMode)
                {
                    onRepeatMode = false;

                    btnRepeat.setImageResource(R.drawable.btn_repeat);
                }
                else
                {
                    onRepeatMode = true;
                    onShuffleMode = false;

                    btnRepeat.setImageResource(R.drawable.repeat_focused2);
                    btnShuffle.setImageResource(R.drawable.shuffle_focused2);
                }
            }

        });

        btnShuffle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(onShuffleMode)
                {
                    onShuffleMode = false;

                    btnShuffle.setImageResource(R.drawable.btn_shuffle);
                }
                else
                {
                    onShuffleMode = true;
                    onRepeatMode = false;

                    btnShuffle.setImageResource(R.drawable.shuffle_focused2);
                    btnRepeat.setImageResource(R.drawable.btn_repeat);
                }

            }
        });

        volumeSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volumeSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                   audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                //NOT_REQUIRED
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                //NOT_REQUIRED
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 100){
            currentSongIndex = data.getExtras().getInt("songIndex");
            // play selected song
            playSong(currentSongIndex);
        }
    }

    public void playSong(int songIndex)
    {
        try
        {
            mediaPlayer.reset();

            mediaPlayer.setDataSource(songList.get(songIndex).path);

            mediaPlayer.prepare();
            mediaPlayer.start();

            String songTitle = songList.get(songIndex).name;

            lblSongTitle.setText(songTitle);

            //Drawable img = Drawable.createFromPath(songList.get(songIndex).albumArtPath);

            btnPlay.setImageResource(R.drawable.btn_pause);

            /*
            Picasso.with(this)
                    .load(songList.get(songIndex).albumArtPath)
                    .error(R.drawable.android3)      // optional
                    //.resize(250, 200)                        // optional
                    .into(albumArtThumbnail);
*/

            songSeekBar.setProgress(DEFAULT_PERCENTAGE_SONG_COMPLETION);
            songSeekBar.setMax(FINAL_PERCENTAGE_SONG_COMPLETION);

            updateProgressBar();

        }
        catch (IOException e)
        {
           e.printStackTrace();
        }


    }

    private void updateProgressBar()
    {
        handler.postDelayed(updateTimeTask, THREAD_POST_DELAYED_TIME);
    }

    private Runnable updateTimeTask = new Runnable() {
        @Override
        public void run() {
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();

            lblSongTotalDuration.setText(utilities.timerFormat(totalDuration - currentDuration));
            lblSongProgression.setText(utilities.timerFormat(currentDuration));

            int progressPercentage = utilities.getCurrentProgressPercentage(currentDuration, totalDuration);

            songSeekBar.setProgress(progressPercentage);

            handler.postDelayed(this, THREAD_POST_DELAYED_TIME);
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
           //NOT REQUIRED
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {
           handler.removeCallbacks(updateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {
           handler.removeCallbacks(updateTimeTask);

           int totalSongDuration = mediaPlayer.getDuration();
           int currentSongProgression = utilities.updateProgress(songSeekBar.getProgress(), totalSongDuration);

           mediaPlayer.seekTo(currentSongProgression);

           updateProgressBar();
    }

    @Override
    public void onCompletion(MediaPlayer mp)
    {
        if (onShuffleMode)
        {
            Random r = new Random();
            currentSongIndex = r.nextInt(songList.size() - 1);
        }
        else
        {
            if(!onRepeatMode)
            {
                if(currentSongIndex < (songList.size() - 1))
                {
                    currentSongIndex++;
                }
                else
                {
                    currentSongIndex = DEFAULT_SONG_INDEX;
                }
            }
        }

        playSong(currentSongIndex);
    }

}
