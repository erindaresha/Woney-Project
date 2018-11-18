package id.ac.ukdw.woney;


import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment {
    private RecyclerView listTransaction;
    private TransactionAdapter transactionAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaction, container, false);


        listTransaction = (RecyclerView) view.findViewById(R.id.list_item);
        List<Transaction> dataTransaction = new ArrayList<Transaction>();
        transactionAdapter = new TransactionAdapter(dataTransaction, this);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        listTransaction.setLayoutManager(lm);
        listTransaction.setItemAnimator(new DefaultItemAnimator());
        listTransaction.setAdapter(transactionAdapter);

        dataTransaction.add(new Transaction("Setiawan hu", "18-11-2018", "kirim" , "2000"));
        dataTransaction.add(new Transaction("Erinda Resha", "18-11-2018", "terima", "5000"));
        dataTransaction.add(new Transaction("Cindy Clara", "18-11-2018", "kirim" , "2000"));
        dataTransaction.add(new Transaction("Charles Eka", "18-11-2018", "terima", "3000"));
        dataTransaction.add(new Transaction("Top Up", "18-11-2018", "TopUp", "5000"));

        transactionAdapter.notifyDataSetChanged();

        return view;
    }

}
