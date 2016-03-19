package ida.org.hk.ida;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by ricky on 14/9/15.
 */
public class PhotoFragment extends Fragment {
    final static private String EXTRA_RESID = "EXTRA_RESID";
    private int imageId;

    public static final PhotoFragment newInstance(int i)
    {
        PhotoFragment f = new PhotoFragment();
        Bundle bdl = new Bundle(1);
        bdl.putInt(EXTRA_RESID, i);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageId = getArguments().getInt(EXTRA_RESID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView image = new ImageView(getActivity());
        image.setImageResource(imageId);

        LinearLayout layout = new LinearLayout(getActivity());
        //layout.setLayoutParams(new ViewGroup.LayoutParams());

        layout.setGravity(Gravity.CENTER);
        layout.addView(image);

        return layout;
    }
}
