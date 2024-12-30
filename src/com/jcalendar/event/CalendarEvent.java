/*********************************************************************
 * This software is Copyright (c) under LGPL license  see 
 * http://www.gnu.org/copyleft/lesser.html#SEC1 for more
 * information.
 * 
 * JCalendar is developed by Suman Mummaneni
 * CalendarEvent.java has been created on 20 Feb, 2010.
 * 
 *********************************************************************/
package com.jcalendar.event;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class CalendarEvent.
 * @author Suman
 */
public class CalendarEvent implements Serializable {

	/**
	 * Serial version UID for this class.
	 */
	private static final long serialVersionUID = 6707166341045532255L;
	
	/** The year. */
	private int day, month, year;
	
	/** The date. */
	private Date date;
	
	/**
	 * Instantiates a new calendar event.
	 * 
	 * @param source
	 *            the source
	 * @param month
	 *            the month
	 * @param year
	 *            the year
	 * @param day
	 *            the day
	 * @param date
	 *            the date
	 */
	public CalendarEvent(Object source, int month, int year, int day, Date date) {
		this.date=date;
		this.day=day;
		this.year=year;
		this.month=month;
	}

	/**
	 * Gets the day.
	 * 
	 * @return the day
	 */
	public int getDay(){
		return day;
	}
	
	/**
	 * Gets the month.
	 * 
	 * @return the month
	 */
	public int getMonth(){
		return month;
	}
	
	/**
	 * Gets the year.
	 * 
	 * @return the year
	 */
	public int getYear(){
		return year;
	}
	
	/**
	 * Gets the date.
	 * 
	 * @return the date
	 */
	public Date getDate(){
		return date;
	}
}
