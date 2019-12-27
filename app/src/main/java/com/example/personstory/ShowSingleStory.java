package com.example.personstory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personstory.SliderPackage.SliderPagerAdapter;
import com.example.personstory.ThemeConfigration.ThemeConfigration;
import com.example.personstory.personpackage.PersonRecyclerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class ShowSingleStory extends AppCompatActivity {

    Toolbar toolbar;
    TextView personIdSingleStory, personNameSingleStory, personStateSingleStory;
    ImageView personImageSingleStory;
    String name, state;
    int id;
    byte[] image;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkTheme();
        setContentView(R.layout.activity_show_single_story);


        initialization();
        fillData();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
//do no thing
        }
    }

    private void fillData() {
        id = SliderPagerAdapter.personModelsSingle.get(0).getId();
        name = SliderPagerAdapter.personModelsSingle.get(0).getName();
        state = SliderPagerAdapter.personModelsSingle.get(0).getState();
        image = SliderPagerAdapter.personModelsSingle.get(0).getImage();
        personIdSingleStory.setText(String.valueOf(id));
        personNameSingleStory.setText(name);
        personStateSingleStory.setText(state);
        Bitmap bitmap = byteToImage(image);
        personImageSingleStory.setImageBitmap(bitmap);
    }

    private void initialization() {
        toolbar = findViewById(R.id.mainToolbsr);
        setSupportActionBar(toolbar);
        personIdSingleStory = findViewById(R.id.personIdSingleStory);
        personNameSingleStory = findViewById(R.id.personNameSingleStory);
        personStateSingleStory = findViewById(R.id.personStateSingleStory);
        personImageSingleStory = findViewById(R.id.personImageSingleStory);
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private Bitmap byteToImage(byte[] imageByte) {
        return BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_theme, menu);
        MenuItem menuItem = menu.findItem(R.id.iconTheme);///hideen item
        menuItem.setVisible(true);///hideen item
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(ShowSingleStory.this, ChangeTheme.class));
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
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


}
