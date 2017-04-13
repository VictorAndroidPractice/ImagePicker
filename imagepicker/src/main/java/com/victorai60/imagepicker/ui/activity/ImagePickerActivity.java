package com.victorai60.imagepicker.ui.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.victorai60.imagepicker.R;
import com.victorai60.imagepicker.entity.Image;
import com.victorai60.imagepicker.ui.adapter.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImagePickerActivity extends AppCompatActivity {
    private GridView gvImages;
    private ImageAdapter mImageAdapter;

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks = new LoaderManager
            .LoaderCallbacks<Cursor>() {
        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media._ID};

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(ImagePickerActivity.this, MediaStore.Images
                    .Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, null, null, IMAGE_PROJECTION[2]
                    + " DESC");
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            List<Image> images = new ArrayList<Image>();
            while (data.moveToNext()) {
                String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                long date = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                images.add(new Image(path, name, date));
            }
            mImageAdapter = new ImageAdapter(ImagePickerActivity.this, images);
            gvImages.setAdapter(mImageAdapter);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagepicker_activity);
        initView();
        loadData();
    }

    private void initView() {
        gvImages = (GridView) findViewById(R.id.gv_images);
        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Image image = (Image) parent.getAdapter().getItem(position);
                if (mImageAdapter.getSelectedImages().contains(image)) {
                    mImageAdapter.getSelectedImages().remove(image);
                } else {
                    mImageAdapter.getSelectedImages().add(image);
                }
                mImageAdapter.notifyDataSetChanged();
            }
        });
    }

    private void loadData() {
        getSupportLoaderManager().initLoader(0, null, mLoaderCallbacks);
    }
}
