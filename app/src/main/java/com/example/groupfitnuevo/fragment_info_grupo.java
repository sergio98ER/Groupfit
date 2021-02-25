package com.example.groupfitnuevo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.groupfitnuevo.adaptadores.adaptadorgrupo;
import com.example.groupfitnuevo.ui.gallery.GalleryFragment;

import java.util.ArrayList;
import java.util.ResourceBundle;

import cn.pedant.SweetAlert.SweetAlertDialog;
import controladores.ControladorGrupo;
import controladores.ControladorUsuario;
import controladores.Grupo;
import controladores.ServidorPHPException;
import de.hdodenhof.circleimageview.CircleImageView;

public class fragment_info_grupo extends AppCompatActivity implements View.OnClickListener {
    private Button modificar, eliminar, usuarios, infoentrenador;
    private EditText nombre_editado;
    private TextView nombre_grupo, objetivo_grupo;
    private Spinner objetivo_editado;
    private ControladorGrupo controladorGrupo;
    private CircleImageView imagencambiante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment_info_grupo);
        Toolbar toolbar = findViewById(R.id.toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nombre_grupo = findViewById(R.id.nombre_grupo_edit);
        objetivo_grupo = findViewById(R.id.objetivo_grupo_edit);
        modificar = findViewById(R.id.bEditar_grupo);
        modificar.setOnClickListener(this);
        eliminar = findViewById(R.id.bEliminar_grupo);
        eliminar.setOnClickListener(this);
        usuarios = findViewById(R.id.bnumerousuarios);
        usuarios.setOnClickListener(this);
        infoentrenador = findViewById(R.id.bentrenador);
        infoentrenador.setOnClickListener(this);
        imagencambiante = findViewById(R.id.ic_objetivo);
        Intent intent = getIntent();
        //Extrayendo el extra de tipo cadena
        String nombre = intent.getStringExtra("nombregrupo");
        String objetivo = intent.getStringExtra("objetivogrupo");
        if (objetivo.equals("Pérdida de peso")) {
            imagencambiante.setImageResource(R.drawable.icon_perder_peso);
        } else {
            if (objetivo.equals("Ganancia de masa muscular")) {
                imagencambiante.setImageResource(R.drawable.icon_ganancia_muscular);
            } else {
                if (objetivo.equals("Tonificar cuerpo")) {
                    imagencambiante.setImageResource(R.drawable.icon_tonificacion);
                } else {
                    if (objetivo.equals("Ganancia de fuerza")) {
                        imagencambiante.setImageResource(R.drawable.icon_fuerza);
                    } else {
                        if (objetivo.equals("Ganancia de resistencia")) {
                            imagencambiante.setImageResource(R.drawable.ic_resistencia);
                        } else {
                            if (objetivo.equals("Hipertrofia")) {
                                imagencambiante.setImageResource(R.drawable.ic_hipertrofia);
                            }
                        }
                    }
                }
            }
        }

        nombre_grupo.setText(nombre);
        objetivo_grupo.setText(objetivo);
        if(nombre_grupo.getText().equals("Usuarios sin grupo")){
            modificar.setEnabled(false);
            eliminar.setEnabled(false);
        }else{
            modificar.setEnabled(true);
            eliminar.setEnabled(true);
        }
        ControladorGrupo controladorgrupoprincipio = new ControladorGrupo();
        ControladorUsuario controladorobtenercuenta = new ControladorUsuario();
        try {

            String id = controladorgrupoprincipio.obtenerIdGrupo(nombre);


        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bEditar_grupo:
                AlertDialog.Builder builder = new AlertDialog.Builder(fragment_info_grupo.this);
                LayoutInflater inflater = fragment_info_grupo.this.getLayoutInflater();
                final View dialogo = inflater.inflate(R.layout.layoutcreargrupo, null, false);
                builder.setView(dialogo).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nombre_editado = dialogo.findViewById(R.id.et_nombre_crea_grupo);
                        objetivo_editado = dialogo.findViewById(R.id.spinnerobjetivo_crear);
                        Grupo grupo = new Grupo(nombre_editado.getText().toString(), objetivo_editado.getSelectedItem().toString(), 1);
                        if (nombre_editado.getText().toString().equals("")) {
                            nombre_editado.setText(nombre_grupo.getText().toString());
                        }


                        try {
                            ControladorGrupo gruponombre = new ControladorGrupo();
                            if (gruponombre.obtenerNombreGrupo(nombre_editado.getText().toString()) == true) {
                                new SweetAlertDialog(dialogo.getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Error nombre grupo.")
                                        .setContentText("¡El grupo " + nombre_editado.getText().toString() + " ya existe!")
                                        .setConfirmText("Vale").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                }).show();
                            } else {
                                controladorGrupo = new ControladorGrupo();
                                ControladorGrupo controladorgrupoprincipio = new ControladorGrupo();
                                String id = controladorgrupoprincipio.obtenerIdGrupo(nombre_grupo.getText().toString());
                                controladorGrupo.modificargrupo(grupo, id);
                                if (objetivo_editado.getSelectedItem().toString().equals("Pérdida de peso")) {
                                    imagencambiante.setImageResource(R.drawable.icon_perder_peso);
                                } else {
                                    if (objetivo_editado.getSelectedItem().toString().equals("Ganancia de masa muscular")) {
                                        imagencambiante.setImageResource(R.drawable.icon_ganancia_muscular);
                                    } else {
                                        if (objetivo_editado.getSelectedItem().toString().equals("Tonificar cuerpo")) {
                                            imagencambiante.setImageResource(R.drawable.icon_tonificacion);
                                        } else {
                                            if (objetivo_editado.getSelectedItem().toString().equals("Ganancia de fuerza")) {
                                                imagencambiante.setImageResource(R.drawable.icon_fuerza);
                                            } else {
                                                if (objetivo_editado.getSelectedItem().toString().equals("Ganancia de resistencia")) {
                                                    imagencambiante.setImageResource(R.drawable.ic_resistencia);
                                                } else {
                                                    if (objetivo_editado.getSelectedItem().toString().equals("Hipertrofia")) {
                                                        imagencambiante.setImageResource(R.drawable.ic_hipertrofia);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                new SweetAlertDialog(dialogo.getContext(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("Grupo modificado.")
                                        .setContentText("El grupo " + nombre_grupo.getText().toString() + " se modificó correctamente a " + nombre_editado.getText().toString())
                                        .setConfirmText("¡Perfecto!").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        nombre_grupo.setText(nombre_editado.getText().toString());
                                        objetivo_grupo.setText(objetivo_editado.getSelectedItem().toString());
                                    }
                                }).show();;
                            }
                        } catch (ServidorPHPException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.show();
                break;

            case R.id.bEliminar_grupo:
                ControladorGrupo controladorgrupo2 = new ControladorGrupo();
                try {
                    controladorgrupo2.eliminarGrupo(nombre_grupo.getText().toString());
                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Grupo eliminado.")
                            .setContentText("El grupo " + nombre_grupo.getText().toString() + " ha sido eliminado.")
                            .setConfirmText("¡Gracias!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    Intent intent = new Intent(fragment_info_grupo.this, postinicioentrenador.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).show();

                } catch (ServidorPHPException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.bnumerousuarios:
                Intent intent = new Intent(fragment_info_grupo.this, listausuariosgrupo.class);

                ControladorGrupo controladorgrupogetID = new ControladorGrupo();
                try {
                    String idnuevo = controladorgrupogetID.obtenerIdGrupo(nombre_grupo.getText().toString());
                    intent.putExtra("id", idnuevo);
                    startActivity(intent);
                    finish();
                } catch (ServidorPHPException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.bentrenador:
                intent = new Intent(this, vistaprevia_aboutpage.class);
                this.startActivity(intent);
                break;

        }
    }
}
