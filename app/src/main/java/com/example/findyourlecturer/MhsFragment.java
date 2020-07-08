package com.example.findyourlecturer;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class MhsFragment extends Fragment {
    EditText etcari;
    ListView listviewmhs;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayList<String> listdua;
    ArrayList<String> listtiga;
    ArrayList<String> listempat;
    ArrayList<String> listkey;
    ArrayList<String> thnkeluar;
    ArrayAdapter<String> adapter;
    user user;
    Spinner spinner;
    String sort, spin;
    Button btnHapus;
    ProgressDialog progressDialog;

    public MhsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mhs, container, false);

        user = new user();
        etcari = (EditText) view.findViewById(R.id.caridosen);
        listviewmhs = (ListView) view.findViewById(R.id.listviewmhs);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("user").child("Mahasiswa");
        list = new ArrayList<>();
        listdua = new ArrayList<>();
        listtiga = new ArrayList<>();
        listempat = new ArrayList<>();
        listkey = new ArrayList<>();
        thnkeluar = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.user_info, R.id.userinfo, list);
        btnHapus = (Button) view.findViewById(R.id.btn_hapus);

        //SORT YEAR
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        years.add("Tahun");
        for (int i = 2015; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, years);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setAdapter(adapter2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin = parent.getItemAtPosition(position).toString();
                list.clear();
                adapter.notifyDataSetChanged();
                if (spin.equals("Tahun")) {
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    user = ds.getValue(user.class);
                                    list.add(user.getNamalengkap());
                                    listdua.add(user.getEmail());
                                    listtiga.add(user.getNoidentitas());
                                    listempat.add(user.getPassword());
                                    listkey.add(user.getKey());
                                    thnkeluar.add(user.getTahunkeluar());
                                    adapter.notifyDataSetChanged();
                                }
                                listviewmhs.setAdapter(adapter);
                            }
                            else if (!dataSnapshot.exists()) {
                                list.clear();
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                } else {
                    Query query3 = FirebaseDatabase.getInstance().getReference("user").child("Mahasiswa").orderByChild("tahunkeluar").equalTo(spin);
                    query3.addListenerForSingleValueEvent(valueEventListener);
//                    Toast.makeText(parent.getContext(), "menampilkan " + spin, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //PENCARIAN
        etcari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MhsFragment.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //KETIKA ITEM LISTVIEW DI KLIK
        listviewmhs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nama = list.get(position);
                String email = listdua.get(position);
                String noidentitas = listtiga.get(position);
                String password = listempat.get(position);
                String user = "Mahasiswa";
                String nokey = listkey.get(position);
                String thnklr = thnkeluar.get(position);

                Intent i = new Intent(getActivity().getBaseContext(), EditUserActivity.class);
                i.putExtra("nama", nama);
                i.putExtra("noidentitas", noidentitas);
                i.putExtra("email", email);
                i.putExtra("psswrd", password);
                i.putExtra("user", user);
                i.putExtra("key", nokey);
                i.putExtra("tahunkeluar", thnklr);
                getActivity().startActivity(i);
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Perhatian!")
                        .setMessage("Apakah Anda yakin ingin menghapus semua Mahasiswa yang lulus di tahun tersebut?")
                        .setPositiveButton("Ya", null)
                        .setNegativeButton("Batal", null)
                        .show();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user").child("Mahasiswa");
                        Query mahasiswaquery = ref.orderByChild("tahunkeluar").equalTo(spin);
                        mahasiswaquery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot querymsh : dataSnapshot.getChildren()) {
                                    querymsh.getRef().removeValue();
                                    list.clear();
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Toast.makeText(getActivity(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

//                Toast.makeText(getContext().getApplicationContext(), "menampilkan ", Toast.LENGTH_LONG).show();

            }
        });
        return view;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    user = snapshot.getValue(user.class);
                    list.add(user.getNamalengkap());
                    listdua.add(user.getEmail());
                    listtiga.add(user.getNoidentitas());
                    listempat.add(user.getPassword());
                    listkey.add(user.getKey());
                    thnkeluar.add(user.getTahunkeluar());
                    adapter.notifyDataSetChanged();
                }
                listviewmhs.setAdapter(adapter);

            } else if (!dataSnapshot.exists()) {
                list.clear();
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


}
