package OperatingSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * 死锁检测
 * @author 庄宇
 * @date 2021-8-23
 */
public class DeadlockDection {
	//文件输入，死锁解除
	
	int proc;			//进程数
	int sour;			//资源数
	
	int[] Available;	// 可获得资源数
	int[][] Allocation;	// 已分配的资源数
	int[][] Need;		// 进程需要的资源数
	int[][] Request;	// 进程请求的资源数
	int[][] Max;			//进程最大需求量
	
	int[] deadlock;		// 死锁队列
	int deadlockCount = 0;		// 死锁队列进程个数
	
	boolean[] sign;		// 标记序列 true不是死锁进程

	
	/**
	 * 构造函数
	 * @param proc 进程数 
	 * @param sour 资源数
	 */
	DeadlockDection(int proc, int sour) {
		if(proc <= 0 || sour <= 0) {
			System.err.println("对象创建失败!");
			return;
		}
		this.proc = proc;
		this.sour = sour;
		this.Available = new int[sour];
		this.Max = new int[proc][sour];			//进程最大需求量
		this.Allocation = new int[proc][sour];
		this.Need = new int[proc][sour];
		this.Request = new int[proc][sour];
		this.sign = new boolean[proc];
		this.deadlock = new int[proc];
	}
	

	/**
	 * 死锁检测
	 * 1、标记allocation中全0的进程
	 * 2、work = available
	 * 3、寻找进程中能够满足request < work的 
	 * 4、标记上述进程并将work += allocation，重复3
	 */
	protected boolean dection() {
		if(proc < 0 || sour < 0) return false;
		//每次检测死锁队列都先将死锁进程数清0
		deadlockCount = 0;
		
		for(int i = 0; i < proc; i++) {
			int count = 0;	// 检查一个进程的资源数是否全为0
			for(int j = 0; j < sour; j++) {
				if(Allocation[i][j] == 0) count++;
			}
			if(count == sour) sign[i] = true;
		}
		
		int[] work = new int[sour];
		// 使work == available
		for(int i = 0; i < sour; i++) {
			work[i] = Available[i];
		}
		
//		for (int k = 0; k < proc; k++) {
//			System.err.print(" " + sign[k]);
//		}
//		System.out.println();
		
		// 寻找未标记的进程并尝试标记，next策略   i:进程
		for(int i = 0; i < proc; i++) {
			if(sign[i]) {
				continue;
			}
//			System.out.println("---------------------------------");
//			System.err.println("i = " + i);
			
			// 寻找request中进程申请的资源 < work 并标记
			sign[i] = true;	// 假设能够被标记
			for(int j = 0; j < sour; j++) {
//				System.out.println("__________");
				// 标记失败 将work回退
				if(Request[i][j] > work[j]) {
//					System.out.println("回退");
					sign[i] = false;
					while(--j >= 0) {
						work[j] -= Allocation[i][j];
					}
//					System.out.print(sign[i]);
//					System.out.println("回退成功-");
//					System.out.print("p" + (i) + " work :");
//					for (int k = 0; k < sour; k++) {
//						System.out.print(work[k] + " ");
//					}
//					System.out.println();
					break;
				}
//				System.out.println("执行+");
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
			
			// 标记成功，再次寻找未标记
			if(sign[i]) {
				i = -1;
			} else {
				continue;
			}
		}
		
		// 将死锁进程放入死锁队列
		for(int i = 0; i < proc; i++) {
			if(!sign[i]) deadlock[deadlockCount++] = i;
		}
		// 如果死锁进程数为0，则没有进程死锁
		if(deadlockCount == 0) return false;
//		this.printDeadlockQueue();
		return true;
	}
	
	/**
	 * 死锁恢复
	 * 逐步对死锁进程进行资源释放
	 * 原则：
	 * 目前为止分配的资源总量最少
	 * @return 是否恢复
	 */
	protected boolean deadlockRecover_PartDeadlockProcess() {
		System.out.println("逐步撤销死锁进程");
		if (proc <= 0 || sour <= 0) return false;
		if(deadlockCount == 0) {
			System.out.println("逐步撤销死锁进程->死锁恢复");
			return true;
		}
		
		// int[][0]进程号 
		// int[][1]已分配的资源总量 -- Allocation
		int[][] pAllocationSource = new int[deadlockCount][2];
		// 最小值
		//1、统计死锁队列中各个进程的allocation资源总量并从小到大排序
		for (int i = 0; i < deadlockCount; i++) {
			int sum = 0;
			for(int j : Allocation[deadlock[i]]) {
				sum += j;
			}
			pAllocationSource[i][0] = deadlock[i];
			pAllocationSource[i][1] = sum;
		}
		
		// 按照各个进程allocation资源总量排序
		Arrays.sort(pAllocationSource, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[1] - o2[1];
			}
		});
		
		//2、将死锁进程按照顺序释放资源
		for(int i = 0; i < deadlockCount; i++) {
			for(int j = 0; j < sour; j++) {
				// need += allocation && available += allocation && allocation清0
				Need[ pAllocationSource[i][0] ][j] += Allocation[ pAllocationSource[i][0] ][j];
				Available[j] += Allocation[ pAllocationSource[i][0] ][j];
				Request[ pAllocationSource[i][0] ][j] ^= Request[ pAllocationSource[i][0] ][j];
				Allocation[ pAllocationSource[i][0] ][j] ^= Allocation[ pAllocationSource[i][0] ][j];
			}
			if(!this.dection()) {
				System.err.println("逐步撤销死锁进程->死锁恢复");
				break;
			}
		}
		return true;
	}
	
	/**
	 * 死锁恢复
	 * 全部死锁进程资源释放
	 * @return
	 */
	protected boolean deadlockRecover_ALLDeadlockProcess() {
		System.out.println("撤销全部死锁进程");
		if (proc <= 0 || sour <= 0) return false;
		if(deadlockCount == 0) {
			System.out.println("撤销全部死锁进程->死锁恢复");
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
			System.err.println("撤销全部死锁进程->死锁恢复");
			return true;
		}
		System.err.println("运行失败");
		return false;
	}

	/**
     * 资源剥夺法
     * 按某种原则来逐一挂起死锁进程，直到死锁解除，死锁解除后重新调入内存，进入就绪态。
     * 挂起原则是：按照各死锁进程已经分配的各资源总数从小到大的顺序
     * 资源剥夺法中恢复进程时是后挂起的进程先恢复
     * 算法上使用了递归
     * 挂起规则的优点是：一定程度上考虑了损失的代价
     * 缺点是：在真正的系统中每种资源撤销时的代价并不相同，但此处暂不考虑，按照相同处理
     * 恢复规则好处是：已分配资源更多的更有可能是请求更加频繁或者更加紧急的，所以先恢复
     * 但缺点也很大：如果这其中的进程量很大，思索情况很复杂的话，最先挂起的进程可能会饥饿
     * 不过因为在现在常用的操作系统如Unix，Linux 和 Windows中，发生死锁时不会对用户造成多大影响，或发生死锁的概率很低，所以问题不大
     */
	protected boolean deadlockRecover_SuspendDeadlockProcess() {
		System.out.println("逐步挂起死锁进程");
		if (proc <= 0 || sour <= 0) return false;
		
		// 模拟外存挂起 存储Max , 进程号存储下标：sour
		int[][] suspendProcess = new int[deadlockCount][sour + 1];
		// int[][0]进程号  int[][1]存储死锁资源的Allocation的总数
		int[][] pAllocationSource = new int[deadlockCount][2];
		// 最小值
		//1、统计死锁队列中各个进程的allocation资源总量并从小到大排序
		for (int i = 0; i < deadlockCount; i++) {
			int sum = 0;
			for(int j : Allocation[deadlock[i]]) {
				sum += j;
			}
			pAllocationSource[i][0] = deadlock[i];
			pAllocationSource[i][1] = sum;
		}
		
		// 按照各个进程allocation资源总量排序
		Arrays.sort(pAllocationSource, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[1] - o2[1];
			}
		});
		System.gc();
		int suspendProcessNum = 0;
		
		//2、将死锁进程按照顺序挂起释放资源
		for(int i = 0; i < deadlockCount; i++) {
			// 记录死锁进程号
			suspendProcess[i][sour] = deadlock[i];
			suspendProcessNum++;
			int suspendSourNum = suspendProcess[i][sour];
			
			// 挂起
			for(int j = 0; j < sour; j++) {
				suspendProcess[i][j] = Max[i][j];
			}
			// 释放资源
			for(int j = 0; j < sour; j++) {
				// Max = 0 && need += allocation && available += allocation && allocation清0]
				Request[suspendSourNum][j] ^= Request[suspendSourNum][j];
				Max[suspendSourNum][j] ^= Max[suspendSourNum][j];
				Need[suspendSourNum][j] ^= Need[suspendSourNum][j];
				Available[j] += Allocation[suspendSourNum][j];
				Allocation[suspendSourNum][j] ^= Allocation[suspendSourNum][j];
			}
			if(!this.dection()) {
				System.err.println("逐步挂起死锁进程->死锁恢复");
				break;
			}
		}
		System.err.println("恢复挂起后");
		// 恢复挂起进程
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
     * 在图形界面中展示死锁队列
     * @return
     */
    protected String showDeadlockQueue() {
    	StringBuilder str = new StringBuilder();
    	if(deadlockCount == 0) {
    		str.append("没有进程正处于死锁状态\n");
    		return str.toString();
    	}
    	str.append("死锁队列: ");
    	for(int i = 0; i < deadlockCount; i++) {
    		str.append("p" + deadlock[i] + ", ");
    	}
    	str.append("\n");
    	return str.toString();
    }
	
	//没有死锁进程不打印
	/**
	 * 打印死锁队列
	 */
	protected void printDeadlockQueue() {
		if(deadlockCount == 0) {
			System.out.println("没有进程正处于死锁状态");
			return;
		}
		System.out.print("死锁队列: ");
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
	 * 记录进程数和资源数
	 * @param count
	 */
	public void setProcAndSour(ArrayList<String> count) {
		this.proc = Integer.parseInt(count.get(0).split(" ")[0]);
		this.sour = Integer.parseInt(count.get(0).split(" ")[1]);
	}
	
	
	/**
	 * 从ArrayList中读取相应数据 存储到Available
	 * @param arrayList ArrayList<String>中对应Available的数据
	 * @param index	数据起始地址
	 */
	public void setAvailable(ArrayList<String> arrayList, int index) {
		// 对ArrayList中存储的字符串进行读取
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
     * 设置Max矩阵
     * @param arrayList
     * @param index	相应 Max 内容在arrayList中的位置
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
