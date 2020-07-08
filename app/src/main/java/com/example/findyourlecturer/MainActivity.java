package com.example.findyourlecturer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSuggestion;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.thanosfisherman.wifiutils.WifiUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 555);
        if (session.getLoggedInStatusUser(getBaseContext())) {
            Intent intent = new Intent(MainActivity.this, MenuUtamaUserActivity.class);
            MainActivity.this.startActivity(intent);
            finish();
        }

/*
        Intent intent = new Intent(MainActivity.this,MyService.class);
//        intent.putExtra("sleepTime", 2);
        startService(intent);
*/
    }

/*
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
*/
/*
            Intent i = new Intent(MainActivity.this, WifiReceiver.class);
            i.putExtra("recive", 1);
            startActivity(i);
            Toast.makeText(MainActivity.this, "coba broadcast",Toast.LENGTH_SHORT).show();
*//*


            int cycleOrder = intent.getIntExtra("data", 1);
            Log.e("aa", "Cycle Order :" + cycleOrder);
            Toast.makeText(MainActivity.this, "Cycle Order :" + cycleOrder, Toast.LENGTH_SHORT).show();

        }
    };
*/

    //PINDAH ACTIVITY
    public void btadmin(View view) {
        Intent intent = new Intent(MainActivity.this, LoginAdminActivity.class);
        startActivity(intent);
    }

    public void btuser(View view) {
        Intent intent = new Intent(MainActivity.this, LoginUserActivity.class);
        startActivity(intent);
    }

/*
    protected void onStart(){
        super.onStart();
        Toast.makeText(this, "sedang menyambungkan wifi...", Toast.LENGTH_SHORT).show();
    }
*/
}
