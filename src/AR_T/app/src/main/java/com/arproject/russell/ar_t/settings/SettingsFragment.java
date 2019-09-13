package com.arproject.russell.ar_t.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.guided_tour.GuidedTourActivity;
import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.theme.ChooseThemeFragment;
import com.arproject.russell.ar_t.utils.PrefConfig;

public class SettingsFragment extends PreferenceFragmentCompat {

    private PrefConfig prefConfig;
    private ContentLoadingProgressBar progressBar;

    public static SettingsFragment newInstance() {

        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.settings_title);
        }
        progressBar = ((MainActivity) getActivity()).findViewById(R.id.main_progress_bar);
        prefConfig = new PrefConfig(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(R.color.white));
        view.setClickable(true);

        Preference chooseThemePref = getPreferenceManager().findPreference("theme");
        Preference showcasePref = getPreferenceManager().findPreference("showcase");
        Preference guidedTourPref = getPreferenceManager().findPreference("guidedtour");
        Preference logoutPref = getPreferenceManager().findPreference("logout");
        Preference deleteAccountPref = getPreferenceManager().findPreference("deleteaccount");

        chooseThemePref.setOnPreferenceClickListener(preference -> {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_content, ChooseThemeFragment.newInstance())
                    .addToBackStack(getString(R.string.choose_theme))
                    .commit();
            return false;
        });

        logoutPref.setOnPreferenceClickListener(preference -> {
            ((MainActivity) getActivity()).getLogoutDialog();
            return true;
        });

        deleteAccountPref.setOnPreferenceClickListener(preference -> {
            deleteAccount();
            return true;
        });

        guidedTourPref.setOnPreferenceClickListener(preference -> {
            Intent intent = new Intent(getActivity(), GuidedTourActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        });

        showcasePref.setOnPreferenceClickListener(preference -> {
            prefConfig.showSequence(true);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
            return true;
        });


        return view;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void deleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete Account")
                .setMessage("Do you wish to permanantly delete your account?")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    progressBar.setVisibility(View.VISIBLE);
                    ((MainActivity) getActivity()).logout();
                })
                .setNegativeButton("CANCEL", (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }
}
