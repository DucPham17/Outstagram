package com.ducpham.outstagram;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    List<Post> postList;
    Context context;

    public PostAdapter(List<Post> postList,Context context){
        this.postList = postList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.name.setText(post.getUser().getUsername());
        Glide.with(context).asBitmap().load(post.getImage().getUrl()).into(holder.dataPicture);
        holder.caption.setText(post.getDescription());
        holder.time.setText(post.getTime().toString().substring(0,post.getTime().toString().length()-8));
        Glide.with(context).asBitmap().load(R.drawable.threedot).into(holder.threedot);
        Glide.with(context).asBitmap().load(R.mipmap.heart).into(holder.heart);
        Glide.with(context).asBitmap().load(R.mipmap.paper).into(holder.share);
        Glide.with(context).asBitmap().load(R.mipmap.bubble).into(holder.comment);
        Glide.with(context).asBitmap().load(R.mipmap.save).into(holder.save);

        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.heart.setColorFilter(R.color.colorPink);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        ImageView dataPicture;
        ImageView heart;
        ImageView comment;
        ImageView share;
        ImageView threedot;
        ImageView save;
        TextView caption;
        TextView time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);
            dataPicture = itemView.findViewById(R.id.dataPicture);
            heart = itemView.findViewById(R.id.heart);
            comment = itemView.findViewById(R.id.comment);
            share = itemView.findViewById(R.id.share);
            threedot = itemView.findViewById(R.id.threedot);
            save = itemView.findViewById(R.id.save);
            caption = itemView.findViewById(R.id.caption);
            time = itemView.findViewById(R.id.time);
        }
    }
}
