package com.app.latifat.parstagram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_USER= "user";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage (ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser()  {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public static class Query extends ParseQuery<Post> {
         public Query() {
            super(Post.class);
        }

        public Query getTop() {
            setLimit(20);
            return this;
        }
        public Query withUser() {
             include("user");
             return this;
        }
    }
}


/* @ParseClassName("Comment")
public class Comment extends ParseObject {
    // ...

    // Associate each comment with a user
    public void setOwner(ParseUser user) {
        put("owner", user);
    }

    // Get the user for this comment
    public ParseUser getOwner()  {
        return getParseUser("owner");
    }

    // Associate each comment with a post
    public void setPost(Post post) {
        put("post", post);
    }

    // Get the post for this item
    public Post getPost()  {
        return (Post) getParseObject("post");
    }
}

    // Create the post
    Post post = new Post("Welcome Spring!");
    // Get the user
    ParseUser currentUser = ParseUser.getCurrentUser();
    // Create the comment
    Comment comment = new Comment("Get milk and eggs");
comment.setPost(post);
        comment.setOwner(currentUser);
        comment.saveInBackground(); */
