package csf.ca.typhonplayer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ~ætyíhœtrē~ on 2016-01-28.
 */
public class Song {
    public String name;
    public String artist;
    public String album;
    public String path;
    public String albumArtPath;


    Song(String _name, String _path)
    {
        name = _name;
        path = _path;
    }
}
