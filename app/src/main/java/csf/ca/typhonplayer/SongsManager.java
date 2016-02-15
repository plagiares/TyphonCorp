package csf.ca.typhonplayer;

import android.app.Activity;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * Created by Alexis on 2016-01-25.
 */
public class SongsManager extends Activity {

    private static final String[] STAR = {"*"};
    private static final String AUDIO_FILE_CRITERIA_SELECTION = " != 0";

    private final String AUDIO_PATH = new String("/sdcard/");
    private ArrayList<Song> songList = new ArrayList<Song>();

    public SongsManager()
    {
        initSongList();
    }

    public ArrayList<Song> getSongList()
    {
        return songList;
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
