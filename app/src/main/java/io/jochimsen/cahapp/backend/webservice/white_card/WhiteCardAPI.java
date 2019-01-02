package io.jochimsen.cahapp.backend.webservice.white_card;

import java.util.List;

import io.jochimsen.cahapp.backend.webservice.global.request.CheckHashRequest;
import io.jochimsen.cahapp.backend.webservice.global.response.CheckHashResponse;
import io.jochimsen.cahapp.backend.webservice.global.response.HashResponse;
import io.jochimsen.cahapp.backend.webservice.white_card.response.WhiteCardResponse;
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
