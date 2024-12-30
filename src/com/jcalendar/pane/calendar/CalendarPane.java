/*********************************************************************
 * This software is Copyright (c) under LGPL license  see 
 * http://www.gnu.org/copyleft/lesser.html#SEC1 for more
 * information.
 * 
 * JCalendar is developed by Suman Mummaneni
 * CalendarPane.java has been created on 27 April, 2010.
 * 
 *********************************************************************/
package com.jcalendar.pane.calendar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TimeZone;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import com.jcalendar.event.CalendarEvent;
import com.jcalendar.model.JCalModel;

/**
 * The Class CalendarPane.
 * @author Suman
 */
public class CalendarPane extends JPanel implements ListSelectionListener {

	/** Serial version UID for this container. */
	private static final long serialVersionUID = -7714183535212294537L;

	/** The monthlist. */
	private JComboBox<String> monthlist;
	
	/** The year. */
	private JSpinner year;
	
	/** The month. */
	private JTable month;
	
	/** The top panel. */
	private JPanel topPanel;
	
	/** The model. */
	private JCalModel model;
	
	/** The listeners. */
	private ArrayList<CalendarSelectionListener> listeners;

	/**
	 * Instantiates a new calendar pane.
	 */
	public CalendarPane() {
		listeners = new ArrayList<CalendarSelectionListener>();
		initPaneles();
		model = new JCalModel();
		initWidgets();
		setPanels();
	}
	
	public CalendarPane(TimeZone timeZone){
		listeners = new ArrayList<CalendarSelectionListener>();
		initPaneles();
		model = new JCalModel(timeZone);
		initWidgets();
		setPanels();
	}

	/**
	 * Initializes the paneles.
	 */
	protected void initPaneles() {
		topPanel = new JPanel();
	}

	/**
	 * Initializes the widgets.
	 */
	protected void initWidgets() {
		monthlist = new JComboBox<String>(model.getMonthList());
		monthlist.setSelectedIndex(model.getCurrentMonth());
		monthlist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> box = (JComboBox<String>) e.getSource();
				if (box.getSelectedIndex() != model.getCurrentMonth()) {
					model.reInitModel(box.getSelectedIndex());
					notifyListeners(new CalendarEvent(monthlist, model
							.getCurrentMonth(), model.getCurrentYear(), model
							.getCurrentDay(), model.getCurrentDate()));
				}
			}
		});
		monthlist.setEditable(false);
		year = new JSpinner(model.getYearModel());
		year.setEditor(new JSpinner.NumberEditor(year,"#"));
		year.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				SpinnerModel spinnerModel = year.getModel();
				int value = Integer
						.parseInt(spinnerModel.getValue().toString());
				if (value != model.getCurrentYear()) {
					model.reInitModel(monthlist.getSelectedIndex(), value);
					notifyListeners(new CalendarEvent(monthlist, model
							.getCurrentMonth(), model.getCurrentYear(), model
							.getCurrentDay(), model.getCurrentDate()));
				}
			}

		});
		// year.setValue(model.getCurrentYear());
		month = new JTable(model);
		month.setCellSelectionEnabled(true);
		month.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		month.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setColumnWidth();
		month.getSelectionModel().addListSelectionListener(this);
		month.getColumnModel().getSelectionModel().addListSelectionListener(this);
		setLayout(new BorderLayout());
	}

	/**
	 * Sets the column width.
	 */
	private void setColumnWidth() {
		TableColumn column = null;
		for (int i = 0; i < 7; i++) {
			column = month.getColumnModel().getColumn(i);
			column.setPreferredWidth(35);
		}
	}

	/**
	 * Sets the panels.
	 */
	protected void setPanels() {
		topPanel.add(monthlist);
		topPanel.add(year);
		add(topPanel, BorderLayout.NORTH);
		JScrollPane pane = new JScrollPane(month);
		add(pane, BorderLayout.CENTER);
	}

	/**
	 * Adds the calendar selection listener.
	 * 
	 * @param l
	 *            the l
	 */
	public void addCalendarSelectionListener(CalendarSelectionListener l) {
		listeners.add(l);
	}

	/**
	 * Removes the calendar selection listener.
	 * 
	 * @param l
	 *            the l
	 */
	public void removeCalendarSelectionListener(CalendarSelectionListener l) {
		if (listeners.contains(l)) {
			listeners.remove(l);
		}
	}

	/**
	 * Notify listeners.
	 * 
	 * @param e
	 *            the e
	 */
	private void notifyListeners(CalendarEvent e) {
		if (listeners != null && listeners.size() > 0) {
			for (int i = 0; i < listeners.size(); i++) {
				listeners.get(i).selectionChanged(e);
			}
		}
	}

	/**
	 * This provides the implementation of the <code>ListSelectionListener</code>
	 * Fires an event when user selects a date in the table view.
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {

		if (e.getValueIsAdjusting()) {
			return;
		}
		int row = month.getSelectedRow();
		int col = month.getSelectedColumn();
		String date = (String) month.getValueAt(row, col);
		if (date != null && !date.equals(" ") && !date.equals("")) {
			model.setDay(Integer.parseInt(date));
			notifyListeners(new CalendarEvent(month, model.getCurrentMonth(),
					model.getCurrentYear(), model.getCurrentDay(), model
							.getCurrentDate()));
		}
	}

	/**
	 * This method basically set the <code>JTable</code> grid on and off
	 * by default the grid is set to true. If user want to set it false
	 * this API can be used. Note: this API will also repaint the <code>
	 * JTable/<code>
	 * @param gridLook
	 */
	public void setTableGridLook(boolean gridLook){
		month.setShowGrid(gridLook);
		month.repaint();
	}	
}