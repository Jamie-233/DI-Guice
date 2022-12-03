package WithGuice_1_Singleton;

import com.google.inject.*;

interface Car {
    int drive();
}

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

@Singleton
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
        // bind(Car.class).to(BMW.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    Car provideCar() {
        // Singleton use case
        // 1. expensive db connection
        // 2. cache object all dev use the same cache
        // call api => response save to cache
        return BMW.create();
    }
}

public class SingletonExample {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new CarModule());

        Owner owner = injector.getInstance(Owner.class);

        System.out.println("with guice car can drive at speed = " + owner.getCar().drive());
    }
}
