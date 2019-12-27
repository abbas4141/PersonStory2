package com.example.personstory.personpackage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class LoadImageAsync extends AsyncTask<byte[], String, Bitmap> {

    private PersonViewHolder personViewHolder;

    public LoadImageAsync(PersonViewHolder personViewHolder) {
        this.personViewHolder = personViewHolder;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(byte[]... bytes) {
        return BitmapFactory.decodeByteArray(bytes[0], 0, bytes[0].length);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        personViewHolder.image.setImageBitmap(bitmap);
        super.onPostExecute(bitmap);
    }
}