package kr.ac.yeonsung.giga.weathernfashion.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import kr.ac.yeonsung.giga.weathernfashion.Adapter.BoardListAdapter;
import kr.ac.yeonsung.giga.weathernfashion.Adapter.DailyWeatherAdapter;
import kr.ac.yeonsung.giga.weathernfashion.R;
import kr.ac.yeonsung.giga.weathernfashion.VO.BoardList;
import kr.ac.yeonsung.giga.weathernfashion.VO.Weather;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardFragment extends Fragment {
    RecyclerView.Adapter adapter;
    ArrayList<BoardList> list = new ArrayList();
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> user_name = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BoardFragment newInstance(String param1, String param2) {
        BoardFragment fragment = new BoardFragment();
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
        View view = inflater.inflate(R.layout.fragment_board, container, false);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.grid_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(layoutManager);

        autoCompleteTextView = view.findViewById(R.id.autoDatas);

        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    user_name.add(snapshot1.child("user_name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        autoCompleteTextView.setAdapter(
                new ArrayAdapter<String>(getActivity()
                        ,android.R.layout.simple_dropdown_item_1line
                ,user_name));



        list.add(new BoardList(R.mipmap.ic_launcher_round,"1"));
        list.add(new BoardList(R.mipmap.ic_launcher_round,"1"));
        list.add(new BoardList(R.mipmap.ic_launcher_round,"1"));
        list.add(new BoardList(R.mipmap.ic_launcher_round,"1"));
        list.add(new BoardList(R.mipmap.ic_launcher_round,"1"));
        list.add(new BoardList(R.mipmap.ic_launcher_round,"1"));
        list.add(new BoardList(R.mipmap.ic_launcher_round,"1"));
        list.add(new BoardList(R.mipmap.ic_launcher_round,"1"));
        list.add(new BoardList(R.mipmap.ic_launcher_round,"1"));
        list.add(new BoardList(R.mipmap.ic_launcher_round,"1"));
        list.add(new BoardList(R.mipmap.ic_launcher_round,"1"));
        list.add(new BoardList(R.mipmap.ic_launcher_round,"1"));
        list.add(new BoardList(R.mipmap.ic_launcher_round,"1"));
        list.add(new BoardList(R.mipmap.ic_launcher_round,"1"));
        list.add(new BoardList(R.mipmap.ic_launcher_round,"1"));
        list.add(new BoardList(R.mipmap.ic_launcher_round,"1"));
        list.add(new BoardList(R.mipmap.ic_launcher_round,"1"));
        list.add(new BoardList(R.mipmap.ic_launcher_round,"1"));


        adapter = new BoardListAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}