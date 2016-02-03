package ca.cmpt213.as3.histogram;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.Icon;

/**
 * An Icon which displays its associated Histogram. Draws it to 
 * fill in the prescribed area, and listing the range and count
 * for each bar.
 */
public class HistogramIcon implements Icon{
	private Histogram histogram;
	private int height;
	private int width;
	
	public HistogramIcon(Histogram histogram, int width, int height) {
		this.histogram = histogram;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public int getIconHeight() {
		return height;
	}
	@Override
	public int getIconWidth() {
		return width;
	}
	
	private final int PADDING_WIDTH = 10;
	private final int TEXT_HEIGHT = 15;
	private final int BARTEXT_OFFSET = 10;
	private final int BAR_SPACE = 5;
	private final int SPACE_TOP = PADDING_WIDTH + BARTEXT_OFFSET + TEXT_HEIGHT;
	private final int SPACE_BOTTOM = PADDING_WIDTH + 2 * TEXT_HEIGHT;
	private final int SPACE_SIDES = PADDING_WIDTH;
	private final int NUM_ROWS_AXIS_TEXT = 2;
	
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g;
		
		clearIcon(x, y, g2d);
		
		int originX = x + SPACE_SIDES;
		int originY = y + height - SPACE_BOTTOM;
		int graphTop = y + SPACE_TOP;
		int graphRight = x + width - SPACE_SIDES;

		drawAxis(g2d, originX, originY, graphTop, graphRight);

		if (histogram.getMaxBarCount() == 0) {
			return;
		}
		
		double oneCountHeight = (originY - graphTop) / (double) histogram.getMaxBarCount();
		int totalWidthPerBar = (graphRight - originX) / histogram.getNumberBars();
		int barWidth = totalWidthPerBar - BAR_SPACE;

		// Process each bar (draw bar and text)
		int countBars = 0;
		Iterator<Histogram.Bar> bars = histogram.iterator();
		while (bars.hasNext()) {
			// Get data:
			Histogram.Bar bar = bars.next();
			
			int barLeft = originX + BAR_SPACE + countBars * totalWidthPerBar;

			// Draw the bar:
			g2d.setColor(Color.BLUE);
			int barHeight = (int)(oneCountHeight * bar.getCount());
			int barTop = originY - barHeight;
			int barMiddleX = barLeft + barWidth / NUM_ROWS_AXIS_TEXT;
			g2d.fill(new Rectangle2D.Double(barLeft, barTop, barWidth, barHeight));
			
			// Print the range:
			g2d.setColor(Color.BLACK);
			String range = "" + bar.getRangeMin();
			int textHeightOffset = TEXT_HEIGHT * (1 + countBars % NUM_ROWS_AXIS_TEXT);
			drawStringCentredOnX(g2d, range, barMiddleX, originY + textHeightOffset);
			
			// Print bar height:
			String heightText = "" + bar.getCount();
			drawStringCentredOnX(g2d, heightText, barMiddleX, barTop - BARTEXT_OFFSET);
			
			// Move on:
			countBars++;
		}
	}

	private void drawAxis(Graphics2D g2d, int left, int bottom, int top, int right) {
		Point2D origin = new Point2D.Double(left, bottom);
		Point2D endX = new Point2D.Double(right, bottom);
		Point2D endY = new Point2D.Double(left, top);
		
		g2d.setColor(Color.BLACK);
		g2d.draw(new Line2D.Double(origin, endX));
		g2d.draw(new Line2D.Double(origin, endY));
	}

	private void clearIcon(int x, int y, Graphics2D g2d) {
		g2d.setBackground(Color.WHITE);
		g2d.clearRect(x, y, getIconWidth(), getIconHeight());
	}
	

	private void drawStringCentredOnX(Graphics2D g2d, String message, int middle, int bottom) {
		Font font = g2d.getFont();
		FontRenderContext context = g2d.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(message, context);
		double width = bounds.getWidth();
		
		int left = (int) (middle - width/2);
		g2d.drawString(message, left, bottom);		
	}
}



