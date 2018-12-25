package dkgnkndz.lebk.cah_app.backend.webservice.white_card;

import java.util.List;

import dkgnkndz.lebk.cah_app.backend.webservice.global.request.CheckHashRequest;
import dkgnkndz.lebk.cah_app.backend.webservice.global.response.CheckHashResponse;
import dkgnkndz.lebk.cah_app.backend.webservice.global.response.HashResponse;
import dkgnkndz.lebk.cah_app.backend.webservice.white_card.response.WhiteCardResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

interface WhiteCardAPI {
    @GET(".")
    Single<HashResponse<List<WhiteCardResponse>>> getWhiteCards();

    @POST("hash")
    Single<CheckHashResponse> checkHash(@Body final CheckHashRequest checkHashRequest);
}
