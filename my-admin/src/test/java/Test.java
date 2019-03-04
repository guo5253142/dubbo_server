import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

	static List<Map<String,String>> rlist=new ArrayList<>();
	static String empty="\\u8d5e";
	static String hfempty="\\u8bc4\\u8bba";
	static String zfempty="\\u8f6c\\u53d1";
	static String zhuanfa="<em class=\\\"W_ficon ficon_forward S_ficon\\\">&#xe607;<\\/em>";
	static String huifu="<em class=\\\"W_ficon ficon_repeat S_ficon\\\">&#xe608;<\\/em>";
	static String zan="<em class=\\\"W_ficon ficon_praised S_txt2\\\">\\u00f1<\\/em>";
	static String last_zan="<em class=\\\"W_ficon ficon_praised S_txt2\\\">ñ<\\/em>";
	
	public static void main(String[] args) throws Exception {
		
		
		test("D:/tmp/1.txt",0);
		test("D:/tmp/2.txt",0);
		test("D:/tmp/last.txt",1);
		
		for(Map map:rlist){
			System.out.println(map.get("zfCount"));
		}
		System.out.println("回复=============");
		for(Map map:rlist){
			System.out.println(map.get("hfCount"));
		}
		System.out.println("赞=============");
		for(Map map:rlist){
			System.out.println(map.get("zCount"));
		}
	}
	
	public static void test(String path,int flag) throws Exception{
		List<Map<String,String>> list=new ArrayList<>();
		File file=new File(path);
		FileReader fr=new FileReader(file);
		BufferedReader br=new BufferedReader(fr);
		String  str=br.readLine();
		count(str,flag,list);
		Collections.reverse(list);
		rlist.addAll(list);
		if(null!=fr){
			fr.close();
		}
		if(null!=br){
			br.close();
		}
	}
	
	public static void count(String source,int flag,List<Map<String,String>> list){
		Map<String,String> map=new HashMap<>();
		int a1=source.indexOf(zhuanfa);
		
		if(a1>=0){
			source=source.substring(a1+zhuanfa.length(), source.length());
			int b1=source.indexOf("<\\/em>");
			String zfCount=source.substring(4,b1);
			if(zfCount.equals(zfempty)){
				zfCount="0";
			}
			map.put("zfCount", zfCount);
			
			int a2=source.indexOf(huifu);
			source=source.substring(a2+huifu.length(), source.length());
			int b2=source.indexOf("<\\/em>");
			String hfCount=source.substring(4,b2);
			if(hfCount.equals(hfempty)){
				hfCount="0";
			}
			map.put("hfCount", hfCount);
			
			
			if(flag==1){
				int a3=source.indexOf(last_zan);
				source=source.substring(a3+last_zan.length(), source.length());
			}else{
				int a3=source.indexOf(zan);
				source=source.substring(a3+zan.length(), source.length());
			}
			
			int b3=source.indexOf("<\\/em>");
			String zCount=source.substring(4,b3);
			if("赞".equals(zCount)){
				map.put("zCount","0");
			}else{
				map.put("zCount", zCount.equals(empty)?"0":zCount);
			}
			
			
			list.add(map);
			count(source,flag,list);
		}
		
	}

}
