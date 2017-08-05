package com.itheima.lucene.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TopDocs;

import com.itheima.lucene.domain.Article;
import com.itheima.lucene.util.LuceneUtil;

public class TestSort {

	 /**
	  * 测试排序
	  * @param args
	  * @throws Exception
	  */
	public static void main(String[] args) throws Exception {
		
		
		  testSearcher("张总");

	}
	
	
	public static void testSearcher(String keywords) throws Exception{
		 IndexSearcher indexSearcher = LuceneUtil.getIndexSearcher();
		    
	      
	       //创建需要用于查询的字段数组
	        String[] fields = {"title"};
	        //创建查询用的类
	        QueryParser queryParser = new MultiFieldQueryParser(fields, LuceneUtil.getAnalyzer());
	        //查询符合关键字的数据
	       Query query = queryParser.parse(keywords);
	      //开始插叙
	       
	         
	        //创建排序字段，升序 
	        // SortField sortField = new SortField("id", Type.INT);
	         
	       //创建排序字段，降序 
	           SortField sortField = new SortField("id", Type.INT,true);
	       //创建一个Sort排序
	         Sort sort = new Sort();
	         
	          //添加排序条件
	           sort.setSort(sortField);
	      TopDocs topDocs = indexSearcher.search(query, 100,sort);
	      
	      System.out.println("总记录数:"+topDocs.totalHits);
	       List<Article> list = new ArrayList<Article>();
	       //返回击中
	        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
	          //int result  = (start+rows)<(scoreDocs.length)?(start+rows):(scoreDocs.length);
	         //如果start+rowsVSscoreDocs.length的话，返回较小值
	         for(ScoreDoc scoreDoc : scoreDocs){
	        	 
	        	  Document document = indexSearcher.doc(scoreDoc.doc);
	        	 System.out.println(document.get("id"));
	         }
	 }

}
