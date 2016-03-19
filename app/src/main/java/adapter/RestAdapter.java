package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import clas.Restaurant;
import ida.org.hk.ida.R;
import ida.org.hk.ida.RestDetailsActivity;

/**
 * Created by ricky on 15/9/15.
 */
public class RestAdapter extends RecyclerView.Adapter<RestAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<Restaurant> data;
    private Context context;

    public RestAdapter(List<Restaurant> list) {
        data = list;
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public RestAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (data.get(i).getImageUrl() != null) {
            Picasso.with(context).load(data.get(i).getImageUrl())
                    .resize(500, 200)
                    .centerCrop()
                    .into(viewHolder.imageView);
        } else {
            viewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.default_img));
            viewHolder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        viewHolder.textView.setText(data.get(i).getRestName());
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 1) ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_iv);
            textView = (TextView) itemView.findViewById(R.id.item_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, RestDetailsActivity.class);
            i.putExtra(RestDetailsActivity.EXTRA_REST_ID, data.get(getAdapterPosition()).getRestID());
            context.startActivity(i);
        }
    }
}
