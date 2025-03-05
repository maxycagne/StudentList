package com.example.studentlist.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.studentlist.Activities.ActivityDashboard;
import com.example.studentlist.Activities.ActivityView;
import com.example.studentlist.Adapter.AppointmentAdapter;
import com.example.studentlist.Database.Dao.AppointmentDao;
import com.example.studentlist.Database.DbHelper;
import com.example.studentlist.Database.Entity.Appointment;
import com.example.studentlist.R;
import com.example.studentlist.databinding.FragmentFragmentHomeBinding;

import java.util.List;
import java.util.concurrent.Executors;


public class FragmentHome extends Fragment {


    private FragmentFragmentHomeBinding root;

    private DbHelper dbHelper;
    private Handler handler;

    private String status;



    public void setDb()
    {
        dbHelper = new DbHelper(getContext());
        handler = new Handler(Looper.getMainLooper());
    }

    public FragmentHome() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retriveAppointment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = FragmentFragmentHomeBinding.inflate(inflater,container,false);
        retriveAppointment();
        setDb();



        return root.getRoot();
    }

    private void retriveAppointment()
    {
        Executors.newSingleThreadExecutor().submit(()->
        {
            AppointmentDao appointmentDao = ((ActivityDashboard)getActivity()).getDbHelper().getAppointmentDao();
            List<Appointment> appointmentList = appointmentDao.getAll();

            ((ActivityDashboard)getActivity()).getHandler().post(()->{

                if(appointmentList.isEmpty())
                {
                    root.txtNoAppointment1.setVisibility(View.VISIBLE);
                    root.txtNoAppointment2.setVisibility(View.VISIBLE);
                    root.rvAppointment.setVisibility(View.GONE);
                }

                else
                {
                    root.txtNoAppointment1.setVisibility(View.GONE);
                    root.txtNoAppointment2.setVisibility(View.GONE);
                    root.rvAppointment.setVisibility(View.VISIBLE);

                    AppointmentAdapter adapter = new AppointmentAdapter(getContext(), appointmentList, new AppointmentAdapter.AppointmentClick() {
                        @Override
                        public void Onclick(Appointment appointment) {
                            startActivity(new Intent(getContext(), ActivityView.class).putExtra("id",appointment.getId()));
                        }
                    });

                    root.rvAppointment.setAdapter(adapter);
                }
            });
        });
    }




    @Override
    public void onResume() {
        super.onResume();

        retriveAppointment();
    }


}