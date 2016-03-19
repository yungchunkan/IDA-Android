package ida.org.hk.ida;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ViewSwitcher;

import com.squareup.picasso.Picasso;

import java.util.List;

import clas.Restaurant;
import clas.ServerHandler;
import clas.SwipeGestureDetector;

public class SliderFragment extends Fragment implements View.OnTouchListener, Runnable {
    private List<Restaurant> restaurants;
    private ImageSwitcher switcher;
    private ImageView imageView;
    private GestureDetector gestureDetector;
    private RadioButton[] radioButtons = new RadioButton[15];
    private int cur_index = 0;
    private Handler handler;

    private static final int HANDLER_DELAY = 2500;

    public SliderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new RecommendRestDLTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slider, container, false);

        gestureDetector = new GestureDetector(new SwipeGestureDetector(new SwipeGestureDetector.OnSwipeListener() {
            @Override
            public void onLeftSwipe() {
                leftSwipe();
            }

            @Override
            public void onRightSwipe() {
                rightSwipe();
            }
        }));

        switcher = (ImageSwitcher) view.findViewById(R.id.switcher);
        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView view = new ImageView(getActivity().getApplicationContext());
                view.setAdjustViewBounds(true);
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                return view;
            }
        });
        Animation in = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), android.R.anim.slide_out_right);
        switcher.setInAnimation(in);
        switcher.setOutAnimation(out);

        switcher.setOnTouchListener(this);

        int index = switcher.getDisplayedChild();
        imageView = (ImageView) switcher.getChildAt(index);

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rg);
        for (int i = 0; i < radioButtons.length; i++) {
            radioButtons[i] = new RadioButton(getActivity().getApplicationContext());
            radioButtons[i].setTag(i);
            radioButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cur_index = (int) v.getTag();
                    setImage();
                }
            });
            radioGroup.addView(radioButtons[i]);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //handler = new Handler();
        //handler.postDelayed(this, HANDLER_DELAY);
    }

    @Override
    public void onPause() {
        super.onPause();
        //handler.removeCallbacks(this);
    }

    public void leftSwipe() {
        cur_index = (cur_index+1) % restaurants.size();
        setImage();
    }

    public void rightSwipe() {
        cur_index = (cur_index != 0) ? cur_index-1 : restaurants.size()-1;
        setImage();
    }

    private void setImage() {
        Log.d("Slider Fragment", "Loading " + restaurants.get(cur_index).getRestName() + " Image...");
        Picasso.with(getActivity().getApplicationContext()).load(restaurants.get(cur_index).getImageUrl()).into(imageView);
        for (RadioButton btn : radioButtons) {
            btn.setChecked(false);
        }
        radioButtons[cur_index].setChecked(true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //Log.d("Slider Fragment", "onTouch Event: " + event.toString());
        boolean bool = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //handler.removeCallbacks(this);
                bool = true;
                break;
            case MotionEvent.ACTION_UP:
                //handler = new Handler();
                //handler.postDelayed(this, HANDLER_DELAY);
                bool = true;
        }
        if (gestureDetector.onTouchEvent(event)) bool = true;
        return bool;
    }

    @Override
    public void run() {
        leftSwipe();
        handler = new Handler();
        handler.postDelayed(this, HANDLER_DELAY);
    }

    private class RecommendRestDLTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            ServerHandler serverHandler = new ServerHandler();
            restaurants = serverHandler.getRecommendRestaurant();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setImage();
        }
    }
}
