package controladores;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

public class ControladorRutina {

    private final int RESULTADO_OK = 1;
    private final int RESULTADO_ERROR = 2;
    private final int RESULTADO_ERROR_DESCONOCIDO = 3;

    private final String urlservidor = "http://192.168.1.16/phpGroupfit/";
    private final String insertarrutina = urlservidor + "insertarRutina.php";
    private final String comprobarnombre = urlservidor + "comprobarNombreRutina.php";
    private final String obteneridrutina = urlservidor + "obtenerIdRutina.php";
    private final String obtenerNombreRutinaPorId = urlservidor + "obtenerNombreRutinaPorID.php";
    private final String obtenerNombresRutinasPorIdMenosElDelUsuario = urlservidor + "obtenerNombresRutinasPorIdMenosElDelUsuario.php";
    private final String obtenertodaslasrutinas = urlservidor + "obtenerRutinas.php";
    private final String modificarrutina = urlservidor + "modificarRutina.php";
    private final String eliminarrutinas = urlservidor + "borrarRutina.php";
    private final String obtenerrutinaporid = urlservidor + "obtenerRutinaPorID.php";
    public ControladorRutina() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    public Rutina obtenerRutinaPorID(String idrutina) throws ServidorPHPException {
        Rutina rutina = new Rutina();
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("id", idrutina);
        // private String nombre, apellidos, email, contrasena, dni, peso, altura, pressBanca, dominadas, forma_actual, token, tiempo_1km;
        //    private objetivos grupo;
        //    private String imagen;
        //    private objetivos entreno;
        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(obtenerrutinaporid, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        JSONArray mensaje = jsonArray.getJSONObject(0).getJSONArray("mensaje");
                        if(mensaje.length() != 0) {
                            for (int i = 0; i < mensaje.length(); i++) {

                                rutina.setNombre(mensaje.getJSONObject(i).getString("nombre"));
                                rutina.setDescripcion(mensaje.getJSONObject(i).getString("descripcion"));
                            }
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
        return rutina;

    }
    public boolean eliminarrutina(String eliminar) throws ServidorPHPException {
        boolean eliminado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("nombre_rutina", eliminar);

        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(eliminarrutinas, parametros);
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

    public boolean modificarRutina(Rutina rutina, String id) throws ServidorPHPException {
        boolean modificado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("nombre", rutina.getNombre());
        parametros.put("objetivo", rutina.getDescripcion());

        parametros.put("id", id);

        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(modificarrutina, parametros);
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

    public ArrayList<Rutina> obtenertodaslasrutinas() throws  ServidorPHPException{
        ArrayList<Rutina> rutinas = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray datos;
        try
        {
            // Obtengo los datos de las personas del servidor
            // Como no tiene parámetros la llamada al servidor
            // el segundo parámetro de la llamada es null
            datos = parser.getJSONArrayFromUrl(obtenertodaslasrutinas, null);

            if( datos != null )
            {
                int resultadoobtenido = datos.getJSONObject(0).getInt("estado");
                //System.out.println("EL RESULTADO ES " + resultadoobtenido);

                switch(resultadoobtenido)
                {
                    case RESULTADO_OK:
                        JSONArray mensaje = datos.getJSONObject(0).getJSONArray("mensaje");
                        for(int i = 0; i < mensaje.length(); i++)
                        {
                            // Obtengo los datos de cada persona
                            String nombre = mensaje.getJSONObject(i).getString("nombre");
                            String objetivo = mensaje.getJSONObject(i).getString("descripcion");



                            Rutina p = new Rutina(nombre, objetivo);
                            rutinas.add(p);
                        }
                        break;
                    case RESULTADO_ERROR:
                        throw new ServidorPHPException("Error, datos incorrectos.");
                    case RESULTADO_ERROR_DESCONOCIDO:
                        throw new ServidorPHPException("Error obteniendo los datos del servidor.");
                }

            }
            else
            {
                System.out.println("Error obteniendo los datos del servidor.");
            }
        }
        catch (IOException | JSONException e)
        {
            throw new ServidorPHPException(e.toString());
        }

        return rutinas;
    }
    public boolean comprobarexistencianombre(String nombre) throws ServidorPHPException {
        boolean verificado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("nombre_rutina", nombre);

        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(comprobarnombre, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        JSONArray mensaje = jsonArray.getJSONObject(0).getJSONArray("mensaje");
                        if(mensaje.length() != 0){
                            for (int i = 0; i < mensaje.length(); i++) {
                                String nombrerutina = mensaje.getJSONObject(i).getString("nombre");
                                String objetivo = mensaje.getJSONObject(i).getString("descripcion");


                            }

                            verificado = true;
                        }else{
                            verificado = false;
                        }
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
    public boolean agregarRutina(Rutina rutina) throws ServidorPHPException {
        boolean registrado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("nombre_rutina", rutina.getNombre());
        parametros.put("descripcion_rutina", rutina.getDescripcion());

        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(insertarrutina, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
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
    public String obtenerIdRutina(String nombrerutina) throws ServidorPHPException {
        String idrutina = "";
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("nombre_rutina", nombrerutina);
        // private String nombre, apellidos, email, contrasena, dni, peso, altura, pressBanca, dominadas, forma_actual, token, tiempo_1km;
        //    private objetivos grupo;
        //    private String imagen;
        //    private objetivos entreno;
        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(obteneridrutina, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        JSONArray mensaje = jsonArray.getJSONObject(0).getJSONArray("mensaje");
                        if(mensaje.length() != 0) {
                            for (int i = 0; i < mensaje.length(); i++) {

                                idrutina = mensaje.getJSONObject(i).getString("id_rutina");

                            }
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
        return idrutina;

    }
    public String obtenernombrerutinaporid(String idrutina) throws ServidorPHPException {
        String nombrerutina = "";
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("id", idrutina);
        // private String nombre, apellidos, email, contrasena, dni, peso, altura, pressBanca, dominadas, forma_actual, token, tiempo_1km;
        //    private objetivos grupo;
        //    private String imagen;
        //    private objetivos entreno;
        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(obtenerNombreRutinaPorId, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        JSONArray mensaje = jsonArray.getJSONObject(0).getJSONArray("mensaje");
                        if(mensaje.length() != 0) {
                            for (int i = 0; i < mensaje.length(); i++) {
                                nombrerutina = mensaje.getJSONObject(i).getString("nombre");

                            }
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
        return nombrerutina;

    }
    public ArrayList<String> obtenerNombresRutinasPorIdMenosElDelUsuario(String idrutina) throws ServidorPHPException {
        ArrayList<String> rutinas = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("id", idrutina);
        // private String nombre, apellidos, email, contrasena, dni, peso, altura, pressBanca, dominadas, forma_actual, token, tiempo_1km;
        //    private objetivos grupo;
        //    private String imagen;
        //    private objetivos entreno;
        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(obtenerNombresRutinasPorIdMenosElDelUsuario, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        JSONArray mensaje = jsonArray.getJSONObject(0).getJSONArray("mensaje");
                        if(mensaje.length() != 0) {
                            for (int i = 0; i < mensaje.length(); i++) {
                                String nombrerutina = mensaje.getJSONObject(i).getString("nombre");
                                rutinas.add(nombrerutina);
                            }
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
        return rutinas;

    }

}
