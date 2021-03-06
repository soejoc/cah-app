package io.jochimsen.cahapp.ui.game;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.jochimsen.cahapp.MyApp;
import io.jochimsen.cahapp.R;
import io.jochimsen.cahapp.network.session.ServerSession;

public class GameActivity extends AppCompatActivity implements GameView {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Inject
    GamePresenter gamePresenter;

    @Inject
    ServerSession serverSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApp.get(this)
                .getSessionComponent()
                .gameActivityComponentBuilder()
                .gameActivity(this)
                .build()
                .inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ButterKnife.bind(this);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START, true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
