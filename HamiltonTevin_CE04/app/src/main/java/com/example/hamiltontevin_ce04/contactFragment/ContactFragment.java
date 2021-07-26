package com.example.hamiltontevin_ce04.contactFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.hamiltontevin_ce04.R;
import com.example.hamiltontevin_ce04.adapter.ContactAdapter;
import com.example.hamiltontevin_ce04.model.ContactInfo;

import java.util.ArrayList;

public class ContactFragment extends ListFragment {
    private static ArrayList<ContactInfo> mContactsList;

    public ContactFragment() {
    }

    public static ContactFragment newInstance(ArrayList<ContactInfo> list) {

        Bundle args = new Bundle();
        mContactsList = list;
        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_list,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if( mContactsList != null && mContactsList.size() > 0){
            ContactAdapter ra = new ContactAdapter(getContext(),mContactsList);
            setListAdapter(ra);
        }
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if(getActivity() != null){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_detailContactInfo, DetailFragment.newInstance(mContactsList.get(position))).commit();
        }
    }
}