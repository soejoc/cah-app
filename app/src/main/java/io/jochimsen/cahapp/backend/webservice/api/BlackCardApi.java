package io.jochimsen.cahapp.backend.webservice.api;

import java.util.List;

import io.jochimsen.cahapp.backend.webservice.request.CheckHashRequest;
import io.jochimsen.cahapp.backend.webservice.response.BlackCardResponse;
import io.jochimsen.cahapp.backend.webservice.response.CheckHashResponse;
import io.jochimsen.cahapp.backend.webservice.response.HashResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BlackCardApi {
    @GET("blackCard/")
    Single<HashResponse<List<BlackCardResponse>>> getBlackCards();

    @POST("blackCard/hash")
    Single<CheckHashResponse> checkHash(@Body final CheckHashRequest checkHashRequest);
}
