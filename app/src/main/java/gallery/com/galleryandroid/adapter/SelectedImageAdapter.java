package gallery.com.galleryandroid.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import gallery.com.galleryandroid.R;
import gallery.com.galleryandroid.model.GalleryData;

public class SelectedImageAdapter extends RecyclerView.Adapter<SelectedImageAdapter.ViewHolder> {
    private Activity mActivity;
    private ArrayList<GalleryData> mList = new ArrayList<>();
    private int DEVICE_WIDTH = 0;
    private int selectCount = 0;
    private final int TOTAL_SELECTION = 10;

    public SelectedImageAdapter(Activity activity, ArrayList<GalleryData> list) {
        this.mActivity = activity;
        this.mList = list;
        this.DEVICE_WIDTH = getDeviceWidth();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.relativeLayoutSelection.getLayoutParams().height = DEVICE_WIDTH / 3;

        String media = mList.get(position).getMedia();
        File file = new File(media);
        Glide.with(mActivity)
                .load(file)
                .into(holder.imageView);

        String type = mList.get(position).getType();
        if (type.equals("3")) { // Video
            holder.imageViewPlay.setVisibility(View.VISIBLE);
        } else { // Image
            holder.imageViewPlay.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * Get device width
     */
    private int getDeviceWidth() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayoutSelection;
        ImageView imageView;
        ImageView imageViewPlay;
        ImageView imageViewSelect;

        ViewHolder(View view) {
            super(view);
            relativeLayoutSelection = view.findViewById(R.id.relativeLayoutSelection);
            imageView = view.findViewById(R.id.imageView);
            imageViewPlay = view.findViewById(R.id.imageViewPlay);
        }
    }
}