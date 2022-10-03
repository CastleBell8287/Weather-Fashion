package kr.ac.yeonsung.giga.weathernfashion.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import kr.ac.yeonsung.giga.weathernfashion.R;
import kr.ac.yeonsung.giga.weathernfashion.VO.Categories;
import kr.ac.yeonsung.giga.weathernfashion.methods.PostMethods;

public class PostActivity extends AppCompatActivity {

    private boolean valid = false;
    private Float lat =0.1f ,lon = 1.2f;
    Geocoder g;
    private Uri imageUri;

    PostMethods postMethods = new PostMethods();
    TextView back_to_post_main,post_done,photo_weather,choice_post_categotis
            ,choice_post_categotis2,post_selected_category;
    EditText post_title,post_main_text;
    ImageView post_img;
    List<String> mSelectedItems;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        g = new Geocoder(this);
        setContentView(R.layout.activity_post);
        back_to_post_main = findViewById(R.id.back_to_post_main);
        post_done = findViewById(R.id.post_done);
        post_title = findViewById(R.id.post_title);
        post_main_text = findViewById(R.id.post_main_text);
        photo_weather = findViewById(R.id.photo_weather);
        choice_post_categotis = findViewById(R.id.choice_post_categoris);
        choice_post_categotis2 = findViewById(R.id.choice_post_categoris2);
        post_selected_category = findViewById(R.id.post_selected_category);
        post_img = findViewById(R.id.post_img);

        //리스너 등록
//        back_to_post_main.setOnClickListener(back_to);
        post_img.setOnClickListener(get_post_img);
//        choice_post_categotis.setOnClickListener(choice_post_categoty);
        choice_post_categotis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }
//
//    View.OnClickListener choice_post_categoty = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Intent intent1 = new Intent(getApplicationContext(), PopupActivity.class);
//
//            try{
//                startActivityForResult(intent1,0);
//            }catch (NullPointerException e){
//                e.printStackTrace();
//            }catch (RuntimeException e){
//
//            }
//
//
//        }
//    };



    public void showDialog(){
        mSelectedItems = new ArrayList<>();
        builder = new AlertDialog.Builder(PostActivity.this);
        builder.setTitle(" 카테고리 선택");
        builder.setMultiChoiceItems(R.array.categoris, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                String[] items = getResources().getStringArray(R.array.categoris);
                if(isChecked){
                    mSelectedItems.add(items[which]);
                }else if(mSelectedItems.contains(items[which])){
                    mSelectedItems.remove(items[which]);
                }
            }
        });

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String final_selection = "";
                for(String item : mSelectedItems){
                    final_selection = final_selection +" "+item;
                }
                Toast.makeText(getApplicationContext()," 선택 카테고리 "+ final_selection , Toast.LENGTH_SHORT).show();
                post_selected_category.setText(final_selection);
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



    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        post_selected_category.setText(" ");
        String[] cate = new String[5];
        if (data.getStringExtra("casual")!=null){cate[0]="캐쥬얼 ";}
        else{cate[0]="";}
        if (data.getStringExtra("minimal")!=null){cate[1]="미니멀 ";}
        else{cate[1]="";}
        if (data.getStringExtra("american")!=null){cate[2]="아메카지 ";}
        else{cate[2]="";}
        if (data.getStringExtra("street")!=null){cate[3]="스트릿 ";}
        else{cate[3]="";}
        if (data.getStringExtra("etc")!=null){cate[4]="기타 ";}
        else{cate[4]="";}


        for(int i = 0; i <= 4 ; i++){
            post_selected_category.append(cate[i]);
        }


    }

//    상단 뒤로가기
//    View.OnClickListener back_to = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            postMethods.onBackPresse(PostActivity.this);
//        }
//    };


    // 사진 갤러리에서 선택
    View.OnClickListener get_post_img = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/");
            activityResult.launch(galleryIntent);
        }
    };

    ActivityResultLauncher<Intent> activityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== RESULT_OK && result.getData() != null){
                        imageUri = result.getData().getData();
                        post_img.setImageURI(imageUri);

                        try {
                            ExifInterface exif = new ExifInterface(getRealPathFromURI(imageUri));
                            showExif(exif);
                            getAddress();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
    // 사진 갤러리에서 선택

    private void showExif(ExifInterface exif) {
        String attrLATITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
        String attrLATITUDE_REF = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
        String attrLONGITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
        String attrLONGITUDE_REF = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
        String attrDate =exif.getAttribute(ExifInterface.TAG_DATETIME);

        if((attrLATITUDE!=null)&&(attrLATITUDE_REF!=null)&&(attrLONGITUDE!=null) &&(attrLONGITUDE_REF!=null)){
            valid=true;
            if(attrLATITUDE_REF.equals("N")){
                lat=convertToDegree(attrLATITUDE);
            }
            else{
                lat=0-convertToDegree(attrLATITUDE);
            }
            if(attrLONGITUDE_REF.equals("E")){
                lon=convertToDegree(attrLONGITUDE);
            }
            else{
                lon=0-convertToDegree(attrLONGITUDE);
            }
        }
        photo_weather.setText(lat+" , "+lon+"\n"+attrDate);
    }

    private String getRealPathFromURI(Uri uri){
        String result;
        Cursor cursor=getContentResolver().query(uri,null,null,null,null);
        if(cursor==null){
            result = uri.getPath();
        }
        else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private Float convertToDegree(String stringDMS) {
        Float result = null;
        String [] DMS = stringDMS.split(",",3);

        String[] stringD = DMS[0].split("/",2);
        Double D0 = Double.valueOf(stringD[0]);
        Double D1 = Double.valueOf(stringD[1]);
        Double FloatD = D0/D1;

        String[] stringM = DMS[1].split("/",2);
        Double M0 = Double.valueOf(stringM[0]);
        Double M1 = Double.valueOf(stringM[1]);
        Double FloatM = M0/M1;

        String[] stringS = DMS[2].split("/",2);
        Double S0 = Double.valueOf(stringS[0]);
        Double S1 = Double.valueOf(stringS[1]);
        Double FloatS = S0/S1;

        result = (float) (FloatD + (FloatM / 60) + (FloatS / 3600));

        return result;
    }

    public Float getLatitude() {
        return lat;
    }

    public Float getLongitude() {
        return lon;
    }

    public String getAddress() {
        List<Address> address=null;
        try {
            address = g.getFromLocation(getLatitude(),getLongitude(),10);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("test","입출력오류");
        }
        if(address!=null){
            if(address.size()==0){
                System.out.println(getLatitude()+"시팔");
                System.out.println(getLongitude()+"시팔");
                Log.d("test", "주소찾기 오류");
            }else{
                Log.d("찾은 주소",address.get(0).getAddressLine(0));
                System.out.println(address.get(0).getAddressLine(0)+"주소");
                return address.get(0).getAddressLine(0);
            }
        }
        return null;
    }


}