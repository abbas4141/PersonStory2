package com.example.personstory;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.personstory.Dialog.PersonDialogFragment;
import com.example.personstory.SQLitePackage.SQLiteHelper;
import com.example.personstory.SliderPackage.SliderPagerAdapter;
import com.example.personstory.ThemeConfigration.ThemeConfigration;
import com.example.personstory.personpackage.PersonModel;
import com.example.personstory.personpackage.PersonRecyclerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ShowAllStorys extends AppCompatActivity {
    TabLayout sliderTabLayout;
    public ViewPager sliderPager;
    public SliderPagerAdapter sliderPagerAdapter;
    Toolbar toolbar;
    public PersonRecyclerAdapter personRecyclerAdapter;
    public List<PersonModel> personModels;
    public RecyclerView personRecyclerView;
  private   RecyclerView.LayoutManager layoutManager;
    public SQLiteHelper sqLiteHelper;
    private  LinearLayout footerLayout;
    public boolean shouldLoadMore = true;
    Uri uriImage;
    String name, state;
    byte[] imageByte;
    public List<PersonModel> personModelsSlider;
    public ActivityOptions options;
    ThemeConfigration themeConfigration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkTheme();
        setContentView(R.layout.activity_show_all_storys);
        options = ActivityOptions.makeSceneTransitionAnimation(this);

        doTransition();
        initViews();
        loadFirstPage();
        timerOfSilder();
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


    public void startTransition(String name, Class aClass) {
        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(this, aClass);
        intent.putExtra("transitionName", name);
        startActivity(intent, options.toBundle());
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

    private void initViews() {
        sliderTabLayout = findViewById(R.id.sliderTabLayout);
        sliderPager = findViewById(R.id.sliderPager);
        sliderTabLayout.setupWithViewPager(sliderPager);
        toolbar = findViewById(R.id.mainToolbsr);
        setSupportActionBar(toolbar);
        footerLayout = findViewById(R.id.footerLayout);
        layoutManager = new LinearLayoutManager(this);
        personRecyclerView = findViewById(R.id.PersonRecyclerView);
        personRecyclerView.setLayoutManager(layoutManager);
        sqLiteHelper = new SQLiteHelper(this);

        personRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (dy > 0) {
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == personModels.size() - 1) {
                        if (shouldLoadMore) {
                            loadMore();
                        }
                    }
                }

            }
        });
    }

    private void loadMore() {
        footerLayout.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                personModels.addAll(sqLiteHelper.getAll().subList(10, sqLiteHelper.getAll().size()));
                personRecyclerAdapter.notifyDataSetChanged();
                if (personModels.size() == sqLiteHelper.getAll().size()) {
                    shouldLoadMore = false;
                }
                footerLayout.setVisibility(View.GONE);
            }
        }, 2000);
    }

    public void loadFirstPage() {
        shouldLoadMore = true;
        personModels = sqLiteHelper.getAll().subList(0, 10);
//        personModels = sqLiteHelper.getAll();
        personModelsSlider = sqLiteHelper.getAll().subList(0, 10);
        personRecyclerAdapter = new PersonRecyclerAdapter(this, personModels);
        personRecyclerView.setAdapter(personRecyclerAdapter);
        sliderPagerAdapter = new SliderPagerAdapter(this, personModelsSlider);
        sliderPager.setAdapter(sliderPagerAdapter);

    }
    @Override
    public void onBackPressed() {
//        System.exit(0);
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        getMenuInflater().inflate(R.menu.change_theme, menu);
        MenuItem changeThemeItem = menu.findItem(R.id.iconTheme);///hideen item
        changeThemeItem.setVisible(true);///hideen item
        MenuItem searchItem = menu.findItem(R.id.searchItem);
        MenuItem addPerson = menu.findItem(R.id.addPerson);
        addPerson.setVisible(true);///hideen item
        SearchView searchView = (SearchView) searchItem.getActionView();


        changeThemeItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(ShowAllStorys.this, ChangeTheme.class));
                return true;
            }
        });

        addPerson.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                Toast.makeText(ShowAllStorys.this, "hi iam menu", Toast.LENGTH_SHORT).show();
                PersonDialogFragment loginDialogFragment = new PersonDialogFragment("Add",
                        "Add Person", true);
                loginDialogFragment.show(getSupportFragmentManager(), null);
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                personRecyclerAdapter.searchPerson(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void timerOfSilder() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ShowAllStorys.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //10-1
                        if (sliderPager.getCurrentItem() < 10 - 1) {
                            sliderPager.setCurrentItem(sliderPager.getCurrentItem() + 1);
                        } else {
                            sliderPager.setCurrentItem(0);
                        }
                        refreshPage();
                    }
                });
            }
        }, 3000, 3000);
    }

    void refreshPage() {
        if (PersonDialogFragment.isRefresh||PersonRecyclerAdapter.isRefresh) {
            loadFirstPage();
            PersonDialogFragment.isRefresh = false;
            PersonRecyclerAdapter.isRefresh=false;
        }
    }

    public void showPersonDialogEdit(View view) {
      //  ThemeConfigration themeConfigration = new ThemeConfigration(this);
       // themeConfigration.DbIsExisting(true);
        PersonDialogFragment loginDialogFragment = new PersonDialogFragment("Save",
                "Edit Person", false);
        loginDialogFragment.show(getSupportFragmentManager(), null);
         Toast.makeText(this, "" + personRecyclerAdapter.index, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            uriImage = data.getData();
//            Image1.setImageURI(uriImage);
            PersonDialogFragment.personImageView.setImageURI(uriImage);
        }
    }

    @SuppressLint("IntentReset")
    public void getImageFromGallary(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    private byte[] imageToByte(ImageView imageView) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }

}
