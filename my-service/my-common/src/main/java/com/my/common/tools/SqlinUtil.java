package com.my.common.tools;

import java.util.List;

public class SqlinUtil {


	//拼装in sql
	public static String sqlInutil(String tids,int num,String recordName){
		String[] strArray = tids.split(",");
		String tidssql ="(" +recordName + " in(";
		
		for(int j = 0;j<(strArray.length/num)+1;j++){
			
			for(int i = (j*num) ;i<(j+1)*num;i++){
				
				if(i>=strArray.length){
					break;
				}else{
					if(i== (strArray.length-1) ){
						tidssql += strArray[i]+"))";
					}
				    else if(i == ((j+1)*num-1)){
						tidssql += strArray[i]+")";
					}else{
						tidssql += strArray[i]+",";
					}
				}
			}
			if(j < (strArray.length/num)){
				tidssql += " or "+" "+recordName +" in(";
			}
		}
		
		return tidssql;
	}
	   //拼装in sql
		public static String sqlutil(List<Long> list,int num,String recordName){
			
			String tidssql = " in(";
			
			for(int j = 0;j<(list.size()/num)+1;j++){
				
				for(int i = (j*num) ;i<(j+1)*num;i++){
					
					if(i>=list.size()){
						break;
					}else{
						if( i== (list.size()-1)){
							tidssql += list.get(i)+"))";
						}
						else if(i == ((j+1)*num-1) || i== (list.size()-1) ){
							tidssql += list.get(i)+")";
						}else{
							tidssql += list.get(i)+",";
						}
					}
				}
				if(j < (list.size()/num)){
					tidssql += " or "+" "+recordName +" in(";
				}
			}
			
			return tidssql;
		}
}
