package ida.org.hk.ida;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import adapter.RestAdapter;
import clas.Restaurant;
import clas.ServerHandler;

public class RestListActivity extends ToolbarActivity {
    private RecyclerView recyclerView;
    private GridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.recycler_list);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        manager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(manager);

        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey("district") && bundle.containsKey("dish") && bundle.containsKey("query")) {
            int district = bundle.getInt("district");
            int dish = bundle.getInt("dish");
            new SearchRestTask().execute(String.valueOf(district), String.valueOf(dish), bundle.getString("query"));
        } else
            new RestDownloadTask().execute();
    }

    class RestDownloadTask extends AsyncTask<Void, Void, List<Restaurant>> {
        private RestAdapter adapter;

        @Override
        protected List<Restaurant> doInBackground(Void... params) {
            ServerHandler handler = new ServerHandler();
            return handler.getAllRestaurants();
        }

        @Override
        protected void onPostExecute(List<Restaurant> list) {
            adapter = new RestAdapter(list);
            recyclerView.setAdapter(adapter);

            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return adapter.isHeader(position) ? manager.getSpanCount() : 1;
                }
            });
        }
    }

    class SearchRestTask extends AsyncTask<String, Void, List<Restaurant>> {
        RestAdapter adapter;
        @Override
        protected List<Restaurant> doInBackground(String... params) {
            ServerHandler handler = new ServerHandler();
            return handler.getRestByDD(Integer.valueOf(params[0]), Integer.valueOf(params[1]), params[2]);
        }

        @Override
        protected void onPostExecute(List<Restaurant> list) {
            adapter = new RestAdapter(list);
            recyclerView.setAdapter(adapter);

            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return adapter.isHeader(position) ? manager.getSpanCount() : 1;
                }
            });
        }
    }

}
