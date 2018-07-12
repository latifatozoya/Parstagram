package com.app.latifat.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.latifat.parstagram.model.Post;

public class PostDetailsActivity extends AppCompatActivity {

    Post post;
    Button feed_button, pic_button,profile_button;   //Button feedbtn, picbtn,profilebtn;
    TextView tvUserName;
    TextView tvBody;
    ImageView ivProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        post=getIntent().getParcelableExtra("Post");
        tvBody = (TextView) findViewById(R.id.tvBody);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        feed_button = (Button) findViewById(R.id.feedbtn);
        pic_button = (Button) findViewById(R.id.picbtn);
        profile_button = (Button) findViewById(R.id.profilebtn);
        Log.d("PostDetailsActivity", String.format("Showing details for '%s'", post.getClass()));

        feed_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                final Intent intent = new Intent(PostDetailsActivity.this,FeedActivity.class);
                startActivity(intent);
            }
        });

        profile_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                final Intent intent = new Intent(PostDetailsActivity.this,LogoutActivity.class);
                startActivity(intent);
            }
        });

        pic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(PostDetailsActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });


        tvBody.setText(post.getDescription());
        tvUserName.setText(post.getUser().getUsername());

        GlideApp.with(this)
                .load(post.getImage().getUrl())
                .into(ivProfileImage);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        Log.d("Tweet", "entered OnActivity");
        int REQUEST_CODE = 10;
        if (resultCode == RESULT_OK && (requestCode == REQUEST_CODE || requestCode == 10)) {
            // Extract name value from result extras
            Intent intent = new Intent(PostDetailsActivity.this, FeedActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }
}
