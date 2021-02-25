package com.example.groupfitnuevo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import cn.pedant.SweetAlert.SweetAlertDialog;
import controladores.ControladorEntrenador;
import controladores.ControladorGrupo;
import controladores.ControladorMensaje;
import controladores.ControladorRutina;
import controladores.ControladorUsuario;
import controladores.Mensaje;
import controladores.ServidorPHPException;
import controladores.Usuario;

public class editarperfilusuario_desde_entrenador extends AppCompatActivity implements View.OnClickListener {
    private EditText nombre, apellidos, email, contrasena;
    private Spinner spinnergrupos, spinnerentrenos;
    private FloatingActionButton guardarcambios;
    private CheckBox mantenergrupo, mantenerrutina;

    public void rellenarSpinner(Spinner spin, ArrayList<String> datos) {
        spin.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, datos));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarperfilusuario_desde_entrenador);
        Toolbar toolbar = findViewById(R.id.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nombre = findViewById(R.id.etnombre_usuario_edicion_desdeentrenador);
        apellidos = findViewById(R.id.etapellidos_usuario_edicion_desdeentrenador);
        email = findViewById(R.id.etemail_usuario_edicion_desdeentrenador);
        contrasena = findViewById(R.id.et_edit_contrasena_usuario_entrenador);

        spinnergrupos = findViewById(R.id.spinnertodosgrupos_edit_usuario);
        spinnerentrenos = findViewById(R.id.spinnertodas_rutinas_edit_usuario);

        guardarcambios = findViewById(R.id.floating_guardarcambios_usuarios);
        guardarcambios.setOnClickListener(this);
        mantenergrupo = findViewById(R.id.mantenergrupoanterior);
        mantenerrutina = findViewById(R.id.mantenerrutinaanterior);
        Intent intent = getIntent();

        String id_usuario = intent.getStringExtra("id");


        ControladorUsuario cargardatosusuarioporid = new ControladorUsuario();
        try {
            Usuario usuarioobtenidoid = cargardatosusuarioporid.obtenerUsuarioPorID(id_usuario);
            ControladorRutina obtenernombrerutina = new ControladorRutina();
            ControladorGrupo obtenernombregrupoid = new ControladorGrupo();
            nombre.setText(usuarioobtenidoid.getNombre());
            apellidos.setText(usuarioobtenidoid.getApellidos());
            email.setText(usuarioobtenidoid.getEmail());
            contrasena.setText(usuarioobtenidoid.getContrasena());

            String nombrerutina = usuarioobtenidoid.getId_entreno();
            String nombregrupo = usuarioobtenidoid.getId_grupo();
            ControladorGrupo obtenertodosnombres = new ControladorGrupo();
            ControladorRutina obtenertodosnombresrutinas = new ControladorRutina();
            rellenarSpinner(spinnergrupos, obtenertodosnombres.obtenerNombreGruposMenosElDelUsuario(nombregrupo));
            rellenarSpinner(spinnerentrenos, obtenertodosnombresrutinas.obtenerNombresRutinasPorIdMenosElDelUsuario(nombrerutina));


            mantenergrupo.setChecked(true);
            mantenerrutina.setChecked(true);


        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floating_guardarcambios_usuarios:
                Intent intent = getIntent();

                String id_usuario = intent.getStringExtra("id");
                Usuario datos = new Usuario();
                ControladorUsuario obtenerdatosusuarioboton = new ControladorUsuario();
                String nombrenuevo, apellidosnuevo, emailnuevo, contrasenanuevo;
                try {
                    datos = obtenerdatosusuarioboton.obtenerUsuarioPorID(id_usuario);
                    String id_grupo = "";
                    String id_entreno = "";

                    ControladorRutina obteneidpornombre = new ControladorRutina();
                    try {
                        if (mantenerrutina.isChecked()) {
                            id_entreno = datos.getId_entreno();
                        } else {
                            String rutina = obteneidpornombre.obtenerIdRutina(spinnerentrenos.getSelectedItem().toString());
                            id_entreno = rutina;

                        }

                        if (nombre.getText().toString().equals("")) {
                            nombrenuevo = datos.getNombre();

                        } else {
                            nombrenuevo = nombre.getText().toString();
                        }
                        if (apellidos.getText().toString().equals("")) {
                            apellidosnuevo = datos.getApellidos();

                        } else {
                            apellidosnuevo = apellidos.getText().toString();
                        }
                        if (email.getText().toString().equals("")) {
                            emailnuevo = datos.getEmail();

                        } else {
                            emailnuevo = email.getText().toString();
                        }
                        if (contrasena.getText().toString().equals("")) {
                            contrasenanuevo = datos.getContrasena();

                        } else {
                            contrasenanuevo = contrasena.getText().toString();
                        }
                        if (mantenergrupo.isChecked()) {
                            id_grupo = datos.getId_grupo();
                        } else {
                            ControladorGrupo obteneridpornombregrupo = new ControladorGrupo();
                            try {
                                String grupo = obteneridpornombregrupo.obtenerIdGrupo(spinnergrupos.getSelectedItem().toString());
                                id_grupo = grupo;
                            } catch (ServidorPHPException e) {
                                e.printStackTrace();
                            }

                        }
                        final Usuario usuariomodificado = new Usuario(nombrenuevo, apellidosnuevo, emailnuevo, contrasenanuevo, datos.getDni(), datos.getPeso(), datos.getAltura(), datos.getPressBanca(), datos.getDominadas(), datos.getForma_actual(), datos.getToken(), datos.getTiempo_1km(), id_grupo, datos.getImagen(), id_entreno);
                        if (nombrenuevo.equals("") || apellidosnuevo.equals("") || emailnuevo.equals("") || contrasenanuevo.equals("")) {
                            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Error campos vacíos.")
                                    .setContentText("La cuenta no se modificó con exito porque hay camps vacíos.")
                                    .setConfirmText("¡Gracias!").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();
                        } else {
                            ControladorUsuario modificarcontrolador = new ControladorUsuario();
                            if (modificarcontrolador.modificarusuario(usuariomodificado, id_usuario) == true) {
                                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Cuenta modificada.")
                                        .setContentText("La cuenta de " + usuariomodificado.getEmail() + " con nombre: " + usuariomodificado.getNombre() + " " + usuariomodificado.getApellidos() + " ha sido modificada con exito.")
                                        .setConfirmText("¡Gracias!").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        ControladorUsuario obteneridusuario = new ControladorUsuario();
                                        try {
                                            ControladorRutina obteneridrutina = new ControladorRutina();
                                            String nombrerutina = obteneridrutina.obtenernombrerutinaporid(usuariomodificado.getId_entreno());
                                            String id = obteneridusuario.obtenerUsuarioIdPorEmail(usuariomodificado);
                                            String titulo = "Entreno modificado.";
                                            String descripcion = "Tiene una nueva rutina: " + nombrerutina + ".";
                                            Mensaje nuevomensaje = new Mensaje(titulo, descripcion);
                                            ControladorMensaje ctrlmensaje = new ControladorMensaje();
                                            ctrlmensaje.enviarMensajeDesdeEntrenador(FirebaseInstanceId.getInstance().getToken(), nuevomensaje, id);
                                            ControladorGrupo obtenernombregrupo = new ControladorGrupo();
                                            String nombregrupo = obtenernombregrupo.obtenerNombreGrupoPorId(usuariomodificado.getId_grupo());
                                            String titulogrupo = "Grupo modificado.";
                                            String nuevomensaje2 = "Fuiste añadido al grupo: "+ nombregrupo+".";
                                            Mensaje mensajegrupo = new Mensaje(titulogrupo, nuevomensaje2);
                                            ctrlmensaje.enviarMensajeDesdeEntrenador(FirebaseInstanceId.getInstance().getToken(), mensajegrupo, id);
                                            Intent intent = new Intent(editarperfilusuario_desde_entrenador.this, postinicioentrenador.class);

                                            startActivity(intent);
                                            finish();
                                        } catch (ServidorPHPException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).show();

                            } else {
                                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Error al modificar.")
                                        .setContentText("La cuenta no se modificó con exito.")
                                        .setConfirmText("¡Gracias!").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                }).show();
                            }
                        }
                    } catch (ServidorPHPException e) {
                        e.printStackTrace();
                    }
                } catch (ServidorPHPException e) {
                    e.printStackTrace();
                }

                break;

        }
    }
}