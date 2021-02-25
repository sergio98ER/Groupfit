package com.example.groupfitnuevo.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.groupfitnuevo.MainActivity;
import com.example.groupfitnuevo.R;
import com.example.groupfitnuevo.fragment_info_grupo;

import org.w3c.dom.Text;

import java.util.ArrayList;

import controladores.Grupo;
import de.hdodenhof.circleimageview.CircleImageView;

public class adaptadorgrupo  extends RecyclerView.Adapter<adaptadorgrupo.HolderGrupo> implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        TextView nombre, objetivo;
        Context context = view.getContext();
        Intent intent = new Intent(context, fragment_info_grupo.class);

        nombre = view.findViewById(R.id.tNombre_grupo_recicler);
        objetivo = view.findViewById(R.id.objetivo_grupo_recycler);
        intent.putExtra("nombregrupo", nombre.getText().toString());
        intent.putExtra("objetivogrupo", objetivo.getText().toString());

        context.startActivity(intent);


    }

    public static class HolderGrupo extends RecyclerView.ViewHolder
    {
        TextView nombre, objetivo;
        CircleImageView circleImageView;
        HolderGrupo(View itemView)
        {
            super(itemView);
            nombre = itemView.findViewById(R.id.tNombre_grupo_recicler);
            objetivo = itemView.findViewById(R.id.objetivo_grupo_recycler);
            circleImageView = itemView.findViewById(R.id.circle_ic_grupo);

        }
    };
    // Atributos de la clase AdaptadorPersona
    private ArrayList<Grupo> datos;
    private Context contexto;

    public adaptadorgrupo(Context contexto, ArrayList<Grupo> datos)
    {
        this.contexto = contexto;
        this.datos = datos;
    }
    /**
     * Agrega los datos que queramos mostrar
     * @param datos Datos a mostrar
     */
    public void add(ArrayList<Grupo> datos)
    {
        datos.clear();
        datos.addAll(datos);
    }
    /**
     * Actualiza los datos del ReciclerView
     */
    public void refrescar()
    {
        notifyDataSetChanged();
    }

    @Override
    public HolderGrupo onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclerview, parent, false);
        HolderGrupo pvh = new HolderGrupo(v);
        v.setOnClickListener(this);
        return pvh;
    }

    @Override
    public void onBindViewHolder(HolderGrupo grupo, int position)
    {
        grupo.nombre.setText(datos.get(position).getNombre());
        grupo.objetivo.setText(datos.get(position).getObjetivo());
        if(datos.get(position).getObjetivo().equals("Pérdida de peso")){
            grupo.circleImageView.setImageResource(R.drawable.icon_perder_peso);
        }else{
            if(datos.get(position).getObjetivo().equals("Ganancia de masa muscular")){
                grupo.circleImageView.setImageResource(R.drawable.icon_ganancia_muscular);
            }else{
                if(datos.get(position).getObjetivo().equals("Tonificar cuerpo")){
                    grupo.circleImageView.setImageResource(R.drawable.icon_tonificacion);
                }else{
                    if(datos.get(position).getObjetivo().equals("Ganancia de fuerza")){
                        grupo.circleImageView.setImageResource(R.drawable.icon_fuerza);
                    }else{
                        if(datos.get(position).getObjetivo().equals("Ganancia de resistencia")){
                            grupo.circleImageView.setImageResource(R.drawable.ic_resistencia);
                        }else{
                            if(datos.get(position).getObjetivo().equals("Hipertrofia")){
                                grupo.circleImageView.setImageResource(R.drawable.ic_hipertrofia);
                            }else{
                                if(datos.get(position).getObjetivo().equals("")){
                                    grupo.circleImageView.setImageResource(R.drawable.account_question);
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public int getItemCount()
    {
        return datos.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
