package com.kreativesquadz.calculatorlock.video.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.Unbinder;
import butterknife.internal.Utils;

import com.bumptech.glide.Glide;
import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.app.MainApplication;
import com.kreativesquadz.calculatorlock.model.AllVideosModel;
import com.kreativesquadz.calculatorlock.video.VideoActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private boolean isLongPressed = false;
    private ArrayList<AllVideosModel> buckets = new ArrayList<>();

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }


    class ImageViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkbox;
        ImageView img;
        View mView;
        TextView txtSize;
        TextView txtTitle;

        public ImageViewHolder(View view) {
            super(view);
            this.checkbox = (CheckBox) view.findViewById(R.id.checkbox);
            this.img = (ImageView) view.findViewById(R.id.img);
            this.txtSize = (TextView) view.findViewById(R.id.txt_size);
            this.txtTitle = (TextView) view.findViewById(R.id.txt_title);
            this.mView = view;
        }
    }


    public class ImageViewHolder_ViewBinding implements Unbinder {
        private ImageViewHolder target;

        @UiThread
        public ImageViewHolder_ViewBinding(ImageViewHolder imageViewHolder, View view) {
            this.target = imageViewHolder;
            imageViewHolder.img = (ImageView) Utils.findRequiredViewAsType(view, R.id.img, "field 'img'", ImageView.class);
            imageViewHolder.txtTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.txt_title, "field 'txtTitle'", TextView.class);
            imageViewHolder.txtSize = (TextView) Utils.findRequiredViewAsType(view, R.id.txt_size, "field 'txtSize'", TextView.class);
            imageViewHolder.checkbox = (CheckBox) Utils.findRequiredViewAsType(view, R.id.checkbox, "field 'checkbox'", CheckBox.class);
        }

        @CallSuper
        public void unbind() {
            ImageViewHolder imageViewHolder = this.target;
            if (imageViewHolder != null) {
                this.target = null;
                imageViewHolder.img = null;
                imageViewHolder.txtTitle = null;
                imageViewHolder.txtSize = null;
                imageViewHolder.checkbox = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }

    public VideoAdapter(Context context) {
        this.context = context;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ImageViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_hidevideo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {
        final AllVideosModel allVideosModel = this.buckets.get(i);
        if (viewHolder instanceof ImageViewHolder) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) viewHolder;
            imageViewHolder.checkbox.setOnCheckedChangeListener(null);
            if (allVideosModel.isEditable()) {
                imageViewHolder.checkbox.setVisibility(View.VISIBLE);
            } else {
                imageViewHolder.checkbox.setVisibility(View.GONE);
            }
            if (allVideosModel.isSelected()) {
                imageViewHolder.checkbox.setChecked(true);
            } else {
                imageViewHolder.checkbox.setChecked(false);
            }
            if (allVideosModel.isSelected()) {
                imageViewHolder.checkbox.setChecked(true);
            } else {
                imageViewHolder.checkbox.setChecked(false);
            }
            if (!allVideosModel.getOldPath().isEmpty()) {
                File file = new File(allVideosModel.getOldPath());
                Glide.with(this.context).load(Uri.fromFile(file)).into(imageViewHolder.img);
                imageViewHolder.txtTitle.setText(file.getName());
                imageViewHolder.txtSize.setText(MainApplication.getInstance().getFileSize(file.length()));
            }
            imageViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (allVideosModel.isEditable()) {
                        AllVideosModel allVideosModel2 = allVideosModel;
                        allVideosModel2.setSelected(!allVideosModel2.isSelected());
                        if (allVideosModel.isSelected()) {
                            ((ImageViewHolder) viewHolder).checkbox.setChecked(true);
                        } else {
                            ((ImageViewHolder) viewHolder).checkbox.setChecked(false);
                        }
                        VideoAdapter.this.checkIfAllFilesDeselected();
                        return;
                    }
                    ((VideoActivity) VideoAdapter.this.context).openVideo(allVideosModel.getOldPath());
                }
            });
            imageViewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    if (allVideosModel.isEditable()) {
                        if (z) {
                            allVideosModel.setSelected(true);
                        } else {
                            allVideosModel.setSelected(false);
                        }
                        VideoAdapter.this.checkIfAllFilesDeselected();
                    }
                }
            });
        }
    }


    public void checkIfAllFilesDeselected() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllVideosModel> it = this.buckets.iterator();
        while (it.hasNext()) {
            AllVideosModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(next.getOldPath());
            }
        }
        if (arrayList.size() == 0) {
            ((VideoActivity) this.context).showDeleteButton(false);
            ((VideoActivity) this.context).showSelectAllButton(false);
        } else if (arrayList.size() == this.buckets.size()) {
            ((VideoActivity) this.context).showDeleteButton(true);
            ((VideoActivity) this.context).showSelectAllButton(true);
        } else {
            ((VideoActivity) this.context).showDeleteButton(true);
            ((VideoActivity) this.context).showSelectAllButton(false);
        }
    }

    public List<String> getSelectedImages() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllVideosModel> it = this.buckets.iterator();
        while (it.hasNext()) {
            AllVideosModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(next.getOldPath());
            }
        }
        return arrayList;
    }

    public List<String> getSelectedImagePaths() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllVideosModel> it = this.buckets.iterator();
        while (it.hasNext()) {
            AllVideosModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(next.getOldPath());
            }
        }
        return arrayList;
    }

    public void selectAllItem() {
        Iterator<AllVideosModel> it = this.buckets.iterator();
        while (it.hasNext()) {
            it.next().setSelected(true);
        }
        this.isLongPressed = true;
        notifyDataSetChanged();
    }

    public void deSelectAllItem() {
        Iterator<AllVideosModel> it = this.buckets.iterator();
        while (it.hasNext()) {
            it.next().setSelected(false);
        }
        this.isLongPressed = false;
        notifyDataSetChanged();
    }

    public void removeSelectedFiles() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllVideosModel> it = this.buckets.iterator();
        while (it.hasNext()) {
            AllVideosModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(new AllVideosModel(next.getOldPath(), next.getLastModified()));
            }
        }
        this.buckets.removeAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        ArrayList<AllVideosModel> arrayList = this.buckets;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public void addItems(ArrayList<AllVideosModel> arrayList) {
        this.buckets.addAll(arrayList);
        notifyDataSetChanged();
    }

    public void isItemEditable(boolean z) {
        Iterator<AllVideosModel> it = this.buckets.iterator();
        while (it.hasNext()) {
            it.next().setEditable(z);
        }
        notifyDataSetChanged();
    }
}
