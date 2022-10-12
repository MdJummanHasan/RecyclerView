package com.recyclerview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.recyclerview.databinding.FragmentSalesYourBikeOneBinding;



public class SalesYourBikeOneFragment extends Fragment {


    FragmentSalesYourBikeOneBinding binding;

    Bundle bundle;



    public SalesYourBikeOneFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSalesYourBikeOneBinding.inflate(inflater, container, false);



        binding.next.setOnClickListener(view -> {

            if (TextUtils.isEmpty(binding.title.getText().toString())){
                Toast.makeText(getContext(), "Please enter title", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (TextUtils.isEmpty(binding.description.getText().toString())){
                Toast.makeText(getContext(), "Please enter description", Toast.LENGTH_SHORT).show();
                return;
            }

            else if (TextUtils.isEmpty(binding.brand.getText().toString())){
                Toast.makeText(getContext(), "Please enter brand", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (TextUtils.isEmpty(binding.model.getText().toString())){
                Toast.makeText(getContext(), "Please enter model", Toast.LENGTH_SHORT).show();
                return;
            }

            else if (TextUtils.isEmpty(binding.madeYear.getText().toString())){
                Toast.makeText(getContext(), "Please enter made Year", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (TextUtils.isEmpty(binding.runKm.getText().toString())){
                Toast.makeText(getContext(), "Please enter run Km", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (TextUtils.isEmpty(binding.price.getText().toString())){
                Toast.makeText(getContext(), "Please enter Additional price", Toast.LENGTH_SHORT).show();
                return;
            }
            else {

                getBundle();





            }


            Fragment fragment = new SalesYourBikeTwoFragment();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction();

            transaction.replace(R.id.container, fragment)
                    .addToBackStack("name")
                    .setReorderingAllowed(true)
                    .commit();



        });


        return binding.getRoot();
    }

    public Bundle getBundle() {

        String titleOne = binding.title.getText().toString();
        String descriptionOne = binding.description.getText().toString();
        String brandOne = binding.brand.getText().toString();
        String modelOne = binding.model.getText().toString();
        String madeYearOne = binding.madeYear.getText().toString();
        String runKmOne = binding.runKm.getText().toString();
        String priceOne = binding.price.getText().toString();

        bundle = new Bundle();

        bundle.putString("title",titleOne );
        bundle.putString("description",descriptionOne );
        bundle.putString("brand",brandOne );
        bundle.putString("model",modelOne );
        bundle.putString("madeYear",madeYearOne );
        bundle.putString("runKm",runKmOne );
        bundle.putString("price",priceOne );




        return bundle;
    }
}