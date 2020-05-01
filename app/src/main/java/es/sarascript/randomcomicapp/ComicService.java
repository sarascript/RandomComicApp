package es.sarascript.randomcomicapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ComicService {

        @GET("{idComic}/info.0.json")
        Call<Comic> getComic(@Path("idComic") int idComic);

}
