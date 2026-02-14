package com.astememe.openani.Ventanas;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astememe.openani.API_Manager.API_Client;
import com.astememe.openani.API_Manager.API_Interface;
import com.astememe.openani.API_Manager.Data;
import com.astememe.openani.R;
import com.astememe.openani.Adaptador_Evento.TorrentAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Arrays;
import java.util.List;

public class MainAnime extends AppCompatActivity {

    TorrentAdapter adapter;

    EditText busqueda;

    List<Data.Torrent> torrentList = new ArrayList<>();
    RecyclerView torrentRecycle;
    ImageView barra_lateral_icono;
    LayoutInflater inflador_menu_lateral;
    LinearLayout contenedor_menu_lateral;
    LinearLayout sombra_menu_lateral;
    View menu_lateral;
    ImageView cerrar_menu_lateral;

    TextView header_categoria;
    TextView header_subcategoria;

    TextView anime;
    TextView anime_music_video;
    TextView anime_non_english;
    TextView anime_original;
    TextView manga;
    TextView manga_english;
    TextView manga_non_english;
    TextView manga_original;

    ImageView foto_perfil;

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
        torrentRecycle = findViewById(R.id.torrentRecycle);
        fillTorrents("anime");
        torrentRecycle.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new TorrentAdapter(this, torrentList);
        torrentRecycle.setAdapter(adapter);
        busqueda = findViewById(R.id.searchbar_anime);


        barra_lateral_icono = findViewById(R.id.side_nav_main_anime);
        contenedor_menu_lateral = findViewById(R.id.contenedormenulateral);
        sombra_menu_lateral = findViewById(R.id.sombramenulateral);
        header_categoria = findViewById(R.id.categoria);
        header_subcategoria = findViewById(R.id.subcategoria);

        sombra_menu_lateral.setVisibility(INVISIBLE);

        inflador_menu_lateral = LayoutInflater
                .from(this);


        barra_lateral_icono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sombra_menu_lateral.setVisibility(VISIBLE);
                contenedor_menu_lateral.removeAllViews();
                menu_lateral = inflador_menu_lateral.inflate(R.layout.menu_lateral, contenedor_menu_lateral, true);
                Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_from_left);
                Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_from_right);
                menu_lateral.setAnimation(slide_in);
                cerrar_menu_lateral = menu_lateral.findViewById(R.id.cerrar_menu_lateral);
                anime = menu_lateral.findViewById(R.id.anime);
                manga = menu_lateral.findViewById(R.id.manga);
                foto_perfil = menu_lateral.findViewById(R.id.imagen_perfil);


                List<TextView> subcategorias_anime = new ArrayList<>(Arrays.asList(
                    anime_music_video = menu_lateral.findViewById(R.id.music_video),
                    anime_non_english = menu_lateral.findViewById(R.id.anime_non_english),
                    anime_original = menu_lateral.findViewById(R.id.anime_original)
                ));

                List<TextView> subcategorias_manga = new ArrayList<>(Arrays.asList(
                        manga_english = menu_lateral.findViewById(R.id.manga_english),
                        manga_non_english = menu_lateral.findViewById(R.id.manga_non_english),
                        manga_original = menu_lateral.findViewById(R.id.manga_original)
                ));


                cerrar_menu_lateral.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cerrar_menu_lateral(slide_out);
                    }
                });

                foto_perfil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainAnime.this, AccountView.class);
                        startActivity(intent);
                    }
                });

                anime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        header_categoria.setText("Anime");
                        header_subcategoria.setText("Most Recent");
                        fillTorrents("anime");
                        cerrar_menu_lateral(slide_out);
                    }
                });

                manga.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        header_categoria.setText("Manga");
                        header_subcategoria.setText("Most Recent");
                        fillTorrents("manga");
                        cerrar_menu_lateral(slide_out);
                    }
                });


                for (TextView subcategoria: subcategorias_anime) {
                    subcategoria.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            header_categoria.setText("Anime");
                            header_subcategoria.setText(subcategoria.getText().toString());
                            cerrar_menu_lateral(slide_out);
                        }
                    });
                }

                for (TextView subcategoria: subcategorias_manga) {
                    subcategoria.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            header_categoria.setText("Manga");
                            header_subcategoria.setText(subcategoria.getText().toString());
                            cerrar_menu_lateral(slide_out);
                        }
                    });
                }
            }

            public void cerrar_menu_lateral(Animation slide_out) {
                menu_lateral.setAnimation(slide_out);
                sombra_menu_lateral.setVisibility(INVISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        contenedor_menu_lateral.removeAllViews();
                    }
                }, slide_out.getDuration());
            }
        });

        busqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filtrarPorNombre(s.toString(), adapter);
            }
        });
    }
    private void fillTorrents(String categoria) {
        API_Client.getAPI_Interface().getByCategory(categoria).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                torrentList.clear();
                torrentList.addAll(response.body().torrents);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filtrarPorNombre(String texto, TorrentAdapter adapter) {
        if (texto.isEmpty()) {
            fillTorrents("anime");
            return;
        }
        API_Client.getAPI_Interface().getByName(texto).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                torrentList.clear();
                torrentList.addAll(response.body().torrents);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}