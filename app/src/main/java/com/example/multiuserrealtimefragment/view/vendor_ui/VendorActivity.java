package com.example.multiuserrealtimefragment.view.vendor_ui;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.multiuserrealtimefragment.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;

public class VendorActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    VendorHomeFragment vendorHomeFragment = new VendorHomeFragment();
    VendorChatFragment vendorChatFragment = new VendorChatFragment();
    VendorProjectFragment vendorProjectFragment = new VendorProjectFragment();
    VendorProfileFragment vendorProfileFragment = new VendorProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vendor);

        bottomNavigationView = findViewById(R.id.nav_view_vendor);

        getSupportFragmentManager().beginTransaction().replace(R.id.containerVendor, vendorHomeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home_vendor) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerVendor, vendorHomeFragment).commit();
                    return true;
                } else if (itemId == R.id.navigation_chat_vendor) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerVendor, vendorChatFragment).commit();
                    return true;
                } else if (itemId == R.id.navigation_project_vendor) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerVendor, vendorProjectFragment).commit();
                    return true;
                } else if (itemId == R.id.navigation_profile_vendor) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerVendor, vendorProfileFragment).commit();
                    return true;
                }
                return false;
            }
        });


    }

}