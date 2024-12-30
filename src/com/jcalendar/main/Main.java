/*********************************************************************
 * This software is Copyright (c) under LGPL license  see 
 * http://www.gnu.org/copyleft/lesser.html#SEC1 for more
 * information.
 * 
 * JCalendar is developed by Suman Mummaneni
 * Main.java has been created on 20 Feb, 2010.
 * 
 *********************************************************************/
package com.jcalendar.main;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jcalendar.event.CalendarEvent;
import com.jcalendar.pane.calendar.CalendarPane;
import com.jcalendar.pane.calendar.CalendarSelectionListener;
import com.jcalendar.pane.clock.Clock;

/**
 * The Class Main.
 */
public class Main {

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		CalendarPane pane = new CalendarPane();
		pane.addCalendarSelectionListener(new CalendarSelectionListener() {
			@Override
			public void selectionChanged(CalendarEvent e) {
				System.out.println("Got event: \n Day:" + e.getDay()
						+ "Month: " + e.getMonth() + "Year: " + e.getYear());
			}

		});
		JPanel main = new JPanel(new BorderLayout());
		Clock clock = new Clock();
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.add(pane, BorderLayout.CENTER);
		main.add(clock, BorderLayout.EAST);
		main.repaint();
		frame.setContentPane(main);
		frame.setTitle("Calendar with Clock");
		frame.setBounds(200, 200, 464, 260);
		frame.setVisible(true);
		clock.startClock();
	}
}
