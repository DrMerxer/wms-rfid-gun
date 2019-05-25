package com.example.wms_rfid_gun;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.ColorSpace;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.*;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.wms_rfid_gun.ui.main.ArriveFragment;
import com.example.wms_rfid_gun.ui.main.CheckFragment;
import com.example.wms_rfid_gun.ui.main.DepartureFragment;
import com.example.wms_rfid_gun.ui.main.SectionsPagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GunActivity extends AppCompatActivity {


    public static final String TAG = GunActivity.class.getSimpleName();
    private NfcAdapter mNfcAdapter;
    private int currIndex = 0;
//
//    private ArriveFragment arriveFragment;
//    private CheckFragment checkFragment;
//    private DepartureFragment departureFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gun);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(this.getResources().getColor(R.color.colorGunStatusBar));


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currIndex = tab.getPosition();
                Log.d(TAG, Integer.toString(currIndex));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        initNFC();
    }

    @Override
    protected void onResume(){
        super.onResume();
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);
        IntentFilter filter = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        try{
            filter.addDataType("text/plain");
        }catch(IntentFilter.MalformedMimeTypeException e){
            e.printStackTrace();
        }
        IntentFilter[] filters = {filter};
        String[][] techListsArray = new String[][] { new String[] {MifareClassic.class.getName(),NfcA.class.getName()}};
        mNfcAdapter.enableForegroundDispatch(this, pi, filters, techListsArray);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    public void onNewIntent(Intent intent){
        super.onNewIntent(intent);

        Tag tag = (Tag)intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Log.d(TAG, ByteArrayToHexString(tag.getId()));
        if(tag != null){
            switch (currIndex){
                case 0:
                    String UID = ByteArrayToHexString(tag.getId());
                    Toast temp = Toast.makeText(this, "UID: " + UID, Toast.LENGTH_SHORT);
                    temp.show();
//                    onNfcDetected(tag);
                    break;
                case 1:
//                    checkFragment = (CheckFragment) getSupportFragmentManager().findFragmentByTag(CheckFragment.TAG);
                    break;
                case 2:
//                    departureFragment = (DepartureFragment) getSupportFragmentManager().findFragmentByTag(DepartureFragment.TAG);
                    break;
            }
        }
    }

    private void initNFC(){
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        Log.d(TAG, "NFC initialized");
    }

    public void makeToast(String mesg){
        Toast.makeText(GunActivity.this, mesg, Toast.LENGTH_SHORT);
    }

    private String ByteArrayToHexString(byte [] inarray){
        int i,j,in;
        String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        String out = "";

        for(j=0; j<inarray.length; ++j){
            in = (int)inarray[j]&0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }

    public void upload(int id, int type, String barcode, String tagid) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.128/gun/arrive";

        JSONObject sendObject = new JSONObject();

        switch (type){
            case 0:
                sendObject.put("api","arrive");
                sendObject.put("barcode", barcode);
                sendObject.put("tagid", tagid);
                sendObject.put("userid", id);
                break;
            case 1:
                sendObject.put("api","check");
                sendObject.put("tagid", tagid);
                sendObject.put("userid", id);
                break;
            case 2:
                sendObject.put("api","departure");
                sendObject.put("tagid", tagid);
                sendObject.put("userid", id);
                break;
        }

        JsonRequest<JSONObject> jsonRequest= new JsonObjectRequest(Request.Method.POST, url, sendObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        makeToast("SUCCESS");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        makeToast("ERROR");
                    }
                }
        );

        queue.add(jsonRequest);
        queue.start();

    }
}