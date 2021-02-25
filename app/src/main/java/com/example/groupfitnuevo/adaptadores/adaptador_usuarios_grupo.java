package com.example.groupfitnuevo.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.groupfitnuevo.R;
import com.example.groupfitnuevo.fragment_info_grupo;
import com.example.groupfitnuevo.perfilusuario_grupo;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import controladores.ControladorRutina;
import controladores.ControladorUsuario;
import controladores.ServidorPHPException;
import controladores.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

public class adaptador_usuarios_grupo extends RecyclerView.Adapter<adaptador_usuarios_grupo.HolderUsuarioGrupo> implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        TextView nombre, email, dni;
        Context context = view.getContext();
        Intent intent = new Intent(context, perfilusuario_grupo.class);

        nombre = view.findViewById(R.id.tNombre_grupo_recicler);
        email = view.findViewById(R.id.emailusuario_recycler);
        dni = view.findViewById(R.id.dni_usuario_recycler);

        intent.putExtra("nombreusuariogrupo", nombre.getText().toString());
        intent.putExtra("emailusuariogrupo", email.getText().toString());
        intent.putExtra("dniusuariogrupo", email.getText().toString());

        context.startActivity(intent);


    }

    public static class HolderUsuarioGrupo extends RecyclerView.ViewHolder {
        TextView nombre, email, dni;
        ImageButton asignarentreno;
        HolderUsuarioGrupo(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tNombre_grupo_recicler);
            email = itemView.findViewById(R.id.emailusuario_recycler);
            dni = itemView.findViewById(R.id.dni_usuario_recycler);
            asignarentreno = itemView.findViewById(R.id.asignarentreno);

        };
    }


    // Atributos de la clase AdaptadorPersona
    private ArrayList<Usuario> datos;
    private Context contexto;

    public adaptador_usuarios_grupo(Context contexto, ArrayList<Usuario> datos) {
        this.contexto = contexto;
        this.datos = datos;
    }

    /**
     * Agrega los datos que queramos mostrar
     *
     * @param datos Datos a mostrar
     */
    public void add(ArrayList<Usuario> datos) {
        datos.clear();
        datos.addAll(datos);
    }

    public void refrescar() {
        notifyDataSetChanged();
    }

    @Override
    public adaptador_usuarios_grupo.HolderUsuarioGrupo onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_usuarios_grupo, parent, false);
        adaptador_usuarios_grupo.HolderUsuarioGrupo pvh = new adaptador_usuarios_grupo.HolderUsuarioGrupo(v);
        v.setOnClickListener(this);
        return pvh;
    }

    @Override
    public void onBindViewHolder(HolderUsuarioGrupo usuarioGrupo, int position) {
        usuarioGrupo.nombre.setText(datos.get(position).getNombre() +" "+ datos.get(position).getApellidos());
        usuarioGrupo.dni.setText(datos.get(position).getDni());
        usuarioGrupo.email.setText(datos.get(position).getEmail());
        ControladorRutina controladorcomprobarid = new ControladorRutina();
        try {
            if(controladorcomprobarid.obtenernombrerutinaporid(datos.get(position).getId_entreno()).equals("Rutina vacía")){
                usuarioGrupo.asignarentreno.setImageResource(R.drawable.dummbellbar_no_entreno);
                usuarioGrupo.asignarentreno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                        View layout = inflater.inflate(R.layout.custom_toast,
                                null);
                        CircleImageView circulo = (CircleImageView) layout.findViewById(R.id.circletoast);
                        TextView texto = (TextView) layout.findViewById(R.id.textotoast);
                        texto.setText("Este usuario no tiene un entrenamiento asignado.");

                        Toast toastpersonalido = new Toast(contexto); 
                        toastpersonalido.setGravity(Gravity.BOTTOM, 0, 0);
                        toastpersonalido.setDuration(Toast.LENGTH_SHORT);
                        toastpersonalido.setView(layout);
                        toastpersonalido.show();
                    }
                });
            } else{
                usuarioGrupo.asignarentreno.setImageResource(R.drawable.dummbellbar_entreno);
                usuarioGrupo.asignarentreno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                        View layout = inflater.inflate(R.layout.custom_toast,
                                null);
                        CircleImageView circulo = (CircleImageView) layout.findViewById(R.id.circletoast);
                        TextView texto = (TextView) layout.findViewById(R.id.textotoast);
                        texto.setText("Este usuario tiene un entrenamiento asignado.");

                        Toast toastpersonalido = new Toast(contexto);
                        toastpersonalido.setGravity(Gravity.BOTTOM, 0, 0);
                        toastpersonalido.setDuration(Toast.LENGTH_SHORT);
                        toastpersonalido.setView(layout);
                        toastpersonalido.show();
                    }
                });
            }
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return  datos.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

