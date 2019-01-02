package io.jochimsen.cahapp.backend.webservice.black_card;

import java.util.List;

import io.jochimsen.cahapp.backend.webservice.black_card.response.BlackCardResponse;
import io.jochimsen.cahapp.backend.webservice.global.request.CheckHashRequest;
import io.jochimsen.cahapp.backend.webservice.global.response.CheckHashResponse;
import io.jochimsen.cahapp.backend.webservice.global.response.HashResponse;

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
