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

import clas.Discount;
import ida.org.hk.ida.DiscountDetailsActivity;
import ida.org.hk.ida.R;

/**
 * Created by ricky on 18/9/15.
 */
public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.ViewHolder> {
    private List<Discount> data;
    private Context context;

    public DiscountAdapter(List<Discount> list) {
        data = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
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
        viewHolder.textView.setText(data.get(i).getTitle());
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
            imageView.setImageDrawable(context.getDrawable(R.drawable.default_img));
            textView = (TextView) itemView.findViewById(R.id.item_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, DiscountDetailsActivity.class);
            i.putExtra(DiscountDetailsActivity.EXTRA_DISCOUNT_ID, data.get(getAdapterPosition()).getId());
            context.startActivity(i);
        }
    }
}
