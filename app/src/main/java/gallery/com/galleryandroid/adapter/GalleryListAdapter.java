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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import gallery.com.galleryandroid.R;
import gallery.com.galleryandroid.model.GalleryData;

public class GalleryListAdapter extends RecyclerView.Adapter<GalleryListAdapter.ViewHolder> {

    private Activity mActivity;
    private ArrayList<GalleryData> mList = new ArrayList<>();
    private int DEVICE_WIDTH = 0;
    private int selectCount = 0;
    private final int TOTAL_SELECTION = 10;

    public GalleryListAdapter(Activity activity, ArrayList<GalleryData> list) {
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

        if (mList.get(position).isSelect()) {
            holder.imageViewSelect.setVisibility(View.VISIBLE);
        } else {
            holder.imageViewSelect.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * Get selected image list
     */
    public ArrayList<GalleryData> getSelectedList() {
        ArrayList<GalleryData> list = new ArrayList<>();
        for (GalleryData galleryData : mList) {
            if (galleryData.isSelect()) {
                list.add(galleryData);
            }
        }
        return list;
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
            imageViewSelect = view.findViewById(R.id.imageViewSelect);

            relativeLayoutSelection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectCount < TOTAL_SELECTION) {
                        if (mList.get(getAdapterPosition()).isSelect()) {
                            selectCount--;
                            mList.get(getAdapterPosition()).setSelect(false);
                        } else {
                            selectCount++;
                            mList.get(getAdapterPosition()).setSelect(true);
                        }
                        notifyItemChanged(getAdapterPosition());
                    } else if (selectCount == TOTAL_SELECTION) {
                        if (mList.get(getAdapterPosition()).isSelect()) {
                            selectCount--;
                            mList.get(getAdapterPosition()).setSelect(false);
                            notifyItemChanged(getAdapterPosition());
                        }
                    } else {
                        Toast.makeText(mActivity, "You can select only 10 images",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
