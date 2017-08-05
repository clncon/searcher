package com.itheima.lucene.test;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LogDocMergePolicy;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.RAMDirectory;

import com.itheima.lucene.util.Contants;
import com.itheima.lucene.util.LuceneUtil;


/**
 * ����������Ż�
 * @author km
 *
 */
public class TestOptimise{

	
	   public void testOptimise1() throws Exception{
		   
		   //����ͨ��indexWriterConfig��������������Ż�������
		   //��lucene4.0֮��İ汾������������Զ����Ż�������
		   //�����ü���
		     
		    Directory directory = FSDirectory.open(Paths.get(Contants.File_Path));
		    
		     IndexWriterConfig indexWriterConfig = new IndexWriterConfig(LuceneUtil.getAnalyzer());
		     
		     
		      //��lucene���涼�����ã������Ϲ����ö���Ĳ������������á���
		     
		     /**
		      * 
		      * MergePolicy���úϲ�����
		      * 
		      */
		      
		        LogDocMergePolicy mergePolicy  = new LogDocMergePolicy();
		        
		         /**
		          * 
		          * 1.mergeFactor
		          * 
		          * �����ֵԽС�����ٵ��ڴ汻�����ڴ���������ʱ��������ʱ��Խ�죬����������ʱ��Խ������
		          * �����ֵԽ�󣬸�����ڴ汻�����ڴ���������ʱ��������ʱ��Խ��������������ʱ��Խ�졣��
		          * 
		          *  smaller value   2 < smaller<10
		          *  big    value   big>10
		          * 
		          */
		        
		          //���������ĺϲ����ӡ�����
		           mergePolicy.setMergeFactor(6);
		           
		           indexWriterConfig.setMergePolicy(mergePolicy);
		           
		           IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);
	   }
	   
	   
	   
	   /**
	    * �ų�ͣ�ôʣ��ų�ͣ�ã����ִʹ��˵����ʾͲ��Ὠ�������������ļ��ͻ��С�����������ͻ��ÿ졣��
	    * 
	    */
	   public void testOptimise2(){
		   
	   }
	   
	   
	    /**
	     * ���������ݷ������
	     * 
	     */
	    public void testOptimise3(){
	    	
	    }
	    

	     /**
	      * ʹ�������ŵ��ڴ����������Ч��
	     * @throws Exception 
	      */
	    public void testOptimise4() throws Exception{
	    	
	    	 //������Ӳ����
	    	 FSDirectory directory1 = FSDirectory.open(Paths.get(Contants.File_Path));
	    	 
	    	  IOContext context = new IOContext();
	    	 //�������ڴ���
	    	  Directory  directory = new RAMDirectory(directory1,context);
	    	  
	    	  
	    	  IndexReader  r = DirectoryReader.open(directory);
	    	  //����indexSearcher
	    	  IndexSearcher indexSearcher  = new IndexSearcher(r);
	    	  
	    	  String field[] = {"title"};
	    	  
	    	  QueryParser queryParser = new MultiFieldQueryParser(field, LuceneUtil.getAnalyzer());
	    	  
	    	  
	    	    Query query = queryParser.parse("����");
	    	    
	    	    TopDocs topDocs = indexSearcher.search(query, 100);
	    	    
	    	    
	    	    System.out.println(topDocs.totalHits);
	    	 
	    }
	    
	    
	     /**
	      * 5.ͨ����ѯ���������Ż�
	      */
	    public void testOptimise5(){
	    	
	    }
}
