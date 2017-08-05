package com.itheima.lucene.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.itheima.lucene.domain.Article;
import com.itheima.lucene.util.LuceneUtil;

public class TestScore {

	public static void main(String[] args) throws Exception {
		
		/**
		 * 1.内容一样,搜索关键字一样，得分也是一样的。。
		 * 2.我们可以人工的去干预这个得分
		 * 3.得分跟搜索关键字在文章当中出现的频率，次数，位置有关系。。
		 * 
		 */
		testSearcher("张");
		
		
		/**
		 * 
		 *  seo:
		 *  百度会把那些数据获取 过去建立索引。。。
		 * 
		 * 
		 */

	}
	
	
	 public static void testSearcher(String keywords) throws Exception{
		 IndexSearcher indexSearcher = LuceneUtil.getIndexSearcher();
		    
	      
	       //创建需要用于查询的字段数组
	        String[] fields = {"title","content"};
	        //创建查询用的类
	        QueryParser queryParser = new MultiFieldQueryParser(fields, LuceneUtil.getAnalyzer());
	        //查询符合关键字的数据
	       Query query = queryParser.parse(keywords);
	      //开始插叙
	      TopDocs topDocs = indexSearcher.search(query, 100);
	      
	      System.out.println("总记录数:"+topDocs.totalHits);
	       List<Article> list = new ArrayList<Article>();
	       //返回击中
	        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
	          //int result  = (start+rows)<(scoreDocs.length)?(start+rows):(scoreDocs.length);
	         //如果start+rowsVSscoreDocs.length的话，返回较小值
	         for(ScoreDoc scoreDoc : scoreDocs){
	        	 
	        	  Document document = indexSearcher.doc(scoreDoc.doc);
	        	 System.out.println("文档编号，id"+document.get("id")	+"====得分===="+scoreDoc.score);
	         }
	 }

}
