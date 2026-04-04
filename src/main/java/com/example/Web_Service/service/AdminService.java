package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.adminDto.dish.request.DishCreateDto;
import com.example.Web_Service.model.dto.adminDto.dish.response.DishDto;
import com.example.Web_Service.model.dto.adminDto.employee.EmployeeDto;
import com.example.Web_Service.model.dto.adminDto.employee.request.EmployeeUpdateDto;
import com.example.Web_Service.model.dto.adminDto.product.ProductDto;
import com.example.Web_Service.model.dto.adminDto.support.response.SupportMessageDto;
import com.example.Web_Service.model.dto.adminDto.user.request.UserUpdateDto;
import com.example.Web_Service.model.dto.adminDto.user.response.UserUpdatePasswordDto;
import com.example.Web_Service.model.dto.user.UserDto;
import com.example.Web_Service.model.dto.user.UserProgressDto;
import com.example.Web_Service.model.entity.*;
import com.example.Web_Service.model.enums.Role;
import com.example.Web_Service.model.enums.Status;
import com.example.Web_Service.users.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final UserProgressService userProgressService;
    private final DishService dishService;
    private final EmployeeService employeeService;
    private final ProductService productService;
    private final SupportService supportService;
    private final UserService userService;

    public AdminService(UserProgressService userProgressService, DishService dishService, EmployeeService employeeService, ProductService productService, SupportService supportService, UserService userService) {
        this.userProgressService = userProgressService;
        this.dishService = dishService;
        this.employeeService = employeeService;
        this.productService = productService;
        this.supportService = supportService;
        this.userService = userService;
    }

    public List<UserProgressDto> getListUserProgressDto() {
        List<UserProgressDto> list = userProgressService.getListUserProgressDto();

        if (list.isEmpty()) {
            return List.of();
        }

        return list;
    }

    public List<EmployeeDto> getListEmployeeDto() {
        List<EmployeeDto> list = employeeService.getListEmployeeDto();

        if (list.isEmpty()) {
            return List.of();
        }

        return list;
    }

    public List<DishDto> getListDishDto() {
        List<DishDto> list = dishService.getListDishDto();

        if (list.isEmpty()) {
            return List.of();
        }

        return list;
    }

    public List<ProductDto> getListProductDto() {
        List<ProductDto> list = productService.getListProductDto();

        if (list.isEmpty()) {
            return List.of();
        }

        return list;
    }

    public List<SupportMessageDto> getListSupportMessageDto() {
        List<SupportMessageDto> list = supportService.getListMessageSupportDto();

        if (list.isEmpty()) {
            return List.of();
        }

        return list;
    }

    public UserUpdatePasswordDto getUserUpdatePasswordDto(String username) {
        User user = userService.getUserUsername(username);

        if (user == null) {
            return null;
        }

        return new UserUpdatePasswordDto(
                user.getUsername(),
                user.getPassword()
        );
    }

    public String updatePasswordUserValidator(UserUpdatePasswordDto userUpdatePasswordDto) {
        User user = userService.getUserUsername(userUpdatePasswordDto.getUsername());

        if (user == null) {
            return "Пользователь " + userUpdatePasswordDto.getUsername() + " не найден!";
        }

        String password = userUpdatePasswordDto.getPassword().trim();

        if (password.isBlank()) {
            return "Нельзя изменить пароль на пустой или состоящий из пробелов!";
        }

        if (password.length() < 12) {
            return "Запрещено ставить пароли длина которых меньше 12 символов!";
        }

        if (!userService.updateUserPassword(userUpdatePasswordDto.getUsername(), userUpdatePasswordDto.getPassword())) {
            return "Не удалось обновить пароль пользователю: " + userUpdatePasswordDto.getUsername() + "!";
        }

        return null;
    }

    public UserDto getUserData(String username) {
        User user = userService.getUserUsername(username);

        if (user == null) {
            return null;
        }

        return new UserDto(
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getBirthdate(),
                user.getRole(),
                user.getBalance()
        );
    }

    public String updateDataUserValidator(UserUpdateDto userUpdateDto, String oldUsername) {
        String message = userService.userDataValidator(userUpdateDto);

        if (message != null) {
            return message;
        }

        if (userUpdateDto.getBalance() < 0) {
            return "Баланс не может быть отрицательным!";
        }

        String name = userUpdateDto.getName().trim();
        String username = userUpdateDto.getUsername().trim();
        String email = userUpdateDto.getEmail().trim();
        String phone = userUpdateDto.getPhone().trim();
        Role role = userUpdateDto.getRole();

        if (name.isBlank()) {
            userUpdateDto.setName(null);
        }

        if (username.isBlank()) {
            userUpdateDto.setUsername(null);
        }

        if (email.isBlank()) {
            userUpdateDto.setEmail(null);
        }

        if (phone.isBlank()) {
            userUpdateDto.setPhone(null);
        }

        User user = userService.getUserUsername(oldUsername);

        if (user == null) {
            return "Не удалось найти игрока по никнейму: " + oldUsername + "!";
        }

        return userService.updateUserData(userUpdateDto, user);
    }

    public String createNewNpcValidator(EmployeeDto employeeDto) {
        String message = employeeService.validator(employeeDto.getUsername(), employeeDto.getEmail(), employeeDto.getPhone());

        if (message != null) {
            return message;
        }

        Employee employee = new Employee(
                employeeDto.getName(),
                employeeDto.getUsername(),
                employeeDto.getPassword(),
                employeeDto.getEmail(),
                employeeDto.getPhone(),
                employeeDto.getRole(),
                employeeDto.getSalary(),
                employeeDto.getBonus()
        );

        return employeeService.createNewNpc(employee);
    }

    public EmployeeDto getEmployeeDto(String username) {
        Employee employee = employeeService.getEmployee(username);

        if (employee == null) {
            return null;
        }

        return new EmployeeDto(
                employee.getName(),
                employee.getUsername(),
                employee.getPassword(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getRole(),
                employee.getSalary(),
                employee.getBonus()
        );
    }

    public String updateDataEmployeeValidator(EmployeeUpdateDto employeeUpdateDto, String oldUsername) {
        String message = employeeService.validator(employeeUpdateDto.getUsername(), employeeUpdateDto.getEmail(), employeeUpdateDto.getPhone());

        if (message != null) {
            return message;
        }

        Employee employee = employeeService.getEmployee(oldUsername);

        if (employee == null) {
            return "Не удалось найти npc по никнейму: " + oldUsername + "!";
        }

        boolean changes = checkChangesEmployee(employee, employeeUpdateDto);

        if (!changes) {
            return "Данные npc не были изменены!";
        }

        employeeService.updateEmployeeData(employee);
        return null;
    }

    public boolean checkChangesEmployee(Employee employee, EmployeeUpdateDto employeeUpdateDto) {
        boolean flag = false;

        if (employeeUpdateDto.getName() != null && !employeeUpdateDto.getName().isBlank() && !employeeUpdateDto.getName().equals(employee.getName())) {
            employee.setName(employeeUpdateDto.getName().trim());
            flag = true;
        }

        if (employeeUpdateDto.getUsername() != null && !employeeUpdateDto.getUsername().isBlank() && !employeeUpdateDto.getUsername().equals(employee.getUsername())) {
            employee.setUsername(employeeUpdateDto.getUsername().trim());
            flag = true;
        }

        if (employeeUpdateDto.getEmail() != null && !employeeUpdateDto.getEmail().isBlank() && !employeeUpdateDto.getEmail().equals(employee.getEmail())) {
            employee.setEmail(employeeUpdateDto.getEmail().trim());
            flag = true;
        }

        if (employeeUpdateDto.getPhone() != null && !employeeUpdateDto.getPhone().isBlank() && !employeeUpdateDto.getPhone().equals(employee.getPhone())) {
            employee.setPhone(employeeUpdateDto.getPhone().trim());
            flag = true;
        }

        if (employeeUpdateDto.getPassword() != null && !employeeUpdateDto.getPassword().isBlank()) {
            employee.setPassword(employeeUpdateDto.getPassword().trim());
            flag = true;
        }

        if (employeeUpdateDto.getRole() != null && !employeeUpdateDto.getRole().equals(employee.getRole())) {
            employee.setRole(employeeUpdateDto.getRole());
            flag = true;
        }

        if (employeeUpdateDto.getSalary() != null && employeeUpdateDto.getSalary() != employee.getSalary()) {
            employee.setSalary(employeeUpdateDto.getSalary());
            flag = true;
        }

        if (employeeUpdateDto.getBonus() != null && employeeUpdateDto.getBonus() != employee.getBonus()) {
            employee.setBonus(employeeUpdateDto.getBonus());
            flag = true;
        }

        return flag;
    }

    public String createNewDishValidator(DishCreateDto dishCreateDto) {
        String message = dishService.validator(dishCreateDto.getName());

        if (message != null) {
            return message;
        }

        Dish dish = new Dish();
        dish.setName(dishCreateDto.getName().trim());
        dish.setPrice(dishCreateDto.getPrice());
        dish.setCategory(dishCreateDto.getCategory());

        dishService.createNewDish(dish);

        return null;
    }

    public DishDto getDishDto(int id) {
        Dish dish = dishService.getDish(id);

        if (dish == null) {
            return null;
        }

        return new DishDto(
                dish.getName(),
                dish.getCategory(),
                dish.getPrice()
        );
    }

    public String updateDataDish(int id, DishDto dishDto) {
        String message = dishService.validator(dishDto.getName());

        if (message != null) {
            return message;
        }

        Dish dish = dishService.getDish(id);

        boolean checkUpdate = checkChangesDish(dishDto, dish);

        if (!checkUpdate) {
            return "Вы не внесли изменения в блюдо!";
        }

        dishService.updateDataDish(dish);
        return null;
    }

    public boolean checkChangesDish(DishDto dishDto, Dish dish) {
        boolean flag = false;

        if (dishDto.getName() != null && !dishDto.getName().trim().isBlank() && !dishDto.getName().equals(dish.getName())) {
            dish.setName(dishDto.getName().trim());
            flag = true;
        }

        if (dishDto.getCategory() != null && !dishDto.getCategory().equals(dish.getCategory())) {
            dish.setCategory(dishDto.getCategory());
            flag = true;
        }

        if (dishDto.getPrice() != null && dishDto.getPrice() != dish.getPrice()) {
            dish.setPrice(dishDto.getPrice());
            flag = true;
        }

        return flag;
    }

    public String createNewProductValidator(ProductDto productDto) {
        String message = productService.createProductValidator(productDto.getName());

        if (message != null) {
            return message;
        }

        Product product = new Product();
        product.setName(productDto.getName().trim());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());

        return productService.createNewProduct(product);
    }

    public ProductDto getProductDto(int id) {
        Product product = productService.getProduct(id);

        if (product == null) {
            return null;
        }

        return new ProductDto(
                product.getName(),
                product.getCategory(),
                product.getPrice()
        );
    }

    public String updateDataProduct(int id, ProductDto productDto) {
        String message = productService.updateValidator(productDto.getName());

        if (message != null) {
            return message;
        }

        Product product = productService.getProduct(id);

        if (product == null) {
            return "Не удалось найти товар!";
        }

        boolean changes = checkChangesProduct(productDto, product);

        if (!changes) {
            return "Не было внесено изменений!";
        }

        return productService.updateDataProduct(product);
    }

    public boolean checkChangesProduct(ProductDto productDto, Product product) {
        boolean flag = false;

        if (productDto.getName() != null && !productDto.getName().trim().isBlank() && !productDto.getName().equals(product.getName())) {
            product.setName(productDto.getName().trim());
            flag = true;
        }

        if (productDto.getCategory() != null && !productDto.getCategory().equals(product.getCategory())) {
            product.setCategory(productDto.getCategory());
            flag = true;
        }

        if (productDto.getPrice() != null && productDto.getPrice() != product.getPrice()) {
            product.setPrice(productDto.getPrice());
            flag = true;
        }

        return flag;
    }

    public String rejectedMessage(int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User administrator = customUserDetails.getUser();
        MessageSupport messageSupport = supportService.getMessageSupport(id);

        if (messageSupport == null) {
            return "Не удалось найти обращение по ID: " + id;
        }

        messageSupport.setStatus(Status.REJECTED);
        messageSupport.setAnswer("Без ответа!");
        messageSupport.setAdministrator(administrator);

        supportService.replyToMessageSave(messageSupport);
        return null;
    }
}