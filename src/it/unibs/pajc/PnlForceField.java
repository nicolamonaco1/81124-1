package it.unibs.pajc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.function.Function;

public class PnlForceField extends JPanel implements MouseMotionListener {
    public PnlForceField() {
        this.addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();

        //fillArrow(g2d, w/2, h/2, 100, angolo);

        //paintForceField(w, h, g2d);

        g2d.translate(w/2, h/2);
        g2d.scale(1, -1);
        g2d.drawLine(-w/2, 0, w/2, 0);
        g2d.drawLine(0, -h/2, 0, h/2);
        g2d.scale(w / (Math.PI*4), h/2.2);
        plotFunction(g2d, -2*Math.PI, 2*Math.PI, 0.05, x->Math.sin(x));
        g2d.setColor(Color.RED);
        plotFunction(g2d, -2*Math.PI, 2*Math.PI, 0.05, x-> 0.5 * (Math.sin(x)+Math.cos(2*x)));


        //disegnaGrafico(g2d, 0, 0);

    }

    private void plotFunction(Graphics2D g2d, double xmin, double xmax, double dx, Function<Double, Double> f) {
        for(double x = xmin; x <= xmax; x+=dx){
            double y = f.apply(x);
            circle(g2d, x, y, 0.05f);
        }
    }

    private void circle (Graphics2D g2, double x, double y, double radius){
        g2.fill(new Ellipse2D.Double(x-radius, y-radius, radius*2, radius*2));
    }

    /*private void disegnaGrafico (Graphics g2, int x, int y){
        AffineTransform at2 = new AffineTransform();

        at2.translate(getWidth()/2, getHeight()/2);

    }*/

    private void paintForceField(int w, int h, Graphics2D g2d) {
        for(int i = 0; i< w; i+=30){
            for(int j = 0; j< h; j+=20){
                float angolo = (float) Math.atan2(mousePos.y - j, mousePos.x - i);
                float dx = mousePos.x - i ;
                float dy = mousePos.y - j ;

                float tinta = (dx*dx + dy*dy) / (w * w + h * h);
                g2d.setColor(Color.getHSBColor(tinta, 1f, 1f));
                fillArrow(g2d, i, j, 40, angolo);
            }
        }
    }

    protected void fillArrow(Graphics2D g2d, int x, int y, int size, float ang) {
        Path2D path = new Path2D.Float();
        path.moveTo(size/2, 0);
        path.lineTo(-size/2, -size/4);
        path.lineTo(-size/6, 0);
        path.lineTo(-size/2, size/4);
        path.closePath();

        AffineTransform at = new AffineTransform();
        at.translate(x, y);
        at.rotate(ang);

        g2d.fill(at.createTransformedShape(path));
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    private Point mousePos = new Point(0,0);
    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos = e.getPoint();
        repaint();

    }
}
