package io.jochimsen.cahapp.ui.landing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import dagger.Lazy;
import io.jochimsen.cahapp.R;
import io.jochimsen.cahapp.di.scope.ActivityScope;
import io.jochimsen.cahapp.ui.BaseActivity;
import io.jochimsen.cahapp.ui.WaitFragment;
import io.jochimsen.cahapp.ui.game.GameActivity;
import io.jochimsen.cahapp.view_model.LandingViewModel;

@ActivityScope
public class LandingActivity extends BaseActivity {

    @Inject
    StartGameFragment startGameFragment;

    @Inject
    WaitFragment waitFragment;

    @Inject
    Lazy<WaitForCardSynchronizationFragment> waitForCardSynchronizationFragmentLazy;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        final LandingViewModel landingViewModel = getViewModel(LandingViewModel.class);

        landingViewModel.getSessionKey().observe(this, sessionKey -> {
            if(sessionKey == null) {
                showStartGameFragment();
            } else {
                landingViewModel.restartGame(sessionKey);
            }
        });

        landingViewModel.getFinishedGameResponse().observe(this, finishedGameResponse -> showStartGameFragment());
        landingViewModel.getWaitForGameResponse().observe(this, waitForGameResponse -> showWaitFragment(R.string.wait_for_game_message));
        landingViewModel.getStartGameResponse().observe(this, startGameResponse -> startGameActivity());
        landingViewModel.getStartedGame().observe(this, startedGame -> {
            if(startedGame != null && !startedGame) {
                showWaitForCardSynchronizationFragment();
            }
        });

        showWaitFragment(0);
    }

    private void showStartGameFragment() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, startGameFragment).commit();
    }

    private void showWaitFragment(final int resId) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        waitFragment.setResId(resId);

        fragmentManager.beginTransaction().replace(R.id.container, waitFragment).commit();
    }

    private void showWaitForCardSynchronizationFragment() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, waitForCardSynchronizationFragmentLazy.get()).commit();
    }

    private void startGameActivity() {
        final Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

        finish();
    }
}
