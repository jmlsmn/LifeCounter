package LifeCounter.Main;

import java.io.IOException;
import java.util.ArrayList;
import LifeCounter.Models.Player;
import LifeCounter.Utilities.DatabaseHandler;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewFlipper;

public class GameSetupActivity extends Activity {

	private ViewFlipper vfGameSetup;
	
	private int startLifeTotal;
	private int playerCount;
	
	private String editType;
	
	private Button btnNewGame;
	private Button standard_button;
	private Button commander_button;
	private Button btnOnePlayer;
	private Button btnTwoPlayer;
	private Button btnThreePlayer;
	private Button btnFourPlayer;
	private Button btnEditProfiles;
	private Button btnEditPlayers;
	
	private Intent newActivityIntent;
	private Intent editPlayersIntent;
	private Intent editProfilesIntent;
		
	private OnClickListener newActivityClickListener = new OnClickListener() {
		
		public void onClick(View v) {
			playerCount = (Integer) v.getTag();
			ArrayList<Player> players = new ArrayList<Player>();
			
			for (int i = 0; i < playerCount; i++) {
				Player p = new Player();
				p.set_lifeTotal(startLifeTotal);
				p.set_poisonCounterTotal(0);
				p.set_commanderLifeTotal(0);
				players.add(p);
			}
			
			if(playerCount == 1)
			{
				newActivityIntent = new Intent(GameSetupActivity.this, LifeCounterActivity.class);
			}
			else if (playerCount > 1)
			{
				newActivityIntent = new Intent(GameSetupActivity.this, MultiplayerLifeCounterActivity.class);
			}
			newActivityIntent.putExtra("players", players);
			startActivity(newActivityIntent);
		}
	};
	
	private OnClickListener playerCountClickListener= new OnClickListener() {
			
		public void onClick(View v) {
			startLifeTotal = (Integer) v.getTag();
			vfGameSetup.setInAnimation(GameSetupActivity.this, R.anim.push_left_in);
			vfGameSetup.setOutAnimation(GameSetupActivity.this, R.anim.push_left_out);
			vfGameSetup.showNext();
		}
	};
		
	private OnClickListener editClickListener = new OnClickListener() {
		
		public void onClick(View v) {
			editType = (String) v.getTag();
			if (editType == "editProfiles")
			{
				startActivity(editProfilesIntent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
			else
			{
				startActivity(editPlayersIntent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_setup);
		
		DatabaseHandler db = new DatabaseHandler(this);
		
		try {
			db.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		db.openDataBase();
		
		vfGameSetup = (ViewFlipper) findViewById(R.id.vfGameSetup);
		
		btnNewGame = (Button) findViewById(R.id.start_button);
		btnNewGame.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				vfGameSetup.setInAnimation(GameSetupActivity.this, R.anim.push_left_in);
				vfGameSetup.setOutAnimation(GameSetupActivity.this, R.anim.push_left_out);
				vfGameSetup.showNext();
			}
		});
		
        editProfilesIntent = new Intent(this, EditProfilesActivity.class);
        editPlayersIntent = new Intent(this, EditPlayersActivity.class);
        
		btnEditProfiles = (Button) findViewById(R.id.btnEditProfiles);
		btnEditProfiles.setTag("editProfiles");
		btnEditProfiles.setOnClickListener(editClickListener);
		
		btnEditPlayers = (Button) findViewById(R.id.btnEditPlayers);
		btnEditPlayers.setTag("editPlayers");
		btnEditPlayers.setOnClickListener(editClickListener);
        
        standard_button = (Button) findViewById(R.id.standard_button);
        standard_button.setTag(20);
        standard_button.setOnClickListener(playerCountClickListener);
        
        commander_button = (Button) findViewById(R.id.commander_button);
        commander_button.setTag(40);
        commander_button.setOnClickListener(playerCountClickListener);
        
        btnOnePlayer = (Button) findViewById(R.id.btnOnePlayer);
        btnOnePlayer.setTag(1);
        btnOnePlayer.setOnClickListener(newActivityClickListener);
        
        btnTwoPlayer = (Button) findViewById(R.id.btnTwoPlayer);
        btnTwoPlayer.setTag(2);
        btnTwoPlayer.setOnClickListener(newActivityClickListener);
        
        btnThreePlayer = (Button) findViewById(R.id.btnThreePlayer);
        btnThreePlayer.setTag(3);
        btnThreePlayer.setOnClickListener(newActivityClickListener);
        
        btnFourPlayer = (Button) findViewById(R.id.btnFourPlayer);
        btnFourPlayer.setTag(4);
        btnFourPlayer.setOnClickListener(newActivityClickListener);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	    int position = vfGameSetup.getDisplayedChild();
	    savedInstanceState.putInt("TAB_NUMBER", position);
	    savedInstanceState.putInt("startLifeTotal",startLifeTotal);
	    savedInstanceState.putInt("playerCount", playerCount);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		playerCount = savedInstanceState.getInt("playerCount");
		startLifeTotal = savedInstanceState.getInt("startLifeTotal");
	    int vfPosition = savedInstanceState.getInt("TAB_NUMBER");
	    vfGameSetup.setDisplayedChild(vfPosition);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK 
        		&& event.getRepeatCount() == 0
        		&& !vfGameSetup.getCurrentView().equals(vfGameSetup.getChildAt(0))) 
        {
        	vfGameSetup.setInAnimation(GameSetupActivity.this, R.anim.push_right_in);
			vfGameSetup.setOutAnimation(GameSetupActivity.this, R.anim.push_right_out);
        	vfGameSetup.showPrevious();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
	
}
