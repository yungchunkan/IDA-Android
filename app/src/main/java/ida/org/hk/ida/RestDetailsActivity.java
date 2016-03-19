package ida.org.hk.ida;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import adapter.RestDetailsAdapter;
import clas.Restaurant;
import clas.ServerHandler;

public class RestDetailsActivity extends ToolbarActivity implements AdapterView.OnItemClickListener {
    public final static String EXTRA_REST_ID = "EXTRA_REST_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.details_page);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString(EXTRA_REST_ID);

        new RestDetailsDownloadTask().execute(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rest_details, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 1:
                String tel = ((TextView) (view.findViewById(android.R.id.text1)))
                        .getText().toString().replace(getResources().getString(R.string.tel), "");
                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
                startActivity(i);
        }
    }

    private class RestDetailsDownloadTask extends AsyncTask<String, Void, Restaurant> {

        @Override
        protected Restaurant doInBackground(String... params) {
            ServerHandler handler = new ServerHandler();
            return handler.getRestById(params[0]);
        }

        @Override
        protected void onPostExecute(Restaurant restaurant) {
            getSupportActionBar().setTitle(restaurant.getRestName());
            ImageView iv = (ImageView) findViewById(R.id.details_iv);
            Picasso.with(getApplicationContext()).load(restaurant.getImageUrl()).into(iv);

            ListView lv = (ListView) findViewById(R.id.details_lv);
            RestDetailsAdapter adapter = new RestDetailsAdapter(getApplicationContext(), restaurant);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(RestDetailsActivity.this);
        }
    }
}
