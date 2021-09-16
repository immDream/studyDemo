package OperatingSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * �������
 * @author ׯ��
 * @date 2021-8-23
 */
public class DeadlockDection {
	//�ļ����룬�������
	
	int proc;			//������
	int sour;			//��Դ��
	
	int[] Available;	// �ɻ����Դ��
	int[][] Allocation;	// �ѷ������Դ��
	int[][] Need;		// ������Ҫ����Դ��
	int[][] Request;	// �����������Դ��
	int[][] Max;			//�������������
	
	int[] deadlock;		// ��������
	int deadlockCount = 0;		// �������н��̸���
	
	boolean[] sign;		// ������� true������������

	
	/**
	 * ���캯��
	 * @param proc ������ 
	 * @param sour ��Դ��
	 */
	DeadlockDection(int proc, int sour) {
		if(proc <= 0 || sour <= 0) {
			System.err.println("���󴴽�ʧ��!");
			return;
		}
		this.proc = proc;
		this.sour = sour;
		this.Available = new int[sour];
		this.Max = new int[proc][sour];			//�������������
		this.Allocation = new int[proc][sour];
		this.Need = new int[proc][sour];
		this.Request = new int[proc][sour];
		this.sign = new boolean[proc];
		this.deadlock = new int[proc];
	}
	

	/**
	 * �������
	 * 1�����allocation��ȫ0�Ľ���
	 * 2��work = available
	 * 3��Ѱ�ҽ������ܹ�����request < work�� 
	 * 4������������̲���work += allocation���ظ�3
	 */
	protected boolean dection() {
		if(proc < 0 || sour < 0) return false;
		//ÿ�μ���������ж��Ƚ�������������0
		deadlockCount = 0;
		
		for(int i = 0; i < proc; i++) {
			int count = 0;	// ���һ�����̵���Դ���Ƿ�ȫΪ0
			for(int j = 0; j < sour; j++) {
				if(Allocation[i][j] == 0) count++;
			}
			if(count == sour) sign[i] = true;
		}
		
		int[] work = new int[sour];
		// ʹwork == available
		for(int i = 0; i < sour; i++) {
			work[i] = Available[i];
		}
		
//		for (int k = 0; k < proc; k++) {
//			System.err.print(" " + sign[k]);
//		}
//		System.out.println();
		
		// Ѱ��δ��ǵĽ��̲����Ա�ǣ�next����   i:����
		for(int i = 0; i < proc; i++) {
			if(sign[i]) {
				continue;
			}
//			System.out.println("---------------------------------");
//			System.err.println("i = " + i);
			
			// Ѱ��request�н����������Դ < work �����
			sign[i] = true;	// �����ܹ������
			for(int j = 0; j < sour; j++) {
//				System.out.println("__________");
				// ���ʧ�� ��work����
				if(Request[i][j] > work[j]) {
//					System.out.println("����");
					sign[i] = false;
					while(--j >= 0) {
						work[j] -= Allocation[i][j];
					}
//					System.out.print(sign[i]);
//					System.out.println("���˳ɹ�-");
//					System.out.print("p" + (i) + " work :");
//					for (int k = 0; k < sour; k++) {
//						System.out.print(work[k] + " ");
//					}
//					System.out.println();
					break;
				}
//				System.out.println("ִ��+");
				work[j] += Allocation[i][j];
//				System.out.print("p" + (i) + " work :");
//				for (int k = 0; k < sour; k++) {
//					System.out.print(work[k] + " ");
//				}
//				System.out.println();
			}
//			System.out.println();
//			System.out.print("\n" + "sign:");
//			for (int k = 0; k < proc; k++) {
//				System.out.print(" " + sign[k]);
//			}
//			System.out.println();
			
			// ��ǳɹ����ٴ�Ѱ��δ���
			if(sign[i]) {
				i = -1;
			} else {
				continue;
			}
		}
		
		// ���������̷�����������
		for(int i = 0; i < proc; i++) {
			if(!sign[i]) deadlock[deadlockCount++] = i;
		}
		// �������������Ϊ0����û�н�������
		if(deadlockCount == 0) return false;
//		this.printDeadlockQueue();
		return true;
	}
	
	/**
	 * �����ָ�
	 * �𲽶��������̽�����Դ�ͷ�
	 * ԭ��
	 * ĿǰΪֹ�������Դ��������
	 * @return �Ƿ�ָ�
	 */
	protected boolean deadlockRecover_PartDeadlockProcess() {
		System.out.println("�𲽳�����������");
		if (proc <= 0 || sour <= 0) return false;
		if(deadlockCount == 0) {
			System.out.println("�𲽳�����������->�����ָ�");
			return true;
		}
		
		// int[][0]���̺� 
		// int[][1]�ѷ������Դ���� -- Allocation
		int[][] pAllocationSource = new int[deadlockCount][2];
		// ��Сֵ
		//1��ͳ�����������и������̵�allocation��Դ��������С��������
		for (int i = 0; i < deadlockCount; i++) {
			int sum = 0;
			for(int j : Allocation[deadlock[i]]) {
				sum += j;
			}
			pAllocationSource[i][0] = deadlock[i];
			pAllocationSource[i][1] = sum;
		}
		
		// ���ո�������allocation��Դ��������
		Arrays.sort(pAllocationSource, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[1] - o2[1];
			}
		});
		
		//2�����������̰���˳���ͷ���Դ
		for(int i = 0; i < deadlockCount; i++) {
			for(int j = 0; j < sour; j++) {
				// need += allocation && available += allocation && allocation��0
				Need[ pAllocationSource[i][0] ][j] += Allocation[ pAllocationSource[i][0] ][j];
				Available[j] += Allocation[ pAllocationSource[i][0] ][j];
				Request[ pAllocationSource[i][0] ][j] ^= Request[ pAllocationSource[i][0] ][j];
				Allocation[ pAllocationSource[i][0] ][j] ^= Allocation[ pAllocationSource[i][0] ][j];
			}
			if(!this.dection()) {
				System.err.println("�𲽳�����������->�����ָ�");
				break;
			}
		}
		return true;
	}
	
	/**
	 * �����ָ�
	 * ȫ������������Դ�ͷ�
	 * @return
	 */
	protected boolean deadlockRecover_ALLDeadlockProcess() {
		System.out.println("����ȫ����������");
		if (proc <= 0 || sour <= 0) return false;
		if(deadlockCount == 0) {
			System.out.println("����ȫ����������->�����ָ�");
			return true;
		}
		
		for(int i = 0; i < deadlockCount; i++) {
			for(int j = 0; j < sour; j++) {
				Need[i][j] += Allocation[i][j];
				Available[j] += Allocation[i][j];
				Request[i][j] ^= Request[i][j];
				Allocation[i][j] ^= Allocation[i][j];
			}
		}
		if(!this.dection()) {
			System.err.println("����ȫ����������->�����ָ�");
			return true;
		}
		System.err.println("����ʧ��");
		return false;
	}

	/**
     * ��Դ���ᷨ
     * ��ĳ��ԭ������һ�����������̣�ֱ�����������������������µ����ڴ棬�������̬��
     * ����ԭ���ǣ����ո����������Ѿ�����ĸ���Դ������С�����˳��
     * ��Դ���ᷨ�лָ�����ʱ�Ǻ����Ľ����Ȼָ�
     * �㷨��ʹ���˵ݹ�
     * ���������ŵ��ǣ�һ���̶��Ͽ�������ʧ�Ĵ���
     * ȱ���ǣ���������ϵͳ��ÿ����Դ����ʱ�Ĵ��۲�����ͬ�����˴��ݲ����ǣ�������ͬ����
     * �ָ�����ô��ǣ��ѷ�����Դ����ĸ��п������������Ƶ�����߸��ӽ����ģ������Ȼָ�
     * ��ȱ��Ҳ�ܴ���������еĽ������ܴ�˼������ܸ��ӵĻ������ȹ���Ľ��̿��ܻἢ��
     * ������Ϊ�����ڳ��õĲ���ϵͳ��Unix��Linux �� Windows�У���������ʱ������û���ɶ��Ӱ�죬���������ĸ��ʺܵͣ��������ⲻ��
     */
	protected boolean deadlockRecover_SuspendDeadlockProcess() {
		System.out.println("�𲽹�����������");
		if (proc <= 0 || sour <= 0) return false;
		
		// ģ�������� �洢Max , ���̺Ŵ洢�±꣺sour
		int[][] suspendProcess = new int[deadlockCount][sour + 1];
		// int[][0]���̺�  int[][1]�洢������Դ��Allocation������
		int[][] pAllocationSource = new int[deadlockCount][2];
		// ��Сֵ
		//1��ͳ�����������и������̵�allocation��Դ��������С��������
		for (int i = 0; i < deadlockCount; i++) {
			int sum = 0;
			for(int j : Allocation[deadlock[i]]) {
				sum += j;
			}
			pAllocationSource[i][0] = deadlock[i];
			pAllocationSource[i][1] = sum;
		}
		
		// ���ո�������allocation��Դ��������
		Arrays.sort(pAllocationSource, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[1] - o2[1];
			}
		});
		System.gc();
		int suspendProcessNum = 0;
		
		//2�����������̰���˳������ͷ���Դ
		for(int i = 0; i < deadlockCount; i++) {
			// ��¼�������̺�
			suspendProcess[i][sour] = deadlock[i];
			suspendProcessNum++;
			int suspendSourNum = suspendProcess[i][sour];
			
			// ����
			for(int j = 0; j < sour; j++) {
				suspendProcess[i][j] = Max[i][j];
			}
			// �ͷ���Դ
			for(int j = 0; j < sour; j++) {
				// Max = 0 && need += allocation && available += allocation && allocation��0]
				Request[suspendSourNum][j] ^= Request[suspendSourNum][j];
				Max[suspendSourNum][j] ^= Max[suspendSourNum][j];
				Need[suspendSourNum][j] ^= Need[suspendSourNum][j];
				Available[j] += Allocation[suspendSourNum][j];
				Allocation[suspendSourNum][j] ^= Allocation[suspendSourNum][j];
			}
			if(!this.dection()) {
				System.err.println("�𲽹�����������->�����ָ�");
				break;
			}
		}
		System.err.println("�ָ������");
		// �ָ��������
		for(int i = 0; i < suspendProcessNum; i++) {
			int suspendSourNum = suspendProcess[i][sour];
			//need = max
			for(int j = 0; j < sour; j++) {
				Max[suspendSourNum][j] = suspendProcess[i][j];
				Need[suspendSourNum][j] = Max[suspendSourNum][j];
			}
		}
		
		return true;
	}
	
	/**
     * ��ͼ�ν�����չʾ��������
     * @return
     */
    protected String showDeadlockQueue() {
    	StringBuilder str = new StringBuilder();
    	if(deadlockCount == 0) {
    		str.append("û�н�������������״̬\n");
    		return str.toString();
    	}
    	str.append("��������: ");
    	for(int i = 0; i < deadlockCount; i++) {
    		str.append("p" + deadlock[i] + ", ");
    	}
    	str.append("\n");
    	return str.toString();
    }
	
	//û���������̲���ӡ
	/**
	 * ��ӡ��������
	 */
	protected void printDeadlockQueue() {
		if(deadlockCount == 0) {
			System.out.println("û�н�������������״̬");
			return;
		}
		System.out.print("��������: ");
		for(int i = 0; i < deadlockCount; i++) {
			System.out.print("P" + deadlock[i] + " ");
		}
		System.out.println();
		for(boolean i : sign) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
	
	
	
	/**
	 * ��¼����������Դ��
	 * @param count
	 */
	public void setProcAndSour(ArrayList<String> count) {
		this.proc = Integer.parseInt(count.get(0).split(" ")[0]);
		this.sour = Integer.parseInt(count.get(0).split(" ")[1]);
	}
	
	
	/**
	 * ��ArrayList�ж�ȡ��Ӧ���� �洢��Available
	 * @param arrayList ArrayList<String>�ж�ӦAvailable������
	 * @param index	������ʼ��ַ
	 */
	public void setAvailable(ArrayList<String> arrayList, int index) {
		// ��ArrayList�д洢���ַ������ж�ȡ
        for(int i = 0; i < sour; i++) {
            String s = arrayList.get(index).split("\\s+")[i];
            Available[i] = Integer.parseInt(s);
        }
        
        System.out.println("Available:");
        for(int i = 0; i < sour; i++) {
        	System.out.print(Available[i] + " ");
        }
        System.out.println();
	}
	
	/**
     * ����Max����
     * @param arrayList
     * @param index	��Ӧ Max ������arrayList�е�λ��
     */
    public void setMax(ArrayList<String> arrayList, int index) {
    	for(int i = 0; i < proc; i++) {
			for(int j = 0; j < sour; j++) {
				String s = arrayList.get(i + index).split("\\s+")[j];
				Max[i][j] = Integer.parseInt(s);
			}
		}
    	System.out.println("Max:");
        for(int i = 0; i < proc; i++) {
        	for (int j = 0; j < sour; j++) {
				System.out.print(Max[i][j] + " ");
			}
        	System.out.println();
        }
        System.out.println();
    }
	

	public void setAllocation(ArrayList<String> arrayList, int index) {
		for(int i = 0; i < proc; i++) {
			for(int j = 0; j < sour; j++) {
				String s = arrayList.get(i + index).split("\\s+")[j];
				Allocation[i][j] = Integer.parseInt(s);
			}
		}
		
		System.out.println("Allocation:");
		for(int i = 0; i < proc; i++) {
			for (int j = 0; j < sour; j++) {
				System.out.print(Allocation[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void setNeed(ArrayList<String> arrayList, int index) {

		for(int i = 0; i < proc; i++) {
			for(int j = 0; j < sour; j++) {
				String s = arrayList.get(i + index).split("\\s+")[j];
				Need[i][j] = Integer.parseInt(s);
			}
		}
		
		System.out.println("Need:");
		for(int i = 0; i < proc; i++) {
			for (int j = 0; j < sour; j++) {
				System.out.print(Need[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void setRequest(ArrayList<String> arrayList, int index) {
		for(int i = 0; i < proc; i++) {
			for(int j = 0; j < sour; j++) {
				String s = arrayList.get(i + index).split("\\s+")[j];
				Request[i][j] = Integer.parseInt(s);
			}
		}
		System.out.println("Request:");
		for(int i = 0; i < proc; i++) {
			for (int j = 0; j < sour; j++) {
				System.out.print(Request[i][j] + " ");
			}
			System.out.println();
		}
	}

	
	
	protected int getProc() {
		return proc;
	}

	protected int getSour() {
		return sour;
	}

	protected int[] getAvailable() {
		return Available;
	}

	protected int[][] getAllocation() {
		return Allocation;
	}

	protected int[][] getNeed() {
		return Need;
	}

	protected int[][] getRequest() {
		return Request;
	}

	protected int[][] getMax() {
		return Max;
	}
	
}
