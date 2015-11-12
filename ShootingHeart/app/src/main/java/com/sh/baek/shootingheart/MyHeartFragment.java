package com.sh.baek.shootingheart;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.sh.baek.shootingheart.Heart.BitmapLab;


/**
 * Created by BAEK on 7/27/2015.
 */
public class MyHeartFragment extends Fragment {

    private static final String TAG = "MyHeartFragment";
    private ImageButton mButtonFriendsList;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_my_heart, parent, false);
        mButtonFriendsList = (ImageButton) v.findViewById(R.id.btn_friendsListFragment);
        mButtonFriendsList.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v){
                Log.d(TAG,"btn clicked");
                Intent i = new Intent(getActivity(),FriendsListActivity.class);
                startActivity(i);
            }
        });
        Log.d(TAG, "onCreateView was created");
        return v;
    }
}
