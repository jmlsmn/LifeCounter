package LifeCounter.Utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
 
import LifeCounter.Models.Player;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String TABLE_PLAYERS = "players";
    private static final String KEY_NAME = "name";
	private static String DB_PATH = "/data/data/LifeCounter.Main/databases/";
    private static String DATABASE_NAME = "lifeCounter.db";
    private SQLiteDatabase db;
    private final Context myContext;
    
    public DatabaseHandler(Context context) { 
    	super(context, DATABASE_NAME, null, 1);
        this.myContext = context;
    }	
 
    public void createDataBase() throws IOException {
    	boolean dbExist = checkDataBase();
 
    	if(dbExist)
		{
    		//do nothing - database already exist
    	}
		else
		{
        	this.getReadableDatabase();
        	try
			{
    			copyDataBase();
    		}
			catch (IOException e)
			{
        		throw new Error("Error copying database");
        	}
    	}
    }
 
    private boolean checkDataBase(){
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DATABASE_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
    		//database does't exist yet.
    	}
 
    	if(checkDB != null){
    		checkDB.close();
    	}
 
    	return checkDB != null ? true : false;
    }
 
    private void copyDataBase() throws IOException{
    	InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
    	String outFileName = DB_PATH + DATABASE_NAME;
 
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }
 
    public void openDataBase() throws SQLException{
        String myPath = DB_PATH + DATABASE_NAME;
    	db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
 
    @Override
	public synchronized void close() {
    	    if(db != null)
    		    db.close(); 
    	    super.close();
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
 
    // Adding new player
    void addPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, player.getName());
 
        db.insert(TABLE_PLAYERS, null, values);
        db.close();
    }
 
    // Getting single player
    Player getPlayer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_PLAYERS, new String[] { KEY_NAME }, KEY_NAME + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Player player = new Player(cursor.getString(0));
        return player;
    }
 
    // Getting All Players
    public String [] getAllPlayers() {
        List<String> playerList = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_PLAYERS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
                playerList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
 
        return playerList.toArray(new String[playerList.size()]);
    }
 
    // Updating single player
    public int updatePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, player.getName());
 
        return db.update(TABLE_PLAYERS, values, KEY_NAME + " = ?",
                new String[] { String.valueOf(player.getName()) });
    }
 
    // Deleting single player
    public void deletePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYERS, KEY_NAME + " = ?",
                new String[] { String.valueOf(player.getName()) });
        db.close();
    }
 
    // Getting players Count
    public int getPlayersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PLAYERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        return cursor.getCount();
    }
 
}