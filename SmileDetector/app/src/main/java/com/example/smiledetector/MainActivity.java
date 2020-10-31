package com.example.smiledetector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Facing;
import com.otaliastudios.cameraview.Frame;
import com.otaliastudios.cameraview.FrameProcessor;
import com.theartofdev.edmodo.cropper.CropImage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements FrameProcessor {

    private Facing cameraFacing = Facing.FRONT;
    private ImageView imageView;
    private CameraView faceDetectionCameraView;
    private RecyclerView bottomSheetRecyclerView;
    private BottomSheetBehavior bottomSheetBehavior;
    private ArrayList<FaceDetectionModel> faceDetectionModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        faceDetectionModels = new ArrayList<>();
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        imageView = findViewById(R.id.face_detection_image_view);
        faceDetectionCameraView = findViewById(R.id.face_detection_camera_view);

        Button toggle = findViewById(R.id.face_detection_camera_toggle_button);
        FrameLayout bottomSheetButton = findViewById(R.id.bottom_sheet_button);
        bottomSheetRecyclerView = findViewById(R.id.bottom_sheet_recycler_view);

        //set up our camera view from our library
        faceDetectionCameraView.setFacing(cameraFacing);
        faceDetectionCameraView.setLifecycleOwner(MainActivity.this);
        faceDetectionCameraView.addFrameProcessor(MainActivity.this);

        bottomSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().start(MainActivity.this);
            }
        });
        bottomSheetRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        bottomSheetRecyclerView.setAdapter(new FaceDetectionAdapter(faceDetectionModels,MainActivity.this));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                assert result != null;
                Uri imageUri = result.getUri();
                try {
                    analyseImage(MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void analyseImage(final Bitmap bitmap) {
        if(bitmap == null){
            Toast.makeText(this,"There was an error", Toast.LENGTH_LONG).show();
            return;
        }
        imageView.setImageBitmap(null);
        faceDetectionModels.clear();
        Objects.requireNonNull(bottomSheetRecyclerView.getAdapter()).notifyDataSetChanged();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        showProgress();
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionFaceDetectorOptions options =
                new FirebaseVisionFaceDetectorOptions.Builder()
                .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                .build();

        FirebaseVisionFaceDetector faceDetector = FirebaseVision.getInstance()
                .getVisionFaceDetector(options);

        faceDetector.detectInImage(firebaseVisionImage)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                        Bitmap mutableImage = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                        detectFaces(firebaseVisionFaces,mutableImage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void detectFaces(List<FirebaseVisionFace> firebaseVisionFaces, Bitmap bitmap) {
        if (firebaseVisionFaces == null || bitmap == null) {
            Toast.makeText(this, "There was an error", Toast.LENGTH_SHORT).show();
            return;
        }
        Canvas canvas = new Canvas(bitmap);
        Paint facePaint = new Paint();
        facePaint.setColor(Color.GREEN);
        facePaint.setStyle(Paint.Style.STROKE);
        facePaint.setStrokeWidth(5f);

        Paint faceTextPaint = new Paint();
        faceTextPaint.setColor(Color.BLUE);
        faceTextPaint.setTextSize(30f);
        faceTextPaint.setTypeface(Typeface.SANS_SERIF);

        Paint landmarkPaint = new Paint();
        landmarkPaint.setColor(Color.RED);
        landmarkPaint.setStyle(Paint.Style.FILL);
        landmarkPaint.setStrokeWidth(8f);

        for (int i = 0; i < firebaseVisionFaces.size(); i++) {
            canvas.drawRect(firebaseVisionFaces.get(i).getBoundingBox(), facePaint);
            canvas.drawText("Face " + i, (firebaseVisionFaces.get(i).getBoundingBox().centerX()
                            - (firebaseVisionFaces.get(i).getBoundingBox().width() >> 2) + 8f), // added >> to avoid errors when dividing with "/"
                    (firebaseVisionFaces.get(i).getBoundingBox().centerY()
                            + firebaseVisionFaces.get(i).getBoundingBox().height() >> 2) - 8F,
                    faceTextPaint);

            FirebaseVisionFace face = firebaseVisionFaces.get(i);
            if (face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EYE) != null) {
                FirebaseVisionFaceLandmark leftEye = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EYE);
                canvas.drawCircle(Objects.requireNonNull(leftEye).getPosition().getX(),
                        leftEye.getPosition().getY(),
                        8f,
                        landmarkPaint
                );
            }
            if (face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EYE) != null) {
                FirebaseVisionFaceLandmark rightEye = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EYE);
                canvas.drawCircle(Objects.requireNonNull(rightEye).getPosition().getX(),
                        rightEye.getPosition().getY(),
                        8f,
                        landmarkPaint
                );
            }

            if (face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE) != null) {
                FirebaseVisionFaceLandmark nose = face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE);
                canvas.drawCircle(Objects.requireNonNull(nose).getPosition().getX(),
                        nose.getPosition().getY(),
                        8f,
                        landmarkPaint
                );
            }
            if (face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR) != null) {
                FirebaseVisionFaceLandmark leftEar = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR);
                canvas.drawCircle(Objects.requireNonNull(leftEar).getPosition().getX(),
                        leftEar.getPosition().getY(),
                        8f,
                        landmarkPaint
                );
            }

            if (face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR) != null) {
                FirebaseVisionFaceLandmark rightEar = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR);
                canvas.drawCircle(Objects.requireNonNull(rightEar).getPosition().getX(),
                        rightEar.getPosition().getY(),
                        8f,
                        landmarkPaint
                );
            }
            if (face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_LEFT) != null
                    && face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_BOTTOM) != null
                    && face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_RIGHT) != null) {
                FirebaseVisionFaceLandmark leftMouth = face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_LEFT);
                FirebaseVisionFaceLandmark bottomMouth = face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_BOTTOM);
                FirebaseVisionFaceLandmark rightMouth = face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_RIGHT);
                canvas.drawLine(leftMouth.getPosition().getX(),
                        leftMouth.getPosition().getY(),
                        bottomMouth.getPosition().getX(),
                        bottomMouth.getPosition().getY(),
                        landmarkPaint);
                canvas.drawLine(bottomMouth.getPosition().getX(),
                        bottomMouth.getPosition().getY(),
                        rightMouth.getPosition().getX(),
                        rightMouth.getPosition().getY(), landmarkPaint);
            }

            faceDetectionModels.add(new FaceDetectionModel(i, "Smiling Probability " + face.getSmilingProbability()));
            faceDetectionModels.add(new FaceDetectionModel(i, "Left Eye Open Probability " + face.getLeftEyeOpenProbability()));
            faceDetectionModels.add(new FaceDetectionModel(i, "Right Eye Open Probability " + face.getRightEyeOpenProbability()));

        }
    }

    private void showProgress() {
        findViewById(R.id.bottom_sheet_button_image).setVisibility(View.GONE);
        findViewById(R.id.bottom_sheet_button_progressbar).setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        findViewById(R.id.bottom_sheet_button_image).setVisibility(View.VISIBLE);
        findViewById(R.id.bottom_sheet_button_progressbar).setVisibility(View.GONE);
    }

    @Override
    public void process(@NonNull Frame frame) {

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
