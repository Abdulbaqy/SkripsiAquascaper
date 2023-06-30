package com.example.multiuserrealtimefragment.view.customer_ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.multiuserrealtimefragment.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.navigation.NavigationBarView;

public class CustomerActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CustomerHomeFragment customerHomeFragment = new CustomerHomeFragment();
    CustomerChatFragment customerChatFragment = new CustomerChatFragment();
    CustomerProjectFragment customerProjectFragment = new CustomerProjectFragment();
    CustomerProfileFragment customerProfileFragment = new CustomerProfileFragment();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        boolean nightMode = sharedPreferences.getBoolean("night", false);

        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);


        bottomNavigationView = findViewById(R.id.nav_view_customer);

        getSupportFragmentManager().beginTransaction().replace(R.id.containerCustomer, customerHomeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home_customer) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerCustomer, customerHomeFragment).commit();
                    return true;
                } else if (itemId == R.id.navigation_chat_customer) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerCustomer, customerChatFragment).commit();
                    return true;
                } else if (itemId == R.id.navigation_project_customer) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerCustomer, customerProjectFragment).commit();
                    return true;
                } else if (itemId == R.id.navigation_profile_customer) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerCustomer, customerProfileFragment).commit();
                    return true;
                }
                return false;
            }
        });



    }

}