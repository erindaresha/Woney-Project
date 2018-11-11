package id.ac.ukdw.woney;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        //pengecekan apakah user sudah pernah login atau belum
        if(sp.getBoolean("isLogin", false)) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        else if(!sp.getBoolean("isLogin", false) && sp.getBoolean("pernahLogin", false)) {
            Intent intent = new Intent(this, UserLoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public int getLayoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {

    }
}
