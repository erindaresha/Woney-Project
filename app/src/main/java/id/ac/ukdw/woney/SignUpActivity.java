package id.ac.ukdw.woney;

import android.content.Context;
import android.content.Intent;
import android.os.PatternMatcher;
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
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity extends MasterActivity {
    EditText edtName, edtUsername, edtEmail, edtPassword, edtPin;
    String nama, username, password, email, pin;
    Button btnDaftar;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //instansiasi
        edtEmail = findViewById(R.id.edtRegEmail);
        edtName = findViewById(R.id.edtRegName);
        edtPassword = findViewById(R.id.edtRegPassword);
        edtPin = findViewById(R.id.edtRegPin);
        edtUsername = findViewById(R.id.edtRegUsername);

        btnDaftar = (Button)findViewById(R.id.btnSignUp123);
        context = this;

        btnDaftar.setOnClickListener(this);
    }

    @Override
    public int getLayoutResourceID() {
        return R.layout.activity_sign_up;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp123 :
                nama = edtName.getText().toString();
                username = edtUsername.getText().toString();
                password = edtPassword.getText().toString();
                email = edtEmail.getText().toString();
                pin = edtPin.getText().toString();

                if(isValidated()) {
                    addUser();
                } else {
                    Toast.makeText(this, "Pendaftaran gagal. Silakan coba lagi", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public boolean isValidated() {
        boolean status = false;
        if ((!username.matches("") && !nama.matches("") && !password.matches("") &&
                !pin.matches("") && !email.matches("")) &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                status = true;
        }
        else {
            status = false;
        }
        return status;
    }

    public void addUser() {
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean status = false;
                String uname = "", Email = "";
                listUser = (ArrayList) dataSnapshot.getValue();
                for (int i=0; i<listUser.size(); i++) {
                    Map mapUser = (Map) listUser.get(i);
                    uname += mapUser.get("username");
                    Email += mapUser.get("email");
                    if (uname.equals(username) || Email.equals(email)) {
                        status = false;
                        Toast.makeText(context, "Pendaftaran Gagal. Username atau email telah terdaftar", Toast.LENGTH_LONG).show();
                        break;
                    } else {
                        status = true;
                        uname = "";
                        Email = "";
                    }
                }
                if(status) {
                    Map data = new HashMap();
                    data.put("email", email);
                    data.put("name", nama);
                    data.put("password", password);
                    data.put("pin", pin);
                    data.put("saldo", 0);
                    data.put("username", username);
                    listUser.add(data);
                    user.setValue(listUser);
                    Toast.makeText(context, "Pendaftaran berhasil!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
