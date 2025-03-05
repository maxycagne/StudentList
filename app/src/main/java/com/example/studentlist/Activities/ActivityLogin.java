package com.example.studentlist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentlist.Database.DbHelper;
import com.example.studentlist.Database.Entity.User;
import com.example.studentlist.R;
import com.example.studentlist.databinding.ActivityLoginBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ActivityLogin extends AppCompatActivity {

    private ActivityLoginBinding root;

    private Handler handler;

    private DbHelper dbHelper;

    private void setDb()
    {
        handler = new Handler(Looper.getMainLooper());
        dbHelper = new DbHelper(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        root = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setDb();


        root.buttonLogin.setOnClickListener(v->{
            //login();
            startActivity(new Intent(this,ActivityDashboard.class));

        });

        root.txtRegister.setOnClickListener(v->{
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
    }

    public void login()
    {
        validate();
        String phoneNumber = "+639" + root.txtPhoneNumber.getText().toString();
        String password = root.txtPassword.getText().toString();
        Executors.newSingleThreadExecutor().submit(()->{
            User user = dbHelper.getUserDao().findUserByUsernamePass(phoneNumber,password);
            handler.post(()->{
                if(user != null)
                {
                    if(user.getPriv().equals("user"))
                    {
                        startActivity(new Intent(this,ActivityDashboard.class));
                    }
                    else
                    {
                        // go to admin dashboard
                    }
                }
                else
                {
                    Toast.makeText(this,"Wrong login credentials",Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public void validate()
    {
        if(root.txtPhoneNumber.getText().toString().equals("")||root.txtPassword.getText().toString().equals(""))
        {

        }


    }
}