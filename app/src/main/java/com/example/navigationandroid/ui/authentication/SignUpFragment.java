package com.example.navigationandroid.ui.authentication;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.navigationandroid.base.BaseFragment;
import com.example.navigationandroid.R;
import com.example.navigationandroid.models.UserModel;
import com.example.navigationandroid.utils.ToastUtils;
import com.example.navigationandroid.utils.helper.CameraHelper;
import com.example.navigationandroid.utils.helper.DateHelper;
import com.example.navigationandroid.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SignUpFragment extends BaseFragment implements CameraHelper.CameraHelperCallback, AdapterView.OnItemSelectedListener {

    @BindView(R.id.et_name)
    public TextInputEditText etName;
    @BindView(R.id.et_email)
    public TextInputEditText etEmail;
    @BindView(R.id.et_phone)
    public TextInputEditText etPhone;
    @BindView(R.id.etDOB)
    public TextInputEditText etDob;
    @BindView(R.id.et_password)
    public TextInputEditText etPassword;

    @BindView(R.id.spinnerGender)
    public Spinner spinner;

    @BindView(R.id.iv_profile)
    public ImageView ivProfile;

    private String gender, selectedImagePath;
    private CameraHelper cameraHelper;

    private Date dob;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraHelper = new CameraHelper(requireContext(), this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSpinner();
    }

    private void initSpinner() {
        String[] genderList = {"Male", "Female"};
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, genderList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SignUpFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cameraHelper.onResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.iv_profile)
    void onClickProfile() {
        SignUpFragmentPermissionsDispatcher.pickImageWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void pickImage() {
        cameraHelper.setCropping(true);
        cameraHelper.initCameraDialog();
    }

    @OnClick({R.id.ivLeft, R.id.textViewLogin})
    void onClickLeft() {
        requireActivity().onBackPressed();
    }

    @OnClick({R.id.textInputLayoutDob, R.id.etDOB})
    void onClickDOB() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        if (dob != null)
            c.setTime(dob);
        DatePickerDialog dialog = new DatePickerDialog(requireActivity(), (view, year, month, dayOfMonth) -> {
            dob = DateHelper.getDate(year, month, dayOfMonth);
            etDob.setText(DateHelper.getFormattedDate(dob));
            etDob.setSelection(etDob.getText().length());
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
        UserModel userModel = new UserModel();
        userModel.setName(etName.getText().toString());
        userModel.setEmail(etEmail.getText().toString());
        userModel.setPhone(etPhone.getText().toString());
        userModel.setDob(etDob.getText().toString());
        userModel.setPassword(etPassword.getText().toString());

        navController.navigate(R.id.action_signUpFragment_to_verificationFragment);
    }

    private boolean validate() {
        if (etName.getText().toString().isEmpty()) {
            etName.setError("Enter Name");
            etName.requestFocus();
            return false;
        }
        if (etName.getText().toString().length() < 3) {
            etName.setError("Enter Valid Name");
            etName.requestFocus();
            return false;
        }
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
        if (etPhone.getText().toString().isEmpty()) {
            etPhone.setError("Enter Phone Number");
            etPhone.requestFocus();
            return false;
        }
        if (etPhone.getText().toString().length() < 7) {
            etPhone.setError("Enter Valid Phone Number");
            etPhone.requestFocus();
            return false;
        }
        if (etDob.getText().toString().isEmpty()) {
            etDob.setError("Enter Age");
            etDob.requestFocus();
            return false;
        }
        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Enter Password");
            etPassword.requestFocus();
            return false;
        }
        if (etPassword.getText().toString().length() < 6) {
            etPassword.setError("Enter Valid Password");
            etPassword.requestFocus();
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

    @Override
    public void startIntent(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onSuccessImage(String imagePath) {
        selectedImagePath = imagePath;
        ivProfile.setImageURI(Uri.parse(selectedImagePath));
    }

    @Override
    public void onErrorImage(String error) {
        ToastUtils.shortToast(error);
    }
}
