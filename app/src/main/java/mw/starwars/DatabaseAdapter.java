package mw.starwars;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by mstowska on 7/11/2016.
 */
public class DatabaseAdapter {
    public static final String DEBUG_TAG = "SqLiteTodoManager";

    public static final int DB_VERSION = 2;
    public static final String DB_NAME = "database.db";
    public static final String DB_TODO_TABLE = "starwars";

    public static final String KEY_ID = "_id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID_COLUMN = 0;

    public static final String KEY_NAME = "name";
    public static final String NAME_OPTIONS = "TEXT NOT NULL UNIQUE";
    public static final int NAME_COLUMN = 1;

    public static final String KEY_ID_SWAPI = "id_swapi";
    public static final String ID_SWAPI_OPTIONS = "INTEGER NULL";
    public static final int ID_SWAPI_COLUMN = 2;

    public static final String KEY_DESCRIPTION = "description";
    public static final String DESCRIPTION_OPTIONS = "TEXT NOT NULL";
    public static final int DESCRIPTION_COLUMN = 3;


    public static final String DB_CREATE_TODO_TABLE =
            "CREATE TABLE " + DB_TODO_TABLE + "( " +
                    KEY_ID + " " + ID_OPTIONS + ", " +
                    KEY_NAME + " " + NAME_OPTIONS + ", " +
                    KEY_ID_SWAPI + " " + ID_SWAPI_OPTIONS + ", " +
                    KEY_DESCRIPTION + " " + DESCRIPTION_OPTIONS +
                    ");";

    public static final String DROP_TODO_TABLE =
            "DROP TABLE IF EXISTS " + DB_TODO_TABLE;

    public static final String SELECT_TODO_TABLE =
            "SELECT * FROM " + DB_TODO_TABLE;



    private SQLiteDatabase database;
    private Context context;
    private DatabaseHelper dbHelper;



    public DatabaseAdapter(Context context) {
        this.context = context;
    }

    public DatabaseAdapter open(){
        dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    //dodanie nowej krotki do tabeli podroze
    public long wstawKrotkeDoTabeli(String name, int id_swapi, String description) {
        ContentValues nowaKrotka = new ContentValues();    //stworzenie obiektu do przekazania danych do zapytania
        nowaKrotka.put(KEY_NAME, name);       //metoda put- umieszcza się pare klucz-opis, gdzie klucz to nazwa kolumny
        nowaKrotka.put(KEY_ID_SWAPI, id_swapi);
        nowaKrotka.put(KEY_DESCRIPTION, description);

        return database.insert(DB_TODO_TABLE, null, nowaKrotka);
    }

    public String[] wypisanieWszystkichKolumnTabeli() {
        String[] krotki = dbHelper.dostanieWszystkichKolumnTabeli(database);
        return krotki;
    }

}
