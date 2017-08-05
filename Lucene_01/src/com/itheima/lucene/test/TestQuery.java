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
	  
		
		  //��һ�ֲ�ѯTermQuery
		
//		  Query query = new TermQuery(new Term("author","����"));
//		    testSearcher(query);
		 
		 //�ڶ��ֲ�ѯ �ַ�����ѯ
		
		//String fields []={"author"};
		//
		//QueryParser queryParser=new MultiFieldQueryParser(LuceneUtils.getMatchVersion(),fields,LuceneUtils.getAnalyzer());
		//Query query=queryParser.parse("�ϼ���");
		
		
		//�����ֲ�ѯ����ѯ����
		
//		 Query query = new MatchAllDocsQuery();		 
//		  testSearcher(query);
		
		 
		
		 //�����ֲ�ѯ����Χ��ѯ,����ʹ�ôβ�ѯ�����������(lucene6.6��֧�ֹ���)
		/**
		 *lucene4.4ͨ��ʹ��NumericRangeQuery������Χ��ѯ
		 */
		
		//Query query = IntPoint.newRangeQuery("id", 1, 10);
		//testSearcher(query);
		
		
		/**
		 * �����ֲ�ѯ��ͨ������ѯ
		 * ?�����������ַ�
		 * *�����������ַ�
		 */
		
		// Query query = new WildcardQuery(new Term("title","luce*"));
		 //testSearcher(query);
		
		//�����ֲ�ѯ��ģ����ѯ
		
		   /**
		    * 1.��Ҫ����������ѯ
		    * 
		    * 2.���ɱ༭����ȡֵ��Χ0��1��2
		    * �����ҵĲ�ѯ������ֵ�����Դ��󼸸��ַ�
		    * 
		    */
		  // Query query = new FuzzyQuery(new Term("author","ŷ������ĳС"),1);
		
		
		   /**
		    * �����ֲ�ѯ�������ѯ
		    * @param slop:������������֮���������������õļ����Խ������ƥ��Ľ����Խ�࣬���ܾ�Խ��
		    * @param field:���ò��ҵ��ֶ�
		    * @param terms:���ò��ҵĶ����һ���ɱ䳤������
		    * (lucene4.4����add��������Ӷ��6.6ͨ�����캯��)
		    */
		  
		        //PhraseQuery query =new PhraseQuery(11,"title", new String[]{"lucene","ȫ"});
		      
		        /**
		         * �ڰ��ֲ�ѯ��������ѯ(������sql����е� and,or�Ȳ�ѯ��ʽ)
		         * 
		         * (��lucene4.4ʹ�÷�ʽ��Щ������Ҫ���ڲ���ֱ�Ӵ���BooleanQuery������Ҫ����BooleanQuery.Builder,)
		         */
		        BooleanQuery.Builder booQuery = new BooleanQuery.Builder();
		        
		         //��Χ��ѯid��0-10
		        
		          Query query1 =  IntPoint.newRangeQuery("id", 1, 10);
		          Query query2 = IntPoint.newRangeQuery("id", 5, 15);
		          
		          
		          booQuery.add(query1, Occur.MUST);
		          booQuery.add(query2,Occur.MUST_NOT);
		          
		           //ʹ��build��������BooleanQuery����
		          Query query = booQuery.build();
		    
		           testSearcher(query);
		  
		
		
		 

	}
	
	
	
	public static void testSearcher( Query  query) throws Exception{
		 IndexSearcher indexSearcher = LuceneUtil.getIndexSearcher();
		    
	      
	       
	      TopDocs topDocs = indexSearcher.search(query, 100);
	      
	      System.out.println("�ܼ�¼��:"+topDocs.totalHits);
	       List<Article> list = new ArrayList<Article>();
	       //���ػ���
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
