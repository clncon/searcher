package com.itheima.lucene.helloWorld;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

public class Demo1 {

	
	
	   /**
	    * ʹ��IndexWrite����������д������
	    * Ctrl+T��ʾĳ����ļ̳нṹ
	 * @throws IOException 
	    */
	    @Test
	   public void CreateIndex() throws IOException{
		   
		    
		    //������ŵ�λ�ã������ڵ�ǰĿ¼��
		    Directory directory = FSDirectory.open(Paths.get("indexDir/"));
		    
		    //���Lucene�İ汾
		      Version version = Version.LUCENE_6_6_0;
		      
		    //����lucene�ķִ�������Ҫ���ڽ��зִʣ�����ʶ����ã��й�������һЩ��ǰû�У������ڳ��ȵĴ�
		       Analyzer analyzer = new StandardAnalyzer(); 
		    //��������д������
		       IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		       
		      //��������д�����
		     IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
		       
		      //����Document���󣬴洢����
		     
		      Document doc = new Document();
		      
		      //�����ֶ����ڴ洢����
		      
		       /**
		        * @param:name:�ֶ��� 
		        * @param:value:�ֶ�ֵ 
		        * @param:store:�Ƿ�洢 
		        */
		           
		         
		       int iD = 6;
		       Field id = new IntPoint("id",iD);
		       Field storeField = new StoredField("id", iD);
		       Field title = new StringField("title","ImportNew - רעJava & Android ��������",Store.YES);
		       Field content = new TextField("content","ImportNew ��һ��רע�� Java & Android ��������Ĳ���,ΪJava �� Android�������ṩ�м�ֵ�����ݡ�����:Android�������Ѷ��Java Web������������Java������ص�",Store.YES);
		   
		       
		       //���ֶμ��뵽doc��
		        doc.add(id);
		        doc.add(title);
		        doc.add(content);
		        doc.add(storeField);
		        
		        //��doc���󱣴浽��������
		        indexWriter.addDocument(doc);
		        
		        //�ر���
		        indexWriter.close();
		    
	   }
	    
	    
	    /**
	     * @throws IOException 
	     * 
	     */
	    @Test
	   public void SelectIndex() throws IOException{
		    
		     //������ŵ�λ�� 
		      Directory directory = FSDirectory.open(Paths.get("indexDir/"));
		    //���������Ķ�ȡ��
		      IndexReader indexReader = DirectoryReader.open(directory);
		      
		       //����һ�������Ĳ�������������������
		   IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		   
		    //����һ��������ѯ��api�������������
		   Term term = new Term("title","ImportNew - רעJava & Android ��������");
		   TermQuery termQuery = new TermQuery(term);
		   
		   //����������������
		     
		       //���ط���������ǰ100����¼
		      TopDocs topDocs =  indexSearcher.search(termQuery, 100);
		   
		       //��ӡ��ѯ���ļ�¼��
		      System.out.println("�ܼ�¼��:"+topDocs.totalHits);
		      
		      //�õ��÷��ĵ�����
		       ScoreDoc scoreDocs[] = topDocs.scoreDocs;
		    
		       //�������飬����һ������
		        for(ScoreDoc scoreDoc : scoreDocs){
		        	
		        	  int docID = scoreDoc.doc;
		        	  
		        	 //ȡ�ö�Ӧ���ĵ�����
		        	   Document document = indexSearcher.doc(docID);
		        	   
		        	   System.out.println(document.get("id"));
		        	   System.out.println(document.get("title"));
		        	   System.out.println(document.get("content"));
		        }
		   
	   }

}
