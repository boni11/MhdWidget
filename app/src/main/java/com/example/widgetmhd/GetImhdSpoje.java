package com.example.widgetmhd;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
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
        Log.d("WIDGET","onPostExecute");
        MySharedPreferences.initSharedPreferences(cnt.getSharedPreferences(prefsName, 0));

        RemoteViews remoteViews = new RemoteViews(cnt.getPackageName(), R.layout.mhd_widget_layout);

        remoteViews.setTextViewText(R.id.zastavka_1, MySharedPreferences.getPreferences("zastavka1") );
        remoteViews.setTextViewText(R.id.zastavka_2, MySharedPreferences.getPreferences("zastavka2") );

        remoteViews.setTextViewText(R.id.line1_odchod, MySharedPreferences.getPreferences("line1_odchod") );
        remoteViews.setTextViewText(R.id.line1_prichod, MySharedPreferences.getPreferences("line1_prichod") );
        remoteViews.setTextViewText(R.id.line1_dlzka, MySharedPreferences.getPreferences("line1_dlzkaCesty") );
        remoteViews.setTextViewText(R.id.line1_spojenie, MySharedPreferences.getPreferences("line1_spoje") );

        remoteViews.setTextViewText(R.id.line2_odchod, MySharedPreferences.getPreferences("line2_odchod") );
        remoteViews.setTextViewText(R.id.line2_prichod, MySharedPreferences.getPreferences("line2_prichod") );
        remoteViews.setTextViewText(R.id.line2_dlzka, MySharedPreferences.getPreferences("line2_dlzkaCesty") );
        remoteViews.setTextViewText(R.id.line2_spojenie, MySharedPreferences.getPreferences("line2_spoje") );

        remoteViews.setTextViewText(R.id.line3_odchod, MySharedPreferences.getPreferences("line3_odchod") );
        remoteViews.setTextViewText(R.id.line3_prichod, MySharedPreferences.getPreferences("line3_prichod") );
        remoteViews.setTextViewText(R.id.line3_dlzka, MySharedPreferences.getPreferences("line3_dlzkaCesty") );
        remoteViews.setTextViewText(R.id.line3_spojenie, MySharedPreferences.getPreferences("line3_spoje") );

        ComponentName myWidget = new ComponentName(cnt, MhdWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(cnt);
//        manager.updateAppWidget(myWidget, remoteViews);
        manager.updateAppWidget(widgetId, remoteViews);

    }

    public void parseImhd() {

        final String url=MySharedPreferences.getPreferences("link");
        String odchod,odchod2,odchod3;
        String prichod,prichod2,prichod3;
        String dlzkaCesty,dlzkaCesty2,dlzkaCesty3;
        String spoje,spoje2,spoje3;
        Context thisContext;




                Document doc;
                try {
                    // need http protocol
                    doc =  Jsoup.connect(url).get();

                    Elements table = doc.select("TABLE[class = tabulka]");//.first();
                    Elements rows = table.select("tr");

			        /*
			        for (Element td: rows.get(0).children()) {
			            System.out.println(td.text());
			        }
			        */

                    System.out.println( (rows.get(1).child(0).text() ));
                    System.out.println( (rows.get(1).child(1).text() ));
                    System.out.println( (rows.get(1).child(2).text() ));
                    System.out.println( (rows.get(1).child(3).text() ));

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


                    MySharedPreferences.initSharedPreferences(cnt.getSharedPreferences(prefsName, 0));
                    MySharedPreferences.setPreferences("line1_odchod",odchod.toString());
                    MySharedPreferences.setPreferences("line1_prichod", prichod.toString());
                    MySharedPreferences.setPreferences("line1_dlzkaCesty", dlzkaCesty.toString());
                    MySharedPreferences.setPreferences("line1_spoje", spoje.toString());

                    MySharedPreferences.setPreferences("line2_odchod",odchod2.toString());
                    MySharedPreferences.setPreferences("line2_prichod", prichod2.toString());
                    MySharedPreferences.setPreferences("line2_dlzkaCesty", dlzkaCesty2.toString());
                    MySharedPreferences.setPreferences("line2_spoje", spoje2.toString());

                    MySharedPreferences.setPreferences("line3_odchod",odchod3.toString());
                    MySharedPreferences.setPreferences("line3_prichod", prichod3.toString());
                    MySharedPreferences.setPreferences("line3_dlzkaCesty", dlzkaCesty3.toString());
                    MySharedPreferences.setPreferences("line3_spoje", spoje3.toString());


			        /*
			        for (Element td: rows.get(1).children()) {
			        	remoteViews.setTextViewText(R.id.textView5, td.text() );
			            System.out.println(td.text());
			        }

			        /*
			        for (Element td: rows.get(2).children()) {
			            System.out.println(td.text());
			        }
			        */




					/*
					Elements links = ((Element) doc).select("a[href]");
					for (Element link : links) {

						// get the value from href attribute
						System.out.println("\nlink : " + link.attr("href"));
						System.out.println("text : " + link.text());

					}
					*/

                } catch (IOException e) {
                    e.printStackTrace();
                }




        }


}
