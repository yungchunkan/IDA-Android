package ida.org.hk.ida;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import adapter.RestAdapter;
import clas.Restaurant;
import clas.ServerHandler;

public class FollowActivity extends NavigationActivity {
    private TabLayout tabLayout;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_follow);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.rest));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.discount));

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new FollowPagerAdapter());
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    class FollowPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Item " + (position + 1);
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.recycler_list, container, false);
            container.addView(view);

            LinearLayoutManager manager = new LinearLayoutManager(FollowActivity.this);
            manager.setOrientation(LinearLayoutManager.VERTICAL);

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
            recyclerView.setLayoutManager(manager);

            new HotRestDownloadTask(recyclerView).execute();

            return view;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    private class HotRestDownloadTask extends AsyncTask<Void, Void, List<Restaurant>> {
        private RecyclerView recyclerView;
        public HotRestDownloadTask(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

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
