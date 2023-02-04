package com.example.shop;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.checkSelfPermission;
import static androidx.core.content.ContextCompat.getDrawable;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class UploadItemFragment extends Fragment implements View.OnClickListener {


    private static final String ARG_PARAM1 = "param1";private static final String ARG_PARAM2 = "param2";private String mParam1;private String mParam2;

     static final int CAMERA_REQUEST = 1888;
     static final int MY_CAMERA_PERMISSION_CODE = 100;
    ImageButton ib_upload,ib_camera,ib_gallery;
    EditText ed_name, ed_category, ed_price, ed_description;
    ImageView iv_img;
    Bitmap bitM_upload =null;
    VideoView videoView;

    public UploadItemFragment() {
    }


    public static UploadItemFragment newInstance(String param1, String param2) {
        UploadItemFragment fragment = new UploadItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_upload_item, container, false);

        videoView = view.findViewById(R.id.videoView_uploadF);
        Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.upload_video);
        videoView.setVideoURI(uri);
        videoView.start();

        ed_name = view.findViewById(R.id.ed_name);
        ed_category = view.findViewById(R.id.ed_category);
        ed_price = view.findViewById(R.id.ed_price);
        ed_description = view.findViewById(R.id.ed_description);
        ib_upload = view.findViewById(R.id.ib_upload);
        ib_camera = view.findViewById(R.id.ib_camera);
        ib_gallery = view.findViewById(R.id.ib_gallery);
        iv_img= view.findViewById(R.id.iv_img);

        ib_upload.setOnClickListener(this);
        ib_camera.setOnClickListener(this);
        ib_gallery.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view==ib_camera){
            if (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            }
            else
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }


        }
        if (view== ib_gallery){
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 2);

        }
        if (view == ib_upload){
            String str_name = ed_name.getText().toString();
            String str_category = ed_category.getText().toString();
            String str_price = ed_price.getText().toString();
            String str_description = ed_description.getText().toString();


            if (valid_info(str_name,str_category,str_price)){ // אפשר להעלות את המוצר(הכל תקין)

                // Code for showing progressDialog while uploading
                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                // Defining the child of storageReference
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReference();
                String uuid = UUID.randomUUID().toString();
                StorageReference ref = storageReference.child("productsImg/" + uuid);

                // adding listeners on upload
                // or failure of image
                iv_img.setDrawingCacheEnabled(true);
                iv_img.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) iv_img.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                UploadTask uploadTask = (UploadTask) ref.putBytes(data);
                uploadTask.addOnSuccessListener(
                        new OnSuccessListener<UploadTask.TaskSnapshot>() {

                            @Override
                            public void onSuccess(
                                    UploadTask.TaskSnapshot taskSnapshot)
                            {
                                Toast.makeText(getContext(), "Product Uploaded!!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                ed_name.setText("");
                                ed_category.setText("");
                                ed_price.setText("");
                                ed_description.setText("");
                                iv_img.setImageDrawable(null);

                            }
                        })
                        .addOnProgressListener(
                                new OnProgressListener<UploadTask.TaskSnapshot>() {

                                    @Override
                                    public void onProgress(
                                            UploadTask.TaskSnapshot taskSnapshot)
                                    {
                                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                        progressDialog.setMessage("Uploaded " + (int)progress + "%");
                                    }
                                });
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            HashMap<String, String> hm =new HashMap<String,String>();
                            hm.put(uuid,downloadUri.toString());
                            Partner p =(Partner)Functions.generalConnectedPerson;
                            Product product_created=createProduct(str_description,str_name,str_category,str_price,uuid,downloadUri.toString());
                            db.collection("products").document(uuid).set(product_created);
                            ArrayList<Product> arr_products= p.getItems();
                            arr_products.add(product_created);
                            p.setItems(arr_products);
                            db.collection("users").document(p.getEmail()).set(p);


                        }
                    }
                });

                    }
                }
            }




    private boolean valid_info(String str_name,String str_category,String str_price) {
        boolean valid_info = true;
        if (iv_img.getDrawable() == null) {
            Toast.makeText(getContext(), "choose an image", Toast.LENGTH_SHORT).show();
            valid_info = false;
        }
        if (str_name.equals("")) {
            ed_name.setError("enter a name");
            valid_info = false;
        }
        if (str_category.equals("")) {
            ed_category.setError("enter a category");
            valid_info = false;
        }
        if (str_price.length()>7) {
            ed_price.setError("price must be less than 1M");
            valid_info = false;
        }
        if (str_price.equals("")) {
            ed_price.setError("enter a price");
            valid_info = false;
        }
        return valid_info;
    }

    private Product createProduct(String str_description,String str_name,String str_category,String str_price,String productId, String imgUrl){
        if (str_description.equals("")){
            return new Product(str_name,str_category,imgUrl,Integer.parseInt(str_price),productId);
        }
        return new Product(str_name,str_category,imgUrl,Integer.parseInt(str_price),productId, str_description);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_SHORT).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            bitM_upload = photo;
            iv_img.setImageBitmap(photo);
        }
        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            bitM_upload = bitmap;
            iv_img.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        videoView.start();
    }
}