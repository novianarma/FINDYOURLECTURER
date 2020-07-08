package com.example.findyourlecturer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HubungiDevActivity extends AppCompatActivity {
    Button btwa, bttelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hubungi_dev);

        btwa = (Button) findViewById(R.id.btwa);
        bttelp = (Button) findViewById(R.id.bttelp);
    }

    public void btwa(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=6289679263655"));
        startActivity(i);
    }

    public void bttelp(View view) {
        final int REQUEST_PHONE_CALL = 1;
        Intent callintent = new Intent (Intent.ACTION_CALL);
        callintent.setData(Uri.parse("tel:081515951662"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            if (ContextCompat.checkSelfPermission(HubungiDevActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(HubungiDevActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            }
            else{
                startActivity(callintent);
            }
        }
    }

    public void btback3(View view) {
        Intent intent = new Intent(HubungiDevActivity.this, MenuUtamaAdminActivity.class);
        startActivity(intent);
    }
}
