package com.example.navigationandroid.ui.authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigationandroid.Base.BaseFragment;
import com.example.navigationandroid.R;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends BaseFragment {

    @BindView(R.id.etNewPassword)
    public TextInputEditText etPassword;
    @BindView(R.id.etConfirmPassword)
    public TextInputEditText etConfirmPassword;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_password, container, false);
    }

    @OnClick(R.id.ivLeft)
    void onClickLeft() {
        navController.navigateUp();
    }

    @OnClick(R.id.tvDone)
    void onClickDone() {
        if (!validate()) {
            return;
        }
        navController.navigate(R.id.action_resetPasswordFragment_to_signInFragment);
    }

    private boolean validate() {
        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Enter Password.");
            etPassword.requestFocus();
            return false;
        }
        if (etPassword.getText().toString().length() < 6) {
            etPassword.setError("Password must contain six digits.");
            etPassword.requestFocus();
            return false;
        }
        if (etConfirmPassword.getText().toString().isEmpty()) {
            etConfirmPassword.setError("Confirm Password.");
            etConfirmPassword.requestFocus();
            return false;
        }
        if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            etConfirmPassword.setError("The password must be the same.");
            etConfirmPassword.requestFocus();
            return false;
        }
        return true;
    }

}
