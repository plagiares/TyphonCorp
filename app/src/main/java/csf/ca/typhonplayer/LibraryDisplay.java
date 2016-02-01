package csf.ca.typhonplayer;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alexis on 2016-01-25.
 */
public class LibraryDisplay extends ListActivity {
    // Songs list
    private ArrayList<Song> songsList = new ArrayList<Song>();
    private ArrayList<String> titles = new ArrayList<String>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list_item_view);


        //SongsManager plm = new SongsManager();
        // get all songs from sdcard
            // this.songsList = plm.getSongList();
        /*
        // looping through playlist
        for (int i = 0; i < songsList.size(); i++) {
            // creating new HashMap
            //HashMap<String, String> song = songsList.get(i).name songsList.get(i).path;

            // adding HashList to ArrayList
            //songsListData.add(song);
        }
        */

        Song song = new Song("name", "abc");
        song.name = "name";
        song.album = "album";
        song.artist = "artist";

        Song song2 = new Song("name2", "abc2");
        song.name = "name2";
        song.album = "album2";
        song.artist = "artist2";

        songsList.add(song);
        songsList.add(song2);

        // Adding menuItems to ListView
        ArrayAdapter<Song> adapter = new ArrayAdapter<Song>(this,
                R.layout.list_item, songsList);
        setListAdapter(adapter);
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

    void getSongs ()
    {
        ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
        String[] STAR = { "*" };

        Cursor cursor;
        Context context;
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        cursor =  getContentResolver().query(uri, STAR, selection, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String songName = cursor
                            .getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));


                    String path = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DATA));


                    String albumName = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    int albumId = cursor
                            .getInt(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

                    HashMap<String, String> song = new HashMap<String, String>();
                    song.put("songTitle",albumName+" "+songName+"___"+albumId);
                    song.put("songPath",path );
                    songsList.add(song);

                } while (cursor.moveToNext());


            }

        }
    }



}
