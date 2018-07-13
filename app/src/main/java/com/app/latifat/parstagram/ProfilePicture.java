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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.latifat.parstagram.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class ProfilePicture extends AppCompatActivity {

    PostAdapter postAdapter;
    ArrayList<Post> posts;
    private Button save_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);

        save_btn = (Button) findViewById(R.id.save_btn);
        posts = new ArrayList<>();
        postAdapter = new PostAdapter(posts);

        View view = findViewById(R.id.upload);
        onLaunchCamera(view);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ParseUser user = ParseUser.getCurrentUser();
                final File file =  getPhotoFileUri(photoFileName);
                final ParseFile parseFile = new ParseFile(file);
                final Intent intent = new Intent(ProfilePicture.this,FeedActivity.class);
                startActivity(intent);
                parseFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        uploadpic(parseFile, user);
                    }
                });
            }
        });

    }

    private static final String imagePath = "photo.jpg";
    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo";
    File photoFile;

    public void onLaunchCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(photoFileName);

        Uri fileProvider = FileProvider.getUriForFile(ProfilePicture.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    public File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                ImageView ivPreview = (ImageView) findViewById(R.id.uploadImage);
                ivPreview.setImageBitmap(takenImage);

                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                takenImage.compress(Bitmap.CompressFormat.PNG, 100, outStream);

                ParseFile parseImage = new ParseFile(outStream.toByteArray());
                Post newUpload = new Post();
                newUpload.setImage(parseImage);

            } else {
                Toast.makeText(this, "Picture was not taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadpic(ParseFile imageFile, ParseUser user) {
        user.put("profilepic", imageFile);
        user.saveInBackground();

    }
}
