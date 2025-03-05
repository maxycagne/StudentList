package com.example.studentlist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentlist.Database.Entity.Appointment;
import com.example.studentlist.databinding.CardAppointmentsBinding;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    private Context context;

    private List<Appointment> appointmentList;

    private AppointmentClick appointmentClick;

    public AppointmentAdapter(Context context, List<Appointment> appointmentList, AppointmentClick appointmentClick) {
        this.context = context;
        this.appointmentList = appointmentList;
        this.appointmentClick = appointmentClick;
    }

    public interface AppointmentClick{
        void Onclick(Appointment appointment);
    }

    public void refreshList(Context context)
    {
        this.appointmentList = appointmentList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CardAppointmentsBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.ViewHolder holder, int position) {

        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);


        Appointment appointment = appointmentList.get(position);

        holder.root.textAppointmentName.setText(appointment.getAppointmentName());
        holder.root.textDate.setText(appointment.getDate());
        holder.root.textTime.setText(appointment.getTime());
        holder.root.textStatus.setText(appointment.getStatus());
        holder.root.textDescription.setText(appointment.getDesc());

        holder.itemView.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardAppointmentsBinding root;
        public ViewHolder(@NonNull CardAppointmentsBinding root) {
            super(root.getRoot());
            this.root = root;
        }
    }
}
