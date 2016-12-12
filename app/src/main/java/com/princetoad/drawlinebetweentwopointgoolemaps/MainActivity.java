package com.princetoad.drawlinebetweentwopointgoolemaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView first_point, second_point;
    private Button btn_draw;
    private AutoCorrect autoCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        control();
    }

    @Override
    protected void onStart() {
        super.onStart();
        autoCorrect.start();
    }

    @Override
    protected void onStop() {
        autoCorrect.stop();
        super.onStop();
    }

    public void init(){
        first_point = (AutoCompleteTextView) findViewById(R.id.first_point);
        second_point = (AutoCompleteTextView) findViewById(R.id.second_point);
        btn_draw = (Button) findViewById(R.id.btn_draw);

        autoCorrect = new AutoCorrect(MainActivity.this);
    }

    public void control(){
        first_point.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoCorrect.autoCorrect(position);
            }
        });
        first_point.setAdapter(autoCorrect.getmPlacesAdapter());

        second_point.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoCorrect.autoCorrect(position);
            }
        });
        second_point.setAdapter(autoCorrect.getmPlacesAdapter());

        btn_draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DrawMaps.class);
                Bundle bundle = new Bundle();
                bundle.putString("first_point", first_point.getText().toString());
                bundle.putString("second_point", second_point.getText().toString());

                i.putExtra("point", bundle);
                startActivity(i);
            }
        });
    }
}
