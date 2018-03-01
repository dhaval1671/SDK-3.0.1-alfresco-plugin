package alf.sporadic.bulk.operation.evidence.DE14915;

public class SimulateParallelEvidenceCreate {

	public static void main(String[] args) {

		for(int i=0; i<=100; i++){
			CreateEvidenceThread cet = new CreateEvidenceThread(i);
			Thread t1 = new Thread(cet);
			t1.start();
			System.out.println("i from for loop :" + i);
		}
		
		
	}

}
