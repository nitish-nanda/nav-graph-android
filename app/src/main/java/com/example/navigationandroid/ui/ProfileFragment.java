package com.example.navigationandroid.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.navigationandroid.Base.BaseFragment;
import com.example.navigationandroid.R;

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
        return inflater.inflate(R.layout.fragment_profile, container, false);
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

}
