package com.app.latifat.parstagram;

import android.app.Application;

import com.app.latifat.parstagram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(TodoItem.class);

        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("latifatozoya")
                .clientKey("music")
                .server("http://latifatozoya-fbu-instagram.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);

    }
}