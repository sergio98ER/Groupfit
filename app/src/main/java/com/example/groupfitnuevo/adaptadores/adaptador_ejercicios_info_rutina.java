package com.example.groupfitnuevo.adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.groupfitnuevo.R;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import controladores.ControladorEjercicio;
import controladores.Ejercicio;
import controladores.ServidorPHPException;

public class adaptador_ejercicios_info_rutina extends RecyclerView.Adapter<adaptador_ejercicios_info_rutina.HolderEjerciciosRutina> {


    public static class HolderEjerciciosRutina extends RecyclerView.ViewHolder {
        TextView nombre, musculo, series, repeticiones;
        ImageButton delete, edit;

        HolderEjerciciosRutina(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tvNombreEjercicioRecycler);
            musculo = itemView.findViewById(R.id.tvmusculo_recycler);
            series = itemView.findViewById(R.id.seriesejercicio_recycler);
            repeticiones = itemView.findViewById(R.id.repeticiones_ejercicico_recycler);
            delete = itemView.findViewById(R.id.eliminar_ejercicio_recycler);
            edit = itemView.findViewById(R.id.editar_ejercicio_recycler);

        }


    }

    // Atributos de la clase AdaptadorPersona
    private ArrayList<Ejercicio> datos;
    private Context contexto;

    public adaptador_ejercicios_info_rutina(Context contexto, ArrayList<Ejercicio> datos) {
        this.contexto = contexto;
        this.datos = datos;
    }

    /**
     * Agrega los datos que queramos mostrar
     *
     * @param datos Datos a mostrar
     */
    public void add(ArrayList<Ejercicio> datos) {
        datos.clear();
        datos.addAll(datos);
    }

    public void refrescar() {
        notifyDataSetChanged();
    }

    @Override
    public adaptador_ejercicios_info_rutina.HolderEjerciciosRutina onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_ejercicios_info_rutina, parent, false);
        adaptador_ejercicios_info_rutina.HolderEjerciciosRutina pvh = new adaptador_ejercicios_info_rutina.HolderEjerciciosRutina(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(HolderEjerciciosRutina ejerciciorutina, final int position) {
        ejerciciorutina.nombre.setText(datos.get(position).getNombre_ejercicio());
        ejerciciorutina.musculo.setText(datos.get(position).getMusculo());
        ejerciciorutina.repeticiones.setText(datos.get(position).getRepeticiones());
        ejerciciorutina.series.setText(datos.get(position).getSeries());

        ejerciciorutina.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder buildermodificar = new AlertDialog.Builder(contexto);
                LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogmodificar = inflater.inflate(R.layout.layout_pestana_crear_ejercicio, null, false);
                buildermodificar.setView(dialogmodificar).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText nombreejercicio, musculo, series, repeticiones;

                        musculo = dialogmodificar.findViewById(R.id.musculo_implicado);
                        nombreejercicio = dialogmodificar.findViewById(R.id.nombre_ejercicio);
                        series = dialogmodificar.findViewById(R.id.et_series_crear_ejercicio);
                        repeticiones = dialogmodificar.findViewById(R.id.et_repeticiones_crear_ejercicio);

                        if (musculo.getText().toString().equals("") || nombreejercicio.getText().toString().equals("") || series.getText().toString().equals("") || repeticiones.getText().toString().equals("")) {
                            new SweetAlertDialog(contexto, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
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
                            Ejercicio modificado = new Ejercicio(datos.get(position).getRutina(), musculo.getText().toString(), nombreejercicio.getText().toString(), series.getText().toString(), repeticiones.getText().toString(), "", "");
                            ControladorEjercicio modificarejercicio = new ControladorEjercicio();
                            try {
                                modificarejercicio.modificarEjercicio(modificado, datos.get(position).getRutina());
                                new SweetAlertDialog(dialogmodificar.getContext(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("Ejercicio modificado.")
                                        .setContentText("El " + datos.get(position).getNombre_ejercicio() + " ejercicio se modificó correctamente a " + nombreejercicio.getText().toString() + ".")
                                        .setConfirmText("¡Gracias!").show();
                            } catch (ServidorPHPException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).show();

            }
        });
        ejerciciorutina.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControladorEjercicio controladorejercicioeliminar = new ControladorEjercicio();
                try {
                    controladorejercicioeliminar.eliminarEjercicio(datos.get(position).getNombre_ejercicio(), datos.get(position).getRutina());
                    new SweetAlertDialog(contexto, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Rutina creada.")
                            .setContentText("El " + datos.get(position).getNombre_ejercicio() + " ejercicio se eliminó correctamente.")
                            .setConfirmText("¡Gracias!").show();
                } catch (ServidorPHPException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}


