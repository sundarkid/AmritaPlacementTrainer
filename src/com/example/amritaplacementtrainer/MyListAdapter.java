package com.example.amritaplacementtrainer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyListAdapter extends BaseAdapter {

	ArrayList<SingleRow> list;
	Context context;
	MyListAdapter(Context c, ArrayList<SingleRow> data)
	{
		context = c;
		list = new ArrayList<SingleRow>();
		list = data;
	}



	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        myHolder holder = null;

        if(row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_row, parent, false);
            holder = new myHolder(row);
            Log.d("Creating Row", "Inflation");
            row.setTag(holder);
        }else{
            holder = (myHolder) row.getTag();

            Log.d("Reusing", "efficiency");
        }
		
		SingleRow temp = list.get(position);
		
		holder.title.setText(temp.name);
		holder.link.setText(temp.link);
		
		return row;
	}
    class myHolder{
        TextView link;
        TextView title;
        myHolder(View row){
            title = (TextView) row.findViewById(R.id.textViewFileName);
            link = (TextView) row.findViewById(R.id.textViewLink);

        }
    }

	public void updateList(ArrayList<SingleRow> data){
		list.clear();
		list.addAll(data);
		this.notifyDataSetChanged();
	}

}
