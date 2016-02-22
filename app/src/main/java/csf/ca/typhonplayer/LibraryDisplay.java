package csf.ca.typhonplayer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Alexis on 2016-01-25.
 */
public class LibraryDisplay extends Activity {
    // Songs list

    private static final String[] STAR = {"*"};
    private static final String AUDIO_FILE_CRITERIA_SELECTION = " != 0";
    private ArrayList<Song> songList = new ArrayList<Song>();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    //Activity initializer
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        ListView listView ;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_view);

        initSongList();
        initView();

        listView = (ListView) findViewById(R.id.list_songs);

        AdapterSong adbSong;
        adbSong = new AdapterSong (LibraryDisplay.this, 0, songList);
        listView.setAdapter(adbSong);

        // listening to single listitem click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting listitem index
                int songIndex = position;

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        AndroidMusicPlayerActivity.class);
                // Sending songIndex to PlayerActivity
                in.putExtra("songIndex", songIndex);
                setResult(100, in);
                // Closing PlayListView
                finish();
            }
        });

    }



    public void initView()
    {

    }


    //Initialize song list
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
                        String albumArtPath = null;
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
}
