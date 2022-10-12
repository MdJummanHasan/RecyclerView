package com.recyclerview;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.recyclerview.Common.UploadProductModel;
import com.recyclerview.databinding.FragmentSalesYourBikeTwoBinding;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;


public class SalesYourBikeTwoFragment extends Fragment {


    FragmentSalesYourBikeTwoBinding binding;
    Bundle bundle;
    Uri uri;
    FirebaseAuth auth;
    FirebaseDatabase database;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ProgressDialog dialog;




    public SalesYourBikeTwoFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("image");
        storageReference = FirebaseStorage.getInstance().getReference("image");
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Uploading Image...");
        dialog.setCancelable(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSalesYourBikeTwoBinding.inflate(inflater, container, false);

        bundle = this.getArguments();

        String titleTwo = bundle.getString("title");
        binding.title.setText(titleTwo);
        String descriptionTwo = bundle.getString("description");
        binding.description.setText(descriptionTwo);

        String brandTwo = bundle.getString("brand");
        binding.brand.setText(brandTwo);

        String modelTwo = bundle.getString("model");
        binding.model.setText(modelTwo);

        String madeYearTwo = bundle.getString("madeYear");
        binding.madeYear.setText(madeYearTwo);

        String runKmTwo = bundle.getString("runKm");
        binding.runKm.setText(runKmTwo);

        String priceTwo = bundle.getString("price");
        binding.price.setText(priceTwo);


        binding.pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.with(SalesYourBikeTwoFragment.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(101);

            }
        });


        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String randomKey = UUID.randomUUID().toString();

                final StorageReference fileRef = storageReference.child(FirebaseAuth.getInstance().getUid())
                        .child(randomKey);
                fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                UploadProductModel model = new UploadProductModel();
                                model.setImage(uri.toString());
                                model.setPostAt(new Date().getTime());
                                model.setPostedBy(FirebaseAuth.getInstance().getUid());
                                model.setTitle(binding.title.getText().toString().trim());
                                model.setDescription(binding.description.getText().toString().trim());
                                model.setBrand(binding.brand.getText().toString().trim());
                                model.setModel(binding.model.getText().toString().trim());
                                model.setMadeYear(binding.madeYear.getText().toString().trim());
                                model.setRunKm(binding.runKm.getText().toString().trim());
                                model.setPrice(binding.price.getText().toString().trim());
                                model.setContactPersonName(binding.contactPersonName.getText().toString().trim());
                                model.setContactNumber(binding.contactNumber.getText().toString().trim());
                                model.setContactNumberTwo(binding.contactNumberTwo.getText().toString().trim());


                                String modelId = databaseReference.child(FirebaseAuth.getInstance().getUid())
                                        .child(randomKey)
                                        .getKey();

                                databaseReference.child(modelId).setValue(model);
                                Snackbar.make(getView(), "Upload Successfully" , Snackbar.LENGTH_SHORT).show();

                            }
                        });
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialog.dismiss();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        dialog.show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(getView(), "Upload Failed Retry" , Snackbar.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });



            }
        });


        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data !=null){
            uri = data.getData();
            Picasso.get().load(uri).into(binding.bikeImage);

        }
    }
}