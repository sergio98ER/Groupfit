package com.example.groupfitnuevo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import controladores.MyFirebaseMessagingService;


public class cambiarentrenousuario extends AppCompatActivity {
    private TextView titulo, texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiarentrenousuario);
        Toolbar toolbar = findViewById(R.id.toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        titulo = findViewById(R.id.tvtitulomensaje);
        texto = findViewById(R.id.tvtextomensaje);

        Intent intent = getIntent();
        ArrayList<String> todo = intent.getStringArrayListExtra(MyFirebaseMessagingService.EXTRA_MESSAGE);
        titulo.setText(todo.get(0));
        texto.setText(todo.get(1));

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
