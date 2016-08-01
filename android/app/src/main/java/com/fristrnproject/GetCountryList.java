package com.fristrnproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import cn.smssdk.SMSSDK;

public class GetCountryList {

		private HashMap<Character, ArrayList<String[]>> first;
		ArrayList<String[]> second; 
		String countyList="\n";
		
		@SuppressWarnings("rawtypes")
		public String getCountry(HashMap<Character, ArrayList<String[]>> list){
			first = SMSSDK.getGroupedCountryList();
			Set set=first.keySet();
			for(Object obj : set){
				System.out.println("第一层"+first.get(obj));  
				second=first.get(obj);
				System.out.println("第二层"+second);
				for(int i = 0;i<second.toArray().length;i++){
					String[] thirst =second.get(i);
					System.out.println("第三层"+thirst);
					System.out.println("实际数据"+thirst[0]+"国家"+thirst[1]);
					String str=thirst[0]+"----------"+thirst[1]+"区域号\n";
					countyList = countyList+str;
				}
			}
			return countyList;
		}
}
