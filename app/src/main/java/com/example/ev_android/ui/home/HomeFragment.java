package com.example.ev_android.ui.home;


import static android.content.ContentValues.TAG;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.ev_android.MainActivity;
import com.example.ev_android.R;
import com.example.ev_android.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class HomeFragment extends Fragment {

    static int MAX_STATION = 3;

    static String CAR_NAME;

    static boolean mStation_arr[] = new boolean[MAX_STATION];

    static ImageView Car_color;

    String[] CarNameArr = new String[MAX_STATION];
    String[] Stationstr = new String[MAX_STATION];
    String StationUsing;

    int StationUseNum;

    Integer[] Station = {R.id.Car_img_01, R.id.Car_img_02, R.id.Car_img_03};




    private TextView StationNumText;
    private FragmentHomeBinding binding;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        //파이어베이스에서 데이터 가져오기
        database = FirebaseDatabase.getInstance().getReference();

        CAR_NAME = ((MainActivity)getActivity()).getCAR_NAME();

        //스테이션 데이터 저장
        for (int i = 0; i < Stationstr.length; i++ ){
            Stationstr[i] = Integer.toString(i+1);
        }

        // 실시간으로 데이터베이스의 정보가 변경되면 실행
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                //데이터 값 변수에 저장
                Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                Log.d(TAG, "Value is: " + map);

                StationUsing = "";
                StationUseNum = 0;



                //데이터베이스 값 저장 후 비교
                for (int i = 0; i < Stationstr.length; i++ ){
                    Map map2 = (Map)map.get("Station" + Stationstr[i]);
                    String using = (String)map2.get("USING");
                    String carName = (String)map2.get("CAR_NAME");
                    int payment = Integer.parseInt(String.valueOf(map2.get("PAYMENT")));

                    Car_color = (ImageView)root.findViewById(Station[i]);
                    CarNameArr[i] = carName;


                    if(using.equals("O") && CAR_NAME.equals(CarNameArr[i])){
                        StationUseNum++;

                        mStation_arr[i] = true;
                        Car_color.setImageResource(R.drawable.carimg03);
                    }

                    else if(using.equals("O")){
                        StationUseNum++;

                        mStation_arr[i] = false;
                        Car_color.setImageResource(R.drawable.carimg01);
                    }

                    else if(using.equals("X")){
                        if (mStation_arr[i] == true){
                            Toast.makeText(root.getContext(), "주차 요금이 "+ Integer.toString(payment) + " 원 부과되었습니다.", Toast.LENGTH_SHORT).show();
                        }

                        mStation_arr[i] = false;

                        Car_color.setImageResource(R.drawable.carimg02);
                    }

                    StationUsing = StationUsing + using + " ";
                }

                //USING 값에 따른 텍스트 값 변경
                StationNumText = (TextView) root.findViewById(R.id.useStationNum);
                StationNumText.setText(Integer.toString(StationUseNum));

                Log.d(TAG, "Value is: "+ StationUsing);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    DatabaseReference database;
}
