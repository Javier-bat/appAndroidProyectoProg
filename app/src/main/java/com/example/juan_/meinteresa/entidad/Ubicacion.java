package com.example.juan_.meinteresa.entidad;
//este es mi unico modelo
public class Ubicacion {
    //argumentos
    private Integer id;
    private double latitud;
    private double longitud;
    private String descripcion;
    private String fecha;
    private String titulo;

    //constructor lleno
    public Ubicacion(Integer id, double latitud, double longitud, String descripcion, String fecha,String titulo) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.titulo=titulo;
    }
//constructor vacio
    public Ubicacion() {

    }
//getter y setter
    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

