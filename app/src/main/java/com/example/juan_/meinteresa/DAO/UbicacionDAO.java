package com.example.juan_.meinteresa.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

import com.example.juan_.meinteresa.ConexionSQLiteHelper;
import com.example.juan_.meinteresa.MainActivity;
import com.example.juan_.meinteresa.constantes.Sentencias;
import com.example.juan_.meinteresa.entidad.Ubicacion;

import java.util.ArrayList;

public class UbicacionDAO {


 //Data access object

    public static int editar(ArrayList<Ubicacion> ubicaciones, int posicion, EditText textTitulo, EditText textEditTextDesc, Context context){
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(context, "db_ubicacion", null, 1);
        SQLiteDatabase dbM = conn.getWritableDatabase();


        String[] parametroID = {ubicaciones.get(posicion).getId().toString()};
        ContentValues values = new ContentValues();
        values.put(Sentencias.campoTitulo,textTitulo.getText().toString());
        values.put(Sentencias.campoDesc,textEditTextDesc.getText().toString());

        int up= dbM.update(Sentencias.tablaNombreUb,values,Sentencias.campoID+"=?",parametroID);
        dbM.close();
        return up;
    }




    public static int borrar(ArrayList<Ubicacion> ubicaciones, int posicion, Context context){

        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(context, "db_ubicacion", null, 1);
        SQLiteDatabase dbM = conn.getWritableDatabase();
        String st = ubicaciones.get(posicion).getId().toString();

        String[] parametro = {st};
        int up = dbM.delete(Sentencias.tablaNombreUb, Sentencias.campoID + "=?", parametro);
        dbM.close();
        return up;
    }





    public static Long ingresar(Context context, double longitude,double latitude,EditText campoDesc,EditText campoTitulo){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(context,"db_ubicacion",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Sentencias.campoDesc,campoDesc.getText().toString());
        cv.put(Sentencias.campoLatitud,latitude);
        cv.put(Sentencias.campoLongitud,longitude);
        cv.put(Sentencias.campoTitulo,campoTitulo.getText().toString());
        cv.put(Sentencias.campoFecha, MainActivity.setFechaActual());
        Long idResult=db.insert(Sentencias.tablaNombreUb, Sentencias.campoID,cv);
        db.close();
        return idResult;
    }

    public static ArrayList<Ubicacion> consultarLista(ArrayList<Ubicacion> ubicaciones,Context context) {
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(context, "db_ubicacion", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        Ubicacion ubicacion = null;

        ubicaciones=new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Sentencias.tablaNombreUb,null);


        while(cursor.moveToNext()){
            ubicacion = new Ubicacion();
            ubicacion.setId(cursor.getInt(0));
            ubicacion.setLatitud(cursor.getDouble(1));
            ubicacion.setLongitud(cursor.getDouble(2));
            ubicacion.setTitulo(cursor.getString(4));
            ubicacion.setDescripcion(cursor.getString(5));
            ubicacion.setFecha(cursor.getString(3));

            ubicaciones.add(ubicacion);
        }

    return ubicaciones;
    }


    public static ArrayList<String> obtenerLista(ArrayList<Ubicacion> ubicaciones,ArrayList<String> listaTitulos){
        listaTitulos = new ArrayList<String>();


        for (int i=0;i<ubicaciones.size();i++){

            listaTitulos.add(ubicaciones.get(i).getTitulo());
        }
return listaTitulos;
    }
}
