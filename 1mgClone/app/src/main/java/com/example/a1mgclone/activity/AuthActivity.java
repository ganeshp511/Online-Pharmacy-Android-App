package com.example.a1mgclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.a1mgclone.R;
import com.example.a1mgclone.fragment.SigninFragment;
import com.example.a1mgclone.fragment.SignupFragment;

public class AuthActivity extends AppCompatActivity {
    SignupFragment signupFragment = new SignupFragment();
    SigninFragment signinFragment = new SigninFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        onSignin();
    }

    public void onSignup() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layoutContainer, signupFragment)
                .commit();
    }

    public void onSignin() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layoutContainer, signinFragment)
                .commit();
    }
}