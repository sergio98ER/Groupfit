package controladores;

public class Mensaje {
    private String titulo, mensaje;

    public Mensaje(){
        titulo = "";
        mensaje = "";
    }
    public Mensaje(String titulo, String mensaje) {
        this.titulo = titulo;
        this.mensaje = mensaje;

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
