package com.example.baoadr01.loginscreen2.filter_demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.example.baoadr01.loginscreen2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BaoADR01 on 8/12/2015.
 */
public class FilterableListActivity extends ActionBarActivity {
    ListView listView;
    FilterableAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_filterable);
        listView = (ListView) findViewById(R.id.edittext_listview);
        adapter = new FilterableAdapter(getData());
        listView.setAdapter(adapter);
        EditText editText = (EditText) findViewById(R.id.editText_search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());
            }
        });
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("Item " + i);
        }
        return data;
    }
//-----------------------------------------------------------------------------------------------
    public class FilterableAdapter extends BaseAdapter implements Filterable {
        List<String> data;
        List<String> filterData;

        public FilterableAdapter(List<String> data) {
            this.data = data;
            filterData = new ArrayList<String>();
            filterData.addAll(data);
        }

        @Override
        public int getCount() {
            return filterData.size();
        }

        @Override
        public Object getItem(int position) {
            return filterData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_adapter, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else
                holder = (ViewHolder) convertView.getTag();
            holder.textView.setText(getItem(position).toString());
            return convertView;
        }

        @Override
        public Filter getFilter() {
            return filter;
        }

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<String> resultData = new ArrayList<String>();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).contains(constraint)) {
                        resultData.add(data.get(i));
                    }
                }
                FilterResults results = new FilterResults();
                results.values = resultData;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterData = (List<String>) results.values;
                notifyDataSetChanged();
            }
        };


        class ViewHolder {
            View itemView;
            TextView textView, textView1;

            public ViewHolder(View view) {
                this.itemView = view;
                textView = (TextView) view.findViewById(R.id.textView_demo);
                textView1 = (TextView) view.findViewById(R.id.textView1_demo);
            }
        }
    }

}
