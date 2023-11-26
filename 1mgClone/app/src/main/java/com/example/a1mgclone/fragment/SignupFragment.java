package com.example.a1mgclone.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a1mgclone.R;
import com.example.a1mgclone.activity.*;
import com.example.a1mgclone.fragment.*;
import com.example.a1mgclone.network.API;

import org.json.JSONObject;

import java.util.HashMap;


public class SignupFragment extends Fragment {

    EditText editFirstName, editLastName, editEmail, editPassword, editConfirmPassword;
    Button buttonSignup;
    TextView textSignin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editFirstName = view.findViewById(R.id.editFirstName);
        editLastName = view.findViewById(R.id.editLastName);
        editEmail = view.findViewById(R.id.editEmail);
        editPassword = view.findViewById(R.id.editPassword);
        editConfirmPassword = view.findViewById(R.id.editConfirmPassword);
        buttonSignup = view.findViewById(R.id.buttonSignup);
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignup();
            }
        });
        textSignin = view.findViewById(R.id.textSignin);
        textSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthActivity activity = (AuthActivity) getActivity();
                activity.onSignin();
            }
        });
    }

    private void onSignup() {
        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        String confirmPassword = editConfirmPassword.getText().toString();

        if (firstName.length() == 0) {
            Toast.makeText(getContext(), "Please enter first name", Toast.LENGTH_SHORT).show();
        } else if (lastName.length() == 0) {
            Toast.makeText(getContext(), "Please enter last name", Toast.LENGTH_SHORT).show();
        } else if (email.length() == 0) {
            Toast.makeText(getContext(), "Please enter email", Toast.LENGTH_SHORT).show();
        } else if (password.length() == 0) {
            Toast.makeText(getContext(), "Please enter password", Toast.LENGTH_SHORT).show();
        } else if (confirmPassword.length() == 0) {
            Toast.makeText(getContext(), "Please confirm password", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(getContext(), "Password does not match", Toast.LENGTH_SHORT).show();
        } else {
            // body
            HashMap<String, String> map = new HashMap<>();
            map.put("firstName", firstName);
            map.put("lastName", lastName);
            map.put("email", email);
            map.put("password", password);

            // make the call
            API.request("/user/signup")
                    .jsonBody(new JSONObject(map))
                    .call(new API.ResponseListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getContext(), "Congratulations!!! Successfully registered a new user..", Toast.LENGTH_SHORT).show();
                            ((AuthActivity) getActivity()).onSignin();
                        }
                    }, new API.ErrorListener() {
                        @Override
                        public void onError(String error) {
                            Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                        }
        });
        }
    }}
