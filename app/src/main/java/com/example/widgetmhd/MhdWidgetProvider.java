package com.example.widgetmhd;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by lukas.stefanko on 1. 9. 2014.
 */
public class MhdWidgetProvider extends AppWidgetProvider{

    static String odchod;
    static String prichod;
    static String dlzkaCesty;
    static String spoje;
    static Context thisContext;

    static int widgetIdd=0;


    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d("WIDGET","onDisabled");
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d("WIDGET","onEnabled");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d("WIDGET","onDeleted");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        //Log.d("WIDGET","onUpadete");
        //Log.d("WIDGETOnUpadate",Integer.toString(appWidgetIds[0]));

        widgetIdd=appWidgetIds[0];
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.mhd_widget_layout);

        // register for button event
        Intent intent=new Intent(context,getClass());
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATEE");
        intent.putExtra("WIDGET_ID_MOJE",(int)this.widgetIdd);


        //PendingIntent pending=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pending=PendingIntent.getBroadcast(context,widgetIdd,intent,0);
        remoteViews.setOnClickPendingIntent(R.id.refresh_button,pending);

//
//        if (MySharedPreferences.getSharedPrefObj() == Boolean.TRUE) {
//            //parseImhd();
//            //Log.d("WIDGET","parse imhd on upadet");
//            //new GetImhdSpoje(remoteViews).execute();
//            remoteViews.setTextViewText(R.id.zastavka_1, MySharedPreferences.getPreferences("zastavka1"));
//            remoteViews.setTextViewText(R.id.zastavka_2, MySharedPreferences.getPreferences("zastavka2"));
//
//            /**
//            remoteViews.setTextViewText(R.id.line1_odchod, MySharedPreferences.getPreferences("line1_odchod") );
//            remoteViews.setTextViewText(R.id.line1_prichod, MySharedPreferences.getPreferences("line1_prichod") );
//            remoteViews.setTextViewText(R.id.line1_dlzka, MySharedPreferences.getPreferences("line1_dlzkaCesty") );
//            remoteViews.setTextViewText(R.id.line1_spojenie, MySharedPreferences.getPreferences("line1_spoje") );
//             */
//        }

        ComponentName myWidget = new ComponentName(context, MhdWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
//        manager.updateAppWidget(myWidget, remoteViews);
        manager.updateAppWidget(widgetIdd, remoteViews);




    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int widgetIddd;

        super.onReceive(context, intent);
        if (intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATEE")){
            Log.d("INTENT ON RECEIVE",Integer.toString(intent.getIntExtra("WIDGET_ID_MOJE",0)));
//            Log.d("INTENT ON RECEIVE",Integer.toString(widgetIdd));
            widgetIddd=intent.getIntExtra("WIDGET_ID_MOJE",0);
            String prefsName="mhd_prefs_"+String.valueOf(widgetIddd);

//            MySharedPreferences.initSharedPreferences(context.getSharedPreferences(prefsName, 0));



            new GetImhdSpoje(context,prefsName,widgetIddd).execute();

//            if (intent.getIntExtra("WIDGET_ID_MOJE",0) == this.widgetIdd) {
//                new GetImhdSpoje(context).execute();
//            }

        }
        //Log.d("WIDGET","onReceive");
    }


//
//    public static void pushWidgetUpdate(Context context){//, RemoteViews remoteViews) {
//        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.mhd_widget_layout);
//
//
//        //parseImhd();
//        new GetImhdSpoje(context).execute();
//
//        remoteViews.setTextViewText(R.id.zastavka_1, MySharedPreferences.getPreferences("zastavka1") );
//        remoteViews.setTextViewText(R.id.zastavka_2, MySharedPreferences.getPreferences("zastavka2") );
//
////        remoteViews.setTextViewText(R.id.line1_odchod, MySharedPreferences.getPreferences("line1_odchod") );
////        remoteViews.setTextViewText(R.id.line1_prichod, MySharedPreferences.getPreferences("line1_prichod") );
////        remoteViews.setTextViewText(R.id.line1_dlzka, MySharedPreferences.getPreferences("line1_dlzkaCesty") );
////        remoteViews.setTextViewText(R.id.line1_spojenie, MySharedPreferences.getPreferences("line1_spoje") );
//
//
//        ComponentName myWidget = new ComponentName(context, MhdWidgetProvider.class);
//        AppWidgetManager manager = AppWidgetManager.getInstance(context);
//        manager.updateAppWidget(myWidget, remoteViews);
//    }




//
//    public static void parseImhd() {
//
//        final String url=MySharedPreferences.getPreferences("link");
//
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                //WidgetConfiguration wc=new WidgetConfiguration();
//                //String url = wc.prefs.getString("link", "default");
//
//                Document doc;
//                try {
//                    // need http protocol
//                    doc =  Jsoup.connect(url).get();
//
//                    Elements table = doc.select("TABLE[class = tabulka]");//.first();
//                    Elements rows = table.select("tr");
//
//			        /*
//			        for (Element td: rows.get(0).children()) {
//			            System.out.println(td.text());
//			        }
//			        */
//
//                    System.out.println( (rows.get(1).child(0).text() ));
//                    System.out.println( (rows.get(1).child(1).text() ));
//                    System.out.println( (rows.get(1).child(2).text() ));
//                    System.out.println( (rows.get(1).child(3).text() ));
//
//                    odchod =rows.get(1).child(0).text();
//                    prichod =rows.get(1).child(1).text();
//                    dlzkaCesty=rows.get(1).child(2).text();
//                    spoje=rows.get(1).child(3).text();
//
//
//                    String[] splited=odchod.split("\\s+");
//                    String[] splited2=prichod.split("\\s+");
//                    //System.out.println( splited[splited.length-1] );
//
//                    odchod=splited[splited.length-1];
//                    prichod=splited2[splited2.length-1];
//                    MySharedPreferences.setPreferences("line1_odchod",odchod.toString());
//                    MySharedPreferences.setPreferences("line1_prichod", prichod.toString());
//                    MySharedPreferences.setPreferences("line1_dlzkaCesty", dlzkaCesty.toString());
//                    MySharedPreferences.setPreferences("line1_spoje", spoje.toString());
//
//
//			        /*
//			        for (Element td: rows.get(1).children()) {
//			        	remoteViews.setTextViewText(R.id.textView5, td.text() );
//			            System.out.println(td.text());
//			        }
//
//			        /*
//			        for (Element td: rows.get(2).children()) {
//			            System.out.println(td.text());
//			        }
//			        */
//
//
//
//
//					/*
//					Elements links = ((Element) doc).select("a[href]");
//					for (Element link : links) {
//
//						// get the value from href attribute
//						System.out.println("\nlink : " + link.attr("href"));
//						System.out.println("text : " + link.text());
//
//					}
//					*/
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
//
//
//
//    }
}
