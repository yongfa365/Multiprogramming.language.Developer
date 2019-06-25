package yongfa365.BuilderInInherit1;

public class Tester {
    public static void main(String[] args) {
        var item = Child.builder().childName("B.b").parentName("A.a").build();
        System.out.println(item);
    }
}
