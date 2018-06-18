package com.example.juan_.meinteresa.constantes;

public class sentencias {

    //campos creacion:
    public static final String tablaNombreUb ="ubicacion";
    public static final String campoID ="id";
    public static final String campoLatitud ="latitud";
    public static final String campoLongitud ="longitud";
    public static final String campoFecha ="fecha";
    public static final String campoDesc ="desc";
    public static final String campoTitulo ="titulo";


    public static final String crearTablaUbicacion="CREATE TABLE "+tablaNombreUb+" (\n" +
            "    "+campoID+"       INTEGER PRIMARY KEY\n" +
            "                     NOT NULL\n" +
            "                     UNIQUE,\n" +
            "    "+campoLatitud+"  DOUBLE  NOT NULL,\n" +
            "    "+campoLongitud+" DOUBLE  NOT NULL,\n" +
            "    "+campoFecha+"    DATE,\n" +
            "    "+campoTitulo+"    TEXT  NOT NULL,\n" +
            "    "+campoDesc+"   TEXT\n" +
            ");\n";



}
