package b.shapes;

public class Test_line {

	public static void main(String[] args) {
		Line line = new Line (3);
		line.print();
		System.out.println("===============");
		Line wline = new Wline(3,4);
		wline.print();
	}

}
