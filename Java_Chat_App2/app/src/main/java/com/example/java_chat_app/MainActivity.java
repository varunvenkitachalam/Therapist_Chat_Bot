
package com.example.java_chat_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.ArrayList;
/*
Creators: Varun Venkitachalam, Salvatore Grillo, Minwoo Soh, Richard Cota
CS4800: ChatBot Application
 */
public class MainActivity extends AppCompatActivity {
    EditText userInput;                     // will hold userinput text
    RecyclerView recyclerView;              // the order the scroll viewer
    List<MessageResponse> messageResponseList;  // the total list of messages
    MessageAdapter messageAdapter;              // our message holder to check whose message should be sent

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
        MessageResponse firstMessage = new MessageResponse("Thanks for waiting! I am Eve, your personal Therapy Chatbot. Feel free to talk to me or ask me any questions!", false);
        messageResponseList.add(firstMessage);
        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    MessageResponse message = new MessageResponse(userInput.getText().toString(), true);
                    messageResponseList.add(message);
                    getResponse(userInput.getText().toString());
                }
                return true;
            }
        });
    }
    // passes our userinput to the server with REST api and volley library and sets returned message as bot message
    public void getResponse(String input){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "https://psychbot-cs4800.herokuapp.com/api/send_message/";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("message", input);
            jsonBody.put("session_id", "7");
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject respObject = new JSONObject(response);
                        String serverResponse = respObject.getString("message");
                        Log.i("VOLLEY", serverResponse);
                        MessageResponse message2 = new MessageResponse(serverResponse, false);
                        messageResponseList.add(message2);
                        if(!isVisible()){
                            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount()-1);
                        }
                        EditText editText = (EditText) findViewById(R.id.userInput);
                        editText.getText().clear();
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(userInput.getWindowToken(), 0);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return super.parseNetworkResponse(response);
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    // scrolls down to the latest message after the user presses send
    public boolean isVisible(){
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int positionOfLastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        int itemCount = recyclerView.getAdapter().getItemCount();
        return (positionOfLastVisibleItem>=itemCount);
    }
}
