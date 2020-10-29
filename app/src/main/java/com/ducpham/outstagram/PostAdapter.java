package com.ducpham.outstagram;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.parse.ParseUser;

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
        Log.d("Adapter",String.valueOf(context));
        final Post post = postList.get(position);
        holder.name.setText(post.getUser().getUsername());
        Glide.with(context).asBitmap().load(post.getImage().getUrl()).into(holder.dataPicture);
        holder.caption.setText(post.getDescription());
        holder.time.setText(post.getTime().toString().substring(0,post.getTime().toString().length()-8));
        Glide.with(context).asBitmap().load(R.drawable.threedot).into(holder.threedot);
        Glide.with(context).asBitmap().load(R.mipmap.heart).into(holder.heart);
        Glide.with(context).asBitmap().load(R.mipmap.paper).into(holder.share);
        Glide.with(context).asBitmap().load(R.mipmap.bubble).into(holder.comment);
        Glide.with(context).asBitmap().load(R.mipmap.save).into(holder.save);

        if(post.getUser().getParseFile("userImage") != null){
            Glide.with(context).asBitmap().load(post.getUser().getParseFile("userImage").getUrl()).into(holder.imageView);
        }
        holder.dataPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailPost.class);
                intent.putExtra("des",post.getDescription());
                intent.putExtra("date",post.getTime().toString().substring(0,post.getTime().toString().length()-8));
                intent.putExtra("image", post.getImage().getUrl());
                context.startActivity(intent);
            }
        });
        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.heart.setColorFilter(Color.RED);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
    // Clean all elements of the recycler
    public void clear() {
        postList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        postList.addAll(list);
        notifyDataSetChanged();
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
