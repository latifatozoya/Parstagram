package com.app.latifat.parstagram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.app.latifat.parstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private Button postbtn;
    private Button Profilebtn;
    RecyclerView rvposts;
    PostAdapter postAdapter;
    ArrayList<Post> posts;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        Profilebtn = (Button) findViewById(R.id.profilebtn);
        postbtn = (Button) findViewById(R.id.picbtn);
        rvposts = (RecyclerView) findViewById(R.id.rvPost);

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

        posts = new ArrayList<>();
        postAdapter = new PostAdapter(posts);
        rvposts.setLayoutManager(new LinearLayoutManager(this));
        rvposts.setAdapter(postAdapter);

        populateTimeline();

    }

    public void populateTimeline() {

            final Post.Query postQuery = new Post.Query();
            postQuery.getTop().withUser();

            postQuery.findInBackground(new FindCallback<Post>() {
                @Override
                public void done(List<Post> objects, ParseException e) {
                    if (e == null) {
                            posts.clear();
                            posts.addAll(objects);
                            postAdapter.notifyDataSetChanged();
                    } else {
                        e.printStackTrace();
                    }
                }
            });
    }
}
