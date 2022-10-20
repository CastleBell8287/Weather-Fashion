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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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

    public String getAddressApi(float lat_post, float lon_post){
        String address = "";
        try {
            URL url2 = new URL("http://api.vworld.kr/req/address?service=address&request=get" +
                    "Address&key=173F9427-85AF-30BF-808F-DCB8F163058B&point=" +
                    +lon_post + "," + lat_post + "&type=PARCEL&format=json\n");
            BufferedReader bf2;
            String line2;
            String result2 = "";

            //날씨 정보를 받아온다.
            bf2 = new BufferedReader(new InputStreamReader(url2.openStream()));

            //버퍼에 있는 정보를 문자열로 변환.
            while ((line2 = bf2.readLine()) != null) {
                result2 = result2.concat(line2 + "\n");
            }

            System.out.println(result2);


            JSONObject jsonObj = new JSONObject(result2);
            JSONObject response = jsonObj.getJSONObject("response");
            JSONArray result3 = response.getJSONArray("result");
            JSONObject structure = result3.getJSONObject(0);

            String str= structure.get("text").toString();
            System.out.println(str);
            if(str.contains("서울")){
                address = "108";
            }else if (str.contains("안양")){
                address = "108";
            }
            else if (str.contains("고양")){
                address = "108";
            }

        }catch (Exception e){}
        return address;
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

    public void getWeatherNow_post2(Activity activity, Float lat_post, Float lon_post, String time, TextView temp, TextView mintemp, TextView maxtemp,TextView date){
        try {

            time = time.replaceFirst(":", "");
            time = time.replaceFirst(":", "");


            String dateStr = time.substring(0,8);
            String addressCode = getAddressApi(lat_post, lon_post);
            System.out.println(addressCode + " " + dateStr);

            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/AsosDalyInfoService/getWthrDataList"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Oc76de7i7dgYvE2mefgKF3S3LR1TgmVuSEQFf4LDmbwhYuNLr%2F8%2FijeD%2FOJB6CMY7yDRNyc%2B0lyeb1YEwG%2BqHg%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호 Default : 1*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수 Default : 10*/
            urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default : XML*/
            urlBuilder.append("&" + URLEncoder.encode("dataCd","UTF-8") + "=" + URLEncoder.encode("ASOS", "UTF-8")); /*자료 분류 코드(ASOS)*/
            urlBuilder.append("&" + URLEncoder.encode("dateCd","UTF-8") + "=" + URLEncoder.encode("DAY", "UTF-8")); /*날짜 분류 코드(DAY)*/
            urlBuilder.append("&" + URLEncoder.encode("startDt","UTF-8") + "=" + URLEncoder.encode(dateStr, "UTF-8")); /*조회 기간 시작일(YYYYMMDD)*/
            urlBuilder.append("&" + URLEncoder.encode("endDt","UTF-8") + "=" + URLEncoder.encode(dateStr, "UTF-8")); /*조회 기간 종료일(YYYYMMDD) (전일(D-1)까지 제공)*/
            urlBuilder.append("&" + URLEncoder.encode("stnIds","UTF-8") + "=" + URLEncoder.encode(addressCode, "UTF-8")); /*종관기상관측 지점 번호 (활용가이드 하단 첨부 참조)*/
            URL url3 = new URL(urlBuilder.toString());
            System.out.println(url3);
            HttpURLConnection conn = (HttpURLConnection) url3.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;

            JSONObject jsonObj = new JSONObject(rd.readLine());
            JSONObject response1 = (JSONObject) jsonObj.get("response");
            JSONObject body = (JSONObject) response1.get("body");

            JSONObject items = (JSONObject) body.get("items");
            JSONArray item = (JSONArray) items.get("item");
            JSONObject map2 = (JSONObject)item.get(0);
            System.out.println(map2);
            String temp_str;
            String min_temp;
            String max_temp;
//            System.out.println(map);
            if (map2.get("avgTa").toString().contains(".")) {
                int temp_idx = map2.get("avgTa").toString().indexOf(".");
                temp_str = map2.get("avgTa").toString().substring(0,temp_idx);
            } else {
                temp_str = map2.get("avgTa").toString();
            }
            if (map2.get("minTa").toString().contains(".")) {
                int min_temp_idx = map2.get("minTa").toString().indexOf(".");
                min_temp = map2.get("minTa").toString().substring(0,min_temp_idx);  // 소수점 첫째 자리까지만 출력
            } else{
                min_temp = map2.get("minTa").toString();
            }if (map2.get("maxTa").toString().contains(".")) {
                int min_temp_idx = map2.get("maxTa").toString().indexOf(".");
                max_temp = map2.get("maxTa").toString().substring(0,min_temp_idx);  // 소수점 첫째 자리까지만 출력
            } else{
                max_temp = map2.get("minTa").toString();
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
