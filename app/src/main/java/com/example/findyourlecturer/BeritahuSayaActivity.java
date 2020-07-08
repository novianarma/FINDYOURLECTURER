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

public class BeritahuSayaActivity extends AppCompatActivity {
    Button btsimpan;
    EditText etcari;
    ListView listdosen;
    FirebaseDatabase database;
    DatabaseReference ref, refmhs, refcount;
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
//    int click;
    FirebaseAuth fAuth;
//    DataSnapshot dataSnapshot;


    public static final String CHANNEL_ID = "simplified_coding";
    private static final String CHANNEL_NAME = "Simplified Coding";
    private static final String CHANNEL_DESC = "Simplified Coding Notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beritahu_saya);

        //Handling Clicks
//        NotificationHelper.displayNotification(this, "title", "body");

        fAuth = FirebaseAuth.getInstance();

        //subscribing to topic
        FirebaseMessaging.getInstance().subscribeToTopic("update");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        user = new user();
        btsimpan = (Button) findViewById(R.id.btsimpan3);
        etcari = (EditText) findViewById(R.id.caridosen2);
        listdosen = (ListView) findViewById(R.id.listdosen);
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


/*
        //TOMBOL SIMPAN NOTIF
        btsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Dosen yang anda cari berada di area kampus! Buka aplikasi untuk mengetahui leberadaannya.";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(BeritahuSayaActivity.this)
                        .setSmallIcon(R.drawable.ic_message)
                        .setContentTitle("Find Your Lecturer")
                        .setContentText(message)
                        .setAutoCancel(true);
                Intent intent = new Intent(BeritahuSayaActivity.this, NotifActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("message", message);

                PendingIntent pendingIntent = PendingIntent.getActivity(BeritahuSayaActivity.this, 0,intent,PendingIntent. FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, builder.build());
            }
        });
*/

        //PENCARIAN
        etcari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                BeritahuSayaActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //MENAMPILKAN NAMA DOSEN DI LIST
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
                    listtoken.add(user.getToken());
                    listpencari.add(user.getPencari());
//                Toast.makeText(BeritahuSayaActivity.this, "Hasilnyaaa" + user.getKey(), Toast.LENGTH_SHORT).show(); //mengecek
                }
                listdosen.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //KETIKA NAMA DOSEN DI KLIK
        listdosen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nama = list.get(position);
                email = listdua.get(position);
                noidentitas = listtiga.get(position);
                password = listempat.get(position);
                userr = listlima.get(position);
                nokey = listkey.get(position);
                token = listtoken.get(position);
                pencari = listpencari.get(position);

            }
        });

        //TOMBOL SIMPAN NOTIF
        btsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= getIntent();
                startActivity(new Intent(getApplicationContext(), BeritahuSayaActivity.class));

                intent.putExtra("nama",nama);
                intent.putExtra("email",email);
                intent.putExtra("noidentitas",noidentitas);
                intent.putExtra("password",password);
                intent.putExtra("user",userr);
                intent.putExtra("key",nokey);
                intent.putExtra("token",token);
                intent.putExtra("pencari",pencari);

                simpan = intent.getStringExtra("key");

                //simpan uid pencari yg dicari
                String user = fAuth.getCurrentUser().getUid();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("user").child("Dosen").child(simpan).child("notif").child(user).child("ssid");
                myRef.setValue("");

                //SAVE TOKEN
                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if(task.isSuccessful()){
                            String token = task.getResult().getToken();
                            saveToken(token);
                        } else{
//                            Toast.makeText(BeritahuSayaActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    //SAVE TOKEN
    private void saveToken(String token) {
        String user = fAuth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user").child("Mahasiswa").child(user + "/token");
        myRef.setValue(token);
        DatabaseReference myRefdosen = database.getReference("user").child("Dosen").child(user + "/token");
        myRefdosen.setValue(token);

//        Toast.makeText(BeritahuSayaActivity.this, "token tersimpan", Toast.LENGTH_SHORT).show();
    }

    public void btback(View view) {
        Intent intent = new Intent(BeritahuSayaActivity.this, MenuUtamaUserActivity.class);
        startActivity(intent);
    }

    public void btsimpan3(View view) {

    }

    public void btberhenti(View view) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
