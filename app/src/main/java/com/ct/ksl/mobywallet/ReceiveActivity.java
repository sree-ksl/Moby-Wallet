package com.ct.ksl.mobywallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by sreek on 27/05/18.
 */

public class ReceiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
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
        Intent vote = new Intent(ReceiveActivity.this, VotingActivity.class);
        startActivity(vote);
    }

    private void freezeTrx() {
//        Go to Freeze Activity
        Intent freeze = new Intent(ReceiveActivity.this, FreezeActivity.class);
        startActivity(freeze);
    }

    private void receiveTrx() {
//        Go to Receive Activity
    }

    private void sendTrx() {
//        Go to Send Activity
        Intent send = new Intent(ReceiveActivity.this, SendActivity.class);
        startActivity(send);
    }
}
