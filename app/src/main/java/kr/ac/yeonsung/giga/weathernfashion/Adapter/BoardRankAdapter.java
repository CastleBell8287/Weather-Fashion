package kr.ac.yeonsung.giga.weathernfashion.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.ac.yeonsung.giga.weathernfashion.R;
import kr.ac.yeonsung.giga.weathernfashion.VO.BoardRank;

public class BoardRankAdapter extends RecyclerView.Adapter<BoardRankAdapter.ViewHolder> {

    private ArrayList<BoardRank> mData = null ;
    private Context context;

    public BoardRankAdapter(Context context, ArrayList<BoardRank> mData) {
        this.mData = mData;
        this.context = context;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView rank;
        ImageView rank_icon;

        public ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            rank = itemView.findViewById(R.id.rank);
            imageView = itemView.findViewById(R.id.imageView);
            rank_icon = itemView.findViewById(R.id.rank_icon);

        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public BoardRankAdapter(ArrayList<BoardRank> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public BoardRankAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_rank_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(BoardRankAdapter.ViewHolder holder, int position) {
        int image_int = mData.get(position).getImage();
        String rank_int = mData.get(position).getRank();
        holder.rank.setText(rank_int);
        if(holder.rank.getText().equals("1")) {
            holder.imageView.setImageResource(image_int);
            holder.rank_icon.setImageResource(R.drawable.rank1);
            System.out.println(holder.rank.getText());
        }
        if(holder.rank.getText().equals("2")) {
            holder.imageView.setImageResource(image_int);
            holder.rank_icon.setImageResource(R.drawable.rank2);
            System.out.println(holder.rank.getText());
        }
        if(holder.rank.getText().equals("3")) {
            holder.imageView.setImageResource(image_int);
            holder.rank_icon.setImageResource(R.drawable.rank3);
            System.out.println(holder.rank.getText());
        } else{
            holder.imageView.setImageResource(image_int);
        }
    }
    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}