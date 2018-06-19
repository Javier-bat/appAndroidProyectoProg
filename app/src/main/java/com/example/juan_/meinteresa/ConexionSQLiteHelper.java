package com.example.juan_.meinteresa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.juan_.meinteresa.constantes.Sentencias;

//mi sqlitehelper asiste en la conexion con la base de datos
public class ConexionSQLiteHelper extends SQLiteOpenHelper {



//conecta con la base de datos
    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(Sentencias.crearTablaUbicacion); //si no existe crea una
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int nuevaVersion) { //si hay una version nueva borra la tabla anterior y crea una nueva
        db.execSQL("DROP TABLE IF EXISTS ubicacion");
        onCreate(db);

    }
}
