package com.example.groupfitnuevo.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.groupfitnuevo.R;
import com.example.groupfitnuevo.fragment_info_grupo;
import com.example.groupfitnuevo.informacionrutina;

import org.w3c.dom.Text;

import java.util.ArrayList;

import controladores.Rutina;
import de.hdodenhof.circleimageview.CircleImageView;

public class adaptadorrutinas extends RecyclerView.Adapter<adaptadorrutinas.HolderRutinas> implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        TextView nombre;
        Context context = view.getContext();
        Intent intent = new Intent(context, informacionrutina.class);
        nombre = view.findViewById(R.id.nombre_rutina_recycler);
        intent.putExtra("nombrerutina", nombre.getText().toString());
        context.startActivity(intent);
    }

    public static class HolderRutinas extends RecyclerView.ViewHolder {
        TextView nombre, descripcion;
        CircleImageView icono_rutina;

        HolderRutinas(View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre_rutina_recycler);
            descripcion = itemView.findViewById(R.id.descripcion_rutina_recycler);
            icono_rutina = itemView.findViewById(R.id.circle_ic_rutina);

        }

        ;
    }

    // Atributos de la clase AdaptadorPersona
    private ArrayList<Rutina> datos;
    private Context contexto;

    public adaptadorrutinas(Context contexto, ArrayList<Rutina> datos) {
        this.contexto = contexto;
        this.datos = datos;
    }

    /**
     * Agrega los datos que queramos mostrar
     *
     * @param datos Datos a mostrar
     */
    public void add(ArrayList<Rutina> datos) {
        datos.clear();
        datos.addAll(datos);
    }

    public void refrescar() {
        notifyDataSetChanged();
    }

    @Override
    public adaptadorrutinas.HolderRutinas onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutrecyclerrutinas, parent, false);
        adaptadorrutinas.HolderRutinas pvh = new adaptadorrutinas.HolderRutinas(v);
        v.setOnClickListener(this);
        return pvh;
    }

    @Override
    public void onBindViewHolder(adaptadorrutinas.HolderRutinas rutina, int position) {
        rutina.nombre.setText(datos.get(position).getNombre());
        rutina.descripcion.setText(datos.get(position).getDescripcion());
        if (datos.get(position).getDescripcion().equals("Pérdida de peso")) {
            rutina.icono_rutina.setImageResource(R.drawable.icon_perder_peso);
        } else {
            if (datos.get(position).getDescripcion().equals("Ganancia de masa muscular")) {
                rutina.icono_rutina.setImageResource(R.drawable.icon_ganancia_muscular);
            } else {
                if (datos.get(position).getDescripcion().equals("Tonificar cuerpo")) {
                    rutina.icono_rutina.setImageResource(R.drawable.icon_tonificacion);
                } else {
                    if (datos.get(position).getDescripcion().equals("Ganancia de fuerza")) {
                        rutina.icono_rutina.setImageResource(R.drawable.icon_fuerza);
                    } else {
                        if (datos.get(position).getDescripcion().equals("Ganancia de resistencia")) {
                            rutina.icono_rutina.setImageResource(R.drawable.ic_resistencia);
                        } else {
                            if (datos.get(position).getDescripcion().equals("Hipertrofia")) {
                                rutina.icono_rutina.setImageResource(R.drawable.ic_hipertrofia);
                            } else {
                                if (datos.get(position).getDescripcion().equals("")) {
                                    rutina.icono_rutina.setImageResource(R.drawable.account_question);
                                }
                            }
                        }
                    }
                }
            }
        }

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

