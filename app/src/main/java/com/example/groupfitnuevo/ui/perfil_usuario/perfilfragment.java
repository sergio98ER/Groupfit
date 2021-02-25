package com.example.groupfitnuevo.ui.perfil_usuario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

import com.example.groupfitnuevo.PostIniciosesion_registro;
import com.example.groupfitnuevo.R;
import com.example.groupfitnuevo.editar_perfil_usuario;
import com.example.groupfitnuevo.editarperfilentrenador;
import com.example.groupfitnuevo.ui.perfil_usuario.PerfilViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import controladores.ControladorGrupo;
import controladores.ControladorRutina;
import controladores.ControladorUsuario;
import controladores.Grupo;
import controladores.Rutina;
import controladores.ServidorPHPException;
import controladores.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class perfilfragment extends Fragment implements View.OnClickListener {
    private static final int SELECT_FILE = 1;
    private PerfilViewModel galleryViewModel;
    private SharedPreferences preferencias;
    private SharedPreferences.Editor editorpreferencias;
    private CircleImageView foto;
    private FloatingActionButton editarfoto;
    private TextView nombre, correo, altura, peso, rutina, dni, objetivo, pressbanca, dominadas, texto1km;
    private ImageButton editarperfil;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(PerfilViewModel.class);
        View root = inflater.inflate(R.layout.perfil, container, false);

        nombre = root.findViewById(R.id.tvNombre_edit);
        correo = root.findViewById(R.id.tvEmail_edit);
        dni = root.findViewById(R.id.tvDni_edit);
        altura = root.findViewById(R.id.tvAltura_edit);
        peso = root.findViewById(R.id.tvPeso_edit);
        rutina = root.findViewById(R.id.rutina_edit);
        foto = root.findViewById(R.id.circlefotoperfil);
        editarperfil = root.findViewById(R.id.ibeditar);
        objetivo = root.findViewById(R.id.objetivoperfilusuario);
        pressbanca = root.findViewById(R.id.pressbancaperfil);
        dominadas = root.findViewById(R.id.dominadas_perfil);
        texto1km = root.findViewById(R.id.tiempo1km_perfil);
        editarperfil.setOnClickListener(this);
        // private String nombre, apellidos, email, contrasena, dni, peso, altura, pressBanca, dominadas, forma_actual, token, tiempo_1km, id_grupo, imagen, id_entreno;
        final SharedPreferences preferencias = getContext().getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        String email = preferencias.getString("email", "");
        ControladorUsuario obtenerusuariomail = new ControladorUsuario();
        Usuario usuarioobtenerpreferences = new Usuario("", "", email, "", "", "", "", "", "", "", "","", "", "", "");

        try {
            obtenerusuariomail.verificado(usuarioobtenerpreferences);

            nombre.setText(usuarioobtenerpreferences.getNombre()+" "+usuarioobtenerpreferences.getApellidos());
            correo.setText(usuarioobtenerpreferences.getEmail());
            dni.setText(usuarioobtenerpreferences.getDni());
            altura.setText(usuarioobtenerpreferences.getAltura());
            peso.setText(usuarioobtenerpreferences.getPeso());
            pressbanca.setText(usuarioobtenerpreferences.getPressBanca());
            dominadas.setText(usuarioobtenerpreferences.getDominadas());
            texto1km.setText(usuarioobtenerpreferences.getTiempo_1km());
            ControladorRutina rutinaporid = new ControladorRutina();
            Rutina nuevarutina = new Rutina();
            nuevarutina = rutinaporid.obtenerRutinaPorID(usuarioobtenerpreferences.getId_entreno());
            rutina.setText(nuevarutina.getNombre());
            objetivo.setText(nuevarutina.getDescripcion());
            if (usuarioobtenerpreferences.getForma_actual().equals("Baja forma") || usuarioobtenerpreferences.getForma_actual().equals("baja forma")) {
                foto.setImageResource(R.drawable.bajaformacircular);
            } else {
                if (usuarioobtenerpreferences.getForma_actual().equals("Normal") || usuarioobtenerpreferences.getForma_actual().equals("normal")) {
                    foto.setImageResource(R.drawable.formanormal);
                } else {
                    if (usuarioobtenerpreferences.getForma_actual().equals("Buena forma") || usuarioobtenerpreferences.getForma_actual().equals("baja forma")) {
                        foto.setImageResource(R.drawable.buenaforma);
                    } else {
                        if (usuarioobtenerpreferences.getForma_actual().equals("Atleta") || usuarioobtenerpreferences.getForma_actual().equals("atleta")) {
                            foto.setImageResource(R.drawable.atleta);
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
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.ibeditar:
                SharedPreferences preferencias = getContext().getSharedPreferences("Usuario", Context.MODE_PRIVATE);
                String email = preferencias.getString("email", "");
                Intent intent = new Intent(getContext(), editar_perfil_usuario.class);
                intent.putExtra("email", email);
                startActivity(intent);
                break;

            case R.id.ib_entrenador_editar_perfil:
                Intent intent2 = new Intent(getContext(), editarperfilentrenador.class);
                startActivity(intent2);
                break;
        }
    }

}
