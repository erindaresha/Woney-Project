package id.ac.ukdw.woney;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public abstract class MasterActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sp;
    SharedPreferences.Editor spEdit;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference user = db.getReference("user");
    DatabaseReference transaksi = db.getReference().child("transaksi");
    ArrayList listUser = new ArrayList();
    ArrayList listTransaksi = new ArrayList();
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceID());

        sp = getSharedPreferences("session", MODE_PRIVATE);
        spEdit = sp.edit();
    }

    public abstract int getLayoutResourceID();
}
