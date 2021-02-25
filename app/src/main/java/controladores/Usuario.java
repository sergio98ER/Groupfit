package controladores;

public class Usuario {
    private String nombre, apellidos, email, contrasena, dni, peso, altura, pressBanca, dominadas, forma_actual, token, tiempo_1km, id_grupo, imagen, id_entreno;

    public Usuario() {

    }

    public Usuario(String nombre, String apellidos, String email, String contrasena, String dni, String peso, String altura, String pressBanca, String dominadas, String forma_actual, String token, String tiempo_1km, String id_grupo, String imagen, String id_entreno) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.contrasena = contrasena;
        this.dni = dni;
        this.peso = peso;
        this.altura = altura;
        this.pressBanca = pressBanca;
        this.dominadas = dominadas;
        this.forma_actual = forma_actual;
        this.token = token;
        this.tiempo_1km = tiempo_1km;
        this.id_grupo = id_grupo;
        this.imagen = imagen;
        this.id_entreno = id_entreno;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPressBanca() {
        return pressBanca;
    }

    public void setPressBanca(String pressBanca) {
        this.pressBanca = pressBanca;
    }

    public String getDominadas() {
        return dominadas;
    }

    public void setDominadas(String dominadas) {
        this.dominadas = dominadas;
    }

    public String getForma_actual() {
        return forma_actual;
    }

    public void setForma_actual(String forma_actual) {
        this.forma_actual = forma_actual;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTiempo_1km() {
        return tiempo_1km;
    }

    public void setTiempo_1km(String tiempo_1km) {
        this.tiempo_1km = tiempo_1km;
    }

    public String getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(String id_grupo) {
        this.id_grupo = id_grupo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getId_entreno() {
        return id_entreno;
    }

    public void setId_entreno(String id_entreno) {
        this.id_entreno = id_entreno;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", dni='" + dni + '\'' +
                ", peso='" + peso + '\'' +
                ", altura='" + altura + '\'' +
                ", pressBanca='" + pressBanca + '\'' +
                ", dominadas='" + dominadas + '\'' +
                ", forma_actual='" + forma_actual + '\'' +
                ", token='" + token + '\'' +
                ", tiempo_1km='" + tiempo_1km + '\'' +
                ", id_grupo='" + id_grupo + '\'' +
                ", imagen='" + imagen + '\'' +
                ", id_entreno='" + id_entreno + '\'' +
                '}';
    }
}
