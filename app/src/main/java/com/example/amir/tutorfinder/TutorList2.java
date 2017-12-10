package com.example.amir.tutorfinder;

import android.app.Activity;
import android.widget.ArrayAdapter;
import java.util.List;

/**
 * Created by amir on 10/12/2017.
 */

public class TutorList2 extends ArrayAdapter<Tutor>{

    private Activity context;
    private List<Tutor> TutorList;

    public TutorList(Activity context, List<Tutor> TutorList){

        super(context,R.layout.list_layout);
        this.context = context;
        this.TutorList = TutorList;
    }


}
