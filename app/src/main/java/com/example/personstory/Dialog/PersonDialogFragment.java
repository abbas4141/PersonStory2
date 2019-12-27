package com.example.personstory.Dialog;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.personstory.R;
import com.example.personstory.SQLitePackage.SQLiteHelper;
import com.example.personstory.ShowAllStorys;
import com.example.personstory.personpackage.PersonRecyclerAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class PersonDialogFragment extends DialogFragment {
    public static TextInputEditText editTextPersonState, editTextPersonName;
    public static ImageView personImageView;
    public static boolean isRefresh = false;
    private Button btnDialogPerson,getImageFromGallary;
    private String textOfButton,textOfTitle,name, state;
    private  TextView title;
    private boolean isAdd;
    private SQLiteHelper sqLiteHelper;
    private byte[] imageByte;
    private int id;
    private  PersonRecyclerAdapter personRecyclerAdapter;
    private  ShowAllStorys showAllStorys;


    public PersonDialogFragment(String textOfButton, String textOfTitle, boolean isAdd) {
        this.textOfButton = textOfButton;
        this.textOfTitle = textOfTitle;
        this.isAdd = isAdd;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_person, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(getDialog()
                .getWindow())
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editTextPersonName = view.findViewById(R.id.editTextPersonName);
        editTextPersonState = view.findViewById(R.id.editTextPersonState);
        personImageView = view.findViewById(R.id.personImageView);
        title = view.findViewById(R.id.title);
        btnDialogPerson = view.findViewById(R.id.btnDialogPerson);
        getImageFromGallary = view.findViewById(R.id.getImageFromGallary);


        btnDialogPerson.setText(textOfButton);
        title.setText(textOfTitle);

        btnDialogPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteHelper = new SQLiteHelper(v.getContext());
                fillData();
                if (isAdd) {
                    //add person
                    sqLiteHelper.insertPerson(name, state, imageByte);
                } else if (!isAdd) {
                    //edit person
                    id = PersonRecyclerAdapter.index;
                  //  title.setText(String.valueOf(id));
                   // Toast.makeText(showAllStorys, "sdfjfrnen"+id, Toast.LENGTH_SHORT).show();
                    sqLiteHelper.updatePerson(id, name, state, imageByte);
                }
                isRefresh = true;
                dismiss();
            }
        });
    }

    private byte[] imageToByte(ImageView imageView) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }

    void fillData() {
        name = editTextPersonName.getText().toString();
        state = editTextPersonState.getText().toString();
        imageByte = imageToByte(personImageView);
    }

}
