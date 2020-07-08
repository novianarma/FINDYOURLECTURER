package com.example.findyourlecturer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class KeteranganActivity extends AppCompatActivity {
    TextView tvnama,tvemail,tvnoidentitas,tvpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keterangan);
        tvnama = (TextView) findViewById(R.id.nama);
        tvemail = (TextView) findViewById(R.id.email);
        tvnoidentitas = (TextView) findViewById(R.id.noidentitas);
        tvpassword = (TextView) findViewById(R.id.psswrd);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String nama =(String) b.get("nama");
            String email =(String) b.get("email");
            String noidentitas =(String) b.get("noidentitas");
            String password =(String) b.get("password");
            tvnama.setText(nama);
            tvemail.setText(email);
            tvnoidentitas.setText(noidentitas);
            tvpassword.setText(password);
        }


    }
}
