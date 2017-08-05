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
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.SortField.Type;

import com.itheima.lucene.domain.Article;
import com.itheima.lucene.util.LuceneUtil;

public class TestFilter {

	  /**
	   * 测试过滤Filter
	   * @param args
	   */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	public static void testSearcher(String keywords) throws Exception{
		 IndexSearcher indexSearcher = LuceneUtil.getIndexSearcher();
		    
	      
	       //创建需要用于查询的字段数组
	        String[] fields = {"title"};
	        //创建查询用的类
	        QueryParser queryParser = new MultiFieldQueryParser(fields, LuceneUtil.getAnalyzer());
	        //查询符合关键字的数据
	       Query query = queryParser.parse(keywords);
	      //开始过滤
	      
	        //创建过滤
	           /**
	            * 
	            *  lucene6.6不能进行过滤
	            * 
	            */
	           
	      TopDocs topDocs = indexSearcher.search(query, 100);
	      
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
