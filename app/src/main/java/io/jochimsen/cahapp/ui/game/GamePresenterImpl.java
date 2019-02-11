package io.jochimsen.cahapp.ui.game;

import javax.inject.Inject;

import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__({@Inject}))
public class GamePresenterImpl implements GamePresenter {

    private final GameView gameView;
}
