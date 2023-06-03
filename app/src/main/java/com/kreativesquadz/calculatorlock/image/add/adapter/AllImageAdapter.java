package com.kreativesquadz.calculatorlock.image.add.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.bumptech.glide.Glide;
import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.image.add.AddImageActivity;
import com.kreativesquadz.calculatorlock.model.AllImagesModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


public class AllImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<AllImagesModel> buckets;
    private Context context;
    int int_position;
    private boolean isLongPressed = false;

    public void addItems(ArrayList<AllImagesModel> arrayList) {
    }

    public void deSelectAllItem() {
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }


    class C06142 implements Comparator<AllImagesModel> {
        public int compare(AllImagesModel allImagesModel, AllImagesModel allImagesModel2) {
            return 0;
        }

        C06142() {
        }
    }


    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageView imgSelection;
        View mView;

        public ImageViewHolder(View view) {
            super(view);
            this.img = (ImageView) view.findViewById(R.id.img);
            this.imgSelection = (ImageView) view.findViewById(R.id.img_selection);
            this.mView = view;
        }
    }


    public class ImageViewHolder_ViewBinding implements Unbinder {
        private ImageViewHolder target;

        @UiThread
        public ImageViewHolder_ViewBinding(ImageViewHolder imageViewHolder, View view) {
            this.target = imageViewHolder;
            imageViewHolder.img = (ImageView) Utils.findRequiredViewAsType(view, R.id.img, "field 'img'", ImageView.class);
            imageViewHolder.imgSelection = (ImageView) Utils.findRequiredViewAsType(view, R.id.img_selection, "field 'imgSelection'", ImageView.class);
        }

        @CallSuper
        public void unbind() {
            ImageViewHolder imageViewHolder = this.target;
            if (imageViewHolder != null) {
                this.target = null;
                imageViewHolder.img = null;
                imageViewHolder.imgSelection = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }

    public AllImageAdapter(Context context, ArrayList<AllImagesModel> arrayList, int i) {
        this.context = context;
        this.int_position = i;
        this.buckets = arrayList;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ImageViewHolder(LayoutInflater.from(this.context).inflate(R.layout.raw_all_images, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {
        final AllImagesModel allImagesModel = this.buckets.get(i);
        if (viewHolder instanceof ImageViewHolder) {
            if (!allImagesModel.getImagePath().isEmpty()) {
                Glide.with(this.context).load(Uri.fromFile(new File(allImagesModel.getImagePath()))).into(((ImageViewHolder) viewHolder).img);
            }
            if (allImagesModel.isSelected()) {
                ((ImageViewHolder) viewHolder).imgSelection.setImageResource(R.drawable.ic_check_white_24dp);
            } else {
                ((ImageViewHolder) viewHolder).imgSelection.setImageResource(0);
            }
            ((ImageViewHolder) viewHolder).mView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    AllImagesModel allImagesModel2 = allImagesModel;
                    allImagesModel2.setSelected(!allImagesModel2.isSelected());
                    if (allImagesModel.isSelected()) {
                        ((ImageViewHolder) viewHolder).imgSelection.setImageResource(R.drawable.ic_check_white_24dp);
                    } else {
                        ((ImageViewHolder) viewHolder).imgSelection.setImageResource(0);
                    }
                    AllImageAdapter.this.checkIfAllFilesDeselected();
                }
            });
        }
    }


    public void checkIfAllFilesDeselected() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllImagesModel> it = this.buckets.iterator();
        while (it.hasNext()) {
            AllImagesModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(next.getImagePath());
            }
        }
        if (arrayList.size() != 0) {
            ((AddImageActivity) this.context).showHideButton(true);
        } else {
            ((AddImageActivity) this.context).showHideButton(false);
        }
        if (arrayList.size() == this.buckets.size()) {
            ((AddImageActivity) this.context).setSelectAll(true);
        } else {
            ((AddImageActivity) this.context).setSelectAll(false);
        }
    }

    public List<String> getSelectedImages() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllImagesModel> it = this.buckets.iterator();
        while (it.hasNext()) {
            AllImagesModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(next.getImagePath());
            }
        }
        return arrayList;
    }

    public void selectAllItem() {
        Iterator<AllImagesModel> it = this.buckets.iterator();
        while (it.hasNext()) {
            it.next().setSelected(true);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        ArrayList<AllImagesModel> arrayList = this.buckets;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }
}
