package com.shree.sigma.trades;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.shree.sigma.HomeActivity;
import com.shree.sigma.R;
import com.shree.sigma.watchlist.MCXFragment;

public class TradesFragment extends Fragment {
    TextView titleText;
    TabLayout tabLayout;
    ViewPager viewPager;
    private static final String TAG = "TradesFragment";

    MCXFragment mcxFragment = new MCXFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trades, container, false);

        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.homeTabs);
        titleText = view.findViewById(R.id.titleText);
        titleText.setText("Trades");
        setupViewPager();

        if (getArguments() != null) {
            String arg = getArguments().getString("KEY");

            if(arg!=null&&arg.equals("Pending")){
                Log.d(TAG, "onCreateView: yesssssssssssssssssssssssss"+arg);
                viewPager.setCurrentItem(0);
            }
            if(arg!=null&&arg.equals("Active")){
                Log.d(TAG, "onCreateView: nnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
                viewPager.setCurrentItem(1);
            }

        }

        return view;
    }

    private void setupViewPager(){
        PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new PendingFragment());
        adapter.addFragment(new ActiveFragment());
        adapter.addFragment(new ClosedFragment());

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Pending");
        tabLayout.getTabAt(1).setText("Active");
        tabLayout.getTabAt(2).setText("Closed");
    }

    @Override
    public void onResume() {
        super.onResume();

        HomeActivity activity = (HomeActivity)getActivity();
        if (activity != null) {
            activity.setTradesFragmentChecked();
        }
    }
}