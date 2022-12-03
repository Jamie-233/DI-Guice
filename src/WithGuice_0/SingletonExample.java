package WithGuice_0;

import com.google.inject.*;

import javax.inject.Inject;

// 所有在 constructor 声明 @Inject 都会创建出来并塞回 hashMap 中

interface Car {
    int drive();
}

// method one: add implementation class e.g cache db connection
@Singleton
final class BMW implements Car {
    @Inject
    BMW() {}

    @Override
    public int drive() {
        return 60;
    }

    public static BMW create() {
        return new BMW();
    }
}

final class Owner {
    private final Car car;

    @Inject
    Owner(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }
}

final class CarModule extends AbstractModule {

    @Override
    protected void configure() {
        // method one: bind
        // bind interface to concrete (implementation class)
        // bind(Car.class).to(BMW.class); // .in(Singleton.class);

        // method two: bind to instance
        // bind(Car.class).toInstance(BMW.create()); // usage factory method
    }

    // method three: provides
    // @Singleton
     @Provides
     Car provideCar() {
        // return value set to hashMap
         return BMW.create();
     }
}

public class SingletonExample {
    public static void main(String[] args) {
        // hashMap establish relationship
        Injector injector = Guice.createInjector(new CarModule());

        // hashMap get key
        Owner owner = injector.getInstance(Owner.class);

        System.out.println("with guice car can drive at speed = " + owner.getCar().drive());
    }
}
