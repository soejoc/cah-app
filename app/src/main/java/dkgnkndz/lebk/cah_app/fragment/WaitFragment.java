package dkgnkndz.lebk.cah_app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dkgnkndz.lebk.cah_app.R;

public class WaitFragment extends FragmentBase {

    @BindView(R.id.waitMessageView)
    TextView waitMessageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_wait, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    public void setMessage(final String message) {
        waitMessageView.setText(message);
    }
}
