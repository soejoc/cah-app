package io.jochimsen.cahapp.ui.landing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.jochimsen.cahapp.R;
import io.jochimsen.cahapp.ui.WaitFragment;
import io.jochimsen.cahapp.ui.game.GameActivity;

public class LandingActivity extends DaggerAppCompatActivity implements LandingView {

    @Inject
    LandingPresenter landingPresenter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        showWaitFragment(0);
    }

    @Override
    protected void onStart() {
        super.onStart();

        landingPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        landingPresenter.onStop();
    }

    @Override
    public void showStartGameFragment() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final StartGameFragment startGameFragment = new StartGameFragment();
        fragmentManager.beginTransaction().replace(R.id.container, startGameFragment).commit();
    }

    @Override
    public void showWaitFragment(final int resId) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final WaitFragment waitFragment = new WaitFragment();

        if(resId != 0) {
            waitFragment.setMessage(getString(resId));
        }

        fragmentManager.beginTransaction().replace(R.id.container, waitFragment).commit();
    }

    @Override
    public void startGameActivity() {
        final Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

        finish();
    }
}
