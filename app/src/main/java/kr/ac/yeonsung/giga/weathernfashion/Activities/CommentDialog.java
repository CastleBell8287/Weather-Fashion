package kr.ac.yeonsung.giga.weathernfashion.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import kr.ac.yeonsung.giga.weathernfashion.Fragment.MyInfoFragment;
import kr.ac.yeonsung.giga.weathernfashion.R;

public class CommentDialog extends Activity {
    TextView my_comment;
    Button btn_modify;
    EditText dlg_edit_intro;
    View dialogView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_myinfo);
        setTitle("한줄 소개 입력");

        my_comment = findViewById(R.id.my_comment);
        btn_modify = findViewById(R.id.btn_modify);

        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView = View.inflate(CommentDialog.this, R.layout.dialog_modify,null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(CommentDialog.this);
                dlg.setTitle("한줄 소개 입력");
                dlg.setView(dialogView);
                dlg.setPositiveButton("변경", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dlg_edit_intro = dialogView.findViewById(R.id.dlg_edit_intro);

                        my_comment.setText(dlg_edit_intro.getText().toString());
                    }
                });
                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast toast = Toast.makeText(getApplicationContext(), "취소되었습니다.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
                dlg.show();
            }
        });
    }


}
