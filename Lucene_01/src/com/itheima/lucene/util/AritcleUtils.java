package com.itheima.lucene.util;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import com.itheima.lucene.domain.Article;

public class AritcleUtils {
	
	 public static Document articleToDocument(Article article){
		 
		//����Document����
		   
		    Document document = new Document();
		    
		    //�����ֶ�
		    
		     int id  = article.getId();
		    IntPoint idPoint = new IntPoint("id",id);
		    StoredField stroField  = new StoredField("id",id);
		    /**
		     * ʹ��lucene4.4���ϰ汾���д�����
		     *NumericDocValuesField,ר��������������ʱ���������ʹ�õġ���������ͨ��ʱ������
		     *SearchUtil��getScoreDocsByPerPageAndSortField()��������add���NumericD
		     *ocValuesField������ʱ�ᱨ��unexpected docvalues type NONE for field 'modified'
		     *(expected=NUMERIC). Use UninvertingReader or index with docvalues.
		     * 
		     */   
		    NumericDocValuesField numericDocvalueField = new NumericDocValuesField("id",(long)id);
		    TextField content = new TextField("content", article.getContent(), Store.YES);
		    TextField title = new TextField("title", article.getTitle(), Store.YES);
		    StringField link = new StringField("link", article.getLink(), Store.YES);
		    StringField author = new StringField("author", article.getAuthor(), Store.YES);
           
		    
		      //����titleȨ��ֵ��Ĭ��Ϊ1f
		      //title.setBoost(4f);
		     
		    //���ֶδ��뵽document��
		      document.add(idPoint);
		      document.add(stroField);
		      document.add(numericDocvalueField);
		      document.add(content);
		      document.add(title);
		      document.add(link);
		      document.add(author);
		return document;
		 
	 }

}
