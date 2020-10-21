package com.ducpham.outstagram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.util.Timer;

import java.io.File;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    public final String APP_TAG = "MainActivity";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    EditText description;
    Button takePicture;
    Button submit;
    ImageView imageView;
    Toolbar toolbar;
    File photoFile;
    ProgressBar pbLoading;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timer = new Timer();
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        takePicture = findViewById(R.id.takePicture);
        submit = findViewById(R.id.submit);
        imageView = findViewById(R.id.pictureImage);
        toolbar = findViewById(R.id.toolbar);
        pbLoading = findViewById(R.id.pbLoading);
        setSupportActionBar(toolbar);

        //queryPost();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descript = description.getText().toString();
                if(descript.isEmpty()){
                    Toast.makeText(MainActivity.this,"Description cannot be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(photoFile == null || imageView.getDrawable() == null){
                    Toast.makeText(MainActivity.this,"Photo cannot be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                pbLoading.setVisibility(ProgressBar.VISIBLE);
                ParseUser curUser = ParseUser.getCurrentUser();
                savePost(curUser,descript,photoFile);
                description.setText("");
                imageView.setImageResource(0);

            }
        });

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.camera);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(MainActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                imageView.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    public void savePost(ParseUser user, String descript, File imageFile) {

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for(int i = 0; i < 10000;i++){
                    System.out.println("abc");
                }
                pbLoading.setVisibility(ProgressBar.INVISIBLE);
            }
        }, 2*1000);

        Post post = new Post();
        post.setDescription(descript);
        post.setImage(new ParseFile(imageFile));
        post.setUser(user);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.d("Main class","loading error",e);
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.Logout){
            ParseUser.logOut();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    public void queryPost(){
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e("Main class","Problem happen with loading",e);
                    return;
                }
                for(Post c : posts){
                    Log.i("Main class",c.getDescription());
                }
            }
        });
    }
}