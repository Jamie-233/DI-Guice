package WithGuice_3_Qualifier;

import com.google.inject.*;
import javax.inject.Qualifier;

import javax.inject.Inject;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

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
    Honda() {}

    @Override
    public int drive() {
        return 40;
    }

    public static Honda create() {
        return new Honda();
    }
}

//@Singleton
final class Owner {
    private final Car car;

    @Inject
    Owner(@CarHonda Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }
}

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD, PARAMETER, METHOD})
@interface CarBMW {}

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD, PARAMETER, METHOD})
@interface CarHonda {}

final class CarModule extends AbstractModule {
    @Override
    protected void configure() {
        // bind 时 声明 annotation
        // bind(Car.class).annotatedWith(CarBMW.class).to(BMW.class);
        // bind(Car.class).annotatedWith(CarHonda.class).to(Honda.class);
    }

    @Provides
    @CarBMW
    Car provideBMWCar() {
        return BMW.create();
    }

    @Provides
    @CarHonda
    Car provideHondaCar() {
        return Honda.create();
    }
}

public class QualifierExample {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new CarModule());
        Owner owner = injector.getInstance(Owner.class);

        System.out.println("with guice car can drive at speed = " + owner.getCar().drive());
    }
}
