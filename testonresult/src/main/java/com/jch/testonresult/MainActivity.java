package com.jch.testonresult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.HashMap;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testMap();
    }

    public void next(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivityForResult(intent, 255);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_OK: {

                Log.d("test_tag", "fdsfsf--------");

            }
        }

    }


    public void testMap() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("test", "test1");
        boolean containB = map.containsKey("test");

        Log.w("test", "map contains test:" + containB);

    }
}
