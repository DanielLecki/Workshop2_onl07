package pl.coderslab.workshop2;

import pl.coderslab.workshop2.user.User;
import pl.coderslab.workshop2.user.UserDao;

public class Main {

    // liwia metody

    private static UserDao userDao = new UserDao();

    public static void main(String[] args) {
        // Creating
        User user = new User("login11", "haslo12332", "1asdas123@o2.pl");
        userDao.create(user);
        System.out.println(user);

        // reading one
        User userRead = userDao.read(1);
        System.out.println(userRead);

        // read all
        User[] allUsers = userDao.findAll();
        for(User u : allUsers) {
            System.out.println(u);
        }

        // remove
        userDao.delete(5);

        // update
        User userUpdate = userDao.read(4);
        userUpdate.setUsername("updatedUserName");
        userUpdate.setEmail("updateedEmail");
        userUpdate.setPassword("updatedPAssword");
        userDao.update(userUpdate);
    }

}
