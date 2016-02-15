package csf.ca.typhonplayer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

/**
 * Created by Alexis on 2016-01-25.
 */
public class SongListItem extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.library_view);

        /*TextView txtSong = (TextView) findViewById(R.id.song_label);

        Intent i = getIntent();

        String song = i.getStringExtra("song");

        txtSong.setText(song);*/

    }
}
