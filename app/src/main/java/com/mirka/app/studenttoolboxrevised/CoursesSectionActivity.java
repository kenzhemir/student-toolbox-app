package com.mirka.app.studenttoolboxrevised;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mirka.app.studenttoolboxrevised.adapter.CourseListAdapter;
import com.mirka.app.studenttoolboxrevised.data.MoodleContract;
import com.mirka.app.studenttoolboxrevised.data.MoodleDbHelper;

public class CoursesSectionActivity extends AppCompatActivity implements CourseListAdapter.CourseListAdapterOnClickHandler{

    private RecyclerView mCurrentCoursesRecyclerView;
    private RecyclerView mPreviousCoursesRecyclerView;

    private boolean mExpanded;



    private final LinearLayout.LayoutParams PARAMS_HIDDEN = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
    private final LinearLayout.LayoutParams PARAMS_SHOWN = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_section);

        getCurrentCoursesFromDatabase();


        mCurrentCoursesRecyclerView = (RecyclerView) findViewById(R.id.rv_current_courses);
        mCurrentCoursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CourseListAdapter adapter = new CourseListAdapter(this, this);
        // Put data into recycler
        adapter.setData(getCurrentCoursesFromDatabase());

        mCurrentCoursesRecyclerView.setAdapter(adapter);


    }

    private Cursor getCurrentCoursesFromDatabase() {
        // Get the data;
        MoodleDbHelper dbHelper = new MoodleDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(MoodleContract.CourseEntry.TABLE_NAME, null,  MoodleContract.CourseEntry.COLUMN_IS_ACTIVE + "= 1", null, null, null, null);
    }

    private void expandPreviousCourses(View view){
        if (mExpanded){
            ((TextView)view).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_more_gray_24dp, 0);

            mPreviousCoursesRecyclerView.setLayoutParams(PARAMS_HIDDEN);
        } else {
            ((TextView)view).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_less_gray_24dp, 0);

            mPreviousCoursesRecyclerView.setLayoutParams(PARAMS_SHOWN);
        }
    }


    @Override
    public void onClick(int position) {

    }
}
