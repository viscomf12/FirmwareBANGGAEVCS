package com.example.banggaevcs;

import static android.widget.Toast.makeText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final int BLUETOOTH_PERMISSION_REQUEST_CODE = 1;
    private static final int BLUETOOTH_ENABLE_REQUEST_CODE = 2;
    private static final int BLUETOOTH_SCAN_REQUEST_CODE = 3;
    private static final int REQUEST_DISCOVERABLE = 4;
    private static final int CAMERA_PERMISSION_REQUEST = 5;
    static final UUID myServiceUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private boolean evcsFound = false;
    public DatabaseData databaseData;
    public TokenData tokenData;
    private OutputStream outputStream;
    private InputStream inputStream;
    private Handler mHandler;
    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;
    BluetoothDevice bluetoothDevice;
    DatabaseReference databaseReference;
    TextView statusbt, data1, data2, data3, token;
    ImageView status;
    Button onoff, scan, data, scanqr;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private boolean checkBluetoothPermissionAndroidT() {
        String bluetoothPermission = android.Manifest.permission.BLUETOOTH;
        String adminPermission = android.Manifest.permission.BLUETOOTH_ADMIN;
        String scanPermission = android.Manifest.permission.BLUETOOTH_SCAN;
        String connectPermission = android.Manifest.permission.BLUETOOTH_CONNECT;

        return ContextCompat.checkSelfPermission(this, bluetoothPermission) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, adminPermission) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, connectPermission) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, scanPermission) == PackageManager.PERMISSION_GRANTED
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
        ActivityCompat.requestPermissions(this, permissions, BLUETOOTH_PERMISSION_REQUEST_CODE);
    }
    @RequiresApi(api = Build.VERSION_CODES.S)
    private boolean checkBluetoothPermissionsAndroidS() {
        String bluetoothPermission = android.Manifest.permission.BLUETOOTH;
        String adminPermission = android.Manifest.permission.BLUETOOTH_ADMIN;
        String scanPermission = android.Manifest.permission.BLUETOOTH_SCAN;
        String connectPermission = android.Manifest.permission.BLUETOOTH_CONNECT;

        return ContextCompat.checkSelfPermission(this, bluetoothPermission) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, adminPermission) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, connectPermission) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, scanPermission) == PackageManager.PERMISSION_GRANTED
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
        ActivityCompat.requestPermissions(this, permissions, BLUETOOTH_PERMISSION_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private boolean checkBluetoothPermissionsAndroidR() {
        String bluetoothPermission = android.Manifest.permission.BLUETOOTH;
        String adminPermission = android.Manifest.permission.BLUETOOTH_ADMIN;

        return ContextCompat.checkSelfPermission(this, bluetoothPermission) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, adminPermission) == PackageManager.PERMISSION_GRANTED
                ;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void requestBluetoothPermissionsAndroidR() {
        String[] permissions = {
                android.Manifest.permission.BLUETOOTH,
                android.Manifest.permission.BLUETOOTH_ADMIN,
        };
        ActivityCompat.requestPermissions(this, permissions, BLUETOOTH_PERMISSION_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private boolean checkBluetoothPermissionsAndroidQ() {
        String bluetoothPermission = android.Manifest.permission.BLUETOOTH;
        String adminPermission = android.Manifest.permission.BLUETOOTH_ADMIN;

        return ContextCompat.checkSelfPermission(this, bluetoothPermission) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, adminPermission) == PackageManager.PERMISSION_GRANTED
                ;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void requestBluetoothPermissionsAndroidQ() {
        String[] permissions = {
                android.Manifest.permission.BLUETOOTH,
                android.Manifest.permission.BLUETOOTH_ADMIN,
        };
        ActivityCompat.requestPermissions(this, permissions, BLUETOOTH_PERMISSION_REQUEST_CODE);
    }

    private boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, BLUETOOTH_SCAN_REQUEST_CODE);
            return false;
        }
        return true;
    }

    private boolean checkBluetoothPermissions() {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = findViewById(R.id.status);
        statusbt = findViewById(R.id.statusbt);
        data1 = findViewById(R.id.data1);
        data2 = findViewById(R.id.data2);
        data3 = findViewById(R.id.data3);
        token = findViewById(R.id.token);
        onoff = findViewById(R.id.onoff);
        scan = findViewById(R.id.scan);
        data = findViewById(R.id.data);
        scanqr = findViewById(R.id.scanqr);
        scanqr.setEnabled(false);

        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        bluetoothAdapter = Bluetooth.getBluetoothAdapter();
        outputStream = Bluetooth.getOutputStream();

        IntentFilter bonding = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(bondReceiver, bonding);

        IntentFilter scanning = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(scanReceiver, scanning);

        generateToken();

        if (!bluetoothAdapter.isEnabled()) {
            status.setImageResource(R.drawable.ic_bluetooth_mati);
        } else {
            status.setImageResource(R.drawable.ic_bluetooth_nyala);
        }

        if (bluetoothAdapter == null) {
            statusbt.setText("Bluetooth Tidak Tersedia");
        } else {
            statusbt.setText("Bluetooth Tersedia");
        }

        onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled()) {
                    if (checkBluetoothPermissions()) {
                        bluetoothAdapter.enable();
                        status.setImageResource(R.drawable.ic_bluetooth_nyala);
                        makeText(MainActivity.this, "Sedang Dinyalakan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    bluetoothAdapter.disable();
                    status.setImageResource(R.drawable.ic_bluetooth_mati);
                    makeText(MainActivity.this, "Sedang Dimatikan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLocationPermission()) {
                    startBluetoothScan();
                }
            }
        });

        scanqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanOptions opsi=new ScanOptions();
                opsi.setPrompt("Pindai Scan QR");
                opsi.setBeepEnabled(true);
                opsi.setOrientationLocked(true);
                opsi.setCaptureActivity(ScanQrActivity.class);
                hasil.launch(opsi);
            }
        });

        data.setOnClickListener(view -> {
            if (Bluetooth.isConnected()) {
                Intent intent = new Intent(MainActivity.this, DataListActivity.class);
                startActivity(intent);
            } else {
                makeText(MainActivity.this, "Belum Terhubung", Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String csid = dataSnapshot.child("CSID").getValue(String.class);
                    String time= dataSnapshot.child("Time").getValue(String.class);
                    String token = dataSnapshot.child("Token").getValue(String.class);

                    databaseData = new DatabaseData(csid, time, token);

                    data1.setText(csid);
                    data2.setText(token);
                    data3.setText(time);

                } else {
                    data1.setText("Data tidak ditemukan");
                    data2.setText("Data tidak ditemukan");
                    data3.setText("Data tidak ditemukan");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public String generateToken() {
        SecureRandom random = new SecureRandom();
        int a = random.nextInt(10000) + 1;
        int maxB = 10000 - a;
        int b = random.nextInt(maxB) + 1;
        int c = 2 * b ;
        int d = 10000 - (b + a) + c;

        String tokenevcs = a + "," + b + "," + c + "," + d;
        token.setText(tokenevcs);
        tokenData = new TokenData(tokenevcs);
        return tokenevcs;
    }

    ActivityResultLauncher<ScanOptions> hasil=registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            String pesan = result.getContents();
            processScannedData(pesan);
        }
    });

    private void processScannedData(String pesan) {
        String[] parts = pesan.split("/");
        if (parts.length == 4) {
            String uuid = parts[1];
            String macAddress = parts[2];
            String deviceName = parts[3];
            pairAndConnectDevice(macAddress);
        } else {
            Toast.makeText(this, "Error Connecting", Toast.LENGTH_SHORT).show();
        }
    }

    private void pairAndConnectDevice(String macAddress) {
        try {
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(macAddress);
            if (device != null && device.getBondState() == BluetoothDevice.BOND_NONE) {
                device.createBond();
                Toast.makeText(this, "Pairing With " + device.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Connecting...", Toast.LENGTH_SHORT).show();
                Log.d("QR Code", "Connecting...");
                connection(macAddress);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void startBluetoothScan() {
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        bluetoothAdapter.startDiscovery();
        Toast.makeText(this, "Scanning...", Toast.LENGTH_SHORT).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BLUETOOTH_ENABLE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                bluetoothAdapter.enable();
            } else {
                makeText(MainActivity.this, "Dapat Terlihat", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_DISCOVERABLE) {
            if (resultCode == 300) {
                makeText(MainActivity.this, "Terlihat Untuk 5 Menit", Toast.LENGTH_SHORT).show();
            } else {
                makeText(MainActivity.this, "Tidak Dapat Terlihat", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                makeText(this, "Izin kamera diberikan.", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                makeText(this, "Izin kamera tidak diberikan.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private final BroadcastReceiver bondReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                int bondState = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                if (bondState == BluetoothDevice.BOND_BONDED) {
                    assert device != null;
                    try {
                        connection(device.getAddress());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    };

    private final BroadcastReceiver scanReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device != null) {
                    if (device.getName() != null && device.getName().equals("ESP Bluetooth EVCS")) {
                        if (!evcsFound) {
                            evcsFound = true;
                            scanqr.setEnabled(true);
                            Toast.makeText(MainActivity.this, "EVCS Found!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bondReceiver);
        unregisterReceiver(scanReceiver);
    }

    public void connection(String macAddress) throws IOException {
        try {
            bluetoothDevice = bluetoothAdapter.getRemoteDevice(macAddress);
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(myServiceUUID);
            bluetoothSocket.connect();
            inputStream=bluetoothSocket.getInputStream();
            outputStream=bluetoothSocket.getOutputStream();
            String tokenEvcs = generateToken() + "\n";
            byte[] tokenEvcsBytes = tokenEvcs.getBytes();
            outputStream.write(tokenEvcsBytes);
            if(databaseData != null) {
                String csidToSend = databaseData.getCsid() + "\n";
                String timeToSend = databaseData.getTime() + "\n";
                byte[] csidBytes = csidToSend.getBytes();
                byte[] timeBytes = timeToSend.getBytes();
                outputStream.write(csidBytes);
                outputStream.write(timeBytes);
            }
            Bluetooth.setBluetoothDevice(bluetoothDevice);
            Bluetooth.setBluetoothSocket(bluetoothSocket);
            Bluetooth.setInputStream(inputStream);
            Bluetooth.setOutputStream(outputStream);
            Toast.makeText(this, "Connected to " + bluetoothDevice.getName(), Toast.LENGTH_SHORT).show();
            Bluetooth.setConnected(true);
        } catch (Exception e) {
            Bluetooth.closeBluetoothSocket();
            Bluetooth.closeInputStream();
            Bluetooth.closeOutputStream();
            Toast.makeText(this, "Disconnected to " + bluetoothDevice.getName(), Toast.LENGTH_SHORT).show();
            Bluetooth.setConnected(false);
        }
    }
}