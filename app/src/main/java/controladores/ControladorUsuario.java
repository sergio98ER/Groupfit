package controladores;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

public class ControladorUsuario {
    private final int RESULTADO_OK = 1;
    private final int RESULTADO_ERROR = 2;
    private final int RESULTADO_ERROR_DESCONOCIDO = 3;

    private final String urlservidor = "http://192.168.1.16/phpGroupfit/";
    private final String obtenerusuariologin = urlservidor + "obtenerusuariologin.php";
    private final String anadirusuario = urlservidor + "insertarUsuario.php";
    private final String obtenerusuarios = urlservidor + "obtenerusuariologin.php";
    private final String eliminarusuaios = urlservidor + "borrarUsuario.php";
    private final String recuperarcontrasena = urlservidor + "obtenerusuariomail.php";
    private final String obtenerUsuariosGrupo = urlservidor + "obtenerUsuariosGrupo.php";
    private final String eliminarUsuarioGrupo = urlservidor + "borrarUsuarioGrupo.php";
    private final String obtenerIdUsuarioEmail = urlservidor + "obtenerIdUsuarioPorEmail.php";
    private final String obtenerDatosUsuarioPorID = urlservidor + "obtenerDatosUsuarioPorID.php";
    private final String modificarUsuario = urlservidor + "modificarUsuario.php";
    private final String modifiarEntrenoUsuariosEliminado = urlservidor + "modifiarEntrenoUsuariosEliminado.php";
    private final String verificarExistenciaEmailUsuario = urlservidor + "verificarExistenciaEmailUsuario.php";
    private final String verificarExistenciaDNI = urlservidor + "verificarExistenciaDNI.php";
    private final String modificarTokenUsuario = urlservidor + "modificarTokenUsuario.php";

    public ControladorUsuario() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    public boolean cambiartokenUsuario(String token, String email) throws ServidorPHPException{
        Boolean cambiado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("emailusuario", email);
        parametros.put("tokenusuario", token);

        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(modificarTokenUsuario, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        cambiado = true;
                        break;
                    case RESULTADO_ERROR:
                        throw new ServidorPHPException("ERROR AL AÑADIR");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cambiado;
    }
    public boolean verificarExistenciaDni(String dni) throws  ServidorPHPException{
        boolean verificado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;

        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("dni", dni);

        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(verificarExistenciaDNI, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        JSONArray mensaje = jsonArray.getJSONObject(0).getJSONArray("mensaje");
                        if (mensaje.length() != 0) {
                            for (int i = 0; i < mensaje.length(); i++) {
                                verificado = true;
                            }
                        } else {
                            System.out.println("ERROR");;
                        }

                        break;
                    case RESULTADO_ERROR:
                        throw new ServidorPHPException("ERROR AL AÑADIR");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return verificado;
    }

    public boolean modifiarEntrenoUsuariosEliminado(String id_entreno_eliminado, String id_entreno) throws ServidorPHPException {
        //private String nombre, apellidos, email, contrasena, dni, peso, altura, pressBanca, dominadas, forma_actual, token, tiempo_1km, id_grupo, imagen, id_entreno;
        boolean modificado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("id_entreno_eliminado", id_entreno_eliminado);
        parametros.put("id_entreno", id_entreno);


        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(modifiarEntrenoUsuariosEliminado, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        System.out.println("modificado");
                        modificado = true;
                        break;
                    case RESULTADO_ERROR:
                        throw new ServidorPHPException("ERROR AL AÑADIR");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return modificado;
    }
    public boolean verificarExistenciaCorreo(String correo) throws  ServidorPHPException{
        boolean verificado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;

        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("correo", correo);

        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(verificarExistenciaEmailUsuario, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        JSONArray mensaje = jsonArray.getJSONObject(0).getJSONArray("mensaje");
                        if (mensaje.length() != 0) {
                            for (int i = 0; i < mensaje.length(); i++) {
                                verificado = true;
                            }
                        } else {
                            System.out.println("ERROR");;
                        }

                        break;
                    case RESULTADO_ERROR:
                        throw new ServidorPHPException("ERROR AL AÑADIR");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return verificado;
    }
    public boolean modificarusuario(Usuario nuevousuario, String id) throws ServidorPHPException {
        //private String nombre, apellidos, email, contrasena, dni, peso, altura, pressBanca, dominadas, forma_actual, token, tiempo_1km, id_grupo, imagen, id_entreno;
        boolean modificado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("nombre", nuevousuario.getNombre());
        parametros.put("apellidos", nuevousuario.getApellidos());
        parametros.put("email", nuevousuario.getEmail());
        parametros.put("contrasena", nuevousuario.getContrasena());
        parametros.put("dni", nuevousuario.getDni());
        parametros.put("peso", nuevousuario.getPeso());
        parametros.put("altura", nuevousuario.getAltura());
        parametros.put("pressBanca", nuevousuario.getPressBanca());
        parametros.put("dominadas", nuevousuario.getDominadas());
        parametros.put("forma_actual", nuevousuario.getForma_actual());
        parametros.put("token", nuevousuario.getToken());
        parametros.put("tiempo_1km", nuevousuario.getTiempo_1km());
        parametros.put("id_grupo", nuevousuario.getId_grupo());
        parametros.put("imagen", nuevousuario.getTiempo_1km());
        parametros.put("id_entreno", nuevousuario.getId_entreno());
        parametros.put("id_usuario", id);

        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(modificarUsuario, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        System.out.println("modificado");
                        modificado = true;
                        break;
                    case RESULTADO_ERROR:
                        throw new ServidorPHPException("ERROR AL AÑADIR");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return modificado;
    }
    public ArrayList<String> obtenerUsuarios(String nombre) throws ServidorPHPException {
        ArrayList<String> usuarios = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        JSONArray datos;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("usuario", nombre);
        try {
            datos = jsonParser.getJSONArrayFromUrl(obtenerusuarios, parametros);

            if (datos != null) {
                int resultado = datos.getJSONObject(0).getInt("estado");
                switch (resultado) {
                    case RESULTADO_OK:
                        JSONArray mensaje = datos.getJSONObject(0).getJSONArray("mensaje");
                        for (int i = 0; i < mensaje.length(); i++) {
                            String nombrenuevo = mensaje.getJSONObject(i).getString("usuario");
                            usuarios.add(nombrenuevo);

                        }
                        break;
                    case RESULTADO_ERROR:

                        throw new ServidorPHPException("Error, datos incorrectos.");
                    case RESULTADO_ERROR_DESCONOCIDO:
                        throw new ServidorPHPException("Error obteniendo los datos del servidor.");
                }
            } else {
                System.out.println("ERROR.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
    public String obtenerUsuarioIdPorEmail(Usuario usuarioverificado) throws ServidorPHPException {
        String id= "";
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("correo", usuarioverificado.getEmail());
        // private String nombre, apellidos, email, contrasena, dni, peso, altura, pressBanca, dominadas, forma_actual, token, tiempo_1km;
        //    private objetivos grupo;
        //    private String imagen;
        //    private objetivos entreno;
        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(obtenerIdUsuarioEmail, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        JSONArray mensaje = jsonArray.getJSONObject(0).getJSONArray("mensaje");
                        if (mensaje.length() != 0) {
                            for (int i = 0; i < mensaje.length(); i++) {

                           id = mensaje.getJSONObject(i).getString("ID");
                            }
                        } else {
                            System.out.println("ERROR");;
                        }

                        break;
                    case RESULTADO_ERROR:
                        throw new ServidorPHPException("ERROR AL AÑADIR");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;

    }

    public Usuario verificado(Usuario usuarioverificado) throws ServidorPHPException {
        Usuario nuevousuario = new Usuario();
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("correo", usuarioverificado.getEmail());
        // private String nombre, apellidos, email, contrasena, dni, peso, altura, pressBanca, dominadas, forma_actual, token, tiempo_1km;
        //    private objetivos grupo;
        //    private String imagen;
        //    private objetivos entreno;
        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(recuperarcontrasena, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        JSONArray mensaje = jsonArray.getJSONObject(0).getJSONArray("mensaje");
                        if (mensaje.length() != 0) {
                            for (int i = 0; i < mensaje.length(); i++) {

                                String nombre = mensaje.getJSONObject(i).getString("nombre");
                                String apellidos = mensaje.getJSONObject(i).getString("apellidos");
                                String email = mensaje.getJSONObject(i).getString("email");
                                String contrasena = mensaje.getJSONObject(i).getString("contrasena");
                                String dni = mensaje.getJSONObject(i).getString("dni");
                                String peso = mensaje.getJSONObject(i).getString("peso");
                                String altura = mensaje.getJSONObject(i).getString("altura");
                                Double pressbanca =  mensaje.getJSONObject(i).getDouble("pressBanca");
                                int dominadas =  mensaje.getJSONObject(i).getInt("dominadas");
                                String forma_actual = mensaje.getJSONObject(i).getString("forma_actual");
                                String token = mensaje.getJSONObject(i).getString("token");
                                String tiempo_1km = mensaje.getJSONObject(i).getString("tiempo_1km");
                                int id_grupo = mensaje.getJSONObject(i).getInt("id_grupo");
                                String imagen = mensaje.getJSONObject(i).getString("imagen");
                                int id_entreno = mensaje.getJSONObject(i).getInt("id_entreno");


                                usuarioverificado.setNombre(nombre);
                                usuarioverificado.setApellidos(apellidos);
                                usuarioverificado.setEmail(email);
                                usuarioverificado.setContrasena(contrasena);
                                usuarioverificado.setDni(dni);
                                usuarioverificado.setPeso(peso);
                                usuarioverificado.setAltura(altura);
                                usuarioverificado.setPressBanca(String.valueOf(pressbanca));
                                usuarioverificado.setDominadas(String.valueOf(dominadas));
                                usuarioverificado.setForma_actual(forma_actual);
                                usuarioverificado.setToken(token);
                                usuarioverificado.setTiempo_1km(tiempo_1km);
                                usuarioverificado.setId_grupo(String.valueOf(id_grupo));
                                usuarioverificado.setImagen(imagen);
                                usuarioverificado.setId_entreno(String.valueOf(id_entreno));

                                nuevousuario = usuarioverificado;
                            }
                        } else {
                            System.out.println("ERROR");;
                        }

                        break;
                    case RESULTADO_ERROR:
                        throw new ServidorPHPException("ERROR AL AÑADIR");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return nuevousuario;

    }
    public Usuario obtenerUsuarioPorID(String id) throws ServidorPHPException {
        Usuario nuevousuario = new Usuario();
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("id_usuario", id);
        // private String nombre, apellidos, email, contrasena, dni, peso, altura, pressBanca, dominadas, forma_actual, token, tiempo_1km;
        //    private objetivos grupo;
        //    private String imagen;
        //    private objetivos entreno;
        try {
            System.out.println(parametros.toString());
            jsonArray = jsonParser.getJSONArrayFromUrl(obtenerDatosUsuarioPorID, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        JSONArray mensaje = jsonArray.getJSONObject(0).getJSONArray("mensaje");
                        if (mensaje.length() != 0) {
                            for (int i = 0; i < mensaje.length(); i++) {

                                String nombre = mensaje.getJSONObject(i).getString("nombre");
                                String apellidos = mensaje.getJSONObject(i).getString("apellidos");
                                String email = mensaje.getJSONObject(i).getString("email");
                                String contrasena = mensaje.getJSONObject(i).getString("contrasena");
                                String dni = mensaje.getJSONObject(i).getString("dni");
                                String peso = mensaje.getJSONObject(i).getString("peso");
                                String altura = mensaje.getJSONObject(i).getString("altura");
                                Double pressbanca =  mensaje.getJSONObject(i).getDouble("pressBanca");
                                int dominadas =  mensaje.getJSONObject(i).getInt("dominadas");
                                String forma_actual = mensaje.getJSONObject(i).getString("forma_actual");
                                String token = mensaje.getJSONObject(i).getString("token");
                                String tiempo_1km = mensaje.getJSONObject(i).getString("tiempo_1km");
                                int id_grupo = mensaje.getJSONObject(i).getInt("id_grupo");
                                String imagen = mensaje.getJSONObject(i).getString("imagen");
                                int id_entreno = mensaje.getJSONObject(i).getInt("id_entreno");


                                nuevousuario.setNombre(nombre);
                                nuevousuario.setApellidos(apellidos);
                                nuevousuario.setEmail(email);
                                nuevousuario.setContrasena(contrasena);
                                nuevousuario.setDni(dni);
                                nuevousuario.setPeso(peso);
                                nuevousuario.setAltura(altura);
                                nuevousuario.setPressBanca(String.valueOf(pressbanca));
                                nuevousuario.setDominadas(String.valueOf(dominadas));
                                nuevousuario.setForma_actual(forma_actual);
                                nuevousuario.setToken(token);
                                nuevousuario.setTiempo_1km(tiempo_1km);
                                nuevousuario.setId_grupo(String.valueOf(id_grupo));
                                nuevousuario.setImagen(imagen);
                                nuevousuario.setId_entreno(String.valueOf(id_entreno));

                            }
                        } else {
                            System.out.println("ERROR");;
                        }

                        break;
                    case RESULTADO_ERROR:
                        throw new ServidorPHPException("ERROR AL AÑADIR");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return nuevousuario;

    }

    public boolean verificarUsuario(Usuario usuarioverifciado) throws ServidorPHPException {
        boolean verificado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("correo", usuarioverifciado.getEmail());
        parametros.put("contrasena", usuarioverifciado.getContrasena());
        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(obtenerusuariologin, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        JSONArray mensaje = jsonArray.getJSONObject(0).getJSONArray("mensaje");
                        System.out.println(mensaje.length());
                        if (mensaje.length() != 0) {
                            for (int i = 0; i < mensaje.length(); i++) {
                                String nombre = mensaje.getJSONObject(i).getString("nombre");
                                String apellidos = mensaje.getJSONObject(i).getString("apellidos");
                                String email = mensaje.getJSONObject(i).getString("email");
                                String contrasena = mensaje.getJSONObject(i).getString("contrasena");
                                String dni = mensaje.getJSONObject(i).getString("dni");
                                String peso = mensaje.getJSONObject(i).getString("peso");
                                String altura = mensaje.getJSONObject(i).getString("altura");
                                Double pressbanca =  mensaje.getJSONObject(i).getDouble("pressBanca");
                                int dominadas =  mensaje.getJSONObject(i).getInt("dominadas");
                                String forma_actual = mensaje.getJSONObject(i).getString("forma_actual");
                                String token = mensaje.getJSONObject(i).getString("token");
                                String tiempo_1km = mensaje.getJSONObject(i).getString("tiempo_1km");
                                int id_grupo = mensaje.getJSONObject(i).getInt("id_grupo");
                                String imagen = mensaje.getJSONObject(i).getString("imagen");
                                int id_entreno = mensaje.getJSONObject(i).getInt("id_entreno");

                                usuarioverifciado.setNombre(nombre);
                                usuarioverifciado.setApellidos(apellidos);
                                usuarioverifciado.setEmail(email);
                                usuarioverifciado.setContrasena(contrasena);
                                usuarioverifciado.setDni(dni);
                                usuarioverifciado.setPeso(peso);
                                usuarioverifciado.setAltura(altura);
                                usuarioverifciado.setPressBanca(String.valueOf(pressbanca));
                                usuarioverifciado.setDominadas(String.valueOf(dominadas));
                                usuarioverifciado.setForma_actual(forma_actual);
                                usuarioverifciado.setToken(token);
                                usuarioverifciado.setTiempo_1km(tiempo_1km);
                                usuarioverifciado.setId_grupo(String.valueOf(id_grupo));
                                usuarioverifciado.setImagen(imagen);
                                usuarioverifciado.setId_entreno(String.valueOf(id_entreno));


                            }

                            verificado = true;
                        } else {

                            verificado = false;
                        }
                        break;
                    case RESULTADO_ERROR:
                        verificado = false;
                        throw new ServidorPHPException("ERROR AL AÑADIR");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return verificado;

    }

    public boolean agregarUsuario(Usuario nuevousuario) throws ServidorPHPException {
        boolean registrado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("nombre", nuevousuario.getNombre());
        parametros.put("apellidos", nuevousuario.getApellidos());
        parametros.put("email", nuevousuario.getEmail());
        parametros.put("contrasena", nuevousuario.getContrasena());
        parametros.put("dni", nuevousuario.getDni());
        parametros.put("peso", nuevousuario.getPeso());
        parametros.put("altura", nuevousuario.getAltura());
        parametros.put("pressBanca", nuevousuario.getPressBanca());
        parametros.put("dominadas", nuevousuario.getDominadas());
        parametros.put("forma_actual", nuevousuario.getForma_actual());
        parametros.put("token", nuevousuario.getToken());
        parametros.put("tiempo_1km", nuevousuario.getTiempo_1km());
        parametros.put("id_grupo", nuevousuario.getId_grupo());
        parametros.put("imagen", nuevousuario.getTiempo_1km());
        parametros.put("id_entreno", nuevousuario.getId_entreno());
        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(anadirusuario, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        System.out.println("REGISTRADO");
                        registrado = true;
                        break;
                    case RESULTADO_ERROR:
                        throw new ServidorPHPException("ERROR AL AÑADIR");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return registrado;
    }
    public boolean verificarcorreo(Usuario correo) throws ServidorPHPException {
        boolean verificado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("correo", correo.getEmail());

        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(eliminarusuaios, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:

                        verificado = true;
                        break;
                    case RESULTADO_ERROR:
                        throw new ServidorPHPException("ERROR AL ELIMINAR");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return verificado;
    }

    public boolean eliminarUsuario(String email) throws ServidorPHPException {
        boolean eliminado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("correo", email);

        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(eliminarusuaios, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:

                        eliminado = true;
                        break;
                    case RESULTADO_ERROR:
                        throw new ServidorPHPException("ERROR AL ELIMINAR");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eliminado;
    }
    public boolean eliminarUsuariogrupo(Usuario usuario, String id) throws ServidorPHPException {
        boolean eliminado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("correo", usuario.getEmail());
        parametros.put("grupovacio", id);

        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(eliminarUsuarioGrupo, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        eliminado = true;
                        break;
                    case RESULTADO_ERROR:
                        throw new ServidorPHPException("ERROR AL ELIMINAR");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eliminado;
    }
    public ArrayList<Usuario> obtenerUsuariosGrupo(String id) throws ServidorPHPException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        JSONArray datos;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("id", id);

        try {
            datos = jsonParser.getJSONArrayFromUrl(obtenerUsuariosGrupo, parametros);

            if (datos != null) {

                int resultado = datos.getJSONObject(0).getInt("estado");
                switch (resultado) {
                    case RESULTADO_OK:

                        JSONArray mensaje = datos.getJSONObject(0).getJSONArray("mensaje");
                        for (int i = 0; i < mensaje.length(); i++) {
                            String nombre = mensaje.getJSONObject(i).getString("nombre");
                            String apellidos = mensaje.getJSONObject(i).getString("apellidos");
                            String email = mensaje.getJSONObject(i).getString("email");
                            String contrasena = mensaje.getJSONObject(i).getString("contrasena");
                            String dni = mensaje.getJSONObject(i).getString("dni");
                            String peso = mensaje.getJSONObject(i).getString("peso");
                            String altura = mensaje.getJSONObject(i).getString("altura");
                            Double pressBanca = mensaje.getJSONObject(i).getDouble("pressBanca");
                            int dominadas = mensaje.getJSONObject(i).getInt("dominadas");
                            String forma_actual = mensaje.getJSONObject(i).getString("forma_actual");
                            String token = mensaje.getJSONObject(i).getString("token");
                            String tiempo_1km = mensaje.getJSONObject(i).getString("tiempo_1km");
                            int id_grupo = mensaje.getJSONObject(i).getInt("id_grupo");
                            String imagen = mensaje.getJSONObject(i).getString("imagen");
                            int id_entreno = mensaje.getJSONObject(i).getInt("id_entreno");


                            Usuario nuevousuario = new Usuario(nombre, apellidos, email, contrasena, dni, peso, altura, String.valueOf(pressBanca),  String.valueOf(dominadas), forma_actual, token, tiempo_1km,  String.valueOf(id_grupo), imagen, String.valueOf(id_entreno));
                            usuarios.add(nuevousuario);

                        }
                        break;
                    case RESULTADO_ERROR:

                        throw new ServidorPHPException("Error, datos incorrectos.");
                    case RESULTADO_ERROR_DESCONOCIDO:
                        throw new ServidorPHPException("Error obteniendo los datos del servidor.");
                }
            } else {
                System.out.println("ERROR.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return usuarios;
    }


}
