package com.niit.dao;

import java.util.List;

import com.niit.model.Blogs;

public interface BlogsDao 
{
	void saveBlogs(Blogs blogs);
	//return list of blogs waiting for approval(0) / list of blogs approved(1)
	//getBlogs(0) -> list of blogs waiting for approval
	//getBlogs(10 -> list of blogs approved
	List<Blogs> getBlogs(int approved);   //value of approved = 0 or 1
}
