package kr.ac.yeonsung.giga.weathernfashion.Activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SurfaceControl;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import kr.ac.yeonsung.giga.weathernfashion.Fragment.PostFragment;
import kr.ac.yeonsung.giga.weathernfashion.Fragment.HomeFragment;
import kr.ac.yeonsung.giga.weathernfashion.Fragment.MyInfoFragment;
import kr.ac.yeonsung.giga.weathernfashion.R;

public class MainActivity extends AppCompatActivity {
    LinearLayout main_ly;
    BottomNavigationView bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(); //객체 정의
        SettingListener(); //리스너 등록

        //맨 처음 시작할 탭 설정
        bottom_nav.setSelectedItemId(R.id.tab_home);
    }


    private void init() {
        main_ly = findViewById(R.id.main_ly);
        bottom_nav = findViewById(R.id.bottom_nav);
    }

    private void SettingListener() {
        //선택 리스너 등록
        bottom_nav.setOnNavigationItemSelectedListener(new TabSelectedListener());
    }

    class TabSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.tab_home: {
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .addToBackStack(null)
                            .replace(R.id.main_ly, new HomeFragment())
                            .commit();
                    return true;
                }
                case R.id.tab_board: {
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .addToBackStack(null)
                            .replace(R.id.main_ly, new PostFragment())
                            .commit();
                    return true;
                }
                case R.id.tab_myinfo: {
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .addToBackStack(null)
                            .replace(R.id.main_ly, new MyInfoFragment())
                            .commit();
                    return true;
                }
            }

            return false;
        }
    }

}