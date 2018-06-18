package com.example.juan_.meinteresa.entidad;
import  java.sql.Date;
public class Ubicacion {
    private Integer id;
    private double latitud;
    private double longitud;
    private String descripcion;
    private Date fecha;
    private String titulo;

    public Ubicacion(Integer id, double latitud, double longitud, String descripcion, Date fecha,String titulo) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.titulo=titulo;
    }

    public Ubicacion() {

    }

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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}

