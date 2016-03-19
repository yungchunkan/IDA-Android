package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import clas.Restaurant;
import ida.org.hk.ida.R;

/**
 * Created by ricky on 2/10/15.
 */
public class RestDetailsAdapter extends BaseAdapter {
    Context context;
    Restaurant rest;

    public RestDetailsAdapter(Context context, Restaurant rest) {
        this.context = context;
        this.rest = rest;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Restaurant getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null);
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setTextColor(context.getResources().getColor(android.R.color.black));
        switch (position) {
            case 0:
                tv.setText("地址: " + rest.getAddress());
                break;
            case 1:
                tv.setText(context.getResources().getString(R.string.tel) + rest.getTel());
                break;
            case 2:
                tv.setText(rest.getRestInfo());
                break;
            case 3:

        }
        return convertView;
    }
}
