package resource;

import bean.ApiResponse;
import bean.UserBean;
import db.DataBase;

public class UserResource implements BaseCRUDResource<UserBean> {
    @Override
    public ApiResponse add(UserBean bean) {
        UserBean user = DataBase.addUser(bean);
        return user == null ? new ApiResponse(400, "User exists!", null) :
                new ApiResponse(200, "Successfully created!", bean);
    }

    @Override
    public ApiResponse get(Integer id) {
        return null;
    }

    @Override
    public ApiResponse edit(UserBean bean) {
        return null;
    }

    @Override
    public ApiResponse delete(Integer id) {
        return null;
    }

    public ApiResponse login(UserBean bean) {
        UserBean user = DataBase.getUser(bean.getUsername(), bean.getPassword());

        return user == null ? new ApiResponse(400, "Error", null) :
                new ApiResponse(200, "Successfully logged in", user);
    }
}
