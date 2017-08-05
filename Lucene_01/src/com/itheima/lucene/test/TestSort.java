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
	  * ��������
	  * @param args
	  * @throws Exception
	  */
	public static void main(String[] args) throws Exception {
		
		
		  testSearcher("����");

	}
	
	
	public static void testSearcher(String keywords) throws Exception{
		 IndexSearcher indexSearcher = LuceneUtil.getIndexSearcher();
		    
	      
	       //������Ҫ���ڲ�ѯ���ֶ�����
	        String[] fields = {"title"};
	        //������ѯ�õ���
	        QueryParser queryParser = new MultiFieldQueryParser(fields, LuceneUtil.getAnalyzer());
	        //��ѯ���Ϲؼ��ֵ�����
	       Query query = queryParser.parse(keywords);
	      //��ʼ����
	       
	         
	        //���������ֶΣ����� 
	        // SortField sortField = new SortField("id", Type.INT);
	         
	       //���������ֶΣ����� 
	           SortField sortField = new SortField("id", Type.INT,true);
	       //����һ��Sort����
	         Sort sort = new Sort();
	         
	          //�����������
	           sort.setSort(sortField);
	      TopDocs topDocs = indexSearcher.search(query, 100,sort);
	      
	      System.out.println("�ܼ�¼��:"+topDocs.totalHits);
	       List<Article> list = new ArrayList<Article>();
	       //���ػ���
	        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
	          //int result  = (start+rows)<(scoreDocs.length)?(start+rows):(scoreDocs.length);
	         //���start+rowsVSscoreDocs.length�Ļ������ؽ�Сֵ
	         for(ScoreDoc scoreDoc : scoreDocs){
	        	 
	        	  Document document = indexSearcher.doc(scoreDoc.doc);
	        	 System.out.println(document.get("id"));
	         }
	 }

}
