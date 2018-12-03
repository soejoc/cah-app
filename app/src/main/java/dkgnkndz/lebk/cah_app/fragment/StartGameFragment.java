package dkgnkndz.lebk.cah_app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import dkgnkndz.lebk.cah_app.MyApp;
import dkgnkndz.lebk.cah_app.R;
import dkgnkndz.lebk.cah_app.network.handler.ResponseMessageHandler;
import protocol.object.message.request.StartGameRequest;

public class StartGameFragment extends FragmentBase {

    @BindView(R.id.nicknameEdit)
    EditText nicknameEdit;

    @BindView(R.id.playButton)
    Button playButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_start_game, container, false);
        ButterKnife.bind(this, view);

        playButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(final View v) {
                  final Activity activity = getActivity();

                  if(activity == null) {
                      return;
                  }

                  final MyApp myApp = (MyApp)activity.getApplication();

                  final String nickName = nicknameEdit.getText().toString();
                  final StartGameRequest startGameRequest = new StartGameRequest();
                  startGameRequest.nickName = nickName;

                  if(myApp.getNetworkingThread() == null) {
                      myApp.createConnection(startGameRequest, (ResponseMessageHandler)getActivity());
                  } else {
                      request(startGameRequest);
                  }
              }
          }
        );

        return view;
    }
}
