package com.mirka.app.studenttoolboxrevised;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mirka.app.studenttoolboxrevised.adapter.CourseListAdapter;
import com.mirka.app.studenttoolboxrevised.data.MoodleContract;
import com.mirka.app.studenttoolboxrevised.data.MoodleDbHelper;
import com.mirka.app.studenttoolboxrevised.utils.DatabaseUtils;
import com.mirka.app.studenttoolboxrevised.utils.MoodleUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.List;

public class CoursesSectionActivity extends AppCompatActivity implements CourseListAdapter.CourseListAdapterOnClickHandler, LoaderManager.LoaderCallbacks<List<Course>> {

    private RecyclerView mCurrentCoursesRecyclerView;
    private RecyclerView mPreviousCoursesRecyclerView;
    private SQLiteDatabase mDb;

    private boolean mExpanded;


    private final int COURSE_SYNC_LOADER = 256;
    private final LinearLayout.LayoutParams PARAMS_HIDDEN = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
    private final LinearLayout.LayoutParams PARAMS_SHOWN = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_section);

        getCurrentCoursesFromDatabase();


        mDb = (new MoodleDbHelper(this)).getWritableDatabase();

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
        return db.query(MoodleContract.CourseEntry.TABLE_NAME, null, MoodleContract.CourseEntry.COLUMN_IS_ACTIVE + "= 1", null, null, null, null);
    }

    private void expandPreviousCourses(View view) {
        if (mExpanded) {
            ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_more_gray_24dp, 0);

            mPreviousCoursesRecyclerView.setLayoutParams(PARAMS_HIDDEN);
        } else {
            ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_less_gray_24dp, 0);

            mPreviousCoursesRecyclerView.setLayoutParams(PARAMS_SHOWN);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.courses_section_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sync) {
            synchronizeDatabase();
            return true;
        } else if (id == R.id.action_drop){
            dropUserTable();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dropUserTable() {
        int res = mDb.delete(MoodleContract.UserEntry.TABLE_NAME, "*", null);
        Toast.makeText(this, String.valueOf(res) + " rows deleted", Toast.LENGTH_SHORT).show();
    }

    private void synchronizeDatabase() {


        Bundle queryBundle = new Bundle();
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> syncLoader = loaderManager.getLoader(COURSE_SYNC_LOADER);
        loaderManager.restartLoader(COURSE_SYNC_LOADER, queryBundle, this);
    }

    @Override
    public void onClick(int position) {

    }

    @Override
    public Loader<List<Course>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Course>>(this) {
            // TODO (1) Create a String member variable called mGithubJson that will store the raw JSON
            List<Course> mData;


            @Override
            protected void onStartLoading() {
                /*
                 * When we initially begin loading in the background, we want to display the
                 * loading indicator to the user
                 */

                //  If mGithubJson is not null, deliver that result. Otherwise, force a load
                if (mData != null) {
                    deliverResult(mData);
                } else {
                    forceLoad();
                }
            }


            @Override
            public List<Course> loadInBackground() {

                SQLiteDatabase db = (new MoodleDbHelper(getContext())).getReadableDatabase();
                Cursor c = db.query(MoodleContract.UserEntry.TABLE_NAME,
                        new String[]{MoodleContract.UserEntry.COLUMN_MOODLE_URL, MoodleContract.UserEntry.COLUMN_MOODLE_ID, MoodleContract.UserEntry.COLUMN_TOKEN},
                        null,
                        null,
                        null,
                        null,
                        null);
                if (c.getCount() == 0) return null;
                c.moveToFirst();
                String url = c.getString(c.getColumnIndex(MoodleContract.UserEntry.COLUMN_MOODLE_URL));
                String user_id = c.getString(c.getColumnIndex(MoodleContract.UserEntry.COLUMN_MOODLE_ID));
                String token = c.getString(c.getColumnIndex(MoodleContract.UserEntry.COLUMN_TOKEN));
                JSONArray course_list = MoodleUtils.getUserCourses(url, user_id, token);
//
//                mDb.insert(MoodleContract.CourseEntry.TABLE_NAME,
//                        null,
//                        DatabaseUtils.getCourseCV(
//                                course_list.getString("id")
//                        ));

                return null;
            }

            @Override
            public void deliverResult(List<Course> data) {
                mData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Course>> loader, List<Course> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<Course>> loader) {

    }
}
