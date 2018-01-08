package com.niit.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.niit.dao.NotificationDao;
import com.niit.model.ErrorClazz;
import com.niit.model.Notification;

@Controller
public class NotificationController 
{
	@Autowired
	private NotificationDao notificationDao;
	
	@RequestMapping(value="/getnotification/{viewed}", method=RequestMethod.GET)
	public ResponseEntity<?> getNotification(HttpSession session, @PathVariable int viewed)
	{
		String username = (String)session.getAttribute("username");
		if(username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "Unauthorized Access.");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		List<Notification> allNotifications = notificationDao.getNotification(username, viewed);
		return new ResponseEntity<List<Notification>>(allNotifications, HttpStatus.OK);
	}
	
	@RequestMapping(value="/updatenotificationviewed/{notificationId}", method=RequestMethod.PUT)
	public ResponseEntity<?> updateNotificationViewed(HttpSession session, @PathVariable int notificationId)
	{
		String username = (String)session.getAttribute("username");
		if(username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "Unauthorized Access.");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		Notification updateNotificationViewed = notificationDao.updateNotificationViewed(notificationId);
		return new ResponseEntity<Notification>(updateNotificationViewed, HttpStatus.OK);
	}
}
