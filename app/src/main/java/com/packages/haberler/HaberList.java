package com.packages.haberler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DecimalFormat;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;


public class HaberList extends AppCompatActivity {



    String[][][] Icerikler = new String[5][50][10] ;
    String urlHaber="http://192.168.1.35/WebService/Save.php";
    int haber_sayisi=0, i=0;
    static int kategori=0;  //0 hepsi, 1 gündem, 2 ekonomi, 3 eğitim, 4 spor
    boolean empty = true, baglanti =true;
    static boolean random=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        Icerik.random=random;

        if(random){
            Random r=new Random();
            int randomKategori=r.nextInt(5);
            kategori=randomKategori;
        }
        setContentView(R.layout.haberlerlist);

        if(kategori==0)
            urlHaber+="?newsType=All";
        else if(kategori==1)
            urlHaber+="?newsType=Gündem";
        else if(kategori==2)
            urlHaber+="?newsType=Ekonomi";
        else if(kategori==3)
            urlHaber+="?newsType=Eğitim";
        else if(kategori==4)
            urlHaber+="?newsType=Spor";
        Get();
    }

    public void Get(){
        new Recevier(HaberList.this, urlHaber, new Recevier.OnTaskDoneListener() {
            @Override
            public void onTaskDone(String responseData) {
                System.out.println("Haber baslik verileri alindi+"+ responseData);

                JSONArray jsonarray = new JSONArray();
                if(responseData.length()>3){
                    System.out.println(responseData.length());
                    empty=false;
                    try {
                        jsonarray = new JSONArray(responseData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for( i=0; i<jsonarray.length(); i++){
                        JSONObject obj = null;
                        try {
                            obj = jsonarray.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String id = null;
                        try {
                            id = obj.getString("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String baslik = null;
                        try {
                            baslik = obj.getString("name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String category = null;
                        try {

                            category = obj.getString("type");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String icerik = null;
                        try {
                            icerik = obj.getString("content");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String img = null;
                        try {

                            img = obj.getString("image");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String date = null;
                        try {

                            date = obj.getString("date");
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
                        String views = null;
                        try {

                            views = obj.getString("views");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String liked = null;
                        try {

                            liked = obj.getString("liked");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Icerikler[kategori][i][0]=id;
                        Icerikler[kategori][i][1]=baslik;
                        Icerikler[kategori][i][2]=category;
                        Icerikler[kategori][i][3]=icerik;
                        Icerikler[kategori][i][4]=img;
                        Icerikler[kategori][i][5]=date;
                        Icerikler[kategori][i][6]=likes;
                        Icerikler[kategori][i][7]=dislikes;
                        Icerikler[kategori][i][8]=views;
                        Icerikler[kategori][i][9]=liked;
                        System.out.println("baslik  " + baslik +" i:  "+i);
                        System.out.println("icerik  " + icerik +" i:  "+i);
                    }
                    haber_sayisi=i;
                    System.out.println(haber_sayisi+ "____"+i);

                    if(random){

                        Random r=new Random();
                        int randomBaslik=r.nextInt(haber_sayisi);//[0,haber_sayisi)
                        Icerik.kategori=kategori;
                        Icerik.Icerikler=Icerikler;
                        Icerik.baslik=randomBaslik;
                        Intent icerik_ac = new Intent(HaberList.this, Icerik.class);
                        startActivity(icerik_ac);
                    }
                    else{
                        ciz();
                    }
                }
                else{
                    ciz();
                }
            }
            @Override
            public void onError() {
                baglanti=false;
                System.out.println("HATA : HaberList verileri alinamadi ");
                ciz();
            }
        }).execute();
    }
    private void ciz() {

        ProgressBar loading = (ProgressBar) findViewById(R.id.progressBar);
        loading.setVisibility(View.INVISIBLE);
        if(!empty){
            float width1 = getResources().getDimension(R.dimen.d_100);
            float width2 = getResources().getDimension(R.dimen.d_75);
            float d_baslik_1 = getResources().getDimension(R.dimen.d_20sp);
            float d_baslik_2 = getResources().getDimension(R.dimen.d_baslik2);
            float d_80dp = getResources().getDimension(R.dimen.d_80);
            float d_20dp = getResources().getDimension(R.dimen.d_20);
            float d_15dp = getResources().getDimension(R.dimen.d_15);
            float d_10dp = getResources().getDimension(R.dimen.d_10);
            float d_215dp = getResources().getDimension(R.dimen.d_215);
            float d_250dp = getResources().getDimension(R.dimen.d_250);
            float d_infoy = getResources().getDimension(R.dimen.d_infoy);
            float d_infox = getResources().getDimension(R.dimen.d_infox);

            LinearLayout l1 = (LinearLayout)findViewById(R.id.Linearl1);
            LinearLayout l2 = (LinearLayout)findViewById(R.id.Linearl2);
            LinearLayout l3 = (LinearLayout)findViewById(R.id.Linearl3);
            LinearLayout lOzet = (LinearLayout)findViewById(R.id.LinearOzet);
            LinearLayout lInfo = (LinearLayout)findViewById(R.id.LinearInfo);
            LinearLayout lkategoriicon = (LinearLayout)findViewById(R.id.LinearKategoriicon);

            LinearLayout.LayoutParams lp100 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(int) width1);
            LinearLayout.LayoutParams lp300 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(int) width1);
            LinearLayout.LayoutParams lpresim = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(int) width1);
            LinearLayout.LayoutParams lp2n300 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams lp80 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(int) width1);

            i=0;
            while(i<haber_sayisi){

                Button btn = new Button(this);
                btn.setId(i);
                final int id_ = btn.getId();
                btn.setBackgroundResource(R.drawable.btn9);
                l3.addView(btn, lp100);
                Button btn1 = ((Button) findViewById(id_));
                btn1.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View view) {
                        Icerik.kategori=kategori;
                        Icerik.Icerikler=Icerikler;
                        Icerik.baslik=id_;
                        Intent icerik_ac = new Intent(HaberList.this, Icerik.class);
                        startActivity(icerik_ac);
                        System.out.println(">>>>----->>>>  "+Icerikler[kategori][i][3] + "  i:   " + i);
                        Toast.makeText(view.getContext(),
                                "İçerik açılıyor...", Toast.LENGTH_SHORT)
                                .show();
                    }
                });


                final TextView rowTextView = new TextView(this);
                rowTextView.setSingleLine();
                final TextView rowTextViewOzet = new TextView(this);
                final TextView rowTextViewGoruntulenme = new TextView(this);
                final TextView rowTextViewTarih = new TextView(this);
                final LinearLayout LinearInfo = new LinearLayout(this);

                lInfo.addView(LinearInfo);
                LinearInfo.setOrientation(LinearLayout.HORIZONTAL);
                LinearInfo.setMinimumHeight((int)width1);



                String substring;
                if(Icerikler[kategori][i][3].length()>250)
                    substring=Icerikler[kategori][i][3].substring(0,250);
                else
                    substring=Icerikler[kategori][i][3];


                rowTextView.setText(Icerikler[kategori][i][1] );
                rowTextViewOzet.setText(substring);
                String textViews = Icerikler[kategori][i][8];

                if(Integer.parseInt(textViews)>999)
                    textViews=String.valueOf(Integer.parseInt(textViews)/1000)+" B";

                rowTextViewGoruntulenme.setText(textViews );
                rowTextViewTarih.setText(Icerikler[kategori][i][5] );

                if(Icerikler[kategori][i][1].length()>40)
                    rowTextView.setTextSize(d_baslik_2);
                else
                    rowTextView.setTextSize(d_baslik_1);

                rowTextView.setTextColor(getResources().getColor(R.color.colorHalfBlack));
                rowTextView.setPadding((int)((float) 80),(int)d_215dp,0,0);
                rowTextViewOzet.setPadding((int)d_15dp,(int)d_250dp,(int)d_20dp,(int)d_10dp);
                rowTextViewGoruntulenme.setTextColor(getResources().getColor(R.color.colorWhiteActionbar));
                rowTextViewTarih.setTextColor(getResources().getColor(R.color.colorWhiteActionbar));

                l2.addView(rowTextView,lp80);
                lOzet.addView(rowTextViewOzet,lp300);

                ImageView ImageG = new ImageView(this);
                ImageView ImageIcoDate = new ImageView(this);
                ImageView ImageIcoViews = new ImageView(this);
                ImageView ImageIcoCategory = new ImageView(this);

                ImageIcoDate.setImageResource(R.drawable.date);
                ImageIcoDate.setScaleX((float)0.7);
                ImageIcoDate.setScaleY((float)0.7);

                ImageIcoViews.setImageResource(R.drawable.views);
                ImageIcoViews.setScaleX((float)0.5);
                ImageIcoViews.setScaleY((float)0.5);
                ImageIcoViews.setPivotX((float)930);
                ImageIcoViews.setPivotY((float) 530);

                ImageIcoDate.setImageResource(R.drawable.date);
                lkategoriicon.addView(ImageIcoCategory,lp300);
                if(Icerikler[kategori][i][2].contains("Gündem"))
                    ImageIcoCategory.setImageResource(R.drawable.gundem_mini);
                else if(Icerikler[kategori][i][2].contains("Ekonomi"))
                    ImageIcoCategory.setImageResource(R.drawable.ekonomi_mini);
                else if(Icerikler[kategori][i][2].contains("Eğitim"))
                    ImageIcoCategory.setImageResource(R.drawable.egitim_mini);
                else if(Icerikler[kategori][i][2].contains("Spor"))
                    ImageIcoCategory.setImageResource(R.drawable.spor_mini);

                LinearInfo.addView(ImageIcoDate,lp2n300);
                LinearInfo.addView(rowTextViewTarih,lp2n300);
                LinearInfo.addView(ImageIcoViews,lp2n300);
                LinearInfo.addView(rowTextViewGoruntulenme,lp2n300);

                ImageIcoDate.setPadding((int)((float) 50),(int)((float) 550),0,0);
                rowTextViewTarih.setPadding(0,(int)d_infoy,0,0);
                ImageIcoViews.setPadding((int)((float) 770),(int)((float) 770),0,0);
                rowTextViewGoruntulenme.setPadding((int)((float) 30),(int)d_infoy,0,0);
                ImageIcoCategory.setPadding((int)((float) 30),(int)((float) 620),(int)((float) 1320),0);

                byte[] decodeString = Base64.decode(Icerikler[kategori][i][4],Base64.DEFAULT);
                Bitmap decoded = BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);

                ImageG.setImageBitmap(decoded);

                l1.addView(ImageG,lp300);

                i++;
            }
        }
        else{
            TextView textInfo = (TextView) findViewById(R.id.textViewInfo);
            textInfo.setVisibility(View.VISIBLE);

            if(baglanti){
                float sp20= getResources().getDimension(R.dimen.d_20sp);
                textInfo.setText("Henüz bu kategoride haber eklenmemiş");
                textInfo.setTextSize(sp20);
            }
            else{
                ImageView imgw = (ImageView) findViewById(R.id.imageViewInfo);
                imgw.setVisibility(View.VISIBLE);
                textInfo.setText("Bağlantı yok");
            }

        }





















    }




    @Override
    public void onBackPressed() {

        Intent intocan = new Intent(HaberList.this, MainActivity.class);
        startActivity(intocan);


    }




}