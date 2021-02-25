package com.example.groupfitnuevo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.circularreveal.coordinatorlayout.CircularRevealCoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.sun.mail.util.UUDecoderStream;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import controladores.ControladorUsuario;
import controladores.ServidorPHPException;
import controladores.Usuario;

public class editar_perfil_usuario extends AppCompatActivity implements View.OnClickListener {

    private Button Eliminar, Actualizar;
    private EditText nombre, apellidos, email, dni, peso, altura, dominadas, pressbanca, minutos, segundos, contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Eliminar = findViewById(R.id.bEliminar_edit);
        Eliminar.setOnClickListener(this);
        Actualizar = findViewById(R.id.bactualizar_perfil);
        Actualizar.setOnClickListener(this);
        nombre = findViewById(R.id.etnombre_editarperfil);
        apellidos = findViewById(R.id.etapellidos_editarperfil);
        email = findViewById(R.id.etemail_editarperfil);
        dni = findViewById(R.id.etdni_editarperfil);
        peso = findViewById(R.id.etaltura_editarperfil);
        altura = findViewById(R.id.etaltura_editarperfil);
        contrasena = findViewById(R.id.etcontrasena_editusuario);
        dominadas = findViewById(R.id.etdominadas_editarperfil);
        pressbanca = findViewById(R.id.etpressbanca_editarperfil);
        minutos = findViewById(R.id.etminutos_editarperfil);
        segundos = findViewById(R.id.etsegundos_editarperfil);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.bEliminar_edit:
                Intent intent = getIntent();
                String emaileliminarusuario = intent.getStringExtra("email");

                ControladorUsuario controladorUsuarioeliminar = new ControladorUsuario();
                try {
                    controladorUsuarioeliminar.eliminarUsuario(emaileliminarusuario);
                    ControladorUsuario obtenerusuariomail = new ControladorUsuario();
                    Usuario usuarioeliminar = new Usuario();
                    usuarioeliminar.setEmail(emaileliminarusuario);
                    usuarioeliminar = obtenerusuariomail.verificado(usuarioeliminar);
                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Cuenta eliminada.")
                            .setContentText("La cuenta de " + usuarioeliminar.getNombre() + " " + usuarioeliminar.getApellidos() + " con email " + usuarioeliminar.getEmail() + ".")
                            .setConfirmText("¡Gracias!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    Intent intent = new Intent(editar_perfil_usuario.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).show();
                } catch (ServidorPHPException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.bactualizar_perfil:
                Intent intent2 = getIntent();
                String EMAILUSUARIO = intent2.getStringExtra("email");
                Usuario obtenerdatosusuario = new Usuario();
                obtenerdatosusuario.setEmail(EMAILUSUARIO);
                ControladorUsuario obtenermailusuario = new ControladorUsuario();
                ControladorUsuario modificarusuario = new ControladorUsuario();
                try {
                    obtenerdatosusuario = obtenermailusuario.verificado(obtenerdatosusuario);
                    String nombremod = "", apellidosmod = "", emailmod = "", contrasenamod = "", dnimod = "", pesomod = "", alturamod = "", forma_actual = "";
                    ControladorUsuario verificaremail = new ControladorUsuario();
                    ControladorUsuario verificardni = new ControladorUsuario();
                    try {
                        if(nombre.getText().toString().equals("")){
                            nombremod = obtenerdatosusuario.getNombre();
                        }else{
                            nombremod = nombre.getText().toString();
                        }
                        if(apellidos.getText().toString().equals("")){
                            apellidosmod = obtenerdatosusuario.getApellidos();
                        }else{
                            apellidosmod = apellidos.getText().toString();
                        }
                        if(email.getText().toString().equals("")){
                            emailmod = obtenerdatosusuario.getEmail();
                        }else{
                            emailmod = email.getText().toString();
                        }
                        if(contrasena.getText().toString().equals("")){
                            contrasenamod = obtenerdatosusuario.getContrasena();
                        }else{
                            contrasenamod = contrasena.getText().toString();
                        }
                        if(dni.getText().toString().equals("")){
                            dnimod = obtenerdatosusuario.getDni();
                        }else{
                            dnimod = dni.getText().toString();
                        }if(peso.getText().toString().equals("")){
                            pesomod = obtenerdatosusuario.getPeso();
                        }else{
                            pesomod = peso.getText().toString();
                        }
                        if(altura.getText().toString().equals("")){
                            alturamod = obtenerdatosusuario.getAltura();
                        }else{
                            alturamod=altura.getText().toString();
                        }
                        if (verificaremail.verificarExistenciaCorreo(email.getText().toString()) == true || verificardni.verificarExistenciaDni(dni.getText().toString()) == true) {
                            new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("ERROR.")
                                    .setContentText("Error dni u email existente.")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                        }
                                    }).show();
                        } else {
                            if(dominadas.getText().toString().equals("")){
                                dominadas.setText(obtenerdatosusuario.getDominadas());
                            }
                            if(minutos.getText().toString().equals("")){
                                String minuto = obtenerdatosusuario.getTiempo_1km();
                                System.out.println(minuto.charAt(0));
                                String numero = String.valueOf(minuto.charAt(0));
                                minutos.setText(numero);
                            }
                            int dominadase = Integer.parseInt(dominadas.getText().toString());
                            int minutose = Integer.parseInt(minutos.getText().toString());
                            if(pressbanca.getText().toString().equals("")){
                                pressbanca.setText(obtenerdatosusuario.getPressBanca());
                            }
                            double pressbancae = Double.parseDouble(pressbanca.getText().toString());
                            if(segundos.getText().toString().equals("")){
                                String tiempo = obtenerdatosusuario.getTiempo_1km();
                                String segundo1 = String.valueOf(tiempo.charAt(2));
                                String segundoscompletos = segundo1 + String.valueOf(tiempo.charAt(3));
                                segundos.setText(segundoscompletos);
                            }
                            if (Integer.parseInt(segundos.getText().toString()) > 60) {
                                new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("ERROR.")
                                        .setContentText("No hay más de 60 segundos.")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                            }
                                        }).show();
                            } else if (Integer.parseInt(minutos.getText().toString()) > 60) {
                                new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("ERROR.")
                                        .setContentText("¡Demasiado tiempo!")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                            }
                                        }).show();
                            }else{
                                String tiempo = minutos.getText().toString()+":"+segundos.getText().toString();
                                // private String nombre, apellidos, email, contrasena, dni, peso, altura, pressBanca, dominadas, forma_actual, token, tiempo_1km, id_grupo, imagen, id_entreno;
                                Usuario usuariomodificado = new Usuario (nombremod, apellidosmod, emailmod, contrasenamod, dnimod, pesomod, alturamod, String.valueOf(pressbancae), String.valueOf(dominadase), "", FirebaseInstanceId.getInstance().getToken(), tiempo, obtenerdatosusuario.getId_grupo(), "", obtenerdatosusuario.getId_entreno());
                                if (dominadase <= 5 && pressbancae <= 50 && Integer.parseInt(minutos.getText().toString()) >= 10) {
                                    usuariomodificado.setForma_actual("Baja forma");
                                } else {
                                    if (dominadase <= 10 && dominadase > 5 && pressbancae <= 80 && pressbancae > 50 && Integer.parseInt(minutos.getText().toString()) > 7 && Integer.parseInt(minutos.getText().toString()) < 10) {
                                        usuariomodificado.setForma_actual("Normal");
                                    } else {
                                        if (dominadase <= 15 && dominadase > 10 && pressbancae <= 110 && pressbancae > 80 && Integer.parseInt(minutos.getText().toString()) > 5 && Integer.parseInt(minutos.getText().toString()) < 7) {
                                            usuariomodificado.setForma_actual("Buena forma");
                                        } else {
                                            if (dominadase > 15 && pressbancae > 110 && Integer.parseInt(minutos.getText().toString()) < 5) {
                                                usuariomodificado.setForma_actual("Atleta");
                                            }
                                        }
                                    }
                                }

                                ControladorUsuario obteneridusuario = new ControladorUsuario();
                                String idmodificar = obteneridusuario.obtenerUsuarioIdPorEmail(obtenerdatosusuario);
                                ControladorUsuario modificardatosusuario = new ControladorUsuario();
                                if(modificardatosusuario.modificarusuario(usuariomodificado, idmodificar) == true){
                                    SharedPreferences  usuario = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor usuarioeditor;
                                    usuarioeditor = usuario.edit();
                                    usuarioeditor.putString("email", (usuariomodificado.getEmail()));
                                    usuarioeditor.putString("nombre", (usuariomodificado.getNombre()));
                                    usuarioeditor.putString("apellidos", (usuariomodificado.getApellidos()));
                                    usuarioeditor.commit();
                                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Modificación realizada.")
                                            .setContentText("La cuenta de "+ usuariomodificado.getNombre()+" "+usuariomodificado.getApellidos() + " se modificó correctamente.")
                                            .setConfirmText("¡Gracias!")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    sDialog.dismissWithAnimation();
                                                    Intent intent = new Intent(editar_perfil_usuario.this, PostIniciosesion_registro.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }).show();
                                }
                            }

                        }
                    } catch (ServidorPHPException e) {
                        e.printStackTrace();
                    }
                    break;
                } catch (ServidorPHPException e) {
                    e.printStackTrace();
                }
        }
    }
}
