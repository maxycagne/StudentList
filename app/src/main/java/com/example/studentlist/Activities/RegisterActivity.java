package com.example.studentlist.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentlist.Database.DbHelper;
import com.example.studentlist.Database.Entity.User;
import com.example.studentlist.R;
import com.example.studentlist.databinding.ActivityRegisterBinding;

import java.util.List;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding root;
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
        root = ActivityRegisterBinding.inflate(getLayoutInflater());

        setContentView(root.getRoot());

        setDb();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        root.buttonRegister.setOnClickListener(v->{

            validation();
        });

        root.btnBack.setOnClickListener(v->{
            startActivity(new Intent(this,ActivityLogin.class));
        });

    }

    public void addUser() {
        String firstName = root.txtFirstName.getText().toString();
        String lastName = root.txtLastName.getText().toString();
        String phoneNum = "+639" + root.txtPhoneNumber.getText().toString();
        String password = root.txtPassword.getText().toString();
        String priv = "user";

        Executors.newSingleThreadExecutor().execute(() -> {
            User existingUser = dbHelper.getUserDao().findUserByNumber(phoneNum);

            handler.post(() -> {
                if (existingUser != null) {
                    handler.post(() -> Toast.makeText(this, "Date not available, please choose another date", Toast.LENGTH_SHORT).show());
                }
                else {
                    User user = new User(firstName, lastName, phoneNum, password, priv);
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Are you sure you want to create this account?")
                            .setTitle("Sign Up")
                            .setPositiveButton(R.string.ok, (dialog, id) -> {
                                Executors.newSingleThreadExecutor().execute(() -> {
                                    try {
                                        dbHelper.getUserDao().insertAll(user);
                                        handler.post(() -> {
                                            Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegisterActivity.this, ActivityLogin.class));
                                            finish();
                                        });
                                    } catch (Exception exception) {
                                        handler.post(() -> Toast.makeText(RegisterActivity.this, "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show());
                                    }
                                });
                            })
                            .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.dismiss());

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }


            });
        });
    }


    public void validation()
    {
        String firstName = root.txtFirstName.getText().toString();
        String lastName = root.txtLastName.getText().toString();
        String phoneNum = root.txtPhoneNumber.getText().toString();
        String password = root.txtPassword.getText().toString();
        String confirmPass = root.txtConfirmPassword.getText().toString();



        if(firstName.equals(""))
        {
            root.txtFirstNameLayout.setError("Fill up the required details");
            root.txtFirstNameLayout.setErrorEnabled(true);

            return;
        }
        else
        {
            root.txtFirstNameLayout.setError("");
            root.txtFirstNameLayout.setErrorEnabled(false);
        }

        if(lastName.equals("")){
            root.txtLastNameLayout.setError("Fill up the required details");
            root.txtLastNameLayout.setErrorEnabled(true);
            return;
        }
        else {
            root.txtLastNameLayout.setError("");
            root.txtLastNameLayout.setErrorEnabled(false);
        }

        if (phoneNum.equals(""))
        {
            root.txtPhoneNumberLayout.setError("Fill up the required details");
            root.txtPhoneNumberLayout.setErrorEnabled(true);
            return;
        }
        else {
            root.txtPhoneNumberLayout.setError("");
            root.txtPhoneNumberLayout.setErrorEnabled(false);
        }

        if(password.equals(""))
        {

            root.txtPasswordLayout.setError("Fill up the required details");
            root.txtPasswordLayout.setErrorEnabled(true);
            return;
        }
        else
        {
            root.txtPasswordLayout.setError("");
            root.txtPasswordLayout.setErrorEnabled(false);
        }

        if(confirmPass.equals(""))
        {

            root.txtConfirmPasswordLayout.setError("Fill up the required details");
            root.txtConfirmPasswordLayout.setErrorEnabled(true);
            return;
        }
        else {
            root.txtConfirmPasswordLayout.setError("");
            root.txtConfirmPasswordLayout.setErrorEnabled(false);
        }

        if (root.txtPhoneNumber.getText().toString().length() != 9)
        {
            root.txtPhoneNumberLayout.setError("Invalid Phone Number");
            root.txtPhoneNumberLayout.setErrorEnabled(true);
            return;
        }
        else
        {
            root.txtPhoneNumberLayout.setError("");
            root.txtPhoneNumberLayout.setErrorEnabled(false);
        }

        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$"))
        {
            root.txtPasswordLayout.setError("Invalid password does not meet the criteria");
            root.txtPasswordLayout.setErrorEnabled(true);
            return;
        }
        else
        {
            root.txtPasswordLayout.setError("");
            root.txtPasswordLayout.setErrorEnabled(false);
        }


        if(!password.equals(confirmPass))
        {
            root.txtConfirmPasswordLayout.setError("Password does not match");
            root.txtConfirmPasswordLayout.setErrorEnabled(true);
            return;
        }
        else
        {
            root.txtConfirmPasswordLayout.setError("");
            root.txtConfirmPasswordLayout.setErrorEnabled(false);
        }


        addUser();


    }
}