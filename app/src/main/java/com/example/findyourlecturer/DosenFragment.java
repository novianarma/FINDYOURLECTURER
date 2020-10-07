package com.example.findyourlecturer;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DosenFragment extends Fragment {
    EditText etcari;
    ListView listviewdosen;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayList<String> listdua;
    ArrayList<String> listtiga;
    ArrayList<String> listempat;
    ArrayList<String> listkey;
    ArrayAdapter<String> adapter;
    user user;

    public DosenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dosen, container, false);

        user = new user();
        etcari = (EditText) view.findViewById(R.id.caridosen);
        listviewdosen = (ListView) view.findViewById(R.id.listmhs);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("user").child("Dosen");
        list = new ArrayList<>();
        listdua = new ArrayList<>();
        listtiga = new ArrayList<>();
        listempat = new ArrayList<>();
        listkey = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.user_info, R.id.userinfo, list);

        //PENCARIAN
        etcari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DosenFragment.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //KETIKA ITEM LISTVIEW DI KLIK
        listviewdosen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nama = list.get(position);
                String email = listdua.get(position);
                String noidentitas = listtiga.get(position);
                String password = listempat.get(position);
                String user = "Dosen";
                String nokey = listkey.get(position);

                Intent i = new Intent(getActivity().getBaseContext(), EditUserActivity.class);
                i.putExtra("nama", nama);
                i.putExtra("noidentitas", noidentitas);
                i.putExtra("email", email);
                i.putExtra("psswrd", password);
                i.putExtra("user", user);
                i.putExtra("key",nokey);
//                Toast.makeText(getContext().getApplicationContext(), "Hasilnyaaa" + user , Toast.LENGTH_SHORT).show(); //mengecek

                getActivity().startActivity(i);

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
                    listkey.add(user.getKey());
                }
                listviewdosen.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}