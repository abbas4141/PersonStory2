package com.example.personstory.SliderPackage;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.personstory.LogIn;
import com.example.personstory.R;
import com.example.personstory.ShowAllStorys;
import com.example.personstory.ShowSingleStory;
import com.example.personstory.personpackage.PersonModel;
import com.example.personstory.personpackage.PersonViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {

    private Context context;
    private List<PersonModel> personModels;
    public static List<PersonModel> personModelsSingle;


    public SliderPagerAdapter(Context context, List<PersonModel> personModels) {
        this.context = context;
        this.personModels = personModels;
    }

    @Override
    public int getCount() {
        return personModels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slider_item, container, false);
        Animation animation = AnimationUtils.loadAnimation(container.getContext(), android.R.anim.fade_in);
        animation.setDuration(2000);
        animation.start();
        ImageView sliderImage = view.findViewById(R.id.sliderImage);
        TextView sliderName = view.findViewById(R.id.personNameSlider);
        TextView sliderState = view.findViewById(R.id.personStateSlider);
        TextView sliderId = view.findViewById(R.id.personIdSlider);
        sliderName.setText(personModels.get(position).getName());
        sliderState.setText(personModels.get(position).getState());
        sliderId.setText(String.valueOf(personModels.get(position).getId()));

        sliderImage.setImageBitmap(doInBackground(personModels.get(position).getImage()));

        //    new LoadImageAsync(new PersonViewHolder(container)).execute(personModels.get(position).getImage());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareModelToSendToShowSingle(v, position);

//                ShowAllStorys showAllStorys = new ShowAllStorys();
//                showAllStorys.startTransition("explode", ShowSingleStory.class);
            }
        });
        container.addView(view);
        return view;
    }

    private void prepareModelToSendToShowSingle(View v, int position) {
        SliderPagerAdapter.personModelsSingle = new ArrayList<>();
        SliderPagerAdapter.personModelsSingle.add(new PersonModel(
                personModels.get(position).getId(),
                personModels.get(position).getName(),
                personModels.get(position).getState(),
                personModels.get(position).getImage()));

        Intent intent = new Intent(v.getContext(), ShowSingleStory.class);
        context.startActivity(intent);
    }

    protected Bitmap doInBackground(byte[]... bytes) {
        return BitmapFactory.decodeByteArray(bytes[0], 0, bytes[0].length);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
