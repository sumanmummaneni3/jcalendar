/*********************************************************************
 * This software is Copyright (c) under LGPL license  see 
 * http://www.gnu.org/copyleft/lesser.html#SEC1 for more
 * information.
 * 
 * JCalendar is developed by Suman Mummaneni
 * JCalModel.java has been created on  27 April, 2014
 * 
 *********************************************************************/
package com.jcalendar.pane.clock;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.font.TextLayout;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.TimeZone;

import javax.swing.JComponent;
import javax.swing.Timer;

import com.jcalendar.model.JCalModel;

/**
 * The Clock Panel Class.
 * 
 * @author Suman
 *
 */
public class Clock extends JComponent {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -6919428216154480035L;

	/** The radius. */
	private double cx, cy, diameter, radius;
	
	/** The radian. */
	private final double RADIAN = 180.0 / Math.PI;
	
	/** The time zone. */
	private TimeZone timeZone;
	
	/** The model. */
	private JCalModel model;
	
	/** The clock timer. */
	private Timer clockTimer;
	
	/** The size of the clock for layout. */
	private Dimension size = new Dimension();	

	/**
	 * Instantiates a new clock panel.
	 */
	public Clock() {
		setOpaque(false);
		resizeClock();
		setTimeZone(TimeZone.getDefault());
		model = new JCalModel(timeZone);
		size.setSize(200, 200);
	}

	/**
	 * Adds a Component listener that will rezise 
	 * the clock base on the panel size.
	 */
	private void resizeClock() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				calculateSize();
				repaint();
			}
		});
	}

	/**
	 * Starts a timer to update the clock.
	 */
	public void startClock() {
		clockTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		clockTimer.start();
	}
	
	/**
	 * Stop clock.
	 */
	public void stopClock(){
		clockTimer.stop();
		clockTimer = null;
	}

	/** 
	 * The overridden paint method. This method has the
	 * basic logic of drawing the clock. Do not modify 
	 * unless you need a different logic. 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		paintFace(g2);
		paintHands(g2);
	}

	/**
	 * Gets the time zone.
	 *
	 * @return the time zone
	 */
	public TimeZone getTimeZone() {
		return timeZone;
	}

	/**
	 * Sets the time zone.
	 *
	 * @param timeZone the new time zone
	 */
	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
		if(model!=null){
			model.setTimeZone(timeZone);
		}
	}

	/**
	 * Calculate size of clock based on <code>JPanel</code>.
	 */
	private void calculateSize() {
		Insets insets = getInsets();
		int width = getWidth() - insets.left - insets.right;
		int height = getHeight() - insets.top - insets.bottom;
		diameter = Math.min(width, height);
		cx = cy = radius = diameter / 2;
	}

	/**
	 * Paint face.
	 *
	 * @param g2 the <code>Graphics2D</code> instance.
	 */
	private void paintFace(Graphics2D g2) {
		Color clockBorder = new Color(255,228,196);
		g2.setPaint(clockBorder);
		g2.fill(new Ellipse2D.Double(0, 0, diameter, diameter));
//		Color dial = new Color(238,221,130);
		g2.setPaint(Color.YELLOW);
		g2.fill(new Ellipse2D.Double(20, 20, diameter - 40, diameter - 40));
		g2.setPaint(Color.BLACK);
		for (int i = 1; i <= 12; i++) {
			TextLayout tl = new TextLayout(Integer.toString(i), getFont(),
					g2.getFontRenderContext());
			Rectangle2D bb = tl.getBounds();
			double angle = (i * 30 - 90) / RADIAN;
			double x = (radius - 10) * Math.cos(angle) - bb.getCenterX() / 2;
			double y = (radius - 10) * Math.sin(angle) - bb.getCenterY() / 2;
			tl.draw(g2, (float) (cx + x), (float) (cy + y));
		}
	}

	/**
	 * Paint hands.
	 *
	 * @param g2 the <code>Graphics2D</code> instance.
	 */
	private void paintHands(Graphics2D g2) {
		
		model.setTime();//Need to update the time in Calendar.
		int hour = model.getHourOfDay();
		int minute = model.getMinute();
		int second = model.getSeconds();
		double angle = 0.0;

		angle = (hour % 12 * 30 + minute / 2) / RADIAN;
		paintHand(g2, 0.4, angle, 6, Color.GRAY);

		angle = (minute * 6 + second / 10) / RADIAN;
		paintHand(g2, 0.6, angle, 4, Color.GRAY);

		angle = second * 6 / RADIAN;
		paintHand(g2, 0.8, angle, 2, Color.GRAY);
	}

	/**
	 * Paint hand.
	 *
	 * @param g2 the <code>Graphics2D</code> instance.
	 * @param proportion the proportion to be repainted.
	 * @param angle the angle
	 * @param width the width
	 * @param color the color
	 */
	private void paintHand(Graphics2D g2, double proportion, double angle,
			float width, Color color) {
		double x = radius * proportion * Math.sin(angle);
		double y = -radius * proportion * Math.cos(angle);
		g2.setPaint(color);
		g2.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		g2.draw(new Line2D.Double(cx, cy, cx + x, cy + y));
	}
	
	/** 
	 * Overriding this method to support Layout Managers.
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	public Dimension getPreferredSize(){
		
		return size;
	}
	
	/**
	 * Overriding this method to support Layout Managers.
	 * @see javax.swing.JComponent#getMinimumSize()
	 */
	public Dimension getMinimumSize (){
		return size;
	}
}
