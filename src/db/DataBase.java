package db;

import bean.ApiResponse;
import bean.CarBean;
import bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    protected static List<UserBean> users = new ArrayList<UserBean>();
    protected static List<CarBean> cars = new ArrayList<CarBean>();
    public static UserBean session = null;

    public static UserBean addUser(UserBean user) {
        for (UserBean userBean : users) {
            if (userBean.getUsername().equals(user.getUsername())) return null;
        }
        user.setId(users.size());
        users.add(user);
        return user;
    }

    public static UserBean getUser(String username, String password) {
        for (UserBean user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return user;
        }
        return null;
    }

    public static Integer addCar(CarBean bean) {
        bean.setId(cars.size());
        cars.add(bean);
        return bean.getId();
    }

    public static List<CarBean> getUserCars(Integer id) {
        List<CarBean> carList = new ArrayList<>();
        for (CarBean car : cars) {
            if (car.getUserId().equals(id)) {
                carList.add(car);
            }
        }
        return carList;
    }

    public static ApiResponse carOnMarket(int id) {
        return null;
    }


    public static Boolean carSell(int id, int newPrice, Integer sessionId) {
        for (CarBean car : cars) {
            if (car.getId().equals(id) && car.getUserId().equals(sessionId)) {
                car.setInStore(true);
                car.setPrice(newPrice);
                return true;
            }
        }
        return false;
    }

    public static Boolean buyCarUser(Integer carId, UserBean userId) {
        for (CarBean car : cars) {
            if (car.getId().equals(carId) && userId.getBalance() >= car.getPrice() && car.getInStore()) {
                car.setInStore(false);
                int ownerId = car.getUserId();
                for (UserBean user : users) {
                    if (user.getId().equals(ownerId))
                        user.setBalance(user.getBalance() + car.getPrice());
                }
                car.setUserId(userId.getId());
                userId.setBalance(userId.getBalance() - car.getPrice());
                return true;
            }
        }
        return false;
    }

    public static boolean marketCars(Integer id) {
        for (CarBean car : cars) {
            if (car.getInStore() && !car.getUserId().equals(id)) {
                System.out.println(car);
                return true;
            }
        }
        return false;
    }
}
