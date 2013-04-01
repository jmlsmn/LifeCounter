package LifeCounter.Main;

import LifeCounter.Utilities.DatabaseHandler;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class EditPlayersActivity extends Activity {
	
	private ListView lvEditPlayers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_players);
        
        lvEditPlayers = (ListView) findViewById(R.id.playerList);
        
        final DatabaseHandler db = new DatabaseHandler(this);
        
        String [] players = db.getAllPlayers();
        
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row_players, players);
        
        lvEditPlayers.setAdapter(adapter);
        
        lvEditPlayers.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, final int position, long id) {
				AlertDialog.Builder adb = new AlertDialog.Builder(EditPlayersActivity.this);
				adb.setTitle("Edit");
				adb.setMessage("Delete " + adapter.getItem(position) );
				adb.setNegativeButton("Cancel", null);
				adb.setPositiveButton("Confirm", new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						adapter.notifyDataSetChanged();
					}
				});
			adb.show();
			}
		});
    }

	@Override
    public void onBackPressed() {
        super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
