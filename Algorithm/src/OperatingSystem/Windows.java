package OperatingSystem;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Windows {
    /**
     * 页面声明部分，用来建立页面的各个组件，并且调用各个函数然后显示和保存运行结果或运行日志
     * 与其他部分的接口主要为fileName、filePath（读取文件的地址），以及result（End）（各个程序运行结果）
     * 其他的接口多在其他部分创建public函数
     */

    //建立页面
    private JFrame frame = new JFrame("资源调度与管理设计 ———— 操作系统课程设计第五组");
    //用来存放载入文件的地址
    String fileName;
    String filePath;
    //添加菜单栏，文件菜单栏，用来载入输入文件和保存日志文件
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("文件");
    private JMenuItem open = new JMenuItem("载入输入文件");
    private JMenuItem saveResult = new JMenuItem("保存记录文件");
    private JMenuItem saveMatrix = new JMenuItem("保存输出矩阵");
    
    //显示菜单栏，用来清空显示
    private JMenu displayMenu = new JMenu("显示");
    private JMenuItem clear = new JMenuItem("清空全部显示");
    private JMenuItem clear1 = new JMenuItem("清空输入显示");
    private JMenuItem clear2 = new JMenuItem("清空记录显示");
    //定义一个按钮，开始执行
    private JButton start = new JButton("开始执行");
    //定义一个复选框组
    private ButtonGroup Select = new ButtonGroup();
    //定义一个单选框，初始处于被选中状态,并添加到Select组中
    private JRadioButton BankersAlgorithm = new JRadioButton("银行家算法", true);
    //定义一个单选框，初始处于未被选中状态,并添加到Select组中
    private JRadioButton DeadlockCheck = new JRadioButton("死锁检测", false);
    //定义一个单选框，初始处于未被选中状态
    private JRadioButton DeadlockRecover = new JRadioButton("死锁解除", false);
    //定义一个复选框，初始处于选中状态
    private JCheckBox autoSave = new JCheckBox("自动保存运行结果",false);
    //定义一个下拉选择框
    private JComboBox<String> Tactics = new JComboBox<String>();
    //定义两个文本域
    public JTextArea first = new JTextArea("请点击左上角的文件菜单载入文件！",40, 60);
    public JTextArea finish = new JTextArea(40, 60);
    

    //日志
    public static final Logger logger = Logger.getLogger("我的日志");
    SourceManager sourceManager = new SourceManager();
    
    /**
     * 初始化程序
     * 主要用来添加各部分的监听，以及将各部分组件组合起来
     *
     */

    public void init() {
    	//输入输出文本域
    	JScrollPane scrollPane1 = new JScrollPane();
    	JScrollPane scrollPane2 = new JScrollPane();
    	//垂直水平滚动条
    	scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
    	Select.add(BankersAlgorithm);
    	Select.add(DeadlockCheck);
    	Select.add(DeadlockRecover);
    	
    	//快捷键  ALT + D 打开文件
    	open.setMnemonic(KeyEvent.VK_D);
        //设置菜单栏
        fileMenu.add(open);
        fileMenu.addSeparator();
        fileMenu.add(saveResult);
        fileMenu.addSeparator();
        fileMenu.add(saveMatrix);
        fileMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        displayMenu.add(clear);
        displayMenu.addSeparator();
        displayMenu.add(clear1);
        displayMenu.addSeparator();
        displayMenu.add(clear2);
        displayMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        //添加菜单栏
        menuBar.add(fileMenu);
        menuBar.add(displayMenu);
        frame.setJMenuBar(menuBar);
        
        
        //添加菜单栏监听
        open.addActionListener(new openListen());
        open.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				//鼠标经过事件，变颜色
				open.setForeground(Color.RED);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标离开时，变回来
				open.setForeground(Color.BLACK);
			}
		});
        saveResult.addActionListener(new storeListen());
        saveMatrix.addActionListener(new saveMatrixListen());
        clear.addActionListener(new clearListen());
        clear1.addActionListener(new clear1Listen());
        clear2.addActionListener(new clear2Listen());

        //往下拉选择框中添加内容
        Tactics.addItem("进程撤销法(全部)");
        Tactics.addItem("进程撤销法(部分)");
        Tactics.addItem("资源剥夺法");
        //设置鼠标格式
        Tactics.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
//        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
//        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        //为开始执行按钮添加监听
        start.addActionListener(new startListen());
        start.setCursor(new Cursor(Cursor.HAND_CURSOR));
        start.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				//鼠标经过事件，变颜色
				start.setForeground(Color.RED);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标离开时，变回来
				start.setForeground(Color.BLACK);
			}
		});
        
        JPanel Jfirst = new JPanel();
        JPanel Jfinish = new JPanel();
        //文本域只读，不可编辑
        first.setEditable(false);
        finish.setEditable(false);
        first.setFont(new java.awt.Font("宋体", 0, 18));
        finish.setFont(new java.awt.Font("宋体", 0, 18));
        //字体颜色
        first.setForeground(Color.BLUE);
        finish.setCaretColor(Color.BLACK);
        Jfirst.add(first);
        Jfirst.add(scrollPane1);
        Jfinish.add(finish);
        Jfinish.add(scrollPane2);

        scrollPane1.setViewportView(first);
		scrollPane2.setViewportView(finish);
		
        //创建一个JPanel容器，装载下拉选择框，单选框和复选框
        JPanel checkPanel = new JPanel();
        checkPanel.add(BankersAlgorithm);
        checkPanel.add(DeadlockCheck);
        checkPanel.add(DeadlockRecover);
        checkPanel.add(Tactics);
        checkPanel.add(autoSave);
        //设置鼠标样式
        BankersAlgorithm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        DeadlockCheck.setCursor(new Cursor(Cursor.HAND_CURSOR));
        DeadlockRecover.setCursor(new Cursor(Cursor.HAND_CURSOR));
        autoSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        //为了让按钮大小适中，将按钮添加到Panel容器中
        JPanel bottom = new JPanel();
        bottom.add(start);

        //创建一个垂直排列，装载CheckPanel和确定按钮bottom容器
        Box select = Box.createVerticalBox();
        select.add(checkPanel);
        select.add(bottom);

        //把select添加到Frame的底部
        frame.add(select,BorderLayout.SOUTH);

        //创建一个水平排列的Box容器，装载两个文本域
        Box text = Box.createHorizontalBox();
        text.add(Jfirst);
        text.add(Box.createHorizontalStrut(15));
        text.add(Jfinish);
        
        
        //将top添加到frame的中间区域
        frame.add(text);
        //设置frame最佳大小并可见
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * 各组件监听的功能函数
     * 其中包含了各个算法的调用部分以及一些显示在日志中的文字提示
     *
     */
    
    private class clearListen implements ActionListener { //清空显示
        @Override
        public void actionPerformed(ActionEvent e) {
        	logger.info("管理员于" + new Date().toLocaleString() + "清空显示\n");
            first.setText("");
            finish.setText("");
            finish.append("输入文件已清除，请重新打开输入文件！\n\n");
        }
    }
    private class clear1Listen implements ActionListener { //清空显示
        @Override
        public void actionPerformed(ActionEvent e) {
        	logger.info("管理员于" + new Date().toLocaleString() + "清空显示\n");
            first.setText("");
            finish.append("输入文件已清除，请重新打开输入文件！\n\n");
        }
    }
    private class clear2Listen implements ActionListener { //清空显示
        @Override
        public void actionPerformed(ActionEvent e) {
        	logger.info("管理员于" + new Date().toLocaleString() + "清空显示\n");
            finish.setText("");
        }
    }
    private class openListen implements ActionListener { //打开输入文件
        @Override
        public void actionPerformed(ActionEvent e) {
        	FileScanner fOpen = new FileScanner();
        	logger.info("管理员于" + new Date().toLocaleString() + "开始打开输入文件" + getFileName() +"\n");
            finish.append("\n载入文件中...\n\n");
            //选择文件窗口
            Frame frame = new Frame();
            FileDialog fd = new FileDialog(frame,"请选择要载入的文件",FileDialog.LOAD);
            fd.setVisible(true);

            // 文件路径
            setFileName(fd.getFile());//文件名
            setFilePath(fd.getDirectory());//文件地址

            //是否成功选择文件
            if(fd.getDirectory()!=null && fd.getFile()!=null) {         	
                //检查文件后缀名
                if(!fOpen.InputFileToIntegerArrayList(getFilePath() + getFileName())) {
                	first.setText(fOpen.arrayList+"");
                    finish.append("载入文件"+fd.getDirectory()+fd.getFile()+"失败，请重新载入\n");
                } else {
                	sourceManager.setProcAndSour(fOpen.arrayList);
                	fOpen.ArrayStore(sourceManager);
                	first.setText(sourceManager.showInputMessage());
                    finish.append("已载入输入文件："+fd.getDirectory()+fd.getFile()+"\n");
                }
            } else
                finish.append("未打开文件，请重新打开文件\n");
            logger.info("管理员于" + new Date().toLocaleString() + "文件打开" + getFileName() + "完毕\n");
        }
    }
    private class saveMatrixListen implements ActionListener { //保存矩阵文件

		@Override
		public void actionPerformed(ActionEvent e) {
			isStoreMatrix(true);
			finish.append("\n保存矩阵文件...\n\n");
			FileStore fileStore = new FileStore();
			if(getFilePath() != null && finish.getText() != null) {
                fileStore.IntegerArrayToOutputFile(getFilePath(), sourceManager);
                finish.append("文件已保存至：\n"+fileStore.getFileStorePath()+"\n");
            } else {
                finish.append("保存错误，未打开过输入文件或文件为空！\n");
                logger.warning("于" + new Date().toLocaleString() + "保存错误，未打开过输入文件或记录文件为空！\n");
            }
			isStoreMatrix(false);
		}
    	
    }
    private class storeListen implements ActionListener {//保存记录文件
        @Override
        public void actionPerformed(ActionEvent e) {
        	isStoreFile(true);
            finish.append("\n保存日志文件...\n\n");
            if(getFilePath()!=null && finish.getText()!=null) {
                FileStore fileStore =new FileStore(getFilePath(),finish.getText(), 0);
                finish.append("文件已保存至：\n"+fileStore.getFileStorePath()+"\n");
            } else {
                finish.append("保存错误，未打开过输入文件或记录文件为空！\n");
                logger.warning("于" + new Date().toLocaleString() + "保存错误，未打开过输入文件或记录文件为空！\n");
            }
            isStoreFile(false);
        }
    }
    private class startListen implements ActionListener {//死锁解除
        @Override
        public void actionPerformed(ActionEvent e) {
            if(DeadlockRecover.isSelected()) {
                if(getFilePath() != null || getFileName() != null) {
                	logger.info("管理员于" + new Date().toLocaleString() + "开始执行" + Tactics.getSelectedItem() + "\n");
                    finish.append("\n\n-----------------开始用" + Tactics.getSelectedItem() + "策略执行死锁解除-----------------\n\n");
                    if(Tactics.getSelectedItem().equals("进程撤销法(全部)")){
                    	int model = 4;//用来表示死锁解除的策略，需要传递给死锁解除函数
                    	getMethod(model);
                    } else if(Tactics.getSelectedItem().equals("进程撤销法(部分)")) {//与上一种策略结构相同
                        int model = 3;
                        getMethod(model);
                    } else if(Tactics.getSelectedItem().equals("资源剥夺法")) {//与之前的策略结构相同
                        int model = 5;
                        getMethod(model);
                    }
                    logger.info("管理员于" + new Date().toLocaleString() + "执行" + Tactics.getSelectedItem() + "完毕\n");
                } else {
                	logger.warning("管理员于" + new Date().toLocaleString() + "开始执行" + Tactics.getSelectedItem() + "失败\n");
                    finish.append("执行失败，未读入文件或读入文件错误!\n");
                }

            } else if(DeadlockCheck.isSelected()) {//死锁检测，与死锁解除的各种策略结构相同
                if(getFilePath()!=null||getFileName()!=null) {
                	logger.info("管理员于" + new Date().toLocaleString() + "开始执行死锁检测\n");
                    finish.append("\n\n-----------------开始执行死锁检测-----------------\n\n");
                    int model = 2;
                    getMethod(model);
                    logger.info("管理员于" + new Date().toLocaleString() + "执行死锁检测完毕\n");
                } else {
                	logger.warning("管理员于" + new Date().toLocaleString() + "执行死锁检测失败\n");
                    finish.append("执行失败，未读入文件或读入文件错误！\n");
                }

            } else if(BankersAlgorithm.isSelected()) {//银行家算法，与之前的结构相同
                if(getFilePath()!=null && getFileName()!=null) {
                	logger.info("管理员于" + new Date().toLocaleString() + "开始执行银行家算法\n");
                    finish.append("\n\n-----------------开始执行银行家算法-----------------\n\n");
                    //finish.append("使用银行家算法判断安全状态：\n");
                    int model = 1;
                    getMethod(model);
                    logger.info("管理员于" + new Date().toLocaleString() + "执行银行家算法完毕\n");
                } else {
                	logger.warning("管理员于" + new Date().toLocaleString() + "执行银行家算法失败\n");
                    finish.append("执行失败，未读入文件或读入文件错误！\n");
                }
            }
        }
    }
    
    /**
     * 日志
     * true开始执行
     * false 执行结束
     */
    
    public boolean isStoreMatrix(boolean start) {
    	if(start)
			logger.info("管理员于" + new Date().toLocaleString() + "开始执行矩阵保存\n");
		else 
			logger.info("管理员于" + new Date().toLocaleString() + "保存矩阵完毕\n");
		return true;
    }
    
    public boolean isStoreFile(boolean start) {
		if(start)
			logger.info("管理员于" + new Date().toLocaleString() + "开始执行文件保存\n");
		else 
			logger.info("管理员于" + new Date().toLocaleString() + "保存文件完毕\n");
		return true;
	}
	
	static class MyFormatter extends Formatter {
		public MyFormatter() {}
		@Override
		public String format(LogRecord record) {
			String logStr = String.format("\n%s [%s]\n%s %s - %s.%s\n",
					record.getLoggerName(),
					record.getLevel(),
					record.getMessage(),
					new Date(record.getMillis()).toLocaleString(),
					record.getSourceClassName(),
					record.getSourceMethodName()
			);
			return logStr;
		}
	}
	static {
		//更改日志文件的默认存放路径
		FileHandler fileHandler;
		ConsoleHandler consoleHandler;
		try {
			fileHandler = new FileHandler("src/OperatingSystem/Store.log", true);
			//使用默认的格式化器，格式化日志文件中日志内容
			//fileHandler.setFormatter(new SimpleFormatter());
			fileHandler.setFormatter(new MyFormatter());
			consoleHandler = new ConsoleHandler();
			consoleHandler.setFormatter(new MyFormatter());
			logger.addHandler(fileHandler);
			logger.addHandler(consoleHandler);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    
    /**
     * 一些set函数和get函数
     *
     */
    
    private void setFileName(String s){
        this.fileName = s;
    }
    private void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getFileName() {
        return fileName;
    }
    public String getFilePath() {
        return filePath;
    }
    
    public void getMethod(int model) {
    	//调用相应方法

    	switch(model) {
		case 1 : sourceManager.requestSourceToEXEBanker(); break;
		case 2 : sourceManager.deadlockDection(); break;
		case 3 : sourceManager.deadlockRecover_PartDeadlockProcess(); break; 
		case 4 : sourceManager.deadlockRecover_ALLDeadlockProcess(); break;
		case 5 : sourceManager.deadlockRecover_SuspendDeadlockProcess(); break;
    	}
        String result = sourceManager.showMessage();//返回运行结果
        finish.append(result);//显示结果
        if(autoSave.isSelected()) {//检查是否要保存结果
            if(result != null) {
                FileStore fileStore =new FileStore(getFilePath(), result, model);
                finish.append("\n运行结果已保存至：\n" + fileStore.getFileStorePath() + "\n");
            } else{
                finish.append("运行结果保存错误，运行结果为空！\n");
            }
        } else{
        	finish.append("\n运行结果未保存！\n\n");
        }
    }

    /**
     * 主函数
     * @param args
     */
    public static void main(String[] args){
    	logger.info("-------------------------------------------------\n"
    			+ "程序于" + new Date().toLocaleString() + "开始执行\n");
        Windows win = new Windows();
        win.init();
        win.frame.addWindowListener(new WindowAdapter() {
        	
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                logger.info("程序于" + new Date().toLocaleString() + "执行完毕\n"
                		+ "-------------------------------------------------\n");
                System.exit(0);
            }
        });
    }
}
