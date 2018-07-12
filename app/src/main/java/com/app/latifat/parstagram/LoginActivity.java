package com.app.latifat.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;
    private Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseUser user = ParseUser.getCurrentUser();
        if (user == null) {
           // super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            usernameInput = findViewById(R.id.username_et);
            passwordInput = findViewById(R.id.password_et);
            loginBtn = findViewById(R.id.login_btn);
            signupBtn = findViewById(R.id.signup_btn);

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String username = usernameInput.getText().toString();
                    final String password = passwordInput.getText().toString();

                    login(username, password);
                }
            });

            signupBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signup();
                }

            });
        }
        else {
            Intent intent = new Intent(LoginActivity.this,FeedActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {

            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null) {
                    Log.d("LoginActivity", "Login Successful");
                    Intent intent = new Intent(LoginActivity.this,FeedActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("LoginActivity", "Login Failure");
                    e.printStackTrace();
                }
            }
        });
    }

    private void signup() {
        ParseUser user = new ParseUser();
        user.setUsername(usernameInput.getText().toString());
        user.setPassword(passwordInput.getText().toString());

        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("SignupActivity", "Sign up Successful");
                    final Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("SignupActivity", "Sign up Failure");
                    e.printStackTrace();
                }
            }
        });

    }
}