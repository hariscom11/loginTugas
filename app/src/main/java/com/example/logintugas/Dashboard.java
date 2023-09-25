package com.example.logintugas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.jetbrains.annotations.Nullable;

public class Dashboard extends Activity {
    private View igbutton, galerybutton, contactbutton, callbutton;
    private static final int REQUEST_PERMISSION_GALLERY = 1;
    private static final int REQUEST_PERMISSION_CONTACT = 2;
    private static final int REQUEST_PERMISSION_CALL = 101;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        igbutton = findViewById(R.id.btnig);
        galerybutton = findViewById(R.id.btngaleri);
        contactbutton = findViewById(R.id.btncontact);
        callbutton = findViewById(R.id.btncall);

        igbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/mharshd_/"));
                startActivity(intent);
            }
        });
        galerybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        contactbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContacts();
            }
        });
        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callperson();
            }
        });
    }
    private void openGallery() {
        // Memeriksa izin untuk mengakses galeri
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_GALLERY);
        } else {
            // Izin telah diberikan, buka galeri
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_PERMISSION_GALLERY);
        }
    }

    private void openContacts() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_PERMISSION_CONTACT);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, REQUEST_PERMISSION_CONTACT);
        }
    }
    private void callperson() {
        if (ContextCompat.checkSelfPermission(Dashboard.this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Jika belum mendapatkan izin, minta izin kepada pengguna
            ActivityCompat.requestPermissions(Dashboard.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_PERMISSION_CALL);
        } else {
            // Jika sudah mendapatkan izin, lakukan panggilan
            makePhoneCall();
        }
    }

    private void makePhoneCall() {
        String phoneNumber = "085607105495"; // Ganti dengan nomor telepon yang ingin dihubungi
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        try {
            startActivity(callIntent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Izin galeri ditolak.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_PERMISSION_CONTACT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openContacts();
            } else {
                Toast.makeText(this, "Izin kontak ditolak.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_PERMISSION_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Izin panggilan ditolak.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PERMISSION_GALLERY && data != null) {
                Uri selectedImageUri = data.getData();
//                 imageView.setImageURI(selectedImageUri);
            } else if (requestCode == REQUEST_PERMISSION_CONTACT && data != null) {
                Uri contactUri = data.getData();
                Intent intent = new Intent(Intent.ACTION_VIEW, contactUri);
                startActivity(intent);
            }
        }
    }
}