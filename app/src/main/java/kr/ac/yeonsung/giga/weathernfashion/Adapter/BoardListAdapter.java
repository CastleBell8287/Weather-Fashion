package kr.ac.yeonsung.giga.weathernfashion.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import kr.ac.yeonsung.giga.weathernfashion.R;
import kr.ac.yeonsung.giga.weathernfashion.VO.BoardList;
import kr.ac.yeonsung.giga.weathernfashion.VO.Weather;

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.ViewHolder> {

    private ArrayList<BoardList> mData = null ;
    private Context context;

    public BoardListAdapter(Context context, ArrayList<BoardList> mData) {
        this.mData = mData;
        this.context = context;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView image_name ;
        ImageView image;


        public ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            image_name = itemView.findViewById(R.id.image_name);
            image = itemView.findViewById(R.id.image);

        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public BoardListAdapter(ArrayList<BoardList> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public BoardListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(BoardListAdapter.ViewHolder holder, int position) {
        int image_int = mData.get(position).getImage();
        String image_str = mData.get(position).getImage_name();
        holder.image.setImageResource(image_int);
        holder.image_name.setText(image_str);

    }
    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}