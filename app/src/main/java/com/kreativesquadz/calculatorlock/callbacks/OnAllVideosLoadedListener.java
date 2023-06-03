package com.kreativesquadz.calculatorlock.callbacks;

import com.kreativesquadz.calculatorlock.model.AllVideosModel;
import java.util.ArrayList;


public interface OnAllVideosLoadedListener {
    void onAllVideosLoaded(ArrayList<AllVideosModel> arrayList);
}
