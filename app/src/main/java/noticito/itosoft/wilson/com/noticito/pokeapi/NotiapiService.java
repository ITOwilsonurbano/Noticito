package noticito.itosoft.wilson.com.noticito.pokeapi;

import noticito.itosoft.wilson.com.noticito.models.NotiRespuesta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wilsonurbano on 10/11/16.
 */

public interface NotiapiService {

    @GET("noticias")
    Call<NotiRespuesta> obtenerListaNoticias(@Query("limit") int limit, @Query("offset") int offset);


}
