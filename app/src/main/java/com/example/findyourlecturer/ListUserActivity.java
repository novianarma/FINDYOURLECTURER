package com.example.findyourlecturer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListUserActivity extends AppCompatActivity {
    EditText etcari;
    ListView listviewdosen;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayList<String> listdua;
    ArrayList<String> listtiga;
    ArrayList<String> listempat;
    ArrayList<String> listlima;
    ArrayAdapter <String> adapter;
    user user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        user = new user();
        etcari = (EditText) findViewById(R.id.caridosen2);
        listviewdosen = (ListView) findViewById(R.id.listviewdosen);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("user").child("dosen_dan_mahasiswa");
        list = new ArrayList<>();
        listdua = new ArrayList<>();
        listtiga = new ArrayList<>();
        listempat = new ArrayList<>();
        listlima = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.user_info, R.id.userinfo, list);

        //PENCARIAN
        etcari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ListUserActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //KETIKA ITEM LISTVIEW DI KLIK
        listviewdosen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListUserActivity.this, EditUserActivity.class);
                String nama = list.get(position);
                String email = listdua.get(position);
                String noidentitas = listtiga.get(position);
                String password = listempat.get(position);
                String user = listlima.get(position);

//                Toast.makeText(ListUserActivity.this, "Hasilnyaaa" + noIdentitas + noIdentitasa , Toast.LENGTH_SHORT).show(); //mengecek
                intent.putExtra("nama",nama);
                intent.putExtra("email",email);
                intent.putExtra("noidentitas",noidentitas);
                intent.putExtra("password",password);
                intent.putExtra("user",user);
                startActivity(intent);

            }
        });


        //MENAMPILKAN DATA DARI FIREBASE KE LISTVIEW
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
/*
                    Toast.makeText(ListUserActivity.this, "Hasilnyaaa" + user.getEmail(), Toast.LENGTH_SHORT).show(); //mengecek
*/
                }
                listviewdosen.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void btback1(View view) {
        Intent intent = new Intent(ListUserActivity.this, MenuUtamaAdminActivity.class);
        startActivity(intent);
    }
}
