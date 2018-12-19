package dkgnkndz.lebk.cah_app.ui.landing;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import javax.annotation.Nullable;
import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import dkgnkndz.lebk.cah_app.R;

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
}
