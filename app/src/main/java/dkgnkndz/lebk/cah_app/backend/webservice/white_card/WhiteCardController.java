package dkgnkndz.lebk.cah_app.backend.webservice.white_card;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dkgnkndz.lebk.cah_app.backend.webservice.BaseController;
import dkgnkndz.lebk.cah_app.backend.webservice.global.request.CheckHashRequest;
import dkgnkndz.lebk.cah_app.backend.webservice.global.response.CheckHashResponse;
import dkgnkndz.lebk.cah_app.backend.webservice.global.response.HashResponse;
import dkgnkndz.lebk.cah_app.backend.webservice.white_card.response.WhiteCardResponse;
import io.reactivex.Single;

@Singleton
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
