package com.example.navigationandroid.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.navigationandroid.R;
import com.example.navigationandroid.ui.authentication.SplashActivity;

public class MainActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavGraph navGraph = navController.getGraph();
        navGraph.setStartDestination(R.id.mainFragment);
        navController.setGraph(navGraph);
    }
}
