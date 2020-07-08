package com.example.findyourlecturer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InputNimNikActivity extends AppCompatActivity {
    EditText mNamaLengkap, mNoidentitas;
    RadioGroup mPilihUser;
    RadioButton mDosen, mMahasiswa;
    Button mBtSimpan;
    String mahasiswa,dosen;
    ProgressDialog progressDialog;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_nim_nik);

        mNoidentitas = findViewById(R.id.editnoidentitas);
        mNamaLengkap = findViewById(R.id.editnamalengkap);
        mBtSimpan = findViewById(R.id.btsimpan2);
        mPilihUser = findViewById(R.id.rbpilihuser);
        mDosen = findViewById(R.id.rbdosen);
        mMahasiswa = findViewById(R.id.rbmahasiswa);

        mBtSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pilihbt = mPilihUser.getCheckedRadioButtonId();
                final String noidentitas = mNoidentitas.getText().toString().trim();
                final String namalengkap = mNamaLengkap.getText().toString().trim();

                if (TextUtils.isEmpty(noidentitas)) {
                    mNoidentitas.setError("No Identitas harus diisi");
                    return;
                }
                if (TextUtils.isEmpty((namalengkap))) {
                    mNamaLengkap.setError("Password harus diisi");
                    return;
                }

                if(pilihbt == mDosen.getId())
                {
                    dosen = mDosen.getText().toString();
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("DataDosen").child(noidentitas + "/");
                    myRef.child("nip").setValue(noidentitas);
                    myRef.child("namalengkap").setValue(namalengkap);
                }
                else if(pilihbt == mMahasiswa.getId())
                {
                    mahasiswa = mMahasiswa.getText().toString();
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("DataMhs").child(noidentitas + "/");
                    myRef.child("nim").setValue(noidentitas);
                    myRef.child("namalengkap").setValue(namalengkap);
                }
                Toast.makeText(InputNimNikActivity.this, "NIM/NIK Ditambahkan", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MenuUtamaAdminActivity.class));

                progressDialog = new ProgressDialog(InputNimNikActivity.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            }
        });
    }

    public void btsimpan(View view) {
    }

    public void btback(View view) {
        Intent intent = new Intent(InputNimNikActivity.this, MenuUtamaAdminActivity.class);
        startActivity(intent);
    }

}
