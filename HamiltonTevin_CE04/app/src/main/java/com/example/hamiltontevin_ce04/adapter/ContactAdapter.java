package com.example.hamiltontevin_ce04.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hamiltontevin_ce04.R;
import com.example.hamiltontevin_ce04.model.ContactInfo;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {
    private final Context mContext;
    private final ArrayList<ContactInfo> mContactList;

    public ContactAdapter(Context mContext, ArrayList<ContactInfo> mContactList) {
        this.mContext = mContext;
        this.mContactList = mContactList;
    }

    @Override
    public int getCount() {
        if(mContactList != null){
            return mContactList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(mContactList != null && position >= 0 && position < mContactList.size()){
            return mContactList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        ContactInfo contact = (ContactInfo) getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.contact_list_layout, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }

        if(contact != null){
            String fullName = contact.getFirstName() +" "+ contact.getLastName();
            vh.tv_ContactName.setText(fullName);
            if(contact.getNumberList().size() != 0) {
                vh.tv_ContactNum.setText(contact.getNumberList().get(0));
            }
            if(contact.getContactImage() != null){
                vh.iv_ContactImage.setImageURI(Uri.parse((contact.getContactImage())));
            }else {
                vh.iv_ContactImage.setImageResource(R.drawable.ic_person_24dp);
            }
        }
        return convertView;
    }

    static class ViewHolder{
        final ImageView iv_ContactImage;
        final TextView tv_ContactName;
        final TextView tv_ContactNum;

        ViewHolder(View _layout){

            iv_ContactImage = _layout.findViewById(R.id.iv_layout_contactPhoto);
            tv_ContactName = _layout.findViewById(R.id.tv_layout_name);
            tv_ContactNum = _layout.findViewById(R.id.tv_layout_phoneNumber);
        }
    }
}
