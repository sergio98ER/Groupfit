package com.example.groupfitnuevo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;

import cn.pedant.SweetAlert.SweetAlertDialog;
import controladores.ControladorEntrenador;
import controladores.Entrenador;
import controladores.ServidorPHPException;

public class editarperfilentrenador extends AppCompatActivity implements View.OnClickListener {

    private ControladorEntrenador controlador;
    private SharedPreferences entrenadorpreferences;
    private EditText nombre, apellidos, email, telefono, contrasena, descripcion;
    private FloatingActionButton guardar;
    private SharedPreferences.Editor entrenadoreditor;
    public static String toMayusculas(String valor) {
        if (valor == null || valor.isEmpty()) {
            return valor;
        } else {
            return valor.toUpperCase().charAt(0) + valor.substring(1, valor.length()).toLowerCase();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarperfilentrenador);
        Toolbar toolbar = findViewById(R.id.toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nombre = findViewById(R.id.etnombre_entrenador_edit);
        apellidos = findViewById(R.id.etapellidos_entrenador_edit);
        email = findViewById(R.id.et_email_edit_entrenador);
        telefono = findViewById(R.id.ettelefonoentrenador_edit);
        contrasena = findViewById(R.id.etcontrasenaentrenador_edit);
        descripcion = findViewById(R.id.etdescripcion_editar);

        guardar = findViewById(R.id.floating_save_entrenador);
        guardar.setOnClickListener(this);


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.floating_save_entrenador:
                entrenadorpreferences = getSharedPreferences("Entrenador", Context.MODE_PRIVATE);
                String nombre1, apellidos1, email1, telefono1, contrasena1, descripcion1;
                nombre1 = entrenadorpreferences.getString("nombre", "");
                apellidos1 = entrenadorpreferences.getString("apellidos", "");
                email1 = entrenadorpreferences.getString("email", "");
                telefono1 = entrenadorpreferences.getString("telefono", "");
                contrasena1 = entrenadorpreferences.getString("contrasena", "");
                descripcion1 = entrenadorpreferences.getString("descripcion", "");
                if(nombre.getText().toString().equals("")){
                    nombre.setText(toMayusculas(nombre1));
                }if(apellidos.getText().toString().equals("")){
                    apellidos.setText(toMayusculas(apellidos1));
                }if(email.getText().toString().equals("")){
                    email.setText(toMayusculas(email1));
                }if(telefono.getText().toString().equals("")){
                    telefono.setText(telefono1);
                }if(contrasena.getText().toString().equals("")){
                    contrasena.setText(contrasena1);
                }if(descripcion.getText().toString().equals("")){
                    descripcion.setText(toMayusculas(descripcion1));
                }

                Entrenador entrenado = new Entrenador(nombre.getText().toString(), apellidos.getText().toString(), "", FirebaseInstanceId.getInstance().getToken(), email.getText().toString(), contrasena.getText().toString(),telefono.getText().toString(), descripcion.getText().toString());

                controlador = new ControladorEntrenador();
                try {
                    controlador.modificarEntrenador(entrenado);
                    entrenadoreditor = entrenadorpreferences.edit();
                    entrenadoreditor.putString("email", toMayusculas(entrenado.getEmail()));
                    entrenadoreditor.putString("contrasena", entrenado.getContrasena());
                    entrenadoreditor.putString("nombre", toMayusculas(entrenado.getNombre()));
                    entrenadoreditor.putString("apellidos", toMayusculas(entrenado.getApellidos()));
                    entrenadoreditor.putString("telefono", entrenado.getTelefono());
                    entrenadoreditor.putString("descripcion", toMayusculas(entrenado.getDescripcion()));
                    entrenadoreditor.putString("token", entrenado.getToken());
                    entrenadoreditor.commit();

                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("¡Modificación correcta!")
                            .setContentText("Se modifico correctamente todo " + entrenadorpreferences.getString("nombre", "") + ".")
                            .setConfirmText("¡Perfecto!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    Intent intent = new Intent(editarperfilentrenador.this, postinicioentrenador.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).show();
                } catch (ServidorPHPException e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}
