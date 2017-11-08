package com.clubdelcaos.proyecto_alerta_105;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Joel-64 on 05/10/2017.
 */

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    String cadenaSQL = "CREATE TABLE usuario (idSesion INTEGER PRIMARY KEY,idUsuario VARCHAR(11),usuario VARCHAR(50),clave VARCHAR(50),correo VARCHAR(50),nivel VARCHAR(3),estado VARCHAR(3))";
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(cadenaSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL(cadenaSQL);

    }
}
