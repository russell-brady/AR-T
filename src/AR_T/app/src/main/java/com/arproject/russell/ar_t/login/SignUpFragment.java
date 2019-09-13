package com.arproject.russell.ar_t.login;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.arproject.russell.ar_t.models.ApiResponse;
import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.utils.ApiInterface;
import com.arproject.russell.ar_t.utils.ApiUtils;
import com.arproject.russell.ar_t.utils.TextWatcherAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {

    public static final String STUDENT = "Student";
    public static final String TEACHER = "Teacher";

    private ApiInterface apiInterface;
    private SignUpViewModel viewModel;
    private TextView loginLink;
    private TextInputEditText name;
    private TextInputLayout nameInputLayout;
    private TextInputEditText email;
    private TextInputLayout emailInputLayout;
    private TextInputEditText password;
    private TextInputLayout passwordInputLayout;
    private TextInputEditText reEnterPassword;
    private TextInputLayout reEnterPasswordLayout;
    private AppCompatTextView signupButton;
    private AppCompatSpinner spinner;
    private TextInputLayout classIdLayout;
    private TextInputEditText classId;
    private ContentLoadingProgressBar progressBar;
    private ArrayList<TextInputEditText> editTexts = new ArrayList<>();

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBar = getActivity().findViewById(R.id.progress_bar);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_fragment, container, false);
        loginLink = view.findViewById(R.id.link_login);
        email = view.findViewById(R.id.signup_email);
        emailInputLayout = view.findViewById(R.id.emailInputLayout);
        name = view.findViewById(R.id.input_name);
        nameInputLayout = view.findViewById(R.id.nameInputLayout);
        password = view.findViewById(R.id.input_password);
        passwordInputLayout = view.findViewById(R.id.passwordInputLayout);
        reEnterPassword = view.findViewById(R.id.input_reEnterPassword);
        reEnterPasswordLayout = view.findViewById(R.id.passwordReEnter);
        signupButton = view.findViewById(R.id.btn_signup);
        spinner = view.findViewById(R.id.spinner);
        classId = view.findViewById(R.id.input_classId);
        classIdLayout = view.findViewById(R.id.classIdLayout);

        TextInputEditText[] array = {email, name, password, reEnterPassword, classId};
        editTexts.addAll(Arrays.asList(array));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        passwordInputLayout.setTypeface(boldTypeface);
        reEnterPasswordLayout.setTypeface(boldTypeface);
        password.addTextChangedListener(new TextWatcherAdapter(){
            @Override
            public void afterTextChanged(Editable editable) {
                passwordInputLayout.setPasswordVisibilityToggleEnabled(editable.length()>0);
            }
        });
        reEnterPassword.addTextChangedListener(new TextWatcherAdapter(){
            @Override
            public void afterTextChanged(Editable editable) {
                reEnterPasswordLayout.setPasswordVisibilityToggleEnabled(editable.length()>0);
            }
        });
        for(TextInputEditText editText:editTexts){
            editText.setOnFocusChangeListener((temp,hasFocus)->{
                if(!hasFocus){
                    boolean isEnabled=editText.getText().length()>0;
                    editText.setSelected(isEnabled);
                }
            });
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        apiInterface = ApiUtils.getApiService();
        loginClickListener(loginLink);
        signupClickListener(signupButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.options, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        setClassIdVisibility();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setClassIdVisibility();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setClassIdVisibility() {
        if (spinner.getSelectedItem().toString().equals(STUDENT)) {
            classIdLayout.setVisibility(View.VISIBLE);
        } else {
            classIdLayout.setVisibility(View.GONE);
        }
    }

    private void signupClickListener(TextView signup) {
        signup.setOnClickListener(view -> performRegistration());
    }

    private void loginClickListener(TextView login) {
        login.setOnClickListener(view -> moveToLogin());
    }

    private void moveToLogin() {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.container, LoginFragment.newInstance())
                .commit();
    }

    public void performRegistration() {
        String userEmail = email.getText().toString();
        String name = this.name.getText().toString();
        String pword = password.getText().toString();
        String reEnterPword = reEnterPassword.getText().toString();
        String type = spinner.getSelectedItem().toString();
        String classIdString = classId.getText().toString();

        int classId;
        if (!classIdString.isEmpty()) {
             classId = Integer.parseInt(classIdString);
        } else {
            classId = -1;
        }

        if (!viewModel.isValidName(name)) {
            nameInputLayout.setError(getString(R.string.invalid_name));
        } else if (!viewModel.isValidEmail(userEmail)) {
            emailInputLayout.setError(getString(R.string.invalid_email));
        } else if (!viewModel.isValidPassword(pword, reEnterPword)) {
            passwordInputLayout.setError(getString(R.string.invalid_password));
        } else if (!viewModel.isValidClassId(classId)){
            classIdLayout.setError(getString(R.string.invalid_class_id));
        } else {
            signUp(userEmail, pword, type, name, classId);
        }

    }

    private void signUp(String userEmail, String pword, String type, String name, int classId) {

        progressBar.setVisibility(View.VISIBLE);
        if (type.equals(TEACHER)) {
            apiInterface.performTeacherRegistration(userEmail, pword, type, name).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                    checkResponse(response);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                    Toast.makeText(getActivity(), R.string.register_fail, Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            apiInterface.performStudentRegistration(userEmail, pword, type, name, classId).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    checkResponse(response);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), R.string.register_fail, Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void checkResponse(@NonNull Response<ApiResponse> response) {
        if (response.body().getCode() == 200) {
            Toast.makeText(getActivity(), R.string.register_success, Toast.LENGTH_LONG).show();
            moveToLogin();
        } else {
            Toast.makeText(getActivity(), response.body().getSuccess(), Toast.LENGTH_LONG).show();
        }
    }

}