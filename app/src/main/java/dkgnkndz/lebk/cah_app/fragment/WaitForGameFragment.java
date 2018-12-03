package dkgnkndz.lebk.cah_app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import dkgnkndz.lebk.cah_app.R;

public class WaitForGameFragment extends FragmentBase {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_wait_for_game, container, false);
        ButterKnife.bind(view);

        return view;
    }
}
