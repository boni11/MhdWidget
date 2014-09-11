package com.example.widgetmhd;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by lukas.stefanko on 7. 9. 2014.
 */
public class GetImhdSpoje extends AsyncTask<String, Intent, Long> {

    RemoteViews remoteViews;
    Context cnt;
    String prefsName;
    int widgetId;

    public GetImhdSpoje(Context context,String prefs, int widgetID){

        cnt=context;
        prefsName=prefs;
        widgetId=widgetID;
    }

    @Override
    protected Long doInBackground(String... strings) {
        parseImhd();
        return null;
    }


    protected void onPostExecute(Long result){
        //Log.d("WIDGET - ONPOSTEXECUTE - PREFS",prefsName);
        MySharedPreferences.initSharedPreferences(cnt.getSharedPreferences(prefsName, 0));

        RemoteViews remoteViews = new RemoteViews(cnt.getPackageName(), R.layout.mhd_widget_layout);

        remoteViews.setTextViewText(R.id.zastavka_1, MySharedPreferences.getPreferences("zastavka1") );
        remoteViews.setTextViewText(R.id.zastavka_2, MySharedPreferences.getPreferences("zastavka2") );

        remoteViews.setTextViewText(R.id.line1_odchod, MySharedPreferences.getPreferences("line1_odchod") );
        remoteViews.setTextViewText(R.id.line1_prichod, MySharedPreferences.getPreferences("line1_prichod") );
        remoteViews.setTextViewText(R.id.line1_dlzka, MySharedPreferences.getPreferences("line1_dlzkaCesty") );
        setBackgroundOfLink(remoteViews,R.id.line1_spojenie,MySharedPreferences.getPreferences("line1_spoje"));
        //remoteViews.setTextViewText(R.id.line1_spojenie, MySharedPreferences.getPreferences("line1_spoje") );

        remoteViews.setTextViewText(R.id.line2_odchod, MySharedPreferences.getPreferences("line2_odchod") );
        remoteViews.setTextViewText(R.id.line2_prichod, MySharedPreferences.getPreferences("line2_prichod") );
        remoteViews.setTextViewText(R.id.line2_dlzka, MySharedPreferences.getPreferences("line2_dlzkaCesty") );
        setBackgroundOfLink(remoteViews,R.id.line2_spojenie,MySharedPreferences.getPreferences("line2_spoje"));
        //remoteViews.setTextViewText(R.id.line2_spojenie, MySharedPreferences.getPreferences("line2_spoje") );

        remoteViews.setTextViewText(R.id.line3_odchod, MySharedPreferences.getPreferences("line3_odchod") );
        remoteViews.setTextViewText(R.id.line3_prichod, MySharedPreferences.getPreferences("line3_prichod") );
        remoteViews.setTextViewText(R.id.line3_dlzka, MySharedPreferences.getPreferences("line3_dlzkaCesty") );
        setBackgroundOfLink(remoteViews,R.id.line3_spojenie,MySharedPreferences.getPreferences("line3_spoje"));
        //remoteViews.setTextViewText(R.id.line3_spojenie, MySharedPreferences.getPreferences("line3_spoje") );

        ComponentName myWidget = new ComponentName(cnt, MhdWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(cnt);
//        manager.updateAppWidget(myWidget, remoteViews);
        manager.updateAppWidget(widgetId, remoteViews);

    }


    private enum Spoje{
        R1,R2,R3,R4,R5,R6,R7,R8, RA1,RA2,RA3,RA4,RA5,RA6,RA7,RA8, X7, N1,N2,N3,N71
        //12T,20L,26P,
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public void setBackgroundOfLink(RemoteViews rv, int viewId, String spoje){
        String[] splited=spoje.split("\\s+");

        //doplnit pre viac spojov
        //v configuracom spravit hladanie so scrollom mozno
        if (splited.length == 1){

            if (splited[0].equals("12T")){
                rv.setInt(viewId, "setBackgroundResource", R.drawable.a_posilove);
                rv.setTextColor(viewId, Color.parseColor("#000000"));
                rv.setTextViewText(viewId, spoje);
            }else if (splited[0].equals("20L")){
                rv.setInt(viewId, "setBackgroundResource", R.drawable.a_doplnkove);
                rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                rv.setTextViewText(viewId, spoje);
            }else if (splited[0].equals("26P")){
                rv.setInt(viewId, "setBackgroundResource", R.drawable.a_posilove);
                rv.setTextColor(viewId, Color.parseColor("#000000"));
                rv.setTextViewText(viewId, spoje);
            }

            if (isNumeric(splited[0])) {
                switch (Integer.parseInt(splited[0])) {

                    case 3:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.e3);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 4:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.e4);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 5:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.e5);
                        rv.setTextColor(viewId, Color.parseColor("#000000"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 6:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.e6);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 9:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.e9);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 10:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a10);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 15:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a15);
                        rv.setTextColor(viewId, Color.parseColor("#000000"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 16:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a16);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 19:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a19);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 23:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a23);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 27:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a27);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 34:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a34);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 36:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a36);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 71:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.t71);
                        rv.setTextColor(viewId, Color.parseColor("#000000"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 72:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.t72);
                        rv.setTextColor(viewId, Color.parseColor("#000000"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    //POSILOVE SPOJE
                    case 18:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_posilove);
                        rv.setTextColor(viewId, Color.parseColor("#000000"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 20:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_posilove);
                        rv.setTextColor(viewId, Color.parseColor("#000000"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 28:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_posilove);
                        rv.setTextColor(viewId, Color.parseColor("#000000"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 30:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_posilove);
                        rv.setTextColor(viewId, Color.parseColor("#000000"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 51:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_posilove);
                        rv.setTextColor(viewId, Color.parseColor("#000000"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 54:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_posilove);
                        rv.setTextColor(viewId, Color.parseColor("#000000"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 55:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_posilove);
                        rv.setTextColor(viewId, Color.parseColor("#000000"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 56:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_posilove);
                        rv.setTextColor(viewId, Color.parseColor("#000000"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 57:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_posilove);
                        rv.setTextColor(viewId, Color.parseColor("#000000"));
                        rv.setTextViewText(viewId, spoje);
                        break;





                    //DOPLNKOVE SPOJE
                    case 11:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_doplnkove);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 12:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_doplnkove);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 13:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_doplnkove);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 14:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_doplnkove);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 17:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_doplnkove);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 21:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_doplnkove);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 22:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_doplnkove);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 24:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_doplnkove);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 25:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_doplnkove);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 26:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_doplnkove);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 29:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_doplnkove);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 31:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_doplnkove);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 33:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_doplnkove);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                    case 52:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.a_doplnkove);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;


//                    default://rv.setInt(viewId,"setBackgroundResource",R.drawable.e3);
//                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
//                        rv.setTextViewText(viewId, spoje);
//                        break;
                }
            }else{
                Spoje spojIny = Spoje.valueOf(splited[0]);
                switch (spojIny){
                    case R1:
                    case R2:
                    case R3:
                    case R4:
                    case R5:
                    case R6:
                    case R7:
                    case R8:
                    case RA1:
                    case RA2:
                    case RA3:
                    case RA4:
                    case RA5:
                    case RA6:
                    case RA7:
                    case RA8:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.e_r);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;
                    case X7:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.nahradna_doprava_x);
                        rv.setTextColor(viewId, Color.parseColor("#ffffff"));
                        rv.setTextViewText(viewId, spoje);
                        break;
                    case N1:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.an1);
                        rv.setTextColor(viewId, Color.parseColor("#f3f60f"));
                        rv.setTextViewText(viewId, spoje);
                        break;
                    case N2:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.an2);
                        rv.setTextColor(viewId, Color.parseColor("#fbb6c5"));
                        rv.setTextViewText(viewId, spoje);
                        break;
                    case N3:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.an3);
                        rv.setTextColor(viewId, Color.parseColor("#c6d640"));
                        rv.setTextViewText(viewId, spoje);
                        break;
                    case N71:
                        rv.setInt(viewId, "setBackgroundResource", R.drawable.an71);
                        rv.setTextColor(viewId, Color.parseColor("#a9dce5"));
                        rv.setTextViewText(viewId, spoje);
                        break;

                }
            }
        }else{
            //rv.setInt(viewId,"setBackgroundResource",R.drawable.e3);
            rv.setTextColor(viewId, Color.parseColor("#ffffff"));
            rv.setTextViewText(viewId, spoje);

        }

    }

    public void parseImhd() {

        final String url;
        String odchod,odchod2,odchod3;
        String prichod,prichod2,prichod3;
        String dlzkaCesty,dlzkaCesty2,dlzkaCesty3;
        String spoje,spoje2,spoje3;
        Context thisContext;




        Document doc;
        try {
            MySharedPreferences.initSharedPreferences(cnt.getSharedPreferences(prefsName, 0));
            url=MySharedPreferences.getPreferences("link");

            doc =  Jsoup.connect(url).get();

            Elements table = doc.select("TABLE[class = tabulka]");
            Elements rows = table.select("tr");


            odchod =rows.get(1).child(0).text();
            prichod =rows.get(1).child(1).text();
            dlzkaCesty=rows.get(1).child(2).text();
            spoje=rows.get(1).child(3).text();

            odchod2 =rows.get(2).child(0).text();
            prichod2 =rows.get(2).child(1).text();
            dlzkaCesty2=rows.get(2).child(2).text();
            spoje2=rows.get(2).child(3).text();

            odchod3 =rows.get(3).child(0).text();
            prichod3 =rows.get(3).child(1).text();
            dlzkaCesty3=rows.get(3).child(2).text();
            spoje3=rows.get(3).child(3).text();



            String[] splited=odchod.split("\\s+");
            String[] splited2=prichod.split("\\s+");
            String[] splited21=odchod2.split("\\s+");
            String[] splited22=prichod2.split("\\s+");
            String[] splited31=odchod3.split("\\s+");
            String[] splited32=prichod3.split("\\s+");


            odchod=splited[splited.length-1];
            prichod=splited2[splited2.length-1];
            odchod2=splited21[splited21.length-1];
            prichod2=splited22[splited22.length-1];
            odchod3=splited31[splited31.length-1];
            prichod3=splited32[splited32.length-1];


            MySharedPreferences.setPreferences("line1_odchod",odchod.toString());
            MySharedPreferences.setPreferences("line1_prichod", prichod.toString());
            MySharedPreferences.setPreferences("line1_dlzkaCesty", dlzkaCesty.toString());
            MySharedPreferences.setPreferences("line1_spoje", spoje.toString());

            MySharedPreferences.setPreferences("line2_odchod", odchod2.toString());
            MySharedPreferences.setPreferences("line2_prichod", prichod2.toString());
            MySharedPreferences.setPreferences("line2_dlzkaCesty", dlzkaCesty2.toString());
            MySharedPreferences.setPreferences("line2_spoje", spoje2.toString());

            MySharedPreferences.setPreferences("line3_odchod",odchod3.toString());
            MySharedPreferences.setPreferences("line3_prichod", prichod3.toString());
            MySharedPreferences.setPreferences("line3_dlzkaCesty", dlzkaCesty3.toString());
            MySharedPreferences.setPreferences("line3_spoje", spoje3.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }




        }


}
