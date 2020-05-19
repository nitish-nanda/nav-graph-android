package com.example.navigationandroid.ui.authentication;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.navigationandroid.base.BaseFragment;
import com.example.navigationandroid.R;
import com.example.navigationandroid.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;


public class SignInFragment extends BaseFragment {

    @BindView(R.id.et_email_forgot)
    public EditText etEmail;
    @BindView(R.id.et_password_sign_in)
    public EditText etPassword;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.ivLeft)
    void onClickLeft() {
        requireActivity().onBackPressed();
    }

    @OnClick(R.id.tv_create_account)
    void onClickCreate() {
        navController.navigate(R.id.action_signInFragment_to_signUpFragment);
    }

    @OnClick(R.id.tv_forgot)
    void onClickForgot() {
        navController.navigate(R.id.action_signInFragment_to_forgetFragment);
    }

    @OnClick(R.id.tv_login)
    void onClickLogin() {
        if (!validate()) {
            return;
        }
        navController.navigate(R.id.action_signInFragment_to_mainFragment);
    }

    private boolean validate() {
        if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Enter Email.");
            etEmail.requestFocus();
            return false;
        }
        if (Utils.isValidEmail(etEmail.getText().toString())) {
            etEmail.setError("Enter valid Email.");
            etEmail.requestFocus();
            return false;
        }
        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Enter Password.");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }
}
