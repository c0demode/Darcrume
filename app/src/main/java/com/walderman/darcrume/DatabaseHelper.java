package com.walderman.darcrume;
//safe to delete?
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database name & Version
    private static final String DATABASE_NAME = "darcrumeManager";
    private static final int DATABASE_VERSION = 1;

    //Table names
    private static final String TABLE_FILMS = "FILMS";
    private static final String TABLE_CHEMISTRY = "CHEMISTRY";
    private static final String TABLE_RECIPE = "RECIPE";
    private static final String TABLE_NOTES = "NOTES";
    private static final String TABLE_SESSION_HISTORY = "SESSION_HISTORY";

    //TABLE_FILMS columns
    private static final String filmFILM_ID = "FILM_ID";
    private static final String filmBRAND = "BRAND";
    private static final String filmNAME = "NAME";
    private static final String filmBW_COLOR = "BW_COLOR";
    private static final String filmISO = "ISO";
    private static final String filmEXP = "EXPOSURES";

    //TABLE_CHEMISTRY columns
    private static final String chemCHEM_ID = "CHEM_ID";
    private static final String chemBRAND = "BRAND";
    private static final String chemNAME = "NAME";
    private static final String chemBW_COLOR = "BW_COLOR";
    private static final String chemTYPE = "TYPE";

    //TABLE_RECIPE columns
    private static final String recipeRECIPE_ID = "RECIPE_ID";
    private static final String recipeFILM_ID = "FILM_ID";
    private static final String recipeDEVELOPER_ID = "DEVELOPER_ID";
    private static final String recipeSTOP_BATH_ID = "STOP_BATH_ID";
    private static final String recipeFIXER_ID = "FIXER_ID";
    private static final String recipeBLIX_ID = "BLIX_ID";
    private static final String recipeSTABILIZER_ID = "STABILIZER_ID";
    private static final String recipeDEVELOPER_WATER_RATIO = "DEVELOPER_WATER_RATIO";

    //TABE_NOTES columns
    private static final String notesNOTE_ID = "NOTE_ID";
    private static final String notesNOTE_TEXT = "NOTE_TEXT";

    //TABLE_SESSION_HISTORY columns
    private static final String sessSESSION_ID = "SESSION_ID";
    private static final String sessRECIPE_ID = "RECIPE_ID";
    private static final String sessWATER_TEMP_F = "WATER_TEMP_F";
    private static final String sessNUM_ROLLS = "NUM_ROLLS";
    private static final String sessBW_COLOR = "BW_COLOR";
    private static final String sessDATE = "DATE";

    //Table creation statements
    //TABLE_FILMS statement
    private static final String createTABLE_FILMS =
            "CREATE TABLE " + TABLE_FILMS + " (" + filmFILM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + filmBRAND + " TEXT, " + filmNAME + " TEXT, " + filmBW_COLOR + " TEXT, " + filmISO + " INTEGER, " + filmEXP + " INTEGER)";

    //TABLE_CHEMISTRY statement
    private static final String createTABLE_CHEMISTRY =
            "CREATE TABLE " + TABLE_CHEMISTRY + " (" + chemCHEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + chemBRAND + " TEXT, " + chemNAME + " TEXT, " + chemBW_COLOR + " TEXT, " + chemTYPE + " TEXT)";

    //TABLE_RECIPE statement
    private static final String createTABLE_RECIPE =
            "CREATE TABLE " + TABLE_RECIPE + " (" + recipeRECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + recipeFILM_ID + " INTEGER, " + recipeDEVELOPER_ID + " INTEGER, " + recipeSTOP_BATH_ID + " INTEGER, " +
                    recipeFIXER_ID + " INTEGER, " + recipeBLIX_ID + " INTEGER, " + recipeSTABILIZER_ID + " INTEGER, " + recipeDEVELOPER_WATER_RATIO + " TEXT)";

    //TABLE_NOTES statement
    private static final String createTABLE_NOTES =
            "CREATE TABLE " + TABLE_NOTES + " (" + notesNOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + notesNOTE_TEXT + " TEXT)";

    //TABLE_SESSION_HISTORY statement
    private static final String createTABLE_SESSION_HISTORY =
            "CREATE TABLE " + TABLE_SESSION_HISTORY + " (" + sessSESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + sessRECIPE_ID + " INTEGER, " + sessWATER_TEMP_F + " REAL, " + sessNUM_ROLLS + " INTEGER, " + sessBW_COLOR + " TEXT, " + sessDATE + " DATE)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the tables
        db.execSQL(createTABLE_FILMS);
        db.execSQL(createTABLE_CHEMISTRY);
        db.execSQL(createTABLE_RECIPE);
        db.execSQL(createTABLE_SESSION_HISTORY);
        db.execSQL(createTABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop older tables on upgrade
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHEMISTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSION_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);

        //now create new tables
        onCreate(db);
    }

    //========== Methods for TABLE_FILM ==========//

    //pass details about film, insert them into the database
    //then create an actual film object, including the film_id generated
    //by database insertion
    public Boolean insertNewFilm(int imageResource, String brand, String name, String bw_color, int iso, int exp){

        //try to insert a new film into database
        try {
            //get instance of database
            SQLiteDatabase db = this.getWritableDatabase();

            //prepare ContentValues to insert into database
            ContentValues cv = new ContentValues();
            cv.put(filmBRAND, brand);
            cv.put(filmNAME, name);
            cv.put(filmBW_COLOR, bw_color);
            cv.put(filmISO, iso);
            cv.put(filmEXP, exp);

            //insert new film into database
            db.insert(TABLE_FILMS, null, cv);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Query database for all films. From results, create film objects and add to an array of these film objects
     * @return
     */
    public ArrayList<Film> getAllFilms(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery("select * from " + TABLE_FILMS + " Order by " + filmBRAND, null);
        ArrayList<Film> filmArray = new ArrayList<>();
        while(result.moveToNext()){
            int film_id = result.getInt(result.getColumnIndex(filmFILM_ID));
            String brand = result.getString(result.getColumnIndex(filmBRAND));
            String name = result.getString(result.getColumnIndex(filmNAME));
            String bw_color = result.getString(result.getColumnIndex(filmBW_COLOR));
            int iso = result.getInt(result.getColumnIndex(filmISO));
            int exp = result.getInt(result.getColumnIndex(filmEXP));
            Film film = new Film(film_id, brand, name, bw_color, exp, iso);
            filmArray.add(film);
        }
        return filmArray;
    }

    public void truncateFilmsTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from " + TABLE_FILMS);
        //reset auto increment
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE name='" + TABLE_FILMS + "\'");
    }

}