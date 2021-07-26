package com.example.hamiltontevin_ce04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.example.hamiltontevin_ce04.contactFragment.ContactFragment;
import com.example.hamiltontevin_ce04.contactFragment.DetailFragment;
import com.example.hamiltontevin_ce04.model.ContactInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private final static String[] CONTACT_COLUMNS = {
            ContactsContract.Contacts.NAME_RAW_CONTACT_ID,
            ContactsContract.Data.DATA2,
            ContactsContract.Data.DATA3,
            ContactsContract.Data.DATA5,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
            ContactsContract.Contacts.HAS_PHONE_NUMBER};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermissions();
    }

    private ArrayList<ContactInfo> getContactData(){
        ArrayList<ContactInfo> contactInfoArrayList = null;

        String[] projection = new String[] {CONTACT_COLUMNS[0],CONTACT_COLUMNS[1], CONTACT_COLUMNS[2],CONTACT_COLUMNS[3],CONTACT_COLUMNS[4],CONTACT_COLUMNS[5]};
        String where = ContactsContract.Data.MIMETYPE + "='" +  ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE + "'";
        Uri uri = ContactsContract.Data.CONTENT_URI;

        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(
                uri, projection, where,
                null, null);

        if(cursor != null){
            contactInfoArrayList = new ArrayList<>();
            while(cursor.moveToNext()){

                String cursorId = cursor.getString(cursor.getColumnIndex(CONTACT_COLUMNS[0]));
                String givenName = cursor.getString(cursor.getColumnIndex(CONTACT_COLUMNS[1]));
                String familyName = cursor.getString(cursor.getColumnIndex(CONTACT_COLUMNS[2]));
                String middleName = cursor.getString(cursor.getColumnIndex(CONTACT_COLUMNS[3]));
                String contactImage = cursor.getString(cursor.getColumnIndex(CONTACT_COLUMNS[4]));

                ArrayList<String> contactNumberList = new ArrayList<>();

                if(cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0){
                    Cursor contactCursor = resolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{cursorId},null);

                    while (contactCursor != null && contactCursor.moveToNext()){
                        contactNumberList.add(contactCursor.getString(contactCursor.
                                getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    }
                    if(contactCursor != null) {
                        contactCursor.close();
                    }
                }else{
                    contactNumberList.add("111-111-1111");
                }
                if(givenName == null){
                    givenName = "";
                }
                if(familyName == null){
                    familyName = "";
                }
                if(middleName == null){
                    middleName = "";
                }

                contactInfoArrayList.add(new ContactInfo(givenName,familyName,middleName,
                        contactNumberList,contactImage));
            }
            cursor.close();
        }
        return contactInfoArrayList;
    }

    private void getPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        else {
            ArrayList<ContactInfo> mContactList = getContactData();
            if(mContactList.size() > 0) {
                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_detailContactInfo, DetailFragment.newInstance(mContactList.get(0))).commit();
                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_contactList, ContactFragment.newInstance(mContactList)).commit();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                getPermissions();
            } else {
                Toast.makeText(this, "Until you grant the permission, we cannot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
