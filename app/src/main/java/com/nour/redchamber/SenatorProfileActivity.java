package com.nour.redchamber;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import android.widget.ImageView;

import com.nour.redchamber.models.Senators;
import com.squareup.picasso.Picasso;

public class SenatorProfileActivity extends AppCompatActivity {
    private CircleImageView senPicture;
    private TextView senName;
    private TextView senDistrict;
    private TextView senParty;
    private TextView mobileNumber;
    private TextView email;
    private ImageView dialer;
    private ImageView message;
    private ImageView sendMail;
    private int senPos;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.senator_profile_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();

        populateProfile();

        dialer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + mobileNumber.getText().toString()));

                if(callIntent.resolveActivity(getPackageManager()) != null)
                startActivity(callIntent);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent messageIntent = new Intent(Intent.ACTION_SENDTO);
                messageIntent.setData(Uri.parse("smsto:" + mobileNumber.getText().toString()));
                messageIntent.putExtra("sms_body", "");

                if(messageIntent.resolveActivity(getPackageManager()) != null && !mobileNumber.getText().toString().equals("Not Available"))
                startActivity(messageIntent);
            }
        });

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mail = new Intent(Intent.ACTION_SENDTO);
                mail.setData(Uri.parse("mailto:"));
                mail.putExtra(Intent.EXTRA_EMAIL, email.getText().toString());
                mail.putExtra(Intent.EXTRA_SUBJECT, "");
                if(mail.resolveActivity(getPackageManager()) != null && !email.getText().toString().equals("Not Available"))
                    startActivity(mail);
            }
        });

    }

    private void populateProfile() {
        Intent intent = getIntent();
        senPos = intent.getIntExtra("senatorPosition", 0);

        Senators senator = MainActivity.senatorData.get(senPos);

        Picasso.get().load(Uri.parse(senator.getImage())).into(senPicture);
        senName.setText(senator.getName());
        getSupportActionBar().setTitle(senName.getText().toString());
        senDistrict.setText(senator.getDistrict());
        senParty.setText(senator.getParty());

        if(!senator.getPhone().equals("")) {
            mobileNumber.setText(senator.getPhone());
        }else{
            mobileNumber.setText("Not Available");
        }

        if(!senator.getEmail().equals("")) {
            email.setText(senator.getEmail());
        }else{
            email.setText("Not Available");
        }

    }

    private void init() {
        senPicture = findViewById(R.id.senPicture);
        senName = findViewById(R.id.senName);
        senDistrict = findViewById(R.id.senDistrict);
        senParty = findViewById(R.id.senParty);
        mobileNumber = findViewById(R.id.mobileNumber);
        email = findViewById(R.id.email);
        dialer = findViewById(R.id.callIcon);
        message = findViewById(R.id.messageIcon);
        sendMail = findViewById(R.id.emailIcon);
    }
}
