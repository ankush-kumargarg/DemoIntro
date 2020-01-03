package com.interactive.demointro.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.interactive.demointro.R;
import com.interactive.demointro.config.PermissionUtils;
import com.interactive.demointro.network.ApiManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.interactive.demointro.config.AppConstant.RESULT_EMP_IMAGE;

public class UploadImage extends AppCompatActivity {
    private static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private String[] permissin = {WRITE_EXTERNAL_STORAGE};

    private Button btSubmit;
    private ImageView ivImage;
    private Uri selectedImage;
    private Uri imageUri;
    private PermissionUtils permissionUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        btSubmit = findViewById(R.id.btn_submit);
        ivImage = findViewById(R.id.cv_image);

        permissionUtils = new PermissionUtils();

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionUtils.hasPermission(UploadImage.this, WRITE_EXTERNAL_STORAGE)) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, RESULT_EMP_IMAGE);
                } else {
                    if (!PermissionUtils.shouldShowRational(UploadImage.this, WRITE_EXTERNAL_STORAGE)) {
                        PermissionUtils.requestPermissions(UploadImage.this, permissin, 2);
                    } else {
                        PermissionUtils.requestPermissions(UploadImage.this, permissin, 2);
                    }
                }
            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestBody driverId = RequestBody.create(MediaType.parse("text/plain"), "123");
                RequestBody panName = RequestBody.create(MediaType.parse("text/plain"), "ankush");
                RequestBody panNumber = RequestBody.create(MediaType.parse("text/plain"), "PQBC2134");

//                File file = new File(String.valueOf(selectedImage));
//                RequestBody fileBody = RequestBody.create(MediaType.parse("*/*"), file);
//                MultipartBody.Part part = MultipartBody.Part.createFormData("pan_image", "pan_image", fileBody);

                InputStream iStream = null;
                byte[] bytes = null;
                try {
                    iStream = getContentResolver().openInputStream(selectedImage);
                    if (iStream != null) {
                        bytes = CommonUtil.getBytes(iStream);
                    } else {
                        return;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (bytes == null) {
                    return;
                }
                RequestBody fileBody = RequestBody.create(MediaType.parse("*/*"), bytes);
                MultipartBody.Part part = MultipartBody.Part.createFormData("pan_image", "pan_image", fileBody);

                ApiManager.getInstance().getApiService().uploadPan(driverId, panName, panNumber, part).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.body() != null) {
                            Log.v("response", String.valueOf(response.body()));
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_EMP_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            ivImage.setImageURI(selectedImage);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0) {
            boolean isPermissionGranted = (grantResults[0] == PackageManager.PERMISSION_GRANTED);
            if (isPermissionGranted) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photo = new File(Environment.getExternalStorageDirectory(), "pic.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                imageUri = Uri.fromFile(photo);
                startActivityForResult(intent, 1);
            } else {
                boolean showRatinal = PermissionUtils.shouldShowRational(UploadImage.this, WRITE_EXTERNAL_STORAGE);
                if (!showRatinal) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(UploadImage.this);
                    dialog.setTitle("This permission is used for this purpose");
                    dialog.setPositiveButton("Go to setting", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                } else {
                    PermissionUtils.requestPermissions(UploadImage.this, permissions, 100);
                }
            }
        }
    }

}