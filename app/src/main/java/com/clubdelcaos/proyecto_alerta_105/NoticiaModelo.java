package com.clubdelcaos.proyecto_alerta_105;

/**
 * Created by Joel-64 on 07/11/2017.
 */

public class NoticiaModelo {
    private String fecha,encabezado,descripcion;
    private int imagen;
    public NoticiaModelo() {
    }

    public NoticiaModelo(String fecha, String encabezado, String descripcion, int imagen) {
        this.fecha = fecha;
        this.encabezado = encabezado;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public String getFecha() {
        return fecha;
    }

    public String getEncabezado() {
        return encabezado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
