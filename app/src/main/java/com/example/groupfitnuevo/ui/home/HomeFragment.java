package com.example.groupfitnuevo.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.groupfitnuevo.R;
import com.example.groupfitnuevo.editar_perfil_usuario;
import com.example.groupfitnuevo.editarperfilentrenador;
import com.example.groupfitnuevo.vistaprevia_aboutpage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ResourceBundle;

import controladores.ControladorEntrenador;
import controladores.Entrenador;
import controladores.ServidorPHPException;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    private TextView nombre, email, telefono, contrasena, descripcion;
    private ImageButton editarinfo;
    private FloatingActionButton vistaprevia;
    private ControladorEntrenador controlador;
    private SharedPreferences entrenador;

    public static String toMayusculas(String valor) {
        if (valor == null || valor.isEmpty()) {
            return valor;
        } else {
            return valor.toUpperCase().charAt(0) + valor.substring(1, valor.length()).toLowerCase();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        nombre = root.findViewById(R.id.tvNombre_edit);
        email = root.findViewById(R.id.tvemail_entrenador_perfil);
        contrasena = root.findViewById(R.id.tv_contrasena_entrenador);
        descripcion = root.findViewById(R.id.tvdescripcion_entrenador);

        vistaprevia = root.findViewById(R.id.floating_vistaprevia);
        vistaprevia.setOnClickListener(this);
        editarinfo = root.findViewById(R.id.ib_entrenador_editar_perfil);
        editarinfo.setOnClickListener(this);
        telefono = root.findViewById(R.id.tvtlf_entrenador_perfil);


        entrenador  = getContext().getSharedPreferences("Entrenador", Context.MODE_PRIVATE);

        nombre.setText(entrenador.getString("nombre", "")+ " "+toMayusculas(entrenador.getString("apellidos", "")) );
        email.setText(entrenador.getString("email", ""));
        contrasena.setText(entrenador.getString("contrasena", ""));
        descripcion.setText(entrenador.getString("descripcion", ""));
        telefono.setText(entrenador.getString("telefono", ""));
        ControladorEntrenador cambiartokenentrenador = new ControladorEntrenador();

        try {
            cambiartokenentrenador.cambiarTokenEntrenador(FirebaseInstanceId.getInstance().getToken(), email.getText().toString());
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.floating_vistaprevia:
                Intent intent = new Intent(getContext(), vistaprevia_aboutpage.class);
                startActivity(intent);
                break;

            case R.id.ib_entrenador_editar_perfil:
                Intent intent2 = new Intent(getContext(), editarperfilentrenador.class);
                startActivity(intent2);
                break;
        }
    }
}
