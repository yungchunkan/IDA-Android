package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;

import java.util.List;

import clas.Restaurant;
import ida.org.hk.ida.R;
import ida.org.hk.ida.RestDetailsActivity;

/**
 * Created by ricky on 1/2/16.
 */
public class AdsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_SLIDER = 0;
    private static final int TYPE_ITEM = 1;

    private Context context;
    private List<Restaurant> sliderDate;
    private List<Restaurant> listData;

    public AdsAdapter(List<Restaurant> data) {
        sliderDate = data.subList(0, 5);
        listData = data.subList(5, data.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if (viewType == TYPE_SLIDER) {
            view = inflater.inflate(R.layout.ads_slider, parent, false);
            return new SliderViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.ads_item, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SliderViewHolder) {
            //((SliderViewHolder) holder).sliderLayout.removeAllSliders();
            ((SliderViewHolder) holder).sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            ((SliderViewHolder) holder).sliderLayout.setPresetTransformer(SliderLayout.Transformer.Fade);

            for (Restaurant rest : sliderDate) {
                TextSliderView textSliderView = new TextSliderView(context);
                textSliderView.description(rest.getRestName()).image(rest.getImageUrl());
                ((SliderViewHolder) holder).sliderLayout.addSlider(textSliderView);
            }
            ((SliderViewHolder) holder).sliderLayout.startAutoCycle();
        } else if (holder instanceof ItemViewHolder) {
            if (getItemRest(position).getImageUrl() != null) {
                Picasso.with(context).load(getItemRest(position).getImageUrl())
                        .resize(500, 200)
                        .centerCrop()
                        .into(((ItemViewHolder) holder).imageView);
            } else {
                ((ItemViewHolder) holder).imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.default_img));
                ((ItemViewHolder) holder).imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            ((ItemViewHolder) holder).child.setTag(getItemRest(position).getRestID());
            ((ItemViewHolder) holder).title.setText(getItemRest(position).getRestName());
            ((ItemViewHolder) holder).name.setText(getItemRest(position).getRestName());
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder instanceof SliderViewHolder) {
            ((SliderViewHolder) holder).sliderLayout.removeAllSliders();
            ((SliderViewHolder) holder).sliderLayout.stopAutoCycle();
        }
    }

    @Override
    public int getItemCount() {
        return listData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? TYPE_SLIDER : TYPE_ITEM;
    }

    public Restaurant getItemRest(int position) {
        return listData.get(position - 1);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, name;
        private ImageView imageView;
        private View child;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(android.R.id.text1);
            child = itemView.findViewById(R.id.ads_child);
            name = (TextView) itemView.findViewById(R.id.item_tv);
            imageView = (ImageView) itemView.findViewById(R.id.item_iv);

            title.setOnClickListener(this);
            child.setOnClickListener(this);

            child.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case android.R.id.text1:
                    child.setVisibility((child.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
                    break;
                case R.id.ads_child:
                    Intent i = new Intent(context, RestDetailsActivity.class);
                    i.putExtra(RestDetailsActivity.EXTRA_REST_ID, child.getTag().toString());
                    context.startActivity(i);
            }
        }
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder {
        private SliderLayout sliderLayout;

        public SliderViewHolder(View itemView) {
            super(itemView);
            sliderLayout = (SliderLayout) itemView.findViewById(R.id.ads_slider);
        }
    }
}
