package com.shree.sigma.trades;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shree.sigma.R;
import com.shree.sigma.watchlist.MCXFragment;

import java.util.ArrayList;
import java.util.List;

public class ActiveFragment extends Fragment {
    private RecyclerView rv_active;
    private ActiveAdapter adapter;
    private List<ActiveModel> mList;
    private ProgressBar progressBar;

    private static final String TAG = "ActiveFragment";

    //FIREBASE
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userList = database.getReference("UserList");

//    private static final String KEY_RECYCLER_VIEW_DATA = "recycler_view_data";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_active, container, false);

        MCXFragment mcxFragment = new MCXFragment();

         rv_active= view.findViewById(R.id.rv_active);
         progressBar= view.findViewById(R.id.active_progress);

         progressBar.setVisibility(View.VISIBLE);

         rv_active.setLayoutManager(new LinearLayoutManager(requireActivity()));
         mList = new ArrayList<>();

        userList.child(currentUser.getUid()).child("ActiveList").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            ActiveModel model = snapshot1.getValue(ActiveModel.class);
                            mList.add(model);
                        }

                        adapter = new ActiveAdapter(getActivity(),mList);
                        rv_active.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        return view;
    }

}