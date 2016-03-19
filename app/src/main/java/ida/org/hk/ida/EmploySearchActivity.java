package ida.org.hk.ida;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapter.SearchAdapter;
import clas.Dish;
import clas.District;
import clas.ServerHandler;

public class EmploySearchActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener,
        ExpandableListView.OnGroupClickListener, ExpandableListView.OnGroupExpandListener {
    private ExpandableListView elv;
    private SearchAdapter adapter;
    private TextView elvTv;
    private int[] choice = {-1, -1, -1, -1};
    private int lastExpandGroup = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employ_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        elv = (ExpandableListView) findViewById(R.id.search_employ_elv);
        elv.setOnGroupClickListener(this);
        elv.setOnChildClickListener(this);
        elv.setOnGroupExpandListener(this);

        new FilterDownload().execute();
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        View view = adapter.getGroupView(groupPosition, parent.isGroupExpanded(groupPosition), v, parent);
        elvTv = (TextView) view.findViewById(R.id.elv_group_item_tv);
        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        elvTv.setText(adapter.getChild(groupPosition, childPosition));
        choice[groupPosition] = childPosition;
        parent.collapseGroup(groupPosition);
        return false;
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        if (groupPosition != lastExpandGroup) elv.collapseGroup(lastExpandGroup);
        lastExpandGroup = groupPosition;
    }

    public void searchClick(View view) {

    }

    private class FilterDownload extends AsyncTask<Void, Void, List<List<String>>> {

        @Override
        protected List<List<String>> doInBackground(Void... params) {
            ServerHandler handler = new ServerHandler();
            List<District> districts = handler.getAllDistrict();
            List<Dish> dishes = handler.getAllDishes();
            List<String> position = Arrays.asList(getResources().getStringArray(R.array.position));
            List<String> cont = Arrays.asList(getResources().getStringArray(R.array.cont));
            List<List<String>> data = new ArrayList<>();
            data.add(position);
            data.add(cont);
            data.add(District.getAllName(districts));
            data.add(Dish.getAllName(dishes));
            return data;
        }

        @Override
        protected void onPostExecute(List<List<String>> lists) {
            List<String> titles = Arrays.asList(getResources().getStringArray(R.array.employ_titles));
            adapter = new SearchAdapter(getApplicationContext(), titles, lists);
            elv.setAdapter(adapter);
        }
    }
}
