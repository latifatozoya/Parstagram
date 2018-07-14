package com.app.latifat.parstagram;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.latifat.parstagram.model.Post;
import com.parse.ParseUser;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    public List<Post> myposts;
    Context context;

    public PostAdapter(List<Post> posts) {
        this.myposts = posts;
    }

    public void clear() {
        myposts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> list) {
        myposts.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        Post post = myposts.get(i);
        holder.tvBody.setText(post.getDescription());
        holder.tvDate.setText(PostRelativeDate.getRelativeTimeAgo(post.getCreatedAt().toString()));
        holder.tvUsername.setText(post.getUser().getUsername());

        GlideApp.with(context)
                .load(post.getImage().getUrl())
                .into(holder.ivProfileImage);

        ParseUser user = post.getUser();

        if(user.getParseFile("profilepic") != null) {
            String avi = user.getParseFile("profilepic").getUrl();

            GlideApp.with(context)
                    .load(avi)
                    .into(holder.uploadImage);
        }
        else{
            GlideApp.with(context)
                    .load("https://transhumane-partei.de/wp-content/uploads/2016/04/blank-profile-picture-973461_960_720.png")
                    .into(holder.uploadImage);
        }

    }

    @Override
    public int getItemCount() { return myposts.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ivProfileImage;
        public TextView tvBody;
        public TextView tvUsername;
        public TextView tvDate;
        public ImageView uploadImage;
        public Button favorite;

        public ViewHolder(View itemView) {
            super(itemView);

            uploadImage = (ImageView) itemView.findViewById(R.id.uploadImage);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            favorite = (Button) itemView.findViewById(R.id.bt1);

            itemView.setOnClickListener(this);

            favorite.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (view.getId() == R.id.bt1 ) {
                        view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ufi_heart_active));
                    } else {
                        view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ufi_heart));
                    }
                }
            });
        }

        public void onClick(final View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                Post post = myposts.get(position);
                Intent intent = new Intent(context, PostDetailsActivity.class);
                intent.putExtra("Post", post);
                context.startActivity(intent);
            }
        }
    }
}
