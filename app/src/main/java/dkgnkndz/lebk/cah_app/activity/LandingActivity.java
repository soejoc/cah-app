package dkgnkndz.lebk.cah_app.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import dkgnkndz.lebk.cah_app.R;
import dkgnkndz.lebk.cah_app.fragment.StartGameFragment;
import dkgnkndz.lebk.cah_app.fragment.WaitForGameFragment;
import dkgnkndz.lebk.cah_app.network.handler.ResponseMessageHandler;
import protocol.object.message.response.StartGameResponse;

public class LandingActivity extends AppCompatActivity implements ResponseMessageHandler {

    @BindView(R.id.container)
    ConstraintLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        ButterKnife.bind(this);

        if(savedInstanceState == null) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final StartGameFragment startGameFragment = new StartGameFragment();
            fragmentManager.beginTransaction().add(R.id.container, startGameFragment).commit();
        }
    }

    @Override
    public void onStartGame(final StartGameResponse startGameResponse) {

    }

    @Override
    public void onWaitForGame() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final WaitForGameFragment waitForGameFragment = new WaitForGameFragment();

        fragmentManager.beginTransaction().replace(R.id.container, waitForGameFragment).commit();
    }
}
