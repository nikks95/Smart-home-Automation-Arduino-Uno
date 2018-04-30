package com.nank.shas.shas;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothConnection extends AppCompatActivity implements View.OnClickListener{
private Button findDevice;
private BluetoothAdapter myBluetoothAdapter = null;
RecyclerView recyclerView;
RecyclerView.Adapter adapter;
private Set<BluetoothDevice> pairedDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_connection);
        findDevice = (Button)findViewById(R.id.findDevice);

        recyclerView  =(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        findDevice.setOnClickListener(this);
        if(myBluetoothAdapter==null){
            Toast.makeText(BluetoothConnection.this,"No bluetooth found on your phone",Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            if(!myBluetoothAdapter.isEnabled()){
                Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnBTon,1);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v==findDevice){
                getListofPairedDevice();
        }
    }

    void getListofPairedDevice() {
        pairedDevice = myBluetoothAdapter.getBondedDevices();
        List<ListItem> list = new ArrayList<>();
        if (pairedDevice.size()>0)
        {
            for(BluetoothDevice bt : pairedDevice)
            {
                list.add(new ListItem(bt.getName(),bt.getAddress())); //Get the device's name and the address
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }
            adapter = new MyBluetoothAdapter(list,this);
            recyclerView.setAdapter(adapter);

    }
}
