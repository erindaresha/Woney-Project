package id.ac.ukdw.woney;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment{


    public TransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        final String[] items = new String[] { "Transfer", "Transfer", "Transfer",
                "Top Up", "Top Up", "Menerima", "Menerima", "Top Up",
                "Transfer", "Transfer" };
        // Inflate the layout for this fragment
        final ListView list = (ListView)view.findViewById(R.id.list_view);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Log.v("TAG", "CLICKED row number: " + arg2);
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
            }

        });

        return view;
    }
}
