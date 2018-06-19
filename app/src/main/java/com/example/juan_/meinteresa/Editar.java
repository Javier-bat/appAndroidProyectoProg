package com.example.juan_.meinteresa;

import android.annotation.SuppressLint;

import android.content.DialogInterface;
import android.content.Intent;

import android.database.sqlite.SQLiteDatabase;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;



import com.example.juan_.meinteresa.entidad.Ubicacion;
import com.example.juan_.meinteresa.DAO.UbicacionDAO;

import java.util.ArrayList;
//activity editar
public class Editar extends AppCompatActivity {

//creo los objetos que voy a usar, los creo aca para que puedan ser tomados por otros metodos
    ArrayList<String> listaTitulos;
    ArrayList<Ubicacion> ubicaciones;
    ConexionSQLiteHelper conn;
    TextView textLong,textLat,textDesc,textFecha;
    EditText textTitulo,textEditTextDesc;
    Button borrar,editar;
    Button mapear;
    ListView lv;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        //spiner = (Spinner) findViewById(R.id.spinner);
        //enlazo cada campo con su id
        textLong = (TextView) findViewById(R.id.textLong);
        textLat = (TextView) findViewById(R.id.textLat);
        textDesc = (TextView) findViewById(R.id.textDesc);
        textFecha = (TextView) findViewById(R.id.textFecha);
        borrar = (Button) findViewById(R.id.buttonEliminar);
        mapear = findViewById(R.id.buttonMapear);
        textTitulo = findViewById(R.id.editTextTitulo);
        textEditTextDesc = findViewById(R.id.editTextDesc);
        editar = findViewById(R.id.buttonEditar);
        lv = findViewById(R.id.listView);
//conecto la base de datos
        conn = new ConexionSQLiteHelper(this, "db_ubicacion", null, 1);


       ubicaciones=UbicacionDAO.consultarLista(ubicaciones,getApplicationContext()); //consulto lista de puntos
        listaTitulos=UbicacionDAO.obtenerLista(ubicaciones,listaTitulos);
        //creo una adapter y le paso la lista de titulos (arraylist(
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaTitulos);


        lv.setAdapter(adaptador); //le seteo el adaptador al listview

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int posicion, long id) { //listener del listview (si apreto algun item)


                        //la variable posicion muestra la posicion del click
                //seteo cada campo con la posicion del mismo objeto
                    textLong.setText(Double.toString(ubicaciones.get(posicion).getLongitud()));
                    textLat.setText(Double.toString(ubicaciones.get(posicion).getLatitud()));
                    textEditTextDesc.setText(ubicaciones.get(posicion ).getDescripcion());
                    textFecha.setText(ubicaciones.get(posicion).getFecha());
                    textTitulo.setText(ubicaciones.get(posicion).getTitulo());
                    mapear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) { //si apreto mapear
                            Intent inten = new Intent(Editar.this,MapearActivity.class); //creo un intent

                            inten.putExtra("latitud",ubicaciones.get(posicion).getLatitud()); //le paso unos parametros extras necesarios
                            inten.putExtra("longitud",ubicaciones.get(posicion).getLongitud());
                            inten.putExtra("titulo",ubicaciones.get(posicion).getTitulo());

                            startActivity(inten); //inicio la activity

                            finish(); //cierro la activity anterior
                        }
                    });
                    editar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) { //si aprieto editar

                            if(textTitulo==null || textTitulo.getText().toString().trim().isEmpty()){
                                Snackbar.make(view, "El Titulo es obligatorio", Snackbar.LENGTH_LONG) //si el titulo esta vacio devuelvo mensaje
                                        .setAction("Action", null).show();

                            }else{

                             int up = UbicacionDAO.editar( ubicaciones, posicion, textTitulo, textEditTextDesc, getApplicationContext()); //sino llamo a DAO editar

                                if(up!=-1){Toast.makeText(getApplicationContext(),"Actualizado correctamente" ,Toast.LENGTH_SHORT).show();}
                                else{Toast.makeText(getApplicationContext(),"Hubo un error al actualizar los datos" ,Toast.LENGTH_SHORT).show();

                                }

                                Intent inten = new Intent(Editar.this,Editar.class);
                                startActivity(inten);
                                finish();
                            }}
                    });
                    borrar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) { //si aprieto borrar
                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Editar.this);
                            dialogo1.setTitle("Importante");  //pregunto si realmente quiere borrar
                            dialogo1.setMessage("¿ Esta seguro que desea eliminar " + ubicaciones.get(posicion).getTitulo() + "?");
                            dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int j) { //si confirma

                                   int up= UbicacionDAO.borrar( ubicaciones, posicion, getApplicationContext());
                                    if (up != -1) {
                                        Toast.makeText(getApplicationContext(), "Eliminado correctamente", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Hubo un error al borrar los datos", Toast.LENGTH_SHORT).show();

                                    }


                                    Intent inten = new Intent(Editar.this, Editar.class);
                                    startActivity(inten);
                                    finish();
                                }
                            });
                            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int j) {
 //si no confirma no hacer nada
                                }
                            });
                            dialogo1.show();

                        }

                    });

            }
        });


    }


}
