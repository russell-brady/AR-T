package com.arproject.russell.ar_t.login;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arproject.russell.ar_t.guided_tour.GuidedTourActivity;
import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.models.ApiResponse;
import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.utils.ApiInterface;
import com.arproject.russell.ar_t.utils.ApiUtils;
import com.arproject.russell.ar_t.utils.PrefConfig;
import com.arproject.russell.ar_t.utils.TextWatcherAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    ApiInterface apiInterface;
    private LoginViewModel viewModel;
    private TextView signupLink;
    private TextInputEditText email;
    private TextInputLayout emailInputLayout;
    private TextInputEditText password;
    private TextInputLayout passwordInputLayout;
    private AppCompatTextView loginButton;
    private PrefConfig prefConfig;
    private ContentLoadingProgressBar progressBar;


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBar = getActivity().findViewById(R.id.progress_bar);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        signupLink = view.findViewById(R.id.link_signup);
        email = view.findViewById(R.id.input_email);
        emailInputLayout = view.findViewById(R.id.emailInputLayout);
        password = view.findViewById(R.id.login_password);
        passwordInputLayout = view.findViewById(R.id.passwordInputLayout);
        loginButton = view.findViewById(R.id.login);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        passwordInputLayout.setTypeface(boldTypeface);
        password.addTextChangedListener(new TextWatcherAdapter(){
            @Override
            public void afterTextChanged(Editable editable) {
                passwordInputLayout.setPasswordVisibilityToggleEnabled(editable.length()>0);
            }
        });
        email.setOnFocusChangeListener((temp,hasFocus)->{
            if(!hasFocus){
                boolean isEnabled=email.getText().length()>0;
                email.setSelected(isEnabled);
            }
        });
        password.setOnFocusChangeListener((temp,hasFocus)->{
            if(!hasFocus){
                boolean isEnabled=password.getText().length()>0;
                password.setSelected(isEnabled);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        prefConfig = new PrefConfig(getContext());
        apiInterface = ApiUtils.getApiService();
        signupClickListener(signupLink);
        loginClickListener(loginButton);
    }

    private void signupClickListener(TextView signup) {
        signup.setOnClickListener(view -> getFragmentManager()
                .beginTransaction()
                .add(R.id.container, SignUpFragment.newInstance())
                .commit());
    }

    private void loginClickListener(TextView login) {
        login.setOnClickListener(view -> performLogin());
    }

    private void performLogin() {
        emailInputLayout.setError(null);
        passwordInputLayout.setError(null);
        String userEmail = email.getText().toString();
        String pword = password.getText().toString();

        if (!viewModel.isValidEmail(userEmail)) {
            emailInputLayout.setError(getString(R.string.invalid_email));
        } else if (!viewModel.isValidPassword(pword)) {
            passwordInputLayout.setError(getString(R.string.invalid_password));
        } else {
            sendPost(userEmail, pword);
        }

    }

    private void sendPost(String userEmail, String userPassword) {

        progressBar.setVisibility(View.VISIBLE);
        apiInterface.performLogin(userEmail, userPassword).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.body() != null && response.body().getCode() == 200) {
                    prefConfig.setLoginStatus(true);
                    prefConfig.storeUserData(response.body().getUser());
                    finishLogin();
                } else {
                    Toast.makeText(getActivity(), R.string.failed_login, Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), R.string.login_failure, Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void finishLogin() {
        if (prefConfig.isFirstRun()) {
            prefConfig.setFirstRun(false);
            Intent intent = new Intent(getActivity(), GuidedTourActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
        } else {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
        }
    }

}
