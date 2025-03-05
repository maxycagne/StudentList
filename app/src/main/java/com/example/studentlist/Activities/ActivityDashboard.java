package com.example.studentlist.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.studentlist.Database.DbHelper;
import com.example.studentlist.Fragments.AddFragment;
import com.example.studentlist.Fragments.FragmentHome;
import com.example.studentlist.Fragments.ProfileFragment;
import com.example.studentlist.R;
import com.example.studentlist.databinding.ActivityDashboardBinding;

public class ActivityDashboard extends AppCompatActivity {

    private ActivityDashboardBinding root;

    private Handler handler;

    private DbHelper dbHelper;

    private void setDb()
    {
        handler = new Handler(Looper.getMainLooper());
        dbHelper = new DbHelper(this);
    }


    public Handler getHandler() {
        return handler;
    }

    public DbHelper getDbHelper() {
        return dbHelper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        root = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });
        setDb();

        ReplaceFragement(new FragmentHome());
        root.bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.menuHome)
            {
                ReplaceFragement(new FragmentHome());

            } else if (item.getItemId() == R.id.menuAdd) {

                ReplaceFragement(new AddFragment());
            } else if (item.getItemId() == R.id.menuProfile) {

                ReplaceFragement(new ProfileFragment());
            }

            return true;
        });
    }

    public void ReplaceFragement(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}