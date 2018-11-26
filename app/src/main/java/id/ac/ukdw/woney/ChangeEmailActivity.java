package id.ac.ukdw.woney;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ChangeEmailActivity extends MasterActivity {
    Button btnChangeEmail;
    String newEmail, oldEmail;
    EditText txtChangeEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnChangeEmail = findViewById(R.id.btnChangeEmail);
        txtChangeEmail = findViewById(R.id.txtChangeEmail);
        oldEmail = sp.getString("email",null);

        btnChangeEmail.setOnClickListener(this);
        mContext = this;
    }

    @Override
    public int getLayoutResourceID() {
        return R.layout.activity_change_email;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChangeEmail :
                newEmail = txtChangeEmail.getText().toString();
                if (isValidate()) {
                    changeEmail();
                }
                break;
        }
    }

    public boolean isValidate() {
        if (!newEmail.matches("") && Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public void changeEmail() {
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean status = false;
                listUser = (ArrayList) dataSnapshot.getValue();
                String email="", _username="";
                for (int i = 0; i < listUser.size(); i++) {
                    Map mapUser = (Map) listUser.get(i);
                    email += mapUser.get("email");
                    if (email.equals(oldEmail)) {
                        if (email.equals(newEmail)) {
                            status = false;
                            Toast.makeText(mContext, "Pengubahan data gagal. Email telah terdaftar", Toast.LENGTH_LONG).show();
                            break;
                        } else {
                            status = true;
                            email = "";
                        }
                        mapUser.put("email", newEmail);
                        listUser.set(i, mapUser);
                        user.setValue(listUser);
                        spEdit.putString("email", newEmail);
                        spEdit.commit();
                        status = true;
                        Toast.makeText(mContext, "Email berhasil diubah", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    } else {
                        email = "";
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
