package io.jochimsen.cahapp.ui.landing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.jochimsen.cahapp.R;
import io.jochimsen.cahapp.di.scope.FragmentScope;
import io.jochimsen.cahapp.ui.BaseFragment;
import io.jochimsen.cahapp.view_model.LandingViewModel;

@FragmentScope
public class WaitForCardSynchronizationFragment extends BaseFragment {

    @BindView(R.id.waitMessageView)
    TextView waitMessageView;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LandingViewModel landingViewModel = getActivityViewModel(LandingViewModel.class);

        landingViewModel.getWhiteCards().observe(this, whiteCards -> landingViewModel.startGame());
        landingViewModel.getBlackCards().observe(this, blackCards -> landingViewModel.startGame());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_wait, container, false);
        ButterKnife.bind(this, view);

        waitMessageView.setText(R.string.wait_for_card_synchronization);

        return view;
    }
}
