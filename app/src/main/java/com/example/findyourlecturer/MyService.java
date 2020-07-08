package com.example.findyourlecturer;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MyService extends IntentService {
    TextView mainText;
    WifiManager mainWifi;
    WifiReceiver receiverWifi;
    List<ScanResult> wifiList;
    StringBuilder sb = new StringBuilder();
    private final Handler handler = new Handler();
    String[] wifis;
    FirebaseAuth fAuth;
    public int a;
    String tipeuser, tipe;
    user user;

    public MyService() {

        super("My_Worker_Thread");
    }

    public void onCreate() {
        // Initiate wifi service manager
        mainWifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        // Check for wifi is disabled
        if (mainWifi.isWifiEnabled() == false) {
            // If wifi disabled then enable it
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled",
                    Toast.LENGTH_LONG).show();
            mainWifi.setWifiEnabled(true);
        }
        doInback();
        Log.i(TAG, "onCreate method" + Thread.currentThread().getName() + "thread dipanggil");
        super.onCreate();

        //CONNECT
        wifiList = mainWifi.getScanResults();
        wifis = new String[wifiList.size()];
        for (int i = 0; i < wifiList.size(); i++) {
            wifis[i] = ((wifiList.get(i)).SSID);
            if (wifis != null) {

                if (wifis[i].equals("Gedung AL")) { //WIFI

                    WifiConfiguration wifiConfig = new WifiConfiguration();
                    wifiConfig.SSID = String.format("\"%s\"", wifis[i]);
                    wifiConfig.preSharedKey = String.format("\"%s\"", "polinema"); //PASSWORD

                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                    //remember id
                    int netId = wifiManager.addNetwork(wifiConfig);
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(netId, true);
                    wifiManager.reconnect();

                    //INPUT TO FIREBASE
                    fAuth = FirebaseAuth.getInstance();
                    String user = fAuth.getCurrentUser().getUid();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("user").child("Dosen").child(user + "/ssid");
                    myRef.setValue("Gedung AL Polinema");

                    //MENAMPILKAN DATE&TIME
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yyyy | hh:mm a");
                    String dateTime = simpleDateFormat.format(calendar.getTime());
                    myRef = database.getReference("user").child("Dosen").child(user + "/tgljam");
                    myRef.setValue(dateTime);

                }
                else if (wifis[i].equals("Gedung AH")) { //WIFI

                    WifiConfiguration wifiConfig = new WifiConfiguration();
                    wifiConfig.SSID = String.format("\"%s\"", wifis[i]);
                    wifiConfig.preSharedKey = String.format("\"%s\"", "polinema"); //PASSWORD

                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                    //remember id
                    int netId = wifiManager.addNetwork(wifiConfig);
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(netId, true);
                    wifiManager.reconnect();

                    //INPUT TO FIREBASE
                    fAuth = FirebaseAuth.getInstance();
                    String user = fAuth.getCurrentUser().getUid();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("user").child("Dosen").child(user + "/ssid");
                    myRef.setValue("Gedung AH Polinema");

                    //MENAMPILKAN DATE&TIME
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yyyy | hh:mm a");
                    String dateTime = simpleDateFormat.format(calendar.getTime());
                    myRef = database.getReference("user").child("Dosen").child(user + "/tgljam");
                    myRef.setValue(dateTime);
                }
                else if (wifis[i].equals("Gedung AI")) { //WIFI

                    WifiConfiguration wifiConfig = new WifiConfiguration();
                    wifiConfig.SSID = String.format("\"%s\"", wifis[i]);
                    wifiConfig.preSharedKey = String.format("\"%s\"", "polinema"); //PASSWORD

                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                    //remember id
                    int netId = wifiManager.addNetwork(wifiConfig);
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(netId, true);
                    wifiManager.reconnect();

                    //INPUT TO FIREBASE
                    fAuth = FirebaseAuth.getInstance();
                    String user = fAuth.getCurrentUser().getUid();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("user").child("Dosen").child(user + "/ssid");
                    myRef.setValue("Gedung AI Polinema");

                    //MENAMPILKAN DATE&TIME
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yyyy | hh:mm a");
                    String dateTime = simpleDateFormat.format(calendar.getTime());
                    myRef = database.getReference("user").child("Dosen").child(user + "/tgljam");
                    myRef.setValue(dateTime);
                }
            }
        }
    }

    public void doInback() {
        handler.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    mainWifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

                                    if (receiverWifi == null)
                                        receiverWifi = new WifiReceiver();
                                    registerReceiver(receiverWifi, new IntentFilter(
                                            WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                                    mainWifi.startScan();
                                    doInback();
                                }
                            },
                1000);
//        Toast.makeText(MyService.this, "Starting Scan...", Toast.LENGTH_SHORT).show();


    }


/*
    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Toast.makeText(this,"Service Started...",Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Service Stopped...",Toast.LENGTH_LONG).show();

    }
*/

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
/*
        synchronized (this){
            int count = 0;
            while (count<10){
                try{
                    wait(1500);
                    count++;
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
        doInback();
*/
        Log.i(TAG, "onHandleIntent method" + Thread.currentThread().getName() + "thread dipanggil");
        int sleepTime = intent.getIntExtra("sleepTime", 1);
        int kontrol = 1;
        while (kontrol <= sleepTime) {
            try {
                Thread.sleep(1000);
                Intent intent1 = new Intent("my action");
                intent1.putExtra("data", kontrol);
                sendBroadcast(intent1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            kontrol++;
        }
        doInback();

    }

/*
    public void doInback() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
*/
/*
                Intent intent = new Intent(MyService.this,WifiReceiver.class);
                intent.putExtra("kirim", "kirim");
                startService(intent);
*//*

                // TODO Auto-generated method stub
                mainWifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

                if (receiverWifi == null)
                    receiverWifi = new WifiReceiver();
                registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                mainWifi.startScan();

//                Toast.makeText(MyService.this, "Starting Scan..." + receiverWifi.getResultData(), Toast.LENGTH_SHORT).show();

                doInback();
            }
        }, 1500);
//        Toast.makeText(this, "coba scan wifi....", Toast.LENGTH_SHORT).show();
    }
*/

    // Broadcast receiver class called its receive method
    // when number of wifi connections changed

/*
    class WifiReceiver extends BroadcastReceiver {
        // This method call when number of wifi connections changed
        public void onReceive(Context c, Intent intent) {

            sb = new StringBuilder();
            wifiList = mainWifi.getScanResults();
            sb.append("\n        Number Of Wifi connections :"+wifiList.size()+"\n\n");

            for(int i = 0; i < wifiList.size(); i++){

                sb.append(new Integer(i+1).toString() + ". ");
                sb.append((wifiList.get(i)).toString());
                sb.append("\n\n");
            }

            mainText.setText(sb);
        }

    }
*/

}
