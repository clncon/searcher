package com.itheima.lucene.util;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexCommit;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneUtil {
	
	
	   private static Directory directory=null;
	   private static IndexWriterConfig indexWriterConfig=null;
	   private static Analyzer analyzer = null;
	  // private static IndexReader  indexReader = null;
	   
	   static {
		    try {
				directory = FSDirectory.open(Paths.get(Contants.File_Path));
				analyzer = new StandardAnalyzer();
				
		    }catch(Exception e){
		    	e.printStackTrace();
		    }
	   
	   }
	 
	
	    /**
	     * 返回一个IndexWriter
	     * @return
	     * @throws IOException
	     */
	   public static IndexWriter getIndexWriter() throws IOException{
		   IndexWriterConfig indexWriterConfig= new IndexWriterConfig(analyzer);
		   IndexWriter indexWriter  = new IndexWriter(directory, indexWriterConfig);
		   return indexWriter;
	   }
	   /**
	    * 
	    * @return
	    * @throws IOException
	    */
	   public static IndexSearcher getIndexSearcher() throws IOException{
		   IndexReader indexReader = DirectoryReader.open(directory);
		  IndexSearcher  indexSearcher = new IndexSearcher(indexReader);
		   return indexSearcher;
	   }
	   
	   
	    /**
	     * 返回一个Analyzer
	     * @return
	     */
	   public static Analyzer getAnalyzer(){
		   return analyzer;
	   }

}
