package com.kreativesquadz.calculatorlock.video.add.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import com.kreativesquadz.calculatorlock.video.add.AddVideoActivity;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;



public class AllVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<AllVideosModel> bucketsArraylist;
    private Context context;
    int int_position;

    public void addItems(ArrayList<AllVideosModel> arrayList) {
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }


    class C06562 implements Comparator<AllVideosModel> {
        C06562() {
        }

        public int compare(AllVideosModel allVideosModel, AllVideosModel allVideosModel2) {
            return Long.valueOf(allVideosModel2.getLastModified()).compareTo(Long.valueOf(allVideosModel.getLastModified()));
        }
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

    public AllVideoAdapter(Context context, ArrayList<AllVideosModel> arrayList, int i) {
        this.bucketsArraylist = new ArrayList<>();
        this.int_position = i;
        this.context = context;
        this.bucketsArraylist = arrayList;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ImageViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_hidevideo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {
        final AllVideosModel allVideosModel = this.bucketsArraylist.get(i);
        if (viewHolder instanceof ImageViewHolder) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) viewHolder;
            imageViewHolder.checkbox.setOnCheckedChangeListener(null);
            if (!allVideosModel.getOldPath().isEmpty()) {
                File file = new File(allVideosModel.getOldPath());
                Glide.with(this.context).load(Uri.fromFile(file)).into(imageViewHolder.img);
                imageViewHolder.txtTitle.setText(file.getName());
                imageViewHolder.txtSize.setText(MainApplication.getInstance().getFileSize(file.length()));
            }
            if (allVideosModel.isSelected()) {
                imageViewHolder.checkbox.setChecked(true);
            } else {
                imageViewHolder.checkbox.setChecked(false);
            }
            imageViewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (((ImageViewHolder) viewHolder).checkbox.isChecked()) {
                        allVideosModel.setSelected(true);
                    } else {
                        allVideosModel.setSelected(false);
                    }
                    AllVideoAdapter.this.checkIfAllFilesDeselected();
                }
            });
        }
    }


    public void checkIfAllFilesDeselected() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllVideosModel> it = this.bucketsArraylist.iterator();
        while (it.hasNext()) {
            AllVideosModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(next.getOldPath());
            }
        }
        if (arrayList.size() != 0) {
            ((AddVideoActivity) this.context).showHideButton(true);
        } else {
            ((AddVideoActivity) this.context).showHideButton(false);
            ((AddVideoActivity) this.context).setSelectAll(false);
        }
        if (arrayList.size() == this.bucketsArraylist.size()) {
            ((AddVideoActivity) this.context).setSelectAll(true);
        } else {
            ((AddVideoActivity) this.context).setSelectAll(false);
        }
    }

    public List<String> getSelectedImages() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllVideosModel> it = this.bucketsArraylist.iterator();
        while (it.hasNext()) {
            AllVideosModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(next.getOldPath());
            }
        }
        return arrayList;
    }

    public void selectAllItem() {
        Iterator<AllVideosModel> it = this.bucketsArraylist.iterator();
        while (it.hasNext()) {
            it.next().setSelected(true);
        }
        notifyDataSetChanged();
    }

    public void deSelectAllItem() {
        Iterator<AllVideosModel> it = this.bucketsArraylist.iterator();
        while (it.hasNext()) {
            it.next().setSelected(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        ArrayList<AllVideosModel> arrayList = this.bucketsArraylist;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }
}
