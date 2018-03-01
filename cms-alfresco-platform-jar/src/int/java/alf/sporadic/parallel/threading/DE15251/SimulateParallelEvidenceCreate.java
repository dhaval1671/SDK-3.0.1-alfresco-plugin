package alf.sporadic.parallel.threading.DE15251;

public class SimulateParallelEvidenceCreate {

	public static void main(String[] args) {

		for(int i=0; i<24; i++){
			CreateEvidenceThread cet = new CreateEvidenceThread(i);
			Thread t1 = new Thread(cet);
			t1.start();
			System.out.println("i from for loop :" + i);
		}
		
		
	}

}
