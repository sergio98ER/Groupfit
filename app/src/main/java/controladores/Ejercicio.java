package controladores;

public class Ejercicio {
    private String rutina, musculo, nombre_ejercicio, series, repeticiones, peso_inicio, peso_fin;

    public Ejercicio(){

    }

    public Ejercicio(String rutina, String musculo, String nombre_ejercicio, String series, String repeticiones, String peso_inicio, String peso_fin) {
        this.rutina = rutina;
        this.musculo = musculo;
        this.nombre_ejercicio = nombre_ejercicio;
        this.series = series;
        this.repeticiones = repeticiones;
        this.peso_inicio = peso_inicio;
        this.peso_fin = peso_fin;
    }

    public String getRutina() {
        return rutina;
    }

    public void setRutina(String rutina) {
        this.rutina = rutina;
    }

    public String getMusculo() {
        return musculo;
    }

    public void setMusculo(String musculo) {
        this.musculo = musculo;
    }

    public String getNombre_ejercicio() {
        return nombre_ejercicio;
    }

    public void setNombre_ejercicio(String nombre_ejercicio) {
        this.nombre_ejercicio = nombre_ejercicio;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getPeso_inicio() {
        return peso_inicio;
    }

    public void setPeso_inicio(String peso_inicio) {
        this.peso_inicio = peso_inicio;
    }

    public String getPeso_fin() {
        return peso_fin;
    }

    public void setPeso_fin(String peso_fin) {
        this.peso_fin = peso_fin;
    }

    @Override
    public String toString() {
        return "Ejercicio{" +
                "rutina='" + rutina + '\'' +
                ", musculo='" + musculo + '\'' +
                ", nombre_ejercicio='" + nombre_ejercicio + '\'' +
                ", series='" + series + '\'' +
                ", repeticiones='" + repeticiones + '\'' +
                ", peso_inicio='" + peso_inicio + '\'' +
                ", peso_fin='" + peso_fin + '\'' +
                '}';
    }
}
