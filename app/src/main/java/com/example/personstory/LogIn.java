package com.example.personstory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personstory.ThemeConfigration.ThemeConfigration;

import java.util.Objects;


public class LogIn extends AppCompatActivity {
    Toolbar toolbar;
    EditText editTextUserName, editTextPassword;
    CheckBox checkboxStay;
    TextView titlePage;
    Button btnSignIn;
    public static SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String name,password;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkTheme();
        initialization();
        changeTextInPage();
        doTransition();

        // checkboxStay = findViewById(R.id.checkboxStay);
//        if (sharedPreferences.getBoolean("state_user", false)) {
//            checkboxStay.setVisibility(View.INVISIBLE);
//        }



    }

    private void changeTextInPage() {
        if (!sharedPreferences.getBoolean("state_user", false)) {
            titlePage.setText("New Account");
            btnSignIn.setText("Add Account");
            //add checkbox visableity
        } else if (sharedPreferences.getBoolean("state_user", true)) {
            titlePage.setText("Login Account");
            btnSignIn.setText("Login");
        }
    }

    private void initialization() {
        setContentView(R.layout.activity_log_in);
        toolbar = findViewById(R.id.mainToolbsr);
        editTextUserName = findViewById(R.id.editTextUserName);
        titlePage = findViewById(R.id.titlePage);
        btnSignIn = findViewById(R.id.btnSignIn);
        editTextPassword = findViewById(R.id.editTextUserPassword);
        setSupportActionBar(toolbar);
    }

    public void checkTheme() {
        ThemeConfigration themeConfigration = new ThemeConfigration(this);
       int resId = themeConfigration.getThemeId();
        if (resId == 0) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(resId);
        }
    }

    void doTransition() {
        Transition transition = null;
        String transitionName = getIntent().getStringExtra("transitionName");

        switch (transitionName) {
            case "explode":
                transition = TransitionInflater.from(this).inflateTransition(R.transition.explode);
                break;
            case "slide":
                transition = TransitionInflater.from(this).inflateTransition(R.transition.slide);
                break;
            case "fade":
                transition = TransitionInflater.from(this).inflateTransition(R.transition.fade);
                break;
            default:
                break;
        }

        getWindow().setEnterTransition(transition);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_theme, menu);
        MenuItem menuItem = menu.findItem(R.id.iconTheme);///hideen item
        menuItem.setVisible(true);///hideen item
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(LogIn.this, ChangeTheme.class));
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void startTransition(String name, Class aClass) {
        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(this, aClass);
        intent.putExtra("transitionName", name);
        startActivity(intent, options.toBundle());
    }

    public void Login(View view) {
        name=editTextUserName.getText().toString();
        password=editTextPassword.getText().toString();
        editor = sharedPreferences.edit();
        ConvertBtnToLoginOrCreateAccount();
    }

    private void ConvertBtnToLoginOrCreateAccount() {
        if (!sharedPreferences.getBoolean("state_user", false)) {
            editor.putString("user_name", name);
            editor.putString("password", password);
            editor.putBoolean("state_user", true);
            editor.apply();
            startTransition("fade", ShowAllStorys.class);
           // startActivity(new Intent(LogIn.this, ShowAllStorys.class));
            finish();
        } else if (sharedPreferences.getBoolean("state_user", true)) {
            if (Objects.equals(sharedPreferences.getString("user_name", null), name)
                    && Objects.equals(sharedPreferences.getString("password", null), password)) {
                startTransition("fade",ShowAllStorys.class);
              //  startActivity(new Intent(LogIn.this, ShowAllStorys.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid password or username!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}