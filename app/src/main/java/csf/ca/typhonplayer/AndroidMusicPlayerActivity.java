package csf.ca.typhonplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import csf.ca.utilities.Utilities;

/**
 * Created by Alexis on 2016-01-26.
 */
public class AndroidMusicPlayerActivity extends Activity
implements OnCompletionListener , SeekBar.OnSeekBarChangeListener
{
     private static final int DEFAULT_FORWARD_TIME = 5000;
     private static final int DEFAULT_BACKWARD_TIME = 5000;
     private static final int INITIAL_SONG_TIME = 0;

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

     private SeekBar songSeekBar;

     private TextView lblSongTitle;
     private TextView lblSongProgression;
     private TextView lblSongTotalDuration;

     private MediaPlayer mediaPlayer;

     private Handler handler = new Handler();

     private SongsManager songsManager;
     private Utilities utilities;

     private int seekForwardTime = DEFAULT_FORWARD_TIME;
     private int seekBackwardTime = DEFAULT_BACKWARD_TIME;
     private int currentSongIndex = DEFAULT_SONG_INDEX;

     private boolean onShuffleMode = false;
     private boolean onRepeatMode = false;

     private ArrayList<HashMap<String,String>> songsList = new ArrayList<HashMap<String, String>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

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

          songSeekBar = (SeekBar) findViewById(R.id.songProgressBar);

          lblSongTitle = (TextView) findViewById(R.id.songTitle);
          lblSongProgression = (TextView) findViewById(R.id.songCurrentDurationLabel);
          lblSongTotalDuration = (TextView) findViewById(R.id.songTotalDurationLabel);

          mediaPlayer = new MediaPlayer();

          songsManager = new SongsManager();

          utilities = new Utilities();

          songSeekBar.setOnSeekBarChangeListener(this);
          mediaPlayer.setOnCompletionListener(this);

          //songsList = songsManager.getPlayList();
    }

    public void initControlListener()
    {
          btnPlayList.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View arg0) {
                  /*TODO
                  Intent intent = new Intent(getApplicationContext(), );
                  startActivity(intent, 100);*/
              }
          });

          btnNext.setOnClickListener(new View.OnClickListener()
          {
              @Override
              public void onClick(View arg0)
              {
                  if(currentSongIndex < (songsList.size() - INDEX_DIFFERENCE))
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

    }

    private void playSong(int currentSongIndex)
    {
        try
        {
            mediaPlayer.reset();

            mediaPlayer.setDataSource(songsList.get(currentSongIndex).get(SONG_PATH_KEY));

            mediaPlayer.prepare();
            mediaPlayer.start();

            String songTitle = songsList.get(currentSongIndex).get(SONG_TITLE_KEY);
            lblSongTitle.setText(songTitle);

            btnPlay.setImageResource(R.drawable.btn_pause);

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

            lblSongTotalDuration.setText(utilities.timerFormat(totalDuration));
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

    }

}
