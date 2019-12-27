package com.example.personstory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;

import com.example.personstory.ThemeConfigration.ThemeConfigration;

public class ChangeTheme extends AppCompatActivity {
    ThemeConfigration themeConfigration;
    Switch changeSwitch;
    int resId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkTheme();
        setContentView(R.layout.activity_change_theme);
        changeSwitch = findViewById(R.id.changeSwitch);

        if (resId==0){
            changeSwitch.setChecked(false);
        }else {
            changeSwitch.setChecked(true);
        }


    }

    public void checkTheme() {
        themeConfigration = new ThemeConfigration(this);
        resId = themeConfigration.getThemeId();

        if (resId == 0) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(resId);
        }
    }
    public void changeThemeClick(View view) {
        if (changeSwitch.isChecked()){
            themeConfigration.saveTheme(R.style.DarkTheme);
        }else {
            themeConfigration.saveTheme(R.style.AppTheme);
        }
        Toast.makeText(this, "يجب اعادة تشغيل البرنامج لتفعيل الثيم", Toast.LENGTH_SHORT).show();
        recreate();
    }
}
