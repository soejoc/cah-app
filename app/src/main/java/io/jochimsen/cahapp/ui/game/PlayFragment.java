package io.jochimsen.cahapp.ui.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.jochimsen.cahapp.R;

public class PlayFragment extends Fragment {

    @BindView(R.id.waitMessageView)
    TextView waitMessageView;

    private String message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_wait, container, false);
        ButterKnife.bind(this, view);

        waitMessageView.setText(message);

        return view;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
