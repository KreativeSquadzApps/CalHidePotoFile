package com.kreativesquadz.calculatorlock.callbacks;

import com.kreativesquadz.calculatorlock.model.AllFilesModel;
import java.util.ArrayList;


public interface OnFilesLoadedListener {
    void onFilesLoaded(ArrayList<AllFilesModel> arrayList);
}
