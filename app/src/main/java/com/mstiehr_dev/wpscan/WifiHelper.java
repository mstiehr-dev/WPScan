package com.mstiehr_dev.wpscan;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.net.wifi.WpsInfo;
import android.util.Log;

import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;


public class WifiHelper extends Observable
{
    private static final String TAG = "WifiHelper";

    private App context;
    private WifiManager wifiManager;
    private Set<ScanResult> scanResults;

    public WifiHelper(App context)
    {
        this.context = context;
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        scanResults = new HashSet<>();
    }

    public void doScan()
    {
        if(!wifiManager.isWifiEnabled())
        {
            Log.d(TAG, "doScan: wifi is not enabled");
        }

        wifiManager.startScan();
    }

    public void handleResults()
    {
        List<ScanResult> tResults = wifiManager.getScanResults();
        for(ScanResult result : tResults)
        {
            Log.d(TAG, result.SSID + " -> " + result.capabilities);
            if(result.capabilities.contains("WPS"))
            {
                scanResults.add(result);
            }
        }

//        for(ScanResult wpsResult : scanResults)
//        {
//            connectToWPS(wpsResult);
//        }

        setChanged();
        notifyObservers();
    }

    public void connectToWPS(ScanResult scanResult)
    {
        WpsInfo wpsInfo = new WpsInfo();
        wpsInfo.BSSID = scanResult.BSSID;
        wpsInfo.pin = "123456789";
        wpsInfo.setup = 0;
        
        wifiManager.startWps(wpsInfo, new WifiManager.WpsCallback() {
            @Override
            public void onStarted(String pin) {
                Log.d(TAG, "onStarted: " + pin);
            }

            @Override
            public void onSucceeded() {
                Log.d(TAG, "onSucceeded: ");
            }

            @Override
            public void onFailed(int reason) {
                Log.d(TAG, "onFailed: " + reason);
            }
        });
    }

    public Set<ScanResult> getScanResults() {
        return scanResults;
    }
}
