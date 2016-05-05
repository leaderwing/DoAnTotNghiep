package com.example.quy2016.doantotnghiep;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    public String user_email, user_name;
    Date dateFinish;
    Calendar cal;
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
                query1.whereEqualTo("user",ParseUser.getCurrentUser());
                query1.getInBackground(profileUser.getObjectId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        object.put("user_avatar",file);
                        object.saveInBackground();
                    }
                });


            }
        });
        cal=Calendar.getInstance();
        SimpleDateFormat dft=null;
        //Định dạng ngày / tháng /năm
        dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        final String strDate=dft.format(cal.getTime());
        //hiển thị lên giao diện

        ParseUser user = ParseUser.getCurrentUser();
        user_email = user.getEmail().toString();
        user_name = user.getUsername().toString();
        tvName.setText(user.get("name").toString());
        tvEmail.setText(user_email);

        final ParseQuery<ParseObject> query = ParseQuery.getQuery("user_details");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for (ParseObject object : objects) {
                    profileUser = new ProfileUser();
                   if (object.getString("user_birthday") != null )
                    profileUser.setBirthday(object.getString("user_birthday"));
                    else
                   profileUser.setBirthday(strDate);
                    if (object.getString("course") != null )
                    profileUser.setCourse(object.getString("course"));
                    if (object.getString("user_char") != null )
                    profileUser.setCharacter(object.getString("user_char"));
                    if (object.getString("user_school") != null )
                    profileUser.setSchool(object.getString("user_school"));
                    if (object.getString("user_hobbies") != null )
                    profileUser.setHobbies(object.getString("user_hobbies"));
                    profileUser.setObjectId(object.getObjectId());
                   // profileUser.setbjectId(object.getObjectId());
                    //if(!object.getParseFile("user_avatar").isDirty())
                   // profileUser.setPhotoFile(object.getParseFile("user_avatar"));
                    tvBirthday.setText(profileUser.getBirthday());
                    tvCourse.setText(profileUser.getCourse());
                    tvSchool.setText(profileUser.getSchool());
                    tvHobby.setText(profileUser.getHobbies());
                    tvCharacter.setText(profileUser.getCharacter());
                   // avatar.setImageBitmap(profileUser.getPhotoFile().);
                }
            }
        });

        btnName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvName.setFocusableInTouchMode(true);

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
                    showDatePickerDialog();
                } else {
                    tvBirthday.setFocusableInTouchMode(false);
                    tvBirthday.setFocusable(false);
                }

                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("user_details");
                query1.whereEqualTo("user",ParseUser.getCurrentUser());
                query1.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        for (ParseObject object : objects) {
                            profileUser = new ProfileUser();
                            profileUser.setObjectId(object.getObjectId());
                            ParseQuery<ParseObject> query11 = ParseQuery.getQuery("user_details");
                            query11.getInBackground(profileUser.getObjectId(), new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject object, ParseException e) {
                                    object.put("user_birthday", tvBirthday.getText().toString());
                                    object.saveInBackground();
                                }
                            });
                        }
                    }
                });

            }
        });
        btnHobby.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvHobby.setFocusableInTouchMode(true);
                } else {
                    tvHobby.setFocusableInTouchMode(false);
                    tvHobby.setFocusable(false);
                    ParseQuery<ParseObject> query2 = ParseQuery.getQuery("user_details");
                    query2.whereEqualTo("user", ParseUser.getCurrentUser());
                    query2.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            for (ParseObject objective : objects) {
                                profileUser = new ProfileUser();
                                profileUser.setObjectId(objective.getObjectId());
                                ParseQuery<ParseObject> query21 = ParseQuery.getQuery("user_details");
                                query21.getInBackground(profileUser.getObjectId(), new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject object, ParseException e) {
                                        object.put("user_hobbies", tvHobby.getText().toString());
                                        object.saveInBackground();
                                    }
                                });
                            }
                        }
                    });
                }


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
                    ParseQuery<ParseObject> query3 = ParseQuery.getQuery("user_details");
                    query3.whereEqualTo("user", ParseUser.getCurrentUser());
                    query3.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            for (ParseObject objective : objects) {
                                profileUser = new ProfileUser();
                                profileUser.setObjectId(objective.getObjectId());
                                ParseQuery<ParseObject> query31 = ParseQuery.getQuery("user_details");
                                query31.getInBackground(profileUser.getObjectId(), new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject object, ParseException e) {
                                        object.put("course", tvCourse.getText().toString());
                                        object.saveInBackground();
                                    }
                                });
                            }
                        }
                    });
                }


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
                    ParseQuery<ParseObject> query4 = ParseQuery.getQuery("user_details");
                    query4.whereEqualTo("user", ParseUser.getCurrentUser());
                    query4.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            for (ParseObject objective : objects) {
                                profileUser = new ProfileUser();
                                profileUser.setObjectId(objective.getObjectId());
                                ParseQuery<ParseObject> query41 = ParseQuery.getQuery("user_details");
                                query41.getInBackground(profileUser.getObjectId(), new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject object, ParseException e) {
                                        object.put("user_char", tvCharacter.getText().toString());
                                        object.saveInBackground();
                                    }
                                });
                            }
                        }
                    });
                }


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
                    ParseQuery<ParseObject> query5 = ParseQuery.getQuery("user_details");
                    query5.whereEqualTo("user", ParseUser.getCurrentUser());
                    query5.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            for (ParseObject objective : objects) {
                                profileUser = new ProfileUser();
                                profileUser.setObjectId(objective.getObjectId());
                                ParseQuery<ParseObject> query51 = ParseQuery.getQuery("user_details");
                                query51.getInBackground(profileUser.getObjectId(), new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject object, ParseException e) {
                                        object.put("user_school", tvSchool.getText().toString());
                                        object.saveInBackground();
                                    }
                                });
                            }
                        }
                    });
                }


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
    public void showDatePickerDialog()
    {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                tvBirthday.setText(
                        (dayOfMonth) + "/" + (monthOfYear+1)+"/"+year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
                dateFinish=cal.getTime();
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=tvBirthday.getText()+"";
        String strArrtmp[]=s.split("/");
        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam= Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(
                getActivity(),
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày sinh");
        pic.show();
    }

}