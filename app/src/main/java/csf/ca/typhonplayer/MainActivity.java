package csf.ca.typhonplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.player);
    }

    public void displaySongList(View view)
    {
        Intent i = new Intent(getApplicationContext(), SongDisplay.class);

        startActivity(i);

    }
}
