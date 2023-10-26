package com.example.banggaevcs;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class BluetoothPermission {
    private static final int BLUETOOTH_PERMISSION_REQUEST_CODE = 1;
    private static final int BLUETOOTH_SCAN_REQUEST_CODE = 2;
    private final Activity activity;
    public BluetoothPermission(Activity activity) {
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private boolean checkBluetoothPermissionAndroidT() {
        String bluetoothPermission = android.Manifest.permission.BLUETOOTH;
        String adminPermission = android.Manifest.permission.BLUETOOTH_ADMIN;
        String scanPermission = android.Manifest.permission.BLUETOOTH_SCAN;
        String connectPermission = android.Manifest.permission.BLUETOOTH_CONNECT;

        return ContextCompat.checkSelfPermission(activity, bluetoothPermission) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, adminPermission) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, connectPermission) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, scanPermission) == PackageManager.PERMISSION_GRANTED
                ;
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void requestBluetoothPermissionsAndroidT() {
        String[] permissions = {
                android.Manifest.permission.BLUETOOTH,
                android.Manifest.permission.BLUETOOTH_ADMIN,
                android.Manifest.permission.BLUETOOTH_CONNECT,
                android.Manifest.permission.BLUETOOTH_SCAN,
        };
        ActivityCompat.requestPermissions(activity, permissions, BLUETOOTH_PERMISSION_REQUEST_CODE);
    }
    @RequiresApi(api = Build.VERSION_CODES.S)
    private boolean checkBluetoothPermissionsAndroidS() {
        String bluetoothPermission = android.Manifest.permission.BLUETOOTH;
        String adminPermission = android.Manifest.permission.BLUETOOTH_ADMIN;
        String scanPermission = android.Manifest.permission.BLUETOOTH_SCAN;
        String connectPermission = android.Manifest.permission.BLUETOOTH_CONNECT;

        return ContextCompat.checkSelfPermission(activity, bluetoothPermission) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, adminPermission) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, connectPermission) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, scanPermission) == PackageManager.PERMISSION_GRANTED
                ;
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void requestBluetoothPermissionsAndroidS() {
        String[] permissions = {
                android.Manifest.permission.BLUETOOTH,
                android.Manifest.permission.BLUETOOTH_ADMIN,
                android.Manifest.permission.BLUETOOTH_CONNECT,
                android.Manifest.permission.BLUETOOTH_SCAN,
        };
        ActivityCompat.requestPermissions(activity, permissions, BLUETOOTH_PERMISSION_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private boolean checkBluetoothPermissionsAndroidR() {
        String bluetoothPermission = android.Manifest.permission.BLUETOOTH;
        String adminPermission = android.Manifest.permission.BLUETOOTH_ADMIN;

        return ContextCompat.checkSelfPermission(activity, bluetoothPermission) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, adminPermission) == PackageManager.PERMISSION_GRANTED
                ;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void requestBluetoothPermissionsAndroidR() {
        String[] permissions = {
                android.Manifest.permission.BLUETOOTH,
                android.Manifest.permission.BLUETOOTH_ADMIN,
        };
        ActivityCompat.requestPermissions(activity, permissions, BLUETOOTH_PERMISSION_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private boolean checkBluetoothPermissionsAndroidQ() {
        String bluetoothPermission = android.Manifest.permission.BLUETOOTH;
        String adminPermission = android.Manifest.permission.BLUETOOTH_ADMIN;

        return ContextCompat.checkSelfPermission(activity, bluetoothPermission) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, adminPermission) == PackageManager.PERMISSION_GRANTED
                ;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void requestBluetoothPermissionsAndroidQ() {
        String[] permissions = {
                android.Manifest.permission.BLUETOOTH,
                android.Manifest.permission.BLUETOOTH_ADMIN,
        };
        ActivityCompat.requestPermissions(activity, permissions, BLUETOOTH_PERMISSION_REQUEST_CODE);
    }

    boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, BLUETOOTH_SCAN_REQUEST_CODE);
            return false;
        }
        return true;
    }

    boolean checkBluetoothPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (!checkBluetoothPermissionsAndroidQ()) {
                requestBluetoothPermissionsAndroidQ();
                return false;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!checkBluetoothPermissionsAndroidS()) {
                requestBluetoothPermissionsAndroidS();
                return false;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!checkBluetoothPermissionsAndroidR()) {
                requestBluetoothPermissionsAndroidR();
                return false;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!checkBluetoothPermissionAndroidT()) {
                requestBluetoothPermissionsAndroidT();
                return false;
            }
        }
        return true;

    }
}
