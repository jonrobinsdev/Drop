package com.hackgt2015project.ui;

/**
 * Created by jonathanrobins on 9/26/15.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackgt2015project.R;

import java.util.List;

public class CustomListViewAdapter extends ArrayAdapter<RowItem> {

    Context context;
    int[] selectedRowsItems;

    public CustomListViewAdapter(Context context, int resourceId,
                                 List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtDesc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtDesc.setText(rowItem.getText());
        holder.imageView.setImageResource(rowItem.getImage());
        return convertView;
    }
}