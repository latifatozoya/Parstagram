package com.app.latifat.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.app.latifat.parstagram.model.LogoutActivity;

public class FeedActivity extends AppCompatActivity {

    private Button postbtn;
    private Button Profilebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        Profilebtn = (Button) findViewById(R.id.profilebtn);
        postbtn = (Button) findViewById(R.id.picbtn);

        postbtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                final Intent intent = new Intent(FeedActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        Profilebtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                final Intent intent = new Intent(FeedActivity.this, LogoutActivity.class);
                startActivity(intent);
            }
        });

    }
}
