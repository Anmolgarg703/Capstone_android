package com.example.anmol.bluetooth_test;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
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

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //Finding devices
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                AbstractList mArray = new ArrayList();
                mArray.add(device.getName() + "\n" + device.getAddress());
                Toast.makeText(getApplicationContext(),device.getName() ,Toast.LENGTH_LONG).show();
                //ArrayAdapter adapter = new ArrayAdapter(this, simple_list_item_1, mArray);
                //lv.setAdapter(adapter);
            }
        }
    };

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
        BA.startDiscovery();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
    }

    /*private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                ArrayList list = new ArrayList();
                list.add(device.getName() + "\n" + device.getAddress());

                //ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);

                //lv.setAdapter(adapter);

            }
        }
    };

    //@Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return super.registerReceiver(receiver, filter);
    }

    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    registerReceiver(mReceiver, filter);*/



    public void list(View v){
        pairedDevices = BA.getBondedDevices();
        ArrayList list = new ArrayList();

        for(BluetoothDevice bt : pairedDevices)
            list.add(bt);
        Toast.makeText(getApplicationContext(),"Showing Paired Devices",Toast.LENGTH_SHORT).show();

        ArrayAdapter adapter = new ArrayAdapter(this, simple_list_item_1, list);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //public static final UUID MY_UUID = ;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final UUID MY_UUID = UUID.fromString("0000110E-0000-1000-8000-00805F9B34FB");
                BluetoothDevice item = (BluetoothDevice) lv.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), item.getName(), Toast.LENGTH_SHORT).show();

                /*BluetoothDevice device = BA.getRemoteDevice(item.getAddress());
                connect(device);
                /*BluetoothSocket tmp = null;
                BluetoothSocket mmSocket = null;

                try{
                    tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
                    Method m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
                    tmp = (BluetoothSocket) m.invoke(device, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                mmSocket = tmp;*/
            }
        });
    }
}
