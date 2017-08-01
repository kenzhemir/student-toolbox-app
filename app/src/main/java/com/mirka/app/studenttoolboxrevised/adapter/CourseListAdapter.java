package com.mirka.app.studenttoolboxrevised.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mirka.app.studenttoolboxrevised.R;
import com.mirka.app.studenttoolboxrevised.data.MoodleContract;


/**
 *      This adapter binds the recycler view for displaying the list of courses on CourseListFragment activity
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder> {

    private Context mContext;
    private Cursor mData;
    private CourseListAdapterOnClickHandler mClickHandler;
    private int mCourseNameIndex;


    public interface CourseListAdapterOnClickHandler {
        public void onClick(int position);
    }

    /**
     * Constructor for CourseListAdapter that accepts the context in which it operates
     *
     * @param context Context of the adapter
     */
    public CourseListAdapter(Context context, CourseListAdapterOnClickHandler handler){
        mContext = context;
        mData = null;
        mClickHandler = handler;
    }


    /**
     * This method is called when view holder is created for an item in the list
     *
     * @param parent Parent view for the list item
     * @param viewType
     * @return Inflated view holder
     */
    @Override
    public CourseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_course, parent, false);
        CourseListViewHolder viewHolder = new CourseListViewHolder(view);
        return viewHolder;
    }


    /**
     * This method binds view holder to the position in the list
     *
     * @param holder Target view holder
     * @param position order number of the item
     */
    @Override
    public void onBindViewHolder(CourseListViewHolder holder, int position) {
        holder.bind(position);
    }


    /**
     * This method returns the number of items in the list
     *
     * @return number of items in the dataset
     */
    @Override
    public int getItemCount() {
        if (mData == null){
            return 0;
        }
        return mData.getCount();
    }

    /**
     * This class is a child class of ViewHolder class, to store the course list item
     */
    class CourseListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mCourseName;

        /**
         * Constructor for the CourseListViewHolder which stores the inner
         * elements of the course list item into the variables
         *
         * @param itemView target view
         */
        public CourseListViewHolder(View itemView) {
            super(itemView);
            mCourseName = (TextView) itemView.findViewById(R.id.tv_course_name);
            itemView.setOnClickListener(this);
        }

        /**
         * This method is called when the ViewHolder is bind to the particular list item
         * @param position order number of the item in the dataset
         */
        public void bind(int position) {
            mData.moveToPosition(position);
            try {
                mCourseName.setText(mData.getString(mCourseNameIndex));
            } catch (Exception ignored){
            }
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(mData.getPosition());
        }
    }


    /**
     * This method allows to change dataset to be displayed in the RecyclerView
     * @param data new dataset
     */
    public void setData(Cursor data){
        mData = data;
        mCourseNameIndex = data.getColumnIndex(MoodleContract.CourseEntry.COLUMN_SHORTNAME);
        notifyDataSetChanged();
    }
}
