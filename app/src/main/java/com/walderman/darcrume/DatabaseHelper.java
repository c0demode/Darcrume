package com.walderman.darcrume;
//safe to delete?
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Switch;


import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database name & Version
    private static final String DATABASE_NAME = "darcrumeManager";
    private static final int DATABASE_VERSION = 1;
    private enum ChemType {
        BW_Dev,
        BW_Stop,
        BW_Fix,
        COL_Dev,
        COL_Blix,
        COL_Stab;
    }
    //Table names
    private static final String TABLE_FILMS = "FILMS";
    private static final String TABLE_BW_DEVELOPER = "BW_DEVELOPER";
    private static final String TABLE_BW_STOP = "BW_STOP";
    private static final String TABLE_BW_FIX = "BW_FIX";
    private static final String TABLE_COLOR_DEVELOPER = "COLOR_DEVELOPER";
    private static final String TABLE_COLOR_BLIX = "COLOR_BLIX";
    private static final String TABLE_COLOR_STABILIZER = "COLOR_STABILIZER";
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

    /**
     * Create tables
     */
    private static final String createTABLE_FILMS           = "CREATE TABLE FILMS(FILM_ID INTEGER PRIMARY KEY AUTOINCREMENT, BRAND TEXT, NAME TEXT, BW_COLOR TEXT, ISO INTEGER, EXPOSURES INTEGER)";
    private static final String createTABLE_BW_DEV          = "CREATE TABLE BW_DEVELOPER(BW_DEV_ID INTEGER PRIMARY KEY AUTOINCREMENT, BRAND TEXT, NAME TEXT)";
    private static final String createTABLE_BW_STOP         = "CREATE TABLE BW_STOP(BW_STOP_ID INTEGER PRIMARY KEY AUTOINCREMENT, BRAND TEXT, NAME TEXT)";
    private static final String createTABLE_BW_FIX          = "CREATE TABLE BW_FIX(BW_FIX_ID INTEGER PRIMARY KEY AUTOINCREMENT, BRAND TEXT, NAME TEXT)";
    private static final String createTABLE_COLOR_DEV       = "CREATE TABLE COLOR_DEVELOPER(COLOR_DEV_ID INTEGER PRIMARY KEY AUTOINCREMENT, BRAND TEXT, NAME TEXT)";
    private static final String createTABLE_COLOR_BLIX      = "CREATE TABLE COLOR_BLIX(COLOR_BLIX_ID INTEGER PRIMARY KEY AUTOINCREMENT, BRAND TEXT, NAME TEXT)";
    private static final String createTABLE_COLOR_STAB      = "CREATE TABLE COLOR_STAB(COLOR_STABILIZER INTEGER PRIMARY KEY AUTOINCREMENT, BRAND TEXT, NAME TEXT)";

    //TABLE_NOTES statement
    private static final String createTABLE_NOTES           = "CREATE TABLE TABLE_NOTES(NOTE_ID INTEGER PRIMARY KEY AUTOINCREMENT, NOTE_TEXT TEXT)";

    //TABLE_SESSION_HISTORY statement
    private static final String createTABLE_SESSION_HISTORY = "CREATE TABLE SESSION_HISTORY(SESSION_ID INTEGER PRIMARY KEY AUTOINCREMENT, RECIPE_ID INTEGER, BW_COLOR TEXT, DATE DATE)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the tables
        db.execSQL(createTABLE_FILMS);

        db.execSQL(createTABLE_BW_DEV);
        db.execSQL(createTABLE_BW_STOP);
        db.execSQL(createTABLE_BW_FIX);

        db.execSQL(createTABLE_COLOR_DEV);
        db.execSQL(createTABLE_COLOR_BLIX);
        db.execSQL(createTABLE_COLOR_STAB);

        db.execSQL(createTABLE_SESSION_HISTORY);
        db.execSQL(createTABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop older tables on upgrade
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILMS);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BW_DEVELOPER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BW_STOP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BW_FIX);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLOR_DEVELOPER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLOR_BLIX);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLOR_STABILIZER);

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
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result = db.rawQuery("select * from FILMS Order by BRAND", null);
        ArrayList<Film> filmArray = new ArrayList<>();
        while(result.moveToNext()){
            Film film = processFilmCursor(result);
            filmArray.add(film);
        }
        return filmArray;
    }

    /**
     * Takes an int "film_id" and queries the database for that film_id. Creates and returns a Film object based on that result.
     * @param film_Id
     * @return
     */
    public Film selectFilm(int film_Id){
        SQLiteDatabase db = this.getWritableDatabase();
        Film film = new Film();
        Cursor result = db.rawQuery("SELECT * FROM FILMS WHERE FILM_ID = " + film_Id, null);
        while(result.moveToNext()){
            film = processFilmCursor(result);
        }
        return film;
    }

    /**
     * Takes a Cursor (result) from db query and creates and returns a Film object based on that result
     * @param cursor
     * @return
     */
    public Film processFilmCursor(Cursor cursor){
        int film_id = cursor.getInt(cursor.getColumnIndex(filmFILM_ID));
        String brand = cursor.getString(cursor.getColumnIndex(filmBRAND));
        String name = cursor.getString(cursor.getColumnIndex(filmNAME));
        String bw_color = cursor.getString(cursor.getColumnIndex(filmBW_COLOR));
        int iso = cursor.getInt(cursor.getColumnIndex(filmISO));
        int exp = cursor.getInt(cursor.getColumnIndex(filmEXP));

        Film film = new Film(film_id, brand, name, bw_color, exp, iso);
        return film;
    }

    public void truncateFilmsTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from FILMS");
        //Delete the sequence for TABLE_FILMS which will reset primary key.
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE name='FILMS'");
    }

    /**
     * takes a film object and updates Films table for that film_id
     * Once row is updated on the table, queries the table for that film_id and returns a new Film object based on that row.
     * This is used to save changes made by a user in the Manage Films activity.
     * @param film
     * @return
     */
    public Film updateFilm(Film film) {
        int filmId = film.getFilm_id();
        String brand = film.getBrand();
        String name = film.getName();
        int iso = film.getIso();
        int exp = film.getExp();
        String type = film.getType();
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE FILMS SET (BRAND, NAME, ISO, EXPOSURES, BW_COLOR) = ('" + brand + "','" + name + "','" + iso + "','" + exp + "','" + type + "') WHERE FILM_ID = " + filmId);

        Film updatedFilm = selectFilm(filmId);
        return updatedFilm;
    }

    /**
     * Take an argument specifying the type of chemical
     * @return
     */
    public ArrayList<Chem> getAllChems(ChemType chemType){
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuilder tableName = new StringBuilder(); //maybe use a string builder?
        StringBuilder query = new StringBuilder();
        switch(chemType){
            case BW_Dev: tableName.append("BW_DEVELOPER"); break;
            case BW_Stop: tableName.append("BW_STOP"); break;
            case BW_Fix: tableName.append("BW_FIX"); break;
            case COL_Dev: tableName.append("COLOR_DEVELOPER"); break;
            case COL_Blix: tableName.append("COLOR_BLIX"); break;
            case COL_Stab: tableName.append("COLOR_STABILIZER"); break;
        }
        query.append("SELECT * FROM " + tableName + " ORDER BY BRAND");
        Cursor result = db.rawQuery(query.toString(), null);
        ArrayList<Chem> chemArray = new ArrayList<>();
        while(result.moveToNext()){
            Chem chem = processChemCursor(result);
            chemArray.add(chem);
        }
        return chemArray;
    }

    /**
     * Query database for all BW Developers. From results, create Chem objects and add to an array of these Chem objects
     * @return
     */
    public ArrayList<Chem> getAllBW_Devs(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result = db.rawQuery("SELECT * FROM BW_DEVELOPER ORDER BY BRAND", null);
        ArrayList<Chem> chemArray = new ArrayList<>();
        while(result.moveToNext()){
            Chem chem = processChemCursor(result);
            chemArray.add(chem);
        }
        return chemArray;
    }

    /**
     * Query database for all BW Stops. From results, create Chem objects and add to an array of these Chem objects
     * @return
     */
    public ArrayList<Chem> getAllBW_Stops(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result = db.rawQuery("SELECT * FROM BW_STOP ORDER BY BRAND", null);
        ArrayList<Chem> chemArray = new ArrayList<>();
        while(result.moveToNext()){
            Chem chem = processChemCursor(result);
            chemArray.add(chem);
        }
        return chemArray;
    }

    /**
     * Query database for all BW Fixers. From results, create Chem objects and add to an array of these Chem objects
     * @return
     */
    public ArrayList<Chem> getAllBW_Fix(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result = db.rawQuery("SELECT * FROM BW_FIX ORDER BY BRAND", null);
        ArrayList<Chem> chemArray = new ArrayList<>();
        while(result.moveToNext()){
            Chem chem = processChemCursor(result);
            chemArray.add(chem);
        }
        return chemArray;
    }

    /**
     * Query database for all Color Devs. From results, create Chem objects and add to an array of these Chem objects
     * @return
     */
    public ArrayList<Chem> getAllColor_Devs(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result = db.rawQuery("SELECT * FROM COLOR_DEVELOPER ORDER BY BRAND", null);
        ArrayList<Chem> chemArray = new ArrayList<>();
        while(result.moveToNext()){
            Chem chem = processChemCursor(result);
            chemArray.add(chem);
        }
        return chemArray;
    }

    /**
     * Query database for all Color Blix. From results, create Chem objects and add to an array of these Chem objects
     * @return
     */
    public ArrayList<Chem> getAllColor_Blix(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result = db.rawQuery("SELECT * FROM COLOR_BLIX ORDER BY BRAND", null);
        ArrayList<Chem> chemArray = new ArrayList<>();
        while(result.moveToNext()){
            Chem chem = processChemCursor(result);
            chemArray.add(chem);
        }
        return chemArray;
    }

    /**
     * Query database for all Color Stabilizers. From results, create Chem objects and add to an array of these Chem objects
     * @return
     */
    public ArrayList<Chem> getAllColor_Stab(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result = db.rawQuery("SELECT * FROM COLOR_STABILIZER ORDER BY BRAND", null);
        ArrayList<Chem> chemArray = new ArrayList<>();
        while(result.moveToNext()){
            Chem chem = processChemCursor(result);
            chemArray.add(chem);
        }
        return chemArray;
    }

    //TODO repeat above method for all bw and color chems

    /**
     * Takes a Cursor (result) from db query and creates and returns a Chem object based on that result
     * @param cursor
     * @return
     */
    public Chem processChemCursor(Cursor cursor){
        int chem_id = cursor.getInt(cursor.getInt(0));
        String chem_brand = cursor.getString(1);
        String chem_name = cursor.getString(2);

        Chem chem = new Chem(chem_id, chem_brand, chem_name);
        return chem;
    }

}