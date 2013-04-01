package LifeCounter.Models;

import java.io.Serializable;

public class Player implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private variables
    int _id;
    String _name;
    int _lifeTotal;
	int _commanderLifeTotal;
    int _poisonCounterTotal;
    
    // Empty constructor
    public Player(){
 
    }
    // constructor
    public Player(String name){
        this._name = name;
    }
 
    // getting name
    public String getName(){
        return this._name;
    }
 
    // setting name
    public void setName(String name){
        this._name = name;
    }
    
    public int get_lifeTotal() {
		return _lifeTotal;
	}
	
    public void set_lifeTotal(int lifeTotal) {
		this._lifeTotal = lifeTotal;
	}
	
	public int get_commanderLifeTotal() {
		return _commanderLifeTotal;
	}
	
	public void set_commanderLifeTotal(int commanderLifeTotal) {
		this._commanderLifeTotal = commanderLifeTotal;
	}
	
	public int get_poisonCounterTotal() {
		return _poisonCounterTotal;
	}
	
	public void set_poisonCounterTotal(int poisonCounterTotal) {
		this._poisonCounterTotal = poisonCounterTotal;
	}
}
