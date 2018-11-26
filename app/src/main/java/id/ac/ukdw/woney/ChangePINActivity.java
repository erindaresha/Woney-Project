package id.ac.ukdw.woney;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ChangePINActivity extends MasterActivity {
    EditText txtCekPin,txtChangePin1, txtChangePin2;
    Button btnChangePIN;
    String oldPin, pin1, pin2, storedUsername, storedPin;
    boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        txtCekPin = findViewById(R.id.txtCekPIN);
        txtChangePin1 = findViewById(R.id.txtChangePIN1);
        txtChangePin2 = findViewById(R.id.txtChangePIN2);
        btnChangePIN = findViewById(R.id.btnChangePIN);

        btnChangePIN.setOnClickListener(this);
    }

    @Override
    public int getLayoutResourceID() {
        return R.layout.activity_change_pin;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChangePIN :
                storedPin = sp.getString("pin", null);
                storedUsername = sp.getString("username", null);
                oldPin = txtCekPin.getText().toString();
                pin1 = txtChangePin1.getText().toString();
                pin2 = txtChangePin2.getText().toString();
                if (isValidated()) {
                    changePassword();
                }
                break;
        }
    }

    public boolean isValidated() {
        status = false;
        if (!oldPin.matches("") && !pin1.matches("") && !pin2.matches("")) {
            if (oldPin.equals(storedPin)) {
                if (pin1.equals(pin2)) {
                    if (oldPin.equals(pin1)) {
                        status = false;
                        Toast.makeText(mContext, "PIN tidak boleh sama dengan PIN lama", Toast.LENGTH_SHORT).show();
                    } else {
                        status = true;
                    }
                } else {
                    status = false;
                    Toast.makeText(mContext, "PIN yang dimasukkan tidak sama", Toast.LENGTH_SHORT).show();
                }
            } else {
                status = false;
                Toast.makeText(mContext, "PIN lama tidak sesuai", Toast.LENGTH_SHORT).show();
            }
        } else status = false;
        return status;
    }

    public void changePassword() {
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status = false;
                String username = "";
                listUser = (ArrayList) dataSnapshot.getValue();
                for (int i=0; i<listUser.size(); i++) {
                    Map mapUser = (Map) listUser.get(i);
                    username += mapUser.get("username");
                    if (username.equals(storedUsername)) {
                        mapUser.put("pin", pin1);
                        listUser.set(i, mapUser);
                        user.setValue(listUser);
                        spEdit.putString("pin", pin1);
                        spEdit.commit();
                        status = true;
                        Toast.makeText(mContext, "PIN berhasil diubah", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    } else {
                        username = "";
                        status = false;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
