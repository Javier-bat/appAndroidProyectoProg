package com.example.juan_.meinteresa;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Telephony;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.juan_.meinteresa.constantes.Sentencias;
import com.example.juan_.meinteresa.MapsActivity;
import com.example.juan_.meinteresa.entidad.Ubicacion;

import java.util.ArrayList;

public class Editar extends AppCompatActivity {

    Spinner spiner;
    ArrayList<String> listaTitulos;
    ArrayList<Ubicacion> ubicaciones;
    ConexionSQLiteHelper conn;
    TextView textLong,textLat,textDesc,textFecha;
    EditText textTitulo,textEditTextDesc;
    Button borrar,editar;
    Button mapear;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        spiner = (Spinner) findViewById(R.id.spinner);
        textLong=(TextView) findViewById(R.id.textLong);
        textLat=(TextView) findViewById(R.id.textLat);
        textDesc=(TextView) findViewById(R.id.textDesc);
        textFecha=(TextView) findViewById(R.id.textFecha);
        borrar=(Button) findViewById(R.id.buttonEliminar);
        mapear = findViewById(R.id.buttonMapear);
        textTitulo = findViewById(R.id.editTextTitulo);
        textEditTextDesc=findViewById(R.id.editTextDesc);
        editar = findViewById(R.id.buttonEditar);

         conn = new ConexionSQLiteHelper(this,"db_ubicacion",null,1);


        consultarLista();
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listaTitulos);
        spiner.setAdapter(adaptador);
        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int posicion, long id) {
                if(posicion!=0) {
                    textLong.setText(Double.toString(ubicaciones.get(posicion - 1).getLongitud()));
                    textLat.setText(Double.toString(ubicaciones.get(posicion - 1).getLatitud()));
                    textEditTextDesc.setText(ubicaciones.get(posicion - 1).getDescripcion());
                    textFecha.setText(ubicaciones.get(posicion - 1).getFecha());
                    textTitulo.setText(ubicaciones.get(posicion-1).getTitulo());
                    mapear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent inten = new Intent(Editar.this,MapearActivity.class);

                            inten.putExtra("latitud",ubicaciones.get(posicion - 1).getLatitud());
                            inten.putExtra("longitud",ubicaciones.get(posicion - 1).getLongitud());
                            inten.putExtra("titulo",ubicaciones.get(posicion - 1).getTitulo());

                            startActivity(inten);

                            finish();
                        }
                    });
                    editar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(textTitulo==null || textTitulo.getText().toString().trim().isEmpty()){
                                Snackbar.make(view, "El Titulo es obligatorio", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            }else{
                            SQLiteDatabase dbM = conn.getWritableDatabase();


                            String[] parametroID = {ubicaciones.get(posicion-1).getId().toString()};
                            ContentValues values = new ContentValues();
                            values.put(Sentencias.campoTitulo,textTitulo.getText().toString());
                            values.put(Sentencias.campoDesc,textEditTextDesc.getText().toString());

                            dbM.update(Sentencias.tablaNombreUb,values,Sentencias.campoID+"=?",parametroID);
                            dbM.close();
                            Toast.makeText(getApplicationContext(),"Actualizado correctamente" ,Toast.LENGTH_SHORT).show();
                            Intent inten = new Intent(Editar.this,Editar.class);
                            startActivity(inten);
                            finish();
                        }}
                    });
                    borrar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String st =ubicaciones.get(posicion-1).getId().toString();

                            String[] parametro = {st};
                            db.delete(Sentencias.tablaNombreUb, Sentencias.campoID+"=?",parametro);
                            Toast.makeText(getApplicationContext(),"Eliminado correctamente" ,Toast.LENGTH_SHORT).show();
                            Intent inten = new Intent(Editar.this,Editar.class);
                            startActivity(inten);
                          finish();

                        }
                    });
                }else{
                    textLong.setText("");
                    textLat.setText("");
                    textEditTextDesc.setText("Descripcion ");
                    textFecha.setText("");
                    textTitulo.setText("Titulo");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void consultarLista() {
         db= conn.getReadableDatabase();
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
        obtenerLista();
    }
    private void obtenerLista(){
        listaTitulos = new ArrayList<String>();
        listaTitulos.add("Intereses ");

        for (int i=0;i<ubicaciones.size();i++){

            listaTitulos.add(ubicaciones.get(i).getTitulo());
        }

    }
}
