package com.example.navigationandroid.ui.authentication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.navigationandroid.base.BaseFragment;
import com.example.navigationandroid.R;
import com.example.navigationandroid.utils.ToastUtils;
import com.example.navigationandroid.utils.Utils;
import com.example.navigationandroid.utils.notifications.NotificationEvent;
import com.example.navigationandroid.utils.notifications.NotificationManagerHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class VerificationFragment extends BaseFragment {

    @BindView(R.id.et1)
    public EditText etFirst;
    @BindView(R.id.et2)
    public EditText etSecond;
    @BindView(R.id.et3)
    public EditText etThird;
    @BindView(R.id.et4)
    public EditText etFourth;
    @BindView(R.id.et5)
    public EditText etFifth;

    private String verificationCode, otp;
    private StringBuilder stringBuilder;
    private String from;

    public VerificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verfication, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        from = getArguments().getString("from");
        stringBuilder = new StringBuilder();
        stringBuilder.append("00000");

        initListener();
        sendOTP();
    }

    private void sendOTP() {
        //FOR CLEARING SOME EXISTING NOTIFICATION EXISTS
        NotificationManagerHelper.clearAll();

        NotificationEvent event = new NotificationEvent();
        event.setTitle(getResources().getString(R.string.app_name));
        otp = String.valueOf(Utils.generateRandomNumber());
        String string = "Dear User, " + otp + " is your one time password(OTP).Please enter the OTP to proceed.";
        event.setBody(string);
        event.setSubject(string);
        NotificationManagerHelper.sendNotificationEvent(requireContext(), event);
    }

    private void initListener() {
        etFirst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etFirst.getText().length() == 1) {
                    stringBuilder.setCharAt(0, s.charAt(0));
                    etSecond.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //mListener.onFirstCodeChanged(s.toString());
            }
        });

        etSecond.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etSecond.getText().length() == 1) {
                    stringBuilder.setCharAt(1, s.charAt(0));
                    etThird.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // mListener.onSecondCodeChanged(s.toString());
            }
        });

        etThird.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etThird.getText().length() == 1) {
                    stringBuilder.setCharAt(2, s.charAt(0));
                    etFourth.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // mListener.onThirdCodeChanged(s.toString());
            }
        });

        etFourth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etFourth.length() == 1) {
                    stringBuilder.setCharAt(3, s.charAt(0));
                    etFifth.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //mListener.onForthCodeChanged(s.toString());
            }
        });

        etFifth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etFifth.length() == 1) {
                    stringBuilder.setCharAt(4, s.charAt(0));
                    hideKeyboard(getView());
                    onClickVerify();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //mListener.onForthCodeChanged(s.toString());
            }
        });

        etSecond.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() != KeyEvent.ACTION_DOWN) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (etSecond.getText().length() == 0 /*&& second*/) {
                                etFirst.requestFocus();
                                etFirst.setSelection(etFirst.length());
                                etFirst.setText("");
                            }
                        }
                    }, 50);
                }
                return false;
            }
        });

        etThird.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() != KeyEvent.ACTION_DOWN) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (etThird.getText().length() == 0 /*&& third*/) {
                                etSecond.requestFocus();
                                etSecond.setSelection(etSecond.length());
                                etSecond.setText("");
                            }
                        }
                    }, 50);
                }
                return false;
            }
        });

        etFourth.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() != KeyEvent.ACTION_DOWN) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (etFourth.getText().length() == 0 /*&& fourth*/) {
                                etThird.requestFocus();
                                etThird.setSelection(etThird.length());
                                etThird.setText("");
                            }
                        }
                    }, 50);
                }
                return false;
            }
        });
        etFourth.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() != KeyEvent.ACTION_DOWN) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (etFifth.getText().length() == 0 /*&& fourth*/) {
                                etFourth.requestFocus();
                                etFourth.setSelection(etFourth.length());
                                etFourth.setText("");
                            }
                        }
                    }, 50);
                }
                return false;
            }
        });
    }

    @OnClick(R.id.ivLeft)
    void onClickLeft() {
        requireActivity().onBackPressed();
    }

    @OnClick(R.id.tvVerify)
    void onClickVerify() {
        if (!validateCode()) {
            return;
        }
        if (from == null) {
            ToastUtils.longToast("Profile verified.");
            navController.navigate(R.id.action_verificationFragment_to_mainFragment);
        } else
            navController.navigate(R.id.action_verificationFragment_to_resetPasswordFragment);
    }

    private boolean validateCode() {
        if (etFirst.getText().length() == 0) {
            ToastUtils.shortToast("Enter Valid Verification Code");
            return false;
        }
        if (etSecond.getText().length() == 0) {
            ToastUtils.shortToast("Enter Valid Verification Code");
            return false;
        }
        if (etThird.getText().length() == 0) {
            ToastUtils.shortToast("Enter Valid Verification Code");
            return false;
        }
        if (etFourth.getText().length() == 0) {
            ToastUtils.shortToast("Enter Valid Verification Code");
            return false;
        }
        if (etFifth.getText().length() == 0) {
            ToastUtils.shortToast("Enter Valid Verification Code");
            return false;
        }
        verificationCode = etFirst.getText().toString() + etSecond.getText().toString() +
                etThird.getText().toString() + etFourth.getText().toString() +
                etFifth.getText().toString();

        if (!otp.equals(verificationCode)) {
            ToastUtils.longToast("You enter wrong verification code. Please try again using new OTP.");
            return false;
        }
        return true;
    }

    @OnClick(R.id.tvResent)
    void onClickResent() {
        sendOTP();
    }

}
