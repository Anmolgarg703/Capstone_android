package com.example.anmol.bluetooth_test;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static android.R.layout.simple_list_item_1;

public class MainActivity extends AppCompatActivity{

    Button b1,b2,b3,b4;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    ListView lv;
    BluetoothSocket mSocket = null;
    /*ThreadBeConnected myThreadBeConnected;


    private class ThreadBeConnected extends Thread {

        private BluetoothServerSocket bluetoothServerSocket = null;

        public ThreadBeConnected(UUID myUUID) {
            try {
                bluetoothServerSocket =
                        BA.listenUsingRfcommWithServiceRecord("Server", myUUID);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            BluetoothSocket bluetoothSocket = null;

            if(bluetoothServerSocket!=null){
                try {
                    bluetoothSocket = bluetoothServerSocket.accept();

                    BluetoothDevice remoteDevice = bluetoothSocket.getRemoteDevice();

                    //connected
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            //textStatus.setText(strConnected);
                        }});

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                    final String eMessage = e.getMessage();
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            //textStatus.setText("something wrong: \n" + eMessage);
                        }});
                }
            }else{
                runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                       // textStatus.setText("bluetoothServerSocket == null");
                    }});
            }
        }
        public void cancel() {

            Toast.makeText(getApplicationContext(),
                    "close bluetoothServerSocket",
                    Toast.LENGTH_LONG).show();

            try {
                bluetoothServerSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);

        BA = BluetoothAdapter.getDefaultAdapter();
        lv = (ListView)findViewById(R.id.listView);
    }

    public void on(View v){
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(),"Turned on",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Already on", Toast.LENGTH_SHORT).show();
        }
    }

    public void off(View v){
        BA.disable();
        Toast.makeText(getApplicationContext(),"Turned off" ,Toast.LENGTH_SHORT).show();
    }

    public void visible(View v){
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);
    }

    public void discover(View v){
        if(BA.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Inside Discover", Toast.LENGTH_SHORT).show();
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            BA.startDiscovery();
            registerReceiver(mReceiver, filter);
        }
        else{
            Toast.makeText(getApplicationContext(), "Enable Bluetooth", Toast.LENGTH_SHORT).show();
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                Toast.makeText(getApplicationContext(),"Discovery started" ,Toast.LENGTH_SHORT).show();
            }
            else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                Toast.makeText(getApplicationContext(),"Discovery finished" ,Toast.LENGTH_SHORT).show();
            }
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                AbstractList mArray = new ArrayList();
                mArray.add(device.getName());
                Toast.makeText(getApplicationContext(),device.getName() ,Toast.LENGTH_LONG).show();
                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, simple_list_item_1, mArray);
                lv.setAdapter(adapter);
            }
        }
    };

    public void list(View v){
        if(BA.isEnabled()) {
            TextView text = (TextView) findViewById(R.id.textView2);

            pairedDevices = BA.getBondedDevices();
            ArrayList list = new ArrayList();

            for (BluetoothDevice bt : pairedDevices)
                list.add(bt);
            Toast.makeText(getApplicationContext(), "Showing Paired Devices", Toast.LENGTH_SHORT).show();
            ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, simple_list_item_1, list);
            lv.setAdapter(adapter);
            text.setText("Paired Devices:");
        }
        else{
            Toast.makeText(getApplicationContext(), "Enable Bluetooth", Toast.LENGTH_SHORT).show();
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice device = (BluetoothDevice) lv.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), device.getName(), Toast.LENGTH_SHORT).show();

               /* myThreadBeConnected = new ThreadBeConnected(UUID.fromString("ec79da00-853f-11e4-b4a9-0800200c9a66"));
                myThreadBeConnected.start();*/
                
                try{
                    mSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
                    Toast.makeText(getApplicationContext(), "Creating Socket", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Socket Exception", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                try{
                    mSocket.connect();
                    Toast.makeText(getApplicationContext(), "Attempting to connect", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Connection exception", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
}
