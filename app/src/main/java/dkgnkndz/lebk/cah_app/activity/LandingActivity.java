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

    private static final String host = "127.0.0.1";
    private static final int port = 8346;
    
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
        final String nickname = this.nickname.getText().toString();

        //ToDo: Check if a networking thread already exists
        NetworkWorker networkWorker = new NetworkWorker(host, port, nickname);
        networkingThread = new Thread(networkWorker);
        networkingThread.start();
    }
}
