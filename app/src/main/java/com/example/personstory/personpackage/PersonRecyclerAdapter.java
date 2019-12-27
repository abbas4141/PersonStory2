package com.example.personstory.personpackage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personstory.R;
import com.example.personstory.SQLitePackage.SQLiteHelper;
import com.example.personstory.ShowSingleStory;
import com.example.personstory.SliderPackage.SliderPagerAdapter;
import com.example.personstory.ThemeConfigration.ThemeConfigration;

import java.util.ArrayList;
import java.util.List;

public class PersonRecyclerAdapter extends RecyclerView.Adapter<PersonViewHolder> {
    private Context context;
    private List<PersonModel> personModels,baseResource;
    public static List<PersonModel> personModelsSingle;
    private SQLiteHelper sqLiteHelper;
    public static boolean isRefresh = false;
    public static int index;
    ThemeConfigration themeConfigration;

    public PersonRecyclerAdapter(Context context, List<PersonModel> personModels) {
        this.context = context;
        this.personModels = personModels;
        baseResource = personModels;
        sqLiteHelper = new SQLiteHelper(context);
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View personView = LayoutInflater.from(context).inflate(R.layout.person_item, parent, false);
        return new PersonViewHolder(personView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, final int position) {
        final PersonModel personModel = personModels.get(position);

        index = personModel.getId();
        holder.id.setText(String.valueOf(personModel.getId()));
        holder.name.setText(personModel.getName());
        holder.state.setText(personModel.getState());
        new LoadImageAsync(holder).execute(personModel.getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareModelToSendToShowSingle(v, position);
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(v.getContext(), personModels.get(position).getName() + "remove", Toast.LENGTH_SHORT).show();

                ShowAlertDialog(v, position, personModel);
            }
        });
//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                index=position;
//            }
//        });
    }

    private void ShowAlertDialog(View v, final int position, final PersonModel personModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setMessage("Are you went delete this item")
                .setTitle("Delete Item")
                .setIcon(R.drawable.error_icon)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isRefresh=true;
                        removePerson(position);
                        sqLiteHelper.removePerson(personModel.getId());
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do no thing
                    }
                }).show();
    }

    private void prepareModelToSendToShowSingle(View v, int position) {
        SliderPagerAdapter.personModelsSingle = new ArrayList<>();
        SliderPagerAdapter.personModelsSingle.add(new PersonModel(
                personModels.get(position).getId(),
                personModels.get(position).getName(),
                personModels.get(position).getState(),
                personModels.get(position).getImage()));
        // currentIndex = position;
        Intent intent = new Intent(v.getContext(), ShowSingleStory.class);
        context.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return personModels.size();
    }

    public void searchPerson(String newText) {
        personModels = new ArrayList<>();
        if (newText.isEmpty()) {
            personModels = baseResource;
        } else {
            for (PersonModel person : baseResource) {
                if (person.getName().toLowerCase().trim().contains(newText.toLowerCase().trim())) {
                    personModels.add(person);
                }
            }
        }

        notifyDataSetChanged();
    }

    private void removePerson(int position) {
        personModels.remove(position);

        notifyDataSetChanged();
    }

    public ItemTouchHelper.SimpleCallback swipeToDelete = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            removePerson(viewHolder.getAdapterPosition());
            // sqLiteHelper.removePerson(personModel.getId());
        }
    };
}
