package com.niit.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.niit.dao.BlogsDao;
import com.niit.dao.UserDao;
import com.niit.model.Blogs;
import com.niit.model.ErrorClazz;
import com.niit.model.User;

@RestController
public class BlogsController 
{
	@Autowired
	private BlogsDao blogsDao;
	
	@Autowired
	private UserDao userDao;
	
	public BlogsController()
	{
		System.out.println("BlogsController is Instantiated.");
	}
	
	@RequestMapping(value="/saveblog", method=RequestMethod.POST)
	public ResponseEntity<?> saveBlog(@RequestBody Blogs blogs, HttpSession session)
	{
		String username = (String)session.getAttribute("username");
		if(username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "Unauthorized Access.");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		User user = userDao.getUserByUsername(username);
		blogs.setPostedOn(new Date());
		blogs.setPostedBy(user);
		try
		{
			blogsDao.saveBlogs(blogs);
		}
		catch(Exception e)
		{
			ErrorClazz error = new ErrorClazz(8, "Unable to insert the blog details "+e.getMessage());
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Blogs>(blogs,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getblogs/{approved}", method=RequestMethod.GET)
	public ResponseEntity<?> getBlogs(HttpSession session, @PathVariable int approved)
	{
		String username = (String)session.getAttribute("username");
		if(username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "Unauthorized Access.");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		if(approved == 0)
		{
			User user = userDao.getUserByUsername(username);
			if(!user.getRole().equals("ADMIN"))
			{
				ErrorClazz error = new ErrorClazz(7, "Access Denied.");
				return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
			}
		}
		List<Blogs> blogs = blogsDao.getBlogs(approved);
		return new ResponseEntity<List<Blogs>>(blogs, HttpStatus.OK);
	}
}
