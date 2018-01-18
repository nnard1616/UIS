package provided;

public class Driver {

	public static void main(String[] args){
		GUI gui = new GUI();
		MaxContigSumFinder m = new MaxContigSumFinder();
		gui.registerModel(m);
	}
	
}