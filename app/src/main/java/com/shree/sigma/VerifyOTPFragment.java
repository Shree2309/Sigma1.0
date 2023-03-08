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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerifyOTPFragment extends Fragment {
    PhoneAuthCredential phoneAuthCredential;

    EditText otp;
    Button btnVerify;
    TextView tvNum, tvResend;
    String backendOtp;
    String userName,userPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verify_otp, container, false);

        otp = view.findViewById(R.id.etOtp);

        tvResend = view.findViewById(R.id.tvResend);
        btnVerify = view.findViewById(R.id.btnVerify);
        tvNum = view.findViewById(R.id.tvNum);

        tvResend = view.findViewById(R.id.tvResend);

        final ProgressBar progressBar = view.findViewById(R.id.otpVarProgress);

        if (getArguments() != null) {
            userName = getArguments().getString("name");
            userPhone = getArguments().getString("mobile");
            backendOtp = getArguments().getString("backendOtp");
        }


        tvNum.setText(String.format("+91-%s", userPhone));


        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!otp.getText().toString().trim().isEmpty()) {

                    String userOtp = otp.getText().toString();

                    if (backendOtp != null) {
                        progressBar.setVisibility(View.VISIBLE);
                        btnVerify.setVisibility(View.INVISIBLE);

                        phoneAuthCredential = PhoneAuthProvider.getCredential(backendOtp, userOtp);

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                btnVerify.setVisibility(View.VISIBLE);

                                if (task.isSuccessful()) {
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference userRef = database.getReference("Users");
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    if (user != null) {
                                        User addUser = new User(user.getUid(),userName,userPhone);
                                        userRef.child(user.getUid()).setValue(addUser);
                                    }

                                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                                    intent.putExtra("userPhone", userPhone);
                                    intent.putExtra("userName", userName);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    requireActivity().finish();
                                } else
                                    Toast.makeText(getActivity(), "Enter the correct OTP", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please enter all numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + userPhone
                        , 60
                        , TimeUnit.SECONDS
                        , requireActivity()
                        , new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                String code = phoneAuthCredential.getSmsCode();
                                if (code != null) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    verifyCode(code);
                                }
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newBackendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                backendOtp = newBackendOtp;
                                Toast.makeText(requireActivity(), "OTP re-sent successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        return view;
    }
    private void verifyCode(String codeByUser) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(backendOtp,codeByUser);
        signInUserByCredential(credential);
    }

    private void signInUserByCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(requireActivity(),HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        else
                            Toast.makeText(requireActivity(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}