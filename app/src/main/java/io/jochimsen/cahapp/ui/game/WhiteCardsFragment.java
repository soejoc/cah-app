package io.jochimsen.cahapp.ui.game;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.jochimsen.cahapp.R;
import io.jochimsen.cahapp.model.WhiteCardModel;
import io.jochimsen.cahapp.network.handler.MessageHandler;
import io.jochimsen.cahapp.repository.AvailableCardsRepository;
import io.jochimsen.cahapp.ui.WaitFragment;
import io.jochimsen.cahapp.view_model.GameViewModel;
import io.jochimsen.cahframework.protocol.object.message.request.SelectCardsRequest;

public class WhiteCardsFragment extends Fragment {

    @BindView(R.id.white_card_1)
    TextView whiteCard1TextView;

    @BindView(R.id.white_card_2)
    TextView whiteCard2TextView;

    @BindView(R.id.white_card_3)
    TextView whiteCard3TextView;

    private AvailableCardsRepository availableCardsRepository;

    private List<WhiteCardModel> whiteCardModels;

    public void setAvailableCardsRepository(final AvailableCardsRepository availableCardsRepository) {
        this.availableCardsRepository = availableCardsRepository;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_white_cards, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final GameViewModel gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        gameViewModel.setAvailableCardsRepository(availableCardsRepository);

        gameViewModel.getWhiteCardModelsLiveData().observe(this, whiteCardModels -> {
            whiteCard1TextView.setText(whiteCardModels.get(0).getText());
            whiteCard2TextView.setText(whiteCardModels.get(1).getText());
            whiteCard3TextView.setText(whiteCardModels.get(2).getText());

            this.whiteCardModels = whiteCardModels;
        });

        whiteCard1TextView.setOnClickListener(v -> {
            final String text = whiteCard1TextView.getText().toString();

            WhiteCardModel selectedWhiteCardModel = null;

            for(final WhiteCardModel whiteCardModel : whiteCardModels) {
                if(whiteCardModel.getText().equals(text)) {
                    selectedWhiteCardModel = whiteCardModel;
                    break;
                }
            }

            final SelectCardsRequest selectCardsRequest = new SelectCardsRequest();

            final io.jochimsen.cahframework.protocol.object.model.WhiteCardModel whiteCardModel = new io.jochimsen.cahframework.protocol.object.model.WhiteCardModel();
            whiteCardModel.whiteCardId = selectedWhiteCardModel.getWhiteCardId();

            List<io.jochimsen.cahframework.protocol.object.model.WhiteCardModel> whiteCardModels = new ArrayList<>();
            whiteCardModels.add(whiteCardModel);

            selectCardsRequest.whiteCardModels = whiteCardModels;

            MessageHandler.getServerSession().say(selectCardsRequest);

            final FragmentManager fragmentManager = getFragmentManager();
            final WaitFragment waitFragment = new WaitFragment();
            waitFragment.setMessage(getString(R.string.wait_on_game_master));

            fragmentManager.beginTransaction().replace(R.id.container, waitFragment).commit();
        });

        whiteCard2TextView.setOnClickListener(v -> {
            final String text = whiteCard2TextView.getText().toString();

            WhiteCardModel selectedWhiteCardModel = null;

            for(final WhiteCardModel whiteCardModel : whiteCardModels) {
                if(whiteCardModel.getText().equals(text)) {
                    selectedWhiteCardModel = whiteCardModel;
                    break;
                }
            }

            final SelectCardsRequest selectCardsRequest = new SelectCardsRequest();

            final io.jochimsen.cahframework.protocol.object.model.WhiteCardModel whiteCardModel = new io.jochimsen.cahframework.protocol.object.model.WhiteCardModel();
            whiteCardModel.whiteCardId = selectedWhiteCardModel.getWhiteCardId();

            List<io.jochimsen.cahframework.protocol.object.model.WhiteCardModel> whiteCardModels = new ArrayList<>();
            whiteCardModels.add(whiteCardModel);

            selectCardsRequest.whiteCardModels = whiteCardModels;

            MessageHandler.getServerSession().say(selectCardsRequest);

            final FragmentManager fragmentManager = getFragmentManager();
            final WaitFragment waitFragment = new WaitFragment();
            waitFragment.setMessage(getString(R.string.wait_on_game_master));

            fragmentManager.beginTransaction().replace(R.id.container, waitFragment).commit();
        });

        whiteCard3TextView.setOnClickListener(v -> {
            final String text = whiteCard3TextView.getText().toString();

            WhiteCardModel selectedWhiteCardModel = null;

            for(final WhiteCardModel whiteCardModel : whiteCardModels) {
                if(whiteCardModel.getText().equals(text)) {
                    selectedWhiteCardModel = whiteCardModel;
                    break;
                }
            }

            final SelectCardsRequest selectCardsRequest = new SelectCardsRequest();

            final io.jochimsen.cahframework.protocol.object.model.WhiteCardModel whiteCardModel = new io.jochimsen.cahframework.protocol.object.model.WhiteCardModel();
            whiteCardModel.whiteCardId = selectedWhiteCardModel.getWhiteCardId();

            List<io.jochimsen.cahframework.protocol.object.model.WhiteCardModel> whiteCardModels = new ArrayList<>();
            whiteCardModels.add(whiteCardModel);

            selectCardsRequest.whiteCardModels = whiteCardModels;

            MessageHandler.getServerSession().say(selectCardsRequest);

            final FragmentManager fragmentManager = getFragmentManager();
            final WaitFragment waitFragment = new WaitFragment();
            waitFragment.setMessage(getString(R.string.wait_on_game_master));

            fragmentManager.beginTransaction().replace(R.id.container, waitFragment).commit();
        });
    }
}
