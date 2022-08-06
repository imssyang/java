package garbage_collection.weakreference.Test1;

import java.lang.ref.WeakReference;

public class TestWeakReference {

    private static void Test1() {
        Car car = new Car(22000,"silver");
        WeakReference<Car> weakCar = new WeakReference<>(car);
        int i=0;
        while(true){
            if(weakCar.get()!=null){
                i++;
                System.out.println("Object is alive for "+i+" loops - "+weakCar);
            }else{
                System.out.println("Object has been collected.");
                break;
            }
        }
    }

    private static void Test2() {
        Car car = new Car(22000,"silver");
        WeakReference<Car> weakCar = new WeakReference<>(car);
        int i=0;
        while(true){
            System.out.println("here is the strong reference 'car' " + car);
            if(weakCar.get()!=null){
                i++;
                System.out.println("Object is alive for "+i+" loops - " + weakCar);
            }else{
                System.out.println("Object has been collected.");
                break;
            }
        }
    }

    public static void main(String[] args) {
        Test1(); //will gc car
        //Test2();  //always alive!!!
    }
}
