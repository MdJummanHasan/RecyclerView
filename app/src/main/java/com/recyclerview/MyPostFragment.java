package com.recyclerview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.recyclerview.Adapter.MyPostAdapter;
import com.recyclerview.Adapter.ProductRecyclerAdapter;
import com.recyclerview.Common.UploadProductModel;
import com.recyclerview.databinding.FragmentMyPostBinding;

import java.util.ArrayList;
import java.util.Objects;


public class MyPostFragment extends Fragment {

    FragmentMyPostBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ArrayList<MyPostAdapter> recycleList;
    MyPostAdapter myPostAdapter;
    private String currentUserId;



    public MyPostFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        myPostAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        myPostAdapter.stopListening();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUserId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMyPostBinding.inflate(inflater, container, false);

        recycleList = new ArrayList<>();


        FirebaseRecyclerOptions<UploadProductModel> options =
                new FirebaseRecyclerOptions.Builder<UploadProductModel>()
                        .setQuery(database.getReference().child("image").orderByChild("postedBy").equalTo(currentUserId), UploadProductModel.class)
                        .build();

        binding.postRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        myPostAdapter = new MyPostAdapter(options);
        binding.postRecycler.setAdapter(myPostAdapter);




        return binding.getRoot();
    }
}