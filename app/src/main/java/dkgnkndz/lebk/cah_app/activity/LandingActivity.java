package dkgnkndz.lebk.cah_app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import dkgnkndz.lebk.cah_app.R;
import dkgnkndz.lebk.cah_app.network.thread.NetworkWorker;
import protocol.object.request.StartGameRequest;

public class LandingActivity extends ActivityBase {

    private static final String host = "10.0.2.2";
    private static final int port = 8345;
    
    @BindView(R.id.nickname)
    EditText nickname;

    Thread networkingThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        ButterKnife.bind(this);
    }

    public void play(View view) {
        if(networkingThread == null) {
            final String nickName = this.nickname.getText().toString();

            StartGameRequest startGameRequest = new StartGameRequest();
            startGameRequest.nickName = nickName;

            NetworkWorker networkWorker = new NetworkWorker(host, port, startGameRequest);
            networkingThread = new Thread(networkWorker);
            networkingThread.start();
        }
    }
}
