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
		 * 1.����һ��,�����ؼ���һ�����÷�Ҳ��һ���ġ���
		 * 2.���ǿ����˹���ȥ��Ԥ����÷�
		 * 3.�÷ָ������ؼ��������µ��г��ֵ�Ƶ�ʣ�������λ���й�ϵ����
		 * 
		 */
		testSearcher("��");
		
		
		/**
		 * 
		 *  seo:
		 *  �ٶȻ����Щ���ݻ�ȡ ��ȥ��������������
		 * 
		 * 
		 */

	}
	
	
	 public static void testSearcher(String keywords) throws Exception{
		 IndexSearcher indexSearcher = LuceneUtil.getIndexSearcher();
		    
	      
	       //������Ҫ���ڲ�ѯ���ֶ�����
	        String[] fields = {"title","content"};
	        //������ѯ�õ���
	        QueryParser queryParser = new MultiFieldQueryParser(fields, LuceneUtil.getAnalyzer());
	        //��ѯ���Ϲؼ��ֵ�����
	       Query query = queryParser.parse(keywords);
	      //��ʼ����
	      TopDocs topDocs = indexSearcher.search(query, 100);
	      
	      System.out.println("�ܼ�¼��:"+topDocs.totalHits);
	       List<Article> list = new ArrayList<Article>();
	       //���ػ���
	        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
	          //int result  = (start+rows)<(scoreDocs.length)?(start+rows):(scoreDocs.length);
	         //���start+rowsVSscoreDocs.length�Ļ������ؽ�Сֵ
	         for(ScoreDoc scoreDoc : scoreDocs){
	        	 
	        	  Document document = indexSearcher.doc(scoreDoc.doc);
	        	 System.out.println("�ĵ���ţ�id"+document.get("id")	+"====�÷�===="+scoreDoc.score);
	         }
	 }

}
