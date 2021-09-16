package OperatingSystem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * 
 * @author ׯ��
 * @date 2021-8-26
 */
public class FileScanner {
	// 1����ȡ����������Դ���洢��proc sour
	// 2�����ݾ������ͷֱ𽫺������ݴ洢����Ӧ������
	// 3��������Ӣ���ַ�������������ͣ�����proc sour����������
	
//	private static final String FilePath = "src/OperatingSystem/input.in";
	
	public ArrayList<String> arrayList = new ArrayList<>();
	
	/**
	 * ���ļ�
	 * @throws IOException
	 */
	protected boolean InputFileToIntegerArrayList(String FilePath) {
		String suffix = FilePath.substring(FilePath.length() - 3);
		if(!suffix.equals(".in")) {
			//�ļ���׺����
			arrayList.add("�ļ���׺����");
			return false;
		}
        try {
        	InputStreamReader input = new InputStreamReader(new FileInputStream(FilePath));
            BufferedReader bf = new BufferedReader(input);
            // �������ַ���
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
	 * ʹ���ļ��е�����ת���ɵ�ArrayList�е�����
	 * ����������е����Խ��г�ʼ��
	 * @param sourceManager �������Ķ���
	 */
	protected void ArrayStore(SourceManager sourceManager) {
		if(sourceManager == null) return;
		
		// ��ArrayList�д洢���ַ������д���
        int length = arrayList.size();
        if(length == 0 || arrayList == null) {
        	System.err.println("����Ϊ��");
        	return;
        }
        //dection.setProcAndSour(arrayList);
        // ���ÿһ���ַ�����Ѱ����Ӧ����
        for(int i = 1; i < length; i ++) {
            ArrayTypeFactory(sourceManager, arrayList, i);
        }
        
	}
	
	
	/**
	 * 
	 * @param sourceManager �޸Ķ���
	 * @param type �����ַ���ѡ������
	 * @param i ���ļ��еĶ�ȡλ��ָ��
	 */
	private void ArrayTypeFactory(SourceManager sourceManager, ArrayList<String> type, int i) {
		if(i < 0 || i + 1 >= type.size() || type.get(i + 1) == null) {
			//System.err.println("��ָ��");
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
