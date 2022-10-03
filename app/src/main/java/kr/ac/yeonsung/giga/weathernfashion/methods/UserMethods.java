package kr.ac.yeonsung.giga.weathernfashion.methods;


import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.ac.yeonsung.giga.weathernfashion.Activities.LoginActivity;
import kr.ac.yeonsung.giga.weathernfashion.VO.User;

public class UserMethods extends Activity{
    List<String> hash = new ArrayList<>();
    FirebaseAuth mAuth;
    API api = new API();
    Boolean result;

    public void join_fb(String email, String passwd, String name, String phone, List<String> styles, Activity activity){
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, passwd)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DatabaseReference mDatabase;
                            mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

                            mDatabase.addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    for(DataSnapshot document: snapshot.getChildren()){
                                        hash.add(document.child("user_email").getValue().toString());
                                    }
                                    if (hash.contains(email)){
                                        api.getToast(activity,"이미 존재하는 이메일입니다.");
                                    }else {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(activity, "성공", Toast.LENGTH_SHORT).show();
                                        join_user(email,passwd,name,phone,styles,activity);
                                        mystartActivity(activity, LoginActivity.class);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });

                        } else {
                            if (hash.contains(email)){
                                api.getToast(activity,"이미 존재하는 이메일입니다.");
                            }else{
                                Toast.makeText(activity, "실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public void join_user(String email, String passwd, String name, String phone, List<String> styles, Activity activity){
        DatabaseReference mDatabase;

        long now = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        Date date = new Date(now);
        String reg_date = sdf.format(date);

        User user = new User(email, passwd, name, phone, styles, reg_date);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("users").push().setValue(user);
    }

    public Boolean email_check(String email){
        result = false;
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

       mDatabase.addValueEventListener(new ValueEventListener() {

           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               hash.clear();
              for(DataSnapshot document: snapshot.getChildren()){
                  hash.add(document.child("user_email").getValue().toString());
              }
           }
           @Override
           public void onCancelled(@NonNull DatabaseError error) {
           }
       });
       System.out.println(hash);
        if(hash.contains(email) == true){
            result = true;
        } else {
            result = false;
        }
       return result;
    }

    public void mystartActivity(Activity activity, Class c){
        Intent intent = new Intent(activity,c);
        startActivity(intent);
    }
}
