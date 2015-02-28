package com.rdupuis.amikcal.commons;

import java.util.ArrayList;
import java.util.HashMap;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.useractivity.UserActivity;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

 
 public class MultipleItemsActivityList extends BaseAdapter {
    	
        final static int TYPE_LUNCH=1;
        final static int TYPE_ACTIVITY=2;
        final static int TYPE_WEIGHT=3;
	 
	    private ArrayList<HashMap<String, String>> mData = new ArrayList<HashMap<String, String>>();
        private LayoutInflater mInflater;
        
        public MultipleItemsActivityList(Activity mActivity) {
            mInflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
 
        public MultipleItemsActivityList(Fragment mFragment) {
            mInflater = (LayoutInflater)mFragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
 
        
        
        public void addItem(final HashMap<String, String> item) {
            mData.add(item);
            notifyDataSetChanged();
        }
       
        public int getCount() {
            return mData.size();
        }
 
        public HashMap<String, String> getItem(int position) {
            return (HashMap<String, String>) mData.get(position);
        }
 
     
        public long getItemId(int position) {
            return position;
        }
 
        public View getView(int position, View convertView, ViewGroup parent) {
            
            HashMap<String, String> map = (HashMap<String, String>) getItem(position);
            
            UserActivity.UA_CLASS_CD UA_Class = UserActivity.UA_CLASS_CD.valueOf(map.get("type"));
            
            
            TextView tv;
            if (convertView == null) {
                
                switch (UA_Class) {
                
           
               
                case WEIGHT:
                        convertView = mInflater.inflate(R.layout.list_item_activity_weight, null);
                       
                        tv = (TextView)convertView.findViewById(R.id.item_activity_weight_hour);
                        tv.setText(map.get("hour"));
                        
                        tv = (TextView)convertView.findViewById(R.id.item_activity_weight_min);
                        tv.setText(map.get("minute"));
                        
                        tv = (TextView)convertView.findViewById(R.id.item_activity_weight_weight);
                        
                        WeightObj w = new WeightObj(map.get("titre"));
                        tv.setText(w.format());
                        
                        
                        
                    break;
           
                
                
                case MOVE:
                        convertView = mInflater.inflate(R.layout.list_item_activity_physical_activity, null);
                        tv = (TextView)convertView.findViewById(R.id.item_activity_activity_title);
                        tv.setText(map.get("titre"));
                   
                        tv = (TextView)convertView.findViewById(R.id.item_activity_activity_hour);
                        tv.setText(map.get("hour"));
                   
                        tv = (TextView)convertView.findViewById(R.id.item_activity_activity_min);
                        tv.setText(map.get("minute"));
                   
                   
                   break;
                   
                   
                case LUNCH:
                default:
                    convertView = mInflater.inflate(R.layout.list_item_activity_lunch, null);
                    tv = (TextView)convertView.findViewById(R.id.item_activity_lunch_title);
                    tv.setText(map.get("titre"));
                    
                    tv = (TextView)convertView.findViewById(R.id.item_activity_lunch_hour);
                    tv.setText(map.get("hour"));
                    
                    tv = (TextView)convertView.findViewById(R.id.item_activity_lunch_min);
                    tv.setText(map.get("minute"));
                   
                    tv = (TextView)convertView.findViewById(R.id.item_activity_lunch_nbkcal);
                    tv.setText(map.get("sumEnergy"));
                    
                    tv = (TextView)convertView.findViewById(R.id.item_activity_lunch_mnt_proteins);
                    tv.setText(map.get("sumProteins"));
                    
                    tv = (TextView)convertView.findViewById(R.id.item_activity_lunch_mnt_lipids);
                    tv.setText(map.get("sumLipids"));
               	
                    tv = (TextView)convertView.findViewById(R.id.item_activity_lunch_mnt_glucids);
                    tv.setText(map.get("sumGlucids"));
              
                    break;
                   
                  
                    
                     
                }
                
            } 
            return convertView;
        }
 
    }
 
    
