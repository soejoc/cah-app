package dkgnkndz.lebk.cah_app.backend.webservice.black_card;

import java.util.List;

import dkgnkndz.lebk.cah_app.backend.webservice.black_card.response.BlackCardResponse;
import dkgnkndz.lebk.cah_app.backend.webservice.global.request.CheckHashRequest;
import dkgnkndz.lebk.cah_app.backend.webservice.global.response.CheckHashResponse;
import dkgnkndz.lebk.cah_app.backend.webservice.global.response.HashResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BlackCardAPI {
    @GET(".")
    Single<HashResponse<List<BlackCardResponse>>> getBlackCards();

    @POST("hash")
    Single<CheckHashResponse> checkHash(@Body final CheckHashRequest checkHashRequest);
}
