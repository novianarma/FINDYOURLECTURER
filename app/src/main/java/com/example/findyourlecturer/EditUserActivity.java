package com.example.findyourlecturer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditUserActivity extends AppCompatActivity {
    EditText editemail, editnama, editpsswrd, editno;
    RadioGroup rguser;
    RadioButton rbdosen, rbmhsswa;
    Button bteditt, bthapus;
    String kunci;
    FirebaseAuth fAuth;
    //    String mahasiswa,dosen;
    user user;
    FirebaseDatabase database;
    String email;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        editnama = (EditText) findViewById(R.id.editnamalengkap);
        editemail = (EditText) findViewById(R.id.editemail);
        editno = (EditText) findViewById(R.id.editnoidentitas);
        editpsswrd = (EditText) findViewById(R.id.editpassword);
        rguser = (RadioGroup) findViewById(R.id.rbpilihuser);
        rbdosen = (RadioButton) findViewById(R.id.rbdosen);
        rbmhsswa = (RadioButton) findViewById(R.id.rbmahasiswa);
        bteditt = (Button) findViewById(R.id.btedit);
        bthapus = (Button) findViewById(R.id.bthapus);

        Intent intent= getIntent();
        String noidentitas = intent.getStringExtra("noidentitas");
//        editno.setText(noidentitas);
        String nama = intent.getStringExtra("nama");
        editnama.setText(nama);
        String email = intent.getStringExtra("email");
//        editemail.setText(email);
        String psswrd = intent.getStringExtra("psswrd");
//        editpsswrd.setText(psswrd);
        String pilihuser = intent.getStringExtra("user");
        Query query3 = FirebaseDatabase.getInstance().getReference("user").child("Mahasiswa").orderByChild("namalengkap").equalTo(nama);
        query3.addListenerForSingleValueEvent(valueEventListener);

        if(pilihuser.equals("Dosen"))
        {
            rbdosen.setChecked(true);
            rbmhsswa.setChecked(false);
        }
        else if(pilihuser.equals("Mahasiswa"))
        {
            rbdosen.setChecked(false);
            rbmhsswa.setChecked(true);
        }
    }


    public void btback3(View view) {
        Intent intent = new Intent(EditUserActivity.this, ListUserFragmentActivity.class);
        startActivity(intent);
    }

    public void bthapus(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential("email", "password");

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent= getIntent();
                            String key = intent.getStringExtra("key");
                            kunci = key;

                            String pilihuser = intent.getStringExtra("user");
                            if(pilihuser.equals("Dosen")) {
                                DatabaseReference refdosen = FirebaseDatabase.getInstance().getReference("user").child("Dosen").child(kunci);

                                refdosen.removeValue();
                                Toast.makeText(EditUserActivity.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), ListUserFragmentActivity.class));
                            }
                            else if(pilihuser.equals("Mahasiswa")) {
                                DatabaseReference refdosen = FirebaseDatabase.getInstance().getReference("user").child("Mahasiswa").child(kunci);

                                refdosen.removeValue();
                                Toast.makeText(EditUserActivity.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), ListUserFragmentActivity.class));
                            }
                        }
                    }
                });
            }
        });
        progressDialog = new ProgressDialog(EditUserActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                user = snapshot.getValue(user.class);
                editemail.setText(user.getEmail());
                editpsswrd.setText(user.getPassword());
                editno.setText(user.getNoidentitas());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}