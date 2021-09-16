//package OperatingSystem;
//
//import java.io.IOException;
//import java.util.Scanner;
//
//public class Main {
//	public static void main(String[] args) throws IOException {
//		FileScanner f = new FileScanner();
//		FileStore fs = new FileStore();
//		int proc, sour;
//		SourceManager sourceManager;
//		DeadlockDection d;
//		//¶ÁÈ¡ÎÄ¼þ
//		f.InputFileToIntegerArrayList("src/OperatingSystem/input.in");
//		proc = Integer.parseInt(f.arrayList.get(0).split("\\s+")[0]);
//		sour = Integer.parseInt(f.arrayList.get(0).split("\\s+")[1]);	
//		sourceManager = new SourceManager(proc, sour);
//		d = new DeadlockDection(proc, sour);
//		f.ArrayStore(sourceManager);
//		
////		d.dection();
////		d.printDeadlockQueue();
////		d.deadlockRecover_PartDeadlockProcess();
////		d.deadlockRecover_ALLDeadlockProcess();
////		d.deadlockRecover_SuspendDeadlockProcess();
////		d.printDeadlockQueue();
//		
//		System.err.println("---------------------------------------");
////		System.out.println(sourceManager.showMessage());
////		sourceManager.printDeadlockQueue();
////		sourceManager.requestSourceToEXEBanker();
//		sourceManager.deadlockDection();
//		System.out.println(sourceManager.showMessage());
////		System.out.println(sourceManager.showSystemVariable());
////		sourceManager.deadlockRecover_PartDeadlockProcess();
////		sourceManager.printSystemVariable();
////		fs.IntegerArrayToOutputFile(sourceManager);
////		System.out.println("Available");
////		for (int i = 0; i < sour; i++ ) {
////			System.out.print(sourceManager.getAvailable()[i] + " ");
////		}
////		System.out.println();
////		System.out.println("Request");
////		for (int i = 0; i < proc; i++) {
////			for (int j = 0; j < sour; j++) {
////				System.out.print(sourceManager.getRequest()[i][j] + " ");
////			}
////			System.out.println();
////		}
////		System.out.println("Allocation");
////		for (int i = 0; i < proc; i++) {
////			for (int j = 0; j < sour; j++) {
////				System.out.print(sourceManager.getAllocation()[i][j] + " ");
////			}
////			System.out.println();
////		}
//		
//	}
//}
