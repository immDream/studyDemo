import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: MyIterator PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/15/15:24
 */
public class Client {
    static Class aClass;
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        aClass = new Class();
        System.out.println("请依次输入五名学生的信息（学号、姓名、年龄）用空格分开，输入null结束:");
        String info = null;
        while(!(info = sc.nextLine()).equals("null")) {
            Student student=new Student();
            String[] stuInfo = info.split(" ");
            student.setId(stuInfo[0]);
            student.setName(stuInfo[1]);
            student.setAge(Integer.parseInt(stuInfo[2]));
            aClass.students.add(student);
        }
        show();
    }

    public static void show(){
        Iterator<Student> iterator =aClass.students.iterator();
        System.out.println("学生信息：");
        while(iterator.hasNext()){
            Student student=iterator.next();
            System.out.println("学号："+student.getId()+" "+"姓名："+student.getName()+" "+"年龄："+student.age);
        }

        Collections.sort(aClass.students,new Student.AgeComparator());
        Iterator<Student> it=aClass.students.iterator();
        System.out.println("按年龄由大到小排序：");
        while(it.hasNext()){
            Student student=it.next();
            System.out.println("学号："+student.getId()+" "+"姓名："+student.getName()+" "+"年龄："+student.age);
        }

    }
}
