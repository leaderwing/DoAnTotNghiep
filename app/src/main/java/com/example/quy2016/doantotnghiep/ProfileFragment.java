package com.example.quy2016.doantotnghiep;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.quy2016.doantotnghiep.R;
import com.model.ProfileUser;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by Ratan on 7/29/2015.
 */
public class ProfileFragment extends Fragment
{
    EditText tvName , tvEmail,tvCourse,tvSchool,tvHobby,tvCharacter,tvBirthday;
    ToggleButton btnName,btnEmail,btnBirth,btnCourse,btnSchool,btnHobby,btnCharacter;
    Button btnSave;
    ImageView avatar;
    ImageButton imgUpload;
    ProfileUser profileUser;
    public  static  final  int SELECT_PICTURE = 1000;
    public Uri selectedImageUri;
    public  String selectedImagePath;
    public String user_email;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.profile_layout,null);

        tvName = (EditText) view.findViewById(R.id.tvName);
        tvEmail = (EditText) view.findViewById(R.id.tvEmail);
        tvCourse = (EditText) view.findViewById(R.id.tvCourse);
        tvBirthday = (EditText) view.findViewById(R.id.tvBirth);
        tvSchool = (EditText) view.findViewById(R.id.tvSchool);
        tvHobby = (EditText) view.findViewById(R.id.tvHobby);
        tvCharacter = (EditText) view.findViewById(R.id.tvCharacter);
        avatar = (ImageView) view.findViewById(R.id.avatar_profile);
        // btnEmail = (ToggleButton) view.findViewById(R.id.btnEmail);
        btnBirth = (ToggleButton) view.findViewById(R.id.btnBirthday);
        btnCourse = (ToggleButton) view.findViewById(R.id.btnCourse);
        btnSchool = (ToggleButton) view.findViewById(R.id.btnSchool);
        btnHobby = (ToggleButton) view.findViewById(R.id.btnHobby);
        btnCharacter = (ToggleButton) view.findViewById(R.id.btnCharacter);
        btnName = (ToggleButton) view.findViewById(R.id.btnName);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        imgUpload = (ImageButton) view.findViewById(R.id.upload_avatar);
        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInGallery();
                final ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage(getString(R.string.dialog_title));
                dialog.show();
                InputStream imageStream = null;
                try {
                    imageStream = getActivity().getContentResolver().openInputStream(selectedImageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();
                final ParseFile file = new ParseFile("user_avatar", image);
                // Upload the image into Parse Cloud
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("user_details");
                profileUser = new ProfileUser();
                query1.whereEqualTo("Email",user_email);
                query1.getInBackground(profileUser.getbjectId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        object.put("user_avatar",file);
                        object.saveInBackground();
                    }
                });


            }
        });
        ParseUser user = ParseUser.getCurrentUser();
        user_email = user.getEmail().toString();
        tvName.setText(user.get("name").toString());
        tvEmail.setText(user_email);

        final ParseQuery<ParseObject> query = ParseQuery.getQuery("user_details");
        query.whereEqualTo("Email", ParseUser.getCurrentUser().getEmail());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for (ParseObject object : objects) {

                    profileUser = new ProfileUser();
                    profileUser.setBirthday(object.getDate("user_birthday").toString());
                    profileUser.setCourse(object.getString("course"));
                    profileUser.setCharacter(object.getString("user_char"));
                    profileUser.setSchool(object.getString("user_school"));
                    profileUser.setHobbies(object.getString("user_hobbies"));
                    if(!object.getParseFile("user_avatar").isDirty())
                    profileUser.setPhotoFile(object.getParseFile("user_avatar"));
                    tvBirthday.setText(profileUser.getBirthday().toString());
                    tvCourse.setText(profileUser.getCourse().toString());
                    tvSchool.setText(profileUser.getSchool().toString());
                    tvHobby.setText(profileUser.getHobbies().toString());
                    tvCharacter.setText(profileUser.getCharacter().toString());
                   // avatar.setImageBitmap(profileUser.getPhotoFile().);
                }
            }
        });

        btnName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvName.setFocusableInTouchMode(true);
                    isChecked = false;
                } else {
                    tvName.setFocusableInTouchMode(false);
                    tvName.setFocusable(false);
                }
                ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
                query.getInBackground(ParseUser.getCurrentUser().getObjectId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        object.put("name" ,tvName.getText().toString());
                        object.saveInBackground();
                    }
                });

            }
        });
        btnBirth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvBirthday.setFocusableInTouchMode(true);
                    isChecked = false;
                } else {
                    tvBirthday.setFocusableInTouchMode(false);
                    tvBirthday.setFocusable(false);
                }
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("user_details");
                profileUser = new ProfileUser();
                query1.whereEqualTo("Email",user_email);
                query1.getInBackground(profileUser.getbjectId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        object.put("user_birthday",tvBirthday.getText());
                        object.saveInBackground();
                    }
                });
            }
        });
        btnHobby.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvHobby.setFocusableInTouchMode(true);
                    isChecked = false;
                } else {
                    tvHobby.setFocusableInTouchMode(false);
                    tvHobby.setFocusable(false);
                }
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("user_details");
                profileUser = new ProfileUser();
                query1.whereEqualTo("Email",user_email);
                query1.getInBackground(profileUser.getbjectId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        object.put("user_hobbies",tvHobby.getText());
                        object.saveInBackground();
                    }
                });

            }
        });
        btnCourse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvCourse.setFocusableInTouchMode(true);
                    isChecked = false;
                } else {
                    tvCourse.setFocusableInTouchMode(false);
                    tvCourse.setFocusable(false);
                }
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("user_details");
                profileUser = new ProfileUser();
                query1.whereEqualTo("Email",user_email);
                query1.getInBackground(profileUser.getbjectId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        object.put("Course",tvCourse.getText());
                        object.saveInBackground();
                    }
                });
            }
        });
        btnCharacter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvCharacter.setFocusableInTouchMode(true);
                    isChecked = false;
                } else {
                    tvCharacter.setFocusableInTouchMode(false);
                    tvCharacter.setFocusable(false);
                }
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("user_details");
                profileUser = new ProfileUser();
                query1.whereEqualTo("Email",user_email);
                query1.getInBackground(profileUser.getbjectId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        object.put("user_char",tvCharacter.getText());
                        object.saveInBackground();
                    }
                });
            }
        });
        btnSchool.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvSchool.setFocusableInTouchMode(true);
                    isChecked = false;
                } else {
                    tvSchool.setFocusableInTouchMode(false);
                    tvSchool.setFocusable(false);
                }
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("user_details");
                profileUser = new ProfileUser();
                query1.whereEqualTo("Email",user_email);
                query1.getInBackground(profileUser.getbjectId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        object.put("user_school",tvSchool.getText());
                        object.saveInBackground();
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void openInGallery() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "Error :" , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();

                selectedImagePath = getPath(selectedImageUri);
                System.out.println("Image Path : " + selectedImagePath);
                imgUpload.setImageURI(selectedImageUri);
            }
        }
    }
    @SuppressWarnings("deprecation")
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

    }
}