package com.example.banggaevcs;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ScheduledExecutorService;

public class Bluetooth {
    private static BluetoothAdapter bluetoothAdapter;
    private static BluetoothSocket bluetoothSocket;
    private static BluetoothDevice bluetoothDevice;
    private static InputStream inputStream;
    private static OutputStream outputStream;
    private static boolean isConnected = false;
    public static void setConnected(boolean connected) {
        isConnected = connected;
    }
    public static boolean isConnected() {
        return isConnected;
    }
    public static BluetoothAdapter getBluetoothAdapter() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter;
    }
    public static BluetoothSocket getBluetoothSocket() {
        return bluetoothSocket;
    }
    public static void setBluetoothSocket(BluetoothSocket socket) {
        bluetoothSocket = socket;
    }
    public static BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }
    public static void setBluetoothDevice(BluetoothDevice device) {
        bluetoothDevice = device;
    }
    public static InputStream getInputStream() {
        return inputStream;
    }
    public static void setInputStream(InputStream input) {
        inputStream = input;
    }
    public static OutputStream getOutputStream() {
        return outputStream;
    }
    public static void setOutputStream(OutputStream output) {
        outputStream = output;
    }
    public static void closeBluetoothSocket() {
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void closeInputStream() {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void closeOutputStream() {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
