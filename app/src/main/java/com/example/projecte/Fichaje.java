package com.example.projecte;

public class Fichaje {

    public String fecha;
    public String hora;
    public boolean tipomarcaje;
    public String tipoincidencia;

    public Fichaje(){

    }

    public Fichaje(String fecha, String hora, boolean tipomarcaje, String tipoincidencia) {
        this.fecha = fecha;
        this.hora = hora;
        this.tipomarcaje = tipomarcaje;
        this.tipoincidencia = tipoincidencia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public boolean isTipomarcaje() {
        return tipomarcaje;
    }

    public void setTipomarcaje(boolean tipomarcaje) {
        this.tipomarcaje = tipomarcaje;
    }

    public void setTipoincidencia(String tipoincidencia) {
        this.tipoincidencia = tipoincidencia;
    }

    public String getTipoincidencia() {
        return tipoincidencia;
    }
}
