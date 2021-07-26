package com.example.hamiltontevin_ce04.contactFragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hamiltontevin_ce04.R;
import com.example.hamiltontevin_ce04.model.ContactInfo;

public class DetailFragment extends Fragment {
    private static final String ARGS_SELECTED_CONTACT = "ARGS_SELECTED_CONTACT";

    public DetailFragment() {
    }

    public static DetailFragment newInstance(ContactInfo ci) {

        Bundle args = new Bundle();
        args.putSerializable(ARGS_SELECTED_CONTACT,ci);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getView() != null){

            if(getArguments() != null && getContext() != null && getView() != null){
                ContactInfo contact = (ContactInfo) getArguments().getSerializable(ARGS_SELECTED_CONTACT);
                if(contact != null) {
                    TextView fullNameDisplay = getView().findViewById(R.id.fragment_detail_tv_name);
                    String fullName  = contact.getFirstName() + " " + contact.getMiddleName() + " " + contact.getLastName();
                    fullNameDisplay.setText(fullName);
                    ImageView image = getView().findViewById(R.id.fragment_detail_imageView);

                    if(contact.getContactImage() == null){
                        image.setImageResource(R.drawable.ic_person_24dp);
                    }else{
                        image.setImageURI(Uri.parse(contact.getContactImage()));
                    }
                    ListView lv = getView().findViewById(R.id.fragment_detail_list);
                    if (lv != null) {
                        if(getContext() != null) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, contact.getNumberList());
                            lv.setAdapter(arrayAdapter);
                        }
                    }
                }

            }
        }
    }

}
