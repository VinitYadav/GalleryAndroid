package gallery.com.galleryandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import gallery.com.galleryandroid.R;
import gallery.com.galleryandroid.adapter.SelectedImageAdapter;
import gallery.com.galleryandroid.model.GalleryData;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private RecyclerView recyclerView;
    private final int GALLERY = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY && resultCode == RESULT_OK && data != null) {
            ArrayList<GalleryData> list = data.getParcelableArrayListExtra("list");
            setAdapter(list);
        }
    }

    private void init() {
        button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        onClickOPenGallery();
    }

    /**
     * Click on open gallery button
     */
    private void onClickOPenGallery() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
                startActivityForResult(intent, GALLERY);
            }
        });
    }

    /**
     * Set adapter
     */
    private void setAdapter(ArrayList<GalleryData> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        recyclerView.setAdapter(new SelectedImageAdapter(MainActivity.this, list));
    }
}
