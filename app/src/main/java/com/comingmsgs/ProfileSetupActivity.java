package com.comingmsgs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.comingmsgs.databinding.ActivityProfileSetupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProfileSetupActivity extends AppCompatActivity {
    ActivityProfileSetupBinding binding;

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri selectedImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileSetupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        binding.profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");  //it open activity for image selction and to get its refernce
                startActivityForResult(intent,45);
            }
        });
        binding.setupProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.enterName.getText().toString();

                if (name.isEmpty()) {
                    binding.enterName.setError("Please enter your name");
                    return;
                }

                if (selectedImage != null)
                {
                    StorageReference reference = storage.getReference().child("Profiles").child(auth.getUid());
                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri)
                                    //here uri in small letters is url for profile image
                                    {
                                        String imageUrl = uri.toString();
                                        String uid = auth.getUid();
                                        String phone  = auth.getCurrentUser().getPhoneNumber();
                                        String name = binding.enterName.getText().toString();

                                        User user =  new User(uid ,name,phone,imageUrl);
                                        // here we get all data that user entered while his login

                                        // now time to put it in firebase

                                        database.getReference()
                                                .child("Users")
                                                .setValue(user)//if these items get uploaded succesfuly then intent will  run
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Intent  intent = new Intent(ProfileSetupActivity.this , MainActivity.class);
                                                        startActivity(intent);
                                                        finishAffinity();


                                                    }
                                                });



                                    }
                                });
                            }
                        }
                    });
                }


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // here activity  open where u can select a image and its refernce is saved
        if (data != null){
            if (data.getData() !=null){
                binding.profilepic.setImageURI(data.getData());
                selectedImage = data.getData();
            }

        }
    }
}