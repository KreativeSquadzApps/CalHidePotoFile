package com.kreativesquadz.calculatorlock.files.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.kreativesquadz.calculatorlock.R;
import com.kreativesquadz.calculatorlock.app.MainApplication;
import com.kreativesquadz.calculatorlock.files.FilesActivity;
import com.kreativesquadz.calculatorlock.model.AllFilesModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class FilesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private boolean isLongPressed = false;
    private ArrayList<AllFilesModel> buckets = new ArrayList<>();

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

    public FilesAdapter(Context context) {
        this.context = context;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ImageViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_file, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {
        final AllFilesModel allFilesModel = this.buckets.get(i);
        if (viewHolder instanceof ImageViewHolder) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) viewHolder;
            imageViewHolder.checkbox.setOnCheckedChangeListener(null);
            if (allFilesModel.isEditable()) {
                imageViewHolder.checkbox.setVisibility(View.VISIBLE);
            } else {
                imageViewHolder.checkbox.setVisibility(View.GONE);
            }
            if (allFilesModel.isSelected()) {
                imageViewHolder.checkbox.setChecked(true);
            } else {
                imageViewHolder.checkbox.setChecked(false);
            }
            if (allFilesModel.isSelected()) {
                imageViewHolder.checkbox.setChecked(true);
            } else {
                imageViewHolder.checkbox.setChecked(false);
            }
            if (!allFilesModel.getOldPath().isEmpty()) {
                File file = new File(allFilesModel.getOldPath());
                imageViewHolder.txtTitle.setText(file.getName());
                imageViewHolder.txtSize.setText(MainApplication.getInstance().getFileSize(file.length()));
            }
            imageViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (allFilesModel.isEditable()) {
                        AllFilesModel allFilesModel2 = allFilesModel;
                        allFilesModel2.setSelected(!allFilesModel2.isSelected());
                        if (allFilesModel.isSelected()) {
                            ((ImageViewHolder) viewHolder).checkbox.setChecked(true);
                        } else {
                            ((ImageViewHolder) viewHolder).checkbox.setChecked(false);
                        }
                        FilesAdapter.this.checkIfAllFilesDeselected();
                        return;
                    }
                    ((FilesActivity) FilesAdapter.this.context).openFile(allFilesModel.getOldPath());
                }
            });
            imageViewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    if (allFilesModel.isEditable()) {
                        if (z) {
                            allFilesModel.setSelected(true);
                        } else {
                            allFilesModel.setSelected(false);
                        }
                        FilesAdapter.this.checkIfAllFilesDeselected();
                    }
                }
            });
        }
    }


    public void checkIfAllFilesDeselected() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllFilesModel> it = this.buckets.iterator();
        while (it.hasNext()) {
            AllFilesModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(next.getOldPath());
            }
        }
        if (arrayList.size() == 0) {
            ((FilesActivity) this.context).showDeleteButton(false);
            ((FilesActivity) this.context).showSelectAllButton(false);
        } else if (arrayList.size() == this.buckets.size()) {
            ((FilesActivity) this.context).showDeleteButton(true);
            ((FilesActivity) this.context).showSelectAllButton(true);
        } else {
            ((FilesActivity) this.context).showDeleteButton(true);
            ((FilesActivity) this.context).showSelectAllButton(false);
        }
    }

    public List<String> getSelectedImages() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllFilesModel> it = this.buckets.iterator();
        while (it.hasNext()) {
            AllFilesModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(next.getOldPath());
            }
        }
        return arrayList;
    }

    public List<String> getSelectedImagePaths() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllFilesModel> it = this.buckets.iterator();
        while (it.hasNext()) {
            AllFilesModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(next.getOldPath());
            }
        }
        return arrayList;
    }

    public void selectAllItem() {
        Iterator<AllFilesModel> it = this.buckets.iterator();
        while (it.hasNext()) {
            it.next().setSelected(true);
        }
        this.isLongPressed = true;
        notifyDataSetChanged();
    }

    public void deSelectAllItem() {
        Iterator<AllFilesModel> it = this.buckets.iterator();
        while (it.hasNext()) {
            it.next().setSelected(false);
        }
        this.isLongPressed = false;
        notifyDataSetChanged();
    }

    public void removeSelectedFiles() {
        ArrayList arrayList = new ArrayList();
        Iterator<AllFilesModel> it = this.buckets.iterator();
        while (it.hasNext()) {
            AllFilesModel next = it.next();
            if (next.isSelected()) {
                arrayList.add(new AllFilesModel(next.getOldPath(), next.getLastModified()));
            }
        }
        this.buckets.removeAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        ArrayList<AllFilesModel> arrayList = this.buckets;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public void addItems(ArrayList<AllFilesModel> arrayList) {
        this.buckets.addAll(arrayList);
        notifyDataSetChanged();
    }

    public void isItemEditable(boolean z) {
        Iterator<AllFilesModel> it = this.buckets.iterator();
        while (it.hasNext()) {
            it.next().setEditable(z);
        }
        notifyDataSetChanged();
    }
}
