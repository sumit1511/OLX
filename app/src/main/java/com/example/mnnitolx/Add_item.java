package com.example.mnnitolx;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import id.zelory.compressor.Compressor;

public class Add_item extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 1;
    ImageView chose_imge;
    EditText et_item_name,et_item_price,et_item_description;
    LinearLayout bt_item_chose,bt_item_upload;
    private Uri imgUrl;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    byte[] final_image;
    private int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        chose_imge=(ImageView)findViewById(R.id.chose_img);
        et_item_name=(EditText)findViewById(R.id.item_name);
        et_item_price=(EditText)findViewById(R.id.item_price);
        et_item_description=(EditText)findViewById(R.id.item_discraption);
        bt_item_chose=(LinearLayout)findViewById(R.id.Btn_chose_item);
        bt_item_upload=(LinearLayout)findViewById(R.id.Btn_upload_item);
        mStorageRef = FirebaseStorage.getInstance().getReference("item");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ITEM");

        bt_item_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    uploadImage();
                    finish();
                    startActivity(new Intent(getApplicationContext(), Home_Activity.class));
                }

        });
        if(chose_imge!=null)
        {
            bt_item_chose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showFileChoose();
                }
            });
        }


    }
    private void showFileChoose() {
       /* Intent intent = new Intent();
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_IMAGE);*/
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUrl = data.getData();
            /*---------------------image size ------------------*////storage/emulated/0/DCIM/Camera/IMG_20181123_161208.jpg
            File actualImage = new File(getRealPathFromURI(imgUrl));
            try {
                Bitmap compressedImage = new Compressor(this)
                        .setMaxWidth(250)
                        .setMaxHeight(250)
                        .setQuality(50)
                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
                        .compressToBitmap(actualImage);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                compressedImage.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                final_image = baos.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
            }
            /*---------------------end-------------------------*/
            Picasso.get().load(imgUrl).into(chose_imge);

        }

    }
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void uploadImage() {
        if (imgUrl != null)
        {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(imgUrl));
            /*---------------image size---------*/
            UploadTask uploadTask = fileReference.putBytes(final_image);
            /*------------------end---------------*/
            // mUploadTask = fileReference.putFile(imgUrl)

            mUploadTask =  uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // uploadProgress.setProgress(0);
                        }
                    }, 500);
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uploadID = mDatabaseRef.push().getKey();
                            Item upload = new Item(uploadID,uri.toString(),et_item_name.getText().toString().trim(),et_item_price.getText().toString().trim()+"₹",
                                    et_item_description.getText().toString().trim(),MainActivity.myemail,
                                    "false","no");

                            mDatabaseRef.child(uploadID).setValue(upload);
                            Toast.makeText(Add_item.this, "Uploaded succesfully ! Wait 15 Minutes for verification of your Ads", Toast.LENGTH_LONG).show();
                        }
                    });


                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Add_item.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }
    /*----------------finding  path from gallary-----------*/
    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);

    }
    /*-----------------right path end------------------------*/
    /*-------------------back press------------------------*/
}
