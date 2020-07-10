package com.example.findyourlecturer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class PendaftaranUserActivity extends AppCompatActivity {
    EditText mEmail, mNamaLengkap, mPassword, mNoidentitas, mTahunMasuk, mTahunKeluar;
    RadioGroup mPilihUser;
    RadioButton mDosen, mMahasiswa;
    Button mBtSimpan;
    boolean nip, nim;
    public int a;
    FirebaseAuth fAuth;
    String mahasiswa, dosen, refpath, orderChildpath;
    ;
    DatabaseReference ref;
    user userr;
    datadosen user2;
    datamhs user3;
    //    FirebaseDatabase database;
//    ArrayList<String> list;
//    datadosen datadosen;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendaftaran_user);
        a = 0;

        nip = false;
        nim = false;
        //REGISTRASI
        mEmail = findViewById(R.id.editemail);
        mNamaLengkap = findViewById(R.id.editnamalengkap);
        mPassword = findViewById(R.id.editpassword);
        mNoidentitas = findViewById(R.id.editnoidentitas);
        mDosen = findViewById(R.id.rbdosen);
        mMahasiswa = findViewById(R.id.rbmahasiswa);
        mTahunMasuk = findViewById(R.id.edittahunmasuk);
        mTahunKeluar = findViewById(R.id.edittahunkeluar);
        mBtSimpan = findViewById(R.id.btsimpan2);
        fAuth = FirebaseAuth.getInstance();
        mPilihUser = findViewById(R.id.rbpilihuser);
//        list = new ArrayList<>();
//        datadosen = new datadosen();
        final int pilihbt = mPilihUser.getCheckedRadioButtonId();

        FirebaseApp.initializeApp(this);
        user2 = new datadosen();
        user3 = new datamhs();

        if (pilihbt == mDosen.getId()) {
            Toast.makeText(PendaftaranUserActivity.this, "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
        }

        else if (pilihbt == mMahasiswa.getId()) {
            Toast.makeText(PendaftaranUserActivity.this, "bbbbbbbbbbb", Toast.LENGTH_SHORT).show();
        }



        mBtSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String namalengkap = mNamaLengkap.getText().toString().trim();
                final String noidentitas = mNoidentitas.getText().toString().trim();
                final String tahunmasuk = mTahunMasuk.getText().toString().trim();
                final String tahunkeluar = mTahunKeluar.getText().toString().trim();

                if (TextUtils.isEmpty(noidentitas)) {
                    mNoidentitas.setError("Nomor Identitas harus diisi");
                    return;
                }
                if (TextUtils.isEmpty(namalengkap)) {
                    mNamaLengkap.setError("Nama Lengkap harus diisi");
                    return;
                }
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


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                if (pilihbt == mDosen.getId()) {
                    refpath = "DataDosen";
                    orderChildpath = "nip";

                    DatabaseReference refdosen = FirebaseDatabase.getInstance().getReference(refpath);
                    refdosen.orderByChild(orderChildpath).equalTo(noidentitas).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                a = 1;

                                //REGISTRASI DI FIREBASE
                                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                            if (firebaseUser != null && a == 1) {
                                                String user = fAuth.getCurrentUser().getUid();

                                                if (pilihbt == mDosen.getId()) {
                                                    dosen = mDosen.getText().toString();
                                                    DatabaseReference myRef = database.getReference("user").child(dosen).child(user + "/");
                                                    myRef.child("namalengkap").setValue(namalengkap);
                                                    myRef.child("email").setValue(email);
                                                    myRef.child("password").setValue(password);
                                                    myRef.child("noidentitas").setValue(noidentitas);
                                                    myRef.child("key").setValue(user);
                                                    myRef.child("tipeuser").setValue("Dosen");
                                                }
                                                progressDialog = new ProgressDialog(PendaftaranUserActivity.this);
                                                progressDialog.show();
                                                progressDialog.setContentView(R.layout.progress_dialog);
                                                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                                Toast.makeText(PendaftaranUserActivity.this, "User Dibuat", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), ListUserFragmentActivity.class));
                                                finish();
                                            } else {
                                                Toast.makeText(PendaftaranUserActivity.this, "GAGAL" + a, Toast.LENGTH_SHORT).show();
                                                //Toast.makeText(PendaftaranUserActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            Toast.makeText(PendaftaranUserActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                mNoidentitas.setError(null);
//                            Toast.makeText(PendaftaranUserActivity.this, "No Identitas benar", Toast.LENGTH_SHORT).show();
                            } else {
                                a = 0;
                                mNoidentitas.setError("No Identitas salah");
//                            Toast.makeText(PendaftaranUserActivity.this, "No Identitas salah", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else if (pilihbt == mMahasiswa.getId()) {
                    refpath = "DataMhs";
                    orderChildpath = "nim";

                    DatabaseReference refmhs = FirebaseDatabase.getInstance().getReference(refpath);
                    refmhs.orderByChild(orderChildpath).equalTo(noidentitas).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                a = 1;

                                //REGISTRASI DI FIREBASE
                                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                            if (firebaseUser != null && a == 1) {
                                                String user = fAuth.getCurrentUser().getUid();

                                                if (pilihbt == mMahasiswa.getId()) {
                                                    mahasiswa = mMahasiswa.getText().toString();
                                                    DatabaseReference myRef = database.getReference("user").child(mahasiswa).child(user + "/");
                                                    myRef.child("namalengkap").setValue(namalengkap);
                                                    myRef.child("email").setValue(email);
                                                    myRef.child("password").setValue(password);
                                                    myRef.child("noidentitas").setValue(noidentitas);
                                                    myRef.child("tahunmasuk").setValue(tahunmasuk);
                                                    myRef.child("tahunkeluar").setValue(tahunkeluar);
                                                    myRef.child("key").setValue(user);
                                                    myRef.child("tipeuser").setValue("Mahasiswa");
                                                }
                                                progressDialog = new ProgressDialog(PendaftaranUserActivity.this);
                                                progressDialog.show();
                                                progressDialog.setContentView(R.layout.progress_dialog);
                                                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                                Toast.makeText(PendaftaranUserActivity.this, "User Dibuat", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), LoginUserActivity.class));
                                            } else {
                                                Toast.makeText(PendaftaranUserActivity.this, "GAGAL" + a, Toast.LENGTH_SHORT).show();
                                                //Toast.makeText(PendaftaranUserActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            Toast.makeText(PendaftaranUserActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                mNoidentitas.setError(null);
//                            Toast.makeText(PendaftaranUserActivity.this, "No Identitas benar", Toast.LENGTH_SHORT).show();
                            } else {
                                a = 0;
                                mNoidentitas.setError("No Identitas salah");
//                            Toast.makeText(PendaftaranUserActivity.this, "No Identitas salah", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

            }
        });
    }

    public void btlogin(View view) {
        Intent intent = new Intent(PendaftaranUserActivity.this, LoginUserActivity.class);
        startActivity(intent);
    }
}
