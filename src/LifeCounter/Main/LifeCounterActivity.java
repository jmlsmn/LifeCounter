package LifeCounter.Main;

import java.util.ArrayList;

import LifeCounter.Models.Player;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LifeCounterActivity extends Activity {
	
	private TextView mainLife;
	private TextView txtLeftLife;
	private TextView txtRightLife;
	
	private Button btnAddOne;
	private Button btnAddFive;
	private Button btnSubtractOne;
	private Button btnSubtractFive;
	
	private Player player;
	
	private OnClickListener lifeIncrementClickListener = new OnClickListener() {
		public void onClick(View v) {
			int adjustAmount = Integer.parseInt(v.getTag().toString());
			player.set_lifeTotal(Integer.parseInt(mainLife.getText().toString()));
			player.set_lifeTotal(player.get_lifeTotal() + adjustAmount);
			mainLife.setText(Integer.toString(player.get_lifeTotal()));
		}
	};;
	
	private OnClickListener changeLifeFocusClickListener = new OnClickListener() {
		public void onClick(View v) {
			String lifeLocation = v.getTag().toString();
			String main = mainLife.getText().toString();
			if (lifeLocation == "left") {
				mainLife.setText(txtLeftLife.getText().toString());
				txtLeftLife.setText(main);
			}
			else {
				mainLife.setText(txtRightLife.getText().toString());
				txtRightLife.setText(main);
			}
		}
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_player_count);
        
        Intent initiatingIntent = getIntent();
        
        ArrayList<Player> players = (ArrayList<Player>)initiatingIntent.getExtras().get("players");
        player = players.get(0);
        
        mainLife = (TextView) findViewById(R.id.mainLife);
        mainLife.setTag("main");
        mainLife.setText(Integer.toString(player.get_lifeTotal()));
      
        txtLeftLife = (TextView) findViewById(R.id.txtLeftLife);
        txtLeftLife.setText(Integer.toString(player.get_commanderLifeTotal()));
        txtLeftLife.setTag("left");
        txtLeftLife.setOnClickListener(changeLifeFocusClickListener);
        
        txtRightLife = (TextView) findViewById(R.id.txtRightLife);
        txtRightLife.setText(Integer.toString(player.get_poisonCounterTotal()));
        txtRightLife.setTag("right");
        txtRightLife.setOnClickListener(changeLifeFocusClickListener);
        
        btnAddOne = (Button) findViewById(R.id.btnAddOne);
        btnAddOne.setTag(1);
        btnAddOne.setOnClickListener(lifeIncrementClickListener);
        
        btnAddFive = (Button) findViewById(R.id.btnAddFive);
        btnAddFive.setTag(5);
        btnAddFive.setOnClickListener(lifeIncrementClickListener);
        
        btnSubtractOne = (Button) findViewById(R.id.btnSubtractOne);
        btnSubtractOne.setTag(-1);
        btnSubtractOne.setOnClickListener(lifeIncrementClickListener);
        
        btnSubtractFive = (Button) findViewById(R.id.btnSubtractFive);
        btnSubtractFive.setTag(-5);
        btnSubtractFive.setOnClickListener(lifeIncrementClickListener);
    }
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.in_game, menu);
        return true;
    }
}