import java.util.HashMap;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: 解释器模式 PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/15/14:39
 */
public class Calculator {
    private String statement;
    private Node node;

    public void build(String statement) {
        //堆栈，运算顺序
        Stack<Node> stack = new Stack<>();

        //将表达式拆分为字符数组
        String[] ex = statement.split(" ");
        // 运算
        Node left = null;
        Node right = null;
        for (int i = 0; i < ex.length; i++) {
            switch (ex[i]) {
                case "+": // 加法
                    left = stack.pop();//将左边的数弹出来
                    StringBuilder sb = new StringBuilder();
                    right = new ValueNode(Double.parseDouble(ex[++i] + ""));//得到右边的数
                    stack.push(new AddNode(left, right));//将加法结果push进去
                    break;

                case "-": // 减法
                    left = stack.pop();//将左边的数弹出来
                    right = new ValueNode(Double.parseDouble(ex[++i] + ""));//得到右边的数
                    stack.push(new SubNode(left, right));//将减法结果push进去
                    break;
                default: // 将运算的元素push进去
                    stack.push(new ValueNode(Double.parseDouble(ex[i]+"")));
            }
        }
        // 把运算结果抛出来
        this.node = stack.pop();
    }

    // 计算结果
    public double calculate() {
        return this.node.interpret();
    }
}
