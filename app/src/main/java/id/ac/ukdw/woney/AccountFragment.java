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


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {
    NumberFormat nf;
    SharedPreferences sp;
    SharedPreferences.Editor spEdit;
    private TextView txtName, txtUsername, txtSaldo, txtChangeName, txtEmailName;
    String nama, username, email;
    float saldo;
    Button btnLogout;
    TextView txtChangeNameBtn;
    TextView txtChangeEmailBtn;
    TextView txtChangePassBtn;
    TextView txtChangePinBtn;
    View view;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference user = db.getReference("user");

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        view = lf.inflate(R.layout.fragment_account, container, false);
        //nf = new DecimalFormat("#.###");
        sp = this.getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);
        spEdit = sp.edit();
        txtName = view.findViewById(R.id.txtName);
        txtUsername = view.findViewById(R.id.txtUsername);
        btnLogout = view.findViewById(R.id.btnLogoutFragmentLogout);
        txtChangeNameBtn = view.findViewById(R.id.txtChangeNameBtn);
        txtChangeEmailBtn = view.findViewById(R.id.txtChangeEmailBtn);
        txtChangePassBtn = view.findViewById(R.id.txtChangePassBtn);
        txtChangePinBtn = view.findViewById(R.id.txtChangePinBtn);
        txtChangeName = view.findViewById(R.id.txtChangeName);
        txtEmailName = view.findViewById(R.id.txtEmailName);

        //txtSaldo = view.findViewById(R.id.txtSaldo);
        //saldo = (float)sp.getFloat("saldo", 0);
        btnLogout.setOnClickListener(this);
        txtChangeNameBtn.setOnClickListener(this);
        txtChangeEmailBtn.setOnClickListener(this);
        txtChangePassBtn.setOnClickListener(this);
        txtChangePinBtn.setOnClickListener(this);

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama = sp.getString("nama", null);
                username = sp.getString("username", null);
                email = sp.getString("email", null);
                txtName.setText(nama);
                txtUsername.setText(username);
                txtEmailName.setText(email);
                txtChangeName.setText(nama);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogoutFragmentLogout :
                spEdit.putBoolean("isLogin", false);
                spEdit.commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "Anda Telah Logout", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                break;
            case R.id.txtChangeNameBtn:
                Intent intent2 = new Intent(getActivity(), ChangeNameActivity.class);
                startActivity(intent2);
                break;
            case R.id.txtChangeEmailBtn:
                Intent intent3 = new Intent(getActivity(), ChangeEmailActivity.class);
                startActivity(intent3);
                break;
            case R.id.txtChangePassBtn:
                Intent intent4 = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent4);
                break;
            case R.id.txtChangePinBtn:
                Intent intent5 = new Intent(getActivity(), ChangePINActivity.class);
                startActivity(intent5);
                break;
        }
    }
}
