package com.zeidex.eldalel.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zeidex.eldalel.utils.Constants.PERMISSION_REQUEST_CODE;


public class AppPermissions {
    Activity context;
    String[] appPermissions;

    public AppPermissions(Activity context ,  String[] appPermissions){
        this.context = context;
        this.appPermissions = appPermissions;
    }

    public boolean checkAndRequestPermission() {
        //check which permissions is granted
        List<String> listPermissionNedded = new ArrayList<>();
        for (String perm : appPermissions) {
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                listPermissionNedded.add(perm);
            }
        }
        // Ask for non-granted permissions
        if (!listPermissionNedded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionNedded.toArray(new String[listPermissionNedded.size()]), PERMISSION_REQUEST_CODE);
            return false;
        }
        //App has all permissions
        return true;
    }

    public boolean onRequestPermissionsResult(int requestCode, int[] grantResults, String[] permissions) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            HashMap<String, Integer> permissionResult = new HashMap<>();
            int deniedCount = 0;
            //permission grant results
            for (int i = 0; i < grantResults.length; i++) {
                // Add only permissions which are denied
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionResult.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }
            // check if all permission are granted
            if (deniedCount == 0) {
                    return true;
            } else {
                for (Map.Entry<String, Integer> entry : permissionResult.entrySet()) {
                    String perName = entry.getKey();
                    int perResult = entry.getValue();
                    if (ActivityCompat.shouldShowRequestPermissionRationale(context, perName)) {
                        //show dialog of explanation
                        showDialog("Permission Required", "You should allow permission to run this activity", "Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                alertDialog.cancel();
                                checkAndRequestPermission();
                            }
                        }, "No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                alertDialog.cancel();
                                context.finish();
                            }
                        }, false);
                        alertDialog.show();
                        // permission is denied and never ask again is checked
                        //shouldShowRequestPermissionRationale return false
                    } else {
                        showDialog("Settings", "Go To Settings", "settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                alertDialog.cancel();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", context.getPackageName(), null));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                context.finish();
                            }
                        }, "No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                alertDialog.cancel();
                                context.finish();
                            }
                        }, false);
                        alertDialog.show();
                    }
                }
            }
        }
        return false;
    }
    androidx.appcompat.app.AlertDialog alertDialog;
    public void showDialog(String title, String msg, String positiveLabel, DialogInterface.OnClickListener
            positiveOnClick, String negativeLabel, DialogInterface.OnClickListener negativeOnClick, boolean isCanclable) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(isCanclable);
        builder.setPositiveButton(positiveLabel, positiveOnClick);
        builder.setNegativeButton(negativeLabel, negativeOnClick);


        alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button button2 = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something

                        //Dismiss once everything is OK.
                       alertDialog.dismiss();
                    }
                });

                button2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something

                        //Dismiss once everything is OK.
                        alertDialog.dismiss();
                    }
                });
            }
        });

    }
}
