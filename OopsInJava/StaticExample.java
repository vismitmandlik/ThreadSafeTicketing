class StaticExample {
    static void method(){
        int a=0;
        System.out.println(a);
    }

    public static void main(String[] args){
        System.out.println("Hello");
        StaticExample.method();
    }
}

class base extends StaticExample{
    static void method(){
        int b=10;
        System.out.println(b);
    }
}
