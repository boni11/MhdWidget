package com.example.widgetmhd;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MhdWidgetIntentReceiver extends BroadcastReceiver {
    public MhdWidgetIntentReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (intent.getAction().equals("com.mhd.intent.action.UPDATE_WIDGET")){

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.mhd_widget_layout);

            // updating view
            //remoteViews.setTextViewText(R.id.title, "neger");
            //remoteViews.setTextViewText(R.id.desc, getDesc(context));

            // re-registering for click listener
            Intent intent1=new Intent();
            intent1.setAction("com.mhd.intent.action.UPDATE_WIDGET");


            PendingIntent pending=PendingIntent.getActivity(context,0,intent1,0);
            remoteViews.setOnClickPendingIntent(R.id.refresh_button,pending);

            remoteViews.setTextViewText(R.id.zastavka_1, "button");

            ComponentName myWidget = new ComponentName(context, MhdWidgetProvider.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            manager.updateAppWidget(myWidget, remoteViews);

        }

    }
}
