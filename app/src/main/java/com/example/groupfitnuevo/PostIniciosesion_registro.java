package com.example.groupfitnuevo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import controladores.ControladorUsuario;
import controladores.ServidorPHPException;
import controladores.Usuario;

public class PostIniciosesion_registro extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView nombre, apellidos, correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_iniciosesion_registro);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View header = navigationView.getHeaderView(0);
        nombre = header.findViewById(R.id.tvNombreNav);
        apellidos = header.findViewById(R.id.tvApellidosNav);
        correo = header.findViewById(R.id.tvCorreoNav);

        final SharedPreferences preferencias = this.getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        String email = preferencias.getString("email", "");
        ControladorUsuario obtenerusuariomail = new ControladorUsuario();
        final Usuario usuarioobtenerpreferences = new Usuario("", "", email, "", "", "", "", "", "", "", "","", "", "", "");
        try {
            obtenerusuariomail.verificado(usuarioobtenerpreferences);
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                nombre.setText(usuarioobtenerpreferences.getNombre());
                apellidos.setText(usuarioobtenerpreferences.getApellidos());
                correo.setText(usuarioobtenerpreferences.getEmail());
            }
            public void onDrawerOpened(View openview){
                super.onDrawerOpened(openview);
                nombre.setText(usuarioobtenerpreferences.getNombre());
                apellidos.setText(usuarioobtenerpreferences.getApellidos());
                correo.setText(usuarioobtenerpreferences.getEmail());
            }
        };
        drawer.addDrawerListener(drawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.post_iniciosesion_registro, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public void onBackPressed() {

    }
}
