package ida.org.hk.ida;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.List;

import adapter.RestAdapter;
import clas.Restaurant;
import clas.ServerHandler;

public class HotRestActivity extends ToolbarActivity {
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.recycler_list);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        manager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(manager);

        new HotRestDownloadTask().execute();
    }

    private class HotRestDownloadTask extends AsyncTask<Void, Void, List<Restaurant>> {

        @Override
        protected List<Restaurant> doInBackground(Void... params) {
            ServerHandler handler = new ServerHandler();
            return handler.getAllRestaurants();
        }

        @Override
        protected void onPostExecute(List<Restaurant> restaurants) {
            RestAdapter adapter = new RestAdapter(restaurants);
            recyclerView.setAdapter(adapter);
        }
    }
}
