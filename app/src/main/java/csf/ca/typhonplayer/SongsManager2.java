package csf.ca.typhonplayer;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ~ætyíhœtrē~ on 2016-02-01.
 */
public class SongsManager2 {

    ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    String[] STAR = { "*" };

    Cursor cursor;
    Context context;
    Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

    cursor = managedQuery(uri, STAR, selection, null, null);

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
