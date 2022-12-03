package WithGuice_2_Provides;

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

//@Singleton
final class Owner {
    private final Car car;

    @Inject
    Owner(Provider<Car> carProvider) {
        this.car = carProvider.get(); // lazy loading when get called
    }

    public Car getCar() {
        return car;
    }
}

final class CarModule extends AbstractModule {
    @Override
    protected void configure() {}

    @Provides
    @Singleton
    Car provideCar() {
        return BMW.create();
    }
}

public class ProvidesExample {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new CarModule());

        Owner owner = injector.getInstance(Owner.class);

        System.out.println("with guice car can drive at speed = " + owner.getCar().drive());
    }
}
