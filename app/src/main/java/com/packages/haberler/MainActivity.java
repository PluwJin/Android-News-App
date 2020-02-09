package com.packages.haberler;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button b1,b2,b3,b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("MyNotifications","MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("general");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //-------------------------

        b1 = (Button)findViewById(R.id.bid_gundem);
        b1.setOnClickListener(new View.OnClickListener() {



            public void onClick(View v) {
                HaberList.kategori=1;
                HaberList.random=false;
                Intent intocan = new Intent(MainActivity.this, HaberList.class);
                startActivity(intocan);
                Toast.makeText(v.getContext(),
                        "Gündem haber başlıkları açılıyor...", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        b2 = (Button)findViewById(R.id.bid_ekonomi);
        b2.setOnClickListener(new View.OnClickListener() {



            public void onClick(View v) {
                HaberList.kategori=2;
                HaberList.random=false;
                Intent intocan = new Intent(MainActivity.this, HaberList.class);
                startActivity(intocan);
                Toast.makeText(v.getContext(),
                        "Ekonomi haber başlıkları açılıyor...", Toast.LENGTH_SHORT)
                        .show()
                ;

            }
        });

        b3 = (Button)findViewById(R.id.bid_egitim);
        b3.setOnClickListener(new View.OnClickListener() {



            public void onClick(View v) {
                HaberList.kategori=3;
                HaberList.random=false;
                Intent intocan = new Intent(MainActivity.this, HaberList.class);
                startActivity(intocan);
                Toast.makeText(v.getContext(),
                        "Eğitim haber başlıkları açılıyor...", Toast.LENGTH_SHORT)
                        .show();

            }
        });

        b4 = (Button)findViewById(R.id.bid_spor);
        b4.setOnClickListener(new View.OnClickListener() {



            public void onClick(View v) {
                HaberList.kategori=4;
                HaberList.random=false;
                Intent intocan = new Intent(MainActivity.this, HaberList.class);
                startActivity(intocan);
                Toast.makeText(v.getContext(),
                        "Spor haber başlıkları açılıyor...", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        //------------------------------
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_hepsi) {
            // Handle the camera action
            HaberList.random=false;
            HaberList.kategori=0;

            Intent intocan = new Intent(MainActivity.this, HaberList.class);
            startActivity(intocan);


            //finish();
        } else if (id == R.id.nav_kategoriler) {
            HaberList.random=false;
            Intent intocan = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intocan);

        } else if (id == R.id.nav_rastgele) {

            HaberList.random=true;
            Intent intocan = new Intent(MainActivity.this, HaberList.class);
            startActivity(intocan);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }






}












