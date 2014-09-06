package com.example.widgetmhd;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Spinner;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MhdWidgetConfiguration extends ActionBarActivity {

    private int widgetId;
    private Spinner sOdkial;
    private Spinner sKam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mhd_widget_configuration);
        setResult(RESULT_CANCELED);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        AppWidgetManager widgetManager=AppWidgetManager.getInstance(this);
        RemoteViews views=new RemoteViews(this.getPackageName(),R.layout.mhd_widget_layout);

        //zavolat fciu nech nastavy spinery
        addItemsToSpinnerOdial();

        Button uloz= (Button) findViewById(R.id.uloz_config);
        uloz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dostat zastavky zo spinerov
                //ulozit do shared preferences

                String[] nazvyZastavok = getResources().getStringArray(R.array.zastavkyNazvy);
                int[] cislaZastavok = getResources().getIntArray(R.array.nazvyCisla);

                HashMap<String, Integer> myMap = new HashMap<String, Integer>();
                for (int i = 0; i < nazvyZastavok.length; i++) {
                    myMap.put(nazvyZastavok[i], cislaZastavok[i]);
                }

                String link="http://imhd.zoznam.sk/ke/planovac-cesty-vyhladanie-spojenia.html?";

                try {
                    String odkial = "spojenieodkial="+ URLEncoder.encode(String.valueOf(sOdkial.getSelectedItem()), "utf-8")+"&";
                    String odkialZastavka="z1k=z"+ myMap.get(String.valueOf(sOdkial.getSelectedItem())) +"&";
                    String kam="spojeniekam="+URLEncoder.encode( String.valueOf(sKam.getSelectedItem()) ,"utf-8")+"&";
                    String kamZastavka="z2k=z"+myMap.get(String.valueOf(sKam.getSelectedItem()));
                    link=link+odkial+odkialZastavka+kam+kamZastavka;

                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                /**
                TextView zastavka1=(TextVie w) findViewById(R.id.zastavka_1);
                zastavka1.setText(String.valueOf(sOdkial.getSelectedItem()));

                TextView zastavka2=(TextView) findViewById(R.id.zastavka_2);
                zastavka2.setText(String.valueOf(sKam.getSelectedItem()));
                */

                Log.d("LINK",link);

                MySharedPreferences.initSharedPreferences(getSharedPreferences("mhd_prefs", MODE_PRIVATE), getApplicationContext());
                MySharedPreferences.setPreferences("link",link);
                MySharedPreferences.setPreferences("zastavka1",String.valueOf(sOdkial.getSelectedItem()));
                MySharedPreferences.setPreferences("zastavka2",String.valueOf(sKam.getSelectedItem()));



                //bud posli intent pre Receiver alebo len zavolaj fciu

                Intent resultValue=new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetId);
                setResult(RESULT_OK,resultValue);
                finish();

            }
        });


    }



    public void addItemsToSpinnerOdial(){
        sOdkial=(Spinner)findViewById(R.id.spinner1);
        sKam=(Spinner)findViewById(R.id.spinner2);

        List<String> list=new ArrayList<String>();
        list.add("Alejová, školy");
        list.add("Alpinka");
        list.add("Alvinczyho");
        list.add("Alžbetina, Rektorát UPJŠ");
        list.add("Amfiteáter");
        list.add("Andrašcíkova");
        list.add("Anicka");
        list.add("Astória");
        list.add("Atletická");
        list.add("Aténska");
        list.add("Autocamping");
        list.add("Bana Bankov");
        list.add("Belehradská");
        list.add("Berlínska");
        list.add("Bernolákova");
        list.add("Blšák");
        list.add("Bosákova");
        list.add("Botanická záhrada");
        list.add("Boženy Slancíkovej - Timravy");
        list.add("Bruselská");
        list.add("Budanova");
        list.add("Budapeštianska");
        list.add("Bukovecká");
        list.add("Bytový podnik mesta Košice");
        list.add("Cassovar");
        list.add("Centrálne prekladisko rúd");
        list.add("Chrastie");
        list.add("Cottbuská");
        list.add("CVC Domino");
        list.add("Cyklistická");
        list.add("Dargovských hrdinov, MiÚ");
        list.add("Danový úrad");
        list.add("Detský domov Satelit");
        list.add("Diamantová");
        list.add("Dneperská");
        list.add("Dolný Bankov");
        list.add("Dom umenia");
        list.add("Dopravná");
        list.add("DP mesta Košice");
        list.add("Drabova");
        list.add("Dénešova");
        list.add("Džungla");
        list.add("EcoPoint");
        list.add("Exnárova");
        list.add("EZ Elektrosystémy");
        list.add("Faurecia");
        list.add("Fábryho");
        list.add("Golianova");
        list.add("Grunt");
        list.add("Gymnázium Opatovská");
        list.add("Haburská");
        list.add("Hala Cassosport");
        list.add("Haniska, Unimo");
        list.add("Haniska, žel. st.");
        list.add("Hanojská");
        list.add("Havlíckova");
        list.add("Heckova");
        list.add("Heringeš");
        list.add("Hlavná pošta");
        list.add("Hodonínska");
        list.add("Holubyho");
        list.add("Horný Bankov");
        list.add("Hranicná");
        list.add("Hronská");
        list.add("Húskova");
        list.add("Idanská");
        list.add("Immopark");
        list.add("Jahodná, chata");
        list.add("Jahodná, horáren");
        list.add("Jahodná, kopec");
        list.add("Jakabov palác");
        list.add("Janigova");
        list.add("Južná trieda c. 125");
        list.add("Kalinovská");
        list.add("Kasárne, Kulturpark");
        list.add("Katastrálny úrad");
        list.add("Kavecany");
        list.add("Kavecany, cintorín");
        list.add("Kavecany, Miestny úrad");
        list.add("Kino Družba");
        list.add("Klimkovicova");
        list.add("Kokšov Bakša c. 2");
        list.add("Kokšov Bakša c. 215");
        list.add("Kokšov Bakša, kostol");
        list.add("Kolibár");
        list.add("Kovalská");
        list.add("Košická Nová Ves");
        list.add("Košická Nová Ves, cintorín");
        list.add("Krajský súd");
        list.add("Krajský úrad");
        list.add("Krematórium");
        list.add("Kremnická");
        list.add("Krupinská");
        list.add("Králicia");
        list.add("Krásna");
        list.add("KVP, kláštor");
        list.add("Kvetná");
        list.add("Lackova");
        list.add("Ladožská");
        list.add("Letecká");
        list.add("Letisko");
        list.add("Levocská");
        list.add("Lingov");
        list.add("Lorincík");
        list.add("Lorincík, Miestny úrad");
        list.add("Ludvíkov Dvor");
        list.add("Ludvíkov Dvor");
        list.add("Luník IX, rázcestie");
        list.add("Luník IX, sídlisko");
        list.add("Luník VIII");
        list.add("Cermel");
        list.add("Dumbierska");
        list.add("Madridská");
        list.add("Magistrát mesta Košice");
        list.add("Magnezitárska");
        list.add("Malá stanica");
        list.add("Maša");
        list.add("Mengusovská");
        list.add("Michalovská");
        list.add("Mier");
        list.add("Miestny úrad, KVP");
        list.add("Minská");
        list.add("Mliecna");
        list.add("Mlynská bašta");
        list.add("Moldavská, obchodné centrá");
        list.add("Moskovská");
        list.add("Motorest, Krásna");
        list.add("Monok");
        list.add("Myslava, kostol");
        list.add("Myslava, Pri škole");
        list.add("Myslavská 87");
        list.add("Májová");
        list.add("Napájadlá");
        list.add("Nemocnica Šaca");
        list.add("Nová nemocnica");
        list.add("Nám. Maratónu mieru");
        list.add("Nám. osloboditelov");
        list.add("Národné nám.");
        list.add("Obchodná akadémia");
        list.add("OC Cassovia");
        list.add("OC Optima");
        list.add("Ovsená");
        list.add("Palackého");
        list.add("Pasteurova");
        list.add("Pereš");
        list.add("Perešská");
        list.add("Pereš, vodojem");
        list.add("Plynáren Haniska");
        list.add("Pltová");
        list.add("Pod Furcou");
        list.add("Podhradová");
        list.add("Podjazdy Haniska");
        list.add("Podnikatelská");
        list.add("Polianska");
        list.add("Poliklinika KVP");
        list.add("Poliklinika Sever");
        list.add("Poliklinika Východ");
        list.add("Polov");
        list.add("Polovská");
        list.add("Polov, rázcestie");
        list.add("Polská");
        list.add("Postupimská");
        list.add("Považská, Nová nemocnica");
        list.add("Pošta 2");
        list.add("Prekladisko hotových výrobkov");
        list.add("Pri Hornáde");
        list.add("Pri hati");
        list.add("Pri Teleku");
        list.add("Priemyselná");
        list.add("Radnica Starého mesta");
        list.add("Rašelinové závody");
        list.add("Revúcka");
        list.add("Rovníková");
        list.add("Rušnové depo");
        list.add("Ružová, OC Galéria");
        list.add("Ryba");
        list.add("Secovská");
        list.add("Senný trh");
        list.add("Slovenská c. 20");
        list.add("Slovenská c. 42");
        list.add("Slovenský rozhlas");
        list.add("Sládkovicova");
        list.add("SOŠ automobilová");
        list.add("Socha Jána Pavla II.");
        list.add("Sofijská");
        list.add("Sokolovská");
        list.add("Spalovna");
        list.add("Spolocenský pavilón");
        list.add("Správa mestskej zelene");
        list.add("Stanicné nám.");
        list.add("Staroslovanská");
        list.add("Starozagorská");
        list.add("Stará nemocnica");
        list.add("Stavadlo 1");
        list.add("Stavadlo 2 U.S.Steel");
        list.add("Stodolova");
        list.add("Suchodolinská");
        list.add("Suchá dolina");
        list.add("Svornosti");
        list.add("Technická univerzita");
        list.add("Teplého");
        list.add("Tepláren");
        list.add("Tesco, Džungla");
        list.add("Tomášikova");
        list.add("Trebišovská");
        list.add("Triton");
        list.add("Ulica Sv. Ladislava");
        list.add("Ucnovská");
        list.add("V úvoze");
        list.add("Valcovne U. S. Steel");
        list.add("Valeo");
        list.add("Važecká");
        list.add("Verejný cintorín");
        list.add("Vinicná");
        list.add("Vitalina");
        list.add("Vo výmoli");
        list.add("Vodná");
        list.add("Vodárenská");
        list.add("Vrátnica 4 U.S.Steel");
        list.add("VSS, križovatka");
        list.add("Vstupný areál U. S. Steel");
        list.add("Vyhliadková veža");
        list.add("Vyšné Opátske");
        list.add("Vyšný dvor");
        list.add("Útulok pre zvieratá");
        list.add("Zelený dvor");
        list.add("Zimná");
        list.add("Zimný štadión");
        list.add("Zoo");
        list.add("Zupkova");
        list.add("ZŠ Bernolákova");
        list.add("Šaca, námestie");
        list.add("Šebastovce");
        list.add("Školy pre telesne postihnutých");
        list.add("Štrbská");
        list.add("Tahanovská");
        list.add("Železnicná nemocnica");
        list.add("Železnicná zastávka Valaliky");
        list.add("Železníky");
        list.add("Železníky, križovatka");
        list.add("Želiarska");
        list.add("Žel. zastávka Tahanovce");
        list.add("Žiacka");
        list.add("Žilinská");

        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sOdkial.setAdapter(dataAdapter);
        sKam.setAdapter(dataAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mhd_widget_configuration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
