package com.app.latifat.parstagram;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.latifat.parstagram.model.Post;

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

        GlideApp.with(context)
                .load(post.getImage().getUrl())
                .fitCenter()
                .into(holder.ivProfileImage);
    }

    @Override
    public int getItemCount() { return myposts.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivProfileImage;
        public TextView tvBody;

        public ViewHolder(View itemView) {
            super(itemView);

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
        }
    }
}
