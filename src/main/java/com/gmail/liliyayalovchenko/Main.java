package com.gmail.liliyayalovchenko;

import com.gmail.liliyayalovchenko.controllers.*;
import com.gmail.liliyayalovchenko.domainModel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private EmployeeController employeeController;
    private IngredientController ingredientController;
    private DishController dishController;
    private OrderController orderController;
    private MenuController menuController;
    private WarehouseController warehouseController;
    private ReadyMealController readyMealController;
    private int orderNumber;
    private boolean stopApp;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml", "hibernate-context.xml");
        Main main = applicationContext.getBean(Main.class);
        main.start();
    }

    private void start() {

        orderNumber = 1002;
        startApplication();
        Scanner sc = new Scanner(System.in);
        String selection = null;
        stopApp = false;
        while (!"q".equals(selection) && !stopApp) {

            selection = sc.next();

            if (selection.equals("d")) {
                goToDishPage(sc, selection);

            } else if (selection.equals("e")) {
                goToEmployeePage(sc, selection);


            } else if (selection.equals("m")) {
                goToMenuPage(sc, selection);

            } else if (selection.equals("o")) {
                goToOrdersPage(sc, selection);

            } else if (selection.equals("rm")) {
                goToReadyMealPage(sc, selection);

            } else if (selection.equals("w")) {
                goToWarehousePage(sc, selection);

            } else if ("q".equals(selection)) {
                System.out.println("Good Bay!");
                stopApp = true;
                LOGGER.info("User left the application");
                break;
            } else {
                System.out.println("Wrong input!!! Try again");
            }
            System.out.println("Do you want continue? - enter 'y'");
            String next = sc.next();
            if (next.equals("y")) {
                selection = "continue";
                stopApp = false;
                startApplication();
            } else {
                stopApp = true;
                LOGGER.info("User left the application");
                break;
            }
        }

    }

    private void goToDishPage(Scanner sc, String selection) {
        System.out.println("Dish page. You have following options:");
        System.out.println("Add new dish - enter d01\nRemove dish - enter d02\n" +
                "Get dish by name - enter d03\nGet all dishes - enter d04");
        System.out.println("To exit database - enter q");
        System.out.println("To start menu - enter 'start'");

        while (!"q".equals(selection) && !stopApp) {
            selection = sc.next();
            if (selection.equals("d01")) {
                addNewDish(sc);

            } else if (selection.equals("d02")) {
                deleteDish(sc);

            } else if (selection.equals("d03")) {
                getDishByName(sc);

            } else if (selection.equals("d04")) {
                getAllDishes();

            } else if (selection.equals("start")) {
                break;

            } else if (selection.equals("q")){
                stopApp = true;
                break;
            } else {
                System.out.println("Wrong input! Try again!");
            }
            System.out.println("Dish page. You have following options:");
            System.out.println("Add new dish - enter d01\nRemove dish - enter d02\n" +
                    "Get dish by name - enter d03\nGet all dishes - enter d04");
            System.out.println("To exit database - enter q");
            System.out.println("To start menu - enter 'start'");
        }

    }
    /**
     * Starts private methods for dish page **/
    private void getAllDishes() {
        try {
            dishController.printAllDishes();
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot get all dishes " + ex);
            System.out.println("Cannot get all dishes ");
        }
    }

    private void getDishByName(Scanner sc) {
        showAllDishNames();
        System.out.println("Enter dish name");
        String name = sc.next();
        Dish dish;
        try{
            dish = dishController.getDishByName(name);
            System.out.println(dish);
        } catch (RuntimeException e) {
            LOGGER.error("Cant get dish by this name");
            System.out.println("dish with such name does not exist");
        }
    }

    private void deleteDish(Scanner sc) {
        showAllDishNames();
        System.out.println("Enter dish name to delete");
        String name = sc.next();
        Dish dish;
        try{
            dish = dishController.getDishByName(name);
            dishController.removeDish(dish);
        } catch (RuntimeException e) {
            LOGGER.error("Cannot get dish by this name.");
            System.out.println("dish with such name does not exist");
        }
    }

    private void addNewDish(Scanner sc) {
        Dish dish = new Dish();
        System.out.println("Enter dish name");
        String name = sc.next();
        dish.setName(name);
        int number = 1;
        for (DishCategory dc : DishCategory.values()) {
            System.out.println(dc + " enter 0" + number++);
        }
        System.out.println("Choose and enter category");
        String category = sc.next();
        DishCategory dishCategory;
        if (category.equals("01")) {
            dishCategory = DishCategory.MAIN_COURSES;
        } else if (category.equals("02")) {
            dishCategory = DishCategory.SALADS;
        } else if (category.equals("03")) {
            dishCategory = DishCategory.SIDE;
        } else if (category.equals("04")) {
            dishCategory = DishCategory.DRINKS;
        } else if (category.equals("05")) {
            dishCategory = DishCategory.WINE;
        } else if (category.equals("06")) {
            dishCategory = DishCategory.SAUCES;
        } else {
            dishCategory = DishCategory.MAIN_COURSES;
            LOGGER.error("Wrong input! " + category +"\nDefault dish category is MAIN_COURSES");
            System.out.println("Wrong input. Default dish category is MAIN_COURSES.");
        }
        dish.setDishCategory(dishCategory);
        try {
            System.out.println("Enter price");
            dish.setPrice(sc.nextDouble());
            System.out.println("Enter weight");
            dish.setWeight(sc.nextInt());
        } catch (InputMismatchException ex) {
            LOGGER.error("Error wile parsing " + ex);
            System.out.println("Wrong input");
        }
        System.out.println("Enter ingredients. Then enter - f");
        List<Ingredient> ingredientList = new ArrayList<>();
        try {
            while (true) {
                String ingre = sc.next();
                if (ingre.equals("f")) break;
                Ingredient ingredientByName = ingredientController.getIngredientByName(ingre);
                ingredientList.add(ingredientByName);
            }
            dish.setIngredients(ingredientList);
            dishController.createDish(dish);
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot add dish " + ex);
        }
    }

    private void showAllDishNames() {
        for (Dish dish : dishController.getAllDishes()) {
            System.out.println(dish.getName());
        }

    }
    /**
     * Stop private methods for dish page**/

    private void goToEmployeePage(Scanner sc, String selection) {
        System.out.println("Employee page. You have following options:");
        System.out.println("Add new employee - enter e01\nRemove employee - enter e02\n" +
                "Get all employees - enter e03\nFind employee by name - enter e04");
        System.out.println("To exit database - enter q");
        System.out.println("To start menu - enter 'start'");

        while (!"q".equals(selection)) {
            selection = sc.next();
            if (selection.equals("e01")) {
                addNewEmployee(sc);

            } else if (selection.equals("e02")) {
                removeEmployee(sc);

            } else if (selection.equals("e03")) {
                getAllEmployees();

            } else if (selection.equals("e04")) {
                findEmployeeByName(sc);

            } else if (selection.equals("start")) {
                break;

            } else if (selection.equals("q")){
                stopApp = true;
                break;
            } else {
                System.out.println("Wrong input!Try again!");
            }
            System.out.println("Employee page. You have following options:");
            System.out.println("Add new employee - enter e01\nRemove employee - enter e02\n" +
                    "Get all employees - enter e03\nFind employee by name - enter e04");
            System.out.println("To exit database - enter q");
            System.out.println("To start menu - enter 'start'");
        }
    }

    /**
     * Start private methods for employee page**/
    private void findEmployeeByName(Scanner sc) {
        showAllEmplNames();
        System.out.println("Enter second name of employee");
        String secondName = sc.next();
        System.out.println("Enter first name of employee");
        String firstName = sc.next();

        Employee employee;
        try {
            employee = employeeController.getEmployeeByName(firstName, secondName);
            System.out.println(employee);
        } catch (RuntimeException ex) {
            LOGGER.error("Exception " + ex);
            System.out.println("Can't find employee by this name");
        }
    }

    private void showAllEmplNames() {
        for (Employee employee : employeeController.getAllEmployees()) {
            System.out.println(employee.getSecondName() + " " + employee.getFirstName());
        }
    }

    private void getAllEmployees() {
        try {
            List<Employee> employeeList = employeeController.getAllEmployees();
            employeeList.forEach(System.out::println);
        } catch (RuntimeException e) {
            LOGGER.error("Cannot get all employee " +e);
        }
    }

    private void removeEmployee(Scanner sc) {
        showAllEmplNames();
        System.out.println("Enter second name of employee to delete");
        String secondName = sc.next();
        System.out.println("Enter first name of employee");
        String firstName = sc.next();
        try {
            employeeController.deleteEmployee(firstName, secondName);
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot remove employee by this name. Cannot find employee with such name");
            System.out.println("Wrong input, cannot delete employee by this name");
        }
    }

    private void addNewEmployee(Scanner sc) {
        Employee employee = new Employee();
        System.out.println("Enter Second name of new employee");
        employee.setSecondName(sc.next());
        System.out.println("Enter First name of new employee");
        employee.setFirstName(sc.next());
        System.out.println("Enter date of employment in format yyyy-mm-dd. For example, 2008-10-3");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date date;
        try {
            date = format.parse(sc.next());
            employee.setEmplDate(date);
            System.out.println("Enter phone");
            employee.setPhone(sc.next());
            int i = 1;
            for (Position p : Position.values()) {
                System.out.println(p + " enter 0" + i++);
            }
            System.out.println("Enter position");
            String position = sc.next();
            Position positionOfEmployee = null;
            if (position.equals("01"))  {
                positionOfEmployee = Position.ADMINISTRATOR;
            } else if (position.equals("02")) {
                positionOfEmployee = Position.SENIOUR_MANAGER;
            } else if (position.equals("03")) {
                positionOfEmployee = Position.MANAGER;
            } else if (position.equals("04")) {
                positionOfEmployee = Position.COOK;
            } else if (position.equals("05")) {
                positionOfEmployee = Position.WAITRESS;
            } else if (position.equals("06")) {
                positionOfEmployee = Position.WAITER;
            } else {
                positionOfEmployee = Position.NO_POSITION;
                LOGGER.error("Wrong input! " + position +"\nDefault position is NO_POSITION");
                System.out.println("Wrong input. Default position is NO_POSITION.");
            }
            employee.setPosition(positionOfEmployee);
            System.out.println("Enter salary");
            employee.setSalary(sc.nextInt());
            employeeController.createEmployee(employee);
        } catch (ParseException e) {
            LOGGER.error("Exception while parsing date" + e);
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot add employee " + ex);
        }
    }
    /**
     * Stop private methods for employee page**/

    private void goToMenuPage(Scanner sc, String selection) {
        System.out.println("Menu page. You have following options:");
        System.out.println("Add new menu - enter m01\nRemove menu - enter m02\n" +
                "Get menu by name - enter m03\nTo see all menus - enter m04\n" +
                "To remove dish from menu - enter m05\nTo add dish to menu - enter m06");
        System.out.println("To exit database - enter q");
        System.out.println("To start menu - enter 'start'");

        while (!"q".equals(selection)) {
            selection = sc.next();
            if ("m01".equals(selection)) {
                addNewMenue(sc);

            } else if ("m02".equals(selection)) {
                removeMenu(sc);

            } else if ("m03".equals(selection)) {
                getMenuByName(sc);

            } else if ("m04".equals(selection)) {
                showAllMenues();

            } else if ("m05".equals(selection)) {
                removeDishFromMenu(sc);

            } else if ("m06".equals(selection)) {
                addDishToMenu(sc);

            } else if ("start".equals(selection)) {
                break;
            } else if(selection.equals("q"))  {
                stopApp = true;
                break;
            } else {
                System.out.println("Wrong input!Try again!");
            }
            System.out.println("Menu page. You have following options:");
            System.out.println("Add new menu - enter m01\nRemove menu - enter m02\n" +
                    "Get menu by name - enter m03\nTo see all menus - enter m04\n" +
                    "To remove dish from menu - enter m05\nTo add dish to menu - enter m06");
            System.out.println("To exit database - enter q");
            System.out.println("To start menu - enter 'start'");
        }

    }

    /**
     * Start private methods for menu page**/
    private void addNewMenue(Scanner sc) {
        System.out.println("Enter name of new Menu");
        String nameMenu = sc.next();
        //showAllDishNames();
        System.out.println("Enter dish name to add to menu. To finish enter - 'f'");
        List<Dish> dishList = new ArrayList<>();
        String dishName;
        try {
            while (true) {
                dishName = sc.next();
                if (dishName.equals("f")) break;
                Dish dish = dishController.getDishByName(dishName);
                dishList.add(dish);

            }
            menuController.addNewMenu(nameMenu, dishList);
        } catch (RuntimeException e) {
            LOGGER.error("Exception " + e);
        }
    }

    private void removeMenu(Scanner sc) {
        menuController.showAllMenus();
        System.out.println("Enter name of menu to delete");
        try {
            Menu menu = menuController.getMenuByName(sc.next());
            menuController.removeMenu(menu);
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot get menu by this name " +ex);
        }
    }

    private void getMenuByName(Scanner sc) {
        System.out.println("Enter name of menu to see");
        try {
            Menu menu = menuController.getMenuByName(sc.next());
            System.out.println(menu);
        } catch (RuntimeException ex) {
            LOGGER.error("cannot get menu by this name " + ex);
        }
    }

    private void showAllMenues() {
        try {
            menuController.showAllMenus();
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot show list of menus " + ex);
        }
    }

    private void removeDishFromMenu(Scanner sc) {
        menuController.showAllMenus();
        System.out.println("Enter menu name you want to remove dish");
        try {
            Menu menu = menuController.getMenuByName(sc.next());
            menu.getDishList().forEach(System.out::println);
            System.out.println("Enter dish name to remove");
            Dish dish = dishController.getDishByName(sc.next());
            menuController.removeDishFromMenu(menu.getId(), dish);
        } catch (RuntimeException ex) {
            LOGGER.error("Error while removing dish from menu Main() method " +ex);
        }
    }

    private void addDishToMenu(Scanner sc) {
        menuController.showAllMenus();
        System.out.println("Enter menu name you want to add dish");
        try {
            Menu menu = menuController.getMenuByName(sc.next());
            showAllDishNames();
            Dish dish;

            dish = dishController.getDishByName(sc.next());
            menuController.addDishToMenu(menu.getId(), dish);
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot gat dish or menu " + ex);
        }
    }
    /**
     * Stop private methods for menu page**/

    private void goToOrdersPage(Scanner sc, String selection) {
        System.out.println("Orders page. You have following options:");
        System.out.println("Create order - enter o01\nAdd dish to open order - enter o02\n" +
                "Delete order - enter o03\nTo change order status - enter o04\n" +
                "To get all open or closed orders - enter o05");
        System.out.println("To exit database - enter q");
        System.out.println("To start menu - enter 'start'");
        while (!"q".equals(selection)) {
            selection = sc.next();
            if ("o01".equals(selection)) {
                createOrder(sc);

            } else if ("o02".equals(selection)) {
                addDishToOpenOrder(sc);

            } else if ("o03".equals(selection)) {
                deleteOrder(sc);

            } else if ("o04".equals(selection)) {
                changeOrderStatus(sc);

            } else if ("o05".equals(selection)) {
                getAllOpenOrClosedOrders(sc);

            } else if ("start".equals(selection)) {
                break;

            } else if ("q".equals(selection)) {
                stopApp = true;
                break;

            } else {
                System.out.println("Wrong input!Try again!");
            }
            System.out.println("Orders page. You have following options:");
            System.out.println("Create order - enter o01\nAdd dish to open order - enter o02\n" +
                    "Delete order - enter o03\nTo change order status - enter o04\n" +
                    "To get all open or closed orders - enter o05");
            System.out.println("To exit database - enter q");
            System.out.println("To start menu - enter 'start'");
        }
    }
    /**
     * Start private methods for Order page**/
    private void addDishToOpenOrder(Scanner sc) {
        System.out.println("Enter dish name");
        showAllDishNames();
        Dish dish;
        try {
            dish = dishController.getDishByName(sc.next());

            for (Order order : orderController.getOpenOrClosedOrder(OrderStatus.opened)) {
                System.out.println("Order number " + order.getOrderNumber() + " ");
            }

            System.out.println("Select number of order");
            orderController.addDishToOpenOrder(dish, sc.nextInt());
        } catch (RuntimeException ex) {
            LOGGER.error("Error wile add dish to open order." + ex);
        }
    }

    private void createOrder(Scanner sc) {
        Order order = new Order();
        order.setOrderNumber(orderNumber++);
        showAllEmplNames();
        System.out.println("Enter employee second name");
        String secondName = sc.next();
        System.out.println("Enter employee firstName");
        String firstName = sc.next();
        Employee employee;
        try {
            employee = employeeController.getEmployeeByName(firstName, secondName);
            order.setEmployeeId(employee);
            System.out.println("Enter table number");
            order.setTableNumber(sc.nextInt());
            order.setOrderDate(new java.sql.Date(new Date(System.currentTimeMillis()).getTime()));
            order.setStatus(OrderStatus.opened);
            showAllDishNames();
            System.out.println("Enter name of dish to add to order, to stop - enter twice 'f'");
            List<Dish> dishForOrder = new ArrayList<>();
            String name;

            while (true) {
                name = sc.next();

                if ("f".equals(name)) break;
                System.out.println("You entered " + name);
                dishForOrder.add(dishController.getDishByName(name));
            }
            order.setDishList(dishForOrder);
            orderController.saveOrder(order);
        } catch (RuntimeException ex) {
            LOGGER.error("Exception " + ex);
            System.out.println("Cannot find employee or dish with such name");
        }
    }

    private void getAllOpenOrClosedOrders(Scanner sc) {
        System.out.println("Select order status to find, print: 'closed' or 'opened'");
        List<Order> orders;
        OrderStatus orderStatus;

        try {
            String status = sc.next();
            if (status.equals("opened")) {
                orderStatus = OrderStatus.opened;
            } else {
                orderStatus = OrderStatus.closed;
            }
            orderController.printOpenOrClosedOrders(orderStatus);

        } catch (RuntimeException e) {
            LOGGER.error("No orders with such status! " + e);
            System.out.println("can not add this dish to order");
        }
    }

    private void changeOrderStatus(Scanner sc) {
        System.out.println("Enter number of order to change status to 'closed'");
        showOpenedOrdersNumb();
        try {
            orderController.changeOrderStatus(sc.nextInt());
        } catch (RuntimeException e) {
            LOGGER.error("Cannot find order by this order number");
        }
    }

    private void showOpenedOrdersNumb() {
        for (Order order : orderController.getOpenOrClosedOrder(OrderStatus.opened)) {
            System.out.println("Order #" + order.getOrderNumber());
        }
    }

    private void deleteOrder(Scanner sc) {
        System.out.println("Enter number of order to delete");
        showOpenedOrdersNumb();
        try {

            int orderNumber1 = sc.nextInt();
            readyMealController.removeReadyMeal(orderNumber1);
            orderController.deleteOrder(orderNumber1);
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot find order by this order number " + ex);
        }
    }
    /**
     * End of private methods for Order page**/

    private void goToReadyMealPage(Scanner sc, String selection) {
        System.out.println("Ready meals page. You have following options:");
        System.out.println("Get all ready meals - enter rm01\nAdd new ready meal - enter rm02");
        System.out.println("To exit database - enter q");
        System.out.println("To start menu - enter 'start'");
        while (!"q".equals(selection)) {
            selection = sc.next();
            if ("rm01".equals(selection)) {
                getAllreadyMeals();

            } else if ("rm02".equals(selection)) {
                addNewReadyMeal(sc);

            } else if ("start".equals(selection)) {
                start();
            } else if ("q".equals(selection)) {
                stopApp = true;
                break;
            } else {
                System.out.println("Wrong input! try again!");
            }
            System.out.println("Ready meals page. You have following options:");
            System.out.println("Get all ready meals - enter rm01\nAdd new ready meal - enter rm02");
            System.out.println("To exit database - enter q");
            System.out.println("To start menu - enter 'start'");
        }
    }
    /**
     * Start private methods for ready meal page**/
    private void addNewReadyMeal(Scanner sc) {
        ReadyMeal readyMeal = new ReadyMeal();
        showAllDishNames();
        System.out.println("Enter dish name");
        Dish dish;
        Employee employee;
        try {
            dish = dishController.getDishByName(sc.next());
            readyMeal.setDishId(dish);
            readyMeal.setDishNumber(dish.getId());
            showAllEmplNames();
            System.out.println("Enter employee second name");
            String secondName = sc.next();
            System.out.println("Enter employee first name");
            String firstName = sc.next();
            employee = employeeController.getEmployeeByName(firstName, secondName);
            readyMeal.setEmployeeId(employee);
            List<Order> openOrders = orderController.getOpenOrClosedOrder(OrderStatus.opened);
            if (openOrders.size() > 0) {

                for (Order openOrder : openOrders) {
                    System.out.println("order_id " + openOrder.getId() + " ");
                }
                System.out.println("Select order id");
                readyMeal.setOrderId(orderController.getOrderById(sc.nextInt()));
                readyMeal.setMealDate(new java.sql.Date(new Date(System.currentTimeMillis()).getTime()));
                readyMealController.addReadyMeal(readyMeal);
            } else {
                LOGGER.info("No open orders. Cant save ready meal");
            }
        } catch (RuntimeException ex) {
            LOGGER.error("Wrong input!!!Try again!");
        }
    }

    private void getAllreadyMeals() {
        try {
            List<ReadyMeal> readyMeals = readyMealController.getAllReadyMeals();
            readyMeals.forEach(System.out::println);
        } catch (RuntimeException e) {
            LOGGER.error("Cannot get all ready meals " + e);
        }
    }
    /**Stop private methods for ready meal page**/

    private void goToWarehousePage(Scanner sc, String selection) {
        System.out.println("Warehouse page. You have following options:");
        System.out.println("Add ingredient to warehouse - enter w01\nRemove ingredient - enter w02\n" +
                "Change amount of ingredient - enter w03\nFind ingredient by name - enter w04\n " +
                "To see all ingredients - enter w05");
        System.out.println("To exit database - enter q");
        System.out.println("To start menu - enter 'start'");
        while (!"q".equals(selection)) {
            selection = sc.next();
            if (selection.equals("w01")) {
                addNewIngredient(sc);

            } else if (selection.equals("w02")) {
                removeIngredientByName(sc);

            } else if (selection.equals("w03")) {
                changeAmountOfIngredient(sc);

            } else if (selection.equals("w04")) {
                findIngredientByName(sc);

            } else if (selection.equals("w05")) {
                getAllIngredients();

            } else if (selection.equals("start")) {
                break;

            } else if ("q".equals(selection)) {
                stopApp = true;
                break;
            }
            System.out.println("Warehouse page. You have following options:");
            System.out.println("Add ingredient to warehouse - enter w01\nRemove ingredient - enter w02\n" +
                    "Change amount of ingredient - enter w03\nFind ingredient by name - enter w04\n " +
                    "To see all ingredients - enter w05");
            System.out.println("To exit database - enter q");
            System.out.println("To start menu - enter 'start'");

        }
    }

    /**
     * Start private methods for warehouse page**/
    private void getAllIngredients() {
        try {
            List<Warehouse> warehouseList = warehouseController.getAllIngredients();
            warehouseList.forEach(System.out::println);
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot get all ingredients " + ex);
        }
    }

    private void findIngredientByName(Scanner sc) {
        for (Warehouse ingredient : warehouseController.getAllIngredients()) {
            System.out.println(ingredient.getIngredId().getName());
        }

        System.out.println("Enter name of ingredient");
        try {
            Warehouse warehouse = warehouseController.findByName(sc.next());
            System.out.println(warehouse);
        } catch (RuntimeException e) {
            System.out.println("No Ingredient with such name on warehouse");
        }
    }

    private void showAllIngredNames() {
       warehouseController.getAllIngredients().forEach(System.out::println);
    }

    private void changeAmountOfIngredient(Scanner sc) {
        showAllIngredNames();
        System.out.println("Enter name of ingredient to change it amount");
        Ingredient ingredient = null;
        try {
            ingredient = ingredientController.getIngredientByName(sc.next());
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot get ingredient by name");
        }
        System.out.println("Enter amount");
        int amount = sc.nextInt();
        System.out.println("If you want to increase amount enter y. If to decrease - n");
        boolean increase = sc.next().equals("y");
        if (ingredient !=null ) {
            warehouseController.changeAmount(ingredient, amount, increase);
        } else {
            LOGGER.error("Ingredient was not selected in right way");
        }
    }

    private void removeIngredientByName(Scanner sc) {
        showAllIngredNames();
        System.out.println("Enter name of ingredient to remove");
        try {
            warehouseController.removeIngredient(sc.next());
        } catch (RuntimeException ex) {
            LOGGER.error("cannot remove ingredient by this name!");
        }
    }

    private void addNewIngredient(Scanner sc) {
        System.out.println("Enter name of ingredient");
        ingredientController.getAllIngredients().forEach(System.out::println);
        String ingredientName = sc.next();
        System.out.println("This ingredient is new in Ingredient department: 'y'/'n'");
        boolean newIngred = sc.next().equals("y");
        Ingredient ingredient = null;
        if (!newIngred) {
            try {
                ingredient = ingredientController.getIngredientByName(ingredientName);
            } catch (RuntimeException ex) {
                LOGGER.error("Cannot get ingredient by this name");
            }
        } else {
            ingredient = new Ingredient();
            ingredient.setName(ingredientName);
            ingredientController.createIngredient(ingredient);
        }
        System.out.println("Enter amount of ingredient");
        int amount = sc.nextInt();
        if (ingredient != null) {
            warehouseController.addIngredient(ingredient, amount);
        }
    }
    /**
     * Stop private methods for warehouse page**/


    private void startApplication() {
        System.out.println("Hi! You entered to restaurant database");
        System.out.println("Select you next step");
        System.out.println("d - work with dish information\ne - work with info about employee");
        System.out.println("m - work with menu information\no - work with info about orders");
        System.out.println("rm - work with ready meals information\nw - work with info about ingredients in warehouse");
        System.out.println("q - to leave application");
    }

    public void setEmployeeController(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }

    public void setIngredientController(IngredientController ingredientController) {
        this.ingredientController = ingredientController;
    }

    public void setDishController(DishController dishController) {
        this.dishController = dishController;
    }

    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setWarehouseController(WarehouseController warehouseController) {
        this.warehouseController = warehouseController;
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public void setReadyMealController(ReadyMealController readyMealController) {
        this.readyMealController = readyMealController;
    }
}
