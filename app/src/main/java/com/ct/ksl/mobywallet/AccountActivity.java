package com.ct.ksl.mobywallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by sreek on 27/05/18.
 */

public class AccountActivity extends AppCompatActivity {

    TextView addressText;
    TextView nameText;
    TextView transacText;

    String[] name;
    String[] transac;
    String[] address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);

        addressText = findViewById(R.id.addressText);
        nameText = findViewById(R.id.nameText);
        transacText= findViewById(R.id.transacText);

        name = getResources().getStringArray(R.array.name);
        int randomIndex = new Random().nextInt(name.length);
        String randomname = name[randomIndex];
        nameText.setText(randomname);

        address = getResources().getStringArray(R.array.address);
        int randomAddressIndex = new Random().nextInt(address.length);
        String randomAddress = address[randomAddressIndex];
        addressText.setText(randomAddress);

        transac = getResources().getStringArray(R.array.transactions);
        int randomTransacIndex = new Random().nextInt(transac.length);
        String randomTran = transac[randomTransacIndex];
        transacText.setText(randomTran);

//        Intent account = getIntent();
//        String address = account.getStringExtra("address");
//        addressText = findViewById(R.id.addressText);
//        addressText.setText(address);
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
//        voting activity
        Intent vote = new Intent(AccountActivity.this, VotingActivity.class);
        startActivity(vote);

    }

    private void freezeTrx() {
//        Go to Freeze Activity
        Intent freeze = new Intent(AccountActivity.this, FreezeActivity.class);
        startActivity(freeze);
    }

    private void receiveTrx() {
//        Go to Receive Activity
        Intent receive = new Intent(AccountActivity.this, ReceiveActivity.class);
        startActivity(receive);
    }

    private void sendTrx() {
//        Go to Send Activity
        Intent send = new Intent(AccountActivity.this, SendActivity.class);
        startActivity(send);
    }


}
