package csf.ca.typhonplayer;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Alexis on 2016-01-25.
 */
public class SongDisplay extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*String[] song_titles = getResources().getStringArray(R.array.song_list);

        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.lblsong, song_titles));

        ListView lv = getListView();

        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String song = ((TextView) view).getText().toString();

                Intent i = new Intent(getApplicationContext(), SongListItem.class);

                i.putExtra("song", song);
                startActivity(i);

            }
        });
*/
    }
}
