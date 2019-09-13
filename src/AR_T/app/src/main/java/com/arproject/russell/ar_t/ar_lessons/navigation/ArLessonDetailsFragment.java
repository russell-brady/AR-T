package com.arproject.russell.ar_t.ar_lessons.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.ar_lessons.ArLessonActivity;
import com.arproject.russell.ar_t.models.Topic;
import com.arproject.russell.ar_t.models.Topics;

public class ArLessonDetailsFragment extends Fragment {

    private ImageView imageView;
    private TextView title;
    private TextView subtitle;
    private TextView content;
    private FloatingActionButton floatingActionButton;
    private Topic topic;
    private int position;

    public static ArLessonDetailsFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        ArLessonDetailsFragment fragment = new ArLessonDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("position");
            topic = getTopic();
        }
    }

    public Topic getTopic() {
        return Topics.getItems().get(position);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.topic_details_fragment, container, false);
        imageView = view.findViewById(R.id.backdrop);
        title = view.findViewById(R.id.topic_title);
        subtitle = view.findViewById(R.id.topic_desc);
        content = view.findViewById(R.id.topic_content);
        floatingActionButton = view.findViewById(R.id.arFab);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView.setImageResource(topic.getImageId());
        title.setText(topic.getTitle());
        content.setText(topic.getContents());
        subtitle.setText(topic.getDesc());

        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), ArLessonActivity.class);
            intent.putExtra("route", position + 1);
            startActivity(intent);
        });
    }

}
