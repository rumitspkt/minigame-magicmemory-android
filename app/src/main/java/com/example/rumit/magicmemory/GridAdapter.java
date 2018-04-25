package com.example.rumit.magicmemory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by RumIT on 4/24/2018.
 */

public class GridAdapter extends BaseAdapter {


    private List<Integer> itemStates;

    private Context context;

    private LayoutInflater layoutInflater;

    public GridAdapter(Context context, List<Integer> itemStates){
        this.context = context;
        this.itemStates = itemStates;
    }


    @Override
    public int getCount() {
        return itemStates.size();
    }

    @Override
    public Object getItem(int position) {
        return itemStates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;

        if(convertView == null){

            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = layoutInflater.inflate(R.layout.custom_layout, null);

        }
        ImageView imgItem = (ImageView) gridView.findViewById(R.id.img_item);

        if(itemStates.get(position) == 1){
            imgItem.setImageResource(R.drawable.check);
        }else{
            imgItem.setImageResource(R.drawable.uncheck);
        }



        return gridView;
    }
}
