package com.example.groupfitnuevo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;

import controladores.ControladorEntrenador;
import controladores.Entrenador;
import controladores.ServidorPHPException;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class vistaprevia_aboutpage extends AppCompatActivity {
    private ControladorEntrenador controlador;
    private SharedPreferences entrenadorpreferences;
    private Element contacto, telefono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        contacto = new Element();
        contacto.setTitle(getString(R.string.ContactaConmigo));
        contacto.setGravity(Gravity.CENTER);
        entrenadorpreferences = getSharedPreferences("Entrenador", Context.MODE_PRIVATE);
        Entrenador entrenadorperfil = new Entrenador("", "", "", FirebaseInstanceId.getInstance().getToken(), entrenadorpreferences.getString("email",""), entrenadorpreferences.getString("contrasena", ""), "","");
        controlador = new ControladorEntrenador();

        try {
            controlador.verificarEntrenador(entrenadorperfil);
            String descripcion = entrenadorperfil.getDescripcion();
            System.out.println(descripcion);
            telefono = new Element();
            telefono.setIconDrawable(R.drawable.phone_forward);
            telefono.setTitle(entrenadorperfil.getTelefono());
            View root = new AboutPage(this).isRTL(false).setImage(R.drawable.foto_informacion).setDescription(""+descripcion).addItem(contacto).addEmail(entrenadorperfil.getEmail(), entrenadorperfil.getEmail()).addItem(telefono).create();

            setContentView(root);
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
