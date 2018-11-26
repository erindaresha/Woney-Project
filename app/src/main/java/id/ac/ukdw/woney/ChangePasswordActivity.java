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

public class ChangePasswordActivity extends MasterActivity {
    EditText txtCekPass,txtChangePass1, txtChangePass2;
    Button btnChangePass;
    String oldPass, pass1, pass2, storedUsername, storedPassword;
    boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        txtCekPass = findViewById(R.id.txtCekPass);
        txtChangePass1 = findViewById(R.id.txtChangePass1);
        txtChangePass2 = findViewById(R.id.txtChangePass2);
        btnChangePass = findViewById(R.id.btnChangePass);

        btnChangePass.setOnClickListener(this);
    }

    @Override
    public int getLayoutResourceID() {
        return R.layout.activity_change_password;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChangePass :
                storedPassword = sp.getString("password", null);
                storedUsername = sp.getString("username", null);
                oldPass = txtCekPass.getText().toString();
                pass1 = txtChangePass1.getText().toString();
                pass2 = txtChangePass2.getText().toString();
                if (isValidated()) {
                    changePassword();
                }
                break;
        }
    }

    public boolean isValidated() {
        status = false;
        if (!oldPass.matches("") && !pass1.matches("") && !pass2.matches("")) {
            if (oldPass.equals(storedPassword)) {
                if (pass1.equals(pass2)) {
                    if (oldPass.equals(pass1)) {
                        status = false;
                        Toast.makeText(mContext, "Password tidak boleh sama dengan password lama", Toast.LENGTH_SHORT).show();
                    } else {
                        status = true;
                    }
                } else {
                    status = false;
                    Toast.makeText(mContext, "Password yang dimasukkan tidak sama", Toast.LENGTH_SHORT).show();
                }
            } else {
                status = false;
                Toast.makeText(mContext, "Password lama tidak sesuai", Toast.LENGTH_SHORT).show();
            }
        } else return status = false;
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
                        mapUser.put("password", pass1);
                        listUser.set(i, mapUser);
                        user.setValue(listUser);
                        spEdit.putString("password", pass1);
                        spEdit.commit();
                        status = true;
                        Toast.makeText(mContext, "Password berhasil diubah", Toast.LENGTH_SHORT).show();
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
