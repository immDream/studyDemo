package OperatingSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


/**
 * ����־���ļ��洢
 * @author ׯ��
 * @date 2021-8-28
 */

public class FileStore {
	//�������·��
	private static final String FilePath = "src/OperatingSystem/Output.out";
	//�ļ��洢·��
	private static String fileStorePath;
	
	public FileStore() {};
	// ���ı������ݴ洢���ļ�
	public FileStore(String filePath, String text, int flag) {
		try {
			int i = 1;
			File file;
			while(true) {
				fileStorePath = filePath + getFileName(flag) + "(" + i +").out";
				file = new File(fileStorePath);//�½��ļ�������
	            //File file = new File(FilePath);
				
	            //����ļ������ھ��½�
				if(!file.exists()) {
					file.createNewFile();
					break;
				} else {//�ļ����ڸ����ļ���
					i++;
				}
			}
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.append("\n[INFO]����Ա��" + new Date().toLocaleString() + "��ʼִ���ļ�����\n");
			//�����ݴ����ļ�
			fileWriter.append(text);
			fileWriter.append("[INFO]����Ա��" + new Date().toLocaleString() + "�����ļ����\n");
			fileWriter.flush();
			fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private String getFileName(int flag) {
		switch(flag) {
			case 1 : return "���м��㷨���н��";
			case 2 : return "����������н��";
			case 3 : return "���ֽ��̳��������н��"; 
			case 4 : return "ȫ�����̳��������н��";
			case 5 : return "��Դ���ᷨ���н��";
			default :
				return "������־";
		}
	}
	// ��ȡ�ļ��洢·��
	public String getFileStorePath() {
		return fileStorePath;
	}
	
	
	
	
	
	
	
	/**
	 * ��һ��˳������洢���ļ���
	 * @param sourceManager
	 */
	public void IntegerArrayToOutputFile(String filePath, SourceManager sourceManager) {
		try {
			File file = new File(FilePath);//�½��ļ�������
//          OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(FilePath));
			FileWriter output = new FileWriter(file, true);
			output.append("\n[INFO]����Ա��" + new Date().toLocaleString() + "��ʼִ�о��󱣴�\n");
			//�����ݴ����ļ�
			output.append("�������Ϊ��\r\n");
			output.append(sourceManager.getProc() + " " + sourceManager.getSour() + "\r\n");
			fileStore(sourceManager.getAvailable(), output);
            output.append("Request\r\n");
            fileStore(sourceManager.getRequest(), output);
            output.append("Allocation\r\n");
            fileStore(sourceManager.getAllocation(), output);
            output.append("Need\r\n");
            fileStore(sourceManager.getNeed(), output);
            output.append("Max\r\n");
            fileStore(sourceManager.getMax(), output);
            fileStorePath = FilePath;
            output.append("[INFO]����Ա��" + new Date().toLocaleString() + "����������\n");
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	public void StringToOutputFile(String str) {
		try {
			File file = new File(FilePath);	
			if(!file.exists()) file.createNewFile();
//			else {
//				file.delete();
//				file.createNewFile();
//			}
	        //����ļ������ھ��½�
			FileWriter output = new FileWriter(FilePath, false);
			fileStore(str, output);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * �ļ��洢
	 * @param array
	 * @param output
	 */
	private void fileStore(int[] array, FileWriter output) {
		if(array == null || array.length == 0) {
			return;
		}
		try {
			output.append("Available\r\n");
			for(int i = 0; i < array.length; i++) {
	        	output.write(array[i] + " ");
	        }
			output.write("\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	private void fileStore(int[][] array, FileWriter output) {
		if(array == null || array.length == 0) {
			return;
		}
		try {
			for(int i = 0; i < array.length; i++) {
				for(int j = 0; j < array[i].length; j++) {
		        	output.write(array[i][j] + " ");
		        	
				}
				output.write("\r\n");
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void fileStore(String str, FileWriter output) {
		try {
			output.write(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
