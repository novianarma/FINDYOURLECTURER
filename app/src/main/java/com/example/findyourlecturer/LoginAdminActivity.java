package com.example.findyourlecturer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginAdminActivity extends AppCompatActivity {
    EditText mPassword;
    CheckBox mShowpass;
    Button mBtLogin;
    ProgressDialog progressDialog;
    private long backPressedTime;

        session sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        mPassword = findViewById(R.id.editpassword);
        mBtLogin = findViewById(R.id.btlogin);
        mShowpass = findViewById(R.id.showpass);

        //jika status login = trus, otomatis menuju ke halaman menu utama tidak perlu mengisi password
        if (session.getLoggedInStatusAdmin(getBaseContext())) {
            Intent intent = new Intent(LoginAdminActivity.this, MenuUtamaAdminActivity.class);
            LoginAdminActivity.this.startActivity(intent);
            finish();
        }

        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mPassword.getText().toString();
                if (password.equals("adminaplikasi")) {
//                    Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginAdminActivity.this, MenuUtamaAdminActivity.class);
                    LoginAdminActivity.this.startActivity(intent);
                    finish();
                    session.setLoggedInStatusAdmin(getBaseContext(), true); //memberikan status login ke true

                    progressDialog = new ProgressDialog(LoginAdminActivity.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginAdminActivity.this);
                    builder.setMessage("Password Salah!").setNegativeButton("Retry", null).create().show();
                }
            }
        });

        mShowpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShowpass.isChecked()) {
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

/*
    @Override
    public void onBackPressed(){
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }
        else{
            Toast.makeText(getBaseContext(),"Tekan lagi untuk keluar", Toast.LENGTH_SHORT).show();
        }
        backPressedTime=System.currentTimeMillis();
    }
*/

    public void btlogin(View view) {
        Intent intent = new Intent(LoginAdminActivity.this, MenuUtamaAdminActivity.class);
        startActivity(intent);
    }
}
