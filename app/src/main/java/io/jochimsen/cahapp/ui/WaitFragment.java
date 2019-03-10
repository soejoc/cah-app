package io.jochimsen.cahapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.jochimsen.cahapp.R;
import lombok.Setter;

public class WaitFragment extends Fragment {

    @BindView(R.id.waitMessageView)
    TextView waitMessageView;

    @Setter
    private int resId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_wait, container, false);
        ButterKnife.bind(this, view);

        if(resId != 0) {
            waitMessageView.setText(resId);
        }

        return view;
    }
}
