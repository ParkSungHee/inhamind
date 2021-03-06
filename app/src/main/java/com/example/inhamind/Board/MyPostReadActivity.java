package com.example.inhamind.Board;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.Common.FirebaseID;
import com.example.inhamind.Models.DataName;
import com.example.inhamind.Models.Post;
import com.example.inhamind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;

public class MyPostReadActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    private EditText postTitle, postContents, postStatus;
    private String title, contents, studentID, status;
    @ServerTimestamp
    private Timestamp timestamp;
    private Intent intent;
    private Post post;
    private Button doneBtn, cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post_read);

        intent = getIntent();
        post = intent.getParcelableExtra(DataName.data);

        title = post.getTitle();
        contents = post.getContents();
        studentID = post.getStudentID();
        status = post.getStatus();
        timestamp = post.getTimestamp();

        postTitle = findViewById(R.id.post_title);
        postContents = findViewById(R.id.post_contents);
        postStatus = findViewById(R.id.post_status);
        doneBtn = findViewById(R.id.done);
        doneBtn.setOnClickListener(this);
        cancelBtn = findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(this);

        postTitle.setText(title);
        postContents.setText(contents);

        if (status.equals("true")) {
            postStatus.setText("완료");
            postStatus.setTextColor(Color.GREEN);
        } else if (status.equals("false")) {
            postStatus.setText("미완료");
            postStatus.setTextColor(Color.RED);
        }

        findViewById(R.id.status_button).setOnClickListener(this);
        findViewById(R.id.close_button).setOnClickListener(this);
        findViewById(R.id.option_button).setOnClickListener(this);

    }

    public void edittextStatusSetting(EditText e, boolean status) {
        e.setClickable(status);
        e.setFocusable(status);
    }

    public void buttonStatusSetting(Button b, boolean status) {
        b.setClickable(status);
        b.setFocusable(status);
        b.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_button:
                this.finish();
                break;

            case R.id.status_button:
                PopupMenu statusPopupMenu = new PopupMenu(MyPostReadActivity.this, view, Gravity.RIGHT);
                getMenuInflater().inflate(R.menu.status_popup, statusPopupMenu.getMenu());
                statusPopupMenu.setOnMenuItemClickListener(listener);
                statusPopupMenu.show();
                break;

            case R.id.option_button:
                PopupMenu popupMenu = new PopupMenu(MyPostReadActivity.this, view);
                getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(listener);
                popupMenu.show();
                break;

            case R.id.cancel:
                postTitle.setText(title);
                postContents.setText(contents);
                buttonStatusSetting(doneBtn, false);
                buttonStatusSetting(cancelBtn, false);
                edittextStatusSetting(postTitle, false);
                edittextStatusSetting(postContents, false);
                break;
            case R.id.done:
                String newTitle = postTitle.getText().toString();
                String newContents = postContents.getText().toString();
                if (newTitle.length() == 0)
                    Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                else if (newContents.length() == 0)
                    Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                else {
                    if (mUser != null) {
                        mStore.collection(FirebaseID.post)
                                .document(post.getPostID())
                                .update(FirebaseID.title, newTitle, FirebaseID.contents, newContents)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task != null) {
                                            Toast.makeText(MyPostReadActivity.this, "수정 완료", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    postTitle.setText(newTitle);
                    postContents.setText(newContents);
                    buttonStatusSetting(doneBtn, false);
                    buttonStatusSetting(cancelBtn, false);
                    edittextStatusSetting(postTitle, false);
                    edittextStatusSetting(postContents, false);
                }
                break;
        }
    }

    PopupMenu.OnMenuItemClickListener listener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.modify:
                    buttonStatusSetting(doneBtn, true);
                    buttonStatusSetting(cancelBtn, true);
                    cancelBtn.setClickable(true);
                    postTitle.setFocusableInTouchMode(true);
                    postTitle.requestFocus();
                    postContents.setFocusableInTouchMode(true);
                    postContents.requestFocus();
                    break;
                case R.id.delete:
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyPostReadActivity.this);
                    builder.setTitle("삭제 하시겠습니까?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (mUser != null) {
                                mStore.collection(FirebaseID.post)
                                        .document(post.getPostID())
                                        .delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task != null) {
                                                    finish();
                                                    Toast.makeText(MyPostReadActivity.this, "삭제가 완료 됐습니다.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                    });
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    break;
                case R.id.ready:
                    postStatus.setText("미완료");
                    postStatus.setTextColor(Color.RED);
                    if(mUser !=null){
                        mStore.collection(FirebaseID.post)
                                .document(post.getPostID())
                                .update(FirebaseID.status, "false")
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task != null) {
                                            Toast.makeText(MyPostReadActivity.this, "상태 변경 완료", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    break;
                case R.id.done:
                    postStatus.setText("완료");
                    postStatus.setTextColor(Color.GREEN);
                    if(mUser !=null){
                        mStore.collection(FirebaseID.post)
                                .document(post.getPostID())
                                .update(FirebaseID.status, "true")
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task != null) {
                                            Toast.makeText(MyPostReadActivity.this, "상태 변경 완료", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    break;
            }
            return false;
        }
    };
}
