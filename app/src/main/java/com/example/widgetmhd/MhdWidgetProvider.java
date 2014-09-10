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
            Log.d("WIDGET - INTENT ON RECEIVE",Integer.toString(intent.getIntExtra("WIDGET_ID_MOJE",0)));

            widgetIddd=intent.getIntExtra("WIDGET_ID_MOJE",0);
            String prefsName="mhd_prefs_"+String.valueOf(widgetIddd);
            //Log.d("WIDGET - PREFS NAME GET SPOJE",prefsName);


            new GetImhdSpoje(context,prefsName,widgetIddd).execute();
        }

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


}
