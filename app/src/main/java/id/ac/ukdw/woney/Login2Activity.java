package id.ac.ukdw.woney;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login2Activity extends MasterActivity {
    TextView txtName, txtUsername, txtChangeAccount;
    EditText edtPassword;
    Button btnLogin;
    String password, username, nama;
    boolean isLogin;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nama = sp.getString("nama", null);
        username = sp.getString("username", null);
        password = sp.getString("password", null);
        txtName = findViewById(R.id.txtName);
        txtUsername = findViewById(R.id.Txtusername);
        txtChangeAccount = findViewById(R.id.txtChangeAccount);
        edtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin2);
        mContext = this;
        isLogin = false;

        txtName.setText(nama);
        txtUsername.setText(username);

        btnLogin.setOnClickListener(this);
        txtChangeAccount.setOnClickListener(this);
    }

    @Override
    public int getLayoutResourceID() {
        return R.layout.activity_login2;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin2 :
                login();
                break;
            case R.id.txtChangeAccount :
                spEdit.clear();
                spEdit.commit();
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void login() {
        String pass = edtPassword.getText().toString();
        if (pass.equals(password)) {
            spEdit.putBoolean("isLogin", true);
            spEdit.apply();
            Toast.makeText(mContext, "Login Berhasil", Toast.LENGTH_SHORT).show();
            Intent homeIntent = new Intent(mContext, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        } else {
            Toast.makeText(mContext, "Login Gagal", Toast.LENGTH_SHORT).show();
        }
    }
}
