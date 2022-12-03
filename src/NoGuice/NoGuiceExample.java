package NoGuice;

interface Car {
    int drive();
}

final class BMW implements Car {
    @Override
    public int drive() {
        return 60;
    }
}

final class Owner {
    private Car car;
    public Owner(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }
}

public class NoGuiceExample {
    public static void main(String[] args) {
        Owner owner = new Owner(new BMW());
        System.out.println("without guice car can drive at speed = " + owner.getCar().drive());
    }
}
