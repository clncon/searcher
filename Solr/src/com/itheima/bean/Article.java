package com.itheima.bean;

import org.apache.solr.client.solrj.beans.Field;

public class Article {
	
	 //注解为了在solr的manager-schema的文件内容保持一致
	 @Field(value="id")
	 private int id;
	 @Field(value="title")
	 private String title;
	 
	 @Field(value="name")
	 private String name;
	 
	 @Field(value="price")
	 private String price;
	 
	 @Field(value="content")
	 private String content;
	 
	 public Article(){}
	 
	 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	 
	 
	 

}
