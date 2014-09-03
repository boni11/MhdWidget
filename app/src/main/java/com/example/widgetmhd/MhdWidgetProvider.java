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
        Log.d("WIDGET","onUpadete");


        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.mhd_widget_layout);

        // register for button event
        Intent intent=new Intent(context,getClass());
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");


        //PendingIntent pending=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pending=PendingIntent.getBroadcast(context,0,intent,0);
        remoteViews.setOnClickPendingIntent(R.id.refresh_button,pending);

        remoteViews.setTextViewText(R.id.zastavka_1,"onUpade");

        ComponentName myWidget = new ComponentName(context, MhdWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATEE")){
            Log.d("WIDGET","onReceive - refresh button");
        }
        Log.d("WIDGET","onReceive");
    }
}
