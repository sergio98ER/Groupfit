package controladores;

public class Grupo {
    private String nombre, objetivo;
    private int id_entrenador;

    public Grupo() {
    }

    public Grupo(String nombre, String objetivo,  int id_entrenador) {
        this.nombre = nombre;
        this.objetivo = objetivo;
        this.id_entrenador = id_entrenador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public int getId_entrenador() {
        return id_entrenador;
    }

    public void setId_entrenador(int id_entrenador) {
        this.id_entrenador = id_entrenador;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + objetivo + '\'' +
                ", id_entrenador=" + id_entrenador +
                '}';
    }
}

