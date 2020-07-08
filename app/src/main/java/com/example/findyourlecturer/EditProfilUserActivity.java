package com.example.findyourlecturer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfilUserActivity extends AppCompatActivity {
    EditText editemail, editnama, editpsswrd, editno, editthnmasuk, editthnkeluar;
    RadioGroup rguser;
    RadioButton rbdosen, rbmhsswa;
    Button bteditt, bthapus;
    String kunci;
    FirebaseAuth fAuth;
    LinearLayout lnr_tahun;
    //    String mahasiswa,dosen;
    user user;
    FirebaseDatabase database;
    String email;
    ProgressDialog progressDialog;

    public static final String mypreference = "novian";
    session sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session sharedPrefManager;
        setContentView(R.layout.activity_edit_profil_user);
        editnama = (EditText) findViewById(R.id.editnamalengkap);
        editemail = (EditText) findViewById(R.id.editemail);
        editno = (EditText) findViewById(R.id.editnoidentitas);
        editpsswrd = (EditText) findViewById(R.id.editpassword);
        rguser = (RadioGroup) findViewById(R.id.rbpilihuser);
        rbdosen = (RadioButton) findViewById(R.id.rbdosen);
        rbmhsswa = (RadioButton) findViewById(R.id.rbmahasiswa);
        editthnmasuk = (EditText) findViewById(R.id.edittahunmasuk);
        editthnkeluar = (EditText) findViewById(R.id.edittahunkeluar);
        bteditt = (Button) findViewById(R.id.btedit);
        bthapus = (Button) findViewById(R.id.bthapus);
        lnr_tahun = (LinearLayout) findViewById(R.id.tahunmasuk);
//        Toast.makeText(getApplicationContext(), session.getstatususer(getBaseContext()), Toast.LENGTH_SHORT).show();


        fAuth = FirebaseAuth.getInstance();
        String user = fAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user").child(session.getstatususer(getBaseContext())).child(user);
        DatabaseReference tahun = FirebaseDatabase.getInstance().getReference("user").child(session.getstatususer(getBaseContext())).child(user).child("tipeuser");
        tahun.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue().toString();
                if (data.equals("Mahasiswa")){
                    lnr_tahun.setVisibility(View.VISIBLE);
                }
                else {
                    lnr_tahun.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user user = dataSnapshot.getValue(user.class);
                editno.setText(user.getNoidentitas());
                editnama.setText(user.getNamalengkap());
                editemail.setText(user.getEmail());
                editpsswrd.setText(user.getPassword());
                editthnmasuk.setText(user.getTahunmasuk());
                editthnkeluar.setText(user.getTahunkeluar());
                String pilihuser = user.getTipeuser();
                if (pilihuser.equals("Dosen")) {
                    rbdosen.setChecked(true);
                    rbmhsswa.setChecked(false);
                } else if (pilihuser.equals("Mahasiswa")) {
                    rbdosen.setChecked(false);
                    rbmhsswa.setChecked(true);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void bteditt(View view) {
        fAuth = FirebaseAuth.getInstance();
        String user = fAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user").child(session.getstatususer(getBaseContext())).child(user);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("noidentitas").setValue(editno.getText().toString());
                dataSnapshot.getRef().child("namalengkap").setValue(editnama.getText().toString());
                dataSnapshot.getRef().child("email").setValue(editemail.getText().toString());
                dataSnapshot.getRef().child("password").setValue(editpsswrd.getText().toString());
                dataSnapshot.getRef().child("tahunmasuk").setValue(editthnmasuk.getText().toString());
                dataSnapshot.getRef().child("tahunkeluar").setValue(editthnkeluar.getText().toString());
                Toast.makeText(EditProfilUserActivity.this, "Update Data Berhasil", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), EditProfilUserActivity.class));
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential("email", "password");

        fUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                fUser.updateEmail(editemail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
//                            Toast.makeText(EditProfilUserActivity.this, "Update Email Berhasil", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(EditProfilUserActivity.this, "Update Email Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                fUser.updatePassword(editpsswrd.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
//                            Toast.makeText(EditProfilUserActivity.this, "Update Password Berhasil", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(EditProfilUserActivity.this, "Update Password Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        progressDialog = new ProgressDialog(EditProfilUserActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    public void btback3(View view) {
        Intent intent = new Intent(EditProfilUserActivity.this, MenuUtamaUserActivity.class);
        startActivity(intent);
    }
}