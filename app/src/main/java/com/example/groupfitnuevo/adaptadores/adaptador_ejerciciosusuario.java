package com.example.groupfitnuevo.adaptadores;

import android.content.Context;
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

public class adaptador_ejerciciosusuario extends RecyclerView.Adapter<adaptador_ejerciciosusuario.HolderEjerciciosUsuario> {


    public static class HolderEjerciciosUsuario extends RecyclerView.ViewHolder {
        TextView nombre, musculo, series, repeticiones;
        EditText pesoinicio, pesofin;
        ImageButton actualizar;

        HolderEjerciciosUsuario(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tvnombreejercicio_usuario);
            musculo = itemView.findViewById(R.id.tvmusculo_ejercicio_usuario);
            series = itemView.findViewById(R.id.tvseries_ejerciciousuario);
            repeticiones = itemView.findViewById(R.id.tvrepeticionesejercicios_usuario);

            pesoinicio = itemView.findViewById(R.id.etpesoinicial_usuario);
            pesofin = itemView.findViewById(R.id.etpesofin_usuario);

            actualizar = itemView.findViewById(R.id.actualizardatosejercicio);

        }
    }

    private ArrayList<Ejercicio> datos;
    private Context contexto;

    public adaptador_ejerciciosusuario(Context contexto, ArrayList<Ejercicio> datos) {
        this.contexto = contexto;
        this.datos = datos;
    }

    public void add(ArrayList<Ejercicio> datos) {
        datos.clear();
        datos.addAll(datos);
    }

    public void refrescar() {
        notifyDataSetChanged();
    }

    @Override
    public adaptador_ejerciciosusuario.HolderEjerciciosUsuario onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclerejerciciosusuario, parent, false);
        adaptador_ejerciciosusuario.HolderEjerciciosUsuario pvh = new adaptador_ejerciciosusuario.HolderEjerciciosUsuario(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final HolderEjerciciosUsuario ejerciciorutina, final int position) {
        ejerciciorutina.nombre.setText(datos.get(position).getNombre_ejercicio());
        ejerciciorutina.musculo.setText(datos.get(position).getMusculo());
        ejerciciorutina.repeticiones.setText(datos.get(position).getRepeticiones());
        ejerciciorutina.series.setText(datos.get(position).getSeries());
        ejerciciorutina.actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControladorEjercicio actualizarpeso = new ControladorEjercicio();
                try {

                    actualizarpeso.modificarPesosEjercicio(ejerciciorutina.pesoinicio.getText().toString(), ejerciciorutina.pesofin.getText().toString(), datos.get(position).getRutina(), ejerciciorutina.nombre.getText().toString());
                    new SweetAlertDialog(contexto, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Peso actualizado.")
                            .setContentText("Los pesos se actualizaron correctamente peso inicio: "+ejerciciorutina.pesoinicio.getText().toString()+" pesofin: "+ejerciciorutina.pesofin.getText().toString())
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

