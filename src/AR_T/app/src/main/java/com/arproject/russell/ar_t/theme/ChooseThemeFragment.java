package com.arproject.russell.ar_t.theme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.utils.PrefConfig;

public class ChooseThemeFragment extends Fragment {

    PrefConfig prefConfig;

    public static ChooseThemeFragment newInstance() {
        ChooseThemeFragment fragment = new ChooseThemeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.choose_theme);
        }
        prefConfig = new PrefConfig(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_theme, container, false);

        AppCompatButton blueButton = view.findViewById(R.id.blueTheme);
        AppCompatButton orangeButton = view.findViewById(R.id.orangeTheme);
        AppCompatButton pinkButton = view.findViewById(R.id.pinkTheme);
        AppCompatButton blackButton = view.findViewById(R.id.blackTheme);
        AppCompatButton mintButton = view.findViewById(R.id.mintTheme);

        blueButton.setOnClickListener(view1 -> setTheme(R.style.AppTheme));
        orangeButton.setOnClickListener(view1 -> setTheme(R.style.RedTheme));
        pinkButton.setOnClickListener(view1 -> setTheme(R.style.PinkTheme));
        blackButton.setOnClickListener(view1 -> setTheme(R.style.BlackTheme));
        mintButton.setOnClickListener(view1 -> setTheme(R.style.MintTheme));
        return view;
    }

    private void setTheme(int mintTheme) {
        prefConfig.setTheme(mintTheme);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

}
