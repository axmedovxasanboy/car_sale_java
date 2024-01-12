import bean.ApiResponse;
import bean.CarBean;
import bean.UserBean;
import db.DataBase;
import resource.CarResource;
import resource.UserResource;

import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scannerNum = new Scanner(System.in);
    static Scanner scannerStr = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the online market.");
        while (true) {
            if (DataBase.session == null) {
                System.out.print("""                        
                        0. Exit
                        1. Register
                        2. LogIn
                        >>\s""");
            } else {
                System.out.print("""
                                                
                        0. Exit
                        1. Add car
                        2. See my cars
                        3. Sell car
                        4. Purchase a new car
                        5. My Information
                        9. LogOut
                        >>\s""");
            }
            String choice = scannerStr.nextLine();
            if (DataBase.session == null) {
                switch (choice) {
                    case "0" -> {
                        return;
                    }
                    case "1" -> register();
                    case "2" -> login();

                }
            } else {
                switch (choice) {
                    case "0" -> {
                        return;
                    }
                    case "1" -> addCar();
                    case "2" -> myCarList();
                    case "3" -> sellCar();
                    case "4" -> buyCar();
                    case "5" -> myInfo();
                    case "9" -> logOut();
                }
            }
        }

    }

    private static void myInfo() {
        System.out.println(DataBase.session);
    }

    private static void logOut() {
        System.out.println("Success");
        DataBase.session = null;
    }

    private static void buyCar() {
        boolean isMarketEmpty = DataBase.marketCars(DataBase.session.getId());

        if (isMarketEmpty) {
            System.out.print("Insert car id: ");
            int id = scannerNum.nextInt();
            CarResource resource = new CarResource();
            ApiResponse response = resource.buyCar(id, DataBase.session);
            System.out.println(response.getMessage());
        } else {
            System.out.println("Car market is empty\n");
        }

    }

    private static void sellCar() {
        boolean carListEmpty = myCarList();
        if (!carListEmpty) {
            System.out.print("Insert car ID: ");
            int id = scannerNum.nextInt();
            System.out.print("Insert new price: ");
            int newPrice = scannerNum.nextInt();

            CarResource resource = new CarResource();
            ApiResponse response = resource.sellCar(id, newPrice, DataBase.session.getId());

            System.out.println(response.getMessage());
        }
    }

    private static boolean myCarList() {
        CarResource resource = new CarResource();
        ApiResponse cars = resource.getCars(DataBase.session.getId());
        System.out.println("*************");
        System.out.println(cars.getMessage());
        if (cars.getCode().equals(400)) {
            System.out.println("*************");
            return true;
        } else if (cars.getCode().equals(200)) {
            if (cars.getData() != null) {
                List<CarBean> list = (List<CarBean>) cars.getData();
                list.forEach(System.out::println);
            }
            System.out.println("*************");
        }
        return false;

    }

    private static void addCar() {
        System.out.print("Enter car name: ");
        String carName = scannerStr.nextLine();
        System.out.print("Enter car color: ");
        String carColor = scannerStr.nextLine();
        System.out.print("Enter car price: ");
        int carPrice = scannerNum.nextInt();

        if (carPrice <= DataBase.session.getBalance() && carPrice > 0) {
            CarBean car = new CarBean(carName, carColor, DataBase.session.getId(), carPrice);
            CarResource resource = new CarResource();
            ApiResponse response = resource.add(car);
            car.setInStore(false);
            System.out.println(response.getMessage());
            if (response.getCode().equals(400)) {
                addCar();
            }
            DataBase.session.setBalance(DataBase.session.getBalance() - carPrice);
        } else {
            System.out.println("Car price is invalid!");
        }
    }

    private static void login() {
        System.out.print("Enter your username: ");
        String username = scannerStr.nextLine();
        System.out.print("Enter your password: ");
        String password = scannerStr.nextLine();

        UserResource resource = new UserResource();
        UserBean bean = new UserBean(username, password);
        ApiResponse login = resource.login(bean);
        System.out.println(login.getMessage());

        if (login.getCode().equals(200)) DataBase.session = (UserBean) login.getData();

    }

    private static void register() {
        UserResource resource = new UserResource();

        System.out.print("Enter your username: ");
        String username = scannerStr.nextLine();
        System.out.print("Enter your password: ");
        String password = scannerStr.nextLine();

        UserBean user = new UserBean(username, password);

        ApiResponse response = resource.add(user);

        System.out.println(response.getMessage());
        if (response.getCode().equals(400)) register();

        else if (response.getCode().equals(200)) DataBase.session = (UserBean) response.getData();


    }

//    TODO: Edit user profile
//    TODO: Edit user car
//    TODO: Removing user car from market price of the car must be previous

}