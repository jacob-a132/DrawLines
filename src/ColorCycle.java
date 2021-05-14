import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class ColorCycle {
	ArrayList<Color> colors;
	int ind;
	int r = 0;
	int g = 0;
	int b = 0;
	
	public ColorCycle(){
		ind = 0;
		colors = new ArrayList<Color>();
		colors.add(Color.BLACK);
		colors.add(Color.BLUE);
		colors.add(Color.GREEN);
		colors.add(Color.YELLOW);
		colors.add(Color.MAGENTA);
		colors.add(Color.ORANGE);
		colors.add(Color.RED);
		colors.add(Color.CYAN);
		colors.add(Color.DARK_GRAY);
		colors.add(Color.GRAY);
		colors.add(Color.LIGHT_GRAY);
		colors.add(Color.PINK);
		colors.add(Color.WHITE);
		Collections.shuffle(colors);
	}
	
	public Color nextColor(){
		ind += 1;
		if(ind >= colors.size()){
			ind = 0;
			Collections.shuffle(colors);
		}
		return colors.get(ind);
	}
	
	public Color getColor(int n){
		int i = (ind+n)%colors.size();
		return colors.get(i);
	}
	
	public Color rgbNext(){
		int ran = (int) Math.floor(Math.random()*3)+1;
		
		if(ran == 1){
			r+=1;
			if(r>255){
				r = 0;
			}
		}
		if(ran == 2){
			g+=1;
			if(g>255){
				g = 0;
			}
		}
		else{
			b+=1;
			if(b>255){
				b = 0;
			}
		}
		return new Color(r,g,b);
	}
	
}
