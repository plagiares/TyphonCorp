package csf.ca.typhonplayer;

import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by Alexis on 2016-01-25.
 */
public class SongsManager {
    final String AUDIO_PATH = new String("/sdcard/");
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

    class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }


}
