package g.objectMethods;

public class TestPoint1 {

	public static void main(String[] args) {
		
		Point p1 = new Point(3,5);
		Point p2 = new Point(3,5);
		
		System.out.println(p1);
		
		System.out.println(p1.hashCode());
		System.out.println(p1.equals(p2));
		
		
		
	}

}
