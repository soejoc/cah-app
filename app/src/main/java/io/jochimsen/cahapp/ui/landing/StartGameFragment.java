package io.jochimsen.cahapp.ui.landing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.jochimsen.cahapp.R;
import io.jochimsen.cahapp.di.scope.FragmentScope;
import io.jochimsen.cahapp.ui.BaseFragment;
import io.jochimsen.cahapp.view_model.LandingViewModel;

@FragmentScope
public class StartGameFragment extends BaseFragment {

    @BindView(R.id.nicknameEdit)
    EditText nicknameEdit;

    @BindView(R.id.playButton)
    Button playButton;

    private LandingViewModel landingViewModel;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        landingViewModel = getActivityViewModel(LandingViewModel.class);

        landingViewModel.getBlackCards().observe(this, blackCards -> {});
        landingViewModel.getWhiteCards().observe(this, whiteCards -> {});
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_start_game, container, false);
        ButterKnife.bind(this, view);

        playButton.setOnClickListener(v -> {
            final String nickName = nicknameEdit.getText().toString();
            landingViewModel.play(nickName);
        });

        return view;
    }
}
