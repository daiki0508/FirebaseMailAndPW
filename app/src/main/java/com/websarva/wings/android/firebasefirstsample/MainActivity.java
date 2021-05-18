package com.websarva.wings.android.firebasefirstsample;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity{
    private boolean flag;
    private EditText mail_e;
    private EditText pass_e;
    private Button execute_b;
    protected static FirebaseAuth mAuth;
    private AuthenticationFlowClass afc;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mail_e = findViewById(R.id.mail_edit);
        pass_e = findViewById(R.id.password_edit);
        execute_b = findViewById(R.id.execute_button);

        afc = new AuthenticationFlowClass(this);

        resultText = findViewById(R.id.result);

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch signUp = findViewById(R.id.signup_switch);
        signUp.setOnCheckedChangeListener(new SignUpSwitchListener());
    }

    public void execute(View view) {
        String mail_str = mail_e.getText().toString();
        String pass_str = pass_e.getText().toString();

        EditCheck(mail_str,pass_str);
    }

    protected void updateUI(FirebaseUser user){
        String email = "Email:" + user.getEmail() + "\n";
        String emailVerified = "Verified:" +  user.isEmailVerified() + "\n";
        String uid = "Uid:" + user.getUid() + "\n";

        String result_str = email + emailVerified + uid;
        resultText.setText(result_str);
    }

    private void EditCheck(String email,String pass){
        if (email.length() > 0 && pass.length() > 0) {
            if (flag){
                afc.CreateUser(email, pass);
            }else {
                afc.SignIn(email, pass);
            }
        } else if (email.length() == 0) {
            Toast.makeText(this, "mailアドレスが入力されていません", Toast.LENGTH_SHORT).show();
            if (pass.length() == 0) {
                Toast.makeText(this, "passwordが入力されていません。", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "passwordが入力されていません。", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options_menu_list,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();

        if (itemId == R.id.signout){
            mAuth.signOut();
            finish();
            overridePendingTransition(0,0);
            startActivity(getIntent());
            overridePendingTransition(0,0);
        }

        return super.onOptionsItemSelected(item);
    }

    private class SignUpSwitchListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton button, boolean isChecked){
            flag = isChecked;
            if (flag){
                execute_b.setText(getString(R.string.signup));
            }else {
                execute_b.setText(getString(R.string.login_button));
            }

        }
    }

    @Override
    public void onStart(){
        super.onStart();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            updateUI(currentUser);
        }
    }
}