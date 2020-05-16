package com.example.navigationandroid.ui.authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigationandroid.base.BaseFragment;
import com.example.navigationandroid.R;
import com.example.navigationandroid.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetFragment extends BaseFragment {

    @BindView(R.id.et_email_forgot)
    public TextInputEditText etEmail;

    public ForgetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forget, container, false);
    }

    @OnClick(R.id.ivLeft)
    void onClickLeft() {
        requireActivity().onBackPressed();
    }

    @OnClick(R.id.tv_forgot_code)
    void onClickCode() {
        if (!validate()) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("from", "forgot");
        navController.navigate(R.id.action_forgetFragment_to_verificationFragment, bundle);
    }

    private boolean validate() {
        if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Enter Email");
            etEmail.requestFocus();
            return false;
        }
        if (Utils.isValidEmail(etEmail.getText().toString())) {
            etEmail.setError("Enter Valid Email");
            etEmail.requestFocus();
            return false;
        }
        return true;
    }
}
