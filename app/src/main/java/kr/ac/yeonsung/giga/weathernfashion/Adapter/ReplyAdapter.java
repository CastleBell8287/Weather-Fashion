package kr.ac.yeonsung.giga.weathernfashion.Adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.ac.yeonsung.giga.weathernfashion.Activities.MainActivity;
import kr.ac.yeonsung.giga.weathernfashion.Fragment.PostViewFragment;
import kr.ac.yeonsung.giga.weathernfashion.R;
import kr.ac.yeonsung.giga.weathernfashion.VO.PostList;
import kr.ac.yeonsung.giga.weathernfashion.VO.TempReply;

public class ReplyAdapter extends BaseAdapter {

    private ArrayList<TempReply> mData = null ;
    private Context context;
    private CircleImageView user_profile;
    private TextView user_name, reply_text, reply_time,user_id, reply_like_count, comment_count;
    private LayoutInflater mLayoutInflater;
    private ImageView reply_like, reply_comment;
    public ReplyAdapter(Context context, ArrayList<TempReply> mData) {
        this.mData = mData;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.reply_list_item, null);
        user_profile = (CircleImageView) view.findViewById(R.id.user_profile);
        user_name = (TextView) view.findViewById(R.id.user_name);
        reply_text = (TextView)view.findViewById(R.id.reply_text);
        reply_time = (TextView)view.findViewById(R.id.reply_time);
        user_id = (TextView)view.findViewById(R.id.user_id);
        reply_like = (ImageView) view.findViewById(R.id.reply_like);
        reply_comment = (ImageView)view.findViewById(R.id.reply_comment);
        reply_like_count = (TextView) view.findViewById(R.id.reply_like_count);
        comment_count = (TextView) view.findViewById(R.id.comment_count);

        user_name.setText(mData.get(position).getName());
        reply_text.setText(mData.get(position).getContent());
        reply_time.setText(mData.get(position).getTime());
        user_id.setText(mData.get(position).getUser_id());

        return view;


    }



}