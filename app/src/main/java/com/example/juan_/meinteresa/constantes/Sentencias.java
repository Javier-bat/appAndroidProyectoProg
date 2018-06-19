package com.example.juan_.meinteresa.constantes;
//La clase sentencias la hice con el proposito de
//no equivocarme con los nombres de la base de datos y ahorrar error
public class Sentencias {

    //campos creacion:
    public static final String tablaNombreUb ="ubicacion";  //nombre de la tabla
    public static final String campoID ="id";   //nombre del campo id
    public static final String campoLatitud ="latitud";    //nombre del campo latitud
    public static final String campoLongitud ="longitud";   //nombre del campo longitud
    public static final String campoFecha ="fecha";         //nombre del campo fecha
    public static final String campoDesc ="desc";           //nombre del campo descricpcion
    public static final String campoTitulo ="titulo";           //nombre del campo titulo


    public static final String crearTablaUbicacion="CREATE TABLE "+tablaNombreUb+" (\n" +  //crear tabla
            "    "+campoID+"       INTEGER PRIMARY KEY\n" +
            "                     NOT NULL\n" +
            "                     UNIQUE,\n" +
            "    "+campoLatitud+"  DOUBLE  NOT NULL,\n" +
            "    "+campoLongitud+" DOUBLE  NOT NULL,\n" +
            "    "+campoFecha+"    TEXT,\n" +
            "    "+campoTitulo+"    TEXT  NOT NULL,\n" +
            "    "+campoDesc+"   TEXT\n" +
            ");\n";



}
