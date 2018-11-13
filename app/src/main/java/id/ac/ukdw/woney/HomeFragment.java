package id.ac.ukdw.woney;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    NumberFormat nf;
    SharedPreferences sp;
    SharedPreferences.Editor spEdit;
    private TextView txtName, txtUsername, txtSaldo, txtSend;
    String nama="", username="";
    float saldo;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference user = db.getReference("user");
    ArrayList listUser;
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.fragment_home, container, false);
        //nf = new DecimalFormat("#.###");
        sp = this.getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);
        spEdit = sp.edit();
        txtName = view.findViewById(R.id.txtName);
        txtUsername = view.findViewById(R.id.txtUsername);
        txtSaldo = view.findViewById(R.id.txtSaldo);
        txtSend = view.findViewById(R.id.txtSend);

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama = sp.getString("nama", null);
                username = sp.getString("username", null);
                if (username != null) {
                    listUser = (ArrayList) dataSnapshot.getValue();
                    String _saldo="", _username="";
                    for (int i = 0; i < listUser.size(); i++) {
                        Map mapUser = (Map) listUser.get(i);
                        _username += mapUser.get("username");
                        if (username.equals(_username)) {
                            _saldo += mapUser.get("saldo");
                            Float f = Float.parseFloat(_saldo);
                            txtName.setText(nama);
                            txtUsername.setText(username);
                            txtSaldo.setText(formatRupiah.format(f));
                            break;
                        } else {
                            _username = "";
                            _saldo = "";
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        txtSend.setOnClickListener(this);
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtSend :
                Intent intent = new Intent(getActivity(), CekTransferActivity.class);
                startActivity(intent);
                break;
        }
    }

}
