package com.example.groupfitnuevo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.sql.SQLOutput;

import cn.pedant.SweetAlert.SweetAlertDialog;
import controladores.ControladorGrupo;
import controladores.ControladorRutina;
import controladores.ControladorUsuario;
import controladores.Grupo;
import controladores.ServidorPHPException;
import controladores.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;


public class perfilusuario_grupo extends AppCompatActivity implements View.OnClickListener {

    private Button eliminar_usuario_grupo, eliminarcuenta, modificarusuario;
    private TextView nombre, email, contrasena, dni, peso, altura, pressbanca, dominadas, tiempo1km, rutina;
    private CircleImageView grupo;

    // String nombre, apellidos, email, contrasena, dni, peso, altura, pressBanca, dominadas, forma_actual, token, tiempo_1km, id_grupo, imagen, id_entreno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfilusuario_grupo);
        Toolbar toolbar = findViewById(R.id.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        grupo = findViewById(R.id.estadoformausuario_grupo);
        nombre = findViewById(R.id.tvNombreUsuarioGrupo);
        email = findViewById(R.id.tvemail_usuario_grupo);
        contrasena = findViewById(R.id.tvcontrasena_usuario_grupo);
        dni = findViewById(R.id.tvdni_grupo_usuario);
        peso = findViewById(R.id.tvpeso_grupo_usuario);
        altura = findViewById(R.id.tvaltura_grupo_usuario);
        pressbanca = findViewById(R.id.pressbanca_usuario_grupo);
        dominadas = findViewById(R.id.dominadas_grupo_usuario);
        tiempo1km = findViewById(R.id.tiempo1km_grupo_usuario);
        rutina = findViewById(R.id.rutina_usuario_grupo);

        Intent intent = getIntent();

        String emailintent = intent.getStringExtra("emailusuariogrupo");
        ControladorUsuario usuariodatos = new ControladorUsuario();

       Usuario datosobtenidos = new Usuario("", "", emailintent, "", "", "", "", "", "", "", "", "", "", "", "");
        try {
            usuariodatos.verificado(datosobtenidos);
            System.out.println(datosobtenidos.toString());
            nombre.setText(datosobtenidos.getNombre() + " " + datosobtenidos.getApellidos());
            email.setText(datosobtenidos.getEmail());
            contrasena.setText(datosobtenidos.getContrasena());
            dni.setText(datosobtenidos.getDni());
            peso.setText(datosobtenidos.getPeso());
            altura.setText(datosobtenidos.getAltura());
            pressbanca.setText(datosobtenidos.getPressBanca());
            dominadas.setText(datosobtenidos.getDominadas());
            tiempo1km.setText(datosobtenidos.getTiempo_1km());

            ControladorRutina obtenernombreidentreno = new ControladorRutina();
            rutina.setText(obtenernombreidentreno.obtenernombrerutinaporid(datosobtenidos.getId_entreno()));

            if (datosobtenidos.getForma_actual().equals("Baja forma") || datosobtenidos.getForma_actual().equals("baja forma")) {
                grupo.setImageResource(R.drawable.bajaformacircular);
            } else {
                if (datosobtenidos.getForma_actual().equals("Normal") || datosobtenidos.getForma_actual().equals("normal")) {
                    grupo.setImageResource(R.drawable.formanormal);
                } else {
                    if (datosobtenidos.getForma_actual().equals("Buena forma") || datosobtenidos.getForma_actual().equals("baja forma")) {
                        grupo.setImageResource(R.drawable.buenaforma);
                    } else {
                        if (datosobtenidos.getForma_actual().equals("Atleta") || datosobtenidos.getForma_actual().equals("atleta")) {
                            grupo.setImageResource(R.drawable.atleta);
                        }
                    }
                }
            }
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }

        eliminar_usuario_grupo = findViewById(R.id.beliminar_cuenta_grupo);
        eliminarcuenta = findViewById(R.id.beliminar_usuariogrupo_cuenta);
        modificarusuario = findViewById(R.id.bmodificar_cuenta_grupo);

        ControladorGrupo verificargrupovacio = new ControladorGrupo();
        try {
            if(datosobtenidos.getId_grupo().equals(verificargrupovacio.obtenerIdGrupo("Usuarios sin grupo"))){
                eliminar_usuario_grupo.setEnabled(false);
            }else{
                eliminar_usuario_grupo.setEnabled(true);
            }
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }

        eliminar_usuario_grupo.setOnClickListener(this);
        eliminarcuenta.setOnClickListener(this);
        modificarusuario.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.beliminar_cuenta_grupo:
                    Usuario usuarioeliminarcuenta = new Usuario("", "", email.getText().toString(), "", "", "", "", "", "", "", "", "", "", "", "");
                    ControladorUsuario controladorobtenerusuario = new ControladorUsuario();
                    Grupo grupovacio = new Grupo("Usuarios sin grupo", "", 1);
                    ControladorGrupo obteneridgrupovacio = new ControladorGrupo();
                try {
                    obteneridgrupovacio.obtenerIdGrupo(grupovacio.getNombre());
                    controladorobtenerusuario.verificado(usuarioeliminarcuenta);
                    ControladorUsuario borrarcuenta = new ControladorUsuario();
                    borrarcuenta.eliminarUsuariogrupo(usuarioeliminarcuenta,  obteneridgrupovacio.obtenerIdGrupo(grupovacio.getNombre()));
                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Cuenta elimanada del grupo.")
                            .setContentText("La cuenta de " + usuarioeliminarcuenta.getEmail() + " con nombre: "+usuarioeliminarcuenta.getNombre()+" "+usuarioeliminarcuenta.getApellidos() +" ha sido eliminada del grupo con éxito.")
                            .setConfirmText("¡Gracias!").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            Intent intent = new Intent(perfilusuario_grupo.this, postinicioentrenador.class);
                            startActivity(intent);
                            finish();
                        }
                    }).show();
                } catch (ServidorPHPException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.beliminar_usuariogrupo_cuenta:
                Usuario usuarioeliminardefinitivo = new Usuario("", "", email.getText().toString(), "", "", "", "", "", "", "", "", "", "", "", "");
                ControladorUsuario controladoreliminarusuario = new ControladorUsuario();
                try {
                    controladoreliminarusuario.verificarcorreo(usuarioeliminardefinitivo);
                    String email = usuarioeliminardefinitivo.getEmail();
                    String nombre = usuarioeliminardefinitivo.getNombre();
                    String Apellidos = usuarioeliminardefinitivo.getApellidos();
                    ControladorUsuario borrarcuenta = new ControladorUsuario();
                    borrarcuenta.eliminarUsuario(usuarioeliminardefinitivo.getEmail());

                        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Cuenta eliminada.")
                                .setContentText("La cuenta de " + email + " con nombre: "+nombre+" "+Apellidos +" ha sido eliminada con exito.")
                                .setConfirmText("¡Gracias!").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Intent intent = new Intent(perfilusuario_grupo.this, postinicioentrenador.class);
                                startActivity(intent);
                                finish();
                            }
                        }).show();
                } catch (ServidorPHPException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.bmodificar_cuenta_grupo:
                    Usuario usuariomodificar = new Usuario("", "", email.getText().toString(), "", "", "", "", "", "", "", "", "", "", "", "");
                    ControladorUsuario controladorobteneridmodificar = new ControladorUsuario();
                Intent intent = new Intent(perfilusuario_grupo.this, editarperfilusuario_desde_entrenador.class);
                try {
                    String id_usuario_intent = controladorobteneridmodificar.obtenerUsuarioIdPorEmail(usuariomodificar);
                    intent.putExtra("id", id_usuario_intent);
                    startActivity(intent);
                } catch (ServidorPHPException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
