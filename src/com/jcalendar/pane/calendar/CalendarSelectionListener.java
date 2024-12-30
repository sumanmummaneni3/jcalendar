/*********************************************************************
 * This software is Copyright (c) under LGPL license  see 
 * http://www.gnu.org/copyleft/lesser.html#SEC1 for more
 * information.
 * 
 * JCalendar is developed by Suman Mummaneni
 * CalendarSelectionListener.java has been created on 27 April, 2014.
 * 
 *********************************************************************/
package com.jcalendar.pane.calendar;

import com.jcalendar.event.CalendarEvent;


/**
 * The listener interface for receiving calendarSelection events. The class that
 * is interested in processing a calendarSelection event can implements this
 * interface, and must registered with the <code>CalendarPane</code> using the
 * <code>addCalendarSelectionListener<code> method. When
 * the calendarSelection event occurs, that object's appropriate
 * method is invoked. 
 * 
 * Also see the following classes.
 * @see com.jcalendar.event.CalendarEvent
 * @see com.jcalendar.pane.calendar.CalendarPane
 * 
 * @author Suman
 */
public interface CalendarSelectionListener {

	/**
	 * Selection changed.
	 * 
	 * @param e
	 *            the e
	 */
	public void selectionChanged(CalendarEvent e);
}
