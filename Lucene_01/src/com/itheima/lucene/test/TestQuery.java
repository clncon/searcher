package com.itheima.lucene.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.BinaryPoint;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PointRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;

import com.itheima.lucene.domain.Article;
import com.itheima.lucene.util.LuceneUtil;

public class TestQuery {

	public static void main(String[] args) throws Exception {
	  
		
		  //第一种查询TermQuery
		
//		  Query query = new TermQuery(new Term("author","张三"));
//		    testSearcher(query);
		 
		 //第二种查询 字符串查询
		
		//String fields []={"author"};
		//
		//QueryParser queryParser=new MultiFieldQueryParser(LuceneUtils.getMatchVersion(),fields,LuceneUtils.getAnalyzer());
		//Query query=queryParser.parse("毕加索");
		
		
		//第三种查询，查询所有
		
//		 Query query = new MatchAllDocsQuery();		 
//		  testSearcher(query);
		
		 
		
		 //第四种查询，范围查询,可以使用次查询来替代过滤器(lucene6.6不支持过滤)
		/**
		 *lucene4.4通过使用NumericRangeQuery创建范围查询
		 */
		
		//Query query = IntPoint.newRangeQuery("id", 1, 10);
		//testSearcher(query);
		
		
		/**
		 * 第五种查询，通配符拆查询
		 * ?代表单个任意字符
		 * *代表多个任意字符
		 */
		
		// Query query = new WildcardQuery(new Term("title","luce*"));
		 //testSearcher(query);
		
		//第六种查询，模糊查询
		
		   /**
		    * 1.需要根据条件查询
		    * 
		    * 2.最大可编辑数，取值范围0，1，2
		    * 允许我的查询条件的值，可以错误几个字符
		    * 
		    */
		  // Query query = new FuzzyQuery(new Term("author","欧阳夏文某小"),1);
		
		
		   /**
		    * 第七种查询，短语查询
		    * @param slop:设置两个短语之间的最大间隔数，设置的间隔数越大，他能匹配的结果就越多，性能就越慢
		    * @param field:设置查找的字段
		    * @param terms:设置查找的短语，是一个可变长的数组
		    * (lucene4.4是用add方法来添加短语，6.6通过构造函数)
		    */
		  
		        //PhraseQuery query =new PhraseQuery(11,"title", new String[]{"lucene","全"});
		      
		        /**
		         * 第八种查询，布尔查询(类似于sql语句中的 and,or等查询方式)
		         * 
		         * (和lucene4.4使用方式有些区别，主要在于不能直接创建BooleanQuery，而是要创建BooleanQuery.Builder,)
		         */
		        BooleanQuery.Builder booQuery = new BooleanQuery.Builder();
		        
		         //范围查询id从0-10
		        
		          Query query1 =  IntPoint.newRangeQuery("id", 1, 10);
		          Query query2 = IntPoint.newRangeQuery("id", 5, 15);
		          
		          
		          booQuery.add(query1, Occur.MUST);
		          booQuery.add(query2,Occur.MUST_NOT);
		          
		           //使用build方法返回BooleanQuery对象
		          Query query = booQuery.build();
		    
		           testSearcher(query);
		  
		
		
		 

	}
	
	
	
	public static void testSearcher( Query  query) throws Exception{
		 IndexSearcher indexSearcher = LuceneUtil.getIndexSearcher();
		    
	      
	       
	      TopDocs topDocs = indexSearcher.search(query, 100);
	      
	      System.out.println("总记录数:"+topDocs.totalHits);
	       List<Article> list = new ArrayList<Article>();
	       //返回击中
	        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
	         
	         for(ScoreDoc scoreDoc : scoreDocs){
	        	 
	        	  Document document = indexSearcher.doc(scoreDoc.doc);
	        	  System.out.println(document.get("id"));
	        	  System.out.println(document.get("title"));
	        	  System.out.println(document.get("content"));
	        	  System.out.println(document.get("author"));
	        	  System.out.println(document.get("link"));
	         }
	 }


}
