package com.shree.sigma.portfolio;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.shree.sigma.PClosedFragment;
import com.shree.sigma.R;
import com.shree.sigma.trades.PagerAdapter;

public class PortfolioFragment extends Fragment {
    TextView titleText;
    TabLayout tabLayout;
    ViewPager viewPager;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_portfolio, container, false);

        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.homeTabs);

        setupViewPager();

        return view;
    }

    private void setupViewPager(){
        PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new PActiveFragment());
        adapter.addFragment(new PClosedFragment());


        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Active");
        tabLayout.getTabAt(1).setText("Closed");
    }
}