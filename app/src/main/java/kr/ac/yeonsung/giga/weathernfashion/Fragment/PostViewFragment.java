package kr.ac.yeonsung.giga.weathernfashion.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import kr.ac.yeonsung.giga.weathernfashion.R;
import kr.ac.yeonsung.giga.weathernfashion.VO.Post;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostViewFragment extends Fragment {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference riversRef = storageRef.child("post");
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    String id;
    TextView view_user_name,view_title,view_maxtemp,view_mintemp,view_location,view_date,likecount,view_now_date,view_content;
    ImageView view_image,like;
    public static final String LOCAL_BROADCAST = "com.xfhy.casualweather.LOCAL_BROADCAST";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostViewFragment newInstance(String param1, String param2) {
        PostViewFragment fragment = new PostViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_view, container, false);


        return inflater.inflate(R.layout.fragment_post_view, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view_user_name = view.findViewById(R.id.view_user_name);
        view_title = view.findViewById(R.id.view_title);
        view_maxtemp = view.findViewById(R.id.view_maxtemp);
        view_mintemp = view.findViewById(R.id.view_mintemp);
        view_location = view.findViewById(R.id.view_location);
        likecount = view.findViewById(R.id.likecount);
        like = view.findViewById(R.id.like);
        view_date = view.findViewById(R.id.view_date);
        view_now_date = view.findViewById(R.id.view_now_date);
        view_content = view.findViewById(R.id.view_content);
        view_image = view.findViewById(R.id.view_image);
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        mDatabase.child("post").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                Date date2 = null;
                try {
                    date = sdf.parse(snapshot.child("post_date").getValue().toString());
                    date2 = sdf.parse(snapshot.child("post_now_date").getValue().toString());
                    view_user_name.setText(snapshot.child("post_user_name").getValue().toString());
                    view_title.setText(snapshot.child("post_title").getValue().toString());
                    view_maxtemp.setText(snapshot.child("post_max_temp").getValue().toString());
                    view_mintemp.setText(snapshot.child("post_min_temp").getValue().toString());
                    view_location.setText(snapshot.child("post_location").getValue().toString());
                    view_maxtemp.setText(snapshot.child("post_max_temp").getValue().toString());
                    likecount.setText(snapshot.child("post_likeCount").getValue().toString());
                    view_date.setText(sdf2.format(date));
                    view_now_date.setText(sdf2.format(date2));
                    view_content.setText(snapshot.child("post_content").getValue().toString());
                    riversRef.child(snapshot.child("post_image").getValue().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(getActivity()).load(uri)
                                    .override(400,400)
                                    .into(view_image);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                        }
                    });
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("id :" + id);
                onLikeClicked(mDatabase.child("post").child(id));
            }
        });

    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.like:

                    break;
            }
        }
    };
    private void onLikeClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {

            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Post postVo = mutableData.getValue(Post.class);
                if (postVo == null) {
                    return Transaction.success(mutableData);
                }

                //좋아요 누른 사람을 확인
                if (postVo.getPost_likes().containsKey(mAuth.getCurrentUser().getUid()))
                {
                    // Unstar the post and remove self from stars
                    //좋아요 취소
                    postVo.setPost_likeCount(postVo.getPost_likeCount() - 1);
                    postVo.getPost_likes().remove(mAuth.getCurrentUser().getUid());
                } else {
                    // Star the post and add self to stars
                    //좋아요 증가
                    postVo.setPost_likeCount(postVo.getPost_likeCount() + 1);
                    postVo.getPost_likes().put(mAuth.getCurrentUser().getUid(), true);
                }

                // Set value and report transaction success
                System.out.println(postVo);
                mutableData.setValue(postVo);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean committed,
                                   DataSnapshot currentData) {
                // Transaction completed
            }
        });
    }

}