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
		 
		//创建Document对象
		   
		    Document document = new Document();
		    
		    //创建字段
		    
		     int id  = article.getId();
		    IntPoint idPoint = new IntPoint("id",id);
		    StoredField stroField  = new StoredField("id",id);
		    /**
		     * 使用lucene4.4以上版本会有此问题
		     *NumericDocValuesField,专门用来在搜索的时候进行排序使用的。在这里是通过时间排序。
		     *SearchUtil中getScoreDocsByPerPageAndSortField()方法。不add这个NumericD
		     *ocValuesField，搜索时会报错。unexpected docvalues type NONE for field 'modified'
		     *(expected=NUMERIC). Use UninvertingReader or index with docvalues.
		     * 
		     */   
		    NumericDocValuesField numericDocvalueField = new NumericDocValuesField("id",(long)id);
		    TextField content = new TextField("content", article.getContent(), Store.YES);
		    TextField title = new TextField("title", article.getTitle(), Store.YES);
		    StringField link = new StringField("link", article.getLink(), Store.YES);
		    StringField author = new StringField("author", article.getAuthor(), Store.YES);
           
		    
		      //设置title权重值，默认为1f
		      //title.setBoost(4f);
		     
		    //将字段存入到document中
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
