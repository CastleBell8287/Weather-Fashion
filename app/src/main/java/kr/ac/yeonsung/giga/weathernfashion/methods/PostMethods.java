package kr.ac.yeonsung.giga.weathernfashion.methods;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostMethods extends Activity {

    public void onBackPresse(Activity activity){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("뒤로가기");
        alert.setMessage("작성중인 정보가 사라질 수 있습니다");
        alert.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                });
        alert.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
//                        Intent intent = new Intent(activity, HomeFragment.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
//                        startActivity(intent);  //인텐트 이동
//                        finish();   //현재 액티비티 종료
                    }
                });
        alert.show();
    }

    private final StorageReference reference = FirebaseStorage.getInstance().getReference();
//    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private void uploadImageToFirebase(Uri uri) {
        StorageReference fileRef = reference.child("사진아이디"+".jpg");
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // 사진 업로드 성공했을때 액션
                        // 리얼타임 데이터 베이스에 사진경로 저장

                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
            //    Toast.makeText(액티비티명.this,"업로드 완료",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            //    Toast.makeText(액티비티명.this,"업로드 실패",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getWeatherNow_post(Activity activity, Float lat_post, Float lon_post, String time, TextView temp, TextView mintemp, TextView maxtemp,TextView date){
        try {

            time = time.replaceFirst(":", "-");
            time = time.replaceFirst(":", "-");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date dateformat = sdf.parse(time);

            long timestamp = dateformat.getTime();

            System.out.println("데이트 파스 : "+timestamp);
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat="+lat_post+"&lon="+lon_post
                    +"&lang=kr&dt="+timestamp+"&appid=623ffab3e9d338b9916bd0b0e33d5d87&units=metric");
            BufferedReader bf;
            String line;
            String result="";

            //날씨 정보를 받아온다.
            bf = new BufferedReader(new InputStreamReader(url.openStream()));

            //버퍼에 있는 정보를 문자열로 변환.
            while((line=bf.readLine())!=null){
                result=result.concat(line+"\n");
            }
            System.out.println(result);
            //문자열을 JSON으로 파싱
            JSONObject jsonObj = new JSONObject(result);
            JSONObject main = jsonObj.getJSONObject("main");
            JSONArray weather = jsonObj.getJSONArray("weather");
            JSONObject wind = jsonObj.getJSONObject("wind");
            JSONObject clouds = jsonObj.getJSONObject("clouds");
            JSONObject weatherdesc = weather.getJSONObject(0);

            String temp_str;
            String min_temp;
            String max_temp;
            if (main.getString("temp").contains(".")) {
                int temp_idx = main.getString("temp").indexOf(".");
                temp_str = main.getString("temp").substring(0,temp_idx);
            } else {
                temp_str = main.getString("temp");
            }
            if (main.getString("temp_min").contains(".")) {
                int min_temp_idx = main.getString("temp_min").indexOf(".");
                min_temp = main.getString("temp_min").substring(0,min_temp_idx);  // 소수점 첫째 자리까지만 출력
            } else{
                min_temp = main.getString("temp_min");
            }

            if (main.getString("temp_max").contains(".")) {
                int max_temp_idx = main.getString("temp_max").indexOf(".");
                max_temp = main.getString("temp_max").substring(0,max_temp_idx);  // 소수점 첫째 자리까지만 출력
            } else{
                max_temp = main.getString("temp_max");
            }
            mintemp.setText(min_temp+"º");
            maxtemp.setText(max_temp+"º");
            temp.setText(temp_str);
            date.setText(time);
            date.setVisibility(View.VISIBLE);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


//
//    private String getFileExtension(Uri uri){
//        ContentResolver cr = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cr.getType(uri));
//    }

}
