package com.example.wms_rfid_gun;

import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.wms_rfid_gun.ui.main.SectionsPagerAdapter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class GunActivity extends AppCompatActivity {


    public static final String TAG = GunActivity.class.getSimpleName();
    private NfcAdapter mNfcAdapter;
    private int currIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gun);
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
    }

    @Override
    protected void onNewIntent(Intent intent){
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        if(tag != null){

        }
    }

    private void initNFC(){
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    public void makeToast(String mesg){
        Toast.makeText(GunActivity.this, mesg, Toast.LENGTH_SHORT);
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