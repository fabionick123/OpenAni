package com.astememe.openani.Ventanas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.astememe.openani.R;

import java.io.File;

import io.woong.shapedimageview.CircleImageView;

public class AccountView extends AppCompatActivity {


    //https://www.geeksforgeeks.org/android/how-to-select-an-image-from-gallery-in-android/
    //https://www.reddit.com/r/learnprogramming/comments/q5xelq/should_i_convert_an_image_uri_to_a_blobbytearray/

    ConstraintLayout botonAtras;
    EditText descripcion;
    CircleImageView fotoPerfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fotoPerfil = findViewById(R.id.fotoperfil);
        descripcion = findViewById(R.id.descripcion);
        botonAtras = findViewById(R.id.flechaAtrasAcountView);
        descripcion.setText(preferences.getString("descripcion", "Escriba su descripción aquí"));
        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountView.this, MainAnime.class);
                startActivity(intent);
            }
        });
        if (preferences.getString("imagen", "").isEmpty()) {
            fotoPerfil.setImageResource(R.drawable.spiderman);
        } else {
            Uri uri = Uri.parse("android.resource://"+this.getPackageName()+"/drawable/foto_de_perfil_" + preferences.getString("imagen", ""));
            fotoPerfil.setImageURI(uri);
        }
    }
}