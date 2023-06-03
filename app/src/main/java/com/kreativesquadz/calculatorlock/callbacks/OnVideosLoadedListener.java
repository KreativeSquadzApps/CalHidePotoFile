package com.kreativesquadz.calculatorlock.callbacks;

import com.kreativesquadz.calculatorlock.model.AllVideosModel;
import java.util.ArrayList;


public interface OnVideosLoadedListener {
    void onVideosLoaded(ArrayList<AllVideosModel> arrayList);
}
