package com.shree.sigma.watchlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.shree.sigma.trades.PagerAdapter;
import com.shree.sigma.R;

public class WatchlistFragment extends Fragment {
    TextView titleText;
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);

        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.homeTabs);
        titleText = view.findViewById(R.id.titleText);
        titleText.setText("Marketwatch");

        setupViewPager();

        return view;
    }

    private void setupViewPager(){
        PagerAdapter adapter = new PagerAdapter(requireActivity().getSupportFragmentManager());
        adapter.addFragment(new MCXFragment());
        adapter.addFragment(new NSEFragment());


        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("MCX Futures");
        tabLayout.getTabAt(1).setText("NSE Futures");
    }
}