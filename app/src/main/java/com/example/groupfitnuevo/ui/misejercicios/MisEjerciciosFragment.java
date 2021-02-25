package com.example.groupfitnuevo.ui.misejercicios;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.groupfitnuevo.R;
import com.example.groupfitnuevo.usuarioejercicios_rutina;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;

import java.io.IOException;

import controladores.ControladorEntrenador;
import controladores.ControladorGrupo;
import controladores.ControladorMensaje;
import controladores.ControladorRutina;
import controladores.ControladorUsuario;
import controladores.Mensaje;
import controladores.Rutina;
import controladores.ServidorPHPException;
import controladores.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;


public class MisEjerciciosFragment extends Fragment implements View.OnClickListener {

    private MisEjerciciosViewModel homeViewModel;
    private Button solicitarentreno;
    private TextView nombre, objetivo, pressbanca, ultimoobjetivo, dominadas, tiempo, forma_actual;
    private CircleImageView imagenobjetivo, formaactualusuario;
    private FloatingActionButton mirarejercicios;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(MisEjerciciosViewModel.class);
        View root = inflater.inflate(R.layout.ejercicios_usuario, container, false);

        solicitarentreno = root.findViewById(R.id.bSolicitarEntreno);
        solicitarentreno.setOnClickListener(this);
        nombre = root.findViewById(R.id.tvnombrerutinausuario);
        objetivo = root.findViewById(R.id.tvObjetivoRutina);
        imagenobjetivo = root.findViewById(R.id.imagenrutina_usuario);
        mirarejercicios = root.findViewById(R.id.floating_ver);
        mirarejercicios.setOnClickListener(this);
        pressbanca = root.findViewById(R.id.tvPressbanca);
        ultimoobjetivo = root.findViewById(R.id.tvrutinapedida);
        dominadas = root.findViewById(R.id.tvrecordominadas);
        tiempo = root.findViewById(R.id.minimotiempo1km);
        forma_actual = root.findViewById(R.id.forma_actual);
        formaactualusuario = root.findViewById(R.id.formaactualusuarioimagen);
         SharedPreferences preferencias = getContext().getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        String email = preferencias.getString("email", "");
        ControladorUsuario usuarioobtener = new ControladorUsuario();
       Usuario usuarionuevo = new Usuario();
        usuarionuevo.setEmail(email);
        ControladorRutina obtenerrutina = new ControladorRutina();
        Rutina rutinausuario = new Rutina();
        SharedPreferences RUTINA = getContext().getSharedPreferences("Ultimarutina", Context.MODE_PRIVATE);
        ultimoobjetivo.setText(RUTINA.getString("objetivo", ""));
       SharedPreferences.Editor editorrutina;
        try {
            ControladorUsuario cambiartokenusuario = new ControladorUsuario();
            cambiartokenusuario.cambiartokenUsuario(FirebaseInstanceId.getInstance().getToken(), email);
            usuarionuevo = usuarioobtener.verificado(usuarionuevo);
            System.out.println(usuarionuevo.toString());
            rutinausuario = obtenerrutina.obtenerRutinaPorID(usuarionuevo.getId_entreno());
            nombre.setText(rutinausuario.getNombre());
            objetivo.setText(rutinausuario.getDescripcion());
            pressbanca.setText(usuarionuevo.getPressBanca()+ "KG.");
            dominadas.setText(usuarionuevo.getDominadas()+" repeticiones.");
            tiempo.setText(usuarionuevo.getTiempo_1km()+" minutos.");
            forma_actual.setText(usuarionuevo.getForma_actual());
            if (usuarionuevo.getForma_actual().equals("Baja forma") || usuarionuevo.getForma_actual().equals("baja forma")) {
                formaactualusuario.setImageResource(R.drawable.bajaformacircular);
            } else {
                if (usuarionuevo.getForma_actual().equals("Normal") || usuarionuevo.getForma_actual().equals("normal")) {
                    formaactualusuario.setImageResource(R.drawable.formanormal);
                } else {
                    if (usuarionuevo.getForma_actual().equals("Buena forma") || usuarionuevo.getForma_actual().equals("baja forma")) {
                        formaactualusuario.setImageResource(R.drawable.buenaforma);
                    } else {
                        if (usuarionuevo.getForma_actual().equals("Atleta") || usuarionuevo.getForma_actual().equals("atleta")) {
                            formaactualusuario.setImageResource(R.drawable.atleta);
                        }
                    }
                }
            }
            if(objetivo.getText().toString().equals("Pérdida de peso")){
                imagenobjetivo.setImageResource(R.drawable.icon_perder_peso);
                mirarejercicios.setEnabled(true);
            }else{
                if(objetivo.getText().toString().equals("Ganancia de masa muscular")){
                    imagenobjetivo.setImageResource(R.drawable.icon_ganancia_muscular);
                    mirarejercicios.setEnabled(true);
                }else{
                    if(objetivo.getText().toString().equals("Tonificar cuerpo")){
                        imagenobjetivo.setImageResource(R.drawable.icon_tonificacion);
                        mirarejercicios.setEnabled(true);
                    }else{
                        if(objetivo.getText().toString().equals("Ganancia de fuerza")){
                            imagenobjetivo.setImageResource(R.drawable.icon_fuerza);
                            mirarejercicios.setEnabled(true);
                        }else{
                            if(objetivo.getText().toString().equals("Ganancia de resistencia")){
                                imagenobjetivo.setImageResource(R.drawable.ic_resistencia);
                                mirarejercicios.setEnabled(true);
                            }else{
                                if(objetivo.getText().toString().equals("Hipertrofia")){
                                    imagenobjetivo.setImageResource(R.drawable.ic_hipertrofia);
                                    mirarejercicios.setEnabled(true);
                                }else{
                                    if(objetivo.getText().toString().equals("")){
                                        imagenobjetivo.setImageResource(R.drawable.account_question);
                                        mirarejercicios.setEnabled(false);
                                        mirarejercicios.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bSolicitarEntreno:

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                final View dialogo = inflater.inflate(R.layout.layoutnotificar, null, false);
                builder.setTitle("Pedir Rutina");
                builder.setView(dialogo).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Pedir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences RUTINA = getContext().getSharedPreferences("Ultimarutina", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorrutina;

                            Spinner objetivos = dialogo.findViewById(R.id.spinnersolicitarentreno);
                            editorrutina = RUTINA.edit();
                            editorrutina.putString("objetivo", objetivos.getSelectedItem().toString());
                            editorrutina.commit();
                            ultimoobjetivo.setText(objetivos.getSelectedItem().toString());
                            Usuario mandarsolicitud = new Usuario();
                            SharedPreferences preferencias = getContext().getSharedPreferences("Usuario", Context.MODE_PRIVATE);
                            String email = preferencias.getString("email", "");
                            mandarsolicitud.setEmail(email);
                            ControladorUsuario obtenerusuariosolicitud = new ControladorUsuario();
                        try {

                            mandarsolicitud = obtenerusuariosolicitud.verificado(mandarsolicitud);
                            ControladorGrupo obtenergrupoid = new ControladorGrupo();
                            String nombregrupo = obtenergrupoid.obtenerNombreGrupoPorId(mandarsolicitud.getId_grupo());
                            String titulo="Solicitud de entreno.";
                            String descripcion = "El usuario " + mandarsolicitud.getNombre()+" "+mandarsolicitud.getApellidos()+" con email "+ mandarsolicitud.getEmail()+ " ha solicitado un entrenamiento con el objetivo "+objetivos.getSelectedItem().toString()+" . Grupo usuario: "+nombregrupo;
                            Mensaje mensajeenviar = new Mensaje(titulo, descripcion);
                            ControladorMensaje controladomensaje = new ControladorMensaje();
                            controladomensaje.enviarMensajeUsuario(mandarsolicitud.getNombre(), mensajeenviar);

                            String titulo2="Actualiza tus datos.";
                            String mensaje2 ="Recuerda actualizar tus datos cada vez que termines una rutina, así tendremos constancia de tus cambios (Peso, dominadas, pressbanca, tiempo en recorrer 1km)";
                            Mensaje recibirmensajeactualizar = new Mensaje(titulo2, mensaje2);
                            ControladorEntrenador obtenertokenentrenador = new ControladorEntrenador();
                            String id = "1";
                            String tokenentrenador = obtenertokenentrenador.obtenertokenentrenadorporid(id);

                            ControladorMensaje automensaje = new ControladorMensaje();
                            ControladorUsuario obteneridusuario = new ControladorUsuario();
                            String idusuario = obteneridusuario.obtenerUsuarioIdPorEmail(mandarsolicitud);

                            automensaje.enviarMensajeDesdeEntrenador(mandarsolicitud.getToken(), recibirmensajeactualizar, idusuario);
                            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                            View layout = inflater.inflate(R.layout.custom_toast,
                                    null);
                            CircleImageView circulo = (CircleImageView) layout.findViewById(R.id.circletoast);
                            TextView texto = (TextView) layout.findViewById(R.id.textotoast);
                            texto.setText("Solicitud de entreno enviado.");
                            Toast toastpersonalido = new Toast(getContext());
                            toastpersonalido.setGravity(Gravity.BOTTOM, 0, 0);
                            toastpersonalido.setDuration(Toast.LENGTH_SHORT);
                            toastpersonalido.setView(layout);
                            toastpersonalido.show();
                        } catch (ServidorPHPException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
                builder.show();


                break;

            case R.id.floating_ver:
                Intent intent = new Intent(getContext(), usuarioejercicios_rutina.class);
                SharedPreferences preferencias = getContext().getSharedPreferences("Usuario", Context.MODE_PRIVATE);
                String email = preferencias.getString("email", "");
                intent.putExtra("email", email);
                startActivity(intent);
                break;
        }
    }
}
