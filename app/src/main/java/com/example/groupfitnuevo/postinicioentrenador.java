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

public class postinicioentrenador extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView nombre, correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postinicioentrenador);
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
        nombre = header.findViewById(R.id.Nombreapellidos);
        correo = header.findViewById(R.id.correo_entrenador_nav);
        final SharedPreferences preferencias = getSharedPreferences("Entrenador", Context.MODE_PRIVATE);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                nombre.setText(""+preferencias.getString("nombre", "")+" "+preferencias.getString("apellidos", ""));
                correo.setText(preferencias.getString("email", ""));
            }
            public void onDrawerOpened(View openview){
                super.onDrawerOpened(openview);
                nombre.setText(""+preferencias.getString("nombre", "")+" "+preferencias.getString("apellidos", ""));
                correo.setText(preferencias.getString("email", ""));
            }
        };
        drawer.addDrawerListener(drawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       /* getMenuInflater().inflate(R.menu.postinicioentrenador, menu);*/
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
