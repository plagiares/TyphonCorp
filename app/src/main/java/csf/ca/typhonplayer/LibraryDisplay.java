package csf.ca.typhonplayer;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import csf.ca.utilities.Utilities;

/**
 * Created by Alexis on 2016-01-25.
 */
public class LibraryDisplay extends Activity {
    // Songs list
    private ArrayList<Song> songsList = new ArrayList<Song>();
    private ArrayList<String> titles = new ArrayList<String>();

    private static final String[] STAR = {"*"};
    private static final String AUDIO_FILE_CRITERIA_SELECTION = " != 0";

    private ArrayList<Song> songList = new ArrayList<Song>();


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        ListView listView ;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_view);

        initView();

        listView = (ListView) findViewById(R.id.list_songs);

        AdapterSong adbSong;
        adbSong = new AdapterSong (LibraryDisplay.this, 0, songList);
        listView.setAdapter(adbSong);

        /*
        // selecting single ListView item
        ListView lv = getListView();
        // listening to single listitem click
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting listitem index
                int songIndex = position;

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        AndroidBuildingMusicPlayerActivity.class);
                // Sending songIndex to PlayerActivity
                in.putExtra("songIndex", songIndex);
                setResult(100, in);
                // Closing PlayListView
                finish();
            }
        });
        */
    }



    public void initView()
    {
        ImageButton btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        ImageButton btnNext = (ImageButton) findViewById(R.id.btnNext);
        ImageButton btnBackward = (ImageButton) findViewById(R.id.btnBackward);
        ImageButton btnForward = (ImageButton) findViewById(R.id.btnForward);
        ImageButton btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
        ImageButton btnPlayList = (ImageButton) findViewById(R.id.btnPlaylist);
        ImageButton btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
        ImageButton btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);


    }

   /* public void initSongList()
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

                    if (type.equals("audio/mpeg")) {

                        String songName = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                        String songArtiste = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                        String songAlbum = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                        String songPath = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA));

                        Song newSong = new Song(songName, songPath);

                        newSong.album = songAlbum;
                        newSong.artist = songArtiste;
                        songList.add(newSong);
                    }

                }
                while(c.moveToNext());


            }

        }
    }


*/
}
