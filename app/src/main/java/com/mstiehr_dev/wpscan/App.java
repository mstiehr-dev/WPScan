package com.mstiehr_dev.wpscan;

import android.app.Application;
import android.content.Context;


public class App extends Application
{
    public static App get(Context ctx)
    {
        return (App) ctx.getApplicationContext();
    }
}
