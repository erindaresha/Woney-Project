package id.ac.ukdw.woney;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.database.Transaction;

import java.util.List;

import mbanje.kurt.fabbutton.CircleImageView;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionHolder> {

    private List<id.ac.ukdw.woney.Transaction> listTransaction;
    private TransactionFragment mContext;


    public TransactionAdapter(List<id.ac.ukdw.woney.Transaction> listTransaction, TransactionFragment mContext) {
        this.listTransaction = listTransaction;
        this.mContext = mContext;
    }

    public class TransactionHolder extends RecyclerView.ViewHolder {
        private ImageView imgTransaction;
        private TextView txtForm, txtDate , txtValue;

        public TransactionHolder(View itemView) {
            super(itemView);

            imgTransaction = (ImageView) itemView.findViewById(R.id.img_item_photo);
            txtForm = (TextView) itemView.findViewById(R.id.item_from);
            txtDate = (TextView) itemView.findViewById(R.id.item_date);
            txtValue = (TextView) itemView.findViewById(R.id.item_value);

        }
    }

    @Override
    public TransactionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);

        return new TransactionHolder(itemView);
    }



    @Override
    public void onBindViewHolder(TransactionHolder holder, int position) {
        id.ac.ukdw.woney.Transaction transaction = listTransaction.get(position);
        holder.txtForm.setText(transaction.from);
        holder.txtDate.setText(transaction.date);
        if(transaction.status == "kirim"){
            holder.imgTransaction.setImageResource(R.drawable.red_arrow);
            holder.txtValue.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.txtValue.setText("- " + transaction.value);
        }
        else if(transaction.status == "terima"){
            holder.imgTransaction.setImageResource(R.drawable.green_arrow);
            holder.txtValue.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.txtValue.setText("+ " + transaction.value);
        }
        else{
            holder.imgTransaction.setImageResource(R.drawable.blue_circle);
            holder.txtValue.setTextColor(mContext.getResources().getColor(R.color.blue));
            holder.txtValue.setText("+ " + transaction.value);
        }
    }

    @Override
    public int getItemCount() {
        return listTransaction.size();
    }
}