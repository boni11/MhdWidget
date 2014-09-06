package com.example.widgetmhd;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by lukas.stefanko on 1. 9. 2014.
 */
public class MhdWidgetProvider extends AppWidgetProvider{

    static String odkial;
    static String kam;
    static String dlzkaCesty;
    static String spoje;
    static Context thisContext;


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
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATEE");


        //PendingIntent pending=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pending=PendingIntent.getBroadcast(context,0,intent,0);
        remoteViews.setOnClickPendingIntent(R.id.refresh_button,pending);

        if (MySharedPreferences.getSharedPrefObj() == Boolean.TRUE) {
            remoteViews.setTextViewText(R.id.zastavka_1, MySharedPreferences.getPreferences("zastavka1"));
            remoteViews.setTextViewText(R.id.zastavka_2, MySharedPreferences.getPreferences("zastavka2"));
        }

        ComponentName myWidget = new ComponentName(context, MhdWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATEE")){
            Log.d("WIDGET","onReceive - refresh button");
            pushWidgetUpdate(context);
        }
        //Log.d("WIDGET","onReceive");
    }

    public static void pushWidgetUpdate(Context context){//, RemoteViews remoteViews) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.mhd_widget_layout);

        parseImhd();

        remoteViews.setTextViewText(R.id.zastavka_1, MySharedPreferences.getPreferences("zastavka1") );
        remoteViews.setTextViewText(R.id.zastavka_2, MySharedPreferences.getPreferences("zastavka2") );

        ComponentName myWidget = new ComponentName(context, MhdWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }





    private static void parseImhd() {

        final String url=MySharedPreferences.getPreferences("link");


        new Thread(new Runnable() {

            @Override
            public void run() {
                //WidgetConfiguration wc=new WidgetConfiguration();
                //String url = wc.prefs.getString("link", "default");


                Document doc;
                try {

                    // need http protocol
                    doc =  Jsoup.connect(url).get();


                    // get page title
                    //String title =  doc.title();
                    //System.out.println("title : " + title);

                    // get all links
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

                    odkial=rows.get(1).child(0).text();
                    kam=rows.get(1).child(1).text();
                    dlzkaCesty=rows.get(1).child(2).text();
                    spoje=rows.get(1).child(3).text();



			        /*
			        remoteViews.setTextViewText(R.id.textView5, rows.get(1).child(0).text() );
			        remoteViews.setTextViewText(R.id.textView6, rows.get(1).child(1).text() );
			        remoteViews.setTextViewText(R.id.textView7, rows.get(1).child(2).text() );
			        remoteViews.setTextViewText(R.id.textView8, rows.get(1).child(3).text() );
			        */


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





				/*
				HttpGet httpRequest = new HttpGet( url);
		        HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response;
				Document doc=null;
				try {
					response = httpclient.execute(httpRequest);
					HttpEntity r_entity = response.getEntity();
			        String xmlString = EntityUtils.toString(r_entity);
			        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			        DocumentBuilder db = factory.newDocumentBuilder();
			        InputSource inStream = new InputSource();
			        inStream.setCharacterStream(new StringReader(xmlString));
			        doc = db.parse(inStream);



				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        String playcount = "empty";
		        NodeList nl = doc.getElementsByTagName("playcount");
		        for(int i = 0; i < nl.getLength(); i++) {
		            if (nl.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
		                 org.w3c.dom.Element nameElement = (org.w3c.dom.Element) nl.item(i);
		                 playcount = nameElement.getFirstChild().getNodeValue().trim();
		             }
		        }*/


            }
        }).start();



    }
}
