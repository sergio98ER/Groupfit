package controladores;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;

public class ControladorEntrenador {
    private final int RESULTADO_OK = 1;
    private final int RESULTADO_ERROR = 2;
    private final int RESULTADO_ERROR_DESCONOCIDO = 3;

    private final String urlservidor = "http://192.168.1.16/phpGroupfit/";
    private final String obtenerusuario = urlservidor + "obtenerentrenadorlogin.php";
    private final String recuperarcontrasena = urlservidor + "obtenerentrenadormail.php";
    private final String modificarentrenador = urlservidor + "modificarentrenador.php";
    private final String obtenerentrenadorporid = urlservidor + "obtenerEntrenadorPorID.php";
    private final String modificarentrenadortoken = urlservidor + "modificarTokenEntrenador.php";
    private final String obtenerTokenEntrenadorPorID = urlservidor + "obtenerTokenEntrenadorPorID.php";

    public String obtenertokenentrenadorporid(String id) throws ServidorPHPException{
        String token = "";
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("id_entrenador", id);
        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(obtenerTokenEntrenadorPorID, parametros);

            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        JSONArray mensaje = jsonArray.getJSONObject(0).getJSONArray("mensaje");
                        if (mensaje.length() != 0) {
                            for (int i = 0; i < mensaje.length(); i++) {

                                token = mensaje.getJSONObject(i).getString("TOKEN");

                            }
                        } else {

                        }
                        break;
                    case RESULTADO_ERROR:

                        throw new ServidorPHPException("ERROR AL AÑADIR");
                }
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return token;
    }
    public boolean cambiarTokenEntrenador(String token, String email) throws ServidorPHPException{
        Boolean cambiado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("emailentrenador", email);
        parametros.put("tokenentrenador", token);

        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(modificarentrenadortoken, parametros);
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


    public Entrenador obtenerEntrenadorPorId(String id) throws ServidorPHPException{
        Entrenador entrenador = new Entrenador();
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("id_entrenador", id);
        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(obtenerentrenadorporid, parametros);

            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        JSONArray mensaje = jsonArray.getJSONObject(0).getJSONArray("mensaje");
                        if (mensaje.length() != 0) {
                            for (int i = 0; i < mensaje.length(); i++) {
                                String nombre = mensaje.getJSONObject(i).getString("nombre");
                                String apellidos = mensaje.getJSONObject(i).getString("apellidos");
                                String imagen = mensaje.getJSONObject(i).getString("imagen");
                                String token = mensaje.getJSONObject(i).getString("token");
                                String email = mensaje.getJSONObject(i).getString("email");
                                String contrasena = mensaje.getJSONObject(i).getString("contrasena");
                                int telefono = mensaje.getJSONObject(i).getInt("telefono");
                                String descripcion = mensaje.getJSONObject(i).getString("descripcion");

                                String telefonostring = String.valueOf(telefono);

                                entrenador.setDescripcion(descripcion);
                                entrenador.setNombre(nombre);
                                entrenador.setApellidos(apellidos);
                                entrenador.setImagen(imagen);
                                entrenador.getToken();
                                entrenador.setEmail(email);
                                entrenador.setContrasena(contrasena);
                                entrenador.setTelefono(telefonostring);
                            }
                        } else {

                        }
                        break;
                    case RESULTADO_ERROR:

                        throw new ServidorPHPException("ERROR AL AÑADIR");
                }
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return entrenador;
    }
    public boolean verificarEntrenador(Entrenador entrenadorverificado) throws ServidorPHPException {
        boolean verificado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("email", entrenadorverificado.getEmail());
        parametros.put("contrasena", entrenadorverificado.getContrasena());

        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(obtenerusuario, parametros);

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
                                String imagen = mensaje.getJSONObject(i).getString("imagen");
                                String token = mensaje.getJSONObject(i).getString("token");
                                String email = mensaje.getJSONObject(i).getString("email");
                                String contrasena = mensaje.getJSONObject(i).getString("contrasena");
                                int telefono = mensaje.getJSONObject(i).getInt("telefono");
                                String descripcion = mensaje.getJSONObject(i).getString("descripcion");

                                String telefonostring = String.valueOf(telefono);

                                entrenadorverificado.setDescripcion(descripcion);
                                entrenadorverificado.setNombre(nombre);
                                entrenadorverificado.setApellidos(apellidos);
                                entrenadorverificado.setImagen(imagen);
                                entrenadorverificado.getToken();
                                entrenadorverificado.setEmail(email);
                                entrenadorverificado.setContrasena(contrasena);
                                entrenadorverificado.setTelefono(telefonostring);
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
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return verificado;
    }

    public boolean correo(Entrenador entrenadorrecuperar) throws ServidorPHPException {
        boolean verificado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("email", entrenadorrecuperar.getEmail());

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
                                String imagen = mensaje.getJSONObject(i).getString("imagen");
                                String token = mensaje.getJSONObject(i).getString("token");
                                String email = mensaje.getJSONObject(i).getString("email");
                                String contrasena = mensaje.getJSONObject(i).getString("contrasena");
                                int telefono = mensaje.getJSONObject(i).getInt("telefono");
                                String descripcion = mensaje.getJSONObject(i).getString("descripcion");

                                String telefonostring = String.valueOf(telefono);

                                entrenadorrecuperar.setNombre(nombre);
                                entrenadorrecuperar.setApellidos(apellidos);
                                entrenadorrecuperar.setImagen(imagen);
                                entrenadorrecuperar.setToken(token);
                                entrenadorrecuperar.setEmail(email);
                                entrenadorrecuperar.setDescripcion(descripcion);
                                entrenadorrecuperar.setContrasena(contrasena);
                                entrenadorrecuperar.setTelefono(telefonostring);

                            }

                            verificado = true;
                        } else {
                            verificado = false;
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

    public boolean modificarEntrenador(Entrenador entrenadormodificado) throws ServidorPHPException {
        boolean modificado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        //nombre, apellidos, token, email, telefono, contrasena, descripcion;
        parametros.put("nombre", entrenadormodificado.getNombre());
        parametros.put("apellidos", entrenadormodificado.getApellidos());
        parametros.put("token", entrenadormodificado.getToken());
        parametros.put("email", entrenadormodificado.getEmail());
        parametros.put("telefono", entrenadormodificado.getTelefono());
        parametros.put("contrasena", entrenadormodificado.getContrasena());
        parametros.put("descripcion", entrenadormodificado.getDescripcion());
        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(modificarentrenador, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
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
}


