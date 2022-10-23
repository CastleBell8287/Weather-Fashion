package kr.ac.yeonsung.giga.weathernfashion.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.ac.yeonsung.giga.weathernfashion.Activities.ChatActivity;
import kr.ac.yeonsung.giga.weathernfashion.Activities.MainActivity;
import kr.ac.yeonsung.giga.weathernfashion.Adapter.CategoryAdapter;
import kr.ac.yeonsung.giga.weathernfashion.R;
import kr.ac.yeonsung.giga.weathernfashion.VO.Post;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostViewFragment extends Fragment {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference riversRef = storageRef.child("post");
    StorageReference riversRef2 = storageRef.child("profile");
    String user_id, post_id;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    AlertDialog.Builder builder;
    RecyclerView.Adapter adapter_list;
    RecyclerView recyclerView;
    ArrayList<String> category_list = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
    HashMap<String,Boolean> hash = new HashMap<>();
    String id;
    TextView view_user_name,view_title,view_maxtemp,view_temp,view_mintemp,view_location,view_date,likecount,view_now_date,view_content;
    ImageView view_image,like, delete_post, chat_image;
    CircleImageView user_profile;
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
        recyclerView = view.findViewById(R.id.recyclerView);
        view_user_name = view.findViewById(R.id.view_user_name);
        view_title = view.findViewById(R.id.view_title);
        view_temp = view.findViewById(R.id.view_temp);
        view_maxtemp = view.findViewById(R.id.view_maxtemp);
        view_mintemp = view.findViewById(R.id.view_mintemp);
        view_location = view.findViewById(R.id.view_location);
        likecount = view.findViewById(R.id.likecount);
        like = view.findViewById(R.id.like);
        chat_image = view.findViewById(R.id.chat_image);
        delete_post = view.findViewById(R.id.delete_post);
        view_date = view.findViewById(R.id.view_date);
        view_now_date = view.findViewById(R.id.view_now_date);
        view_content = view.findViewById(R.id.view_content);
        view_image = view.findViewById(R.id.view_image);
        user_profile = view.findViewById(R.id.user_profile);
        recyclerView.setLayoutManager(layoutManager);
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        chat_image.setOnClickListener(chatListener);
        delete_post.setOnClickListener(deleteListener);




        //좋아요 버튼 활성화 체크
        mDatabase.child("post").child(id).child("post_likes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null){
                    hash.clear();
                hash = (HashMap<String, Boolean>) snapshot.getValue();
                System.out.println("좋아요 누른 사람들 :" + hash);

                if (hash.containsKey(user.getUid())) {
                    System.out.println("해쉬에 내 유저 아이디가 있으면" + hash.containsKey(user.getUid()));
                    if (hash.get(user.getUid()) == true) {
                        like.setImageResource(R.drawable.ic_baseline_favorite_24);
                        System.out.println("내 아이디의 값이 true면 : " + hash.get(user.getUid()));
                    } else {
                        like.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        System.out.println("내 아이디의 값이 false면 : " + hash.get(user.getUid()));
                    }
                } else {
                    System.out.println("해쉬에 내 유저 아이디가 없으면" + hash.containsKey(user.getUid()));
                    like.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }
            }else{
                    System.out.println("파이어베이스에 좋아요 누른 사람이 한명도 없으면" + hash.containsKey(user.getUid()));
                    like.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //세팅



        mDatabase.child("post").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                category_list.clear();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yy-MM-dd HH:mm");
                Date date = null;
                Date date2 = null;
                try {
                    date = sdf.parse(snapshot.child("post_date").getValue().toString());
                    date2 = sdf.parse(snapshot.child("post_now_date").getValue().toString());

                    mDatabase.child("users").child(snapshot.child("post_user_id").getValue().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            riversRef2.child(snapshot.child("user_profile").getValue().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Glide.with(getActivity()).load(uri)
                                            .override(50,50)
                                            .into(user_profile);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    String post_user_id_str = snapshot.child("post_user_id").getValue().toString();

                    if(post_user_id_str.equals(user.getUid())){
                        delete_post.setVisibility(View.VISIBLE);
                        chat_image.setVisibility(View.GONE);
                    }else{
                        delete_post.setVisibility(View.GONE);
                        chat_image.setVisibility(View.VISIBLE);
                    }
                    post_id = snapshot.getKey();
                    view_user_name.setText(snapshot.child("post_user_name").getValue().toString());
                    view_title.setText(snapshot.child("post_title").getValue().toString());
                    view_temp.setText(snapshot.child("post_temp").getValue().toString()+"º");
                    view_maxtemp.setText(snapshot.child("post_max_temp").getValue().toString());
                    view_mintemp.setText(snapshot.child("post_min_temp").getValue().toString());
                    view_location.setText(snapshot.child("post_location").getValue().toString());
                    view_maxtemp.setText(snapshot.child("post_max_temp").getValue().toString());
                    likecount.setText(snapshot.child("post_likeCount").getValue().toString());
                    view_date.setText(sdf2.format(date));
                    view_now_date.setText(sdf2.format(date2));
                    user_id = snapshot.child("post_user_id").getValue().toString();
                    user_profile.setOnClickListener(clickListener);
                    view_user_name.setOnClickListener(clickListener);
                    category_list = (ArrayList<String>) snapshot.child("post_categories").getValue();
                    adapter_list = new CategoryAdapter(getContext(),category_list);
                    recyclerView.setAdapter(adapter_list);
                    adapter_list.notifyDataSetChanged();

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
                } catch (NullPointerException e){
                    FragmentManager fm = ((MainActivity) getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction;
                    PostFragment postFragment = new PostFragment();
                    fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.addToBackStack(null)
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace(R.id.main_ly, postFragment)
                            .commit();
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

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!user_id.equals(user.getUid())) {
                Bundle result = new Bundle();
                result.putString("id", user_id);
                FragmentManager fm = ((MainActivity) getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction;
                OtherInfoFragment otherInfoFragment = new OtherInfoFragment();
                otherInfoFragment.setArguments(result);
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.addToBackStack(null)
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.main_ly, otherInfoFragment)
                        .commit();
            }else {
                Bundle result = new Bundle();
                result.putString("id", user_id);
                FragmentManager fm = ((MainActivity) getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction;
                MyInfoFragment myInfoFragment = new MyInfoFragment();
                myInfoFragment.setArguments(result);
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.addToBackStack(null)
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.main_ly, myInfoFragment)
                        .commit();
            }
        }
    };

    View.OnClickListener chatListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("id",user_id);
            startActivity(intent);
        }
    };

    View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            builder = new AlertDialog.Builder(getContext());
            builder.setTitle("글을 삭제하시겠습니까?");

            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                        mDatabase.child("post").child(post_id).removeValue();
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
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