package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import clas.Employ;

/**
 * Created by ricky on 10/1/16.
 */
public class EmployAdapter extends RecyclerView.Adapter<EmployAdapter.ViewHolder> {
    private List<Employ> data;
    private Context context;

    public EmployAdapter(List<Employ> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(data.get(position).getPosition());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Employ> data) {
        this.data.clear();
        this.data.addAll(data);
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
