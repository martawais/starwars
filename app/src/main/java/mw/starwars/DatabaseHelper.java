package mw.starwars;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mstowska on 7/11/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private Cursor cursor;


    public DatabaseHelper(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(DatabaseAdapter.DEBUG_TAG, DatabaseAdapter.DB_CREATE_TODO_TABLE);
        db.execSQL(DatabaseAdapter.DB_CREATE_TODO_TABLE);

        Log.d(DatabaseAdapter.DEBUG_TAG, "Database creating...");
        Log.d(DatabaseAdapter.DEBUG_TAG, "Table " + DatabaseAdapter.DB_TODO_TABLE + " ver." + DatabaseAdapter.DB_VERSION + " created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseAdapter.DROP_TODO_TABLE);

        Log.d(DatabaseAdapter.DEBUG_TAG, "Database updating...");
        Log.d(DatabaseAdapter.DEBUG_TAG, "Table " + DatabaseAdapter.DB_TODO_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
        Log.d(DatabaseAdapter.DEBUG_TAG, "All data is lost.");

        onCreate(db);
    }


    public void selectTable(SQLiteDatabase db) {
        cursor = db.rawQuery(DatabaseAdapter.DB_TODO_TABLE, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                String string1 = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_ID));
                String string2 = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_NAME));
                String string3 = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_ID_SWAPI));
                String string4 = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_DESCRIPTION));
                Log.d(DatabaseAdapter.DB_TODO_TABLE, string1 + "," + string2 + "," + string3 + "," + string4); // Only assign string value if we moved to first record
                cursor.moveToNext();
            }
        }
    }



    public String[] dostanieWszystkichKolumnTabeli(SQLiteDatabase db) {
        String[] columns = {DatabaseAdapter.KEY_ID, DatabaseAdapter.KEY_DESCRIPTION};
        cursor = db.query(DatabaseAdapter.DB_TODO_TABLE, columns, null, null, null, null, null);
        int iloscKrotekPodroze = cursor.getCount();
        String[] krotki = new String[iloscKrotekPodroze];
        if (cursor.moveToFirst()) {
            int i = 0;
            while (cursor.isAfterLast() == false) {
                krotki[i] = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_ID)) + ".\n" +
                        cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_DESCRIPTION));
                cursor.moveToNext();
                i++;
            }
        }
        return krotki;
    }




}