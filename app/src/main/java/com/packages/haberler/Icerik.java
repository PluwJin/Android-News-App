package com.packages.haberler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Icerik extends AppCompatActivity {


    static String[][][] Icerikler = new String[5][50][10] ;
    static int kategori=0, baslik=0, i=0;
    static Boolean random=false;
    static String isLike="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icerik);
        Get();
    }
    private void Get(){

        String urlHaber="http://192.168.1.35/WebService/Save.php";  //Change this
        urlHaber+="?newsId="+Icerikler[kategori][baslik][0];
        System.out.println(urlHaber);

        new Recevier(Icerik.this, urlHaber, new Recevier.OnTaskDoneListener() {
            @Override
            public void onTaskDone(String responseData) {
                System.out.println("Haber id ile cekildi" );
                System.out.println("iiiiiiii.   >" + responseData);
                JSONArray jsonarray = new JSONArray();
                try {
                    jsonarray = new JSONArray(responseData);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for( i=0; i<jsonarray.length(); i++) {
                    JSONObject obj = null;
                    System.out.println("..................>" + jsonarray);
                    try {
                        obj = jsonarray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String view = null;
                    try {
                        view = obj.getString("views");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String likes = null;
                    try {
                        likes = obj.getString("like");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String dislikes = null;
                    try {
                        dislikes = obj.getString("dislike");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String liked = null;
                    try {

                        liked = obj.getString("liked");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        Icerikler[kategori][baslik][1] = obj.getString("name");
                        Icerikler[kategori][baslik][2] = obj.getString("type");
                        Icerikler[kategori][baslik][3] = obj.getString("content");
                        Icerikler[kategori][baslik][4] = obj.getString("image");
                        Icerikler[kategori][baslik][5] = obj.getString("date");
                    }catch (Exception e){
                        System.out.println("hata var");
                    }
                    Icerikler[kategori][baslik][6]=likes;
                    Icerikler[kategori][baslik][7]=dislikes;
                    Icerikler[kategori][baslik][8]=view;
                    if(liked==null)
                        Icerikler[kategori][baslik][9]="null";
                    else
                        Icerikler[kategori][baslik][9]=liked;

                    System.out.println(liked);
                    for(int i=0;i<10;i++)
                    System.out.println(Icerikler[kategori][baslik][i]);


                }

                isLike=Icerikler[kategori][baslik][9];
                System.out.println("Get içerisi: "+isLike);
                ciz();
            }
            @Override
            public void onError() {
                System.out.println("HATA : Haber id ile cekilemedi");
            }}).execute();
    }
    private void Put(String id, final String likeParam){
        new Changer(Icerik.this,id+","+likeParam, new Changer.OnTaskDoneListener() {
            @Override
            public void onTaskDone(String responseData) {
                System.out.println("Bağlanıldı" );
                System.out.println("===> "+responseData);
                if(likeParam.equals(isLike) && responseData.contains("True")){
                    if(likeParam.equals("like"))
                        Icerikler[kategori][baslik][6]=String.valueOf(Integer.parseInt(Icerikler[kategori][baslik][6])-1);
                    else
                        Icerikler[kategori][baslik][7]=String.valueOf(Integer.parseInt(Icerikler[kategori][baslik][7])-1);
                    isLike="null";
                }
                else if(!isLike.equals("null")  ){
                    if(likeParam.equals("like")){
                        Icerikler[kategori][baslik][6]=String.valueOf(Integer.parseInt(Icerikler[kategori][baslik][6])+1);
                        Icerikler[kategori][baslik][7]=String.valueOf(Integer.parseInt(Icerikler[kategori][baslik][7])-1);
                    }
                    else{
                        Icerikler[kategori][baslik][7]=String.valueOf(Integer.parseInt(Icerikler[kategori][baslik][7])+1);
                        Icerikler[kategori][baslik][6]=String.valueOf(Integer.parseInt(Icerikler[kategori][baslik][6])-1);
                    }
                    isLike=likeParam;
                }
                else{
                    if(likeParam.equals("like")){
                        Icerikler[kategori][baslik][6]=String.valueOf(Integer.parseInt(Icerikler[kategori][baslik][6])+1);
                    }
                    else{
                        Icerikler[kategori][baslik][7]=String.valueOf(Integer.parseInt(Icerikler[kategori][baslik][7])+1);
                    }
                    isLike=likeParam;
                }
                ciz();
            }
            @Override
            public void onError() {
                System.out.println("HATA : Bağlanılamadı");
            }}).execute();
    }
    private void ciz(){
        ConstraintLayout LayoutBilgiler = (ConstraintLayout) findViewById(R.id.LayoutBilgiler);
        LayoutBilgiler.setVisibility(View.VISIBLE);
        ProgressBar prb2 = (ProgressBar) findViewById(R.id.progressBar2) ;
        prb2.setVisibility(View.GONE);
        ImageView Res = (ImageView) findViewById(R.id.app_bar_image);
        byte[] decodeString = Base64.decode(Icerikler[kategori][baslik][4],Base64.DEFAULT);
        Bitmap decoded = BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);
        Res.setImageBitmap(decoded);

        TextView Baslik = (TextView) findViewById(R.id.textViewBaslik);
        Baslik.setText(Icerikler[kategori][baslik][1]);

        TextView Icer = (TextView) findViewById(R.id.textViewIcerik);
        Icer.setText(Icerikler[kategori][baslik][3]);

        TextView Tarih = (TextView) findViewById(R.id.textViewTarih);
        Tarih.setText(Icerikler[kategori][baslik][5]);

        TextView Views = (TextView) findViewById(R.id.textViewGoruntulenme);
        Views.setText(String.valueOf(Integer.parseInt(Icerikler[kategori][baslik][8])+1));

        TextView tlike = (TextView) findViewById(R.id.textViewlike_sayi);
        tlike.setText(String.valueOf(Icerikler[kategori][baslik][6]));

        TextView tdislike = (TextView) findViewById(R.id.textViewdislike_sayi);
        tdislike.setText(String.valueOf(Icerikler[kategori][baslik][7]));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_b_begen);
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.floating_b_begenme);

        if(isLike.equals("like")){
            System.out.println("Like a boyandı: "+isLike);
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorNew)));
            fab2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorDeactive)));
        }
        else if(isLike.equals("dislike")){
            System.out.println("Disike a boyandı: "+isLike);
            fab2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorNew)));
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorDeactive)));
        }
        else{
            System.out.println("Null e boyandı: "+isLike);
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorNew)));
            fab2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorNew)));
        }
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(isLike.equals("dislike") || isLike.equals("null"))
                    Snackbar.make(view, "Haber beğenildi", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                if(isLike.equals("like"))
                    Snackbar.make(view, "Beğenme geri alındı", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                Put(Icerikler[kategori][baslik][0],"like");
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(isLike.equals("like") || isLike.equals("null"))
                    Snackbar.make(view, "Haber beğenilmedi", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else if(isLike.equals("dislike"))
                    Snackbar.make(view, "Beğenmeme geri alındı", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                Put(Icerikler[kategori][baslik][0],"dislike");
            }
        });
    }
    @Override
    public void onBackPressed() {
       if(random){
           Intent intocan = new Intent(this, MainActivity.class);
           startActivity(intocan);
       }
       else{
           Intent intocan = new Intent(this, HaberList.class);
           startActivity(intocan);
       }
    }
}
