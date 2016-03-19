package ida.org.hk.ida;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import adapter.DiscountDetailsAdapter;
import clas.Discount;
import clas.ServerHandler;

public class DiscountDetailsActivity extends ToolbarActivity implements AdapterView.OnItemClickListener {
    public final static String EXTRA_DISCOUNT_ID = "EXTRA_DISCOUNT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.details_page);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString(EXTRA_DISCOUNT_ID);

        new DiscountDetailsDownloadTask().execute(id);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private class DiscountDetailsDownloadTask extends AsyncTask<String, Void, Discount> {

        @Override
        protected Discount doInBackground(String... params) {
            ServerHandler handler = new ServerHandler();
            return handler.getDiscountInfoById(params[0]);
        }

        @Override
        protected void onPostExecute(Discount discount) {
            getSupportActionBar().setTitle(discount.getTitle());
            ImageView iv = (ImageView) findViewById(R.id.details_iv);
            if (discount.getImageUrl() != null) Picasso.with(getApplicationContext()).load(discount.getImageUrl()).into(iv);

            ListView lv = (ListView) findViewById(R.id.details_lv);
            DiscountDetailsAdapter adapter = new DiscountDetailsAdapter(getApplicationContext(), discount);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(DiscountDetailsActivity.this);
        }
    }
}
