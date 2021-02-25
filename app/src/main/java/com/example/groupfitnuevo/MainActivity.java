package com.example.groupfitnuevo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ResourceBundle;

import cn.pedant.SweetAlert.SweetAlertDialog;
import controladores.ControladorEmails;
import controladores.ControladorEntrenador;
import controladores.ControladorGrupo;
import controladores.ControladorRutina;
import controladores.ControladorUsuario;
import controladores.Entrenador;
import controladores.Grupo;
import controladores.MailException;
import controladores.Rutina;
import controladores.ServidorPHPException;
import controladores.Usuario;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button registrar, inises, recuperar;
    private ImageButton registrado, iniciarsesion;
    private LinearLayout Ini, Reg;
    private EditText etmaillogin, etcontrsenalogin, etnombrereg, etapellidosreg, etmailreg, etcontrasenareg, etverificarconrtasenareg, mailrecup, etdni_reg, etpeso_reg, etaltura_reg, etpressbanca_reg, etdominadas_reg, etminutos_reg, etsegundos, reg;
    private ControladorUsuario controladorUsuario;
    private CheckBox cbRecordar;
    private SharedPreferences usuario, entrenador;
    private SharedPreferences.Editor usuarioeditor, entrenadoreditor;
    private ControladorEntrenador controladorEntrenador;

    public static String toMayusculas(String valor) {
        if (valor == null || valor.isEmpty()) {
            return valor;
        } else {
            return valor.toUpperCase().charAt(0) + valor.substring(1, valor.length()).toLowerCase();
        }
    }

    //etdni_reg, etaltura_reg, etpressbanca_reg, etdominadas_reg, etminutos_reg, etsegundos,reg
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String comprobarnombrerutina = "Rutina vacía";
        ControladorRutina controladorutinacomienza = new ControladorRutina();
        try {
            if (controladorutinacomienza.comprobarexistencianombre(comprobarnombrerutina) == false) {
                Rutina rutinaprueba = new Rutina(comprobarnombrerutina, "");
                ControladorRutina anadirrutinaprueba = new ControladorRutina();
                anadirrutinaprueba.agregarRutina(rutinaprueba);
            } else {
                System.out.println("La rutina estaba ya creada");
            }
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }
        String nogrupo = "Usuarios sin grupo";
        ControladorGrupo verificarnombregrupo = new ControladorGrupo();
        try {
            if (verificarnombregrupo.obtenerNombreGrupo(nogrupo) == false) {
                Grupo getnesingrupo = new Grupo(nogrupo, "", 1);
                ControladorGrupo anadirgruponoexistente = new ControladorGrupo();
                anadirgruponoexistente.agregarGrupo(getnesingrupo);

            } else {
                System.out.println("El grupo estaba ya creado");
            }
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }
        etdni_reg = findViewById(R.id.et_dni_reg);
        etaltura_reg = findViewById(R.id.et_altura_reg);
        etpeso_reg = findViewById(R.id.et_peso_reg);
        etpressbanca_reg = findViewById(R.id.etPressbanca_reg);
        etdominadas_reg = findViewById(R.id.etDominadas_reg);
        etminutos_reg = findViewById(R.id.etminutos_reg);
        etsegundos = findViewById(R.id.etsegundos_reg);
        etmailreg = findViewById(R.id.etEmailReg);
        iniciarsesion = findViewById(R.id.bInicioSesion);
        iniciarsesion.setOnClickListener(this);
        registrado = findViewById(R.id.bRegistrar);
        registrado.setOnClickListener(this);
        registrar = findViewById(R.id.bRegistrarIni);
        registrar.setOnClickListener(this);
        inises = findViewById(R.id.bIniciarSesion);
        inises.setOnClickListener(this);
        cbRecordar = findViewById(R.id.cbRecordarDatos);
        Ini = findViewById(R.id.layoutIniciarSesion);
        Reg = findViewById(R.id.layoutRegistrar);
        etmaillogin = findViewById(R.id.etEmailLogin);
        etcontrsenalogin = findViewById(R.id.etContrasenaLogin);
        etnombrereg = findViewById(R.id.etNombreReg);
        etapellidosreg = findViewById(R.id.etApellidosReg);
        etmailreg = findViewById(R.id.etEmailReg);
        etcontrasenareg = findViewById(R.id.etContrasenaReg);
        recuperar = findViewById(R.id.bRecupearContrasena);
        recuperar.setOnClickListener(this);
        etverificarconrtasenareg = findViewById(R.id.etContrasenaVerifReg);


        final SharedPreferences recordar = getSharedPreferences("Recordar", Context.MODE_PRIVATE);
        etmaillogin.setText(recordar.getString("correorecordar", ""));
        etcontrsenalogin.setText(recordar.getString("contrasenarecordar", ""));
        cbRecordar.setChecked(recordar.getBoolean("cb", false));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bRecupearContrasena:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                final View dialogo = inflater.inflate(R.layout.layout_recuperarcontrasena, null, false);
                builder.setTitle("Recuperar contraseña.");
                builder.setView(dialogo).setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Recuperar contraseña", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mailrecup = dialogo.findViewById(R.id.MailRecuperar);
                        boolean email = true;
                        ControladorEmails ctrlem = new ControladorEmails();
                        try {
                            email = ctrlem.enviarEmailUsuario(mailrecup.getText().toString());
                            if (email == true) {
                                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("¡Enviado!.")
                                        .setContentText("Email enviado a " + mailrecup.getText().toString())
                                        .setConfirmText("¡Gracias!")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                            }
                                        }).show();
                            } else {
                                new SweetAlertDialog(MainActivity.this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("ERROR.")
                                        .setContentText("No existe el correo.")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                            }
                                        }).show();
                            }

                        } catch (MailException e) {
                            e.printStackTrace();
                        } catch (ServidorPHPException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.show();
                break;

            case R.id.bRegistrarIni:
                Ini.setVisibility(View.GONE);
                Reg.setVisibility(View.VISIBLE);
                break;
            case R.id.bIniciarSesion:
                Ini.setVisibility(View.VISIBLE);
                Reg.setVisibility(View.GONE);
                break;
            case R.id.bInicioSesion:
                if (etmaillogin.getText().equals("")) {
                    new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ERROR.")
                            .setContentText("Error campo 'Email' vacio.")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();
                } else if (etcontrsenalogin.getText().equals("")) {
                    new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ERROR.")
                            .setContentText("Error campo 'Contraseña' vacio.")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();
                } else {
                    //Entrenador -> nombre, apellidos, imagen, token, email, contrasena, telefono, descripcion;
                    Entrenador entrenadorverificar = new Entrenador("", "", "", FirebaseInstanceId.getInstance().getToken(), etmaillogin.getText().toString(), etcontrsenalogin.getText().toString(), "", "");
                    controladorEntrenador = new ControladorEntrenador();
                    try {
                        if (controladorEntrenador.verificarEntrenador(entrenadorverificar) == true) {

                            entrenador = getSharedPreferences("Entrenador", Context.MODE_PRIVATE);
                            entrenadoreditor = entrenador.edit();
                            entrenadoreditor.putString("email", toMayusculas(entrenadorverificar.getEmail()));
                            entrenadoreditor.putString("contrasena", entrenadorverificar.getContrasena());
                            entrenadoreditor.putString("nombre", toMayusculas(entrenadorverificar.getNombre()));
                            entrenadoreditor.putString("apellidos", toMayusculas(entrenadorverificar.getApellidos()));
                            entrenadoreditor.putString("telefono", entrenadorverificar.getTelefono());
                            entrenadoreditor.putString("descripcion", toMayusculas(entrenadorverificar.getDescripcion()));
                            entrenadoreditor.putString("token", entrenadorverificar.getToken());
                            entrenadoreditor.commit();
                            if (cbRecordar.isChecked()) {
                                SharedPreferences.Editor recordareditor = getSharedPreferences("Recordar", Context.MODE_PRIVATE).edit();
                                recordareditor.putString("correorecordar", entrenador.getString("email", ""));
                                recordareditor.putString("contrasenarecordar", entrenador.getString("contrasena", ""));
                                recordareditor.putBoolean("cb", true);
                                recordareditor.commit();
                            } else {
                                SharedPreferences.Editor recordareditor = getSharedPreferences("Recordar", Context.MODE_PRIVATE).edit();
                                recordareditor.putString("correorecordar", "");
                                recordareditor.putString("contrasenarecordar", "");
                                recordareditor.putBoolean("cb", false);
                                recordareditor.commit();
                            }
                            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Bienvenido.")
                                    .setContentText("Bienvenido a Groupfit " + entrenador.getString("nombre", "") + ".")
                                    .setConfirmText("¡Gracias!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            Intent intent = new Intent(MainActivity.this, postinicioentrenador.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).show();

                        }else{
                            // private String nombre, apellidos, email, contrasena, dni, peso, altura, pressBanca, dominadas, forma_actual, token, tiempo_1km, id_grupo, imagen, id_entreno;
                            final Usuario usuarioverificar = new Usuario("", "", etmaillogin.getText().toString(), etcontrsenalogin.getText().toString(), "", "", "", "", "", "", "", "", "", "","");
                            ControladorUsuario loguearusuario = new ControladorUsuario();

                            if(loguearusuario.verificarUsuario(usuarioverificar) == true){
                                usuario = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
                                usuarioeditor = usuario.edit();
                                usuarioeditor.putString("email", toMayusculas(usuarioverificar.getEmail()));
                                usuarioeditor.putString("nombre", toMayusculas(usuarioverificar.getNombre()));
                                usuarioeditor.putString("apellidos", toMayusculas(usuarioverificar.getApellidos()));
                                usuarioeditor.commit();
                                if (cbRecordar.isChecked()) {
                                    SharedPreferences.Editor recordareditor = getSharedPreferences("Recordar", Context.MODE_PRIVATE).edit();
                                    recordareditor.putString("correorecordar", usuarioverificar.getEmail());
                                    recordareditor.putString("contrasenarecordar", usuarioverificar.getContrasena());
                                    recordareditor.putBoolean("cb", true);
                                    recordareditor.commit();
                                } else {
                                    SharedPreferences.Editor recordareditor = getSharedPreferences("Recordar", Context.MODE_PRIVATE).edit();
                                    recordareditor.putString("correorecordar", "");
                                    recordareditor.putString("contrasenarecordar", "");
                                    recordareditor.putBoolean("cb", false);
                                    recordareditor.commit();
                                }
                                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Bienvenido.")
                                        .setContentText("Bienvenido a Groupfit " + usuarioverificar.getNombre() + ".")
                                        .setConfirmText("¡Gracias!")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();

                                                Intent intent = new Intent(MainActivity.this, PostIniciosesion_registro.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).show();
                            }else{
                                if (controladorEntrenador.verificarEntrenador(entrenadorverificar) == false) {
                                    new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("ERROR.")
                                            .setContentText("Datos incorrectos.")
                                            .setConfirmText("OK")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    sDialog.dismissWithAnimation();
                                                }
                                            }).show();
                                }
                            }
                        }

                    } catch (ServidorPHPException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case R.id.bRegistrar:
                if (etmailreg.getText().equals("")) {
                    new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ERROR.")
                            .setContentText("Error campo 'Email' vacio.")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();
                } else if (etnombrereg.getText().equals("")) {
                    new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ERROR.")
                            .setContentText("Error campo 'Nombre' vacio.")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();
                } else if (etapellidosreg.getText().equals("")) {
                    new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ERROR.")
                            .setContentText("Error campo 'Apellidos' vacio.")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();
                } else if (etcontrasenareg.getText().equals("")) {
                    new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ERROR.")
                            .setContentText("Error campo 'Contraseña' vacio.")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();
                } else if (etverificarconrtasenareg.getText().equals("")) {
                    new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ERROR.")
                            .setContentText("Error campo 'Repetir Contraseña' vacio.")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();
                } else if (!etcontrasenareg.getText().toString().equals(etverificarconrtasenareg.getText().toString()) || !etverificarconrtasenareg.getText().toString().equals(etcontrasenareg.getText().toString())) {
                    new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ERROR.")
                            .setContentText("Error no coinciden las contraseñas.")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();
                } else if (etdni_reg.getText().toString().equals("")) {
                    new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ERROR.")
                            .setContentText("Error campo 'DNI' vacio.")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();
                } else if (etpeso_reg.getText().toString().equals("")) {
                    new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ERROR.")
                            .setContentText("Error campo 'Peso' vacio.")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();
                } else if (etaltura_reg.getText().toString().equals("")) {
                    new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ERROR.")
                            .setContentText("Error campo 'Altura' vacio.")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();
                } else {
                    ControladorUsuario verificaremail = new ControladorUsuario();
                    ControladorUsuario verificardni = new ControladorUsuario();
                    try {
                        if (verificaremail.verificarExistenciaCorreo(etmailreg.getText().toString()) == true || verificardni.verificarExistenciaDni(etdni_reg.getText().toString()) == true) {
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
                            int dominadas = Integer.parseInt(etdominadas_reg.getText().toString()), minutos =  Integer.parseInt(etminutos_reg.getText().toString());
                            double pressbanca = Double.parseDouble(etpressbanca_reg.getText().toString());
                             if (Integer.parseInt(etsegundos.getText().toString()) > 60) {
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
                            } else if (Integer.parseInt(etminutos_reg.getText().toString()) > 60) {
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
                            } else {

                                String tiempo = etminutos_reg.getText().toString()+":"+etsegundos.getText().toString();
                                ControladorGrupo obteneridsingrupo = new ControladorGrupo();
                                String idsingrupo = obteneridsingrupo.obtenerIdGrupo("Usuarios sin grupo");
                                ControladorRutina obteneridrutinavacia = new ControladorRutina();
                                String idvacia = obteneridrutinavacia.obtenerIdRutina("Rutina vacía");
                                String forma_actual_usuario = "";
                                // private String nombre, apellidos, email, contrasena, dni, peso, altura, pressBanca, dominadas, forma_actual, token, tiempo_1km, id_grupo, imagen, id_entreno;
                                final Usuario registrado = new Usuario(toMayusculas(etnombrereg.getText().toString()), toMayusculas(etapellidosreg.getText().toString()), toMayusculas(etmailreg.getText().toString()), etcontrasenareg.getText().toString(), etdni_reg.getText().toString(), etpeso_reg.getText().toString(), etaltura_reg.getText().toString(), etpressbanca_reg.getText().toString(), etdominadas_reg.getText().toString(), toMayusculas(forma_actual_usuario), FirebaseInstanceId.getInstance().getToken(), tiempo, idsingrupo, "", idvacia);

                                if (dominadas <= 5 && pressbanca <= 50 && minutos >= 10) {
                                    registrado.setForma_actual("Baja forma");
                                } else {
                                    if (dominadas <= 10 && dominadas > 5 && pressbanca <= 80 && pressbanca > 50 && minutos > 7 && minutos < 10) {
                                        registrado.setForma_actual("Normal");
                                    } else {
                                        if (dominadas <= 15 && dominadas > 10 && pressbanca <= 110 && pressbanca > 80 && minutos > 5 && minutos < 7) {
                                            registrado.setForma_actual("Buena forma");
                                        } else {
                                            if (dominadas > 15 && pressbanca > 110 && minutos < 5) {
                                                registrado.setForma_actual("Atleta");
                                            }

                                        }
                                    }
                                }
                                final ControladorUsuario registrarusuario = new ControladorUsuario();
                                if(registrarusuario.agregarUsuario(registrado) == true){
                                    usuario = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
                                    usuarioeditor = usuario.edit();
                                    usuarioeditor.putString("email", toMayusculas(registrado.getEmail()));
                                    usuarioeditor.putString("nombre", toMayusculas(registrado.getNombre()));
                                    usuarioeditor.putString("apellidos", toMayusculas(registrado.getApellidos()));
                                    usuarioeditor.commit();
                                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Registro correcto.")
                                            .setContentText("Bienvenido a Groupfit "+registrado.getNombre()+" "+registrado.getApellidos() +".")
                                            .setConfirmText("¡Gracias!")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    sDialog.dismissWithAnimation();
                                                    Intent intent = new Intent(MainActivity.this, PostIniciosesion_registro.class);

                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }).show();
                                }else{
                                    new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("ERROR.")
                                            .setContentText("¡Error al registrar!")
                                            .setConfirmText("OK")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    sDialog.dismissWithAnimation();
                                                }
                                            }).show();
                                }
                            }
                        }

                    } catch (ServidorPHPException e) {
                        e.printStackTrace();
                    }
                }

                    /*
                    Usuario nuevousuario = new Usuario(toMayusculas(etnombrereg.getText().toString()), toMayusculas(etapellidosreg.getText().toString()), toMayusculas(etmailreg.getText().toString()), etcontrasenareg.getText().toString(), "Usuario", "123");
                    controladorUsuario = new ControladorUsuario();
                    preferencias = getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
                    editorpreferencias = preferencias.edit();
                    editorpreferencias.putString("correo", toMayusculas(nuevousuario.getCorreo()));
                    editorpreferencias.putString("contrasena", nuevousuario.getContrasena());
                    editorpreferencias.putString("nombre", toMayusculas(nuevousuario.getNombre()));
                    editorpreferencias.putString("apellidos", toMayusculas(nuevousuario.getApellidos()));
                    editorpreferencias.commit();
                    try {
                        if(controladorUsuario.correo(nuevousuario) == false){
                            if(controladorUsuario.agregarUsuario(nuevousuario) == true && nuevousuario.getTipo_usuario().equals("Usuario")){
                                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Bienvenido.")
                                        .setContentText("Bienvenido a Groupfit " + preferencias.getString("nombre", "") + ".")
                                        .setConfirmText("¡Gracias!")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                                Intent intent = new Intent(MainActivity.this, PostIniciosesion_registro.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).show();
                        }else{
                                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Bienvenido.")
                                        .setContentText("Bienvenido a Groupfit " + preferencias.getString("nombre", "") + ".")
                                        .setConfirmText("¡Gracias!")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                                Intent intent = new Intent(MainActivity.this, PostIniciosesion_registro.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).show();
                            }

                        }else{
                            new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("ERROR.")
                                    .setContentText("¡Usuario ya registrado!")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                        }
                                    }).show();
                        }
                    } catch (ServidorPHPException e) {
                        e.printStackTrace();
                    }

                }*/

                break;
        }
    }
}
