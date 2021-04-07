package com.sufi.prayertimes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    private TextView title, content;
    private ImageView bkImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        title = findViewById(R.id.notificationTitle);
        content = findViewById(R.id.notificationContent);
        bkImage = findViewById(R.id.notiBG);

        if (getIntent().getExtras() != null){
            for (String key : getIntent().getExtras().keySet()){
                if (key.equals("title")){
                    title.setText(getIntent().getExtras().getString(key));
                }else if (key.equals("content")){
                    content.setText(getIntent().getExtras().getString(key));
                }
            }
        }

    }
}
