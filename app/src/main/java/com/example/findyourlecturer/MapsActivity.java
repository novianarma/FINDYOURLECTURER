package com.example.findyourlecturer;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findyourlecturer.Model.ShowDosen;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    TextView tnama, tno, tlastlocation, ttgljam;
    private GoogleMap mMap;
    FirebaseDatabase database;
    DatabaseReference ref;
    String kunci = "1212";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        database = FirebaseDatabase.getInstance();
        tno = (TextView) findViewById(R.id.tvnip);
        tnama = (TextView) findViewById(R.id.tvnamadosen);
        tlastlocation = (TextView) findViewById(R.id.tvlokasiterakhir);
        ttgljam = (TextView) findViewById(R.id.tvtgljam);


        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null) {
            String nama = (String) b.get("nama");
            String no = (String) b.get("noidentitas");
            String tgljam = (String) b.get("tgljam");
            String key = (String) b.get("key");

            tnama.setText(nama);
            tno.setText(no);
            ttgljam.setText(tgljam);
            kunci = key;
//            Toast.makeText(MapsActivity.this, "Hasilnyaaa" + key, Toast.LENGTH_SHORT).show(); //mengecek
        }
        ref = database.getReference("user").child("Dosen").child(kunci);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ShowDosen user = dataSnapshot.getValue(ShowDosen.class);
                tlastlocation.setText(user.getSsid());
                ttgljam.setText(user.getTgljam());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        // Add a marker
        LatLng Polinema = new LatLng(-7.9466753, 112.6157413);
//        mMap.addMarker(new MarkerOptions().position(Polinema).title("Politeknik Negeri Malang").snippet("Jl. Soekarno Hatta"));

        LatLng gdAH = new LatLng(-7.9470559, 112.6153410);
        mMap.addMarker(new MarkerOptions().position(gdAH).title("Gedung AH").snippet("Politeknik Negeri Malang"));

        LatLng gdAI = new LatLng(-7.9450197, 112.6149327);
        mMap.addMarker(new MarkerOptions().position(gdAI).title("Gedung AI").snippet("Politeknik Negeri Malang"));

        LatLng gdAL = new LatLng(-7.9453182, 112.6145226);
        mMap.addMarker(new MarkerOptions().position(gdAL).title("Gedung AL").snippet("Politeknik Negeri Malang"));

/*
        LatLng gdAG = new LatLng(-7.9468015, 112.6155016);
        mMap.addMarker(new MarkerOptions().position(gdAG).title("Gedung AG").snippet("Politeknik Negeri Malang"));

        LatLng gdAF = new LatLng(-7.9465392, 112.6151399);
        mMap.addMarker(new MarkerOptions().position(gdAF).title("Gedung AF").snippet("Politeknik Negeri Malang"));

        LatLng auper = new LatLng(-7.9451844, 112.6143372);
        mMap.addMarker(new MarkerOptions().position(auper).title("Aula Pertamina").snippet("Politeknik Negeri Malang"));

        LatLng masjidannur = new LatLng(-7.9454082, 112.6153826);
        mMap.addMarker(new MarkerOptions().position(masjidannur).title("Masjid An-Nur").snippet("Politeknik Negeri Malang"));

        LatLng gdAK = new LatLng(-7.9455304, 112.6146675);
        mMap.addMarker(new MarkerOptions().position(gdAK).title("Gedung AK").snippet("Politeknik Negeri Malang"));

        LatLng gdAJ = new LatLng(-7.9456745, 112.6148854);
        mMap.addMarker(new MarkerOptions().position(gdAJ).title("Gedung AJ").snippet("Politeknik Negeri Malang"));

        LatLng gdAE = new LatLng(-7.9462470, 112.6153487);
        mMap.addMarker(new MarkerOptions().position(gdAE).title("Gedung AE").snippet("Politeknik Negeri Malang"));

        LatLng gdAC = new LatLng(-7.9460803, 112.6157581);
        mMap.addMarker(new MarkerOptions().position(gdAC).title("Gedung AC").snippet("Politeknik Negeri Malang"));

        LatLng gdAB = new LatLng(-7.9462612, 112.6160324);
        mMap.addMarker(new MarkerOptions().position(gdAB).title("Gedung AB").snippet("Politeknik Negeri Malang"));

        LatLng gdAA = new LatLng(-7.9467650, 112.6159982);
        mMap.addMarker(new MarkerOptions().position(gdAA).title("Gedung AA").snippet("Politeknik Negeri Malang"));

        LatLng grapol = new LatLng(-7.9466703, 112.6169084);
        mMap.addMarker(new MarkerOptions().position(grapol).title("Graha Polinema").snippet("Politeknik Negeri Malang"));

        LatLng grater = new LatLng(-7.9464834, 112.6156408);
        mMap.addMarker(new MarkerOptions().position(grater).title("Graha Theater").snippet("Politeknik Negeri Malang"));

        LatLng gdsipil = new LatLng(-7.9439212, 112.6147395);
        mMap.addMarker(new MarkerOptions().position(gdsipil).title("Gedung Teknik Sipil").snippet("Politeknik Negeri Malang"));

        LatLng gdAW = new LatLng(-7.9476087, 112.6166989);
        mMap.addMarker(new MarkerOptions().position(gdAW).title("Gedung AW").snippet("Politeknik Negeri Malang"));
*/





        //gambar tiap gedung
        //gdAH
        Polyline AHline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-7.9469974, 112.6150564),
                        new LatLng(-7.9468211, 112.6151824),
                        new LatLng(-7.9471273, 112.6156118),
                        new LatLng(-7.9473032, 112.6154865),
                        new LatLng(-7.9469974, 112.6150564)));
        AHline.setTag("Gedung AH");

        //gdAG
        Polyline AGline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-7.9467713, 112.6152720),
                        new LatLng(-7.9466039, 112.6153916),
                        new LatLng(-7.9468579, 112.6157534),
                        new LatLng(-7.9470253, 112.6156317),
                        new LatLng(-7.9467713, 112.6152720)));
        AGline.setTag("Gedung AG");

        //gdAF
        Polyline AFline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-7.9465106, 112.6149132),
                        new LatLng(-7.9463462, 112.6150376),
                        new LatLng(-7.9465863, 112.6153628),
                        new LatLng(-7.9467504, 112.6152388),
                        new LatLng(-7.9465106, 112.6149132)));
        AFline.setTag("Gedung AF");

        //auper
        Polyline auperline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-7.9453903, 112.6143442),
                        new LatLng(-7.9451342, 112.6145347),
                        new LatLng(-7.9449994, 112.6143384),
                        new LatLng(-7.9452558, 112.6141528),
                        new LatLng(-7.9453903, 112.6143442)));
        auperline.setTag("Aula Pertamina");

        //gdAI
        Polyline AIline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-7.9449629, 112.6146815),
                        new LatLng(-7.9448115, 112.6147902),
                        new LatLng(-7.9449201, 112.6149404),
                        new LatLng(-7.9448507, 112.6149943),
                        new LatLng(-7.9449669, 112.6151462),
                        new LatLng(-7.9450330, 112.6151362),
                        new LatLng(-7.9451293, 112.6150909),
                        new LatLng(-7.9452173, 112.6150168),
                        new LatLng(-7.9449629, 112.6146815)));
        AIline.setTag("Gedung AI");

        //masjid annur
        Polyline annurline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-7.9454653, 112.6152562),
                        new LatLng(-7.9452644, 112.6153947),
                        new LatLng(-7.9453003, 112.6154621),
                        new LatLng(-7.9453497, 112.6154362),
                        new LatLng(-7.9454082, 112.6155355),
                        new LatLng(-7.9454955, 112.6154835),
                        new LatLng(-7.9454726, 112.6154443),
                        new LatLng(-7.9455556, 112.6153896),
                        new LatLng(-7.9454653, 112.6152562)));
        annurline.setTag("Masjid An-Nur");

        //gdAL
        Polyline ALline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-7.9452299, 112.6147050),
                        new LatLng(-7.9451625, 112.6146319),
                        new LatLng(-7.9451170, 112.6145501),
                        new LatLng(-7.9454274, 112.6143164),
                        new LatLng(-7.9454779, 112.6144093),
                        new LatLng(-7.9455530, 112.6144804),
                        new LatLng(-7.9452299, 112.6147050)));
        ALline.setTag("Gedung AL");

        //gdAK
        Polyline AKline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-7.9452329, 112.6147429),
                        new LatLng(-7.9452833, 112.6148384),
                        new LatLng(-7.9453574, 112.6149119),
                        new LatLng(-7.9458096, 112.6145920),
                        new LatLng(-7.9456835, 112.6144173),
                        new LatLng(-7.9452329, 112.6147429)));
        AKline.setTag("Gedung AK");

        //gdAJ
        Polyline AJline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-7.9458508, 112.6146306),
                        new LatLng(-7.9453946, 112.6149588),
                        new LatLng(-7.9454470, 112.6150530),
                        new LatLng(-7.9455181, 112.6151244),
                        new LatLng(-7.9459730, 112.6147955),
                        new LatLng(-7.9459069, 112.6147261),
                        new LatLng(-7.9458508, 112.6146306)));
        AJline.setTag("Gedung AJ");



        //gdAE
        Polyline AEline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-7.9460325, 112.6152428),
                        new LatLng(-7.9462227, 112.6151090),
                        new LatLng(-7.9464678, 112.6154426),
                        new LatLng(-7.9462835, 112.6155804),
                        new LatLng(-7.9460325, 112.6152428)));
        AEline.setTag("Gedung AE");

        //gdAC
        Polyline ACline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-7.9458714, 112.6157806),
                        new LatLng(-7.9461756, 112.6155623),
                        new LatLng(-7.9462911, 112.6157263),
                        new LatLng(-7.9461842, 112.6158044),
                        new LatLng(-7.9461932, 112.6158188),
                        new LatLng(-7.9461158, 112.6158758),
                        new LatLng(-7.9461038, 112.6158624),
                        new LatLng(-7.9459870, 112.6159449),
                        new LatLng(-7.9458714, 112.6157806)));
        ACline.setTag("Gedung AC");

        //gdAB
        Polyline ABline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-7.9461653, 112.6162399),
                        new LatLng(-7.9465070, 112.6159911),
                        new LatLng(-7.9463795, 112.6158104),
                        new LatLng(-7.9462536, 112.6159003),
                        new LatLng(-7.9462397, 112.6158862),
                        new LatLng(-7.9461630, 112.6159462),
                        new LatLng(-7.9461746, 112.6159633),
                        new LatLng(-7.9460401, 112.6160639),
                        new LatLng(-7.9461653, 112.6162399)));
        ABline.setTag("Gedung AB");

        //gdAA
        Polyline AAline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-7.9467822, 112.6157353),
                        new LatLng(-7.9467032, 112.6157913),
                        new LatLng(-7.9466839, 112.6157672),
                        new LatLng(-7.9466112, 112.6158221),
                        new LatLng(-7.9466248, 112.6158436),
                        new LatLng(-7.9465119, 112.6159274),
                        new LatLng(-7.9467128, 112.6162101),
                        new LatLng(-7.9468264, 112.6161306),
                        new LatLng(-7.9468941, 112.6162231),
                        new LatLng(-7.9469735, 112.6161651),
                        new LatLng(-7.9469058, 112.6160723),
                        new LatLng(-7.9469821, 112.6160176),
                        new LatLng(-7.9467822, 112.6157353)));
        AAline.setTag("Gedung AA");

        //grapol
        Polyline grapolline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-7.9470137, 112.6170358),
                        new LatLng(-7.9466501, 112.6164547),
                        new LatLng(-7.9462632, 112.6166784),
                        new LatLng(-7.9464522, 112.6169638),
                        new LatLng(-7.9464782, 112.6172042),
                        new LatLng(-7.9467211, 112.6173010),
                        new LatLng(-7.9468679, 112.6172595),
                        new LatLng(-7.9469602, 112.6171679),
                        new LatLng(-7.9470137, 112.6170358)));
        grapolline.setTag("Graha Polinema");

        //grater
        Polyline graterline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-7.9464558, 112.6154607),
                        new LatLng(-7.9463333, 112.6155496),
                        new LatLng(-7.9463456, 112.6156579),
                        new LatLng(-7.9463582, 112.6157028),
                        new LatLng(-7.9463818, 112.6157383),
                        new LatLng(-7.9464107, 112.6157662),
                        new LatLng(-7.9464538, 112.6157833),
                        new LatLng(-7.9465017, 112.6157916),
                        new LatLng(-7.9465554, 112.6157789),
                        new LatLng(-7.9466043, 112.6157387),
                        new LatLng(-7.9466242, 112.6157018),
                        new LatLng(-7.9466388, 112.6156592),
                        new LatLng(-7.9466388, 112.6156113),
                        new LatLng(-7.9466229, 112.6155704),
                        new LatLng(-7.9465946, 112.6155325),
                        new LatLng(-7.9465631, 112.6155103),
                        new LatLng(-7.9465030, 112.6155030),
                        new LatLng(-7.9464558, 112.6154607)));
        graterline.setTag("Graha Theater");

        //sipil
        Polyline sipilline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-7.9443536, 112.6148270),
                        new LatLng(-7.9440952, 112.6152337),
                        new LatLng(-7.9436961, 112.6149943),
                        new LatLng(-7.9433452, 112.6144271),
                        new LatLng(-7.9438332, 112.6141762),
                        new LatLng(-7.9443536, 112.6148270)));
        sipilline.setTag("Gedung Teknik Sipil");

        //gdAW
        Polyline gdawline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-7.9476231, 112.6165470),
                        new LatLng(-7.9474792, 112.6166439),
                        new LatLng(-7.9475181, 112.6167022),
                        new LatLng(-7.9475045, 112.6167126),
                        new LatLng(-7.9475928, 112.6168427),
                        new LatLng(-7.9477256, 112.6167505),
                        new LatLng(-7.9477050, 112.6167190),
                        new LatLng(-7.9477286, 112.6167009),
                        new LatLng(-7.9476231, 112.6165470)));
        gdawline.setTag("Gedung AW");



        //zoom location
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(Polinema)
                .zoom(18)
                .tilt(30)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition((cameraPosition)));

    }
}
