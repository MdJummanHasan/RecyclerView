package com.recyclerview.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.recyclerview.Common.UploadProductModel;
import com.recyclerview.R;
import com.recyclerview.SalesYourBikeTwoFragment;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyPostAdapter extends FirebaseRecyclerAdapter<UploadProductModel, MyPostAdapter.myViewHolder> {

    public MyPostAdapter(@NonNull FirebaseRecyclerOptions<UploadProductModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull UploadProductModel model) {

        Picasso.get().load(model.getImage()).placeholder(R.drawable.home_24).into(holder.imageViewPost);
        holder.titlePost.setText(model.getTitle());
        holder.descriptionPost.setText(model.getDescription());
        holder.pricePost.setText(model.getPrice());

        holder.optionMenuPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(view.getContext(), holder.optionMenuPost);
                popupMenu.inflate(R.menu.my_post_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.edit:
                                edit();
                                
                                break;
                            case R.id.delete:
                                delete();
                                break;
                            default:break;

                        }
                        return false;
                    }

                   
                });
                popupMenu.show();

            }

            private void delete() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.descriptionPost.getContext());
                    builder.setTitle("Do You Really want to Delete?")
                            .setMessage("Once you delete, it permanently deleted from Our Database")
                            .setCancelable(false)
                            .setNegativeButton("CANCEL", (dialogInterface, i) -> {
                                dialogInterface.dismiss();

                            })
                            .setPositiveButton("DELETE", (dialogInterface, i) -> {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference().child("image").child(Objects.requireNonNull(getRef(position).getKey()));
                                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("image/")
                                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child(Objects.requireNonNull(getRef(position).getKey()));
                                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        reference.removeValue();
                                        notifyDataSetChanged();

                                    }
                                });


                            });
                    AlertDialog dialog = builder.create();
                    dialog.setOnShowListener(dialogInterface -> {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                                .setTextColor(holder.descriptionPost.getContext().getResources().getColor(android.R.color.holo_red_dark));

                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                                .setTextColor(holder.descriptionPost.getContext().getResources().getColor(android.R.color.holo_blue_dark));
                    });
                    dialog.show();


            }

            private void edit() {
                DialogPlus dialog = DialogPlus.newDialog(holder.descriptionPost.getContext())
                        .setContentHolder(new ViewHolder(R.layout.edit_layout))
                        .create();

                View EditView = dialog.getHolderView();

                EditText titleEdit = EditView.findViewById(R.id.titleEdit);
                titleEdit.setText(model.getTitle());

                EditText descriptionEdit = EditView.findViewById(R.id.descriptionEdit);
                descriptionEdit.setText(model.getDescription());

                EditText brandEdit = EditView.findViewById(R.id.brandEdit);
                brandEdit.setText(model.getBrand());

                EditText modelEdit = EditView.findViewById(R.id.modelEdit);
                modelEdit.setText(model.getModel());

                EditText madeYearEdit = EditView.findViewById(R.id.madeYearEdit);
                madeYearEdit.setText(model.getMadeYear());

                EditText runKmEdit = EditView.findViewById(R.id.runKmEdit);
                runKmEdit.setText(model.getRunKm());

//                EditText AdditionalInformationEdit = EditView.findViewById(R.id.AdditionalInformationEdit);
//                AdditionalInformationEdit.setText(model.getAd);

                EditText priceEdit = EditView.findViewById(R.id.priceEdit);
                priceEdit.setText(model.getPrice());

                EditText contactPersonNameEdit = EditView.findViewById(R.id.contactPersonNameEdit);
                contactPersonNameEdit.setText(model.getContactPersonName());

                EditText contactNumberEdit = EditView.findViewById(R.id.contactNumberEdit);
                contactNumberEdit.setText(model.getContactNumber());

                EditText contactNumberTwoEdit = EditView.findViewById(R.id.contactNumberTwoEdit);
                contactNumberTwoEdit.setText(model.getContactNumberTwo());

                Button submitEdit = EditView.findViewById(R.id.submitEdit);
                submitEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Map<String, Object> map = new HashMap<>();
                        map.put("title", titleEdit.getText().toString());
                        map.put("description", descriptionEdit.getText().toString());
                        map.put("brand", brandEdit.getText().toString());
                        map.put("model", modelEdit.getText().toString());
                        map.put("madeYear", madeYearEdit.getText().toString());
                        map.put("runKm", runKmEdit.getText().toString());
                        map.put("price", priceEdit.getText().toString());
                        map.put("contactPersonName", contactPersonNameEdit.getText().toString());
                        map.put("contactNumber", contactNumberEdit.getText().toString());
                        map.put("contactNumberTwo", contactNumberTwoEdit.getText().toString());



                        FirebaseDatabase.getInstance().getReference().child("image")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(EditView.getContext(), "Update Successfully", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(EditView.getContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();

                                    }
                                });



                    }
                });


                dialog.show();
                
                
            }
        });
        
        


    }

   

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_post_recycler, parent, false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView titlePost, pricePost, descriptionPost;
        ImageView imageViewPost, optionMenuPost;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            titlePost = itemView.findViewById(R.id.titlePost);
            descriptionPost = itemView.findViewById(R.id.textDescriptionPost);
            pricePost = itemView.findViewById(R.id.pricePost);
            imageViewPost = itemView.findViewById(R.id.ImagePost);
            optionMenuPost = itemView.findViewById(R.id.optionMenuPost);

        }
    }


}
