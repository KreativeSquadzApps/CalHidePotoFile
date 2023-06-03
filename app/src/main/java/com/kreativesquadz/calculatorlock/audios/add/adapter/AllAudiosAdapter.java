package com.kreativesquadz.calculatorlock.audios.add.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.app.MainApplication;
import com.kreativesquadz.calculatorlock.audios.add.AddAudiosActivity;
import com.kreativesquadz.calculatorlock.model.AllAudioModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;



public class AllAudiosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<AllAudioModel> bucketsArraylist = new ArrayList<>();
    private Context context;

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }



    public class C05142 implements Comparator<AllAudioModel> {
        C05142() {
        }

        public int compare(AllAudioModel allAudioModel, AllAudioModel allAudioModel2) {
            return Long.valueOf(allAudioModel2.getLastModified()).compareTo(Long.valueOf(allAudioModel.getLastModified()));
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

    public AllAudiosAdapter(Context context) {
        this.context = context;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ImageViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_file_hide, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {
        final AllAudioModel allAudioModel = this.bucketsArraylist.get(i);
        if (viewHolder instanceof ImageViewHolder) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) viewHolder;
            imageViewHolder.checkbox.setOnCheckedChangeListener(null);
            if (!allAudioModel.getOldPath().isEmpty()) {
                File file = new File(allAudioModel.getOldPath());
                imageViewHolder.txtTitle.setText(file.getName());
                imageViewHolder.txtSize.setText(MainApplication.getInstance().getFileSize(file.length()));
            }
            if (allAudioModel.isSelected()) {
                imageViewHolder.checkbox.setChecked(true);
            } else {
                imageViewHolder.checkbox.setChecked(false);
            }
            imageViewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (((ImageViewHolder) viewHolder).checkbox.isChecked()) {
                        allAudioModel.setSelected(true);
                    } else {
                        allAudioModel.setSelected(false);
                    }
                    AllAudiosAdapter.this.checkIfAllFilesDeselected();
                }
            });
        }
    }


    public void checkIfAllFilesDeselected() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllAudioModel> it = this.bucketsArraylist.iterator();
        while (it.hasNext()) {
            AllAudioModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(next.getOldPath());
            }
        }
        if (arrayList.size() != 0) {
            ((AddAudiosActivity) this.context).showHideButton(true);
        } else {
            ((AddAudiosActivity) this.context).showHideButton(false);
            ((AddAudiosActivity) this.context).setSelectAll(false);
        }
        if (arrayList.size() == this.bucketsArraylist.size()) {
            ((AddAudiosActivity) this.context).setSelectAll(true);
        } else {
            ((AddAudiosActivity) this.context).setSelectAll(false);
        }
    }

    public List<String> getSelectedImages() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllAudioModel> it = this.bucketsArraylist.iterator();
        while (it.hasNext()) {
            AllAudioModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(next.getOldPath());
            }
        }
        return arrayList;
    }

    public void selectAllItem() {
        Iterator<AllAudioModel> it = this.bucketsArraylist.iterator();
        while (it.hasNext()) {
            it.next().setSelected(true);
        }
        notifyDataSetChanged();
    }

    public void deSelectAllItem() {
        Iterator<AllAudioModel> it = this.bucketsArraylist.iterator();
        while (it.hasNext()) {
            it.next().setSelected(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        ArrayList<AllAudioModel> arrayList = this.bucketsArraylist;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public void addItems(ArrayList<AllAudioModel> arrayList) {
        if (arrayList != null) {
            Collections.sort(arrayList, new C05142());
            this.bucketsArraylist.addAll(arrayList);
            notifyDataSetChanged();
        }
    }
}
