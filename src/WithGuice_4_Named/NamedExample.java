package WithGuice_4_Named;

import com.google.inject.*;

import javax.inject.Named;

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

final class Honda implements Car {
    @Inject
    Honda() {};

    @Override
    public int drive() {
        return 40;
    }

    public static Honda create() {
        return new Honda();
    }
}

final class Owner {
    private Car car;

    @Inject
    Owner (@Named("CarHonda") Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }
}

final class CarModule extends AbstractModule {
    protected void configure() {}

    @Provides
    @Named("CarBMW")
    Car provideBMW() {
        return BMW.create();
    }

    @Provides
    @Named("CarHonda")
    // string not strong type checking
    Car provideHonda() {
        return Honda.create();
    }
}

public class NamedExample {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new CarModule());
        Owner owner = injector.getInstance(Owner.class);

        System.out.println("with guice car can drive at speed = " + owner.getCar().drive());
    }
}
