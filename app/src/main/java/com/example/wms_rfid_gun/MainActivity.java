package com.example.wms_rfid_gun;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.wms_rfid_gun.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText id = findViewById(R.id.userid);
        final Button login = findViewById(R.id.login);
        final LinearLayout loginView = findViewById(R.id.login_view);

        final Data uni_data = new Data();

        final TextView loginText = findViewById(R.id.login_text);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginText.setText(getString(R.string.main_hello) + id.getText());
                uni_data.setUsrId(Integer.parseInt(id.getText().toString()));
                loginView.setVisibility(View.INVISIBLE);
                loginText.setVisibility(View.VISIBLE);
            }
        });
    }


    public void openGun(View view) {
        Intent intent = new Intent(this, GunActivity.class);
        EditText email = findViewById(R.id.userid);
        String message = email.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
