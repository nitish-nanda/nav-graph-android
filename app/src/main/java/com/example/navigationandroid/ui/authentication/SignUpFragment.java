package com.example.navigationandroid.ui.authentication;

import android.app.DatePickerDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.navigationandroid.Base.BaseFragment;
import com.example.navigationandroid.R;
import com.example.navigationandroid.Utils.DateHelper;
import com.example.navigationandroid.Utils.ToastUtils;
import com.example.navigationandroid.Utils.Utils;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

public class SignUpFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.textInputLayoutName)
    public TextInputLayout layoutName;
    @BindView(R.id.textInputLayoutEmail)
    public TextInputLayout layoutEmail;
    @BindView(R.id.textInputLayoutPhone)
    public TextInputLayout layoutPhone;
    @BindView(R.id.textInputLayoutDob)
    public TextInputLayout layoutDob;
    @BindView(R.id.textInputLayoutpassword)
    public TextInputLayout layoutPassword;

    @BindView(R.id.spinnerGender)
    public Spinner spinner;

    private String gender;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSpinner();
    }

    private void initSpinner() {
        String[] genderList = {"Select Gender", "Male", "Female"};
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, genderList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @OnClick({R.id.ivLeft, R.id.textViewLogin})
    void onClickLeft() {
        requireActivity().onBackPressed();
    }

    @OnClick(R.id.textInputLayoutDob)
    void onClickDOB() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), (view, year, month, dayOfMonth) ->
        {
            EditText editText = layoutDob.getEditText();
            editText.setText(DateHelper.getDate(year, month + 1, dayOfMonth));
            editText.setSelection(editText.getText().length());
            spinner.requestFocus();
        }, mYear, mMonth, mDay);
        dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        dialog.show();
    }

    @OnClick(R.id.textViewCreateAccount)
    void onClickCreate() {
        if (!validate()) {
            return;
        }
        navController.navigate(R.id.action_signUpFragment_to_verificationFragment);
    }

    private boolean validate() {
        if (layoutName.getEditText().getText().toString().isEmpty()) {
            layoutName.getEditText().setError("Enter Name");
            layoutName.getEditText().requestFocus();
            return false;
        }
        if (layoutName.getEditText().getText().toString().length() < 3) {
            layoutName.getEditText().setError("Enter Valid Name");
            layoutName.getEditText().requestFocus();
            return false;
        }
        if (layoutEmail.getEditText().getText().toString().isEmpty()) {
            layoutEmail.getEditText().setError("Enter Email");
            layoutEmail.getEditText().requestFocus();
            return false;
        }
        if (!Utils.isValidEmail(layoutEmail.getEditText().getText().toString())) {
            layoutEmail.getEditText().setError("Enter Valid Email");
            layoutEmail.getEditText().requestFocus();
            return false;
        }
        if (layoutPhone.getEditText().getText().toString().isEmpty()) {
            layoutPhone.getEditText().setError("Enter Phone Number");
            layoutPhone.getEditText().requestFocus();
            return false;
        }
        if (layoutPhone.getEditText().getText().toString().length() < 7) {
            layoutPhone.getEditText().setError("Enter Valid Phone Number");
            layoutPhone.getEditText().requestFocus();
            return false;
        }
        if (layoutDob.getEditText().getText().toString().isEmpty()) {
            layoutDob.getEditText().setError("Enter Age");
            layoutDob.getEditText().requestFocus();
            return false;
        }
        if (layoutPassword.getEditText().getText().toString().isEmpty()) {
            layoutPassword.getEditText().setError("Enter Password");
            layoutPassword.getEditText().requestFocus();
            return false;
        }
        if (layoutPassword.getEditText().getText().toString().length() < 6) {
            layoutPassword.getEditText().setError("Enter Valid Password");
            layoutPassword.getEditText().requestFocus();
            return false;
        }
        if (gender != null && gender.equalsIgnoreCase("Select Gender")) {
            ToastUtils.shortToast(getActivity(), "Select Valid Gender");
            return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gender = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
