package com.example.banggaevcs;

import static android.widget.Toast.makeText;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ScheduledExecutorService;

public class DataListActivity extends AppCompatActivity {
    private int selectedTime = 0;
    MainActivity mainActivity;
    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;
    BluetoothDevice bluetoothDevice;
    InputStream inputStream;
    OutputStream outputStream;
    TextView kwh, data1, data2, data3, data4;
    EditText kontol;
    Button kembali, send, disconnect;
    ImageView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);

        data1 = findViewById(R.id.data1);
        data2 = findViewById(R.id.data2);
        data3 = findViewById(R.id.data3);
        data4 = findViewById(R.id.data4);
        kembali = findViewById(R.id.kembali);
        kwh = findViewById(R.id.kwh);
        kontol = findViewById(R.id.kontol);
        send = findViewById(R.id.send);
        disconnect = findViewById(R.id.disconnect);
        status = findViewById(R.id.status);

        bluetoothAdapter = Bluetooth.getBluetoothAdapter();
        bluetoothSocket = Bluetooth.getBluetoothSocket();
        bluetoothDevice = Bluetooth.getBluetoothDevice();
        inputStream = Bluetooth.getInputStream();
        outputStream = Bluetooth.getOutputStream();

        mainActivity = new MainActivity();

        if (bluetoothSocket != null) {
            status.setImageResource(R.drawable.ic_terhubung);
        } else {
            status.setImageResource((R.drawable.ic_tidak_terhubung));
        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = kontol.getText().toString();
                if (outputStream != null) {
                    try {
                        outputStream.write(message.getBytes());
                        makeText(DataListActivity.this, "Pesan Terkirim: " + message, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        makeText(DataListActivity.this, "Gagal Mengirim Pesan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    makeText(DataListActivity.this, "Bluetooth tidak terhubung.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bluetoothSocket.isConnected()) {
                    Bluetooth.closeBluetoothSocket();
                    Bluetooth.closeInputStream();
                    Bluetooth.closeOutputStream();
                    status.setImageResource((R.drawable.ic_tidak_terhubung));
                    makeText(DataListActivity.this, "Disconnecting From " + bluetoothDevice.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    makeText(DataListActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        kembali.setOnClickListener(view -> {
            Intent intent = new Intent(DataListActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] buffer = new byte[1024];
                int bytes;

                while (Bluetooth.isConnected()) {
                    try {
                        bytes = inputStream.read(buffer);
                        String receivedMessage = new String(buffer, 0, bytes);

                        Log.d("BluetoothDebug", "dapet Message from Bluetooth: " + receivedMessage);
                        displayReceivedMessage(receivedMessage);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Bluetooth.setConnected(false);
                    }
                }
            }

            private void displayReceivedMessage(String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] data = message.split(",");
                        String[] eachData = new String[data.length];
                        for(int i=0; i<data.length; i++){
                            eachData[i] = String.valueOf(data[i]);
                        }
                        data1.setText(eachData[0]);
                        data2.setText(eachData[1]);
                        data3.setText(eachData[2]);
                        data4.setText(message);
                    }
                });
            }
        });

        readThread.start();
    }
}

