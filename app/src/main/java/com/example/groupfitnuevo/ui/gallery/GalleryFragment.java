package com.example.groupfitnuevo.ui.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.groupfitnuevo.R;
import com.example.groupfitnuevo.adaptadores.adaptadorgrupo;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import controladores.ControladorGrupo;
import controladores.Grupo;
import controladores.ServidorPHPException;


public class GalleryFragment extends Fragment implements View.OnClickListener {

    private GalleryViewModel galleryViewModel;
    private Button creargrupo;
    private EditText nombre_grupo_crear;
    private Spinner objetivos_grupo_crear;
    private ControladorGrupo controladorGrupo;
    private RecyclerView lista;
    private adaptadorgrupo adaptador;
    private SwipeRefreshLayout refreshLayout;

    public static String toMayusculas(String valor) {
        if (valor == null || valor.isEmpty()) {
            return valor;
        } else {
            return valor.toUpperCase().charAt(0) + valor.substring(1, valor.length()).toLowerCase();
        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        creargrupo = root.findViewById(R.id.bCrearGrupo);
        creargrupo.setOnClickListener(this);
        lista = root.findViewById(R.id.grupos);
        refreshLayout = root.findViewById(R.id.refresh_grupos);
        refreshLayout.setColorSchemeColors(Color.GREEN, Color.YELLOW, Color.RED);
        ControladorGrupo controladorgrupo2 = new ControladorGrupo();

        // Obtengo los datos de las personas del servidor
        try {
            ArrayList<Grupo> todosgrupos = controladorgrupo2.obtenerGrupos();
            // Hay que crear en la caperta values un fichero dimens.xml y crear ahi list_space

            // Con esto el tamaño del recyclerwiew no cambiará
            lista.setHasFixedSize(true);
            // Creo un layoutmanager para el recyclerview
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            lista.setLayoutManager(llm);

            adaptador = new adaptadorgrupo(getContext(), todosgrupos);
            lista.setAdapter(adaptador);
            adaptador.refrescar();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ControladorGrupo refresh = new ControladorGrupo();
                        refreshLayout.setRefreshing(false);
                        try {
                            ArrayList<Grupo> todosgrupos = refresh.obtenerGrupos();
                            adaptador = new adaptadorgrupo(getContext(), todosgrupos);
                            lista.setAdapter(adaptador);
                            adaptador.refrescar();
                        } catch (ServidorPHPException e) {
                            e.printStackTrace();
                        }

                    }
                }, 1000);

            }
        });
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bCrearGrupo:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                final View dialogo = inflater.inflate(R.layout.layoutcreargrupo, null, false);
                builder.setView(dialogo).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nombre_grupo_crear = dialogo.findViewById(R.id.et_nombre_crea_grupo);
                        objetivos_grupo_crear = dialogo.findViewById(R.id.spinnerobjetivo_crear);
                        if(nombre_grupo_crear.getText().equals("")) {
                            new SweetAlertDialog(dialogo.getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Nombre vacío.")
                                    .setContentText("El campo nombre está vacío.")
                                    .setConfirmText("¡Vale!").show();
                        }else{
                            Grupo grupo = new Grupo(toMayusculas(nombre_grupo_crear.getText().toString()), objetivos_grupo_crear.getSelectedItem().toString(), 1);
                            controladorGrupo = new ControladorGrupo();
                            ControladorGrupo agregar = new ControladorGrupo();
                            try {
                                if (controladorGrupo.obtenerNombreGrupo(grupo.getNombre()) == false) {

                                    if (agregar.agregarGrupo(grupo) == true) {
                                        new SweetAlertDialog(dialogo.getContext(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("Grupo creado.")
                                                .setContentText("El grupo " + grupo.getNombre() + " se creó correctamente.")
                                                .setConfirmText("¡Gracias!").show();
                                        ControladorGrupo controladorgrupo2 = new ControladorGrupo();

                                        // Obtengo los datos de las personas del servidor
                                        try {
                                            ArrayList<Grupo> todosgrupos = controladorgrupo2.obtenerGrupos();
                                            // Hay que crear en la caperta values un fichero dimens.xml y crear ahi list_space
                                            // Con esto el tamaño del recyclerwiew no cambiará
                                            lista.setHasFixedSize(true);
                                            // Creo un layoutmanager para el recyclerview
                                            LinearLayoutManager llm = new LinearLayoutManager(getContext());
                                            lista.setLayoutManager(llm);

                                            adaptador = new adaptadorgrupo(getContext(), todosgrupos);
                                            lista.setAdapter(adaptador);
                                            adaptador.refrescar();
                                        } catch (ServidorPHPException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else {
                                    new SweetAlertDialog(getContext(), cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("ERROR.")
                                            .setContentText("¡Ya existe un grupo con este nombre!")
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
                        }
                    }

                });
                builder.show();
                break;
        }
    }
}
