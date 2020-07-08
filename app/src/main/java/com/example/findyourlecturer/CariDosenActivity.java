package com.example.findyourlecturer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CariDosenActivity extends AppCompatActivity {
    EditText etcari;
    ListView listdosen;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list; //MENAMPUNG DATA USER
    ArrayList<String> listdua;
    ArrayList<String> listkey;
    ArrayList<String> listtgl;
    ArrayAdapter<String> adapter;
    user user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_dosen);

        user = new user();
        etcari = (EditText) findViewById(R.id.caridosen);
        listdosen = (ListView) findViewById(R.id.listdosen);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("user").child("Dosen");
        list = new ArrayList<>();
        listdua = new ArrayList<>();
        listkey = new ArrayList<>();
        listtgl = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.user_info, R.id.userinfo, list); //mengatur bagaimana listview akan tampil

        //PENCARIAN
        etcari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CariDosenActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //KETIKA ITEM LISTVIEW DI KLIK
        listdosen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CariDosenActivity.this, MapsActivity.class);
                String nama = list.get(position);
                String noidentitas = listdua.get(position);
                String nokey = listkey.get(position);
                String tgljam = listtgl.get(position);

                intent.putExtra("key",nokey);
                intent.putExtra("nama",nama);
                intent.putExtra("noidentitas",noidentitas);
                intent.putExtra("tgljam",tgljam);
                startActivity(intent);
            }
        });


        //MEMANGGIL DATA DARI TABEL DOSEN
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    user = ds.getValue(user.class);
                    list.add(user.getNamalengkap());
                    listdua.add(user.getNoidentitas());
                    listkey.add(user.getKey());
                    listtgl.add(user.getTgljam());
//                    Toast.makeText(ListUserActivity.this, "Hasilnyaaa" + user.getEmail(), Toast.LENGTH_SHORT).show(); //mengecek
                }
                listdosen.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void btback(View view) {
        Intent intent = new Intent(CariDosenActivity.this, MenuUtamaUserActivity.class);
        startActivity(intent);
    }

}
