package com.example.groupfitnuevo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupfitnuevo.adaptadores.adaptador_ejerciciosusuario;
import com.example.groupfitnuevo.adaptadores.adaptadorrutinas;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import controladores.ControladorEjercicio;
import controladores.ControladorGrupo;
import controladores.ControladorMensaje;
import controladores.ControladorRutina;
import controladores.ControladorUsuario;
import controladores.Ejercicio;
import controladores.Mensaje;
import controladores.ServidorPHPException;
import controladores.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

public class usuarioejercicios_rutina extends AppCompatActivity implements View.OnClickListener {
    private Button enviarresultados, obtenernuevosejercicios;
    private RecyclerView ejerciciosusuario;
    private adaptador_ejerciciosusuario adaptador;
    private SwipeRefreshLayout refrescar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarioejercicios_rutina);
        Toolbar toolbar = findViewById(R.id.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        final String emailusuario = intent.getStringExtra("email");
        enviarresultados = findViewById(R.id.bEnviarResultados);
        obtenernuevosejercicios = findViewById(R.id.bPedirNuevosEjercicios);
        enviarresultados.setOnClickListener(this);
        obtenernuevosejercicios.setOnClickListener(this);
        ejerciciosusuario = findViewById(R.id.recyclerejerciciosusuario);

        ejerciciosusuario.setHasFixedSize(true);
        // Creo un layoutmanager para el recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
        ejerciciosusuario.setLayoutManager(llm);
        ControladorUsuario obtenerusuarioporemail = new ControladorUsuario();
        ControladorEjercicio obtenertodosejercicios = new ControladorEjercicio();

        Usuario usuarioobtenerdatos = new Usuario();
        usuarioobtenerdatos.setEmail(emailusuario);
        try {
            usuarioobtenerdatos = obtenerusuarioporemail.verificado(usuarioobtenerdatos);
            final ArrayList<Ejercicio> todosejercicios = obtenertodosejercicios.obtenerejerciciosrutina(usuarioobtenerdatos.getId_entreno());
            adaptador = new adaptador_ejerciciosusuario(this, todosejercicios);
            ejerciciosusuario.setAdapter(adaptador);
            adaptador.refrescar();
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }
        refrescar = findViewById(R.id.refresejerciciosusuario);
        refrescar.setColorSchemeColors(Color.GREEN, Color.YELLOW, Color.RED);
        refrescar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = getIntent();
                        String emailusuario = intent.getStringExtra("email");

                        ControladorEjercicio todosejercicios = new ControladorEjercicio();
                        refrescar.setRefreshing(false);
                        ControladorUsuario obtenerusuariorefrescar = new ControladorUsuario();
                        Usuario usuariorefrescar = new Usuario();
                        usuariorefrescar.setEmail(emailusuario);
                        try {
                            usuariorefrescar = obtenerusuariorefrescar.verificado(usuariorefrescar);
                            ArrayList<Ejercicio> todasrutinasrefrescadas = todosejercicios.obtenerejerciciosrutina(usuariorefrescar.getId_entreno());
                            adaptador = new adaptador_ejerciciosusuario(usuarioejercicios_rutina.this, todasrutinasrefrescadas);
                            ejerciciosusuario.setAdapter(adaptador);
                            adaptador.refrescar();
                        } catch (ServidorPHPException e) {
                            e.printStackTrace();
                        }
                    }
                }, 1000);
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bEnviarResultados:
                Intent intent2 = getIntent();
                final String emailusuario2 = intent2.getStringExtra("email");
                Usuario usuarioobtenerdatos2 = new Usuario();
                usuarioobtenerdatos2.setEmail(emailusuario2);
                ControladorUsuario obtenerusuarioporemail2 = new ControladorUsuario();
                try {
                    usuarioobtenerdatos2 = obtenerusuarioporemail2.verificado(usuarioobtenerdatos2);
                    ControladorMensaje enviarresultadosentreno = new ControladorMensaje();
                    ControladorEjercicio mandarresultadosentreno = new ControladorEjercicio();

                    String titulo = "Resultados entreno.";
                    String descripcion ="El usuario con nombre: "+ usuarioobtenerdatos2.getNombre() + " y apellidos: "+usuarioobtenerdatos2.getApellidos()+" ha obtenido los siguientes resultados en su entreno: "+"\n"+mandarresultadosentreno.obtenerejerciciosrutina(usuarioobtenerdatos2.getId_entreno());
                    Mensaje datosobtenidos =  new Mensaje(titulo, descripcion);
                    if(enviarresultadosentreno.enviarMensajeUsuario(usuarioobtenerdatos2.getToken(), datosobtenidos)== true){
                        LayoutInflater inflater = (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                        View layout = inflater.inflate(R.layout.custom_toast,
                                null);
                        CircleImageView circulo = (CircleImageView) layout.findViewById(R.id.circletoast);
                        TextView texto = (TextView) layout.findViewById(R.id.textotoast);
                        texto.setText("Se han envidado los resultados del entrenamiento.");
                        Toast toastpersonalido = new Toast(this);
                        toastpersonalido.setGravity(Gravity.BOTTOM, 0, 0);
                        toastpersonalido.setDuration(Toast.LENGTH_SHORT);
                        toastpersonalido.setView(layout);
                        toastpersonalido.show();
                    }else{
                        LayoutInflater inflater = (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                        View layout = inflater.inflate(R.layout.custom_toast,
                                null);
                        CircleImageView circulo = (CircleImageView) layout.findViewById(R.id.circletoast);
                        TextView texto = (TextView) layout.findViewById(R.id.textotoast);
                        texto.setText("Algo salió mal.");
                        Toast toastpersonalido = new Toast(this);
                        toastpersonalido.setGravity(Gravity.BOTTOM, 0, 0);
                        toastpersonalido.setDuration(Toast.LENGTH_SHORT);
                        toastpersonalido.setView(layout);
                        toastpersonalido.show();
                    }
                } catch (ServidorPHPException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.bPedirNuevosEjercicios:
                Intent intent = getIntent();
                final String emailusuario = intent.getStringExtra("email");
                Usuario usuarioobtenerdatos = new Usuario();
                usuarioobtenerdatos.setEmail(emailusuario);
                ControladorUsuario obtenerusuarioporemail = new ControladorUsuario();
                try {
                    usuarioobtenerdatos = obtenerusuarioporemail.verificado(usuarioobtenerdatos);
                    ControladorMensaje pedirnuevosejercicios = new ControladorMensaje();
                    ControladorRutina obtenernombrerutina = new ControladorRutina();
                    ControladorGrupo obtenernombregrupo = new ControladorGrupo();
                    String nombregrupo = obtenernombregrupo.obtenerNombreGrupoPorId(usuarioobtenerdatos.getId_grupo());
                    String nombrerutina = obtenernombrerutina.obtenernombrerutinaporid(usuarioobtenerdatos.getId_entreno());
                    String titulo = "¡Necesito nuevos ejercicios!";
                    String descripcion = "El usuario: "+usuarioobtenerdatos.getNombre()+" "+usuarioobtenerdatos.getApellidos()+ " con rutina: "+nombrerutina+" y perteneciente al grupo: " +nombregrupo+" ha solicitado la actualización de los ejercicios de su entreno.";
                    Mensaje mensajepedir = new Mensaje(titulo, descripcion);
                   if( pedirnuevosejercicios.enviarMensajeUsuario(usuarioobtenerdatos.getToken(), mensajepedir) == true){
                       LayoutInflater inflater = (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                       View layout = inflater.inflate(R.layout.custom_toast,
                               null);
                       CircleImageView circulo = (CircleImageView) layout.findViewById(R.id.circletoast);
                       TextView texto = (TextView) layout.findViewById(R.id.textotoast);
                       texto.setText("Se han pedido nuevos ejercicios.");
                       Toast toastpersonalido = new Toast(this);
                       toastpersonalido.setGravity(Gravity.BOTTOM, 0, 0);
                       toastpersonalido.setDuration(Toast.LENGTH_SHORT);
                       toastpersonalido.setView(layout);
                       toastpersonalido.show();
                   }else{
                       LayoutInflater inflater = (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                       View layout = inflater.inflate(R.layout.custom_toast,
                               null);
                       CircleImageView circulo = (CircleImageView) layout.findViewById(R.id.circletoast);
                       TextView texto = (TextView) layout.findViewById(R.id.textotoast);
                       texto.setText("Algo salió mal.");
                       Toast toastpersonalido = new Toast(this);
                       toastpersonalido.setGravity(Gravity.BOTTOM, 0, 0);
                       toastpersonalido.setDuration(Toast.LENGTH_SHORT);
                       toastpersonalido.setView(layout);
                       toastpersonalido.show();
                   }
                } catch (ServidorPHPException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;
        }
    }
}
