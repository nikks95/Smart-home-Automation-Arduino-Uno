package com.nank.shas.shas;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class StartAutomation extends AppCompatActivity implements View.OnClickListener {
    Button btConnect;
    Button light1,light2;
    Button fan1;

    boolean islight1on = false;
    boolean islight2on=false;
    boolean isfan1on=false;

    String address="";
    TextView connectedDeviceName;

    private ProgressDialog progress;
    private BluetoothAdapter myBluetooth = null;
    private BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    public static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_automation);

        connectedDeviceName=(TextView) findViewById(R.id.connectedDevice);

        btConnect = (Button)findViewById(R.id.connect);
        light1 = (Button)findViewById(R.id.light1);
        light2 = (Button)findViewById(R.id.light2);
        fan1 = (Button)findViewById(R.id.fan1);
        if(!BluetoothConnectionData.BluetoothDeviceName.equals("none")) {
            new ConnectBT().execute();
        }


        address = BluetoothConnectionData.BluetoothDeviceAddress;

        btConnect.setOnClickListener(this);
        light1.setOnClickListener(this);
        light2.setOnClickListener(this);
        fan1.setOnClickListener(this);

        connectedDeviceName.setText(BluetoothConnectionData.BluetoothDeviceName);

    }

    @Override
    public void onClick(View v) {
        if(v==btConnect)
        {
            Intent intent = new Intent(StartAutomation.this,BluetoothConnection.class);
            startActivity(intent);
            finish();
        }
        else if(v==light1){
            if(isBtConnected){
            if(!islight1on) {
                islight1on = ledStateChanger("a", islight1on);
                greenColorForButton(light1);
            }
            else
            {
                islight1on = ledStateChanger("b",islight1on);
                redColorForButton(light1);
            }
            }
            else {
                msg("Connect to device first");
            }
        }
        else if(v==light2){ }
        else if(v==fan1){}
        else {msg("Select valid input");}
    }
    private class ConnectBT extends AsyncTask<Void,Void,Void>{

        private boolean ConnectSuccess = true;
        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(StartAutomation.this, "Connecting: "+BluetoothConnectionData.BluetoothDeviceName, "Please wait!!!");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;

            }
            progress.dismiss();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;
            }
            return null;
        }
    }
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
    boolean ledStateChanger(String id,boolean status){
        if(!status){
            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write(id.toString().getBytes());
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
        }
        else{

        }
        return !status;
    }
    void greenColorForButton(Button b){
    b.setText("OFF");
    b.setBackgroundColor(Color.parseColor("#00bfa5"));
    }
    void redColorForButton(Button b){
    b.setText("ON");
    b.setBackgroundColor(Color.parseColor("#fa1c20"));
    }
}
