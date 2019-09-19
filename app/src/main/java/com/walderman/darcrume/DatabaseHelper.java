package com.walderman.darcrume;
//safe to delete?
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
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

    //TABLE_FILMS columns
    private static final String filmBRAND = "BRAND";
    private static final String filmNAME = "NAME";
    private static final String filmBW_COLOR = "BW_COLOR";
    private static final String filmISO = "ISO";
    private static final String filmEXP = "EXPOSURES";

    /**
     * Create tables
     */
    private static final String createTABLE_FILMS           = "CREATE TABLE FILMS(FILM_ID INTEGER PRIMARY KEY AUTOINCREMENT, BRAND TEXT, NAME TEXT, BW_COLOR TEXT, ISO INTEGER, EXPOSURES INTEGER)";
    private static final String createTABLE_CHEMS           = "CREATE TABLE CHEMS(CHEM_ID INTEGER PRIMARY KEY AUTOINCREMENT, BRAND TEXT, NAME TEXT, BW_COLOR TEXT, CHEM_ROLE TEXT)";

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
        db.execSQL(createTABLE_CHEMS);
        db.execSQL(createTABLE_SESSION_HISTORY);
        db.execSQL(createTABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop older tables on upgrade
        db.execSQL("DROP TABLE IF EXISTS FILMS");
        db.execSQL("DROP TABLE IF EXISTS CHEMS");
        db.execSQL("DROP TABLE IF EXISTS RECIPE");
        db.execSQL("DROP TABLE IF EXISTS SESSION_HISTORY");
        db.execSQL("DROP TABLE IF EXISTS NOTES");

        //now create new tables
        onCreate(db);
    }


    /**
     *           _______  __   __      .___  ___.      _______.
     *          |   ____||  | |  |     |   \/   |     /       |
     *          |  |__   |  | |  |     |  \  /  |    |   (----`
     *          |   __|  |  | |  |     |  |\/|  |     \   \
     *          |  |     |  | |  `----.|  |  |  | .----)   |
     *          |__|     |__| |_______||__|  |__| |_______/
     *
     */

    public Boolean insertNewFilm(Film newFilm){

        //try to insert a new film into database
        try {
            //get instance of database
            SQLiteDatabase db = this.getWritableDatabase();

            //prepare ContentValues to insert into database
            ContentValues cv = new ContentValues();
            cv.put(filmBRAND, newFilm.getBrand());
            cv.put(filmNAME, newFilm.getName());
            cv.put(filmBW_COLOR, newFilm.getType());
            cv.put(filmISO, newFilm.getIso());
            cv.put(filmEXP, newFilm.getExp());

            //insert new film into database
            db.insert("FILMS", null, cv);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertNewFilm(String brand, String name, String type, int iso, int exp) {
        try {
            //get instance of database
            SQLiteDatabase db = this.getWritableDatabase();

            //prepare ContentValues to insert into database
            ContentValues cv = new ContentValues();
            cv.put(filmBRAND, brand);
            cv.put(filmNAME, name);
            cv.put(filmBW_COLOR, type);
            cv.put(filmISO, iso);
            cv.put(filmEXP, exp);

            //insert new film into database
            db.insert("FILMS", null, cv);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    /**
     * Query database for all films. From results, create film objects and add to an array of these film objects
     * @return
     */
    public ArrayList<Film> getAllFilms(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result = db.rawQuery("select * from FILMS Order by upper(BRAND)", null);
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
        int film_id = cursor.getInt(cursor.getColumnIndex("FILM_ID"));
        String brand = cursor.getString(cursor.getColumnIndex("BRAND"));
        String name = cursor.getString(cursor.getColumnIndex("NAME"));
        String bw_color = cursor.getString(cursor.getColumnIndex("BW_COLOR"));
        int iso = cursor.getInt(cursor.getColumnIndex("ISO"));
        int exp = cursor.getInt(cursor.getColumnIndex("EXPOSURES"));

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
 *                    ______  __    __   _______ .___  ___.      _______.
 *                   /      ||  |  |  | |   ____||   \/   |     /       |
 *                  |  ,----'|  |__|  | |  |__   |  \  /  |    |   (----`
 *                  |  |     |   __   | |   __|  |  |\/|  |     \   \
 *                  |  `----.|  |  |  | |  |____ |  |  |  | .----)   |
 *                   \______||__|  |__| |_______||__|  |__| |_______/
 *
 */

    /**
     * Take an argument specifying the type of chemical
     * @return
     */
    public ArrayList<Chem> getAllChems(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM CHEMS WHERE CHEM_ID > 0 ORDER BY UPPER(BRAND)";
        Cursor result = db.rawQuery(query, null);
        ArrayList<Chem> chemArray = new ArrayList<>();
        while(result.moveToNext()){
            Chem chem = processChemCursor(result);
            chemArray.add(chem);
        }
        return chemArray;
    }

    /**
     * Takes a Cursor (result) from db query and creates and returns a Chem object based on that result
     * @param cursor
     * @return
     */
    public Chem processChemCursor(Cursor cursor){
        int chemId = cursor.getInt(cursor.getColumnIndex("CHEM_ID"));
        String brand = cursor.getString(cursor.getColumnIndex("BRAND"));
        String name = cursor.getString(cursor.getColumnIndex("NAME"));
        String bw_Color = cursor.getString(cursor.getColumnIndex("BW_COLOR"));
        String chemRole = cursor.getString(cursor.getColumnIndex("CHEM_ROLE"));

        Chem chem = new Chem(chemId, brand, name, bw_Color, chemRole);
        return chem;
    }

    /**
     * Pass in arguments & Insert new chem record into database
     * @param brand
     * @param name
     * @param bw_color
     * @param chemRole
     * @return
     */
    public Boolean insertNewChem(String brand, String name, String bw_color, String chemRole){

        //try to insert a new film into database
        try {
            //get instance of database
            SQLiteDatabase db = this.getWritableDatabase();

            //prepare ContentValues to insert into database
            ContentValues cv = new ContentValues();
            cv.put("BRAND", brand);
            cv.put("NAME", name);
            cv.put("BW_COLOR", bw_color);
            cv.put("CHEM_ROLE", chemRole);

            //insert new film into database
            db.insert("CHEMS", null, cv);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean insertNewChem(Chem newChem){

        //try to insert a new film into database
        try {
            //get instance of database
            SQLiteDatabase db = this.getWritableDatabase();

            //prepare ContentValues to insert into database
            ContentValues cv = new ContentValues();
            cv.put("BRAND", newChem.getBrand());
            cv.put("NAME", newChem.getName());
            cv.put("BW_COLOR", newChem.getBw_Color());
            cv.put("CHEM_ROLE", newChem.getChemRole());

            //insert new film into database
            db.insert("CHEMS", null, cv);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public Chem updateChem(Chem chem) {
        int chemId = chem.getChemId();
        String brand = chem.getBrand();
        String name = chem.getName();
        String bw_Color = chem.getBw_Color();
        String chemRole = chem.getChemRole();
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE CHEMS SET (BRAND, NAME, BW_COLOR, CHEM_ROLE) = ('" + brand + "','" + name + "','" + bw_Color + "','" + chemRole + "') WHERE CHEM_ID = " + chemId);

        Chem updatedChem = selectChem(chemId);
        return updatedChem;
    }

    public Chem selectChem(int chemId){
        SQLiteDatabase db = this.getWritableDatabase();
        Chem chem = new Chem();
        Cursor result = db.rawQuery("SELECT * FROM CHEMS WHERE CHEM_ID = " + chemId, null);
        while(result.moveToNext()){
            chem = processChemCursor(result);
        }
        return chem;
    }

    public void truncateChemsTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from CHEMS");
        //Delete the sequence for TABLE_FILMS which will reset primary key.
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE name='CHEMS'");
    }

    public void resetTables(){
        truncateChemsTable();
        //this section should be removed / commented out. using to populate w/ several chems for testing purposes
        insertNewChem("Arista","Color Dev","Color","CLRDEV");
        insertNewChem("Arista","Color Blix","Color","CLRBLX");
        insertNewChem("Arista","Color Stabilizer","Color","CLRSTB");
        insertNewChem("Kodak","BW Dev","BW","BWDEV");
        insertNewChem("Kodak","BW Stop","BW","BWSTP");
        insertNewChem("Kodak","BW Fix","BW","BWFIX");
        insertNewChem("Kodak","Color Dev","Color","CLRDEV");
        insertNewChem("Kodak","Color Blix","Color","CLRBLX");
        insertNewChem("Kodak","Color Stabilizer","Color","CLRSTB");
        insertNewChem("Ilford","BW Dev","BW","BWDEV");
        insertNewChem("Ilford","BW Stop","BW","BWSTP");
        insertNewChem("Ilford","BW Fix","BW","BWFIX");

        truncateFilmsTable();
        insertNewFilm("Kodak", "Ektar", "Color", 100, 36);
        insertNewFilm("Kodak", "Portra", "Color", 400, 36);
        insertNewFilm("Fujifilm", "Fujicolor Superia", "Color", 1600, 24);
        insertNewFilm("Kodak", "Gold", "Color", 200, 36);
        insertNewFilm("Agfa", "Vista", "Color", 400, 36);
        insertNewFilm("Ilford", "HP5 Plus", "BW", 400, 36);
        insertNewFilm("Ilford", "Delta", "BW", 3200, 36);
        insertNewFilm("Kodak", "TMAX", "BW", 800, 36);
        insertNewFilm("Fuji", "Neopan ACROS", "BW", 100, 36);
        insertNewFilm("Ilford", "Delta", "BW", 400, 36);
    }
}