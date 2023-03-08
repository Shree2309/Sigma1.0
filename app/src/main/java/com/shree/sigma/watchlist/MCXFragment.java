package com.shree.sigma.watchlist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shree.sigma.R;
import com.shree.sigma.trades.ActiveModel;
import com.shree.sigma.trades.TradesFragment;

import java.util.Date;
import java.util.List;


public class MCXFragment extends Fragment {
    private RecyclerView rv_mcx;
    private MCXAdapter mcxadapter;
    private List<MCXModel> mList;
    private MCXViewModel mcxViewModel;
    private ProgressBar progressBar;

    private static final String TAG = "MCXFragment";
    private MCXAdapter.RecyclerViewClickListener clickListener;

    //FIREBASE
    private MutableLiveData<List<ActiveModel>> data = new MutableLiveData<>();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    //POPUP_WINDOW
    private View popupView;
    private View mView;
    private PopupWindow popupWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_m_c_x, container, false);

        mView = view;
        mcxViewModel = new ViewModelProvider(requireActivity()).get(MCXViewModel.class);

        rv_mcx = view.findViewById(R.id.rv_mcx);
        progressBar = view.findViewById(R.id.mcx_progress);

        progressBar.setVisibility(View.VISIBLE);

        rv_mcx.setLayoutManager(new LinearLayoutManager(requireActivity()));
        
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clickListener = new MCXAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(int position) {
                Log.d(TAG, "onClick: clicked item on position "+position);
                showPopupWindow(position);
            }
        };

        mcxViewModel.getList().observe(requireActivity(), new Observer<List<MCXModel>>() {
            @Override
            public void onChanged(List<MCXModel> mcxModels) {
                if (mcxModels != null) {
                    mList = mcxModels;
                    mcxadapter = new MCXAdapter(requireActivity(), mList, mcxViewModel,clickListener);
                    Log.d(TAG, "onChanged: first onChanged");

                    rv_mcx.setAdapter(mcxadapter);
                    progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void mSellClick(int position, String lots) {

        if (!lots.isEmpty()) {

            Log.d(TAG, "mSellClick: oooooooooooooookkkkkkkkkkkkkkk dismiss " + popupWindow);
            popupWindow.dismiss();
            addMarketSell(position, lots);

        } else {
            Toast.makeText(getContext(), "Please enter Lots quantity", Toast.LENGTH_SHORT).show();
        }

    }

    public void mBuyClick(int position, String lots) {

        if (!lots.isEmpty()) {
            popupWindow.dismiss();
            addMarketBuy(position, lots);
        } else {
            Toast.makeText(getContext(), "Please enter Lots quantity", Toast.LENGTH_SHORT).show();
        }

    }

    public void orderSellClick(int position, String lots, String price) {

        if (!lots.isEmpty() && !price.isEmpty()) {

            popupWindow.dismiss();
            addOrderSell(position, lots, price);

        } else {
            Toast.makeText(getContext(), "Lots and price cannot be empty", Toast.LENGTH_SHORT).show();
        }

    }

    public void orderBuyClick(int position, String lots, String price) {

        if (!lots.isEmpty() && !price.isEmpty()) {
            popupWindow.dismiss();
            addOrderBuy(position, lots, price);
        } else {
            Toast.makeText(getContext(), "Lots and price cannot be empty", Toast.LENGTH_SHORT).show();
        }

    }

    public void addMarketSell(int position, String lot) {
        MCXModel model = mcxadapter.getDataAt(position);

        if (model != null) {
            String name = model.getName();
            String date = model.getDate();
            String str3 = model.getLot();
            String rate = model.getNum1();
            String margin = model.getNum2();

            Date date1 = new Date();
            long time = date1.getTime();
            ActiveModel activeModel = new ActiveModel("Sell X", "Market", name, date, String.valueOf(time), rate, "Sold by trader", margin);
            setActiveData(activeModel);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Success")
                    .setMessage("Sold " + lot + " lots of " + name + " AT " + rate)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Log.d(TAG, "onClick: dismissed dialog");
                            Bundle bundle = new Bundle();
                            bundle.putString("KEY", "Active");
                            changeFragment(new TradesFragment(), bundle);
                        }
                    }).show();
        }
    }

    public void addMarketBuy(int position, String lot) {
        MCXModel model = mcxadapter.getDataAt(position);

        if (model != null) {
            String name = model.getName();
            String date = model.getDate();
            String str3 = model.getLot();
            String rate = model.getNum1();
            String margin = model.getNum2();

            Date date1 = new Date();
            long time = date1.getTime();
            ActiveModel activeModel = new ActiveModel("Buy X", "Market", name, date, String.valueOf(time), rate, "Bought by trader", margin);

            setActiveData(activeModel);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Success")
                    .setMessage("Bought " + lot + " lots of " + name + " AT " + rate)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Log.d(TAG, "onClick: dismissed dialog");

                            Bundle bundle = new Bundle();
                            bundle.putString("KEY", "Active");

                            changeFragment(new TradesFragment(), bundle);
                        }
                    }).show();
        }

    }

    public void addOrderSell(int position, String lot, String price) {
        MCXModel model = mcxadapter.getDataAt(position);

        if (model != null) {
            String name = model.getName();
            String date = model.getDate();
            String str3 = model.getLot();
            String rate = model.getNum1();
            String margin = model.getNum2();

            Date date1 = new Date();
            long time = date1.getTime();
            ActiveModel activeModel = new ActiveModel("Sell X", "Market", name, date, String.valueOf(time), price, "order by trader", margin);
            setPendingData(activeModel);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Success")
                    .setMessage("Sell order " + lot + " lots of " + name + " AT " + price + " placed")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Log.d(TAG, "onClick: dismissed dialog");

                            Bundle bundle = new Bundle();
                            bundle.putString("KEY", "Pending");
                            changeFragment(new TradesFragment(), bundle);
                        }
                    }).show();
        }
    }

    public void addOrderBuy(int position, String lot, String price) {

        MCXModel model = mcxadapter.getDataAt(position);

        if (model != null) {
            String name = model.getName();
            String date = model.getDate();
            String str3 = model.getLot();
            String rate = model.getNum1();
            String margin = model.getNum2();

            Date date1 = new Date();
            long time = date1.getTime();
            ActiveModel activeModel = new ActiveModel("Buy X", "Market", name, date, String.valueOf(time), price, "order by trader", margin);
            setPendingData(activeModel);


            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Success")
                    .setMessage("Buy order " + lot + " lots of " + name + " AT " + price + " placed")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Log.d(TAG, "onClick: dismissed dialog");
                            Bundle bundle = new Bundle();
                            bundle.putString("KEY", "Pending");
                            changeFragment(new TradesFragment(), bundle);

                        }
                    }).show();
        }
    }


    public void setActiveData(ActiveModel activeModel) {

        Log.d(TAG, "setActiveData: setting active fragment data list");
        DatabaseReference ref = database.getReference("UserList").child(currentUser.getUid()).child("ActiveList");
        ref.push().setValue(activeModel);

    }

    public void setPendingData(ActiveModel activeModel) {

        DatabaseReference ref = database.getReference("UserList").child(currentUser.getUid()).child("PendingList");
        ref.push().setValue(activeModel);

    }

    public void changeFragment(Fragment fragment, Bundle bundle) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment.setArguments(bundle);
        transaction.replace(R.id.homeFrame, fragment)
                .commit();
    }



    public void showPopupWindow(int position) {
        popupView = getLayoutInflater().inflate(R.layout.popup_layout_mcx, null);
        popupWindow = new PopupWindow(getActivity());
        TextView tvMarket = popupView.findViewById(R.id.tvMarket);
        TextView tvOrder = popupView.findViewById(R.id.tvOrder);
        TextView tvSelectedName = popupView.findViewById(R.id.tvSelectedName);
        TextView numSellMarket = popupView.findViewById(R.id.numSellMarket);
        TextView numBuyMarket = popupView.findViewById(R.id.numBuyMarket);
        LinearLayout orderPriceLayout = popupView.findViewById(R.id.orderPriceLayout);
        LinearLayout orderLotsLayout = popupView.findViewById(R.id.orderLotsLayout);
        LinearLayout marketLotsLayout = popupView.findViewById(R.id.marketLotsLayout);
        LinearLayout buylinearLayout = popupView.findViewById(R.id.buylinearlayout);
        LinearLayout mbuylinearLayout = popupView.findViewById(R.id.mbuylinearlayout);
        LinearLayout selllinearLayout = popupView.findViewById(R.id.selllinearlayout);
        LinearLayout mselllinearLayout = popupView.findViewById(R.id.mselllinearlayout);
        EditText etMarketLots = popupView.findViewById(R.id.etMarketLots);
        EditText etOrderLots = popupView.findViewById(R.id.etOrderLots);
        EditText etOrderPrice = popupView.findViewById(R.id.etOrderPrice);

        popupWindow.setContentView(popupView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(mView, Gravity.CENTER, 0, 0);


        MCXModel model = mcxadapter.getDataAt(position);
        if (model != null) {

            String str1 = model.getName();
            String str2 = model.getDate();
            String str3 = model.getLot();
            String str4 = model.getNum1();
            String str5 = model.getNum2();

            tvSelectedName.setText(str1);
            numSellMarket.setText(str4);
            numBuyMarket.setText(str5);

        }

        tvMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: tvmarket pr aagayiiiiiiiiiii");
                buylinearLayout.setVisibility(View.GONE);
                selllinearLayout.setVisibility(View.GONE);
                mbuylinearLayout.setVisibility(View.VISIBLE);
                mselllinearLayout.setVisibility(View.VISIBLE);

                marketLotsLayout.setVisibility(View.VISIBLE);
                orderPriceLayout.setVisibility(View.GONE);
                orderLotsLayout.setVisibility(View.GONE);
                tvMarket.setBackgroundColor(getResources().getColor(R.color.black));
                tvMarket.setTextColor(getResources().getColor(R.color.white));

                tvOrder.setBackgroundColor(getResources().getColor(R.color.white));
                tvOrder.setTextColor(getResources().getColor(R.color.black));

                Log.d(TAG, "onClick: Showing popup window tvMArket");

            }
        });

        tvOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: tvOrder pr b aagaiiiiiiiiiiiiiiiiii");
                mbuylinearLayout.setVisibility(View.GONE);
                mselllinearLayout.setVisibility(View.GONE);
                buylinearLayout.setVisibility(View.VISIBLE);
                selllinearLayout.setVisibility(View.VISIBLE);

                marketLotsLayout.setVisibility(View.GONE);
                orderPriceLayout.setVisibility(View.VISIBLE);
                orderLotsLayout.setVisibility(View.VISIBLE);
                tvOrder.setBackgroundColor(getResources().getColor(R.color.black));
                tvOrder.setTextColor(getResources().getColor(R.color.white));

                tvMarket.setBackgroundColor(getResources().getColor(R.color.white));
                tvMarket.setTextColor(getResources().getColor(R.color.black));

                Log.d(TAG, "onClick: Showing popup window tvOrder");


            }
        });

        mselllinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etMarketLots.getText().toString().isEmpty()) {
                    String lots = etMarketLots.getText().toString();

                    mSellClick(position, lots);
                    Log.d(TAG, "onClick: Dismissing mSellLinear layoutt");

                } else {
                    Toast.makeText(getContext(), "Please enter lots", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mbuylinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!etMarketLots.getText().toString().isEmpty()) {
                    String lots = etMarketLots.getText().toString();
                    mBuyClick(position, lots);
                    Log.d(TAG, "onClick: Dismissing mBuyLinear layoutt");

                } else {
                    Toast.makeText(getContext(), "Please enter lots", Toast.LENGTH_SHORT).show();
                }
            }
        });

        selllinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: selllinear layout pr click kiya " + popupWindow);
                if (!etOrderLots.getText().toString().isEmpty() && !etOrderPrice.getText().toString().isEmpty()) {

                    popupWindow.dismiss();
                    String lots = etOrderLots.getText().toString();
                    String price = etOrderPrice.getText().toString();
                    orderSellClick(position, lots, price);
                    Log.d(TAG, "onClick: Dismissing orderSellLinear layoutt");


                } else {
                    Toast.makeText(getContext(), "lots and price cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buylinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etOrderLots.getText().toString().isEmpty() && !etOrderPrice.getText().toString().isEmpty()) {
                    String lots = etOrderLots.getText().toString();
                    String price = etOrderPrice.getText().toString();
                    orderBuyClick(position, lots, price);
                    Log.d(TAG, "onClick: Dismissing orderBuyLinear layoutt");


                } else {
                    Toast.makeText(getContext(), "lots and price cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow=null;
        }
    }
}