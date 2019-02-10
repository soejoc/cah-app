package io.jochimsen.cahapp.backend.webservice.api;

import java.util.List;

import io.jochimsen.cahapp.backend.webservice.request.CheckHashRequest;
import io.jochimsen.cahapp.backend.webservice.response.CheckHashResponse;
import io.jochimsen.cahapp.backend.webservice.response.HashResponse;
import io.jochimsen.cahapp.backend.webservice.response.WhiteCardResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WhiteCardApi {
    @GET("whiteCard/")
    Single<HashResponse<List<WhiteCardResponse>>> getWhiteCards();

    @POST("whiteCard/hash")
    Single<CheckHashResponse> checkHash(@Body final CheckHashRequest checkHashRequest);
}
