package com.kreativesquadz.calculatorlock.image.adapter;

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
import com.kreativesquadz.calculatorlock.image.ImagesActivity;
import com.kreativesquadz.calculatorlock.model.AllImagesModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private boolean isLongPressed = false;
    private ArrayList<AllImagesModel> bucketsArraylist = new ArrayList<>();

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public int getItemViewType(int i) {
        return i;
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

    public ImagesAdapter(Context context) {
        this.context = context;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ImageViewHolder(LayoutInflater.from(this.context).inflate(R.layout.raw_all_images, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {
        final AllImagesModel allImagesModel = this.bucketsArraylist.get(i);
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
                    if (allImagesModel.isEditable()) {
                        AllImagesModel allImagesModel2 = allImagesModel;
                        allImagesModel2.setSelected(!allImagesModel2.isSelected());
                        if (allImagesModel.isSelected()) {
                            ((ImageViewHolder) viewHolder).imgSelection.setImageResource(R.drawable.ic_check_white_24dp);
                        } else {
                            ((ImageViewHolder) viewHolder).imgSelection.setImageResource(0);
                        }
                        ImagesAdapter.this.checkIfAllFilesDeselected();
                        return;
                    }
                    ((ImagesActivity) ImagesAdapter.this.context).startFullScreenImageActivity(ImagesAdapter.this.bucketsArraylist, viewHolder.getAdapterPosition());
                }
            });
        }
    }


    public void checkIfAllFilesDeselected() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllImagesModel> it = this.bucketsArraylist.iterator();
        while (it.hasNext()) {
            AllImagesModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(next.getImagePath());
            }
        }
        if (arrayList.size() == 0) {
            ((ImagesActivity) this.context).showDeleteButton(false);
            ((ImagesActivity) this.context).showSelectAllButton(false);
        } else if (arrayList.size() == this.bucketsArraylist.size()) {
            ((ImagesActivity) this.context).showDeleteButton(true);
            ((ImagesActivity) this.context).showSelectAllButton(true);
        } else {
            ((ImagesActivity) this.context).showDeleteButton(true);
            ((ImagesActivity) this.context).showSelectAllButton(false);
        }
    }

    public List<String> getSelectedImages() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllImagesModel> it = this.bucketsArraylist.iterator();
        while (it.hasNext()) {
            AllImagesModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(next.getImagePath());
            }
        }
        return arrayList;
    }

    public List<String> getSelectedImagePaths() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllImagesModel> it = this.bucketsArraylist.iterator();
        while (it.hasNext()) {
            AllImagesModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(next.getImagePath());
            }
        }
        return arrayList;
    }

    public void selectAllItem() {
        Iterator<AllImagesModel> it = this.bucketsArraylist.iterator();
        while (it.hasNext()) {
            it.next().setSelected(true);
        }
        this.isLongPressed = true;
        notifyDataSetChanged();
    }

    public void deSelectAllItem() {
        Iterator<AllImagesModel> it = this.bucketsArraylist.iterator();
        while (it.hasNext()) {
            it.next().setSelected(false);
        }
        this.isLongPressed = false;
        notifyDataSetChanged();
    }

    public void isItemEditable(boolean z) {
        Iterator<AllImagesModel> it = this.bucketsArraylist.iterator();
        while (it.hasNext()) {
            it.next().setEditable(z);
        }
        notifyDataSetChanged();
    }

    public void removeSelectedFiles() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllImagesModel> it = this.bucketsArraylist.iterator();
        while (it.hasNext()) {
            AllImagesModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(new AllImagesModel(next.getImagePath(), next.getImageLastModified()));
            }
        }
        this.bucketsArraylist.removeAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        ArrayList<AllImagesModel> arrayList = this.bucketsArraylist;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public void addItems(ArrayList<AllImagesModel> arrayList) {
        this.bucketsArraylist.addAll(arrayList);
        notifyDataSetChanged();
    }
}
