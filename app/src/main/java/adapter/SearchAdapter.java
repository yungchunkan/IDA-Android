package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ida.org.hk.ida.R;

/**
 * Created by ricky on 5/10/15.
 */
public class SearchAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> titles;
    private List<List<String>> data;

    public SearchAdapter(Context context, List<String> titles, List<List<String>> data) {
        this.context = context;
        this.titles = titles;
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).size();
    }

    @Override
    public List<String> getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.elv_group_item, null);
        }

        TextView title = (TextView) convertView.findViewById(R.id.elv_group_item_title);
        title.setText(titles.get(groupPosition));
        ImageView iv = (ImageView) convertView.findViewById(R.id.elv_group_item_iv);
        iv.setImageResource(R.drawable.ida_logo);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
        }

        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setTextColor(context.getResources().getColor(android.R.color.black));
        tv.setText(getChild(groupPosition, childPosition));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
