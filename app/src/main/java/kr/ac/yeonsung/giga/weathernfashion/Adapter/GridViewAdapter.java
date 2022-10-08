package kr.ac.yeonsung.giga.weathernfashion.Adapter;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import kr.ac.yeonsung.giga.weathernfashion.R;

public class GridViewAdapter extends BaseAdapter {
    private String[]  imageNames;
    private int[] imagesPhoto;
    private Context context;
    private LayoutInflater layoutInflater;

    public GridViewAdapter(String[] imageNames, int[] imagesPhoto, Context context) {
        this.imageNames = imageNames;
        this.imagesPhoto = imagesPhoto;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imagesPhoto.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = layoutInflater.inflate(R.layout.row_items, viewGroup, false);

        }
        //TextView tvName = view.findViewById(R.id.tvName);
        ImageView imageView = view.findViewById(R.id.imageView);

        //tvName.setText(imageNames[i);
        imageView.setImageResource(imagesPhoto[i]);

        return view;
    }
}