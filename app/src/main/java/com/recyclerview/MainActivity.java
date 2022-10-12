package com.recyclerview;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.os.Bundle;



import com.recyclerview.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;



    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentReplace(new HomeFragment());

        binding.navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    fragmentReplace(new HomeFragment());
                    break;

                case R.id.notifications:
                    fragmentReplace(new NotificationFragment());
                    break;

                case R.id.profile:
                    fragmentReplace(new ProfileFragment());
                    break;
            }



            return true;
        });

    }

    private void fragmentReplace (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragmentManager.popBackStack();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}