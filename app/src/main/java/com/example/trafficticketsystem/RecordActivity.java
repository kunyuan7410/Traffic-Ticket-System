package com.example.trafficticketsystem;

import android.app.Activity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.Continuation;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import android.util.Log;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;

import org.w3c.dom.Text;


public class RecordActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    static final int pui = 100;
    //    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
    static final int REQUEST_IMAGE_CAPTURE = 200;
    private int upload_count = 0;

    private StorageReference ProductImagesRef;
    private Button UploadBtn, detectTextBtn, locationBtn, checkBtn;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private ResultReceiver resultReceiver;
    private LocationManager lm;
    AutoCompleteTextView autoCompleteTextView;
    private ImageView imageView;
    private TextView txtSnumber, type_Summon;
    private TextInputLayout textView, tLocation, tDate, tTime, tImage;
    private Bitmap imageBitmap;
    private ImageButton captureImageBtn;
    private ProgressBar progressBar;
    private Uri pickedImage;
    private String downloadImageUrl;
    private String currentPhotoPath;

    String[] option = {"Speeding", "Parking", "Run a red light", "Overloading of passengers"};
    ArrayAdapter<String> adapter;

    SharedPreferences SP;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    FirebaseDatabase rootNode;
    DatabaseReference reference,reference2,reference3;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        resultReceiver = new AddressResultReceiver(new Handler());

        drawerLayout = findViewById(R.id.drawer_layout);

        checkBtn = findViewById(R.id.buttonCheck);
        UploadBtn = findViewById(R.id.buttonUpload);
        captureImageBtn = findViewById(R.id.c_image);
        detectTextBtn = findViewById(R.id.d_image);
//        Toolbar toolbar4 = findViewById(R.id.toolbar4);
//        setSupportActionBar(toolbar4);
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Summon Images");
        tImage = findViewById(R.id.timage);
        imageView = findViewById(R.id.p_image);
        textView = findViewById(R.id.p_number);
        tTime = findViewById(R.id.tTime);
        tDate = findViewById(R.id.tDate);
        tLocation = findViewById(R.id.tLocation);
        locationBtn = findViewById(R.id.btnLocation);
        txtSnumber = findViewById(R.id.txtSNumber);
        progressBar = findViewById(R.id.Progress);

        SP = getSharedPreferences("GetUserContact", Context.MODE_PRIVATE);


        autoCompleteTextView = findViewById(R.id.dpSummon);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, option);
        autoCompleteTextView.setText("Please Select One");


        autoCompleteTextView.setAdapter(adapter);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("hh:mm:ss a");

        String date = simpleDateFormat.format(calendar.getTime());
        String time = simpleDateFormat1.format(calendar.getTime());
        tDate.getEditText().setText(date);
        tTime.getEditText().setText(time);

        type_Summon = findViewById(R.id.type_summon);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                type_Summon.setText(adapter.getItem(i));

            }
        });


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveSummonInfoToDatabase();
//                SaveSummonInfoToDatabase2();
//                SaveSummonInfoToDatabase3();


            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lm = (LocationManager) getApplicationContext().getSystemService(getApplicationContext().LOCATION_SERVICE);
                boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (ok) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(RecordActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
                    } else {
                        getCurrentLocation();
                    }
                } else {
                    Toast.makeText(RecordActivity.this, "Please open the GPS", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
            }
        });

        captureImageBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);
                builder.setTitle("Add Image");
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (items[i].equals("Camera")) {
                            dispatchTakePictureIntent();
                            textView.getEditText().setText("");


                        } else if (items[i].equals("Gallery")) {
                            pickImageGal();


                        } else if (items[i].equals("Cancel")) {
                            dialogInterface.dismiss();
                        }

                    }
                });

                builder.show();

            }

        });
        detectTextBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (imageView.getDrawable() != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    detectTextFromImage();
                } else {
                    Toast.makeText(RecordActivity.this, "Please add a image", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }



    private void pickImageGal() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, pui);
    }
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hhmmss");
    String random_filename = simpleDateFormat2.format(calendar.getTime());
    final String random_filename2 = UUID.randomUUID().toString();

    private void dispatchTakePictureIntent() {
        String fileName = random_filename + random_filename2;
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        try {
        File imageFile = null;
        try {
            imageFile = File.createTempFile(fileName, ".jpg", storageDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentPhotoPath = imageFile.getAbsolutePath();
        pickedImage = FileProvider.getUriForFile(RecordActivity.this, "com.example.trafficticketsystem.fileprovider", imageFile);
        try {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, pickedImage);
//            if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(RecordActivity.this,""+e,Toast.LENGTH_LONG).show();
        }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(RecordActivity.this, "Failed to open", Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            pickedImage = data.getData();
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
//            String path = MediaStore.Images.Media.getContentUri(,imageBitmap);
//            =
//            Bundle extras = data.getExtras();
//            imageBitmap = (Bitmap) extras.get("data");

            //
            imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
            imageView.getLayoutParams().height = 500;
            imageView.setImageBitmap(imageBitmap);
            imageView.setVisibility(View.VISIBLE);
            uploadPicture();
//            Toast.makeText(RecordActivity.this,""+pickedImage,Toast.LENGTH_SHORT).show();


        } else if (requestCode == pui && resultCode == RESULT_OK) {
            pickedImage = data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), pickedImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.getLayoutParams().height = 500;
            imageView.setImageURI(pickedImage);
            imageView.setVisibility(View.VISIBLE);
            uploadPicture();
//            Toast.makeText(RecordActivity.this,""+pickedImage,Toast.LENGTH_SHORT).show();

        }
    }


    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        progressBar.setVisibility(View.VISIBLE);
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(RecordActivity.this).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(RecordActivity.this).removeLocationUpdates(this);
                if (locationResult != null && locationResult.getLocations().size() > 0) {
                    int latestLocationIndex = locationResult.getLocations().size() - 1;
                    double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                    Location location = new Location("providerNA");
                    location.setLatitude(latitude);
                    location.setLongitude(longitude);
                    fetchAddressFromLatLong(location);

                } else {
                    progressBar.setVisibility(View.GONE);

                }
            }
        }, Looper.getMainLooper());
    }


    private class AddressResultReceiver extends ResultReceiver {
        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == Constants.SUCCESS_RESULT) {
                tLocation.getEditText().setText(resultData.getString(Constants.RESULT_DATA_KEY));
            } else {
                Toast.makeText(RecordActivity.this, resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        }
    }

    private void fetchAddressFromLatLong(Location location) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        startService(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void ClickMenu(View view) {
        PoliceActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        PoliceActivity.closeDrawer(drawerLayout);
    }

//    public void ClickHome(View view) {
//        PoliceActivity.redirectActivity(this, PoliceActivity.class);
//    }

    public void ClickRecord(View view) {
        PoliceActivity.redirectActivity(this, RecordActivity.class);
    }

    public void ClickHistory(View view) {
        PoliceActivity.redirectActivity(this, PoliceHistoryActivity.class);
    }

    public void Logout(View view) {
        Intent intent = new Intent(RecordActivity.this, MainActivity.class);
        startActivity(intent);
        preferences.clearData(this);
        finish();
    }

    protected void onPause() {
        super.onPause();
        PoliceActivity.closeDrawer(drawerLayout);
    }

    private void detectTextFromImage() {
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextDetector firebaseVisionTextDetector = FirebaseVision.getInstance().getVisionTextDetector();
        firebaseVisionTextDetector.detectInImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                displayTextFromImage(firebaseVisionText);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RecordActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Error: ", e.getMessage());
            }
        });
    }

    private void displayTextFromImage(FirebaseVisionText firebaseVisionText) {
        List<FirebaseVisionText.Block> blockList = firebaseVisionText.getBlocks();
        if (blockList.size() == 0) {
            Toast.makeText(this, "No Text Found in image.", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        } else {
            for (FirebaseVisionText.Block block : firebaseVisionText.getBlocks()) {

//                for(FirebaseVisionText.Line line : block.getLines()){
//                    for(FirebaseVisionText.Element element : line.getElements()){
//
                String text = block.getText();
//                        String elementText = element.getText();
                        String PlateNumber = "^[a-zA-Z]{1,3}\\s[0-9]{1,4}$";
                        String PlateNumber2 = "^[a-zA-Z]{1,2}\\s[0-9]{1,4}\\s[a-zA-Z]$";
                        String PlateNumber3 = "^[a-zA-Z]{1,2}[0-9]{1,4}\\s[a-zA-Z]$";
                        String PlateNumber4 = "^[a-zA-Z]{1,2}\\s[0-9]{1,4}[a-zA-Z]$";
                         String PlateNumber5 = "^[a-zA-Z]{1,2}[0-9]{1,4}[a-zA-Z]$";
//                        String LPR = "";
//                        if(!elementText.matches(PlateNumber)){
//                            Toast.makeText(this, "No License Plate Number Found in image."+LPR, Toast.LENGTH_SHORT).show();
//                        }else {
                if(text.matches(PlateNumber)||text.matches(PlateNumber2)||text.matches(PlateNumber3)||text.matches(PlateNumber4)||text.matches(PlateNumber5)) {
                    textView.getEditText().setText(text.toUpperCase().replaceAll("\\s+", ""));
                }
//                else{
//                    Toast.makeText(this, "No License Plate Number Found in image."+text, Toast.LENGTH_SHORT).show();
//                }
//                        }
//                        if(!LPR.isEmpty()){
//                            textView.getEditText().setText(LPR.toUpperCase().replaceAll("\\s+", ""));
//                        }else {
//                            Toast.makeText(this, "No License Plate Number Found in image."+LPR, Toast.LENGTH_SHORT).show();
//                        }



//                        textView.getEditText().setText(elementText.toUpperCase().replaceAll("\\s+", ""));
                progressBar.setVisibility(View.GONE);

//                    }
//                }
//                String text = block.getText();
//


            }
        }
    }

    private void uploadPicture() {


        final String randomKey = UUID.randomUUID().toString();
        final StorageReference filePath = ProductImagesRef.child(pickedImage.getLastPathSegment() + randomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(pickedImage);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(RecordActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();


                        }
                    }
                });
            }
        });


    }
//    private void SaveSummonInfoToDatabase2() {
//        rootNode = FirebaseDatabase.getInstance();
//        reference2 = rootNode.getReference("police");
//        String Pno = textView.getEditText().getText().toString();
//        String Stype = type_Summon.getText().toString();
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hhmmss");
//        String S_number = simpleDateFormat2.format(calendar.getTime());
//        String plateNo = textView.getEditText().getText().toString();
//        String typeSummon = type_Summon.getText().toString();
//        String cLocation = tLocation.getEditText().getText().toString();
//        String cTime = tTime.getEditText().getText().toString();
//        String cDate = tDate.getEditText().getText().toString();
//        String Sno = plateNo + S_number;
//        String summonNo = Sno;
//        String imgURI = downloadImageUrl;
//        String SummonAmount = "";
//        if (Stype.equals("Speeding")) {
//            SummonAmount = "300";
//        } else if (Stype.equals("Parking")) {
//            SummonAmount = "150";
//        } else if (Stype.equals("Run a red light")) {
//            SummonAmount = "300";
//        } else if (Stype.equals("Overloading of passengers")) {
//            SummonAmount = "150";
//        }
//        preferences preferences = new preferences(RecordActivity.this);
//        HashMap<String, String> policeDetails = preferences.getUsersDetailFromSession();
//        String policeUsername = policeDetails.get(preferences.KEY_USERNAME);
//        String policeName = policeDetails.get(preferences.KEY_NAME);
//Toast.makeText(RecordActivity.this,""+policeUsername,Toast.LENGTH_LONG).show();
//        UploadSummon_Police police_record = new UploadSummon_Police(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo);
//        reference2.child(policeUsername).child("history").child(Sno).setValue(police_record);
//
//
//
//
//    }
//    private void SaveSummonInfoToDatabase3() {
//        rootNode = FirebaseDatabase.getInstance();
//        reference3 = rootNode.getReference("admin");
//        String Pno = textView.getEditText().getText().toString();
//        String Stype = type_Summon.getText().toString();
//
////        UploadSummon_Admin admin_record = new UploadSummon_Admin(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo, paymentStatus,policeName);
//    }

    private void SaveSummonInfoToDatabase() {

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");
        reference2 = rootNode.getReference("police");
        reference3 = rootNode.getReference("admin");
        String Pno = textView.getEditText().getText().toString();
        String Stype = type_Summon.getText().toString();
        reference.orderByChild("plateNo").equalTo(Pno).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    Toast.makeText(RecordActivity.this, "License plate number exists", Toast.LENGTH_SHORT).show();

                    String plateNo = textView.getEditText().getText().toString();
                    String snapshot_phoneNo = dataSnapshot.child(plateNo).child("phoneNo").getValue(String.class);
                    String snapshot_name = dataSnapshot.child(plateNo).child("name").getValue(String.class);

                    if (dataSnapshot.child(plateNo).hasChild("phoneNo")) {
                        if (downloadImageUrl != null) {
                            checkBtn.setVisibility(View.GONE);
                            UploadBtn.setVisibility(View.VISIBLE);
                            UploadBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DialogPlus dialog = DialogPlus.newDialog(RecordActivity.this)
                                            .setContentHolder(new ViewHolder(R.layout.sendnotification))
                                            .setExpanded(true, 1500).create();

                                    View summon_view = dialog.getHolderView();
                                    Button btnSend = summon_view.findViewById(R.id.btnSend);
                                    TextView contactNo = summon_view.findViewById(R.id.phoneNo_txt);
                                    TextView message = summon_view.findViewById(R.id.message_txt);

                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hhmmss");
                                    String S_number = simpleDateFormat2.format(calendar.getTime());
                                    String plateNo = textView.getEditText().getText().toString();
                                    String typeSummon = type_Summon.getText().toString();
                                    String cLocation = tLocation.getEditText().getText().toString();
                                    String cTime = tTime.getEditText().getText().toString();
                                    String cDate = tDate.getEditText().getText().toString();
                                    String Sno = plateNo + S_number;
                                    String paymentStatus = "Unpaid ";
                                    String summonNo = Sno;



                                    contactNo.setText(snapshot_phoneNo);
                                    message.setText("Dear " + snapshot_name + ", The purpose of this message is to remind " +
                                            "you that you have received a summon. The license plate number of the illegal vehicle is " + plateNo +
                                            ". Kindly go to MySummon to check the details of your summon, Thank you.");


                                    btnSend.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String SummonAmount = "";
                                            if (Stype.equals("Speeding")) {
                                                SummonAmount = "300";
                                            } else if (Stype.equals("Parking")) {
                                                SummonAmount = "150";
                                            } else if (Stype.equals("Run a red light")) {
                                                SummonAmount = "300";
                                            } else if (Stype.equals("Overloading of passengers")) {
                                                SummonAmount = "150";
                                            }

                                            String phone = contactNo.getText().toString();
                                            String smsContent = message.getText().toString();
                                            preferences preferences = new preferences(RecordActivity.this);
                                            HashMap<String, String> policeDetails = preferences.getUsersDetailFromSession();
                                            String policeUsername = policeDetails.get(preferences.KEY_USERNAME);
                                            String policeName = policeDetails.get(preferences.KEY_NAME);





                                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                                if(checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                                                    try {
                                                        String imgURI = downloadImageUrl;

                                                        SmsManager smsManager = SmsManager.getDefault();
                                                        smsManager.sendTextMessage(phone, null, smsContent, null, null);

                                                        Toast.makeText(RecordActivity.this, "Send Successfully", Toast.LENGTH_SHORT).show();

                                                        uploadSummon summon_info = new uploadSummon(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo, paymentStatus);
                                                        reference.child(plateNo).child("Summons").child(Sno).setValue(summon_info);
                                                        UploadSummon_Police record = new UploadSummon_Police(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo);
                                                      UploadSummon_Admin record2 = new UploadSummon_Admin(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo, paymentStatus,policeName);

                                                        reference2.child(policeUsername).child("RecordHistory").child(Sno).setValue(record);
                                                        reference3.child("Summon").child(Sno).setValue(record2);



//
                                                        startActivity(new Intent(RecordActivity.this,RecordActivity.class));

                                                        dialog.dismiss();
                                                    }catch (Exception e){
                                                        e.printStackTrace();
                                                        Toast.makeText(RecordActivity.this, "Failed to send", Toast.LENGTH_SHORT).show();
                                                    }
                                                }else{
                                                    requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                                                }
                                            }



                                        }
                                    });


                                    dialog.show();
                                }
                            });
                        } else {
                            checkBtn.setVisibility(View.GONE);
                            UploadBtn.setVisibility(View.VISIBLE);
                            UploadBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DialogPlus dialog = DialogPlus.newDialog(RecordActivity.this)
                                            .setContentHolder(new ViewHolder(R.layout.sendnotification))
                                            .setExpanded(true, 1500).create();

                                    View summon_view = dialog.getHolderView();
                                    Button btnSend = summon_view.findViewById(R.id.btnSend);
                                    TextView contactNo = summon_view.findViewById(R.id.phoneNo_txt);
                                    TextView message = summon_view.findViewById(R.id.message_txt);

                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hhmmss");
                                    String S_number = simpleDateFormat2.format(calendar.getTime());
                                    String plateNo = textView.getEditText().getText().toString();
                                    String typeSummon = type_Summon.getText().toString();
                                    String cLocation = tLocation.getEditText().getText().toString();
                                    String cTime = tTime.getEditText().getText().toString();
                                    String cDate = tDate.getEditText().getText().toString();
                                    String Sno = plateNo + S_number;
                                    String imgURI = tImage.getEditText().getText().toString();
                                    String paymentStatus = "Unpaid ";
                                    String summonNo = Sno;


                                    contactNo.setText(snapshot_phoneNo);
                                    message.setText("Dear " + snapshot_name + ", The purpose of this message is to remind " +
                                            "you that you have received a summon. The license plate number of the illegal vehicle is" + plateNo +
                                            ". Kindly go to MySummon to check the details of your summon, Thank you.");


                                    btnSend.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String SummonAmount = "";
                                            if (Stype.equals("Speeding")) {
                                                SummonAmount = "300";
                                            } else if (Stype.equals("Parking")) {
                                                SummonAmount = "150";
                                            } else if (Stype.equals("Run a red light")) {
                                                SummonAmount = "300";
                                            } else if (Stype.equals("Overloading of passengers")) {
                                                SummonAmount = "150";
                                            }

                                            String phone = contactNo.getText().toString();
                                            String smsContent = message.getText().toString();


                                            preferences preferences = new preferences(RecordActivity.this);
                                            HashMap<String, String> policeDetails = preferences.getUsersDetailFromSession();
                                            String policeUsername = policeDetails.get(preferences.KEY_USERNAME);
                                            String policeName = policeDetails.get(preferences.KEY_NAME);





                                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                                if(checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                                                    try {
                                                        SmsManager smsManager = SmsManager.getDefault();
                                                        smsManager.sendTextMessage(phone, null, smsContent, null, null);
                                                        Toast.makeText(RecordActivity.this, "Send Successfully", Toast.LENGTH_SHORT).show();
                                                        uploadSummon summon_info = new uploadSummon(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo, paymentStatus);
                                                        reference.child(plateNo).child("Summons").child(Sno).setValue(summon_info);
                                                        UploadSummon_Police record = new UploadSummon_Police(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo);
                                                        UploadSummon_Admin record2 = new UploadSummon_Admin(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo, paymentStatus,policeName);

                                                        reference2.child(policeUsername).child("RecordHistory").child(Sno).setValue(record);
                                                        reference3.child("Summon").child(Sno).setValue(record2);
                                                        startActivity(new Intent(RecordActivity.this,RecordActivity.class));
                                                        dialog.dismiss();
                                                    }catch (Exception e){
                                                        e.printStackTrace();
                                                        Toast.makeText(RecordActivity.this, "Failed to send", Toast.LENGTH_SHORT).show();
                                                    }
                                                }else{
                                                    requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                                                }
                                            }



                                        }
                                    });


                                    dialog.show();
                                }
                            });
                        }
                    } else {
                        if (downloadImageUrl != null) {
                            checkBtn.setVisibility(View.GONE);
                            UploadBtn.setVisibility(View.VISIBLE);
                            UploadBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                                    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("hh:mm:ss a");
                                    String date = simpleDateFormat.format(calendar.getTime());
                                    String time = simpleDateFormat1.format(calendar.getTime());

                                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hhmmss");
                                    String S_number = simpleDateFormat2.format(calendar.getTime());
                                    String plateNo = textView.getEditText().getText().toString();
                                    String typeSummon = autoCompleteTextView.getText().toString();
                                    String cLocation = tLocation.getEditText().getText().toString();
                                    String cTime = tTime.getEditText().getText().toString();
                                    String cDate = tDate.getEditText().getText().toString();
                                    String Sno = plateNo + S_number;
                                    String SummonAmount = "";
                                    String imgURI = downloadImageUrl;
                                    String paymentStatus = "Unpaid";
                                    String summonNo = Sno;
                                    preferences preferences = new preferences(RecordActivity.this);
                                    HashMap<String, String> policeDetails = preferences.getUsersDetailFromSession();
                                    String policeUsername = policeDetails.get(preferences.KEY_USERNAME);
                                    String policeName = policeDetails.get(preferences.KEY_NAME);


                                    if (Stype.equals("Speeding")) {
                                        SummonAmount = "300";
                                    } else if (Stype.equals("Parking")) {
                                        SummonAmount = "150";
                                    } else if (Stype.equals("Run a red light")) {
                                        SummonAmount = "300";
                                    } else if (Stype.equals("Overloading of passengers")) {
                                        SummonAmount = "150";
                                    }


                                    uploadSummon summon_info = new uploadSummon(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo, paymentStatus);
                                    reference.child(plateNo).child("Summons").child(Sno).setValue(summon_info);
                                    UploadSummon_Police record = new UploadSummon_Police(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo);
                                    UploadSummon_Admin record2 = new UploadSummon_Admin(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo, paymentStatus,policeName);

                                    reference2.child(policeUsername).child("RecordHistory").child(Sno).setValue(record);
                                    reference3.child("Summon").child(Sno).setValue(record2);
                                    Toast.makeText(RecordActivity.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RecordActivity.this,RecordActivity.class));

                                }
                            });
                        } else {
                            checkBtn.setVisibility(View.GONE);
                            UploadBtn.setVisibility(View.VISIBLE);

                            UploadBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                                    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("hh:mm:ss a");
                                    String date = simpleDateFormat.format(calendar.getTime());
                                    String time = simpleDateFormat1.format(calendar.getTime());

                                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hhmmss");
                                    String S_number = simpleDateFormat2.format(calendar.getTime());
                                    String plateNo = textView.getEditText().getText().toString();
                                    String typeSummon = autoCompleteTextView.getText().toString();
                                    String cLocation = tLocation.getEditText().getText().toString();
                                    String cTime = tTime.getEditText().getText().toString();
                                    String cDate = tDate.getEditText().getText().toString();
                                    String Sno = plateNo + S_number;
                                    String SummonAmount = "";
                                    String imgURI = tImage.getEditText().getText().toString();
                                    String Stype = type_Summon.getText().toString();
                                    String paymentStatus = "Unpaid";
                                    String summonNo = Sno;
                                    preferences preferences = new preferences(RecordActivity.this);
                                    HashMap<String, String> policeDetails = preferences.getUsersDetailFromSession();
                                    String policeUsername = policeDetails.get(preferences.KEY_USERNAME);
                                    String policeName = policeDetails.get(preferences.KEY_NAME);

                                    if (Stype.equals("Speeding")) {
                                        SummonAmount = "300";
                                    } else if (Stype.equals("Parking")) {
                                        SummonAmount = "150";
                                    } else if (Stype.equals("Run a red light")) {
                                        SummonAmount = "300";
                                    } else if (Stype.equals("Overloading of passengers")) {
                                        SummonAmount = "150";
                                    }


                                    uploadSummon summon_info = new uploadSummon(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo, paymentStatus);
                                    reference.child(plateNo).child("Summons").child(Sno).setValue(summon_info);
                                    UploadSummon_Police record = new UploadSummon_Police(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo);
                                    UploadSummon_Admin record2 = new UploadSummon_Admin(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo, paymentStatus,policeName);

                                    reference2.child(policeUsername).child("RecordHistory").child(Sno).setValue(record);
                                    reference3.child("Summon").child(Sno).setValue(record2);
                                    Toast.makeText(RecordActivity.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RecordActivity.this,RecordActivity.class));

                                }
                            });

                        }
                    }
                    //Get User phone from plate No


//                    SharedPreferences.Editor editor = SP.edit();
//
//                    editor.putString("phoneNo",contactNo);
//                    editor.putString("plateNo",plateNo);
//                    editor.putString("typeSummon",typeSummon);
//                    editor.putString("Location",cLocation);
//                    editor.putString("date",cDate);
//                    editor.putString("time",cTime);
//                    editor.putString("amount",SummonAmount);
//                    editor.commit();


                } else {
                    if (downloadImageUrl != null) {
                        Toast.makeText(RecordActivity.this, "License plate number doesn't exists", Toast.LENGTH_SHORT).show();
                        checkBtn.setVisibility(View.GONE);
                        UploadBtn.setVisibility(View.VISIBLE);
                        UploadBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("hh:mm:ss a");
                                String date = simpleDateFormat.format(calendar.getTime());
                                String time = simpleDateFormat1.format(calendar.getTime());

                                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hhmmss");
                                String S_number = simpleDateFormat2.format(calendar.getTime());
                                String plateNo = textView.getEditText().getText().toString();
                                String typeSummon = autoCompleteTextView.getText().toString();
                                String cLocation = tLocation.getEditText().getText().toString();
                                String cTime = tTime.getEditText().getText().toString();
                                String cDate = tDate.getEditText().getText().toString();
                                String Sno = plateNo + S_number;
                                String SummonAmount = "";
                                String imgURI = downloadImageUrl;
                                String paymentStatus = "Unpaid";
                                String summonNo = Sno;
                                preferences preferences = new preferences(RecordActivity.this);
                                HashMap<String, String> policeDetails = preferences.getUsersDetailFromSession();
                                String policeUsername = policeDetails.get(preferences.KEY_USERNAME);
                                String policeName = policeDetails.get(preferences.KEY_NAME);


                                if (Stype.equals("Speeding")) {
                                    SummonAmount = "300";
                                } else if (Stype.equals("Parking")) {
                                    SummonAmount = "150";
                                } else if (Stype.equals("Run a red light")) {
                                    SummonAmount = "300";
                                } else if (Stype.equals("Overloading of passengers")) {
                                    SummonAmount = "150";
                                }


                                uploadSummon summon_info = new uploadSummon(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo, paymentStatus);
                                reference.child(plateNo).child("Summons").child(Sno).setValue(summon_info);
                                UploadSummon_Police record = new UploadSummon_Police(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo);
                                UploadSummon_Admin record2 = new UploadSummon_Admin(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo, paymentStatus,policeName);

                                reference2.child(policeUsername).child("RecordHistory").child(Sno).setValue(record);
                                reference3.child("Summon").child(Sno).setValue(record2);
                                Toast.makeText(RecordActivity.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RecordActivity.this,RecordActivity.class));

                            }
                        });
                    } else {
                        Toast.makeText(RecordActivity.this, "License plate number doesn't exists", Toast.LENGTH_SHORT).show();
                        checkBtn.setVisibility(View.GONE);
                        UploadBtn.setVisibility(View.VISIBLE);
                        UploadBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("hh:mm:ss a");
                                String date = simpleDateFormat.format(calendar.getTime());
                                String time = simpleDateFormat1.format(calendar.getTime());

                                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hhmmss");
                                String S_number = simpleDateFormat2.format(calendar.getTime());
                                String plateNo = textView.getEditText().getText().toString();
                                String typeSummon = autoCompleteTextView.getText().toString();
                                String cLocation = tLocation.getEditText().getText().toString();
                                String cTime = tTime.getEditText().getText().toString();
                                String cDate = tDate.getEditText().getText().toString();
                                String Sno = plateNo + S_number;
                                String SummonAmount = "";
                                String imgURI = tImage.getEditText().getText().toString();
                                String paymentStatus = "Unpaid";
                                String summonNo = Sno;
                                preferences preferences = new preferences(RecordActivity.this);
                                HashMap<String, String> policeDetails = preferences.getUsersDetailFromSession();
                                String policeUsername = policeDetails.get(preferences.KEY_USERNAME);
                                String policeName = policeDetails.get(preferences.KEY_NAME);


                                if (Stype.equals("Speeding")) {
                                    SummonAmount = "300";
                                } else if (Stype.equals("Parking")) {
                                    SummonAmount = "150";
                                } else if (Stype.equals("Run a red light")) {
                                    SummonAmount = "300";
                                } else if (Stype.equals("Overloading of passengers")) {
                                    SummonAmount = "150";
                                }

//
                                uploadSummon summon_info = new uploadSummon(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo, paymentStatus);
                                reference.child(plateNo).child("Summons").child(Sno).setValue(summon_info);
                                UploadSummon_Police record = new UploadSummon_Police(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo);
                                UploadSummon_Admin record2 = new UploadSummon_Admin(imgURI, plateNo, typeSummon, cLocation, cTime, cDate, SummonAmount,summonNo, paymentStatus,policeName);

                                reference2.child(policeUsername).child("RecordHistory").child(Sno).setValue(record);
                                reference3.child("Summon").child(Sno).setValue(record2);
                                Toast.makeText(RecordActivity.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                              startActivity(new Intent(RecordActivity.this,RecordActivity.class));
                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




}



