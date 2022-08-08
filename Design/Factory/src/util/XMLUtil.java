package util;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 *
 * @see: 工厂模式 util
 * @author: immDream
 * @since: 2022/03/18/9:36
 */
public class XMLUtil {
    public static Object getBean() {
        try {
            // 创建DOM文档对象
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document;
            String path = "D:\\IDEA Projects\\Design\\Factory\\src\\XMLUtil.xml";
            File file = new File(path);
            document = documentBuilder.parse(file);

            // 获取包含类名的文本节点
            NodeList nodeList = document.getElementsByTagName("className");
            Node classNode = nodeList.item(0).getFirstChild();
            String cName = classNode.getNodeValue();

            // 通过类名生成实例对象并返回
            Class c = Class.forName("factory."+cName);
            Object obj = c.newInstance();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
