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

    public SongsManager(){

    }

    public ArrayList<Song>getSongList()
    {
        File songFolder = new File(AUDIO_PATH);
        if (songFolder.listFiles(new FileExtensionFilter()).length > 0) {
            for (File file : songFolder.listFiles(new FileExtensionFilter())) {

                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(String.valueOf(file));

                String albumName =
                        mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);


                String songTitle = file.getName().substring(0, (file.getName().length() - 4));
                String songPath = file.getPath();
                Song songtoAdd = new Song(songTitle, songPath);

                songList.add(songtoAdd);
            }
        }
        return songList;
    }

    public ArrayList<Song> getInternalDriveAudio()
    {
        Cursor c;
        Uri externalContentPath = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selectMusicCriteria = MediaStore.Audio.Media.IS_MUSIC + AUDIO_FILE_CRITERIA_SELECTION;

        c = managedQuery(externalContentPath, STAR, selectMusicCriteria, null, null);

        if(c != null)
        {
            if(c.moveToFirst())
            {
                do
                {
                    String songName = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String songArtiste = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String songAlbum = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String songPath = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA));

                    Song newSong = new Song(songName, songPath);

                    newSong.album = songAlbum;
                    newSong.artist = songArtiste;

                    songList.add(newSong);
                }
                while(c.moveToNext());


            }
        }

        return songList;

    }

    class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }
}
