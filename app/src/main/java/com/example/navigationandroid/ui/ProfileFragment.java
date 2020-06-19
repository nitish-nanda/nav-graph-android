package com.example.navigationandroid.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.navigationandroid.base.BaseFragment;
import com.example.navigationandroid.R;
import com.example.navigationandroid.databinding.FragmentProfileBinding;
import com.example.navigationandroid.models.UserModel;
import com.example.navigationandroid.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfileFragment extends BaseFragment {

    @BindView(R.id.ivLeft)
    public ImageView ivLeft;
    @BindView(R.id.ivRight)
    public ImageView ivRight;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        UserModel user = new UserModel();
        user.setName("Jassi");
        user.setDob("02/01/2005");
        user.setGender("Male");
        user.setEmail("jassi2005@gmail.com");
        user.setPhone("0987654321");
        binding.setUser(user);
        binding.setListener(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
    }

    private void initToolbar() {
        ivLeft.setPadding(7, 7, 7, 7);
        ivLeft.setImageResource(R.drawable.ic_back_black);
        ivRight.setPadding(7, 7, 7, 7);
        ivRight.setImageResource(R.mipmap.icon_logout);
    }


    @OnClick(R.id.ivLeft)
    void onClickLeft() {
        requireActivity().onBackPressed();
    }

    @OnClick(R.id.ivRight)
    void onClickRight() {
        navController.navigate(R.id.action_profileFragment_to_signInFragment);
    }

    public void onClickListener(View view) {
        ToastUtils.longToast("Clicked on image");
    }
}
