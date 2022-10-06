package com.example.heremiStartup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.NotifHolder> {


    private ArrayList<modelLogRes> res;

    private Context context;



    public NotifAdapter(Context context, ArrayList<modelLogRes> res) {
        this.res = res;
        this.context = context;
    }

    public NotifAdapter() {

    }

    @NonNull
    @Override
    public NotifAdapter.NotifHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_notification, parent, false);

        return new NotifHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifAdapter.NotifHolder holder, int position) {
            modelLogRes modelReminderRes = res.get(position);
            holder.setNotifDetails(modelReminderRes);

    }

    @Override
    public int getItemCount() {
        return res.size();
    }

    public class NotifHolder extends RecyclerView.ViewHolder {
        TextView header;
        TextView note;
        TextView done;
        TextView edit;
        public NotifHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.notif_header);
            note = itemView.findViewById(R.id.notif_note);
            edit = itemView.findViewById(R.id.notif_edit);
            done = itemView.findViewById(R.id.notif_done);

        }

        @SuppressLint("SetTextI18n")
        void setNotifDetails(modelLogRes res) {
            header.setText(res.getMed_name()+" at "+ res.getTime());
            note.setText("Dose: "+res.getMed_dose());


        }
    }
}
