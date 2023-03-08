package com.shree.sigma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shree.sigma.trades.TradesFragment;

public class AccountFragment extends Fragment {
    TextView tvUsername,tvVersion, tvLogout, tvChangeUsername,tvNotification,tvFunds,tvProfile,tvComplaint;
    private static final String TAG = "AccountFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        tvUsername = view.findViewById(R.id.tv_username);
        tvVersion = view.findViewById(R.id.tv_version);
        tvLogout = view.findViewById(R.id.tv_logout);
        tvChangeUsername = view.findViewById(R.id.tv_change_username);
        tvNotification = view.findViewById(R.id.tv_notification);
        tvFunds = view.findViewById(R.id.tv_funds);
        tvProfile = view.findViewById(R.id.tv_profile);
        tvComplaint = view.findViewById(R.id.tv_complaint);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(User.class).getName();
                tvUsername.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        tvProfile.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 View popupView = getLayoutInflater().inflate(R.layout.profile_popup, null);
                 Button btOk = popupView.findViewById(R.id.btOk);
                 PopupWindow popupWindow = new PopupWindow(getActivity());

                 popupWindow.setContentView(popupView);
                 popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                 popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

                 popupWindow.setFocusable(true);
                 popupWindow.setOutsideTouchable(true);

                 popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
                 popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                 btOk.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         popupWindow.dismiss();
                     }
                 });
             }
         });

        tvComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View popupView = getLayoutInflater().inflate(R.layout.complaint_popup, null);
                EditText etComplaint = popupView.findViewById(R.id.etComplaint);
                Button btSubmit = popupView.findViewById(R.id.btSubmit);
                PopupWindow popupWindow = new PopupWindow(getActivity());

                popupWindow.setContentView(popupView);
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);

                popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                btSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(etComplaint.getText().toString().isEmpty()){
                            Toast.makeText(requireActivity(), "Complaint cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            popupWindow.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Success")
                                    .setMessage("Username updated successfully")
                                    .setIcon(R.drawable.simple_trading_icon)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();

                        }
                    }
                });
            }
        });


        tvFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Funds")
                        .setMessage("Amount : 677384")
                        .setIcon(R.drawable.simple_trading_icon)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        tvNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Notification")
                        .setMessage("No notifications! ")
                        .setIcon(R.drawable.ic_baseline_notifications_24)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        tvChangeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View popupView = getLayoutInflater().inflate(R.layout.change_username_popup, null);
                EditText etUsername = popupView.findViewById(R.id.etUsername);
                Button btSubmit = popupView.findViewById(R.id.btSubmit);
                PopupWindow popupWindow = new PopupWindow(getActivity());

                popupWindow.setContentView(popupView);
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);

                popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                btSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(etUsername.getText().toString().isEmpty()){
                            Toast.makeText(requireActivity(), "Username cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            reference.child(user.getUid()).child("name").setValue(etUsername.getText().toString());
                            popupWindow.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Success")
                                    .setMessage("Username updated successfully")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();

                        }
                    }
                });

            }
        });

        tvVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Sigma")
                        .setMessage("Version \n1.0")
                        .setIcon(R.drawable.software)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(requireActivity())
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(getActivity(),MainActivity.class);
                                startActivity(intent);
                                requireActivity().finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        return view;
    }
}