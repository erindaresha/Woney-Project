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

public class ChangeNameActivity extends MasterActivity {
    EditText txtChangeName;
    Button btnChangeName;
    String oldName, newName, storedUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        txtChangeName = findViewById(R.id.txtChangeName);
        btnChangeName = findViewById(R.id.btnChangeName);

        btnChangeName.setOnClickListener(this);
        mContext = this;
    }

    @Override
    public int getLayoutResourceID() {
        return R.layout.activity_change_name;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChangeName :
                oldName = sp.getString("nama", null);
                newName = txtChangeName.getText().toString();
                storedUsername = sp.getString("username", null);
                if (isValidated()) {
                    changeName();
                }
                break;
        }
    }

    public boolean isValidated() {
        if (newName.matches("")) return false;
        else return true;
    }

    public void changeName() {
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean status = false;
                listUser = (ArrayList) dataSnapshot.getValue();
                String username="";
                for (int i = 0; i < listUser.size(); i++) {
                    Map mapUser = (Map) listUser.get(i);
                    username += mapUser.get("username");
                    if (username.equals(storedUsername)) {
                        mapUser.put("name", newName);
                        listUser.set(i, mapUser);
                        user.setValue(listUser);
                        spEdit.putString("nama", newName);
                        spEdit.commit();
                        status = true;
                        Toast.makeText(mContext, "Nama berhasil diubah", Toast.LENGTH_SHORT).show();
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
