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

public class LandingActivity extends AppCompatActivity {

    @BindView(R.id.container)
    ConstraintLayout container;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        ButterKnife.bind(this);

        if(savedInstanceState == null) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final StartGameFragment startGameFragment = new StartGameFragment();
            fragmentManager.beginTransaction().add(R.id.container, startGameFragment).commit();
        }
    }

    public void switchToWaitForGameFragment() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final WaitForGameFragment waitForGameFragment = new WaitForGameFragment();

        fragmentManager.beginTransaction().replace(R.id.container, waitForGameFragment).commit();
    }
}
