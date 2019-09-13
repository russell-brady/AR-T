package com.arproject.russell.ar_t.quizzes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arproject.russell.ar_t.R;

import com.arproject.russell.ar_t.ar_lessons.navigation.CardAdapter;
import com.arproject.russell.ar_t.models.Topic;

public class CardFragment extends Fragment {

    private static final String TOPIC = "TOPIC";
    private static final String POSITION = "POSITION";

    private CardView mCardView;
    private Button button;
    private TextView lessonTitle;
    private TextView lessonDesc;

    private Topic topic;
    private int position;

    private String buttonText;

    public static CardFragment newInstance(int position, Topic topic) {
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        args.putParcelable(TOPIC, topic);
        CardFragment fragment = new CardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            topic = bundle.getParcelable(TOPIC);
            position = bundle.getInt(POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lesson_card, container, false);
        mCardView = view.findViewById(R.id.cardView);
        button = view.findViewById(R.id.lessonButton);
        lessonTitle = view.findViewById(R.id.lessonTitle);
        lessonDesc = view.findViewById(R.id.lessonDesc);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCardView.setMaxCardElevation(mCardView.getCardElevation() * CardAdapter.MAX_ELEVATION_FACTOR);

        button.setOnClickListener(view1 -> {
                    Intent intent = new Intent(getActivity(), QuizActivity.class);
                    intent.putExtra("position", position + 1);
                    startActivity(intent);
                });

        lessonTitle.setText(topic.getTitle());
        lessonDesc.setText(topic.getDesc());

        if (buttonText != null) {
            button.setText(buttonText);
        }
    }

    public CardView getCardView() {
        return mCardView;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
        if (button != null) {
            button.setText(buttonText);
        }
    }

}
