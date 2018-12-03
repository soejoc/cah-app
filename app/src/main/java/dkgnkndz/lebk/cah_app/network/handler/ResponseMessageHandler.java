package dkgnkndz.lebk.cah_app.network.handler;

import protocol.object.message.response.StartGameResponse;

public interface ResponseMessageHandler {
    void onStartGame(final StartGameResponse startGameResponse);
    void onWaitForGame();
}
