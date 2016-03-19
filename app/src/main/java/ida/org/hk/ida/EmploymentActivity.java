package ida.org.hk.ida;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import adapter.EmployAdapter;
import clas.Employ;
import clas.ServerHandler;

public class EmploymentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EmployAdapter adapter;
    private List<Employ> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.employ_list);

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        data = new ArrayList<>();
        adapter = new EmployAdapter(data);
        recyclerView.setAdapter(adapter);

        new EmployDownloadTask().execute();
    }

    private class EmployDownloadTask extends AsyncTask<Void, Void, List<Employ>> {

        @Override
        protected List<Employ> doInBackground(Void... params) {
            ServerHandler handler = new ServerHandler();
            return handler.getAllEmploy();
        }

        @Override
        protected void onPostExecute(List<Employ> list) {
            adapter.setData(list);
        }
    }
}
