package com.app.latifat.parstagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.latifat.parstagram.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    PostAdapter postAdapter;
    ArrayList<Post> posts;
    private static final String imagePath = "photo.jpg";
    private EditText captionInput;
    private Button postBtn;
    private Button feed_Button;
    private Button profile_Button;
    private Button pic_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        captionInput = (EditText) findViewById(R.id.caption_et);
        postBtn = (Button) findViewById(R.id.post_btn);

        setSupportActionBar(toolbar);

        View view = findViewById(R.id.home);
        onLaunchCamera(view);

        feed_Button = (Button) findViewById(R.id.feedbtn);
        pic_button = (Button) findViewById(R.id.picbtn);
        profile_Button = (Button) findViewById(R.id.profilebtn);
        final ProgressBar pb = (ProgressBar) findViewById(R.id.pbLoading);

        // run a background job and once complete
        pb.setVisibility(ProgressBar.INVISIBLE);

        posts = new ArrayList<>();
        postAdapter = new PostAdapter(posts);

        feed_Button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                final Intent intent = new Intent(HomeActivity.this,FeedActivity.class);
                startActivity(intent);
            }
        });

        profile_Button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                final Intent intent = new Intent(HomeActivity.this,LogoutActivity.class);
                startActivity(intent);
            }
        });

        pic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(HomeActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(ProgressBar.VISIBLE);
               final String caption = captionInput.getText().toString();
               final ParseUser user = ParseUser.getCurrentUser();

               final File file =  getPhotoFileUri(photoFileName);
               final ParseFile parseFile = new ParseFile(file);
               parseFile.saveInBackground(new SaveCallback() {
                   @Override
                   public void done(ParseException e) {
                       createPost(caption, parseFile, user);
                       pb.setVisibility(ProgressBar.INVISIBLE);
                   }
               });
            }
        });
    }

    private void createPost(String caption, ParseFile imageFile, ParseUser user) {
        final Post newPost = new Post();
        newPost.setDescription(caption);
        newPost.setUser(user);
        newPost.setImage(imageFile);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    posts.add(newPost);
                    postAdapter.notifyItemInserted(0);
                    final Intent intent = new Intent(HomeActivity.this,FeedActivity.class);
                    startActivity(intent);
                    Log.d("HomeActivity","Create post Success!");
                } else {
                    e.printStackTrace();
                }
            }
        });

    }

    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo";
    File photoFile;

    public void onLaunchCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(photoFileName);

        Uri fileProvider = FileProvider.getUriForFile(HomeActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    public File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    // PICK_PHOTO_CODE is a constant integer

    public final static int PICK_PHOTO_CODE = 1046;

    // Trigger gallery selection for a photo
    public void onPickPhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ImageView ivPreview = (ImageView) findViewById(R.id.ivPreview);
                ivPreview.setImageBitmap(takenImage);

                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                takenImage.compress(Bitmap.CompressFormat.PNG, 100, outStream);

                ParseFile parseImage = new ParseFile(outStream.toByteArray());

                Post newPost = new Post();
                newPost.setImage(parseImage);

                String descriptionInput = captionInput.getText().toString();
                newPost.setDescription(descriptionInput);

                ParseUser author = ParseUser.getCurrentUser();
                newPost.setUser(author);
            } else {
                Toast.makeText(this, "Picture was not taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}