package dkgnkndz.lebk.cah_app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dkgnkndz.lebk.cah_app.MyApp;
import dkgnkndz.lebk.cah_app.R;
import protocol.object.request.StartGameRequest;
import protocol.object.response.StartGameResponse;

public class LandingActivity extends ActivityBase {
    
    @BindView(R.id.nicknameEdit)
    EditText nicknameEdit;

    @BindView(R.id.playButton)
    Button playButton;

    @BindView(R.id.waitForGameProgressBar)
    ProgressBar progressBar;

    @BindView(R.id.waitForGameMessageView)
    TextView waitForGameMessageView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        ButterKnife.bind(this);
    }

    public void play(final View view) {
        final MyApp myApp = (MyApp)getApplication();

        final String nickName = nicknameEdit.getText().toString();
        final StartGameRequest startGameRequest = new StartGameRequest();
        startGameRequest.nickName = nickName;

        if(myApp.getNetworkingThread() == null) {
            myApp.createConnection(startGameRequest);
        } else {
            request(startGameRequest);
        }
    }

    public void onStartGame(final StartGameResponse startGameResponse) {

    }

    public void onWaitForGame() {
        toggleVisibility();
    }

    private void toggleVisibility() {
        nicknameEdit.setVisibility((nicknameEdit.getVisibility() == View.VISIBLE) ? View.INVISIBLE : View.VISIBLE);
        playButton.setVisibility((playButton.getVisibility() == View.VISIBLE) ? View.INVISIBLE : View.VISIBLE);
        progressBar.setVisibility((progressBar.getVisibility() == View.VISIBLE) ? View.INVISIBLE : View.VISIBLE);
        waitForGameMessageView.setVisibility((waitForGameMessageView.getVisibility() == View.VISIBLE) ? View.INVISIBLE : View.VISIBLE);
    }
}
