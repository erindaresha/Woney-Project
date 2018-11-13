package id.ac.ukdw.woney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CekTransferActivity extends MasterActivity {
    Button btnCekPIN;
    EditText txtInputPIN;
    String pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtInputPIN = findViewById(R.id.txtInputPIN);
        btnCekPIN = findViewById(R.id.btnCekPIN);
        btnCekPIN.setOnClickListener(this);
    }

    @Override
    public int getLayoutResourceID() {
        return R.layout.activity_cek_transfer;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCekPIN :
                pin = txtInputPIN.getText().toString();
                if(isNotEmpty()) {
                    if(isValidated()) {
                        Intent intent = new Intent(this, TransferActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "PIN salah", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    public boolean isNotEmpty() {
        if(pin.matches("")) return false;
        else return true;
    }

    public boolean isValidated() {
        String _pin = sp.getString("pin", null);
        if(pin.equals(_pin)) return true;
        else return false;
    }
}
