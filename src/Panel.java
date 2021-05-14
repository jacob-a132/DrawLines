import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Panel extends JPanel implements ActionListener, KeyListener {
	Timer timer;
	Line l1;
	Line l2;
	Line l3;
	Line l4;
	int linethickness;
	ArrayList<ArrayList<DrawCommand>> old_line_cmds;
	ArrayList<DrawCommand> line_cmds;
	ArrayList<Line> lines;
	ColorCycle cc;
	Color color;
	String mode;
	int n;
	int time;
	boolean r;
	boolean screenSaver;
	
	public Panel(){
		
		screenSaver = true;
		
		cc = new ColorCycle();
		this.color = Color.BLACK;
		line_cmds = new ArrayList<DrawCommand>();
		old_line_cmds = new ArrayList<ArrayList<DrawCommand>>();
		r = screenSaver;
		mode = "time";
		time = 20;
		n = 30;
		linethickness = 1;
		
		if (screenSaver) {
			makeScreenSaver(25);
		}
		else {
			makeOneBox(n);
			addKeyListener(this);
		}
		
		timer = new Timer(time, this);
		timer.start();
	}
	
	public void makeOneBox(int n){
		lines = new ArrayList<Line>();
		int x = 300;
		int y = 100;
		
		int size = 0;
		while(size < 600){
			size += n;
		}
		size -= n;
		
		l1 = new Line(x,y,size,n,"x", "+");
		l2 = new Line(x+size,y,size,n,"y", "+");
		l3 = new Line(x+size,y+size,size,n,"x", "-");
		l4 = new Line(x,y+size,size,n,"y", "-");
		lines.add(l1);
		lines.add(l2);
		lines.add(l3);
		lines.add(l4);
	}

	public void makeScreenSaver(int n){
		lines = new ArrayList<Line>();
		int size = 100;
		for(int i = 0; i < 14; i++){
			
			int xx=(int)(i/2)*size*2;
			int xy=0;
			if(i%2==1){
				xy = size*2;
			}
			
			for (int j = 0; j < 2; j++) {
				int x = xx;
				int y = xy + 4*size*j;
				l1 = new Line(x,y,size,n,"x", "+");
				l2 = new Line(x+size,y,size,n,"y", "+");
				l3 = new Line(x+size,y+size,size,n,"x", "-");
				l4 = new Line(x,y+size,size,n,"y", "-");
				lines.add(l2);
				lines.add(l3);
				lines.add(l4);
				lines.add(l1);
				
				x = size+xx;
				y = xy + 4*size*j;
				l1 = new Line(x,y,size,n,"x", "+");
				l2 = new Line(x+size,y,size,n,"y", "+");
				l3 = new Line(x+size,y+size,size,n,"x", "-");
				l4 = new Line(x,y+size,size,n,"y", "-");
				lines.add(l3);
				lines.add(l4);
				lines.add(l1);
				lines.add(l2);
				
				x = xx;
				y = size+xy + 4*size*j;
				l1 = new Line(x,y,size,n,"x", "+");
				l2 = new Line(x+size,y,size,n,"y", "+");
				l3 = new Line(x+size,y+size,size,n,"x", "-");
				l4 = new Line(x,y+size,size,n,"y", "-");
				lines.add(l4);
				lines.add(l1);
				lines.add(l2);
				lines.add(l3);
				
				x = size+xx;
				y = size+xy + 4*size*j;
				l1 = new Line(x,y,size,n,"x", "+");
				l2 = new Line(x+size,y,size,n,"y", "+");
				l3 = new Line(x+size,y+size,size,n,"x", "-");
				l4 = new Line(x,y+size,size,n,"y", "-");
				lines.add(l1);
				lines.add(l2);
				lines.add(l3);
				lines.add(l4);
			}
		
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		for(int i = 0; i < lines.size(); i+=4){
			if(!lines.get(i).isDone){
				addLine(lines.get(i), lines.get(i+1));
			}
			else if(!lines.get(i+1).isDone){
				addLine(lines.get(i+1), lines.get(i+2));
			}
			else if(!lines.get(i+2).isDone){
				addLine(lines.get(i+2), lines.get(i+3));
			}
			else if(!lines.get(i+3).isDone){
				addLine(lines.get(i+3), lines.get(i));
			}
			else{
				if(r){
					reset();
				}
			}
		}
		
		repaint();
	}
	
	public void addLine(Line l1, Line l2){
		int[] p1 = l1.getPoint();
		int[] p2 = l2.getPoint(l1.cur+1);
		line_cmds.add(new DrawCommand(p1[0], p1[1], p2[0], p2[1], this.color));
		l1.increment();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(linethickness));
		
		for(Line l: lines){
			l.draw(g2d);
		}
		
		for(ArrayList<DrawCommand> a: old_line_cmds){
			for(DrawCommand c: a){
				c.draw(g2d);
			}
		}
		
		for(DrawCommand c: line_cmds){
			c.draw(g2d);
		}
		
		g2d.drawString("number of lines : " + n, 20, 40);
		g2d.drawString("time between draws : " + time + " ms", 20, 60);
		g2d.drawString("line thickness : " + linethickness, 20, 80);
		g2d.drawString("current mode : " + mode, 20, 100);
		
		g2d.dispose();
		requestFocus();
	}
	
	public void reset(){
		for(Line l : lines){
			l.reset();
		}
		if(screenSaver){
			forScreenSaver();
		}
		else{
			line_cmds = new ArrayList<DrawCommand>();
			// timer.stop();
		}
	}
	
	public void forScreenSaver(){
		cutList();
		this.color = cc.nextColor();
	}
	
	public void cutList(){
		old_line_cmds = new ArrayList<ArrayList<DrawCommand>>();
		old_line_cmds.add(line_cmds);
		line_cmds = new ArrayList<DrawCommand>();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == ' '){
			reset();
		}
		if(e.getKeyChar() == 'r'){
			r = !r;
		}
		if(e.getKeyChar() == 't'){
			mode = "time";
		}
		else if(e.getKeyChar() == 'l'){
			mode = "line";
		}
		else if(e.getKeyChar() == 'n'){
			mode = "n";
		}
		else if(mode.equals("time")){
			if(Character.isDigit(e.getKeyChar())){
				if(e.getKeyChar() == '0'){
					time = 1;
				}
				else{
					time = Character.getNumericValue(e.getKeyChar())*10;
				}
				timer.stop();
				timer = new Timer(time, this);
				timer.start();
				reset();
			}
		}
		else if(mode.equals("n")){
			if(Character.isDigit(e.getKeyChar())){
				if(e.getKeyChar() == '0'){
					n = 100;
					makeOneBox(n);
				}
				else{
					n = Character.getNumericValue(e.getKeyChar())*10;
					makeOneBox(n);
				}
				reset();
			}
		}
		else if(mode.equals("line")){
			if(Character.isDigit(e.getKeyChar())){
				linethickness = Character.getNumericValue(e.getKeyChar());
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}
