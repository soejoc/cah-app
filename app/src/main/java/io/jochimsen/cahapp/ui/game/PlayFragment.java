package io.jochimsen.cahapp.ui.game;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.jochimsen.cahapp.R;
import io.jochimsen.cahapp.network.handler.MessageSubject;
import io.jochimsen.cahapp.repository.AvailableCardsRepository;
import io.jochimsen.cahapp.ui.WaitFragment;
import io.jochimsen.cahapp.view_model.GameViewModel;
import io.jochimsen.cahapp.view_model.PlayerViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PlayFragment extends Fragment {

    @BindView(R.id.black_card)
    TextView blackCardTextView;

    private AvailableCardsRepository availableCardsRepository;

    public void setAvailableCardsRepository(final AvailableCardsRepository availableCardsRepository) {
        this.availableCardsRepository = availableCardsRepository;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_play, container, false);
        ButterKnife.bind(this, view);

        showWhiteCardsFragment();
        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final GameViewModel gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        gameViewModel.setAvailableCardsRepository(availableCardsRepository);

        gameViewModel.getBlackCardModelLiveData().observe(this, blackCardModel -> {
            blackCardTextView.setText(blackCardModel.getText());
        });

        MessageSubject.gameMasterResponseSubject
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(gameMasterResponse -> showWaitFragment());
    }

    public void showWhiteCardsFragment() {
        final FragmentManager fragmentManager = getChildFragmentManager();
        final WhiteCardsFragment whiteCardsFragment = new WhiteCardsFragment();
        whiteCardsFragment.setAvailableCardsRepository(availableCardsRepository);

        fragmentManager.beginTransaction().replace(R.id.container, whiteCardsFragment).commit();
    }

    public void showWaitFragment() {
        final FragmentManager fragmentManager = getChildFragmentManager();
        final WaitFragment waitFragment = new WaitFragment();
        waitFragment.setMessage(getString(R.string.wait_on_players));

        fragmentManager.beginTransaction().replace(R.id.container, waitFragment).commit();
    }
}
