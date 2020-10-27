package com.ducpham.outstagram;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment {
    RecyclerView recyclerView;
    List<Post> postList;
    PostAdapter postAdapter;
    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    private EndlessRecyclerViewScrollListener scrollListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                queryPost();
            }
        };
        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
        postAdapter = new PostAdapter(postList, getContext());
        recyclerView.setAdapter(postAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        queryPost();

    }

    public void queryPost(){
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.addDescendingOrder(Post.KEY_CREATED);
        query.setLimit(20);
//        if(postList.size() > 0){
//            query.whereLessThanOrEqualTo(Post.KEY_CREATED,postList.get(postList.size()-1).getTime());
//        }
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e("Home","Problem happen with loading",e);
                    return;
                }
                Log.d("Home","Everything ok");
                postList.addAll(posts);
                Log.d("Home",String.valueOf(postList.size()));
                postAdapter.notifyDataSetChanged();
            }
        });
    }

}