package com.example.findyourlecturer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSuggestion;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thanosfisherman.wifiutils.WifiUtils;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static com.example.findyourlecturer.R.layout.activity_login_user;
import static com.example.findyourlecturer.R.layout.activity_menu_utama_user;

public class MenuUtamaUserActivity extends AppCompatActivity {
    Button btn;
    TextView tvnama, tvno, tvemail;

    int position;
    ArrayList<String> panggilnama;
    ArrayList<String> panggilno;
    ArrayList<String> panggilemail;

    private Bundle savedInstanceState;
    FirebaseAuth fAuth;
    public int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(activity_menu_utama_user);
        if (session.getstatususer(getBaseContext()).equals("Dosen")){
            Intent intent = new Intent(MenuUtamaUserActivity.this, MyService.class);
            intent.putExtra("sleepTime", 2);
            startService(intent);

        }

/*
        Intent service = new Intent(MenuUtamaUserActivity.this, LoginUserActivity.class);
        startService(service);
        session.getService(getBaseContext(), a);
        finish();
*/

        panggilnama = new ArrayList<>();
        panggilno = new ArrayList<>();
        panggilemail = new ArrayList<>();

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("my action");
        registerReceiver(myBroadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myBroadcastReceiver);
    }

    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int cycleOrder = intent.getIntExtra("data", 1);
            Log.e("aa", "Cycle Order :" + cycleOrder);
//            Toast.makeText(MenuUtamaUserActivity.this, "Cycle Order :" + cycleOrder, Toast.LENGTH_SHORT).show();
/*
            if (!intent.getAction().equals(
                    WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION)) {
                return;
            }
            // do post connect processing here...
*/
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected void onStart() {
        super.onStart();
    }

    public void btcaridosen(View view) {
        Intent intent = new Intent(MenuUtamaUserActivity.this, CariDosenActivity.class);
        startActivity(intent);
    }

    public void bteditdata(View view) {
        Intent intent = new Intent(MenuUtamaUserActivity.this, EditProfilUserActivity.class);
        startActivity(intent);
    }

    public void btlogout1(View view) {
        AlertDialog dialog = new AlertDialog.Builder(MenuUtamaUserActivity.this)
                .setTitle("Perhatian!")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Keluar", null)
                .setNegativeButton("Batal", null)
                .show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MenuUtamaUserActivity.this, LoginUserActivity.class);
                startActivity(intent);
                session.setLoggedInStatusUser(getBaseContext(), false);
                finish();

                dialog.dismiss();
            }
        });
    }
}
