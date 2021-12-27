package com.comingmsgs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.comingmsgs.databinding.ActivitySignInBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignInActivity extends AppCompatActivity {
    ActivitySignInBinding binding;
    EditText enterNumber;
    EditText countryCode;
    ProgressBar progressBar;
    Button Continue;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();


        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null)
        {
            Intent intent = new Intent(SignInActivity.this , MainActivity.class);
            startActivity(intent);
            finish();

        }


        enterNumber = findViewById(R.id.enterNumber);
        countryCode = findViewById(R.id.CountryCode);
        progressBar = findViewById(R.id.progress_bar_sendingOTP);
        Continue = findViewById(R.id.Continue);



        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!enterNumber.getText().toString().trim().isEmpty()) {
                    if ((enterNumber.getText().toString().trim().length() == 10)) {

                        progressBar.setVisibility(View.VISIBLE);
                        Continue.setVisibility(View.INVISIBLE);
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(

                                countryCode.getText().toString()+ enterNumber.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                SignInActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                        progressBar.setVisibility(View.GONE);
                                        Continue.setVisibility(View.VISIBLE);

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {

                                        progressBar.setVisibility(View.GONE);
                                        Continue.setVisibility(View.VISIBLE);
                                        Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backEndOtp, @NonNull PhoneAuthProvider
                                            .ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        Continue.setVisibility(View.VISIBLE);

                                        Intent intent = new Intent(SignInActivity.this, VerificationActivity.class);
                                        intent.putExtra("mobile", countryCode.getText().toString() + enterNumber.getText().toString());
                                        intent.putExtra("backEndOtp",backEndOtp);
                                        startActivity(intent);
                                    }
                                }
                        );
                    } else {
                        Toast.makeText(SignInActivity.this, "Please enter correct phone number", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SignInActivity.this, "Please enter phone number", Toast.LENGTH_LONG).show();
                }


            }
        });
    }
}