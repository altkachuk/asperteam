package com.cyplay.atproj.asperteam.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.MenuItem;
import android.widget.ImageView;

import com.cyplay.atproj.asperteam.R;
import atproj.cyplay.com.asperteamapi.domain.interactor.ProfileInteractor;
import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.model.User;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import atproj.cyplay.com.asperteamapi.picasso.CircleTransform;

import com.cyplay.atproj.asperteam.ui.RequestCode;
import com.cyplay.atproj.asperteam.ui.activity.base.BaseResourceActivity;
import atproj.cyplay.com.asperteamapi.util.ActivityHelper;
import com.cyplay.atproj.asperteam.utils.FileHelper;
import com.cyplay.atproj.asperteam.utils.ScalingUtilities;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by andre on 06-Apr-18.
 */

public class EditProfileActivity extends BaseResourceActivity {

    @Inject
    ProfileInteractor profileInteractor;

    @Inject
    Picasso picasso;

    @BindView(R.id.photoImage)
    ImageView photoImage;

    protected User _currentUser;
    protected User _updatedUser;

    private File _photoFile;

    protected void setUser(User user) {
        _currentUser = user;
        _updatedUser = new User();
        if (user.getImage() != null && user.getImage().length() > 0)
            picasso.load(user.getImage()).transform(new CircleTransform(getApplicationContext(), R.color.colorImageCircleStroke)).into(photoImage);

    }

    protected void openSelectPhotoPopup() {
        getPopup().initPopup(RequestCode.SELECT_IMAGE_REQUEST,
                getString(R.string.select_photo_title),
                getString(R.string.select_photo_description));
        getPopup().setPositive(getString(R.string.photo_button));
        getPopup().setNegative(getString(R.string.storage_button));
        getPopup().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionDone:
                ActivityHelper.hideKeyboard(this);
                if (_updated)
                    backToAndUpdate();
                else if (_updatedUser.isEmpty())
                    supportFinishAfterTransition();
                else
                    updateUser(_currentUser.getId(), _currentUser.getUsername(), _updatedUser);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void updateUser(String id, String username, User user) {
        showPreloader();
        profileInteractor.updateUser(id, username, user, new ResourceRequestCallback<User>() {
            @Override
            public void onSucess(User user) {
                hidePreloader();
                backToAndUpdate();
            }

            @Override
            public void onError(BaseException e) {
                hidePreloader();
            }
        });
    }

    @Override
    protected void onPopupPositiveClick(int requestCode) {
        switch (requestCode) {
            case RequestCode.SELECT_IMAGE_REQUEST:
                if (checkCameraPermissions()) {
                    openCameraIntent();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{
                                    android.Manifest.permission.CAMERA,
                                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },
                            RequestCode.CAMERA_REQUEST);
                }
                break;
        }
    }

    @Override
    protected void onPopupNegativeClick(int requestCode) {
        switch (requestCode) {
            case RequestCode.SELECT_IMAGE_REQUEST:
                if (checkStoragePermissions()) {
                    openStorageIntent();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{
                                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },
                            RequestCode.STORAGE_REQUEST);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == RequestCode.CAMERA_REQUEST) {
            compressImage(_photoFile, _photoFile);
            updatePhoto(_currentUser.getId(), _currentUser.getUsername(), _photoFile);
        } else if (requestCode == RequestCode.STORAGE_REQUEST) {
            String path = FileHelper.getActualPath(this.getApplicationContext(), data.getData());
            File selectedFile = new File(path);
            File outFile = createImageFile();
            compressImage(selectedFile, outFile);
            updatePhoto(_currentUser.getId(), _currentUser.getUsername(), outFile);
        }
    }

    private void compressImage(File inFile, File outFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(inFile.getAbsolutePath(), options);

        int newWidth = bitmap.getWidth() < bitmap.getHeight() ? 400 : bitmap.getWidth() / bitmap.getHeight() * 400;
        int newHeight = bitmap.getHeight() < bitmap.getWidth() ? 400 : bitmap.getHeight() / bitmap.getWidth() * 400;

        Bitmap scaledBitmap = null;

        try {
            Bitmap unscaledBitmap = ScalingUtilities.decodeFile(inFile.getAbsolutePath(), newWidth, newHeight, ScalingUtilities.ScalingLogic.FIT);
            scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, newWidth, newHeight, ScalingUtilities.ScalingLogic.FIT);
            unscaledBitmap.recycle();
        } catch (Exception e) {
            ;
        }


        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outFile);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            ;
        }

        scaledBitmap.recycle();
    }

    private void updatePhoto(String id, String username, File file) {
        showPreloader();
        profileInteractor.updatePhotoProfile(id, username, file, new ResourceRequestCallback<User>() {
            @Override
            public void onSucess(User user) {
                hidePreloader();
                _updated = true;
                picasso.load(user.getImage()).transform(new CircleTransform(getApplicationContext(), R.color.colorImageCircleStroke)).into(photoImage);
            }

            @Override
            public void onError(BaseException e) {
                hidePreloader();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == RequestCode.CAMERA_REQUEST) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int res = grantResults[i];
                if (permission.equals(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) && res == PackageManager.PERMISSION_GRANTED) {
                    if (checkCameraPermissions())
                        openCameraIntent();
                } else if (permission.equals(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) && res == PackageManager.PERMISSION_GRANTED) {
                    if (checkCameraPermissions())
                        openCameraIntent();
                }
            }
        } else if (requestCode == RequestCode.STORAGE_REQUEST) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int res = grantResults[i];
                if (permission.equals(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) && res == PackageManager.PERMISSION_GRANTED) {
                    if (checkStoragePermissions())
                        openStorageIntent();
                }
            }
        }
    }

    private boolean checkCameraPermissions() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkStoragePermissions() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void openCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            _photoFile = createImageFile();
            Uri photoUri = FileProvider.getUriForFile(this, getPackageName() +".provider", _photoFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            this.startActivityForResult(cameraIntent, RequestCode.CAMERA_REQUEST);
        }
    }

    private void openStorageIntent() {
        Intent storageIntent = new Intent();
        storageIntent.setType("image/*");
        storageIntent.setAction(Intent.ACTION_GET_CONTENT);
        this.startActivityForResult(storageIntent, RequestCode.STORAGE_REQUEST);
    }

    private File createImageFile(){
        File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_"+ timeStamp + "_";
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", mediaStorageDir);
        } catch (IOException e) {};

        return image;
    }
}
