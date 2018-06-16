package gallery.com.galleryandroid.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import gallery.com.galleryandroid.R;
import gallery.com.galleryandroid.adapter.GalleryListAdapter;
import gallery.com.galleryandroid.model.GalleryData;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textViewDone;
    private GalleryListAdapter galleryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        init();
    }

    private void init() {
        textViewDone = findViewById(R.id.textViewDone);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        getAllMedia();

        onClickDone();
    }

    /**
     * Click on done button
     */
    private void onClickDone() {

        textViewDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (galleryListAdapter != null) {
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra("list"
                            , galleryListAdapter.getSelectedList());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    /**
     * Get all media from gallery
     */
    private void getAllMedia() {
        ArrayList<GalleryData> galleryList = new ArrayList<>();
        String[] columns = new String[]{MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE,
        };

        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

        final String orderBy = MediaStore.Files.FileColumns.DATE_ADDED;
        Uri queryUri = MediaStore.Files.getContentUri("external");

        try {
            Cursor imageCursor = getContentResolver()
                    .query(queryUri,
                            columns,
                            selection,
                            null, // Selection args (none).
                            orderBy + " DESC" // Sort order.
                    );

            if (imageCursor != null) {
                int imageColumnIndex = imageCursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
                int count = imageCursor.getCount();
                Bitmap[] thumbnails = new Bitmap[count];

                for (int i = 0; i < count; i++) {
                    imageCursor.moveToPosition(i);
                    int id = imageCursor.getInt(imageColumnIndex);
                    int dataColumnIndex = imageCursor.getColumnIndex(
                            MediaStore.Files.FileColumns.DATA);
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    bmOptions.inSampleSize = 4;
                    bmOptions.inPurgeable = true;
                    int type = imageCursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE);
                    String file = imageCursor.getString(dataColumnIndex);
                    int tempType = imageCursor.getInt(type);

                    galleryList.add(new GalleryData(file, tempType + "", false));
                }
                imageCursor.close();
                setAdapter(galleryList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set adapter
     */
    private void setAdapter(ArrayList<GalleryData> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        recyclerView.setAdapter(galleryListAdapter = new GalleryListAdapter(
                GalleryActivity.this, list));
    }
}
