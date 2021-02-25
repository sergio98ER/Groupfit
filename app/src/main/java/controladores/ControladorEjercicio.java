package controladores;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ControladorEjercicio {
    private final int RESULTADO_OK = 1;
    private final int RESULTADO_ERROR = 2;
    private final int RESULTADO_ERROR_DESCONOCIDO = 3;

    private final String urlservidor = "http://192.168.1.16/phpGroupfit/";
    private final String anadirejercicio = urlservidor + "insertarEjercicio.php";
    private final String obtenerejerciciosrutina = urlservidor + "obtenerEjerciciosRutina.php";
    private final String eliminarejercicio = urlservidor + "borrarEjercicio.php";
    private final String modificarejercicio = urlservidor + "modificarEjercicio.php";
    private final String modificarPesosEjercicio = urlservidor + "modificarPesosEjercicio.php";
    private final String eliminarEjerciciosRutina = urlservidor + "borrarEjerciciosRutina.php";

    public ControladorEjercicio() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    public boolean eliminarEjerciciosRutina(String id_rutina) throws ServidorPHPException {
        boolean eliminado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("id_rutina", id_rutina);

        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(eliminarEjerciciosRutina, parametros);
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
    public boolean modificarPesosEjercicio(String peso_inicio, String peso_fin, String id_rutina, String nombre_ejercicio) throws ServidorPHPException {
        boolean modificado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("peso_inicio", peso_inicio);
        parametros.put("peso_fin",  peso_fin);
        parametros.put("rutina", id_rutina);
        parametros.put("nombre_ejercicio", nombre_ejercicio);


        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(modificarPesosEjercicio, parametros);
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
    public boolean modificarEjercicio(Ejercicio ejercicio, String id_rutina) throws ServidorPHPException {
        boolean modificado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();

        parametros.put("id_rutina", ejercicio.getRutina());
        parametros.put("musculo", ejercicio.getMusculo());
        parametros.put("nombre_ejercicio", ejercicio.getNombre_ejercicio());
        parametros.put("series_ejercicio", ejercicio.getSeries());
        parametros.put("repeticiones_ejercicio", ejercicio.getRepeticiones());
        parametros.put("peso_inicio", ejercicio.getPeso_inicio());
        parametros.put("peso_fin", ejercicio.getPeso_fin());

        parametros.put("rutina", id_rutina);

        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(modificarejercicio, parametros);
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
    public boolean eliminarEjercicio(String nombreejercicio, String id_rutina) throws ServidorPHPException {
        boolean eliminado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("id_rutina", id_rutina);
        parametros.put("nombre_ejercicio", nombreejercicio);

        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(eliminarejercicio, parametros);
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
    public ArrayList<Ejercicio> obtenerejerciciosrutina(String id) throws ServidorPHPException
    {
        ArrayList<Ejercicio> ejercicios = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray datos;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("id_rutina", id);

        try
        {
            // Obtengo los datos de las personas del servidor
            // Como no tiene parámetros la llamada al servidor
            // el segundo parámetro de la llamada es null
            datos = parser.getJSONArrayFromUrl(obtenerejerciciosrutina, parametros);

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
                            int rutina = mensaje.getJSONObject(i).getInt("rutina");
                            String musculo = mensaje.getJSONObject(i).getString("musculo");
                            String nombre = mensaje.getJSONObject(i).getString("nombre_ejercicio");
                            int series = mensaje.getJSONObject(i).getInt("series_ejercicio");
                            int repeticiones = mensaje.getJSONObject(i).getInt("repeticion_ejercicio");
                            double peso_inicio = mensaje.getJSONObject(i).getDouble("peso_inicio");
                            double peso_fin = mensaje.getJSONObject(i).getDouble("peso_fin");
                            // String rutina, musculo, nombre_ejercicio, series, repeticiones, peso_inicio, peso_fin;
                            Ejercicio ejercicioprueba = new Ejercicio(String.valueOf(rutina), musculo, nombre, String.valueOf(series), String.valueOf(repeticiones), String.valueOf(peso_inicio), String.valueOf(peso_fin));
                            ejercicios.add(ejercicioprueba);
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

        return ejercicios;
    }
    public boolean agregarEjercicio(Ejercicio nuevoejercicio) throws ServidorPHPException {
        boolean registrado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("id_rutina", nuevoejercicio.getRutina());
        parametros.put("musculo", nuevoejercicio.getMusculo());
        parametros.put("nombre_ejercicio", nuevoejercicio.getNombre_ejercicio());
        parametros.put("series_ejercicio", nuevoejercicio.getSeries());
        parametros.put("repeticiones_ejercicio", nuevoejercicio.getRepeticiones());
        parametros.put("peso_inicio", nuevoejercicio.getPeso_inicio());
        parametros.put("peso_fin", nuevoejercicio.getPeso_fin());
        try {
            jsonArray = jsonParser.getJSONArrayFromUrl(anadirejercicio, parametros);
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

}
