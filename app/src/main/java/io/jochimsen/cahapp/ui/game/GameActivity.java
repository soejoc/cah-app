package io.jochimsen.cahapp.ui.game;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import io.jochimsen.cahapp.R;
import io.jochimsen.cahapp.model.PlayerModel;
import io.jochimsen.cahapp.network.handler.MessageSubject;
import io.jochimsen.cahapp.repository.AvailableCardsRepository;
import io.jochimsen.cahapp.repository.MergedCardRepository;
import io.jochimsen.cahapp.repository.PlayerRepository;
import io.jochimsen.cahapp.view_model.PlayerViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.UnicastSubject;

public class GameActivity extends DaggerAppCompatActivity implements GameView {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Inject
    GamePresenter gamePresenter;

    @Inject
    PlayerRepository playerRepository;

    @Inject
    AvailableCardsRepository availableCardsRepository;

    @Inject
    MergedCardRepository mergedCardRepository;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    PlayFragment playFragment;

    SelectWinnerFragment selectWinnerFragment;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ButterKnife.bind(this);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        final PlayerViewModel playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        playerViewModel.setPlayerRepository(playerRepository);

        final Menu menu = navigationView.getMenu();

        playerViewModel.getPlayer().observe(this, players -> {
            menu.clear();

            for(final PlayerModel playerModel : players) {
                menu.add(playerModel.getNickname());
            }
        });

        playFragment = new PlayFragment();
        selectWinnerFragment = new SelectWinnerFragment();

        playFragment.setAvailableCardsRepository(availableCardsRepository);
        selectWinnerFragment.setMergedCardRepository(mergedCardRepository);
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

    @Override
    public void showGameFragment() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, playFragment).commit();
    }

    public void showSelectWinnerFragment() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, selectWinnerFragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        gamePresenter.onStart();

        compositeDisposable.add(MessageSubject.newRoundResponseSubject
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(newRoundResponse -> {
                    showGameFragment();
                }));

        compositeDisposable.add(MessageSubject.selectCardResponseSubject
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(selectCardResponse -> {
                    showSelectWinnerFragment();
                }));
    }

    @Override
    protected void onStop() {
        super.onStop();

        compositeDisposable.clear();
    }
}
