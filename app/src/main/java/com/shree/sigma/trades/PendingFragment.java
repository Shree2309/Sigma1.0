package com.shree.sigma.trades;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shree.sigma.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PendingFragment extends Fragment {

    private RecyclerView rv_pending;
    private ActiveAdapter adapter;
    private List<ActiveModel> mList;
    private List<ActiveModel> oList;
    private ProgressBar progressBar;

    //FIREBASE

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userList = database.getReference("UserList");


    private static final String TAG = "PendingFragment";

    private static final String KEY_RECYCLER_VIEW_DATA = "recycler_view_data";

//    SharedPreferences sharedPreferences ;
//    Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending, container, false);

        rv_pending= view.findViewById(R.id.rv_pending);
        progressBar = view.findViewById(R.id.pending_progress);

        progressBar.setVisibility(View.VISIBLE);

        rv_pending.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mList = new ArrayList<>();

        userList.child(currentUser.getUid()).child("PendingList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    ActiveModel model = snapshot1.getValue(ActiveModel.class);
                    mList.add(model);
                }

                adapter = new ActiveAdapter(getActivity(),mList);
                rv_pending.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        oList = new ArrayList<>();
//
//        gson = new Gson();
//        if (isAdded()) {
//
//            sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
//
//            Log.d(TAG, "onCreateView: is added if condition is running");
//
//            String recyclerViewData = sharedPreferences.getString(KEY_RECYCLER_VIEW_DATA, null);
//            if (recyclerViewData != null) {
//
//                Type type = new TypeToken<List<ActiveModel>>() {}.getType();
//
//                List<ActiveModel>  recyclerViewDataList = gson.fromJson(recyclerViewData, type);
//
//                oList.addAll(recyclerViewDataList);
//                Log.d(TAG, "onCreateView: old data is added to oList in if condition");
//
//                adapter = new ActiveAdapter(getActivity(),oList);
//                Log.d(TAG, "onCreateView: displaying oList data in recycler view if wala");
//                rv_pending.setAdapter(adapter);
//            }
//
//
//
//        } else {
//            Toast.makeText(requireActivity(), "Fragment is not attached to activity", Toast.LENGTH_SHORT).show();
//        }
//
//
//        PendingSharedViewModel pendingSharedViewModel = new ViewModelProvider(requireActivity()).get(PendingSharedViewModel.class);
//
//        pendingSharedViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<ActiveModel>>() {
//            @Override
//            public void onChanged(List<ActiveModel> activeModels) {
//                mList = activeModels;
//
//                oList.addAll(mList);
//                Log.d(TAG, "onCreateView: current data is added to oList in onChanged "+oList.size());
//
//                adapter = new ActiveAdapter(getActivity(),oList);
//                rv_pending.setAdapter(adapter);
//                Log.d(TAG, "onChanged: displaying data in onChanged method of oList");
//            }
//        });
//
//        saveList(oList);

        return view;
    }
//    public  void  saveList(List<ActiveModel> list){
//        Log.d(TAG, "saveList: active fragment save list called ");
//        String recyclerViewDataJson = gson.toJson(list);
//        sharedPreferences.edit().putString(KEY_RECYCLER_VIEW_DATA, recyclerViewDataJson).apply();
//
//    }



}