
class Test{
    public static void main(String[] a){
    }
}

class A{
    public A test () {return new B();}
}

class B extends A{
    public B test () {return new B();}
}

