package kr.ac.yeonsung.giga.weathernfashion.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.ac.yeonsung.giga.weathernfashion.Activities.ChatActivity;
import kr.ac.yeonsung.giga.weathernfashion.Activities.MainActivity;
import kr.ac.yeonsung.giga.weathernfashion.Fragment.ChatListFragment;
import kr.ac.yeonsung.giga.weathernfashion.Fragment.PostFragment;
import kr.ac.yeonsung.giga.weathernfashion.Fragment.PostViewFragment;
import kr.ac.yeonsung.giga.weathernfashion.R;
import kr.ac.yeonsung.giga.weathernfashion.VO.PostList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder>{

    private ArrayList<ChatListFragment.ChatList> mData = null ;
    private Context context;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy.MM.dd HH:mm");
    DatabaseReference mDatabase;
    String chat_text;
    String chat_time;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference riversRef = storageRef.child("profile");

    public ChatListAdapter(Context context, ArrayList<ChatListFragment.ChatList> mData) {
        this.mData = mData;
        this.context = context;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user_name, chat_text, chat_time;
        TextView user_id;
        CircleImageView user_profile;
        LinearLayout chatLinear;


        public ViewHolder(View itemView) {
            super(itemView) ;

            user_name = itemView.findViewById(R.id.user_name);
            user_id = itemView.findViewById(R.id.user_id);
            chatLinear = itemView.findViewById(R.id.chat_linear);
            user_profile = itemView.findViewById(R.id.user_profile);
            chat_time = itemView.findViewById(R.id.chat_text);
            chat_time = itemView.findViewById(R.id.chat_time);

        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public ChatListAdapter(ArrayList<ChatListFragment.ChatList> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public ChatListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ChatListAdapter.ViewHolder holder, int position) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String user_id_str = mData.get(position).getUser_id();
        String user_name_str = mData.get(position).getUser_name();
        String user_profile_str = mData.get(position).getUser_profile();
        String chat_id = mData.get(position).getChat_id();

        System.out.println("챗아이디"+chat_id);

        System.out.println(user_id_str);
        System.out.println(user_profile_str);
        System.out.println(user_name_str);
        holder.user_id.setText(user_id_str);
        holder.user_name.setText(user_name_str);

        riversRef.child(user_profile_str).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri)
                        .into(holder.user_profile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });


        holder.chatLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("id",user_id_str);
                context.startActivity(intent);
            }
        });
    }
    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}