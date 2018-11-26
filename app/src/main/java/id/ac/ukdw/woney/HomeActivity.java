package id.ac.ukdw.woney;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends MasterActivity {
    private BottomNavigationView navMainNav;
    private FrameLayout frmMainFrame;

    private HomeFragment homeFragment;
    private TransactionFragment transactionFragment;
    private HistoryFragment historyFragment;
    private AccountFragment accountFragment;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        frmMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        navMainNav = (BottomNavigationView) findViewById(R.id.main_nav);
        context = this;

        homeFragment = new HomeFragment();
        transactionFragment = new TransactionFragment();
        historyFragment = new HistoryFragment();
        accountFragment = new AccountFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, homeFragment);
        fragmentTransaction.commit();

        navMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        navMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(homeFragment);
                        return true;
                    case R.id.nav_transaction:
                        navMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(transactionFragment);
                        return true;

                    /*case R.id.nav_history:
                        navMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(historyFragment);
                        return true;*/

                    case R.id.nav_account:
                        navMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(accountFragment);
                        return true;

                    default: return false;
                }
            }

            private void setFragment(Fragment fragment) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, fragment);
                fragmentTransaction.commit();
            }
        });

//        user.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                NotificationHelper nh = new NotificationHelper(context);
//                nh.createNotification("Woney", "Transaksi berhasil dilakukan");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public int getLayoutResourceID() {
        return R.layout.activity_home;
    }

    @Override
    public void onClick(View v) {

    }
}
