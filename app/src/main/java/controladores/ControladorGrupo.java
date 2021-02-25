package controladores;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ControladorGrupo {
    private final int RESULTADO_OK = 1;
    private final int RESULTADO_ERROR = 2;
    private final int RESULTADO_ERROR_DESCONOCIDO = 3;

    private final String urlservidor = "http://192.168.1.16/phpGroupfit/";
    private final String insertargrupo = urlservidor + "insertarGrupo.php";
    private final String obtenergrupos = urlservidor + "obtenerGrupos.php";
    private final String modificargrupo = urlservidor + "modificargrupo.php";
    private final String eliminargrupo = urlservidor + "borrarGrupo.php";
    private final String obtenernombregrupo = urlservidor + "obtenerGrupoNombre.php";
    private final String obtenerIdgrupo = urlservidor + "obtenerIdGrupo.php";
    private final String obtenerNombreGrupoPorId = urlservidor + "obtenerNombreGrupoPorId.php";
    private final String obtenerNombreGruposMenosElDelUsuario = urlservidor + "obtenerNombreGruposMenosElDelUsuario.php";

    public ControladorGrupo() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    public ArrayList<Grupo> obtenerGrupos() throws ServidorPHPException
    {
        ArrayList<Grupo> grupos = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray datos;

        try
        {
            // Obtengo los datos de las personas del servidor
            // Como no tiene parámetros la llamada al servidor
            // el segundo parámetro de la llamada es null
            datos = parser.getJSONArrayFromUrl(obtenergrupos, null);

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
                            String objetivo = mensaje.getJSONObject(i).getString("objetivo");
                            int id_entrenador = mensaje.getJSONObject(i).getInt("id_entrenador");


                            Grupo p = new Grupo(nombre, objetivo, id_entrenador);
                            grupos.add(p);
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

        return grupos;
    }
    public ArrayList<String> obtenerNombreGruposMenosElDelUsuario(String id) throws ServidorPHPException
    {
        ArrayList<String> nombres = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray datos;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("id_grupo", id);

        try
        {
            // Obtengo los datos de las personas del servidor
            // Como no tiene parámetros la llamada al servidor
            // el segundo parámetro de la llamada es null
            datos = parser.getJSONArrayFromUrl(obtenerNombreGruposMenosElDelUsuario, parametros);

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

                            nombres.add(nombre);
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

        return nombres;
    }

    public boolean agregarGrupo(Grupo grupo) throws ServidorPHPException {
        boolean registrado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("nombre", grupo.getNombre());
        parametros.put("objetivo", grupo.getObjetivo());
        parametros.put("id_entrenador", String.valueOf(grupo.getId_entrenador()));
        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(insertargrupo, parametros);
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
    public boolean modificargrupo(Grupo grupo, String id) throws ServidorPHPException {
        boolean modificado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("nombre", grupo.getNombre());
        parametros.put("objetivo", grupo.getObjetivo());

        parametros.put("id", id);

        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(modificargrupo, parametros);
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
    public boolean eliminarGrupo(String nombregrupo) throws ServidorPHPException {
        boolean eliminado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("nombre", nombregrupo);

        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(eliminargrupo, parametros);
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

    public boolean obtenerNombreGrupo(String nombregrupo) throws ServidorPHPException {
        boolean verificado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("nombre", nombregrupo);
        // private String nombre, apellidos, email, contrasena, dni, peso, altura, pressBanca, dominadas, forma_actual, token, tiempo_1km;
        //    private objetivos grupo;
        //    private String imagen;
        //    private objetivos entreno;
        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(obtenernombregrupo, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        JSONArray mensaje = jsonArray.getJSONObject(0).getJSONArray("mensaje");
                        if(mensaje.length() != 0){
                            for (int i = 0; i < mensaje.length(); i++) {
                                String nombre = mensaje.getJSONObject(i).getString("nombre");
                                String objetivo = mensaje.getJSONObject(i).getString("objetivo");
                                String id_entrenador = mensaje.getJSONObject(i).getString("id_entrenador");

                            }

                            verificado = true;
                        }else{
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

    public String obtenerIdGrupo(String nombregrupo) throws ServidorPHPException {
        String idgrupo = "";
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("nombre", nombregrupo);
        // private String nombre, apellidos, email, contrasena, dni, peso, altura, pressBanca, dominadas, forma_actual, token, tiempo_1km;
        //    private objetivos grupo;
        //    private String imagen;
        //    private objetivos entreno;
        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(obtenerIdgrupo, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        JSONArray mensaje = jsonArray.getJSONObject(0).getJSONArray("mensaje");
                        if(mensaje.length() != 0) {
                            for (int i = 0; i < mensaje.length(); i++) {
                                 idgrupo = mensaje.getJSONObject(i).getString("id");

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
        return idgrupo;

    }
    public String obtenerNombreGrupoPorId(String id) throws ServidorPHPException {
        String NOMBREGRUPO = "";
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("id_metido", id);
        // private String nombre, apellidos, email, contrasena, dni, peso, altura, pressBanca, dominadas, forma_actual, token, tiempo_1km;
        //    private objetivos grupo;
        //    private String imagen;
        //    private objetivos entreno;
        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(obtenerNombreGrupoPorId, parametros);
            if (jsonArray != null) {
                int rs = jsonArray.getJSONObject(0).getInt("estado");
                switch (rs) {
                    case RESULTADO_OK:
                        JSONArray mensaje = jsonArray.getJSONObject(0).getJSONArray("mensaje");
                        if(mensaje.length() != 0) {
                            for (int i = 0; i < mensaje.length(); i++) {
                                NOMBREGRUPO = mensaje.getJSONObject(i).getString("nombre");

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
        return NOMBREGRUPO;

    }


}

