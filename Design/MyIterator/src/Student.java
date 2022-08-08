import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: MyIterator PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/15/15:24
 */

public class Student{
    String id;
    String name;
    int age;

    public Student(){}

    public Student(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static class AgeComparator implements Comparator<Student>{

        /**
         * 从大 -> 小排序
         * @param o1
         * @param o2
         * @return
         */
        @Override
        public int compare(Student o1, Student o2) {
            return o2.age-o1.age;
        }
    }

}
