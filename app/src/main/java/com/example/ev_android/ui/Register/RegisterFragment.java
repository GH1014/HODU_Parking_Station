package com.example.ev_android.ui.Register;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ev_android.MainActivity;
import com.example.ev_android.R;
import com.example.ev_android.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static String IP_ADDRESS = "10.0.2.2";
    private static String TAG = "EV_Information";

    private EditText mEditTextTAG_ID;
    private EditText mEditTextCAR_NAME;
    private EditText mEditTextCARD_NUM;

    private TextView mTextViewResult;

    View root;

    private static String CAR_NAME;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        mEditTextTAG_ID = (EditText)root.findViewById(R.id.editText_TAG_ID);
        mEditTextCAR_NAME = (EditText)root.findViewById(R.id.editText_CAR_NAME);
        mEditTextCARD_NUM = (EditText)root.findViewById(R.id.editText_CARD_NUM);

        mTextViewResult = (TextView)root.findViewById(R.id.textView_main_result);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());


        Button buttonInsert = (Button)root.findViewById(R.id.button_main_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CAR_NAME = mEditTextCAR_NAME.getText().toString();

                mEditTextTAG_ID.setText("");
                mEditTextCAR_NAME.setText("");
                mEditTextCARD_NUM.setText("");

                clear();

                setCarName();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setCarName(){
        Bundle bundle = new Bundle();
        bundle.putString("ID",CAR_NAME);

        Toast.makeText(root.getContext(), "등록되었습니다.", Toast.LENGTH_SHORT).show();

        // 자동차 이름 메인 엑티비티에 전달
        ((MainActivity)getActivity()).setCAR_NAME(CAR_NAME);

        // 앱을 다시 실행시켜도 정보가 유지될 수 있도록 자동차 이름 사용자 기기에 저장
        pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("CAR_NAME", CAR_NAME);
        editor.commit();
    }

    public void clear() {

        SharedPreferences prefs = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();

    }


}

