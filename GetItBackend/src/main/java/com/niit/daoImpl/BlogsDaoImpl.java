package com.niit.daoImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.niit.dao.BlogsDao;
import com.niit.model.Blogs;

@Repository
@Transactional
public class BlogsDaoImpl implements BlogsDao 
{
	@Autowired
	private SessionFactory sessionFactory;
	
	public void saveBlogs(Blogs blogs) 
	{
		Session session = sessionFactory.getCurrentSession();
		session.save(blogs);
	}

	public List<Blogs> getBlogs(int approved) 
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Blogs where approved = "+approved);
		return query.list();
	}
}
