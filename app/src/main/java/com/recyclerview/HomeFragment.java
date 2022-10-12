package com.recyclerview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.recyclerview.Adapter.ProductRecyclerAdapter;
import com.recyclerview.Common.UploadProductModel;
import com.recyclerview.databinding.FragmentHomeBinding;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ArrayList<UploadProductModel> recycleList;
    ProductRecyclerAdapter adapter;






    public HomeFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        recycleList = new ArrayList<>();

        FirebaseRecyclerOptions<UploadProductModel> options =
                new FirebaseRecyclerOptions.Builder<UploadProductModel>()
                        .setQuery(database.getReference().child("image"), UploadProductModel.class)
                        .build();

        binding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductRecyclerAdapter(options);
        binding.recycler.setAdapter(adapter);



//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        binding.recycler.setLayoutManager(linearLayoutManager);
//        binding.recycler.addItemDecoration(new DividerItemDecoration(binding.recycler.getContext(), DividerItemDecoration.VERTICAL));
//        binding.recycler.setNestedScrollingEnabled(true);
//        adapter = new ProductRecyclerAdapter(options);
//        adapter.startListening();



        return binding.getRoot();
    }



}