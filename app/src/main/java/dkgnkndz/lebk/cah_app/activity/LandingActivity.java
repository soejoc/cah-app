package dkgnkndz.lebk.cah_app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import dkgnkndz.lebk.cah_app.MyApp;
import dkgnkndz.lebk.cah_app.R;
import dkgnkndz.lebk.cah_app.network.thread.NetworkWorker;
import protocol.object.request.StartGameRequest;
import protocol.object.response.StartGameResponse;

public class LandingActivity extends ActivityBase {
    
    @BindView(R.id.nickname)
    EditText nickname;

    @BindView(R.id.waitForGameProgressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        ButterKnife.bind(this);
    }

    public void play(final View view) {
        MyApp myApp = (MyApp)getApplication();

        final String nickName = this.nickname.getText().toString();
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
        progressBar.setVisibility(View.VISIBLE);
    }
}
