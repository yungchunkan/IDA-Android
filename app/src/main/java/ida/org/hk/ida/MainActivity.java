package ida.org.hk.ida;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;

import adapter.AdsAdapter;
import clas.Restaurant;
import clas.ServerHandler;

public class MainActivity extends NavigationActivity {
    private RecyclerView recyclerView;
    private SlidingUpPanelLayout slidingUpPanelLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_main);

        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        new RecommendRestDLTask().execute();
    }

    @Override
    public void onBackPressed() {
        switch (slidingUpPanelLayout.getPanelState()) {
            case EXPANDED:
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                break;
            case COLLAPSED:
                super.onBackPressed();
        }
    }

    public void onMenuClick(View v) {
        Intent i = null;
        switch (v.getId()) {
            case R.id.main_find_rest:
                i = new Intent(this, SearchActivity.class);
                break;
            case R.id.main_nearby:
                i = new Intent(this, MapActivity.class);
                break;
            case R.id.main_new_discount:
                i = new Intent(this, DiscountListActivity.class);
                break;
            case R.id.main_hot_discount:
                i = new Intent(this, HotDiscountActivity.class);
                break;
            case R.id.main_hot_rest:
                i = new Intent(this, HotRestActivity.class);
                //break;
            //case R.id.main_employment:
                //i = new Intent(this, EmploySearchActivity.class);
                //i = new Intent(this, EmploymentActivity.class);
        }
        if (i != null) startActivity(i);
    }

    private class RecommendRestDLTask extends AsyncTask<Void, Void, List<Restaurant>> {

        @Override
        protected List<Restaurant> doInBackground(Void... params) {
            ServerHandler serverHandler = new ServerHandler();
            return serverHandler.getRecommendRestaurant();
        }

        @Override
        protected void onPostExecute(List<Restaurant> list) {
            AdsAdapter adapter = new AdsAdapter(list);
            recyclerView.setAdapter(adapter);
        }
    }
}
