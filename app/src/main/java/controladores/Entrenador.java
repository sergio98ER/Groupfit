package controladores;

public class Entrenador {

    private String nombre, apellidos, imagen, token, email, contrasena, telefono, descripcion;

    public Entrenador() {

    }

    public Entrenador(String nombre, String apellidos, String imagen, String token, String email, String contrasena, String telefono, String descripcion) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.imagen = imagen;
        this.token = token;
        this.email = email;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    @Override
    public String toString() {
        return "Entrenador{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", imagen='" + imagen + '\'' +
                ", token='" + token + '\'' +
                ", email='" + email + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", telefono='" + telefono + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
