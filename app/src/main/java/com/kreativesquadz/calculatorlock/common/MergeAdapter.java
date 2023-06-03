package com.kreativesquadz.calculatorlock.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MergeAdapter extends BaseAdapter {
    private ArrayList<ListAdapter> pieces = new ArrayList<>();

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    public void addAdapter(ListAdapter listAdapter) {
        this.pieces.add(listAdapter);
    }

    public void addView(View view) {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(view);
        addViews(arrayList);
    }


    public void addViews(List<View> list) {
        this.pieces.add(new SackOfViewsAdapter(list));
    }

    @Override
    public Object getItem(int i) {
        Iterator<ListAdapter> it = this.pieces.iterator();
        while (it.hasNext()) {
            ListAdapter next = it.next();
            int count = next.getCount();
            if (i < count) {
                return next.getItem(i);
            }
            i -= count;
        }
        return null;
    }

    @Override
    public int getCount() {
        Iterator<ListAdapter> it = this.pieces.iterator();
        int i = 0;
        while (it.hasNext()) {
            i += it.next().getCount();
        }
        return i;
    }

    @Override
    public int getViewTypeCount() {
        Iterator<ListAdapter> it = this.pieces.iterator();
        int i = 0;
        while (it.hasNext()) {
            i += it.next().getViewTypeCount();
        }
        return Math.max(i, 1);
    }

    @Override
    public int getItemViewType(int i) {
        Iterator<ListAdapter> it = this.pieces.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            ListAdapter next = it.next();
            int count = next.getCount();
            if (i < count) {
                return i2 + next.getItemViewType(i);
            }
            i -= count;
            i2 += next.getViewTypeCount();
        }
        return -1;
    }

    @Override
    public boolean isEnabled(int i) {
        Iterator<ListAdapter> it = this.pieces.iterator();
        while (it.hasNext()) {
            ListAdapter next = it.next();
            int count = next.getCount();
            if (i < count) {
                return next.isEnabled(i);
            }
            i -= count;
        }
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Iterator<ListAdapter> it = this.pieces.iterator();
        while (it.hasNext()) {
            ListAdapter next = it.next();
            int count = next.getCount();
            if (i < count) {
                return next.getView(i, view, viewGroup);
            }
            i -= count;
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        Iterator<ListAdapter> it = this.pieces.iterator();
        while (it.hasNext()) {
            ListAdapter next = it.next();
            int count = next.getCount();
            if (i < count) {
                return next.getItemId(i);
            }
            i -= count;
        }
        return -1;
    }
}
