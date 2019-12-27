package com.example.personstory.personpackage;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personstory.R;

public class PersonViewHolder extends RecyclerView.ViewHolder {

    public TextView id, state, name;
    public Button remove,edit;
    public ImageView image;


    public PersonViewHolder(@NonNull View itemView) {
        super(itemView);
        id = itemView.findViewById(R.id.personId);
        name = itemView.findViewById(R.id.personName);
        state = itemView.findViewById(R.id.personState);
        image= itemView.findViewById(R.id.personImage);
        remove = itemView.findViewById(R.id.remove);
        edit = itemView.findViewById(R.id.edit);


        /////////////we can do this/////////////


//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //startActivity(new Intent(ShowAllStorys.this, ShowSingleStory.class));
//
//
//               // Toast.makeText(v.getContext(), name.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });


    }




}
