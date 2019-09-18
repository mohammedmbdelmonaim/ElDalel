package com.zeidex.eldalel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.zeidex.eldalel.utils.NetworkUtil;

import es.dmoral.toasty.Toasty;


public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast. throw new UnsupportedOperationException("Not yet implemented");
            String status = NetworkUtil.getConnectivityStatusString(context);
            if (status != null){
                Toasty.error(context, status, Toast.LENGTH_LONG).show();
            }

        }
}
