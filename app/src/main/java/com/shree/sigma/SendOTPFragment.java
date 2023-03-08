package com.shree.sigma;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.shree.sigma.watchlist.WatchlistFragment;

import java.util.concurrent.TimeUnit;

public class SendOTPFragment extends Fragment {
    EditText edtPhone, edtName;
    Button btnOtp;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_otp, container, false);

        edtPhone = view.findViewById(R.id.edtPhone);
        edtName = view.findViewById(R.id.edtName);
        btnOtp = view.findViewById(R.id.btnOtp);
        progressBar = view.findViewById(R.id.otpProgress);



        btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtPhone.getText().toString().trim().isEmpty()){
                    if((edtPhone.getText().toString().trim().length())==10){

                        progressBar.setVisibility(View.VISIBLE);
                        btnOtp.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + edtPhone.getText().toString()
                                , 60
                                , TimeUnit.SECONDS
                                , requireActivity()
                                , new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                        progressBar.setVisibility(View.GONE);
                                        btnOtp.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        btnOtp.setVisibility(View.VISIBLE);
                                        Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        btnOtp.setVisibility(View.VISIBLE);

                                        Bundle bundle = new Bundle();
                                        VerifyOTPFragment fragment = new VerifyOTPFragment();
                                        bundle.putString("mobile",edtPhone.getText().toString());
                                        bundle.putString("name",edtName.getText().toString());
                                        bundle.putString("backendOtp",backendOtp);

                                        fragment.setArguments(bundle);
                                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.mainFrame, fragment,null)
                                                .commit();
                                    }
                                });


                    }

                    else {
                        Toast.makeText(requireActivity(), "Please enter the correct number", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(requireActivity(), "Mobile Number cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }

        });

        return view;
    }
}