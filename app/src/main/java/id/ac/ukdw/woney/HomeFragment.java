package id.ac.ukdw.woney;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    NumberFormat nf;
    SharedPreferences sp;
    SharedPreferences.Editor spEdit;
    private TextView txtName, txtUsername, txtSaldo;
    String nama, username;
    float saldo;
    TextView txtSend;
    View view;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        view = lf.inflate(R.layout.fragment_home, container, false);
        nf = new DecimalFormat("#.###");
        sp = this.getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);
        spEdit = sp.edit();
        txtName = view.findViewById(R.id.txtName);
        txtUsername = view.findViewById(R.id.txtUsername);
        txtSaldo = view.findViewById(R.id.txtSaldo);
        txtSend = view.findViewById(R.id.txtSend);


        nama = sp.getString("nama", null);
        username = sp.getString("username", null);
        saldo = (float)sp.getFloat("saldo", 0);

        txtName.setText(nama);
        txtUsername.setText(username);
        String Saldo = "Rp ";
        Saldo += nf.format((double)saldo) + ",-";
        txtSaldo.setText(Saldo);

        txtSend.setOnClickListener(this);

        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtSend :
                Intent intent = new Intent(getActivity(), TransferActivity.class);
                startActivity(intent);
                break;
        }
    }

}
