package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import clas.Discount;

/**
 * Created by ricky on 10/1/16.
 */
public class DiscountDetailsAdapter extends BaseAdapter {
    Context context;
    Discount discount;

    public DiscountDetailsAdapter(Context context, Discount discount) {
        this.context = context;
        this.discount = discount;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
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
                tv.setText(discount.getDesc());
                break;
            case 1:
                tv.setText(discount.getExpire());
                break;
            case 2:
            case 3:

        }
        return convertView;
    }
}
