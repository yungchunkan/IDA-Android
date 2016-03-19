package ida.org.hk.ida;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.List;

import adapter.DiscountAdapter;
import clas.Discount;
import clas.ServerHandler;

public class HotDiscountActivity extends ToolbarActivity {
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.recycler_list);

        getSupportActionBar().setTitle(getString(R.string.title_hot_discount));

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        manager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(manager);

        new HotDiscountDownloadTask().execute();
    }

    private class HotDiscountDownloadTask extends AsyncTask<Void, Void, List<Discount>> {

        @Override
        protected List<Discount> doInBackground(Void... params) {
            ServerHandler handler = new ServerHandler();
            return handler.getAllDiscount();
        }

        @Override
        protected void onPostExecute(List<Discount> discounts) {
            DiscountAdapter adapter = new DiscountAdapter(discounts);
            recyclerView.setAdapter(adapter);
        }
    }
}
