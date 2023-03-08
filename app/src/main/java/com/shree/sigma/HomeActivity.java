package com.shree.sigma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shree.sigma.portfolio.PortfolioFragment;
import com.shree.sigma.trades.ActiveModel;
import com.shree.sigma.trades.TradesFragment;
import com.shree.sigma.watchlist.MCXAdapter;
import com.shree.sigma.watchlist.WatchlistFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private static final String TAG = "HomeActivity";

    //FIREBASE
    private MutableLiveData<List<ActiveModel>> data = new MutableLiveData<>();
    private List<ActiveModel> aList = new ArrayList<>();
    private List<ActiveModel> pList = new ArrayList<>();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userList = database.getReference("UserList");

    MCXAdapter mcxAdapter;

    View popupView ;

    PopupWindow popupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.homeFrame, WatchlistFragment.class, null)
                .commit();

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_watchList:
                        changeFragment(new WatchlistFragment(), "WatchlistFragment");
                        break;
                    case R.id.nav_trades:
                        changeFragment(new TradesFragment(), "TradesFragment");
                        break;
                    case R.id.nav_portfolio:
                        changeFragment(new PortfolioFragment(), "PortfolioFragment");
                        break;

                    case R.id.nav_account:
                        changeFragment(new AccountFragment(), "AccountFragment");
                        break;

                }
                return true;
            }
        });
    }

    public void changeFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.attach(fragment);
        transaction
                .add(fragment, tag)
                .replace(R.id.homeFrame, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    public  void  setTradesFragmentChecked(){
        bottomNavigationView.getMenu().findItem(R.id.nav_trades).setChecked(true);
    }


}