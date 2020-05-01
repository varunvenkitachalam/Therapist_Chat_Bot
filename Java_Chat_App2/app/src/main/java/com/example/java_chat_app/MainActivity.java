package com.example.java_chat_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    EditText userInput;
    RecyclerView recyclerView;
    List<MessageResponse> messageResponseList;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userInput = findViewById(R.id.userInput);
        recyclerView = findViewById(R.id.conversation);
        messageResponseList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageResponseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(messageAdapter);
        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    MessageResponse message = new MessageResponse(userInput.getText().toString(), true);
                    messageResponseList.add(message);
                    MessageResponse message2 = new MessageResponse(userInput.getText().toString(), false);
                    messageResponseList.add(message2);
                    if(!isVisible()){
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount()-1);
                    }
                    EditText editText = (EditText) findViewById(R.id.userInput);
                    editText.getText().clear();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(userInput.getWindowToken(), 0);
                }
                return true;
            }
        });
    }

    public boolean isVisible(){
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int positionOfLastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        int itemCount = recyclerView.getAdapter().getItemCount();
        return (positionOfLastVisibleItem>=itemCount);
    }
}
