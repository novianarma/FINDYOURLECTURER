package com.example.findyourlecturer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListUserFragmentActivity extends FragmentActivity implements View.OnClickListener {
    Button btDosen, btMhs;
    DosenFragment dosen;
    MhsFragment mhsswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_fragment);

        btDosen = (Button) findViewById(R.id.btdosen);
        btMhs = (Button) findViewById(R.id.btmhs);
        btDosen.setOnClickListener(this);
        btMhs.setOnClickListener(this);
    }

    void mDosen(){
        dosen = new DosenFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.container, dosen);
        ft.commit();
    }
    void mMhs(){
        mhsswa = new MhsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.container, mhsswa);
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        if(v == btDosen){
            mDosen();
        }
        if(v == btMhs){
            mMhs();
        }
    }

    public void btback1(View view) {
        Intent intent = new Intent(ListUserFragmentActivity.this, MenuUtamaAdminActivity.class);
        startActivity(intent);

    }
}
