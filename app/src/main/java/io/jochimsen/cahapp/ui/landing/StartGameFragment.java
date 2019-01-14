package io.jochimsen.cahapp.ui.landing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.jochimsen.cahapp.MyApp;
import io.jochimsen.cahapp.R;
import io.jochimsen.cahframework.protocol.object.message.request.StartGameRequest;

public class StartGameFragment extends Fragment {

    @BindView(R.id.nicknameEdit)
    EditText nicknameEdit;

    @BindView(R.id.playButton)
    Button playButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_start_game, container, false);
        ButterKnife.bind(this, view);

        playButton.setOnClickListener(v -> {
            final Activity activity = getActivity();

            if(activity == null) {
                return;
            }

            final MyApp myApp = (MyApp)activity.getApplication();

            final String nickName = nicknameEdit.getText().toString();
            final StartGameRequest startGameRequest = new StartGameRequest();
            startGameRequest.nickName = nickName;

            if(myApp.getNetworkingThread() == null) {
                myApp.createConnection(startGameRequest);
            } else {
                //request(startGameRequest);
            }
        }
        );

        return view;
    }
}
