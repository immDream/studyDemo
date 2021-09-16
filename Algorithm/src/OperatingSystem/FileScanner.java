package OperatingSystem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * 
 * @author 庄宇
 * @date 2021-8-26
 */
public class FileScanner {
	// 1、读取进程数和资源数存储到proc sour
	// 2、根据矩阵类型分别将后续数据存储到相应矩阵中
	// 3、规则：以英文字符串定义矩阵类型，根据proc sour定义矩阵结束
	
//	private static final String FilePath = "src/OperatingSystem/input.in";
	
	public ArrayList<String> arrayList = new ArrayList<>();
	
	/**
	 * 读文件
	 * @throws IOException
	 */
	protected boolean InputFileToIntegerArrayList(String FilePath) {
		String suffix = FilePath.substring(FilePath.length() - 3);
		if(!suffix.equals(".in")) {
			//文件后缀错误
			arrayList.add("文件后缀错误！");
			return false;
		}
        try {
        	InputStreamReader input = new InputStreamReader(new FileInputStream(FilePath));
            BufferedReader bf = new BufferedReader(input);
            // 按输入字符串
            String str;
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            input.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
	}
	
	/**
	 * 使用文件中的数据转换成的ArrayList中的数据
	 * 对死锁检测中的属性进行初始化
	 * @param sourceManager 死锁检测的对象
	 */
	protected void ArrayStore(SourceManager sourceManager) {
		if(sourceManager == null) return;
		
		// 对ArrayList中存储的字符串进行处理
        int length = arrayList.size();
        if(length == 0 || arrayList == null) {
        	System.err.println("数组为空");
        	return;
        }
        //dection.setProcAndSour(arrayList);
        // 检测每一行字符串，寻找相应类型
        for(int i = 1; i < length; i ++) {
            ArrayTypeFactory(sourceManager, arrayList, i);
        }
        
	}
	
	
	/**
	 * 
	 * @param sourceManager 修改对象
	 * @param type 根据字符串选择数组
	 * @param i 在文件中的读取位置指针
	 */
	private void ArrayTypeFactory(SourceManager sourceManager, ArrayList<String> type, int i) {
		if(i < 0 || i + 1 >= type.size() || type.get(i + 1) == null) {
			//System.err.println("空指针");
			return ;
		}
		switch(type.get(i)) {
		case "Available" :
			sourceManager.setAvailable(type, ++i);
			break;
		case "Max" :
			sourceManager.setMax(type, ++i);
			break;
		case "Allocation" :
			sourceManager.setAllocation(type,	++i);
			break;
		case "Need" :
			sourceManager.setNeed(type, ++i);
			break;
		case "Request" :
			sourceManager.setRequest(type, ++i);
			break;
		default :
			break;
		}
	}

}
