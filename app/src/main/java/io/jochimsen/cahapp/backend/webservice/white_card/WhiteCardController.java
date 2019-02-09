package io.jochimsen.cahapp.backend.webservice.white_card;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.jochimsen.cahapp.backend.webservice.BaseController;
import io.jochimsen.cahapp.backend.webservice.global.request.CheckHashRequest;
import io.jochimsen.cahapp.backend.webservice.global.response.CheckHashResponse;
import io.jochimsen.cahapp.backend.webservice.global.response.HashResponse;
import io.jochimsen.cahapp.backend.webservice.white_card.response.WhiteCardResponse;
import io.jochimsen.cahapp.di.scope.AppScope;
import io.reactivex.Single;

@AppScope
public class WhiteCardController extends BaseController {
    private static final String NAMESPACE = "whiteCard";

    private final WhiteCardAPI whiteCardAPI;

    @Inject
    public WhiteCardController() {
        super(NAMESPACE);

        whiteCardAPI = createAPI(WhiteCardAPI.class);
    }

    public Single<HashResponse<List<WhiteCardResponse>>> getWhiteCards() {
        return whiteCardAPI.getWhiteCards();
    }

    public Single<CheckHashResponse> checkHash(final int hash) {
        final CheckHashRequest checkHashRequest = new CheckHashRequest(hash);
        return whiteCardAPI.checkHash(checkHashRequest);
    }
}
