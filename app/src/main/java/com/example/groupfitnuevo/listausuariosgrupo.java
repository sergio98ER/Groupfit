package com.example.groupfitnuevo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.groupfitnuevo.adaptadores.adaptador_usuarios_grupo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLOutput;
import java.util.ArrayList;

import controladores.ControladorUsuario;
import controladores.ServidorPHPException;
import controladores.Usuario;

public class listausuariosgrupo extends AppCompatActivity {
    private FloatingActionButton anadirusuarios;
    private RecyclerView recyclerusuariosgrupo;
    private adaptador_usuarios_grupo adaptador;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listausuariosgrupo);

        Toolbar toolbar = findViewById(R.id.toolbar);

        recyclerusuariosgrupo = findViewById(R.id.recyclerusuarios_grupo);

        Intent intent = getIntent();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final String id = intent.getStringExtra("id");

        ControladorUsuario obtenerUsuariosgrupo = new ControladorUsuario();
        // Obtengo los datos de las personas del servidor
        try {
           final ArrayList<Usuario> todosUsuarios = obtenerUsuariosgrupo.obtenerUsuariosGrupo(id);

            // Hay que crear en la caperta values un fichero dimens.xml y crear ahi list_space

            // Con esto el tamaño del recyclerwiew no cambiará
            recyclerusuariosgrupo.setHasFixedSize(true);
            // Creo un layoutmanager para el recyclerview
            LinearLayoutManager llm = new LinearLayoutManager(this);
            recyclerusuariosgrupo.setLayoutManager(llm);

            adaptador = new adaptador_usuarios_grupo(this, todosUsuarios);
            recyclerusuariosgrupo.setAdapter(adaptador);
            adaptador.refrescar();

            refreshLayout = findViewById(R.id.refresh);
            refreshLayout.setColorSchemeColors(Color.GREEN, Color.YELLOW, Color.RED);
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ControladorUsuario usuariorefrescar = new ControladorUsuario();
                                refreshLayout.setRefreshing(false);
                                ArrayList<Usuario> todosUsuarios2 = null;
                                try {
                                    todosUsuarios2 = usuariorefrescar.obtenerUsuariosGrupo(id);
                                } catch (ServidorPHPException e) {
                                    e.printStackTrace();
                                }
                                adaptador = new adaptador_usuarios_grupo(listausuariosgrupo.this, todosUsuarios2);
                                recyclerusuariosgrupo.setAdapter(adaptador);
                                adaptador.refrescar();
                            }
                        }, 1000);



                }
            });

        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
