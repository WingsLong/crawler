package crawler.utils;

public class Title {
	public String title;
	public int rowSpan;
	public int colSpan;
	
	public Title () {
		
	}
	
	public Title(String title, int rowSpan, int colSpan) {
		this.title = title;
		this.rowSpan = rowSpan;
		this.colSpan = colSpan;
	}
	
	public String toString() {
		return title;
	}
}
