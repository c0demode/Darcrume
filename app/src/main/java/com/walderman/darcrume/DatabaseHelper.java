package com.walderman.darcrume;
//safe to delete?
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ImageView;


import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database name & Version
    private static final String DATABASE_NAME = "darcrumeManager";
    private static final int DATABASE_VERSION = 1;

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

    //TABLE_BW_DEVELOPER columns
    private static final String bw_DEV_ID = "BW_DEV_ID";
    private static final String bw_DEV_BRAND = "BRAND";
    private static final String bw_DEV_NAME = "NAME";

    //TABLE_BW_STOP columns
    private static final String bw_STOP_ID = "BW_STOP_ID";
    private static final String bw_STOP_BRAND = "BRAND";
    private static final String bw_STOP_NAME = "NAME";

    //TABLE_BW_FIX columns
    private static final String bw_FIX_ID = "BW_FIX_ID";
    private static final String bw_FIX_BRAND = "BRAND";
    private static final String bw_FIX_NAME = "NAME";

    //TABLE_COLOR_DEVELOPER columns
    private static final String color_DEV_ID = "COLOR_DEV_ID";
    private static final String color_DEV_BRAND = "BRAND";
    private static final String color_DEV_NAME = "NAME";

    //TABLE_COLOR_BLIX columns
    private static final String color_BLIX_ID = "COLOR_BLIX_ID";
    private static final String color_BLIX_BRAND = "BRAND";
    private static final String color_BLIX_NAME = "NAME";

    //TABLE_COLOR_STABILIZER columns
    private static final String color_STAB_ID = "COLOR_STAB_ID";
    private static final String color_STAB_BRAND = "BRAND";
    private static final String color_STAB_NAME = "NAME";

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

    /**
     * Create tables
     */
    //TABLE_FILMS statement
    private static final String createTABLE_FILMS =
            "CREATE TABLE " + TABLE_FILMS + " (" + filmFILM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + filmBRAND + " TEXT, " + filmNAME + " TEXT, " + filmBW_COLOR + " TEXT, " + filmISO + " INTEGER, " + filmEXP + " INTEGER)";

    //TABLE_BW_DEVELOPER statement
    private static final String createTABLE_BW_DEVELOPER =
            "CREATE TABLE " + TABLE_BW_DEVELOPER + " (" + bw_DEV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + bw_DEV_BRAND + " TEXT, " + bw_DEV_NAME + " TEXT)";
    //TABLE_BW_STOP columns statement
    private static final String createTABLE_BW_STOP =
            "CREATE TABLE " + TABLE_BW_STOP + " (" + bw_STOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + bw_STOP_BRAND + " TEXT, " + bw_STOP_NAME + " TEXT)";
    //TABLE_BW_FIX statement
    private static final String createTABLE_BW_FIX =
            "CREATE TABLE " + TABLE_BW_FIX + " (" + bw_FIX_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + bw_FIX_BRAND + " TEXT, " + bw_FIX_NAME + " TEXT)";

    //TABLE_COLOR_DEVELOPER statement
    private static final String createTABLE_COLOR_DEVELOPER =
            "CREATE TABLE " + TABLE_COLOR_DEVELOPER + " (" + color_DEV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + color_DEV_BRAND + " TEXT, " + color_DEV_NAME + " TEXT)";
    //TABLE_COLOR_BLIX statement
    private static final String createTABLE_COLOR_BLIX =
            "CREATE TABLE " + TABLE_COLOR_BLIX + " (" + color_BLIX_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + color_BLIX_BRAND + " TEXT, " + color_BLIX_NAME + " TEXT)";
    //TABLE_COLOR_STABILIZER statement
    private static final String createTABLE_COLOR_STABILIZER =
            "CREATE TABLE " + TABLE_COLOR_STABILIZER + " (" + color_STAB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + color_STAB_BRAND + " TEXT, " + color_STAB_NAME + " TEXT)";

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

        db.execSQL(createTABLE_BW_DEVELOPER);
        db.execSQL(createTABLE_BW_STOP);
        db.execSQL(createTABLE_BW_FIX);

        db.execSQL(createTABLE_COLOR_DEVELOPER);
        db.execSQL(createTABLE_COLOR_BLIX);
        db.execSQL(createTABLE_COLOR_STABILIZER);

        db.execSQL(createTABLE_RECIPE);
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

        Cursor result = db.rawQuery("select * from " + TABLE_FILMS + " Order by " + filmBRAND, null);
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
        Cursor result = db.rawQuery("select * from " + TABLE_FILMS + " where " + filmFILM_ID + " = " + film_Id, null);
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
        db.execSQL("Delete from " + TABLE_FILMS);
        //reset auto increment
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE name='" + TABLE_FILMS + "\'");
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

        db.execSQL("update " + TABLE_FILMS + " set " +
                "(" + filmBRAND + "," + filmNAME + "," + filmISO + "," + filmEXP + "," + filmBW_COLOR + ") " +
                "= ('" + brand + "','" + name + "','" + iso + "','" + exp + "','" + type + "') " +
                "where " + filmFILM_ID + " = " + filmId);

        Film updatedFilm = selectFilm(filmId);
        return updatedFilm;
    }


    /**
     * Query database for all BW Developers. From results, create Chem objects and add to an array of these Chem objects
     * @return
     */
    public ArrayList<Chem> getAllBW_Devs(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result = db.rawQuery("select * from " + TABLE_BW_DEVELOPER + " Order by " + bw_DEV_BRAND, null);
        ArrayList<Chem> bwDevArray = new ArrayList<>();
        while(result.moveToNext()){
            Chem bwDev = processChemCursor(result);
            bwDevArray.add(bwDev);
        }
        return bwDevArray;
    }
    //TODO repeat above method for all bw and color chems

    /**
     * Takes a Cursor (result) from db query and creates and returns a Chem object based on that result
     * @param cursor
     * @return
     */
    public Chem processChemCursor(Cursor cursor){
        int chem_id = cursor.getInt(cursor.getInt(0));
        String chem_brand = cursor.getString(cursor.getColumnIndex(filmBRAND));
        String chem_name = cursor.getString(cursor.getColumnIndex(filmNAME));
        String bw_color = cursor.getString(cursor.getColumnIndex(filmBW_COLOR));
        int iso = cursor.getInt(cursor.getColumnIndex(filmISO));
        int exp = cursor.getInt(cursor.getColumnIndex(filmEXP));

        Chem chem = new Chem(chem_id, chem_brand, chem_name);
        return chem;
    }

}