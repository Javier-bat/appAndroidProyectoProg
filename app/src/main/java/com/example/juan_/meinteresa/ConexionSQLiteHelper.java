package com.example.juan_.meinteresa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import com.example.juan_.meinteresa.constantes.sentencias;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {




    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(sentencias.crearTablaUbicacion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int nuevaVersion) {
        db.execSQL("DROP TABLE IF EXISTS ubicacion");
        onCreate(db);

    }
}
