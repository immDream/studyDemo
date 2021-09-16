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
 * 带日志的文件存储
 * @author 庄宇
 * @date 2021-8-28
 */

public class FileStore {
	//输出矩阵路径
	private static final String FilePath = "src/OperatingSystem/Output.out";
	//文件存储路径
	private static String fileStorePath;
	
	public FileStore() {};
	// 将文本框内容存储到文件
	public FileStore(String filePath, String text, int flag) {
		try {
			int i = 1;
			File file;
			while(true) {
				fileStorePath = filePath + getFileName(flag) + "(" + i +").out";
				file = new File(fileStorePath);//新建文件及命名
	            //File file = new File(FilePath);
				
	            //如果文件不存在就新建
				if(!file.exists()) {
					file.createNewFile();
					break;
				} else {//文件存在更改文件名
					i++;
				}
			}
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.append("\n[INFO]管理员于" + new Date().toLocaleString() + "开始执行文件保存\n");
			//将数据存入文件
			fileWriter.append(text);
			fileWriter.append("[INFO]管理员于" + new Date().toLocaleString() + "保存文件完毕\n");
			fileWriter.flush();
			fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private String getFileName(int flag) {
		switch(flag) {
			case 1 : return "银行家算法运行结果";
			case 2 : return "死锁检测运行结果";
			case 3 : return "部分进程撤销法运行结果"; 
			case 4 : return "全部进程撤销法运行结果";
			case 5 : return "资源剥夺法运行结果";
			default :
				return "运行日志";
		}
	}
	// 获取文件存储路径
	public String getFileStorePath() {
		return fileStorePath;
	}
	
	
	
	
	
	
	
	/**
	 * 按一定顺序将数组存储到文件中
	 * @param sourceManager
	 */
	public void IntegerArrayToOutputFile(String filePath, SourceManager sourceManager) {
		try {
			File file = new File(FilePath);//新建文件及命名
//          OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(FilePath));
			FileWriter output = new FileWriter(file, true);
			output.append("\n[INFO]管理员于" + new Date().toLocaleString() + "开始执行矩阵保存\n");
			//将数据存入文件
			output.append("输出矩阵为：\r\n");
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
            output.append("[INFO]管理员于" + new Date().toLocaleString() + "保存矩阵完毕\n");
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
	        //如果文件不存在就新建
			FileWriter output = new FileWriter(FilePath, false);
			fileStore(str, output);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 文件存储
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
