package com.example.personstory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


import com.example.personstory.Dialog.PersonDialogFragment;
import com.example.personstory.ThemeConfigration.ThemeConfigration;
import com.example.personstory.checkDbIsExisting.CheckDbIsExistingSP;
import com.jgabrielfreitas.core.BlurImageView;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button btnSignUp, btnSignIn;
   public  static CheckDbIsExistingSP checkDbIsExistingSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         checkTheme();
        setContentView(R.layout.activity_main);
        initialisation();
        btnSignUp.setOnClickListener(onClickListenerSUP);
        btnSignIn.setOnClickListener(onClickListenerSIN);
        chooseTheBtnToShow();
    }

    private void chooseTheBtnToShow() {
        if (LogIn.sharedPreferences.getBoolean("state_user", false)) {
            btnSignUp.setVisibility(View.INVISIBLE);
        } else {
            btnSignIn.setVisibility(View.INVISIBLE);
        }
    }

    View.OnClickListener onClickListenerSUP=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startTransition("fade", LogIn.class);
//                startActivity(new Intent(v.getContext(), LogIn.class));
            finish();
        }
    };


    View.OnClickListener onClickListenerSIN=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startTransition("explode", LogIn.class);
            // startActivity(new Intent(v.getContext(), ShowAllStorys.class));
            finish();
        }
    };

    private void initialisation() {
        checkDbIsExistingSP=new CheckDbIsExistingSP(this);
        // Toast.makeText(this, ""+themeConfigration.getDBresult(), Toast.LENGTH_SHORT).show();
        toolbar = findViewById(R.id.mainToolbsr);
        LogIn.sharedPreferences = getSharedPreferences("filename", Context.MODE_PRIVATE);
        setSupportActionBar(toolbar);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);
    }

    private void startTransition(String name, Class aClass) {
        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(this, aClass);
        intent.putExtra("transitionName", name);
        startActivity(intent, options.toBundle());
    }

    @Override
    protected void onResume() {
        checkTheme();
        super.onResume();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_theme, menu);
        MenuItem menuItem = menu.findItem(R.id.iconTheme);///hideen item
        menuItem.setVisible(true);///hideen item
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, ChangeTheme.class));
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}