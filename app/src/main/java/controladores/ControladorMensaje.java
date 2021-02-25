package controladores;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

public class ControladorMensaje {
    private final int RESULTADO_OK = 1;
    private final int RESULTADO_ERROR = 2;
    private final int RESULTADO_ERROR_DESCONOCIDO = 3;

    private final String urlservidor = "http://192.168.1.16/phpGroupfit/";
    private final String EnviarMensajeUsuario = urlservidor + "EnviarMensajeUsuario.php";
    private final String EnviarMensajeEntrenador = urlservidor + "EnviarMensajeEntrenador.php";

    public boolean enviarMensajeUsuario(String emisor, Mensaje mensaje) throws ServidorPHPException, IOException, JSONException {
        boolean enviado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("emisor", emisor);
        parametros.put("titulo", mensaje.getTitulo());
        parametros.put("mensaje", mensaje.getMensaje());
        jsonArray = jsonParser.getJSONArrayFromUrl(EnviarMensajeUsuario, parametros);
        if (jsonArray != null) {
            int rs = jsonArray.getJSONObject(0).getInt("estado");
            switch (rs) {
                case RESULTADO_OK:
                    System.out.println("ENVIADO");
                    enviado = true;
                    break;
                case RESULTADO_ERROR:
                    throw new ServidorPHPException("ERROR AL AÑADIR");
            }
        }
        return enviado;
    }
    public boolean enviarMensajeDesdeEntrenador(String emisor, Mensaje mensaje, String receptor) throws ServidorPHPException, IOException, JSONException {
        boolean enviado = false;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("receptor", receptor);
        parametros.put("emisor", emisor);
        parametros.put("titulo", mensaje.getTitulo());
        parametros.put("mensaje", mensaje.getMensaje());

        jsonArray = jsonParser.getJSONArrayFromUrl(EnviarMensajeEntrenador, parametros);
        if (jsonArray != null) {
            int rs = jsonArray.getJSONObject(0).getInt("estado");
            switch (rs) {
                case RESULTADO_OK:
                    System.out.println("ENVIADO");
                    enviado = true;
                    break;
                case RESULTADO_ERROR:
                    throw new ServidorPHPException("ERROR AL AÑADIR");
            }
        }
        return enviado;
    }
}
