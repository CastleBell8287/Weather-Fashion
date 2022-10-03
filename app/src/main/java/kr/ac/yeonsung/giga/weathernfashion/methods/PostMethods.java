package kr.ac.yeonsung.giga.weathernfashion.methods;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import kr.ac.yeonsung.giga.weathernfashion.Activities.PostActivity;
import kr.ac.yeonsung.giga.weathernfashion.Fragment.BoardFragment;
import kr.ac.yeonsung.giga.weathernfashion.Fragment.HomeFragment;

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

//
//    private String getFileExtension(Uri uri){
//        ContentResolver cr = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cr.getType(uri));
//    }

}
