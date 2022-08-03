package com.cmontero.tallerdpmjava.usuarios;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.cmontero.tallerdpmjava.utils.Constantes;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper mInstance;

    public DBHelper(Context context) {
        super(context, Constantes.DB_NAME, null, Constantes.DB_VERSION);
    }

    public static DBHelper getInstance(Context ctx){
        if(mInstance == null){
            mInstance = new DBHelper(ctx);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + Constantes.NOMBRETABLA +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nombres TEXT NOT NULL," +
                " email TEXT NOT NULL," +
                " fechaNac TEXT NOT NULL," +
                " sexo TEXT NOT NULL)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
