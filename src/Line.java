import java.awt.Graphics2D;

public class Line {
	int[] offsets;
	String dir;
	int cur;
	int x;
	int y;
	int n;
	int size;
	String sign;
	boolean isDone = false;
	
	public Line(int x, int y, int size, int n, String dir, String sign){
		offsets = new int[n];
		for(int i = 0; i < n; i++){
			offsets[i] = i;
		}
		this.n = n;
		this.x = x;
		this.y = y;
		this.size = size;
		this.dir = dir;
		this.cur = 0;
		this.sign = sign;
	}
	
	public void draw(Graphics2D g){
		int[] p = getPoint(n);
		g.drawLine(x, y, p[0], p[1]);
	}
	
	public int[] getPoint(){
		int new_x = x;
		int new_y = y;
		if(dir == "x"){
			if(sign == "+"){
				new_x += (size/n)*cur;
			}
			else{
				new_x -= (size/n)*cur;
			}
		}
		else{
			if(sign == "+"){
				new_y += (size/n)*cur;
			}
			else{
				new_y -= (size/n)*cur;
			}
		}
		int[] arr = {new_x, new_y};
		return arr;
	}
	
	public int[] getPoint(int m){
		int new_x = x;
		int new_y = y;
		if(dir == "x"){
			if(sign == "+"){
				new_x += (size/n)*m;
			}
			else{
				new_x -= (size/n)*m;
			}
		}
		else{
			if(sign == "+"){
				new_y += (size/n)*m;
			}
			else{
				new_y -= (size/n)*m;
			}
		}
		int[] arr = {new_x, new_y};
		return arr;
	}
	
	public void increment(){
		cur += 1; 
		if(cur == n){
			isDone = true;
		}
	}
	
	public void reset(){
		cur = 0;
		isDone = false;
	}
	
}
