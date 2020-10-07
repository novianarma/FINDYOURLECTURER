package com.example.findyourlecturer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;

public class KecualikanActivity extends AppCompatActivity {
    Button btsimpan;
    EditText etcari;
    ListView listmhs;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayList<String> listdua;
    ArrayList<String> listtiga;
    ArrayList<String> listempat;
    ArrayList<String> listlima;
    ArrayList<String> listkey;
    ArrayList<String> listtoken;
    ArrayList<String> listpencari;
    ArrayAdapter<String> adapter;
    user user;
    String simpan, nama, email, noidentitas, password, userr, nokey, nilai, token, pencari;
    View view;
    long id;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kecualikan);


        fAuth = FirebaseAuth.getInstance();

        user = new user();
        btsimpan = (Button) findViewById(R.id.btsimpan3);
        etcari = (EditText) findViewById(R.id.caridosen2);
        listmhs = (ListView) findViewById(R.id.listmhs);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("user").child("Dosen");
        list = new ArrayList<>();
        listdua = new ArrayList<>();
        listtiga = new ArrayList<>();
        listempat = new ArrayList<>();
        listlima = new ArrayList<>();
        listkey = new ArrayList<>();
        listtoken = new ArrayList<>();
        listpencari = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.user_info, R.id.userinfo, list);


        //PENCARIAN
        etcari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                KecualikanActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        //MENAMPILKAN NAMA MHS DI LIST
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    user = ds.getValue(user.class);
                    list.add(user.getNamalengkap());
                    listdua.add(user.getEmail());
                    listtiga.add(user.getNoidentitas());
                    listempat.add(user.getPassword());
                    listlima.add(user.getUser());
                    listkey.add(user.getKey());
//                Toast.makeText(BeritahuSayaActivity.this, "Hasilnyaaa" + user.getKey(), Toast.LENGTH_SHORT).show(); //mengecek
                }
                listmhs.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //KETIKA NAMA MHS DI KLIK
        listmhs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nama = list.get(position);
                email = listdua.get(position);
                noidentitas = listtiga.get(position);
                password = listempat.get(position);
                userr = listlima.get(position);
                nokey = listkey.get(position);
                token = listtoken.get(position);

            }
        });

        //TOMBOL SIMPAN
        btsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= getIntent();
                startActivity(new Intent(getApplicationContext(), KecualikanActivity.class));

                intent.putExtra("nama",nama);
                intent.putExtra("email",email);
                intent.putExtra("noidentitas",noidentitas);
                intent.putExtra("password",password);
                intent.putExtra("user",userr);
                intent.putExtra("key",nokey);

                simpan = intent.getStringExtra("key");

                //simpan uid pencari yg dicari
                String user = fAuth.getCurrentUser().getUid();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("user").child("Mahasiswa").child(simpan).child(user).child("dikecualikan");
                myRef.setValue("");

            }
        });
    }


    public void btback(View view) {
        Intent intent = new Intent(KecualikanActivity.this, MenuUtamaUserActivity.class);
        startActivity(intent);
    }
}
