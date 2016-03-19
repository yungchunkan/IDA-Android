package ida.org.hk.ida;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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

public class SearchActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener,
        ExpandableListView.OnGroupClickListener, ExpandableListView.OnGroupExpandListener, SearchView.OnQueryTextListener {
    private ExpandableListView elv;
    private SearchAdapter adapter;
    private TextView elvTv;
    private SearchView searchView;

    private String query = "";
    //private int lastExpandGroup = -1;
    private int[] choice = {-1, -1, -1};  //for record user choice

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        elv = (ExpandableListView) findViewById(R.id.search_elv);
        elv.setOnGroupClickListener(this);
        elv.setOnChildClickListener(this);
        elv.setOnGroupExpandListener(this);

        new FilterDownload().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);
        
        return true;
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
        /*
        if (groupPosition != lastExpandGroup) elv.collapseGroup(lastExpandGroup);
        lastExpandGroup = groupPosition;
        */
        for (int i = 0; i < 3; i++) if (i != groupPosition) elv.collapseGroup(i);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        this.query = query;
        search();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public void searchClick(View view) {
        search();
    }

    private void search() {
        //TODO: send text and filter to Rest List or Discount List
        Intent i = null;
        switch (choice[0]) {
            case 0: //rest
                i = new Intent(this, RestListActivity.class);
                i.putExtra("district", choice[1]+1);
                i.putExtra("dish", choice[2]+1);
                i.putExtra("query", query);
        }
        if (i != null) startActivity(i);
    }

    private class FilterDownload extends AsyncTask<Void, Void, List<List<String>>> {

        @Override
        protected List<List<String>> doInBackground(Void... params) {
            ServerHandler handler = new ServerHandler();
            List<District> districts = handler.getAllDistrict();
            List<Dish> dishes = handler.getAllDishes();
            List<List<String>> data = new ArrayList<>();
            List<String> category = Arrays.asList(getResources().getStringArray(R.array.category));
            data.add(category);
            data.add(District.getAllName(districts));
            data.add(Dish.getAllName(dishes));
            return data;
        }

        @Override
        protected void onPostExecute(List<List<String>> lists) {
            List<String> titles = Arrays.asList(getResources().getStringArray(R.array.titles));
            adapter = new SearchAdapter(getApplicationContext(), titles, lists);
            elv.setAdapter(adapter);
        }
    }

    /*
    private class SearchTask extends AsyncTask<String, Void, List<Restaurant>> {

        @Override
        protected List<Restaurant> doInBackground(String... params) {
            ServerHandler handler = new ServerHandler();
            //return handler.getRestByDD(districtIndex, typeIndex, params[0]);
        }

        @Override
        protected void onPostExecute(List<Restaurant> restaurants) {
            //adapter = new RestAdapter(restaurants);
            //lv.setAdapter(adapter);
        }
    }
    */
}
