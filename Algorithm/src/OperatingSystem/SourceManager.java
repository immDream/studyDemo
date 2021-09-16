package OperatingSystem;

import java.util.*;


/**
 * 银行家算法和死锁检测
 * 银行家算法通过requestSource()调用、前提是有一个request矩阵
 * @author acer
 *
 */
public class SourceManager {
    private Scanner in = new Scanner(System.in);
//    private FileStore file = new FileStore();
    private int proc;
    private int sour;
    
	private int[] Available;		//可用资源
    private int[][] Max;			//进程最大需求量      Max[i][j]=k ：i进程所需资源Rj为k
    private int[][] Allocation;		//进程已占有资源数  Allocation[i][j]=k：i进程已分配Rj类资源数目为k
    private int[][] Need;			//进程还需资源数      Need[i][j]=k：i进程还需Rj类资源数目为k
    private int[][] Request;		//进程请求数             	Request[i][j]=k：i进程请求Rj类资源数目为k
    
    private int[] Work;				//试分配
    private int[] securitySequence;	//进程执行顺序

	private int[] deadlock;			// 死锁队列
	int deadlockCount = 0;			// 死锁队列进程个数
	
	boolean[] sign;					// 标记序列
	
    int num = 0;					//进程编号
    
    //存放输出结果
    private StringBuilder str = new StringBuilder();
    //存储输入结果
    private StringBuilder inputResult = new StringBuilder();
    
    public SourceManager() {
		
	}
    
    
    /**
     * 含参构造函数，对变量初始化
     *
     * @param proc 进程数
     * @param sour 资源数
     */
    public SourceManager(int proc, int sour) {
    	if(proc <= 0 || sour <= 0) {
    		System.err.println("对象创建失败！！！");
    		return;
    	}
    	this.proc = proc;
    	this.sour = sour;
        this.Available = new int[sour];			//可用资源
        this.Max = new int[proc][sour];			//进程最大需求量
        this.Allocation = new int[proc][sour];	//进程已占有资源数
        this.Need = new int[proc][sour];		//进程还需资源数
        this.Request = new int[proc][sour];		//进程请求数
        
        this.Work = new int[sour];				//试分配
        this.securitySequence = new int[proc];
        
        this.sign = new boolean[proc];
		this.deadlock = new int[proc];
    }

    /**
     * 启动方法
     */
//    public void start() {//设置各初始系统变量，并判断是否处于安全状态。
//        setAvailable();
//        setMax();
//        setAllocation();
//        printSystemVariable();
//        SecurityAlgorithm();
//    }

    
    /**
     * 对所有请求进程执行银行家算法，在其中寻找安全序列
     */
    public void requestSourceToEXEBanker()  {
    	int i = num;	//更新进程号num
    	int reNum = num;
    	str.append("-----------------------------------------对进程P" + num +  "执行银行家算法-----------------------------------------\n\n");
    	while(!BankerAlgorithm()) {
    		//System.err.println("--" + showMessage());
    		num = ++i % proc;
    		if(num == reNum) {//死锁后检查死锁队列解除死锁
//    			System.err.println("死锁+++");
    			str.append("[WARNING]警告！！！！！！！！！\n"
    					+ "[WARNING]当前所有进程死锁，自动调用死锁检测算法\n");
    			deadlockDection();
    			str.append("[INFO]请手动调用银行家算法！\n");
//    			str.append("\n");
////    			System.out.println(showDeadlockQueue());
////    			deadlockRecover_SuspendDeadlockProcess();
//    			deadlockRecover_PartDeadlockProcess();
    			return ;
    		}
    		str.append("-----------------------------------------对进程P" + num +  "执行银行家算法-----------------------------------------\n\n");
    	}
    	
    	System.out.println("银行家算法得到安全序列");
    	System.out.println(Arrays.toString(securitySequence));
    	str.append("银行家算法得到安全序列\n" + Arrays.toString(securitySequence) + "\n");
    }
    

    /**
     * 银行家算法
     *
     * @param proc
     * @param sour
     */
    private boolean BankerAlgorithm() {
        boolean T = true;
        int b = 0;		//资源是否分配完(一个进程分配到了几个资源)
        int count = 0;	//满足Request < need 的资源数
        int number = 0;	//满足Request < Available的资源数
        for (int i = 0; i < sour; i++) {
            if (Request[num][i] <= Need[num][i]) {//判断Request是否小于Need
                count++;
            }
            if (Request[num][i] <= Available[i]) {//判断Request是否小于Available
                number++;
            }
        }

        if (count == sour) {
            if (number == sour) {
                //T = true 时，改变数据
                for (int i = 0; i < sour; i++) {
                    Available[i] -= Request[num][i];
                    Allocation[num][i] += Request[num][i];
                    Need[num][i] -= Request[num][i];
                }
            } else {
//                System.out.println("当前没有足够的资源可分配，进程P" + num + "需等待。");
                str.append("当前没有足够的资源可分配，进程P" + num + "需等待。\n");
                T = false;
                return false;
            }
        } else {
//            System.out.println("进程P" + num + "请求已经超出最大需求量Need");
            str.append("[WARNING]进程P" + num + "请求已经超出最大需求量Need\n");
            T = false;
            return false;
        }

        if (T) {
        	showSystemVariable();
//            printSystemVariable();
//            System.out.println("现在进入安全算法：");
            str.append("现在进行安全算法：\n");
            boolean egg = SecurityAlgorithm();
            if (!egg) {
//                System.out.println("进程" + num + "申请资源后，系统进入死锁状态，分配失败!");
                str.append("[WARNING]进程" + num + "申请资源后，系统进入死锁状态，分配失败!\n");
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
     * 安全性算法
     *
     * @param proc
     * @param sour
     */
    private boolean SecurityAlgorithm() {
//    	System.err.println("安全性算法");
    	str.append("安全性算法\n");
        boolean[] Finish = new boolean[proc];//初始化Finish
        // finish = true完成进程的分配
        for (int i = 0; i < proc; i++) {
            Finish[i] = false;
        }
        boolean lable = false;//是否安全
        int apply;//计数标志  一个进程可分配到的资源数
        int circle = 0;//圈数
        int count = 0;//完成进程数
        for (int i = 0; i < sour; i++) {//设置工作向量
            Work[i] = Available[i];//用于试分配
        }
        boolean flag = true;
        while (count < proc) {//分配进程数小于总进程数
            if (flag) {		//only one
//                System.out.println("进程  " + "   Work \t" + "   Alloction \t" + "    Need  \t" + "     \tWork+Alloction " + "  Finish");
                str.append(String.format("%-5s\t %-8s\t     %-15s %6s %25s %10s\n", "进程", "Work", "Alloction", "Need", "Work+Alloction", "Finish"));
                flag = false;
            }

            for (int i = 0; i < proc; i++) {//遍历进程   每一个进程的每一个资源
                apply = 0;//一个进程可分配到的资源数
                for (int n = 0; n < sour; n++) {

                	//Need 修改成 Request
                    if (!Finish[i] && Need[i][n] <= Work[n]) {//判断进程是否已试分配成功，
                        // 若没有分配，且资源需求数小于可用资源数，输出
                        apply++;
                        if (apply == sour) {//如果进程都符合分配要求
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
                                Work[j] += Allocation[i][j];//试分配数组暂代可用资源的职责
                            }

                            Finish[i] = true;//当前进程能满足时，设为true
                            securitySequence[count++] = i;//安全序列

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
            circle++;//圈数

            if (count == proc) {//正好有proc个进程执行  也就是有安全序列
                lable = true;//返回为真
//                System.out.println("系统是安全的");
//                System.out.print("此时存在一个安全序列：");
                str.append("系统是安全的\n此时存在一个安全序列：");
                
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
            if (count < circle) {//如果走完圈数但是还没有找到安全序列那就说明当前系统处于不安全状态
                count = proc;
                lable = false;//返回为假
                for (int i = 0; i < proc; i++) {
                    if (!Finish[i]) {
//                        System.out.println("当前系统处于不安全状态,故不存在安全序列");
                        str.append("[WARNING]当前系统处于不安全状态,故不存在安全序列\n");
                        break;
                    }
                }
            }
//            System.out.print(showMessage());
        }
        return lable;
    }
    
    /**
	 * 死锁检测
	 * 1、标记allocation中全0的进程
	 * 2、work = available
	 * 3、寻找进程中能够满足request < work的 
	 * 4、标记上述进程并将work += allocation，重复3
	 * @return false没有死锁
	 */
	protected boolean deadlockDection() {
		str.append("死锁检测...\n");
		if(proc <= 0 || sour <= 0) return false;
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
		
		// 寻找未标记的进程并尝试标记，next策略   i:进程
		for(int i = 0; i < proc; i++) {
			if(sign[i]) {
				continue;
			}
			// 寻找request中进程申请的资源 < work 并标记
			sign[i] = true;	// 假设能够被标记
			for(int j = 0; j < sour; j++) {
				// 标记失败 将work回退
				if(Request[i][j] > work[j]) {
					sign[i] = false;
					while(--j >= 0) {
						work[j] -= Allocation[i][j];
					}
					break;
				}
				work[j] += Allocation[i][j];
			}
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
		if(deadlockCount == 0) {
			str.append("打印死锁队列: \n");
			showDeadlockQueue();
			return false;
		}
		str.append("打印死锁队列: \n");
		showDeadlockQueue();
		return true;
	}
	
	/**
	 * 死锁恢复
	 * 逐步对死锁进程进行资源释放
	 * 原则：
	 * 目前为止分配的资源总量最少
	 */
	protected void deadlockRecover_PartDeadlockProcess() {
		str.append("撤销部分死锁进程资源...\n");
		if (proc <= 0 || sour <= 0) return ;
		if(deadlockCount == 0) {
			str.append("[INFO]0.0程序不存在死锁或未调用死锁检测算法！\n");
			return ;
		}
		
		// int[][0]进程号 
		// int[][1]已分配的资源总量
		int[][] pAllocationSource = new int[deadlockCount][2];
		// 最小值
		//1、统计死锁队列中各个进程的allocation资源总量并从小到大排序
		for (int i = 0; i < deadlockCount; i++) {
			int sum = 0;
			for(int j : Allocation[deadlock[i]]) {
				sum += j;
			}
			pAllocationSource[i][0] = i;
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
				// need += allocation && allocation清0 && available += allocation
				Need[pAllocationSource[i][0]][j] += Allocation[pAllocationSource[i][0]][j];
				Available[j] += Allocation[pAllocationSource[i][0]][j];
				Allocation[pAllocationSource[i][0]][j] ^= Allocation[pAllocationSource[i][0]][j];
			}
			if(!this.deadlockDection()) {
				//BankerAlgorithm();
//				System.err.println("执行检测");
				str.append("执行死锁算法检测...\n");
				str.append("当前资源量：\n");
				showSystemVariable();
				str.append("死锁已恢复\n");
				return ;
			}
			str.append("当前资源量：\n");
			showSystemVariable();
		}
		str.append("[WARNING]执行错误!!\n");
//		System.out.println("死锁已恢复！");
	}

	
	/**
	 * 死锁恢复
	 * 全部死锁进程资源释放
	 */
	protected void deadlockRecover_ALLDeadlockProcess() {
		str.append("撤销全部进程...\n");
		if (proc <= 0 || sour <= 0) return ;
		if(deadlockCount == 0) {
			str.append("[INFO]0.0程序不存在死锁或未调用死锁检测算法！\n");
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
			str.append("死锁恢复\n");
			showSystemVariable();
//			System.err.println("撤销全部死锁进程->死锁恢复");
			return ;
		}
		str.append("撤销全部死锁进程运行失败\n");
		System.err.println("撤销全部死锁进程运行失败");
		return ;
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
	protected void deadlockRecover_SuspendDeadlockProcess() {
//		System.out.println("逐步挂起死锁进程");
		str.append("逐步挂起死锁进程...\n");
		if (proc <= 0 || sour <= 0) return ;
		if (deadlockCount == 0) {
			str.append("[INFO]0.0程序不存在死锁或未调用死锁检测算法！\n");
			return ;
		}
		
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
			int sourNum = suspendProcess[i][sour];
			
			// 挂起
			for(int j = 0; j < sour; j++) {
				suspendProcess[i][j] = Max[i][j];
			}
			// 释放资源
			for(int j = 0; j < sour; j++) {
				// Max = 0 && need += allocation && available += allocation && allocation清0]
				Request[sourNum][j] ^= Request[sourNum][j];
				Max[sourNum][j] ^= Max[sourNum][j];
				Need[sourNum][j] ^= Need[sourNum][j];
				Available[j] += Allocation[sourNum][j];
				Allocation[sourNum][j] ^= Allocation[sourNum][j];
			}
			if(!this.deadlockDection()) {
//				System.err.println("逐步挂起死锁进程->死锁恢复");
				str.append("->死锁恢复\n挂起后:");
				break;
			}
		}
//		System.err.println("挂起后");
//		System.out.println(showMessage());
		showSystemVariable();
//		System.err.println("恢复挂起后");
		str.append("恢复挂起后:\n");
//		System.out.println(showMessage());
		
		
		// 恢复挂起进程
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
	 * 在图形界面中展示系统变量
	 * @return
	 */
	public void showSystemVariable() {
		str.append("此时资源分配如下: \n"
				+ String.format("%-5s\t %-8s\t  %-10s %12s %20s\n", "进程", "Max", "Alloction", "Need", "Available"));
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
     * 打印矩阵
     * @param proc
     * @param sour
     */
    public void printSystemVariable() {
        System.out.println("此时资源分配量如下：");
        
        System.out.println(String.format("%-5s\t %-10s\t %-15s %6s %20s", "进程", "Max", "Alloction", "Need", "Available"));

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
     * 在图形界面中展示死锁队列
     * @return
     */
    protected void showDeadlockQueue() {
    	if(deadlockCount == 0) {
    		str.append("[INFO]没有进程正处于死锁状态\n");
    		return ;
    	}
    	str.append("死锁队列: ");
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
    
    
	//没有死锁进程不打印
	/**
	 * 打印死锁队列
	 */
	protected void printDeadlockQueue() {
		if(deadlockCount == 0) {
			System.out.println("[INFO]没有进程正处于死锁状态");
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
	 * 通过文件设置proc sour 并初始化对象
	 * @param count
	 */
	public void setProcAndSour(ArrayList<String> count) {
		this.proc = Integer.parseInt(count.get(0).split(" ")[0]);
		this.sour = Integer.parseInt(count.get(0).split(" ")[1]);
		if(proc <= 0 || sour <= 0) {
    		System.err.println("对象创建失败！！！");
    		return;
    	}
        this.Available = new int[sour];			//可用资源
        this.Max = new int[proc][sour];			//进程最大需求量
        this.Allocation = new int[proc][sour];	//进程已占有资源数
        this.Need = new int[proc][sour];		//进程还需资源数
        this.Request = new int[proc][sour];		//进程请求数
        
        this.Work = new int[sour];				//试分配
        this.securitySequence = new int[proc];
        
        this.sign = new boolean[proc];
		this.deadlock = new int[proc];
	}
	
	
	/**
	 * 从ArrayList中读取相应数据 存储到Available
	 * @param arrayList ArrayList<String>中对应Available的数据
	 * @param index	数据起始地址
	 */
	public void setAvailable(ArrayList<String> arrayList, int index) {
		inputResult.append("Available : \n");
		// 对ArrayList中存储的字符串进行读取
        for(int i = 0; i < sour; i++) {
            String s = arrayList.get(index).split("\\s+")[i];
            inputResult.append(s + " ");
            Available[i] = Integer.parseInt(s);
        }
        inputResult.append("\n");
	}

	/**
     * 设置Max矩阵
     * @param arrayList
     * @param index	相应 Max 内容在arrayList中的位置
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
     * 设置 Request 矩阵
     */
    public void setRequest() {
    	boolean flag = true;
    	while (flag) {
	        System.out.println("请输入请求资源的进程编号：");
	        //设置全局变量进程编号num
	        if((num = in.nextInt()) >= sour) {
	        	System.err.println("进程不存在!!请重新输入");
	        	continue;
	        }
	        
	        System.out.println("请输入请求各资源的数量：");
	        for (int j = 0; j < sour; j++) {
	            Request[num][j] = in.nextInt();
	        }
	
	        String str = Arrays.toString(Request[num]);
	        System.out.println("即进程P" + num + "对各资源请求Request：(" +
	                str + ")");
	        BankerAlgorithm();
	        System.out.println("是否继续请求资源(y/n)");
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