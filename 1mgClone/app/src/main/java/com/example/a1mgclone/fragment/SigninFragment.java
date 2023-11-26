package com.example.a1mgclone.fragment;

import android.os.Bundle;
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
import com.example.a1mgclone.fragment.*;
import com.example.a1mgclone.activity.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a1mgclone.R;
import com.example.a1mgclone.activity.AuthActivity;
import com.example.a1mgclone.activity.HomeActivity;
import com.example.a1mgclone.network.API;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SigninFragment extends Fragment {

    EditText editEmail, editPassword;
    Button buttonSignin;
    TextView textSignup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editEmail = view.findViewById(R.id.editEmail);
        editPassword = view.findViewById(R.id.editPassword);
        buttonSignin = view.findViewById(R.id.buttonSignin);
        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignin();
            }
        });

        textSignup = view.findViewById(R.id.textSignup);
        textSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthActivity activity = (AuthActivity) getActivity();
                activity.onSignup();
            }
        });
    }

    private void onSignin() {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        if (email.length() == 0) {
            Toast.makeText(getContext(), "Please enter email", Toast.LENGTH_SHORT).show();
        } else if (password.length() == 0) {
            Toast.makeText(getContext(), "Please enter password", Toast.LENGTH_SHORT).show();
        } else {
            // body
            HashMap<String, String> map = new HashMap<>();
            map.put("email", email);
            map.put("password", password);

            API.request("/user/signin")
                    .jsonBody(new JSONObject(map))
                    .call(new API.ResponseListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject data = response.getJSONObject("data");

                                String token = data.getString("token");
                                String name = data.getString("name");

                                // put the login status in preferences
                                SharedPreferences preferences =
                                        PreferenceManager.getDefaultSharedPreferences(getContext());
                                preferences
                                        .edit()
                                        .putBoolean("login_status", true)
                                        .putString("token", token)
                                        .putString("name", name)
                                        .commit();

                                // redirect to the home activity
                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                startActivity(intent);
                                getActivity().finish();

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }, new API.ErrorListener() {
                        @Override
                        public void onError(String error) {
                            Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }
}
