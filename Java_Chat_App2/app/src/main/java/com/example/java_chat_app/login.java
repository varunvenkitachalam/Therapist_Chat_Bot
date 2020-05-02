package com.example.java_chat_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()
         {
          @Override
          public void onClick(View v)
           {
            openWait();
           }
         });
    }
   public void openWait()
    {
     Intent intent = new Intent(this, wait.class);
     startActivity(intent);
    }
}
