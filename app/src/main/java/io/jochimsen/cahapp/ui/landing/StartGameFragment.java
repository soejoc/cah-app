package io.jochimsen.cahapp.ui.landing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import io.jochimsen.cahapp.MyApp;
import io.jochimsen.cahapp.R;
import io.jochimsen.cahapp.di.scope.StartGameFragmentScope;
import io.jochimsen.cahframework.protocol.object.message.request.StartGameRequest;

@StartGameFragmentScope
public class StartGameFragment extends DaggerFragment {

    @BindView(R.id.nicknameEdit)
    EditText nicknameEdit;

    @BindView(R.id.playButton)
    Button playButton;

    @Inject
    MyApp myApp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_start_game, container, false);
        ButterKnife.bind(this, view);

        playButton.setOnClickListener(v -> {
            final String nickName = nicknameEdit.getText().toString();
            final StartGameRequest startGameRequest = new StartGameRequest(nickName);

            myApp.createConnection(startGameRequest);
        });

        return view;
    }
}
