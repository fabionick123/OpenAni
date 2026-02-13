package com.astememe.openani.Ventanas;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.astememe.openani.R;

import java.util.zip.Inflater;

public class MainAnime extends AppCompatActivity {

    ImageView barra_lateral_icono;
    LayoutInflater inflador_menu_lateral;
    LinearLayout contenedor_menu_lateral;
    LinearLayout sombra_menu_lateral;
    View menu_lateral;
    ImageView cerrar_menu_lateral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_anime);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        barra_lateral_icono = findViewById(R.id.side_nav_main_anime);
        contenedor_menu_lateral = findViewById(R.id.contenedormenulateral);
        sombra_menu_lateral = findViewById(R.id.sombramenulateral);

        sombra_menu_lateral.setVisibility(INVISIBLE);

        inflador_menu_lateral = LayoutInflater
                .from(this);


        barra_lateral_icono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sombra_menu_lateral.setVisibility(VISIBLE);
                contenedor_menu_lateral.removeAllViews();
                menu_lateral = inflador_menu_lateral.inflate(R.layout.menu_lateral, contenedor_menu_lateral, true);
                cerrar_menu_lateral = menu_lateral.findViewById(R.id.cerrar_menu_lateral);

                cerrar_menu_lateral.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Hola", "hola");
                        sombra_menu_lateral.setVisibility(INVISIBLE);
                        contenedor_menu_lateral.removeAllViews();
                    }
                });
            }
        });

    }






    public static class SostenDeVistas extends RecyclerView.ViewHolder {

        TextView titulo_torrent, tamano_torrent, ultima_fecha_torrent, cantidad_seeders_torrent, cantidad_leechers_torrent, cantidad_likes_torrent, cantidad_dislikes_torrent, magnet_boton_torrent;

        public SostenDeVistas(@NonNull View itemView) {
            super(itemView);
            titulo_torrent = itemView.findViewById(R.id.titulo_torrent);
            tamano_torrent = itemView.findViewById(R.id.tamano_torrent);
            ultima_fecha_torrent = itemView.findViewById(R.id.fecha_actualizacion_torrent);
            cantidad_seeders_torrent = itemView.findViewById(R.id.cantidad_seeders);
            cantidad_leechers_torrent = itemView.findViewById(R.id.cantidad_leechers);
            cantidad_likes_torrent = itemView.findViewById(R.id.cantidad_likes);
            cantidad_dislikes_torrent = itemView.findViewById(R.id.cantidad_dislikes);
            magnet_boton_torrent = itemView.findViewById(R.id.magnet_boton);
        }
    }
}