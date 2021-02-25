package com.example.groupfitnuevo.ui.informacionentrenador;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.groupfitnuevo.R;
import com.google.firebase.iid.FirebaseInstanceId;

import controladores.ControladorEntrenador;
import controladores.Entrenador;
import controladores.ServidorPHPException;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class InformacionEntrenadorFragment extends Fragment {

    private com.example.groupfitnuevo.ui.informacionentrenador.InformacionEntrenadorViewModel slideshowViewModel;
    private Element telefono, contacto;
    private ControladorEntrenador controlador;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        contacto = new Element();
        contacto.setTitle(getString(R.string.ContactaConmigo));
        contacto.setGravity(Gravity.CENTER);
         Entrenador entrenadorencargado = new Entrenador();
        controlador = new ControladorEntrenador();


        try {
            entrenadorencargado = controlador.obtenerEntrenadorPorId("1");
             System.out.println(entrenadorencargado.toString());
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }

            telefono = new Element();
            telefono.setIconDrawable(R.drawable.phone_forward);
            telefono.setTitle(entrenadorencargado.getTelefono());
            View root = new AboutPage(getContext()).isRTL(false).setImage(R.drawable.foto_informacion).setDescription("" + entrenadorencargado.getDescripcion()).addItem(contacto).addEmail(entrenadorencargado.getEmail(), entrenadorencargado.getEmail()).addItem(telefono).create();

        return root;
    }

}