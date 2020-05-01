package es.sarascript.randomcomicapp;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView imgComic;
    TextView txtTitle;
    Button btnGetComic;

    ComicService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgComic = findViewById(R.id.imgvComic);
        txtTitle = findViewById(R.id.txtvTitle);
        btnGetComic = findViewById(R.id.btnGetComic);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://xkcd.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ComicService.class);

        btnGetComic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Call<Comic> call = service.getComic(new Random().nextInt(2300));

                call.enqueue(new Callback<Comic>() {
                    @Override
                    public void onResponse(Call<Comic> call, Response<Comic> response) {
                        Comic comic = response.body();
                        try {
                            if (comic != null) {
                                txtTitle.setText(comic.getTitle());
                                Picasso.get()
                                        .load(comic.getImg())
                                        .into(imgComic);
                            }
                        } catch (Exception e) {
                            Log.e("MainActivity", e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Comic> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error with API", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
