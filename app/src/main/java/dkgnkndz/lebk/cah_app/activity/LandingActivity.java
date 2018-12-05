package dkgnkndz.lebk.cah_app.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import dkgnkndz.lebk.cah_app.R;
import dkgnkndz.lebk.cah_app.di.SessionKeyModelComponent;
import dkgnkndz.lebk.cah_app.fragment.StartGameFragment;
import dkgnkndz.lebk.cah_app.fragment.WaitFragment;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        ButterKnife.bind(this);

        if(!isChangingConfigurations()) {

        }

        if(savedInstanceState == null) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final StartGameFragment startGameFragment = new StartGameFragment();
            fragmentManager.beginTransaction().add(R.id.container, startGameFragment).commit();
        }
    }

    public void switchToWaitForGameFragment() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final WaitFragment waitFragment = new WaitFragment();
        waitFragment.setMessage(getString(R.string.wait_for_game_message));

        fragmentManager.beginTransaction().replace(R.id.container, waitFragment).commit();
    }
}
