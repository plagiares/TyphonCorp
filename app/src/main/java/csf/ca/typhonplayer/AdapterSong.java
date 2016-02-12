package csf.ca.typhonplayer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ~ætyíhœtrē~ on 2016-02-10.
 */
public class AdapterSong extends ArrayAdapter<Song> {
    private Activity activity;
    private ArrayList<Song> lSongs;
    private static LayoutInflater inflater = null;

    public AdapterSong (Activity activity, int textViewResourceId,ArrayList<Song> _lSongs) {
        super(activity, textViewResourceId);
        try {
            this.activity = activity;
            this.lSongs = lSongs;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return lSongs.size();
    }

    public Song getItem(Song position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_title;
        public TextView display_artist;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.list_item, null);
                holder = new ViewHolder();

                holder.display_title = (TextView) vi.findViewById(R.id.songTitle);
                holder.display_artist = (TextView) vi.findViewById(R.id.songArtist);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.display_title.setText(lSongs.get(position).name);
            holder.display_artist.setText(lSongs.get(position).album);

        } catch (Exception e) {


        }
        return vi;
    }
}
