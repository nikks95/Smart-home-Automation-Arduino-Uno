package com.nank.shas.shas;



import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MyBluetoothAdapter extends RecyclerView.Adapter<MyBluetoothAdapter.ViewHolder> {

    private List<ListItem> list;
    private Context context;
    public static  String EXTRA_ADDRESS;
    public MyBluetoothAdapter(List<ListItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v,context,list);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem listItem = list.get(position);
        holder.dname.setText(listItem.getName());
        holder.dadd.setText(listItem.getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView dname, dadd;
    Button connect;
    Context context;
        public ViewHolder(View itemView,Context context,List<ListItem> list) {
            super(itemView);
            this.context = context;
            dname = (TextView)itemView.findViewById(R.id.dname);
            dadd = (TextView)itemView.findViewById(R.id.dadd);
            connect = (Button)itemView.findViewById(R.id.connectToDevice);
            connect.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String address=  list.get(position).getAddress();
            BluetoothConnectionData.BluetoothDeviceName = list.get(position).getName();
            BluetoothConnectionData.BluetoothDeviceAddress = list.get(position).getAddress();
            Intent intent = new Intent(this.context,StartAutomation.class);
            intent.putExtra("BTaddress",address);
            this.context.startActivity(intent);


        }
    }
}
