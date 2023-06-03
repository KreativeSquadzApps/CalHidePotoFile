package com.kreativesquadz.calculatorlock.audios.adapter;

import android.content.Context;
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

import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.app.MainApplication;
import com.kreativesquadz.calculatorlock.audios.AudiosActivity;
import com.kreativesquadz.calculatorlock.model.AllAudioModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class AudiosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private boolean isLongPressed = false;
    private ArrayList<AllAudioModel> bucketArraylist = new ArrayList<>();

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

    public AudiosAdapter(Context context) {
        this.context = context;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ImageViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_file_hide, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {
        final AllAudioModel allAudioModel = this.bucketArraylist.get(i);
        if (viewHolder instanceof ImageViewHolder) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) viewHolder;
            imageViewHolder.checkbox.setOnCheckedChangeListener(null);
            if (allAudioModel.isEditable()) {
                imageViewHolder.checkbox.setVisibility(View.VISIBLE);
            } else {
                imageViewHolder.checkbox.setVisibility(View.GONE);
            }
            if (allAudioModel.isSelected()) {
                imageViewHolder.checkbox.setChecked(true);
            } else {
                imageViewHolder.checkbox.setChecked(false);
            }
            if (allAudioModel.isSelected()) {
                imageViewHolder.checkbox.setChecked(true);
            } else {
                imageViewHolder.checkbox.setChecked(false);
            }
            if (!allAudioModel.getOldPath().isEmpty()) {
                File file = new File(allAudioModel.getOldPath());
                imageViewHolder.txtTitle.setText(file.getName());
                imageViewHolder.txtSize.setText(MainApplication.getInstance().getFileSize(file.length()));
            }
            imageViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (allAudioModel.isEditable()) {
                        AllAudioModel allAudioModel2 = allAudioModel;
                        allAudioModel2.setSelected(!allAudioModel2.isSelected());
                        if (allAudioModel.isSelected()) {
                            ((ImageViewHolder) viewHolder).checkbox.setChecked(true);
                        } else {
                            ((ImageViewHolder) viewHolder).checkbox.setChecked(false);
                        }
                        AudiosAdapter.this.checkIfAllFilesDeselected();
                        return;
                    }
                    ((AudiosActivity) AudiosAdapter.this.context).openAudio(allAudioModel.getOldPath());
                }
            });
            imageViewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    if (allAudioModel.isEditable()) {
                        if (z) {
                            allAudioModel.setSelected(true);
                        } else {
                            allAudioModel.setSelected(false);
                        }
                        AudiosAdapter.this.checkIfAllFilesDeselected();
                    }
                }
            });
        }
    }


    public void checkIfAllFilesDeselected() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllAudioModel> it = this.bucketArraylist.iterator();
        while (it.hasNext()) {
            AllAudioModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(next.getOldPath());
            }
        }
        if (arrayList.size() == 0) {
            ((AudiosActivity) this.context).showDeleteButton(false);
            ((AudiosActivity) this.context).showSelectAllButton(false);
        } else if (arrayList.size() == this.bucketArraylist.size()) {
            ((AudiosActivity) this.context).showDeleteButton(true);
            ((AudiosActivity) this.context).showSelectAllButton(true);
        } else {
            ((AudiosActivity) this.context).showDeleteButton(true);
            ((AudiosActivity) this.context).showSelectAllButton(false);
        }
    }

    public List<String> getSelectedImages() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllAudioModel> it = this.bucketArraylist.iterator();
        while (it.hasNext()) {
            AllAudioModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(next.getOldPath());
            }
        }
        return arrayList;
    }

    public List<String> getSelectedImagePaths() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllAudioModel> it = this.bucketArraylist.iterator();
        while (it.hasNext()) {
            AllAudioModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(next.getOldPath());
            }
        }
        return arrayList;
    }

    public void selectAllItem() {
        Iterator<AllAudioModel> it = this.bucketArraylist.iterator();
        while (it.hasNext()) {
            it.next().setSelected(true);
        }
        this.isLongPressed = true;
        notifyDataSetChanged();
    }

    public void deSelectAllItem() {
        Iterator<AllAudioModel> it = this.bucketArraylist.iterator();
        while (it.hasNext()) {
            it.next().setSelected(false);
        }
        this.isLongPressed = false;
        notifyDataSetChanged();
    }

    public void removeSelectedFiles() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllAudioModel> it = this.bucketArraylist.iterator();
        while (it.hasNext()) {
            AllAudioModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(new AllAudioModel(next.getOldPath(), next.getLastModified()));
            }
        }
        this.bucketArraylist.removeAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        ArrayList<AllAudioModel> arrayList = this.bucketArraylist;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public void addItems(ArrayList<AllAudioModel> arrayList) {
        this.bucketArraylist.addAll(arrayList);
        notifyDataSetChanged();
    }

    public void isItemEditable(boolean z) {
        Iterator<AllAudioModel> it = this.bucketArraylist.iterator();
        while (it.hasNext()) {
            it.next().setEditable(z);
        }
        notifyDataSetChanged();
    }
}
