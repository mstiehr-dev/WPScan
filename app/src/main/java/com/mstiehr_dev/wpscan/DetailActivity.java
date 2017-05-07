package com.mstiehr_dev.wpscan;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity
{
    private static final String TAG = "DetailActivity";
    private static final String EXTRA_SCAN_RESULT = "SCAN_RESULT";

    private ScanResult scanResult;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv = (TextView) findViewById(R.id.textView);


        this.scanResult = getIntent().getParcelableExtra(EXTRA_SCAN_RESULT);

        populate();
    }

    private void populate() {
        StringBuilder sb = new StringBuilder();

        sb
                .append("BSSID: ").append(scanResult.BSSID).append("\n")
                .append("SSID: ").append(scanResult.SSID).append("\n")
                .append("capabilities: ").append(scanResult.capabilities).append("\n")
                .append("describeContents: ").append(scanResult.describeContents()).append("\n")
                .append("frequency: ").append(scanResult.frequency).append("\n")
                .append("level: ").append(scanResult.level).append("\n");

        tv.setText(sb.toString());
    }

    static Intent newIntent(Context context, ScanResult scanResult)
    {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_SCAN_RESULT, scanResult);

        return intent;
    }
}
