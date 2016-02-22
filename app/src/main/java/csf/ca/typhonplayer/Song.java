package csf.ca.typhonplayer;

/**
 * Created by ~ætyíhœtrē~ on 2016-01-28.
 */
//Container Class that keep song info
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
