package resource;

import bean.ApiResponse;
import bean.CarBean;
import bean.UserBean;
import db.DataBase;

import java.util.List;

public class CarResource implements BaseCRUDResource<CarBean> {
    @Override
    public ApiResponse add(CarBean bean) {
        Integer id = DataBase.addCar(bean);

        return id == null ? new ApiResponse(400, "Error occurred", null) :
                new ApiResponse(200, "Successfully added!", id);
    }

    @Override
    public ApiResponse get(Integer id) {
        return null;
    }

    @Override
    public ApiResponse edit(CarBean bean) {
        return null;
    }

    @Override
    public ApiResponse delete(Integer id) {
        return null;
    }

    public ApiResponse getCars(Integer id) {
        List<CarBean> cars = DataBase.getUserCars(id);
        return cars.isEmpty() ? new ApiResponse(400, "Empty List!", null) :
                new ApiResponse(200, "Car List", cars);
    }


    public ApiResponse sellCar(int id, int price, Integer sessionId) {
        Boolean onSale = DataBase.carSell(id, price, sessionId);
        return onSale ? new ApiResponse(200, "Success", true) :
                new ApiResponse(400, "Error occurred", false);
    }

    public ApiResponse buyCar(Integer carId, UserBean userId) {
        Boolean isBuy = DataBase.buyCarUser(carId, userId);
        return isBuy ? new ApiResponse(200, "Success", true) :
                new ApiResponse(400, "Error occurred", false);
    }
}