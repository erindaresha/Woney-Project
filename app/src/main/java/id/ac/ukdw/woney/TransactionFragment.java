package id.ac.ukdw.woney;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment {
    private RecyclerView listTransaction;
    private TransactionAdapter transactionAdapter;
    SharedPreferences sp;
    SharedPreferences.Editor spEdit;
    String storedUsername="";
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference transaksi = db.getReference("transaksi");
    ArrayList listTransaksi;
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.fragment_transaction, container, false);

        sp = this.getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);
        spEdit = sp.edit();
        storedUsername = sp.getString("username", null);

        listTransaction = (RecyclerView) view.findViewById(R.id.list_item);
        final List<Transaction> dataTransaction = new ArrayList<Transaction>();
        transactionAdapter = new TransactionAdapter(dataTransaction, this);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        listTransaction.setLayoutManager(lm);
        listTransaction.setItemAnimator(new DefaultItemAnimator());
        listTransaction.setAdapter(transactionAdapter);

        transaksi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (storedUsername != null) {
                    listTransaksi = (ArrayList) dataSnapshot.getValue();
                    String saldo = "", _username_pengirim="", nama = "", waktu = "", jenis_transaksi = "",
                    username_penerima = "", status = "";
                    for (int i = listTransaksi.size()-1; i >= 0; i--) {
                        Map mapTransaksi = (Map) listTransaksi.get(i);
                        _username_pengirim += mapTransaksi.get("username_pengirim");
                        username_penerima += mapTransaksi.get("username_penerima");
                        if(username_penerima.equals(storedUsername) || _username_pengirim.equals(storedUsername)) {
                            saldo += mapTransaksi.get("saldo");
                            waktu += mapTransaksi.get("waktu");
                            jenis_transaksi += mapTransaksi.get("jenis_transaksi");
                            if(jenis_transaksi.equalsIgnoreCase("Top Up")) {
                                nama += _username_pengirim;
                            } else {
                                nama += mapTransaksi.get("nama_pengirim") + "\n(" + _username_pengirim + ")";
                            }
                            if(jenis_transaksi.equalsIgnoreCase("transfer")) {
                                if(_username_pengirim.equals(storedUsername)) {
                                    status = "kirim";
                                    nama = "";
                                    nama += mapTransaksi.get("nama_penerima") + "\n(" + username_penerima + ")";
                                } else {
                                    status = "terima";
                                }
                            }
                            Float f = Float.parseFloat(saldo);
                            saldo = formatRupiah.format(f);
                            dataTransaction.add(new Transaction(nama, waktu, status, saldo));
                            System.out.println(nama + waktu + status + saldo);
                        }
                        username_penerima = "";
                        _username_pengirim = "";
                        nama = "";
                        waktu = "";
                        jenis_transaksi = "";
                        saldo = "";
                        status = "";
                    }
                    transactionAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}
