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
     * ҳ���������֣���������ҳ��ĸ�����������ҵ��ø�������Ȼ����ʾ�ͱ������н����������־
     * ���������ֵĽӿ���ҪΪfileName��filePath����ȡ�ļ��ĵ�ַ�����Լ�result��End���������������н����
     * �����Ľӿڶ����������ִ���public����
     */

    //����ҳ��
    private JFrame frame = new JFrame("��Դ������������ �������� ����ϵͳ�γ���Ƶ�����");
    //������������ļ��ĵ�ַ
    String fileName;
    String filePath;
    //��Ӳ˵������ļ��˵������������������ļ��ͱ�����־�ļ�
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("�ļ�");
    private JMenuItem open = new JMenuItem("���������ļ�");
    private JMenuItem saveResult = new JMenuItem("�����¼�ļ�");
    private JMenuItem saveMatrix = new JMenuItem("�����������");
    
    //��ʾ�˵��������������ʾ
    private JMenu displayMenu = new JMenu("��ʾ");
    private JMenuItem clear = new JMenuItem("���ȫ����ʾ");
    private JMenuItem clear1 = new JMenuItem("���������ʾ");
    private JMenuItem clear2 = new JMenuItem("��ռ�¼��ʾ");
    //����һ����ť����ʼִ��
    private JButton start = new JButton("��ʼִ��");
    //����һ����ѡ����
    private ButtonGroup Select = new ButtonGroup();
    //����һ����ѡ�򣬳�ʼ���ڱ�ѡ��״̬,����ӵ�Select����
    private JRadioButton BankersAlgorithm = new JRadioButton("���м��㷨", true);
    //����һ����ѡ�򣬳�ʼ����δ��ѡ��״̬,����ӵ�Select����
    private JRadioButton DeadlockCheck = new JRadioButton("�������", false);
    //����һ����ѡ�򣬳�ʼ����δ��ѡ��״̬
    private JRadioButton DeadlockRecover = new JRadioButton("�������", false);
    //����һ����ѡ�򣬳�ʼ����ѡ��״̬
    private JCheckBox autoSave = new JCheckBox("�Զ��������н��",false);
    //����һ������ѡ���
    private JComboBox<String> Tactics = new JComboBox<String>();
    //���������ı���
    public JTextArea first = new JTextArea("�������Ͻǵ��ļ��˵������ļ���",40, 60);
    public JTextArea finish = new JTextArea(40, 60);
    

    //��־
    public static final Logger logger = Logger.getLogger("�ҵ���־");
    SourceManager sourceManager = new SourceManager();
    
    /**
     * ��ʼ������
     * ��Ҫ������Ӹ����ֵļ������Լ�������������������
     *
     */

    public void init() {
    	//��������ı���
    	JScrollPane scrollPane1 = new JScrollPane();
    	JScrollPane scrollPane2 = new JScrollPane();
    	//��ֱˮƽ������
    	scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
    	Select.add(BankersAlgorithm);
    	Select.add(DeadlockCheck);
    	Select.add(DeadlockRecover);
    	
    	//��ݼ�  ALT + D ���ļ�
    	open.setMnemonic(KeyEvent.VK_D);
        //���ò˵���
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
        
        //��Ӳ˵���
        menuBar.add(fileMenu);
        menuBar.add(displayMenu);
        frame.setJMenuBar(menuBar);
        
        
        //��Ӳ˵�������
        open.addActionListener(new openListen());
        open.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				//��꾭���¼�������ɫ
				open.setForeground(Color.RED);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// ����뿪ʱ�������
				open.setForeground(Color.BLACK);
			}
		});
        saveResult.addActionListener(new storeListen());
        saveMatrix.addActionListener(new saveMatrixListen());
        clear.addActionListener(new clearListen());
        clear1.addActionListener(new clear1Listen());
        clear2.addActionListener(new clear2Listen());

        //������ѡ������������
        Tactics.addItem("���̳�����(ȫ��)");
        Tactics.addItem("���̳�����(����)");
        Tactics.addItem("��Դ���ᷨ");
        //��������ʽ
        Tactics.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
//        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
//        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        //Ϊ��ʼִ�а�ť��Ӽ���
        start.addActionListener(new startListen());
        start.setCursor(new Cursor(Cursor.HAND_CURSOR));
        start.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				//��꾭���¼�������ɫ
				start.setForeground(Color.RED);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// ����뿪ʱ�������
				start.setForeground(Color.BLACK);
			}
		});
        
        JPanel Jfirst = new JPanel();
        JPanel Jfinish = new JPanel();
        //�ı���ֻ�������ɱ༭
        first.setEditable(false);
        finish.setEditable(false);
        first.setFont(new java.awt.Font("����", 0, 18));
        finish.setFont(new java.awt.Font("����", 0, 18));
        //������ɫ
        first.setForeground(Color.BLUE);
        finish.setCaretColor(Color.BLACK);
        Jfirst.add(first);
        Jfirst.add(scrollPane1);
        Jfinish.add(finish);
        Jfinish.add(scrollPane2);

        scrollPane1.setViewportView(first);
		scrollPane2.setViewportView(finish);
		
        //����һ��JPanel������װ������ѡ��򣬵�ѡ��͸�ѡ��
        JPanel checkPanel = new JPanel();
        checkPanel.add(BankersAlgorithm);
        checkPanel.add(DeadlockCheck);
        checkPanel.add(DeadlockRecover);
        checkPanel.add(Tactics);
        checkPanel.add(autoSave);
        //���������ʽ
        BankersAlgorithm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        DeadlockCheck.setCursor(new Cursor(Cursor.HAND_CURSOR));
        DeadlockRecover.setCursor(new Cursor(Cursor.HAND_CURSOR));
        autoSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        //Ϊ���ð�ť��С���У�����ť��ӵ�Panel������
        JPanel bottom = new JPanel();
        bottom.add(start);

        //����һ����ֱ���У�װ��CheckPanel��ȷ����ťbottom����
        Box select = Box.createVerticalBox();
        select.add(checkPanel);
        select.add(bottom);

        //��select��ӵ�Frame�ĵײ�
        frame.add(select,BorderLayout.SOUTH);

        //����һ��ˮƽ���е�Box������װ�������ı���
        Box text = Box.createHorizontalBox();
        text.add(Jfirst);
        text.add(Box.createHorizontalStrut(15));
        text.add(Jfinish);
        
        
        //��top��ӵ�frame���м�����
        frame.add(text);
        //����frame��Ѵ�С���ɼ�
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * ����������Ĺ��ܺ���
     * ���а����˸����㷨�ĵ��ò����Լ�һЩ��ʾ����־�е�������ʾ
     *
     */
    
    private class clearListen implements ActionListener { //�����ʾ
        @Override
        public void actionPerformed(ActionEvent e) {
        	logger.info("����Ա��" + new Date().toLocaleString() + "�����ʾ\n");
            first.setText("");
            finish.setText("");
            finish.append("�����ļ�������������´������ļ���\n\n");
        }
    }
    private class clear1Listen implements ActionListener { //�����ʾ
        @Override
        public void actionPerformed(ActionEvent e) {
        	logger.info("����Ա��" + new Date().toLocaleString() + "�����ʾ\n");
            first.setText("");
            finish.append("�����ļ�������������´������ļ���\n\n");
        }
    }
    private class clear2Listen implements ActionListener { //�����ʾ
        @Override
        public void actionPerformed(ActionEvent e) {
        	logger.info("����Ա��" + new Date().toLocaleString() + "�����ʾ\n");
            finish.setText("");
        }
    }
    private class openListen implements ActionListener { //�������ļ�
        @Override
        public void actionPerformed(ActionEvent e) {
        	FileScanner fOpen = new FileScanner();
        	logger.info("����Ա��" + new Date().toLocaleString() + "��ʼ�������ļ�" + getFileName() +"\n");
            finish.append("\n�����ļ���...\n\n");
            //ѡ���ļ�����
            Frame frame = new Frame();
            FileDialog fd = new FileDialog(frame,"��ѡ��Ҫ������ļ�",FileDialog.LOAD);
            fd.setVisible(true);

            // �ļ�·��
            setFileName(fd.getFile());//�ļ���
            setFilePath(fd.getDirectory());//�ļ���ַ

            //�Ƿ�ɹ�ѡ���ļ�
            if(fd.getDirectory()!=null && fd.getFile()!=null) {         	
                //����ļ���׺��
                if(!fOpen.InputFileToIntegerArrayList(getFilePath() + getFileName())) {
                	first.setText(fOpen.arrayList+"");
                    finish.append("�����ļ�"+fd.getDirectory()+fd.getFile()+"ʧ�ܣ�����������\n");
                } else {
                	sourceManager.setProcAndSour(fOpen.arrayList);
                	fOpen.ArrayStore(sourceManager);
                	first.setText(sourceManager.showInputMessage());
                    finish.append("�����������ļ���"+fd.getDirectory()+fd.getFile()+"\n");
                }
            } else
                finish.append("δ���ļ��������´��ļ�\n");
            logger.info("����Ա��" + new Date().toLocaleString() + "�ļ���" + getFileName() + "���\n");
        }
    }
    private class saveMatrixListen implements ActionListener { //��������ļ�

		@Override
		public void actionPerformed(ActionEvent e) {
			isStoreMatrix(true);
			finish.append("\n��������ļ�...\n\n");
			FileStore fileStore = new FileStore();
			if(getFilePath() != null && finish.getText() != null) {
                fileStore.IntegerArrayToOutputFile(getFilePath(), sourceManager);
                finish.append("�ļ��ѱ�������\n"+fileStore.getFileStorePath()+"\n");
            } else {
                finish.append("�������δ�򿪹������ļ����ļ�Ϊ�գ�\n");
                logger.warning("��" + new Date().toLocaleString() + "�������δ�򿪹������ļ����¼�ļ�Ϊ�գ�\n");
            }
			isStoreMatrix(false);
		}
    	
    }
    private class storeListen implements ActionListener {//�����¼�ļ�
        @Override
        public void actionPerformed(ActionEvent e) {
        	isStoreFile(true);
            finish.append("\n������־�ļ�...\n\n");
            if(getFilePath()!=null && finish.getText()!=null) {
                FileStore fileStore =new FileStore(getFilePath(),finish.getText(), 0);
                finish.append("�ļ��ѱ�������\n"+fileStore.getFileStorePath()+"\n");
            } else {
                finish.append("�������δ�򿪹������ļ����¼�ļ�Ϊ�գ�\n");
                logger.warning("��" + new Date().toLocaleString() + "�������δ�򿪹������ļ����¼�ļ�Ϊ�գ�\n");
            }
            isStoreFile(false);
        }
    }
    private class startListen implements ActionListener {//�������
        @Override
        public void actionPerformed(ActionEvent e) {
            if(DeadlockRecover.isSelected()) {
                if(getFilePath() != null || getFileName() != null) {
                	logger.info("����Ա��" + new Date().toLocaleString() + "��ʼִ��" + Tactics.getSelectedItem() + "\n");
                    finish.append("\n\n-----------------��ʼ��" + Tactics.getSelectedItem() + "����ִ���������-----------------\n\n");
                    if(Tactics.getSelectedItem().equals("���̳�����(ȫ��)")){
                    	int model = 4;//������ʾ��������Ĳ��ԣ���Ҫ���ݸ������������
                    	getMethod(model);
                    } else if(Tactics.getSelectedItem().equals("���̳�����(����)")) {//����һ�ֲ��Խṹ��ͬ
                        int model = 3;
                        getMethod(model);
                    } else if(Tactics.getSelectedItem().equals("��Դ���ᷨ")) {//��֮ǰ�Ĳ��Խṹ��ͬ
                        int model = 5;
                        getMethod(model);
                    }
                    logger.info("����Ա��" + new Date().toLocaleString() + "ִ��" + Tactics.getSelectedItem() + "���\n");
                } else {
                	logger.warning("����Ա��" + new Date().toLocaleString() + "��ʼִ��" + Tactics.getSelectedItem() + "ʧ��\n");
                    finish.append("ִ��ʧ�ܣ�δ�����ļ�������ļ�����!\n");
                }

            } else if(DeadlockCheck.isSelected()) {//������⣬����������ĸ��ֲ��Խṹ��ͬ
                if(getFilePath()!=null||getFileName()!=null) {
                	logger.info("����Ա��" + new Date().toLocaleString() + "��ʼִ���������\n");
                    finish.append("\n\n-----------------��ʼִ���������-----------------\n\n");
                    int model = 2;
                    getMethod(model);
                    logger.info("����Ա��" + new Date().toLocaleString() + "ִ������������\n");
                } else {
                	logger.warning("����Ա��" + new Date().toLocaleString() + "ִ���������ʧ��\n");
                    finish.append("ִ��ʧ�ܣ�δ�����ļ�������ļ�����\n");
                }

            } else if(BankersAlgorithm.isSelected()) {//���м��㷨����֮ǰ�Ľṹ��ͬ
                if(getFilePath()!=null && getFileName()!=null) {
                	logger.info("����Ա��" + new Date().toLocaleString() + "��ʼִ�����м��㷨\n");
                    finish.append("\n\n-----------------��ʼִ�����м��㷨-----------------\n\n");
                    //finish.append("ʹ�����м��㷨�жϰ�ȫ״̬��\n");
                    int model = 1;
                    getMethod(model);
                    logger.info("����Ա��" + new Date().toLocaleString() + "ִ�����м��㷨���\n");
                } else {
                	logger.warning("����Ա��" + new Date().toLocaleString() + "ִ�����м��㷨ʧ��\n");
                    finish.append("ִ��ʧ�ܣ�δ�����ļ�������ļ�����\n");
                }
            }
        }
    }
    
    /**
     * ��־
     * true��ʼִ��
     * false ִ�н���
     */
    
    public boolean isStoreMatrix(boolean start) {
    	if(start)
			logger.info("����Ա��" + new Date().toLocaleString() + "��ʼִ�о��󱣴�\n");
		else 
			logger.info("����Ա��" + new Date().toLocaleString() + "����������\n");
		return true;
    }
    
    public boolean isStoreFile(boolean start) {
		if(start)
			logger.info("����Ա��" + new Date().toLocaleString() + "��ʼִ���ļ�����\n");
		else 
			logger.info("����Ա��" + new Date().toLocaleString() + "�����ļ����\n");
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
		//������־�ļ���Ĭ�ϴ��·��
		FileHandler fileHandler;
		ConsoleHandler consoleHandler;
		try {
			fileHandler = new FileHandler("src/OperatingSystem/Store.log", true);
			//ʹ��Ĭ�ϵĸ�ʽ��������ʽ����־�ļ�����־����
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
     * һЩset������get����
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
    	//������Ӧ����

    	switch(model) {
		case 1 : sourceManager.requestSourceToEXEBanker(); break;
		case 2 : sourceManager.deadlockDection(); break;
		case 3 : sourceManager.deadlockRecover_PartDeadlockProcess(); break; 
		case 4 : sourceManager.deadlockRecover_ALLDeadlockProcess(); break;
		case 5 : sourceManager.deadlockRecover_SuspendDeadlockProcess(); break;
    	}
        String result = sourceManager.showMessage();//�������н��
        finish.append(result);//��ʾ���
        if(autoSave.isSelected()) {//����Ƿ�Ҫ������
            if(result != null) {
                FileStore fileStore =new FileStore(getFilePath(), result, model);
                finish.append("\n���н���ѱ�������\n" + fileStore.getFileStorePath() + "\n");
            } else{
                finish.append("���н������������н��Ϊ�գ�\n");
            }
        } else{
        	finish.append("\n���н��δ���棡\n\n");
        }
    }

    /**
     * ������
     * @param args
     */
    public static void main(String[] args){
    	logger.info("-------------------------------------------------\n"
    			+ "������" + new Date().toLocaleString() + "��ʼִ��\n");
        Windows win = new Windows();
        win.init();
        win.frame.addWindowListener(new WindowAdapter() {
        	
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                logger.info("������" + new Date().toLocaleString() + "ִ�����\n"
                		+ "-------------------------------------------------\n");
                System.exit(0);
            }
        });
    }
}
