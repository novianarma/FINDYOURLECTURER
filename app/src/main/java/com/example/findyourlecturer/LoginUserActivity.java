package com.example.findyourlecturer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginUserActivity extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mBtLogin;
    CheckBox mShowpass;
    FirebaseAuth fAuth;
    String refpath, orderChildpath;
    public int a;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        mEmail = findViewById(R.id.editEmail);
        mPassword = findViewById(R.id.editPassword);
        mBtLogin = findViewById(R.id.btlogin2);
        mShowpass = findViewById(R.id.showpass1);
        fAuth = FirebaseAuth.getInstance();

        //jika status login = trus, otomatis menuju ke halaman menu utama tidak perlu mengisi password
        if (session.getLoggedInStatusUser(getBaseContext())) {
            Intent intent = new Intent(LoginUserActivity.this, MenuUtamaUserActivity.class);
            LoginUserActivity.this.startActivity(intent);
            finish();
        }

        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email harus diisi");
                    return;
                }
                if (TextUtils.isEmpty((password))) {
                    mPassword.setError("Password harus diisi");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password harus lebih dari 6 karakter");
                    return;
                }

                //AUTENTIKASI USER
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog = new ProgressDialog(LoginUserActivity.this);
                            progressDialog.show();
                            progressDialog.setContentView(R.layout.progress_dialog);
                            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            DatabaseReference refdosen = FirebaseDatabase.getInstance().getReference("user").child("Dosen");
                            refdosen.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        a = 1;
                                        Toast.makeText(LoginUserActivity.this, "Login Dosen Berhasil", Toast.LENGTH_SHORT).show();
                                        session.setstatususer(getBaseContext(),"Dosen");
                                    } else {
                                        a = 2;
                                        Toast.makeText(LoginUserActivity.this, "Login Mahasiswa Berhasil", Toast.LENGTH_SHORT).show();
                                        session.setstatususer(getBaseContext(),"Mahasiswa");
                                    }

                                    startActivity(new Intent(getApplicationContext(), MenuUtamaUserActivity.class));
                                    finish();
                                    session.setLoggedInStatusUser(getBaseContext(), true); //memberikan status login ke true
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        } else {
                            Toast.makeText(LoginUserActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //MENAMPILKAN PASSWORD
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

    public void btregistrasi(View view) {
        Intent intent = new Intent(LoginUserActivity.this, PendaftaranUserActivity.class);
        startActivity(intent);
    }
}
