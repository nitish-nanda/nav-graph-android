package com.example.navigationandroid.ui.authentication;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigationandroid.Base.BaseFragment;
import com.example.navigationandroid.R;

import butterknife.OnClick;

public class MainFragment extends BaseFragment {

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @OnClick(R.id.ivRight)
    void onClickProfile() {
        navController.navigate(R.id.action_mainFragment_to_profileFragment);
    }

}
