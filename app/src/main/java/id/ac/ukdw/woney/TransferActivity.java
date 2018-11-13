package id.ac.ukdw.woney;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class TransferActivity extends MasterActivity {
    TextView txtSaldoAktif;
    String nama, username, saldo;
    Context mContext;
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
    Button btnBayar;
    EditText edtUsername, edtSaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtSaldoAktif = findViewById(R.id.txtSaldoAktif);
        btnBayar = findViewById(R.id.btnBayar);

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama = sp.getString("nama", null);
                username = sp.getString("username", null);
                listUser = (ArrayList) dataSnapshot.getValue();
                String _saldo="", _username="";
                for (int i = 0; i < listUser.size(); i++) {
                    Map mapUser = (Map) listUser.get(i);
                    _username += mapUser.get("username");
                    if (username.equals(_username)) {
                        _saldo += mapUser.get("saldo");
                        Float f = Float.parseFloat(_saldo);
                        txtSaldoAktif.setText(formatRupiah.format(f));
                        break;
                    } else {
                        _username = "";
                        _saldo = "";
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnBayar.setOnClickListener(this);
        mContext = this;
    }

    @Override
    public int getLayoutResourceID() {
        return R.layout.activity_transfer;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBayar :
                edtUsername = findViewById(R.id.edtUsername);
                edtSaldo = findViewById(R.id.edtSaldo);
                username = edtUsername.getText().toString();
                saldo = edtSaldo.getText().toString();
                if(isValidate()) {
                    bayar();
                }
                break;
        }
    }

    public boolean isValidate() {
        if (!username.matches("") && !saldo.matches(""))
            return true;
        else return false;
    }

    public void bayar() {
        final Float _saldo = Float.parseFloat(saldo);
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            boolean status = false;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listUser = (ArrayList) dataSnapshot.getValue();
                String uname = "", Saldo = "", email = "", nama = "", password = "", pin = "";
                String usernameSumber = sp.getString("username", null);
                for (int i=0; i<listUser.size(); i++) {
                    Map mapUser = (Map) listUser.get(i);
                    uname += mapUser.get("username");
                    if(uname.equals(usernameSumber)) {
                        Saldo += mapUser.get("saldo");
                        Float f = Float.parseFloat(Saldo);
                        if(_saldo > f) {
                            status = false;
                            break;
                        }
                        f -= _saldo;
                        Saldo = Float.toString(f);
                        mapUser.put("saldo", Saldo);
                        user.setValue(listUser);
                        status = true;
                        break;
                    } else {
                        uname = "";
                    }
                }
                uname = "";
                Saldo = "";
                if (status) {
                    for (int i = 0; i < listUser.size(); i++) {
                        Map mapUser = (Map) listUser.get(i);
                        uname += mapUser.get("username");
                        if (uname.equals(username)) {
                            Saldo += mapUser.get("saldo");
                            Float f = Float.parseFloat(Saldo);
                            f += _saldo;
                            Saldo = Float.toString(f);
                            mapUser.put("saldo", Saldo);
                            user.setValue(listUser);
                            Toast.makeText(mContext, "Pembayaran berhasil dilakukan", Toast.LENGTH_LONG).show();
                            status = true;
                            break;
                        } else {
                            uname = "";
                            status = false;
                        }
                    }
                    if (!status) Toast.makeText(mContext, "Pembayaran gagal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
