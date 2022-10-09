package kr.ac.yeonsung.giga.weathernfashion.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;

import kr.ac.yeonsung.giga.weathernfashion.R;
import kr.ac.yeonsung.giga.weathernfashion.VO.PostRank;

public class PostRankAdapter extends RecyclerView.Adapter<PostRankAdapter.ViewHolder> {

    private ArrayList<PostRank> mData = null ;


    private Context context;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference riversRef = storageRef.child("post");

    public PostRankAdapter(Context context, ArrayList<PostRank> mData) {
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
    public PostRankAdapter(ArrayList<PostRank> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public PostRankAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_rank_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(PostRankAdapter.ViewHolder holder, int position) {

        String image_str = mData.get(position).getImage();

        holder.rank.setText(String.valueOf(position+1));

        riversRef.child(image_str).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri)
                        .into(holder.imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });

        if(holder.rank.getText().equals("1")) {
            holder.rank_icon.setImageResource(R.drawable.rank1);
            System.out.println(holder.rank.getText());
        }
        if(holder.rank.getText().equals("2")) {
            holder.rank_icon.setImageResource(R.drawable.rank2);
            System.out.println(holder.rank.getText());
        }
        if(holder.rank.getText().equals("3")) {
            holder.rank_icon.setImageResource(R.drawable.rank3);
            System.out.println(holder.rank.getText());
        } else{
        }
    }
    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}