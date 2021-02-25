package com.example.groupfitnuevo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.groupfitnuevo.adaptadores.adaptador_ejercicios_info_rutina;
import com.example.groupfitnuevo.adaptadores.adaptador_usuarios_grupo;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import controladores.ControladorEjercicio;
import controladores.ControladorRutina;
import controladores.ControladorUsuario;
import controladores.Ejercicio;
import controladores.Rutina;
import controladores.ServidorPHPException;
import de.hdodenhof.circleimageview.CircleImageView;

public class informacionrutina extends AppCompatActivity implements View.OnClickListener {
    private Button editar, eliminar, anadirejericios;
    private EditText nombrerutina, nombreejercicio, musculo, series, repeticiones;
    private Spinner descripcionrutina, nivel;
    private RecyclerView listaejercicios;
    private adaptador_ejercicios_info_rutina adaptador;
    private SwipeRefreshLayout refresh;
    private CircleImageView nivelrutina;

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
        setContentView(R.layout.activity_informacionrutina);
        Toolbar toolbar = findViewById(R.id.toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();

        //Extrayendo el extra de tipo cadena
        final String nombre = intent.getStringExtra("nombrerutina");
        editar = findViewById(R.id.beditarrutina_informacion);
        editar.setOnClickListener(this);
        eliminar = findViewById(R.id.beliminarrutina);
        eliminar.setOnClickListener(this);
        anadirejericios = findViewById(R.id.banadirejercicios_rutina);
        anadirejericios.setOnClickListener(this);
        listaejercicios = findViewById(R.id.recyclerejercicios);
        refresh = findViewById(R.id.refreshejercicios);

        if (nombre.equals("Rutina vacía")) {
            editar.setEnabled(false);
            eliminar.setEnabled(false);
            anadirejericios.setEnabled(false);
        } else {
            editar.setEnabled(true);
            eliminar.setEnabled(true);
            anadirejericios.setEnabled(true);
        }
        // Con esto el tamaño del recyclerwiew no cambiará
        listaejercicios.setHasFixedSize(true);
        // Creo un layoutmanager para el recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
        listaejercicios.setLayoutManager(llm);
        ControladorEjercicio obtenertodosejercicios = new ControladorEjercicio();
        ControladorRutina obteneridrutina = new ControladorRutina();
        try {
            String idrutina = obteneridrutina.obtenerIdRutina(nombre);
            final ArrayList<Ejercicio> todosejercicios = obtenertodosejercicios.obtenerejerciciosrutina(idrutina);
            adaptador = new adaptador_ejercicios_info_rutina(this, todosejercicios);
            listaejercicios.setAdapter(adaptador);
            adaptador.refrescar();
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }
        refresh.setColorSchemeColors(Color.GREEN, Color.YELLOW, Color.RED);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ControladorRutina obteneridrutinarefresh = new ControladorRutina();
                        try {
                            String idrutinarefresh = obteneridrutinarefresh.obtenerIdRutina(nombre);
                            ControladorEjercicio refrescardatos = new ControladorEjercicio();
                            refresh.setRefreshing(false);
                            ArrayList<Ejercicio> todosEjercicios2 = null;

                            todosEjercicios2 = refrescardatos.obtenerejerciciosrutina(idrutinarefresh);
                            adaptador = new adaptador_ejercicios_info_rutina(informacionrutina.this, todosEjercicios2);
                            listaejercicios.setAdapter(adaptador);
                            adaptador.refrescar();
                        } catch (ServidorPHPException e) {
                            e.printStackTrace();
                        }


                    }
                }, 1000);


            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.beditarrutina_informacion:
                AlertDialog.Builder builder = new AlertDialog.Builder(informacionrutina.this);
                LayoutInflater inflater = informacionrutina.this.getLayoutInflater();
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
                }).setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ControladorRutina controladorrutinaobtenerid = new ControladorRutina();
                        Intent intent = getIntent();
                        String nombre = intent.getStringExtra("nombrerutina");
                        String id = null;
                        try {
                            id = controladorrutinaobtenerid.obtenerIdRutina(nombre);
                            nivel = dialogo.findViewById(R.id.spinernivel);
                            nivelrutina = dialogo.findViewById(R.id.circlenivelrutina);
                            nombrerutina = dialogo.findViewById(R.id.et_nombre_crea_rutina);
                            descripcionrutina = dialogo.findViewById(R.id.spinnerdescripcionrutina_crear);


                            if (nombrerutina.getText().equals("")) {
                                new SweetAlertDialog(dialogo.getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Nombre vacío.")
                                        .setContentText("El campo nombre está vacío.")
                                        .setConfirmText("¡Vale!").show();
                            } else {
                                Rutina rutina = new Rutina(toMayusculas(nombrerutina.getText().toString()) + " | " + nivel.getSelectedItem().toString(), descripcionrutina.getSelectedItem().toString());
                                ControladorRutina verificarNombreRutina = new ControladorRutina();
                                ControladorRutina modificarutina = new ControladorRutina();
                                //Extrayendo el extra de tipo cadena
                                if (verificarNombreRutina.comprobarexistencianombre(rutina.getNombre()) == false) {
                                    if (modificarutina.modificarRutina(rutina, id) == true) {
                                        new SweetAlertDialog(dialogo.getContext(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("Rutina modificada.")
                                                .setContentText("La rutina " + nombre + " se modificó correctamente a " + rutina.getNombre())
                                                .setConfirmText("¡Perfecto!").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                            }
                                        }).show();
                                    } else {

                                    }
                                } else {
                                    new SweetAlertDialog(informacionrutina.this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
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
                            }
                        } catch (ServidorPHPException e) {
                            e.printStackTrace();
                        }
                    }

                });
                builder.show();
                break;

            case R.id.beliminarrutina:
                ControladorRutina controladornuevo = new ControladorRutina();
                Intent intent = getIntent();
                String nombre = intent.getStringExtra("nombrerutina");
                ControladorRutina obteneridrutinavacia = new ControladorRutina();

                try {
                    String id = obteneridrutinavacia.obtenerIdRutina("Rutina vacía");

                    ControladorUsuario cambiarentrenousuarios = new ControladorUsuario();
                    ControladorRutina obteneridrutinaactual = new ControladorRutina();
                    String id_grupoactual = obteneridrutinaactual.obtenerIdRutina(nombre);
                    ControladorEjercicio eliminarejerciciosrutina = new ControladorEjercicio();
                    System.out.println(id_grupoactual);
                    eliminarejerciciosrutina.eliminarEjerciciosRutina(id_grupoactual);
                    cambiarentrenousuarios.modifiarEntrenoUsuariosEliminado(id, id_grupoactual);
                    controladornuevo.eliminarrutina(nombre);
                    new SweetAlertDialog(informacionrutina.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("La rutina fue eliminada.")
                            .setContentText("La rutina  " + nombre + " ha sido eliminado.")
                            .setConfirmText("¡Gracias!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    Intent intent = new Intent(informacionrutina.this, postinicioentrenador.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).show();

                } catch (ServidorPHPException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.banadirejercicios_rutina:
                AlertDialog.Builder builderanadir = new AlertDialog.Builder(informacionrutina.this);
                LayoutInflater inflateranadir = informacionrutina.this.getLayoutInflater();
                final View dialogoanadir = inflateranadir.inflate(R.layout.layout_pestana_crear_ejercicio, null, false);
                builderanadir.setView(dialogoanadir).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        //Extrayendo el extra de tipo cadena
                        String nombre = intent.getStringExtra("nombrerutina");
                        ControladorRutina obteneridrutina = new ControladorRutina();
                        try {
                            String idrutina = obteneridrutina.obtenerIdRutina(nombre);

                            musculo = dialogoanadir.findViewById(R.id.musculo_implicado);
                            nombreejercicio = dialogoanadir.findViewById(R.id.nombre_ejercicio);
                            series = dialogoanadir.findViewById(R.id.et_series_crear_ejercicio);
                            repeticiones = dialogoanadir.findViewById(R.id.et_repeticiones_crear_ejercicio);
                            if (musculo.getText().toString().equals("") || nombreejercicio.getText().toString().equals("") || series.getText().toString().equals("") || repeticiones.getText().toString().equals("")) {
                                new SweetAlertDialog(informacionrutina.this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("ERROR.")
                                        .setContentText("No puedes dejar campos vacíos.")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                            }
                                        }).show();

                            } else {
                                ControladorEjercicio controladoranadirejercicio = new ControladorEjercicio();
                                Ejercicio nuevoejercicio = new Ejercicio(idrutina, toMayusculas(musculo.getText().toString()), toMayusculas(nombreejercicio.getText().toString()), toMayusculas(series.getText().toString()), toMayusculas(repeticiones.getText().toString()), "", "");
                                controladoranadirejercicio.agregarEjercicio(nuevoejercicio);
                                new SweetAlertDialog(dialogoanadir.getContext(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("Rutina creada.")
                                        .setContentText("El " + nuevoejercicio.getNombre_ejercicio() + " ejercicio se añadió correctamente.")
                                        .setConfirmText("¡Gracias!").show();
                                // Con esto el tamaño del recyclerwiew no cambiará
                                listaejercicios.setHasFixedSize(true);
                                // Creo un layoutmanager para el recyclerview
                                LinearLayoutManager llm = new LinearLayoutManager(informacionrutina.this);
                                listaejercicios.setLayoutManager(llm);
                                ControladorEjercicio obtenerejercicios = new ControladorEjercicio();
                                ControladorRutina obtenerelidrutina = new ControladorRutina();
                                try {
                                    String idrutinaanadir = obtenerelidrutina.obtenerIdRutina(nombre);
                                    final ArrayList<Ejercicio> todosejercicios = obtenerejercicios.obtenerejerciciosrutina(idrutinaanadir);
                                    adaptador = new adaptador_ejercicios_info_rutina(informacionrutina.this, todosejercicios);
                                    listaejercicios.setAdapter(adaptador);
                                    adaptador.refrescar();
                                } catch (ServidorPHPException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (ServidorPHPException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builderanadir.show();
                break;

        }
    }
}
