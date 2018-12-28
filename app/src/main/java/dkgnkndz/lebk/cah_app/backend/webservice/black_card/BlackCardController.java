package dkgnkndz.lebk.cah_app.backend.webservice.black_card;

import java.util.List;

import javax.inject.Inject;

import dkgnkndz.lebk.cah_app.backend.webservice.BaseController;
import dkgnkndz.lebk.cah_app.backend.webservice.black_card.response.BlackCardResponse;
import dkgnkndz.lebk.cah_app.backend.webservice.global.request.CheckHashRequest;
import dkgnkndz.lebk.cah_app.backend.webservice.global.response.CheckHashResponse;
import dkgnkndz.lebk.cah_app.backend.webservice.global.response.HashResponse;
import io.reactivex.Single;

public class BlackCardController extends BaseController {
    private static final String NAMESPACE = "blackCard";

    private final BlackCardAPI blackCardAPI;

    @Inject
    public BlackCardController() {
        super(NAMESPACE);

        blackCardAPI = createAPI(BlackCardAPI.class);
    }

    public Single<HashResponse<List<BlackCardResponse>>> getBlackCards() {
        return blackCardAPI.getBlackCards();
    }

    public Single<CheckHashResponse> checkHash(final String hash) {
        final CheckHashRequest checkHashRequest = new CheckHashRequest(hash);
        return blackCardAPI.checkHash(checkHashRequest);
    }
}
