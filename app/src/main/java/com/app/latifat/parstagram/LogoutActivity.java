package com.app.latifat.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class LogoutActivity extends AppCompatActivity {

    private Button logoutBtn;
    private Button feed_Button;
    private Button pic_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        logoutBtn = (Button) findViewById(R.id.logout_btn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();

                final Intent intent = new Intent(LogoutActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        feed_Button = (Button) findViewById(R.id.feedbtn);
        pic_button = (Button) findViewById(R.id.picbtn);



        feed_Button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                final Intent intent = new Intent(LogoutActivity.this,FeedActivity.class);
                startActivity(intent);
            }
        });

        pic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(LogoutActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}
