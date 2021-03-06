package com.example.inhamind.Board;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.Chat.MessageActivity;
import com.example.inhamind.Models.DataName;
import com.example.inhamind.Models.Post;
import com.example.inhamind.R;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class PostReadActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView postTitle, postContents, postStudentID, postStatus;
    private ImageButton chatting;
    private String title, contents, studentID, status, documentID;
    private Intent intent;
    private Post post;
    @ServerTimestamp
    private Timestamp timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_read);

        intent = getIntent();
        post = intent.getParcelableExtra(DataName.data);

        title = post.getTitle();
        contents = post.getContents();
        studentID = post.getStudentID();
        status = post.getStatus();
        timestamp = post.getTimestamp();
        documentID = post.getDocumentID();

        postTitle = findViewById(R.id.post_title);
        postContents = findViewById(R.id.post_contents);
        postStudentID = findViewById(R.id.post_student_id);
        postStatus = findViewById(R.id.post_status);
        chatting = findViewById(R.id.read_chat_button);

        postTitle.setText(title);
        postContents.setText(contents);
        postStudentID.setText(studentID);

        findViewById(R.id.read_close_button).setOnClickListener(this);
        findViewById(R.id.read_chat_button).setOnClickListener(this);

        if (status.equals("true")) {
            postStatus.setText("완료");
            postStatus.setTextColor(Color.BLUE);
            chatting.setVisibility(View.INVISIBLE);
            chatting.setFocusable(false);
            chatting.setClickable(false);
        } else {
            postStatus.setText("미완료");
            postStatus.setTextColor(Color.RED);
            chatting.setVisibility(View.VISIBLE);
            chatting.setFocusable(true);
            chatting.setClickable(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.read_close_button:
                this.finish();
                break;

            case R.id.read_chat_button:
                Intent intent = new Intent(view.getContext(), MessageActivity.class);
                intent.putExtra("destinationUid", documentID);
                ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(view.getContext(), R.anim.fromright, R.anim.toleft);
                startActivity(intent, activityOptions.toBundle());
                break;
        }

    }
}
