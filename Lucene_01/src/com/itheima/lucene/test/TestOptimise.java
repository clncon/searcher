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
 * 对索引库的优化
 * @author km
 *
 */
public class TestOptimise{

	
	   public void testOptimise1() throws Exception{
		   
		   //可以通过indexWriterConfig这个对象来进行优化。。。
		   //在lucene4.0之后的版本会对索引进行自动的优化。。。
		   //改配置即可
		     
		    Directory directory = FSDirectory.open(Paths.get(Contants.File_Path));
		    
		     IndexWriterConfig indexWriterConfig = new IndexWriterConfig(LuceneUtil.getAnalyzer());
		     
		     
		      //在lucene里面都是配置，都是拖过设置对象的参数来进行配置。。
		     
		     /**
		      * 
		      * MergePolicy设置合并规则
		      * 
		      */
		      
		        LogDocMergePolicy mergePolicy  = new LogDocMergePolicy();
		        
		         /**
		          * 
		          * 1.mergeFactor
		          * 
		          * 当这个值越小，更少的内存被运用在创建索引的时候，搜索的时候越快，创建索引的时候越慢。。
		          * 当这个值越大，更多的内存被运用在创建索引的时候，搜索的时候越慢，创建索引的时候越快。。
		          * 
		          *  smaller value   2 < smaller<10
		          *  big    value   big>10
		          * 
		          */
		        
		          //设置索引的合并因子。。。
		           mergePolicy.setMergeFactor(6);
		           
		           indexWriterConfig.setMergePolicy(mergePolicy);
		           
		           IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);
	   }
	   
	   
	   
	   /**
	    * 排除停用词，排除停用，被分词过滤掉，词就不会建立索引，索引文件就会变小，这样索引就会变得快。。
	    * 
	    */
	   public void testOptimise2(){
		   
	   }
	   
	   
	    /**
	     * 将索引数据分区存放
	     * 
	     */
	    public void testOptimise3(){
	    	
	    }
	    

	     /**
	      * 使用索引放到内存中提高索引效率
	     * @throws Exception 
	      */
	    public void testOptimise4() throws Exception{
	    	
	    	 //索引在硬盘中
	    	 FSDirectory directory1 = FSDirectory.open(Paths.get(Contants.File_Path));
	    	 
	    	  IOContext context = new IOContext();
	    	 //索引在内存中
	    	  Directory  directory = new RAMDirectory(directory1,context);
	    	  
	    	  
	    	  IndexReader  r = DirectoryReader.open(directory);
	    	  //创建indexSearcher
	    	  IndexSearcher indexSearcher  = new IndexSearcher(r);
	    	  
	    	  String field[] = {"title"};
	    	  
	    	  QueryParser queryParser = new MultiFieldQueryParser(field, LuceneUtil.getAnalyzer());
	    	  
	    	  
	    	    Query query = queryParser.parse("张总");
	    	    
	    	    TopDocs topDocs = indexSearcher.search(query, 100);
	    	    
	    	    
	    	    System.out.println(topDocs.totalHits);
	    	 
	    }
	    
	    
	     /**
	      * 5.通过查询条件进行优化
	      */
	    public void testOptimise5(){
	    	
	    }
}
