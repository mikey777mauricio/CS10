
public class TeleportTest {

	public static void main(String[] args) {
		//define teleporter as Teleporter
		Teleporter t1 = new Teleporter(1.0,1.0,5,5); // new teleporter
		// t1 defined as teleporter so it can call teleport() method without issue
		t1.teleport(); //calls teleport method, this is ok - jumps somewhere randomly on the screen
		t1.step(); //uses Blob step method (Teleporter doesn't implement step)
		
		//define teleporter as Blob
		Blob t2 = new Teleporter(5.0, 5.0, 5, 5); // teleporter is a blob but allocated a teleporter
		((Teleporter)t2).teleport(); //need to cast to Teleporter because declared as Blob
		// need to cast t2 as a teleporter and then call teleport
		t2.step(); //no need to cast -- uses Blob's step method

		// teleport() call would crash if t2 not "new teleporter"
		//step () from base class (blob) because step() not defined in teleporter

		Blob t3 = new Blob(); //normal Blob  // defined as 'new blob'
		t3.step(); //this is ok
		//t3.teleport(); //not allowed, Blob has no teleport method
	}

}
