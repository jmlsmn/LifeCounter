package LifeCounter.Main;

import java.util.*;

import LifeCounter.Models.LifeAdjustment;
import LifeCounter.Models.LifeAdjustmentType;
import LifeCounter.Models.Player;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MultiplayerLifeCounterActivity extends Activity {

	private TextView txtOnePlayer;
	private TextView txtTwoPlayer;
	private TextView txtThreePlayer;
	private TextView txtFourPlayer;
	
	private TextView txtLife;
	private TextView txtPoisonLife;
	private TextView txtCommanderLife;
	
	private ArrayList<Player> players;
	
	private HashMap<LifeAdjustmentType,LifeAdjustment> adjustments;
	
	private Player currentPlayer;
	
	private LifeAdjustment currentAdjustment;
	
	private OnClickListener lifeChangeDialogListener = new OnClickListener() {
		
		public void onClick(View v) {
			currentPlayer = (Player)v.getTag();
			
			final Dialog counterDialog = new Dialog(MultiplayerLifeCounterActivity.this);
			counterDialog.setContentView(R.layout.counter);
			counterDialog.setTitle(currentPlayer.getName());
			
			SetUpDialog(counterDialog);
			
			counterDialog.show();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent initiatingIntent = getIntent();
		
		players = (ArrayList<Player>)initiatingIntent.getExtras().get("players");
		
		int viewToShow = 0;
		
		switch (players.size()) 
		{
			case 2:
				viewToShow = R.layout.two_player_count;
				break;
			case 3:
				viewToShow = R.layout.three_player_count;
				break;
			case 4:
				viewToShow = R.layout.four_player_count;
				break;
		}
		
		setContentView(viewToShow);
		
		txtOnePlayer = (TextView) findViewById(R.id.txtOnePlayer);
		txtOnePlayer.setText(Integer.toString(players.get(0).get_lifeTotal()));
		txtOnePlayer.setOnClickListener(lifeChangeDialogListener);
		txtOnePlayer.setTag(players.get(0));
		
		txtTwoPlayer = (TextView) findViewById(R.id.txtTwoPlayer);
		txtTwoPlayer.setText(Integer.toString(players.get(1).get_lifeTotal()));
		txtTwoPlayer.setOnClickListener(lifeChangeDialogListener);
		txtTwoPlayer.setTag(players.get(1));
		
		if(viewToShow == R.layout.three_player_count || viewToShow == R.layout.four_player_count)
		{
			txtThreePlayer = (TextView) findViewById(R.id.txtThreePlayer);
			txtThreePlayer.setText(Integer.toString(players.get(2).get_lifeTotal()));
			txtThreePlayer.setOnClickListener(lifeChangeDialogListener);
			txtThreePlayer.setTag(players.get(2));
			
			if(viewToShow == R.layout.four_player_count)
			{
				txtFourPlayer = (TextView) findViewById(R.id.txtFourPlayer);
				txtFourPlayer.setText(Integer.toString(players.get(3).get_lifeTotal()));
				txtFourPlayer.setOnClickListener(lifeChangeDialogListener);
				txtFourPlayer.setTag(players.get(3));
			}
		}
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.in_game, menu);
        return true;
    }
	
	private void SetUpDialog(final Dialog dialog)
	{
		//create adjustments
		adjustments = new HashMap<LifeAdjustmentType,LifeAdjustment>();
		LifeAdjustment lifeTotalAdjustment = new LifeAdjustment(LifeAdjustmentType.LIFE,
																currentPlayer.get_lifeTotal(),
																0);
		LifeAdjustment poisonTotalAdjustment = new LifeAdjustment(LifeAdjustmentType.POISON,
																  currentPlayer.get_poisonCounterTotal(),
																  0);
		LifeAdjustment commanderTotalAdjustment = new LifeAdjustment(LifeAdjustmentType.COMMANDER,
																	 currentPlayer.get_commanderLifeTotal(),
															         0);
		
		adjustments.put(LifeAdjustmentType.LIFE, lifeTotalAdjustment);
		adjustments.put(LifeAdjustmentType.POISON, poisonTotalAdjustment);
		adjustments.put(LifeAdjustmentType.COMMANDER, commanderTotalAdjustment);
		
		currentAdjustment = adjustments.get(LifeAdjustmentType.LIFE);
		
		txtLife = (TextView) dialog.findViewById(R.id.txtLife);
		txtLife.setText(Integer.toString(currentPlayer.get_lifeTotal()));
		txtLife.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				currentAdjustment = adjustments.get(LifeAdjustmentType.LIFE);
			}
		});
		
		txtCommanderLife = (TextView) dialog.findViewById(R.id.txtCommanderLife);
		txtCommanderLife.setText(Integer.toString(currentPlayer.get_commanderLifeTotal()));
		txtCommanderLife.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				currentAdjustment = adjustments.get(LifeAdjustmentType.COMMANDER);
			}
		});
		
		txtPoisonLife = (TextView) dialog.findViewById(R.id.txtPoisonLife);
		txtPoisonLife.setText(Integer.toString(currentPlayer.get_poisonCounterTotal()));
		txtPoisonLife.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				currentAdjustment = adjustments.get(LifeAdjustmentType.POISON);
			}
		});
		
		Button btnApply = (Button) dialog.findViewById(R.id.btnApply);
		btnApply.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				//adjustments
			}
		});
		
		Button btnAddOne = (Button) dialog.findViewById(R.id.btnAddOne);
		btnAddOne.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				AdjustTotal(1);
			}
		});
		
		Button btnAddFive = (Button) dialog.findViewById(R.id.btnAddFive);
		btnAddFive.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				AdjustTotal(5);
			}
		});
		
		Button btnSubstractOne = (Button) dialog.findViewById(R.id.btnSubtractOne);
		btnSubstractOne.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				AdjustTotal(-1);
			}
		});
		
		Button btnSubtractFive = (Button) dialog.findViewById(R.id.btnSubtractFive);
		btnSubtractFive.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				AdjustTotal(-5);
			}
		});
		
		Button btnApplyToAll = (Button) dialog.findViewById(R.id.btnApplyToAll);
		Button btnApplyToOpponents = (Button) dialog.findViewById(R.id.btnApplyToOpponents);
		
		Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
	}
	
	private void AdjustTotal(int adjustmentAmount)
	{
		currentAdjustment.set_adjustment(currentAdjustment.get_adjustment() + adjustmentAmount);
		currentAdjustment.set_total(currentAdjustment.get_total() + adjustmentAmount);
		switch (currentAdjustment.get_type()) {
		case LIFE:
			txtLife.setText(Integer.toString(currentAdjustment.get_total()));
			break;
		case COMMANDER:
			txtCommanderLife.setText(Integer.toString(currentAdjustment.get_total()));
			break;
		case POISON:
			txtPoisonLife.setText(Integer.toString(currentAdjustment.get_total()));
			break;
		}
	}
}

