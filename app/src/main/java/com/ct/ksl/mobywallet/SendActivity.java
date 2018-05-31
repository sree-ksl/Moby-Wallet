package com.ct.ksl.mobywallet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sreek on 27/05/18.
 */

public class SendActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button sendButton;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);

//        implement spinner part
        Spinner spinner = findViewById(R.id.tokenSpinner);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("TRX");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        sendButton = findViewById(R.id.sendBtn);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Progress dialog and then toast saying toast
                progressDialog = new ProgressDialog(SendActivity.this);
                progressDialog.setMessage("Sending..."); // Setting Message
                progressDialog.setTitle("Progress"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(80000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                }).start();
                Intent account = new Intent(SendActivity.this, AccountActivity.class);
                startActivity(account);
                Toast.makeText(getApplicationContext(), "Sent", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // On selecting a spinner item
//        String item = adapterView.getItemAtPosition(i).toString();

        // Showing selected spinner item
//        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.send:
                sendTrx();
                return true;
            case R.id.receive:
                receiveTrx();
                return true;
            case R.id.freeze:
                freezeTrx();
                return true;
            case R.id.vote:
                vote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void vote() {
//        to do
        Intent vote = new Intent(SendActivity.this, VotingActivity.class);
        startActivity(vote);
    }

    private void freezeTrx() {
//        Go to Freeze Activity
        Intent freeze = new Intent(SendActivity.this, FreezeActivity.class);
        startActivity(freeze);
    }

    private void receiveTrx() {
//        Go to Receive Activity
        Intent receive = new Intent(SendActivity.this, ReceiveActivity.class);
        startActivity(receive);
    }

    private void sendTrx() {
//        Go to Send Activity
//        Intent send = new Intent(SendActivity.this, SendActivity.class);
//        startActivity(send);
    }

}
