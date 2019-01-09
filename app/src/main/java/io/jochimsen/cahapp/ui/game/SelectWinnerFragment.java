package io.jochimsen.cahapp.ui.game;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.jochimsen.cahapp.R;
import io.jochimsen.cahapp.model.MergedCardModel;
import io.jochimsen.cahapp.network.handler.MessageHandler;
import io.jochimsen.cahapp.network.handler.MessageSubject;
import io.jochimsen.cahapp.repository.AvailableCardsRepository;
import io.jochimsen.cahapp.repository.MergedCardRepository;
import io.jochimsen.cahapp.ui.WaitFragment;
import io.jochimsen.cahapp.view_model.GameViewModel;
import io.jochimsen.cahapp.view_model.SelectWinnerViewModel;
import io.jochimsen.cahframework.protocol.object.message.request.SelectWinnerRequest;
import io.jochimsen.cahframework.protocol.object.model.WhiteCardModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SelectWinnerFragment extends Fragment {

    @BindView(R.id.merged_card)
    TextView mergedCard;

    private MergedCardRepository mergedCardRepository;

    private List<MergedCardModel> mergedCardModels;

    public void setMergedCardRepository(final MergedCardRepository mergedCardRepository) {
        this.mergedCardRepository = mergedCardRepository;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_select_winner, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final SelectWinnerViewModel selectWinnerViewModel = ViewModelProviders.of(this).get(SelectWinnerViewModel.class);
        selectWinnerViewModel.setMergedCardRepository(mergedCardRepository);

        selectWinnerViewModel.getMergedCardModelsLiveData().observe(this, mergedCardModels -> {
            mergedCard.setText(mergedCardModels.get(0).getMergedText());
            this.mergedCardModels = mergedCardModels;
        });

        mergedCard.setOnClickListener(v -> {
            final SelectWinnerRequest selectWinnerRequest = new SelectWinnerRequest();

            selectWinnerRequest.whiteCardModels = mergedCardModels.stream()
                    .map(mergedCardModel -> {
                        final WhiteCardModel whiteCardModel = new WhiteCardModel();
                        whiteCardModel.whiteCardId = mergedCardModel.getWhiteCardId();

                        return whiteCardModel;
                    }).collect(Collectors.toList());

            MessageHandler.getServerSession().say(selectWinnerRequest);
        });
    }
}
