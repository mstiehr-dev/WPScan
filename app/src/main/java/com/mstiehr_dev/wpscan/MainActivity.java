package com.mstiehr_dev.wpscan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements Observer
{
    private static final String TAG = "MainActivity";

    WifiHelper wifiHelper;
    List<ScanResult> scanResults;

    ListView listView;
    BaseAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanResults = new ArrayList<>();

        listView = (ListView) findViewById(android.R.id.list);
        listView.setEmptyView(findViewById(android.R.id.empty));
        adapter = new ScanResultAdapter();
        listView.setAdapter(adapter);


        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                wifiHelper.handleResults();
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));


        wifiHelper = new WifiHelper(App.get(this));
        wifiHelper.addObserver(this);
        wifiHelper.doScan();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        scanResults.clear();
        scanResults.addAll(wifiHelper.getScanResults());
        adapter.notifyDataSetChanged();
    }

    class ScanResultAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return scanResults.size();
        }

        @Nullable
        @Override
        public ScanResult getItem(int position) {
            return scanResults.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            ScanResult item = getItem(position);

            if(null==convertView)
            {
                convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
            }
            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText(item.SSID);

            return convertView;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }
}
