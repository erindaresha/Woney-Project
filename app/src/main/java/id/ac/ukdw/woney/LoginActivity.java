package id.ac.ukdw.woney;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class LoginActivity extends MasterActivity {

    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private TextView txtCreateUser;
    Context mContext;
    boolean isLogin;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inisialisasi
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtCreateUser = (TextView) findViewById(R.id.txtCreateUser);
        mContext = this;

        //pengecekan apakah user sudah login, dan pengecekan apakah user sudah pernah login atau belum
        if(sp.getBoolean("isLogin", false)) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        else if(!sp.getBoolean("isLogin", false) && sp.getBoolean("pernahLogin", false)) {
            Intent intent = new Intent(this, Login2Activity.class);
            startActivity(intent);
            finish();
        }

        //set listener
        btnLogin.setOnClickListener(this);
        txtCreateUser.setOnClickListener(this);
    }

    @Override
    public int getLayoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin :
                /*login();*/
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.txtCreateUser :
                daftar();
                break;
        }
    }

    private void login() {
        username = txtUsername.getText().toString();
        password = txtPassword.getText().toString();
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listUser = (ArrayList) dataSnapshot.getValue();
                String uname = "", pass = "", email = "", name = "", pin = "", saldo = "";
                for (int i=0; i<listUser.size(); i++) {
                    Map mapUser = (Map) listUser.get(i);
                    uname += mapUser.get("username");
                    pass += mapUser.get("password");
                    name += mapUser.get("name");
                    email += mapUser.get("email");
                    pin += mapUser.get("pin");
                    saldo += mapUser.get("saldo");
                    if (uname.equals(username) && pass.equals(password)) {
                        isLogin = true;
                        spEdit.putBoolean("isLogin", true);
                        spEdit.putBoolean("pernahLogin", true);
                        spEdit.putString("username", username);
                        spEdit.putString("nama", name);
                        spEdit.putString("password", password);
                        spEdit.putString("pin", pin);
                        spEdit.putString("email", email);
                        Float f = Float.parseFloat(saldo);
                        spEdit.putFloat("saldo", f);
                        spEdit.apply();
                        Toast.makeText(mContext, "Login Berhasil", Toast.LENGTH_SHORT).show();
                        Intent homeIntent = new Intent(mContext, HomeActivity.class);
                        startActivity(homeIntent);
                        finish();
                        break;
                    } else {
                        isLogin = false;
                        Toast.makeText(mContext, "Login Gagal", Toast.LENGTH_SHORT).show();
                        uname = "";
                        pass = "";
                        pin = "";
                        name = "";
                        email = "";
                        saldo = "";
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void daftar() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
}
