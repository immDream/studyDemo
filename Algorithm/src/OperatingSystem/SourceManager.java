package OperatingSystem;

import java.util.*;


/**
 * ���м��㷨���������
 * ���м��㷨ͨ��requestSource()���á�ǰ������һ��request����
 * @author acer
 *
 */
public class SourceManager {
    private Scanner in = new Scanner(System.in);
//    private FileStore file = new FileStore();
    private int proc;
    private int sour;
    
	private int[] Available;		//������Դ
    private int[][] Max;			//�������������      Max[i][j]=k ��i����������ԴRjΪk
    private int[][] Allocation;		//������ռ����Դ��  Allocation[i][j]=k��i�����ѷ���Rj����Դ��ĿΪk
    private int[][] Need;			//���̻�����Դ��      Need[i][j]=k��i���̻���Rj����Դ��ĿΪk
    private int[][] Request;		//����������             	Request[i][j]=k��i��������Rj����Դ��ĿΪk
    
    private int[] Work;				//�Է���
    private int[] securitySequence;	//����ִ��˳��

	private int[] deadlock;			// ��������
	int deadlockCount = 0;			// �������н��̸���
	
	boolean[] sign;					// �������
	
    int num = 0;					//���̱��
    
    //���������
    private StringBuilder str = new StringBuilder();
    //�洢������
    private StringBuilder inputResult = new StringBuilder();
    
    public SourceManager() {
		
	}
    
    
    /**
     * ���ι��캯�����Ա�����ʼ��
     *
     * @param proc ������
     * @param sour ��Դ��
     */
    public SourceManager(int proc, int sour) {
    	if(proc <= 0 || sour <= 0) {
    		System.err.println("���󴴽�ʧ�ܣ�����");
    		return;
    	}
    	this.proc = proc;
    	this.sour = sour;
        this.Available = new int[sour];			//������Դ
        this.Max = new int[proc][sour];			//�������������
        this.Allocation = new int[proc][sour];	//������ռ����Դ��
        this.Need = new int[proc][sour];		//���̻�����Դ��
        this.Request = new int[proc][sour];		//����������
        
        this.Work = new int[sour];				//�Է���
        this.securitySequence = new int[proc];
        
        this.sign = new boolean[proc];
		this.deadlock = new int[proc];
    }

    /**
     * ��������
     */
//    public void start() {//���ø���ʼϵͳ���������ж��Ƿ��ڰ�ȫ״̬��
//        setAvailable();
//        setMax();
//        setAllocation();
//        printSystemVariable();
//        SecurityAlgorithm();
//    }

    
    /**
     * �������������ִ�����м��㷨��������Ѱ�Ұ�ȫ����
     */
    public void requestSourceToEXEBanker()  {
    	int i = num;	//���½��̺�num
    	int reNum = num;
    	str.append("-----------------------------------------�Խ���P" + num +  "ִ�����м��㷨-----------------------------------------\n\n");
    	while(!BankerAlgorithm()) {
    		//System.err.println("--" + showMessage());
    		num = ++i % proc;
    		if(num == reNum) {//���������������н������
//    			System.err.println("����+++");
    			str.append("[WARNING]���棡����������������\n"
    					+ "[WARNING]��ǰ���н����������Զ�������������㷨\n");
    			deadlockDection();
    			str.append("[INFO]���ֶ��������м��㷨��\n");
//    			str.append("\n");
////    			System.out.println(showDeadlockQueue());
////    			deadlockRecover_SuspendDeadlockProcess();
//    			deadlockRecover_PartDeadlockProcess();
    			return ;
    		}
    		str.append("-----------------------------------------�Խ���P" + num +  "ִ�����м��㷨-----------------------------------------\n\n");
    	}
    	
    	System.out.println("���м��㷨�õ���ȫ����");
    	System.out.println(Arrays.toString(securitySequence));
    	str.append("���м��㷨�õ���ȫ����\n" + Arrays.toString(securitySequence) + "\n");
    }
    

    /**
     * ���м��㷨
     *
     * @param proc
     * @param sour
     */
    private boolean BankerAlgorithm() {
        boolean T = true;
        int b = 0;		//��Դ�Ƿ������(һ�����̷��䵽�˼�����Դ)
        int count = 0;	//����Request < need ����Դ��
        int number = 0;	//����Request < Available����Դ��
        for (int i = 0; i < sour; i++) {
            if (Request[num][i] <= Need[num][i]) {//�ж�Request�Ƿ�С��Need
                count++;
            }
            if (Request[num][i] <= Available[i]) {//�ж�Request�Ƿ�С��Available
                number++;
            }
        }

        if (count == sour) {
            if (number == sour) {
                //T = true ʱ���ı�����
                for (int i = 0; i < sour; i++) {
                    Available[i] -= Request[num][i];
                    Allocation[num][i] += Request[num][i];
                    Need[num][i] -= Request[num][i];
                }
            } else {
//                System.out.println("��ǰû���㹻����Դ�ɷ��䣬����P" + num + "��ȴ���");
                str.append("��ǰû���㹻����Դ�ɷ��䣬����P" + num + "��ȴ���\n");
                T = false;
                return false;
            }
        } else {
//            System.out.println("����P" + num + "�����Ѿ��������������Need");
            str.append("[WARNING]����P" + num + "�����Ѿ��������������Need\n");
            T = false;
            return false;
        }

        if (T) {
        	showSystemVariable();
//            printSystemVariable();
//            System.out.println("���ڽ��밲ȫ�㷨��");
            str.append("���ڽ��а�ȫ�㷨��\n");
            boolean egg = SecurityAlgorithm();
            if (!egg) {
//                System.out.println("����" + num + "������Դ��ϵͳ��������״̬������ʧ��!");
                str.append("[WARNING]����" + num + "������Դ��ϵͳ��������״̬������ʧ��!\n");
                for (int i = 0; i < sour; i++) {
                    Available[i] += Request[num][i];
                    Allocation[num][i] -= Request[num][i];
                    Need[num][i] += Request[num][i];
                }
                return false;
            } else {
                for (int i = 0; i < sour; i++) {
                    if (Need[num][i] == 0) {
                        b++;
                    }
                }
                if (b == sour) {
                    for (int i = 0; i < sour; i++) {
                        Available[i] += Allocation[num][i];
                    }
//                    printSystemVariable();
                }
            }
        }
        return true;
    }

    /**
     * ��ȫ���㷨
     *
     * @param proc
     * @param sour
     */
    private boolean SecurityAlgorithm() {
//    	System.err.println("��ȫ���㷨");
    	str.append("��ȫ���㷨\n");
        boolean[] Finish = new boolean[proc];//��ʼ��Finish
        // finish = true��ɽ��̵ķ���
        for (int i = 0; i < proc; i++) {
            Finish[i] = false;
        }
        boolean lable = false;//�Ƿ�ȫ
        int apply;//������־  һ�����̿ɷ��䵽����Դ��
        int circle = 0;//Ȧ��
        int count = 0;//��ɽ�����
        for (int i = 0; i < sour; i++) {//���ù�������
            Work[i] = Available[i];//�����Է���
        }
        boolean flag = true;
        while (count < proc) {//���������С���ܽ�����
            if (flag) {		//only one
//                System.out.println("����  " + "   Work \t" + "   Alloction \t" + "    Need  \t" + "     \tWork+Alloction " + "  Finish");
                str.append(String.format("%-5s\t %-8s\t     %-15s %6s %25s %10s\n", "����", "Work", "Alloction", "Need", "Work+Alloction", "Finish"));
                flag = false;
            }

            for (int i = 0; i < proc; i++) {//��������   ÿһ�����̵�ÿһ����Դ
                apply = 0;//һ�����̿ɷ��䵽����Դ��
                for (int n = 0; n < sour; n++) {

                	//Need �޸ĳ� Request
                    if (!Finish[i] && Need[i][n] <= Work[n]) {//�жϽ����Ƿ����Է���ɹ���
                        // ��û�з��䣬����Դ������С�ڿ�����Դ�������
                        apply++;
                        if (apply == sour) {//������̶����Ϸ���Ҫ��
//                            System.out.print("P" + i + "  ");
                            str.append("P" + i + " ");
                            str.append("|  ");
                            for (int m = 0; m < sour; m++) {
//                                System.out.print(Work[m] + "  ");
                                str.append(String.format("%-3s", Work[m]));
                            }

//                            System.out.print("|  ");
                            str.append("|  ");
                            for (int j = 0; j < sour; j++) {
                                Work[j] += Allocation[i][j];//�Է��������ݴ�������Դ��ְ��
                            }

                            Finish[i] = true;//��ǰ����������ʱ����Ϊtrue
                            securitySequence[count++] = i;//��ȫ����

                            for (int j = 0; j < sour; j++) {
//                                System.out.print(Allocation[i][j] + "  ");
                                str.append(String.format("%-3s", Allocation[i][j]));
                            }

//                            System.out.print("|  ");
                            str.append("|  ");
                            for (int j = 0; j < sour; j++) {
//                                System.out.print(Need[i][j] + "  ");
                                str.append(String.format("%-3s", Need[i][j]));
                            }

//                            System.out.print("|  ");
                            str.append("|  ");
                            for (int j = 0; j < sour; j++) {
//                                System.out.print(Request[i][j] + "  ");
                                str.append(String.format("%-3s", Request[i][j]));
                            }
                            
//                            System.out.print("|  ");
                            str.append("|  ");
                            for (int j = 0; j < sour; j++) {
//                                System.out.print(Work[j] + "  ");
                                str.append(String.format("%-3s", Work[j]));
                            }

//                            System.out.print("|");
                            str.append("|" + Finish[i] + "\n");

//                            System.out.print(Finish[i]);

                           
                        }
                    }
                }
            }
//            System.out.print(showMessage());
            circle++;//Ȧ��

            if (count == proc) {//������proc������ִ��  Ҳ�����а�ȫ����
                lable = true;//����Ϊ��
//                System.out.println("ϵͳ�ǰ�ȫ��");
//                System.out.print("��ʱ����һ����ȫ���У�");
                str.append("ϵͳ�ǰ�ȫ��\n��ʱ����һ����ȫ���У�");
                
                for (int i = 0; i < proc; i++) {
                    System.out.print("P" + securitySequence[i]);
                    str.append("P" + securitySequence[i]);
                    if (i < proc - 1) {
                        System.out.print("->");
                        str.append("->");
                    }
                }
                System.out.println();
                str.append("\n");
                break;
            }
//            System.out.print(showMessage());
            if (count < circle) {//�������Ȧ�����ǻ�û���ҵ���ȫ�����Ǿ�˵����ǰϵͳ���ڲ���ȫ״̬
                count = proc;
                lable = false;//����Ϊ��
                for (int i = 0; i < proc; i++) {
                    if (!Finish[i]) {
//                        System.out.println("��ǰϵͳ���ڲ���ȫ״̬,�ʲ����ڰ�ȫ����");
                        str.append("[WARNING]��ǰϵͳ���ڲ���ȫ״̬,�ʲ����ڰ�ȫ����\n");
                        break;
                    }
                }
            }
//            System.out.print(showMessage());
        }
        return lable;
    }
    
    /**
	 * �������
	 * 1�����allocation��ȫ0�Ľ���
	 * 2��work = available
	 * 3��Ѱ�ҽ������ܹ�����request < work�� 
	 * 4������������̲���work += allocation���ظ�3
	 * @return falseû������
	 */
	protected boolean deadlockDection() {
		str.append("�������...\n");
		if(proc <= 0 || sour <= 0) return false;
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
		
		// Ѱ��δ��ǵĽ��̲����Ա�ǣ�next����   i:����
		for(int i = 0; i < proc; i++) {
			if(sign[i]) {
				continue;
			}
			// Ѱ��request�н����������Դ < work �����
			sign[i] = true;	// �����ܹ������
			for(int j = 0; j < sour; j++) {
				// ���ʧ�� ��work����
				if(Request[i][j] > work[j]) {
					sign[i] = false;
					while(--j >= 0) {
						work[j] -= Allocation[i][j];
					}
					break;
				}
				work[j] += Allocation[i][j];
			}
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
		if(deadlockCount == 0) {
			str.append("��ӡ��������: \n");
			showDeadlockQueue();
			return false;
		}
		str.append("��ӡ��������: \n");
		showDeadlockQueue();
		return true;
	}
	
	/**
	 * �����ָ�
	 * �𲽶��������̽�����Դ�ͷ�
	 * ԭ��
	 * ĿǰΪֹ�������Դ��������
	 */
	protected void deadlockRecover_PartDeadlockProcess() {
		str.append("������������������Դ...\n");
		if (proc <= 0 || sour <= 0) return ;
		if(deadlockCount == 0) {
			str.append("[INFO]0.0���򲻴���������δ������������㷨��\n");
			return ;
		}
		
		// int[][0]���̺� 
		// int[][1]�ѷ������Դ����
		int[][] pAllocationSource = new int[deadlockCount][2];
		// ��Сֵ
		//1��ͳ�����������и������̵�allocation��Դ��������С��������
		for (int i = 0; i < deadlockCount; i++) {
			int sum = 0;
			for(int j : Allocation[deadlock[i]]) {
				sum += j;
			}
			pAllocationSource[i][0] = i;
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
				// need += allocation && allocation��0 && available += allocation
				Need[pAllocationSource[i][0]][j] += Allocation[pAllocationSource[i][0]][j];
				Available[j] += Allocation[pAllocationSource[i][0]][j];
				Allocation[pAllocationSource[i][0]][j] ^= Allocation[pAllocationSource[i][0]][j];
			}
			if(!this.deadlockDection()) {
				//BankerAlgorithm();
//				System.err.println("ִ�м��");
				str.append("ִ�������㷨���...\n");
				str.append("��ǰ��Դ����\n");
				showSystemVariable();
				str.append("�����ѻָ�\n");
				return ;
			}
			str.append("��ǰ��Դ����\n");
			showSystemVariable();
		}
		str.append("[WARNING]ִ�д���!!\n");
//		System.out.println("�����ѻָ���");
	}

	
	/**
	 * �����ָ�
	 * ȫ������������Դ�ͷ�
	 */
	protected void deadlockRecover_ALLDeadlockProcess() {
		str.append("����ȫ������...\n");
		if (proc <= 0 || sour <= 0) return ;
		if(deadlockCount == 0) {
			str.append("[INFO]0.0���򲻴���������δ������������㷨��\n");
			return ;
		}
		
		for(int i = 0; i < deadlockCount; i++) {
			for(int j = 0; j < sour; j++) {
				Need[i][j] += Allocation[i][j];
				Available[j] += Allocation[i][j];
				Request[i][j] ^= Request[i][j];
				Allocation[i][j] ^= Allocation[i][j];
			}
		}
		if(!this.deadlockDection()) {
			str.append("�����ָ�\n");
			showSystemVariable();
//			System.err.println("����ȫ����������->�����ָ�");
			return ;
		}
		str.append("����ȫ��������������ʧ��\n");
		System.err.println("����ȫ��������������ʧ��");
		return ;
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
	protected void deadlockRecover_SuspendDeadlockProcess() {
//		System.out.println("�𲽹�����������");
		str.append("�𲽹�����������...\n");
		if (proc <= 0 || sour <= 0) return ;
		if (deadlockCount == 0) {
			str.append("[INFO]0.0���򲻴���������δ������������㷨��\n");
			return ;
		}
		
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
			int sourNum = suspendProcess[i][sour];
			
			// ����
			for(int j = 0; j < sour; j++) {
				suspendProcess[i][j] = Max[i][j];
			}
			// �ͷ���Դ
			for(int j = 0; j < sour; j++) {
				// Max = 0 && need += allocation && available += allocation && allocation��0]
				Request[sourNum][j] ^= Request[sourNum][j];
				Max[sourNum][j] ^= Max[sourNum][j];
				Need[sourNum][j] ^= Need[sourNum][j];
				Available[j] += Allocation[sourNum][j];
				Allocation[sourNum][j] ^= Allocation[sourNum][j];
			}
			if(!this.deadlockDection()) {
//				System.err.println("�𲽹�����������->�����ָ�");
				str.append("->�����ָ�\n�����:");
				break;
			}
		}
//		System.err.println("�����");
//		System.out.println(showMessage());
		showSystemVariable();
//		System.err.println("�ָ������");
		str.append("�ָ������:\n");
//		System.out.println(showMessage());
		
		
		// �ָ��������
		for(int i = 0; i < suspendProcessNum; i++) {
			int sourNum = suspendProcess[i][sour];
			//need = max
			for(int j = 0; j < sour; j++) {
				Max[sourNum][j] = suspendProcess[i][j];
				Need[sourNum][j] = Max[sourNum][j];
			}
		}
//		printSystemVariable();
		showSystemVariable();
	}
	
	/**
	 * ��ͼ�ν�����չʾϵͳ����
	 * @return
	 */
	public void showSystemVariable() {
		str.append("��ʱ��Դ��������: \n"
				+ String.format("%-5s\t %-8s\t  %-10s %12s %20s\n", "����", "Max", "Alloction", "Need", "Available"));
		for(int i = 0; i < proc; i++) {
			str.append(String.format("%s%d%2s ", "P", i, "|"));
			for(int j = 0; j < sour; j++) {
				str.append(String.format("%-3d", Max[i][j]));
			}
			str.append(String.format("%-2s", "|"));
			for(int j = 0; j < sour; j++) {
				str.append(String.format("%-3d", Allocation[i][j]));
			}
			str.append(String.format("%-2s", "|"));
			for(int j = 0; j < sour; j++) {
				str.append(String.format("%-3d", Need[i][j]));
			}
			str.append(String.format("%-2s", "|"));
			if (i == 0) {
                for (int j = 0; j < sour; j++) {
                	str.append(String.format("%-3d", Available[j]));
                }
            }
            str.append("\n");
		}
	}
	
	/**
     * ��ӡ����
     * @param proc
     * @param sour
     */
    public void printSystemVariable() {
        System.out.println("��ʱ��Դ���������£�");
        
        System.out.println(String.format("%-5s\t %-10s\t %-15s %6s %20s", "����", "Max", "Alloction", "Need", "Available"));

        for (int i = 0; i < proc; i++) {
            System.out.print(String.format("%s%d%2s ", "P", i, "|"));

            for (int j = 0; j < sour; j++) {
                System.out.print(String.format("%-3d", Max[i][j]));
            }

            System.out.print(String.format("%-2s", "|"));

            for (int j = 0; j < sour; j++) {
            	System.out.print(String.format("%-3d", Allocation[i][j]));
            }

            System.out.print(String.format("%-2s", "|"));

            for (int j = 0; j < sour; j++) {
            	System.out.print(String.format("%-3d", Need[i][j]));
            }

            System.out.print(String.format("%-2s", "|"));

            if (i == 0) {
                for (int j = 0; j < sour; j++) {
                	System.out.print(String.format("%-3d", Available[j]));
                }
            }
            System.out.println();
        }
    }

	
    /**
     * ��ͼ�ν�����չʾ��������
     * @return
     */
    protected void showDeadlockQueue() {
    	if(deadlockCount == 0) {
    		str.append("[INFO]û�н�������������״̬\n");
    		return ;
    	}
    	str.append("��������: ");
    	for(int i = 0; i < deadlockCount; i++) {
    		str.append("P" + deadlock[i] + " ");
    	}
    	str.append("\n");
    }
    
    protected String showMessage() {
    	String s = str.toString();
    	str = new StringBuilder();
    	return s;
    }
    
    protected String showInputMessage() {
    	return inputResult.toString();
    }
    
    
	//û���������̲���ӡ
	/**
	 * ��ӡ��������
	 */
	protected void printDeadlockQueue() {
		if(deadlockCount == 0) {
			System.out.println("[INFO]û�н�������������״̬");
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
	 * ͨ���ļ�����proc sour ����ʼ������
	 * @param count
	 */
	public void setProcAndSour(ArrayList<String> count) {
		this.proc = Integer.parseInt(count.get(0).split(" ")[0]);
		this.sour = Integer.parseInt(count.get(0).split(" ")[1]);
		if(proc <= 0 || sour <= 0) {
    		System.err.println("���󴴽�ʧ�ܣ�����");
    		return;
    	}
        this.Available = new int[sour];			//������Դ
        this.Max = new int[proc][sour];			//�������������
        this.Allocation = new int[proc][sour];	//������ռ����Դ��
        this.Need = new int[proc][sour];		//���̻�����Դ��
        this.Request = new int[proc][sour];		//����������
        
        this.Work = new int[sour];				//�Է���
        this.securitySequence = new int[proc];
        
        this.sign = new boolean[proc];
		this.deadlock = new int[proc];
	}
	
	
	/**
	 * ��ArrayList�ж�ȡ��Ӧ���� �洢��Available
	 * @param arrayList ArrayList<String>�ж�ӦAvailable������
	 * @param index	������ʼ��ַ
	 */
	public void setAvailable(ArrayList<String> arrayList, int index) {
		inputResult.append("Available : \n");
		// ��ArrayList�д洢���ַ������ж�ȡ
        for(int i = 0; i < sour; i++) {
            String s = arrayList.get(index).split("\\s+")[i];
            inputResult.append(s + " ");
            Available[i] = Integer.parseInt(s);
        }
        inputResult.append("\n");
	}

	/**
     * ����Max����
     * @param arrayList
     * @param index	��Ӧ Max ������arrayList�е�λ��
     */
    public void setMax(ArrayList<String> arrayList, int index) {
    	inputResult.append("Max : \n");
    	for(int i = 0; i < proc; i++) {
			for(int j = 0; j < sour; j++) {
				String s = arrayList.get(i + index).split("\\s+")[j];
				inputResult.append(s + " ");
				Max[i][j] = Integer.parseInt(s);
			}
			inputResult.append("\n");
		}
    }
	
	public void setAllocation(ArrayList<String> arrayList, int index) {
		inputResult.append("Allocation : \n");
		for(int i = 0; i < proc; i++) {
			for(int j = 0; j < sour; j++) {
				String s = arrayList.get(i + index).split("\\s+")[j];
				inputResult.append(s + " ");
				Allocation[i][j] = Integer.parseInt(s);
			}
			inputResult.append("\n");
		}
	}

	public void setNeed(ArrayList<String> arrayList, int index) {
		inputResult.append("Need : \n");
		for(int i = 0; i < proc; i++) {
			for(int j = 0; j < sour; j++) {
				String s = arrayList.get(i + index).split("\\s+")[j];
				inputResult.append(s + " ");
				Need[i][j] = Integer.parseInt(s);
			}
			inputResult.append("\n");
		}
	}

	public void setRequest(ArrayList<String> arrayList, int index) {
		inputResult.append("Request : \n");
		for(int i = 0; i < proc; i++) {
			for(int j = 0; j < sour; j++) {
				String s = arrayList.get(i + index).split("\\s+")[j];
				inputResult.append(s + " ");
				Request[i][j] = Integer.parseInt(s);
			}
			inputResult.append("\n");
		}		
	}
    
    /**
     * ���� Request ����
     */
    public void setRequest() {
    	boolean flag = true;
    	while (flag) {
	        System.out.println("������������Դ�Ľ��̱�ţ�");
	        //����ȫ�ֱ������̱��num
	        if((num = in.nextInt()) >= sour) {
	        	System.err.println("���̲�����!!����������");
	        	continue;
	        }
	        
	        System.out.println("�������������Դ��������");
	        for (int j = 0; j < sour; j++) {
	            Request[num][j] = in.nextInt();
	        }
	
	        String str = Arrays.toString(Request[num]);
	        System.out.println("������P" + num + "�Ը���Դ����Request��(" +
	                str + ")");
	        BankerAlgorithm();
	        System.out.println("�Ƿ����������Դ(y/n)");
	        char c = in.next().charAt(0);
	        if(c == 'n' || c == 'N') {
	        	flag = !flag;
	        }
    	}
    }
    
	
	protected int[] getAvailable() {
		return Available;
	}

	protected int[][] getMax() {
		return Max;
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

	protected int getProc() {
		return proc;
	}

	protected int getSour() {
		return sour;
	}
	
	

}