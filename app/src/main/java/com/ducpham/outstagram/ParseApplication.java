package com.ducpham.outstagram;

import android.app.Application;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Post.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("7agXqctBLtAF7SSOI67KpTrv7WtFvbpZTxGTxodH")
                .clientKey("elfONU7SfZIcjTpM9qO1K24saXPEZ50MNDElEcIe")
                .server("https://parseapi.back4app.com")
                .build()
        );




    }


}
