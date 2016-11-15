package noticito.itosoft.wilson.com.noticito;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import noticito.itosoft.wilson.com.noticito.models.NotiRespuesta;
import noticito.itosoft.wilson.com.noticito.models.Noticias;
import noticito.itosoft.wilson.com.noticito.pokeapi.NotiapiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "NOTIDEX";

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private ListaNoticiasAdapter listaNoticiasAdapter;

    private int offset;

    private boolean aptoParaCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        listaNoticiasAdapter = new ListaNoticiasAdapter(this);
        recyclerView.setAdapter(listaNoticiasAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (aptoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, " Llegamos al final.");

                            aptoParaCargar = false;
                            offset += 5;
                            obtenerdatos(offset);
                        }
                    }
                }
            }
        });
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.inder.gov.co/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        aptoParaCargar = true;
        offset = 0;
        obtenerdatos(offset);
    }
    private void obtenerdatos(int offset) {

        NotiapiService service = retrofit.create(NotiapiService.class);
        Call<NotiRespuesta> notiRespuestaCall = service.obtenerListaNoticias(5, offset);

        notiRespuestaCall.enqueue(new Callback<NotiRespuesta>() {
            @Override
            public void onResponse(Call<NotiRespuesta> call, Response<NotiRespuesta> response) {
                aptoParaCargar = true;
                if (response.isSuccessful()){
                    NotiRespuesta notiRespuesta = response.body();
                    ArrayList<Noticias> listaNoticias = notiRespuesta.getResults();

                    listaNoticiasAdapter.adicionarListaNoticias(listaNoticias);
                }else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<NotiRespuesta> call, Throwable t) {
                aptoParaCargar = true;
                Log.e(TAG, " onFailure: " + t.getMessage());
            }

        });
    }
}