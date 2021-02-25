package com.example.groupfitnuevo.ui.slideshow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.groupfitnuevo.R;
import com.example.groupfitnuevo.adaptadores.adaptador_usuarios_grupo;
import com.example.groupfitnuevo.adaptadores.adaptadorgrupo;
import com.example.groupfitnuevo.adaptadores.adaptadorrutinas;
import com.example.groupfitnuevo.listausuariosgrupo;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import controladores.ControladorRutina;
import controladores.ControladorUsuario;
import controladores.Rutina;
import controladores.ServidorPHPException;
import controladores.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

public class SlideshowFragment extends Fragment implements View.OnClickListener {

    private SlideshowViewModel slideshowViewModel;
    private Button crearrutina;
    private EditText nombrerutina;
    private Spinner descripcionrutina, nivel;
    private RecyclerView listarutinas;
    private adaptadorrutinas adaptador;
    private SwipeRefreshLayout refreshLayout;
    private CircleImageView nivelrutina;

    public static String toMayusculas(String valor) {
        if (valor == null || valor.isEmpty()) {
            return valor;
        } else {
            return valor.toUpperCase().charAt(0) + valor.substring(1, valor.length()).toLowerCase();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        crearrutina = root.findViewById(R.id.bcrearrutina);
        crearrutina.setOnClickListener(this);
        listarutinas = root.findViewById(R.id.recyclerrutinas);
        ControladorRutina controladortodorutinas = new ControladorRutina();

        try {
            final ArrayList<Rutina> todaslasrutinas = controladortodorutinas.obtenertodaslasrutinas();
            // Con esto el tamaño del recyclerwiew no cambiará
            listarutinas.setHasFixedSize(true);
            // Creo un layoutmanager para el recyclerview
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            listarutinas.setLayoutManager(llm);

            adaptador = new adaptadorrutinas(getContext(), todaslasrutinas);
            listarutinas.setAdapter(adaptador);
            adaptador.refrescar();
            refreshLayout = root.findViewById(R.id.refresh_rutinas);
            refreshLayout.setColorSchemeColors(Color.GREEN, Color.YELLOW, Color.RED);
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ControladorRutina todasrutinasrefresh = new ControladorRutina();
                            refreshLayout.setRefreshing(false);

                            try {
                                ArrayList<Rutina>  todasrutinasrefrescadas = todasrutinasrefresh.obtenertodaslasrutinas();
                                adaptador = new adaptadorrutinas(getContext(), todasrutinasrefrescadas);
                                listarutinas.setAdapter(adaptador);
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
            case R.id.bcrearrutina:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                final View dialogo = inflater.inflate(R.layout.layout_crear_rutina, null, false);
                final Spinner spinner = dialogo.findViewById(R.id.spinernivel);
                final CircleImageView imagennivel = dialogo.findViewById(R.id.circlenivelrutina);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String items = spinner.getSelectedItem().toString();
                        System.out.println(items);
                        if (items.equals("Baja forma")) {
                            imagennivel.setImageResource(R.drawable.bajaformacircular);
                        }else if (items.equals("Normal")) {
                            imagennivel.setImageResource(R.drawable.formanormal);
                        } else if (items.equals("Buena forma")) {
                            imagennivel.setImageResource(R.drawable.buenaforma);
                        }else if (items.equals("Atleta")) {
                            imagennivel.setImageResource(R.drawable.atleta);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                builder.setView(dialogo).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nivel = dialogo.findViewById(R.id.spinernivel);
                        nivelrutina = dialogo.findViewById(R.id.circlenivelrutina);
                        nombrerutina = dialogo.findViewById(R.id.et_nombre_crea_rutina);
                        descripcionrutina = dialogo.findViewById(R.id.spinnerdescripcionrutina_crear);
                        if(nombrerutina.getText().equals("")) {
                            new SweetAlertDialog(dialogo.getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Nombre vacío.")
                                    .setContentText("El campo nombre está vacío.")
                                    .setConfirmText("¡Vale!").show();
                        }else{
                            Rutina rutina = new Rutina(toMayusculas(nombrerutina.getText().toString()+" | "+toMayusculas(nivel.getSelectedItem().toString())), descripcionrutina.getSelectedItem().toString());
                            ControladorRutina verificarNombreRutina = new ControladorRutina();
                            ControladorRutina agregarrutina = new ControladorRutina();
                            try {
                                if (verificarNombreRutina.comprobarexistencianombre(rutina.getNombre()) == false) {

                                    if (agregarrutina.agregarRutina(rutina) == true) {
                                        new SweetAlertDialog(dialogo.getContext(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("Rutina creada.")
                                                .setContentText("La rutina " + rutina.getNombre() + " se creó correctamente.")
                                                .setConfirmText("¡Gracias!").show();
                                        ControladorRutina obtenerrutinastrasagregar = new ControladorRutina();

                                        // Obtengo los datos de las personas del servidor
                                        try {
                                            ArrayList<Rutina> todasrutinasnuevas = obtenerrutinastrasagregar.obtenertodaslasrutinas();
                                            // Hay que crear en la caperta values un fichero dimens.xml y crear ahi list_space
                                            // Con esto el tamaño del recyclerwiew no cambiará
                                            listarutinas.setHasFixedSize(true);
                                            // Creo un layoutmanager para el recyclerview
                                            LinearLayoutManager llm = new LinearLayoutManager(getContext());
                                            listarutinas.setLayoutManager(llm);

                                            adaptador = new adaptadorrutinas(getContext(), todasrutinasnuevas);
                                            listarutinas.setAdapter(adaptador);
                                            adaptador.refrescar();
                                        } catch (ServidorPHPException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else {
                                    new SweetAlertDialog(getContext(), cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("ERROR.")
                                            .setContentText("¡Ya existe una rutina con este nombre!")
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

