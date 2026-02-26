package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.*;
import com.example.Web_Service.model.dto.adminDto.*;
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

    public List<CreateNewEmployeeRequestDto.EmployeeDto> getListEmployeeDto() {
        List<CreateNewEmployeeRequestDto.EmployeeDto> list = employeeService.getListEmployeeDto();

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

    public List<SupportMessageResponseDto> getListSupportMessageDto() {
        List<SupportMessageResponseDto> list = supportService.getListMessageSupportDto();

        if (list.isEmpty()) {
            return List.of();
        }

        return list;
    }

    public UserUpdatePasswordRequestDto getUserUpdatePasswordDto(String username) {
        User user = userService.getUserUsername(username);

        if (user == null) {
            return null;
        }

        return new UserUpdatePasswordRequestDto(
                user.getUsername(),
                user.getPassword()
        );
    }

    public String updatePasswordUserValidator(UserUpdatePasswordRequestDto userUpdatePasswordRequestDto) {
        User user = userService.getUserUsername(userUpdatePasswordRequestDto.getUsername());

        if (user == null) {
            return "Пользователь " + userUpdatePasswordRequestDto.getUsername() + " не найден!";
        }

        String password = userUpdatePasswordRequestDto.getPassword().trim();

        if (password.isBlank()) {
            return "Нельзя изменить пароль на пустой или состоящий из пробелов!";
        }

        if (password.length() < 12) {
            return "Запрещено ставить пароли длина которых меньше 12 символов!";
        }

        if (!userService.updateUserPassword(userUpdatePasswordRequestDto.getUsername(), userUpdatePasswordRequestDto.getPassword())) {
            return "Не удалось обновить пароль пользователю: " + userUpdatePasswordRequestDto.getUsername() + "!";
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

    public String updateDataUserValidator(UpdateDataUserRequestDto updateDataUserRequestDto, String oldUsername) {
        String message = userService.userDataValidator(updateDataUserRequestDto);

        if (message != null) {
            return message;
        }

        if (updateDataUserRequestDto.getBalance() < 0) {
            return "Баланс не может быть отрицательным!";
        }

        String name = updateDataUserRequestDto.getName().trim();
        String username = updateDataUserRequestDto.getUsername().trim();
        String email = updateDataUserRequestDto.getEmail().trim();
        String phone = updateDataUserRequestDto.getPhone().trim();
        Role role = updateDataUserRequestDto.getRole();

        if (name.isBlank()) {
            updateDataUserRequestDto.setName(null);
        }

        if (username.isBlank()) {
            updateDataUserRequestDto.setUsername(null);
        }

        if (email.isBlank()) {
            updateDataUserRequestDto.setEmail(null);
        }

        if (phone.isBlank()) {
            updateDataUserRequestDto.setPhone(null);
        }

        User user = userService.getUserUsername(oldUsername);

        if (user == null) {
            return "Не удалось найти игрока по никнейму: " + oldUsername + "!";
        }

        return userService.updateUserData(updateDataUserRequestDto, user);
    }

    public String createNewNpcValidator(CreateNewEmployeeRequestDto createNewEmployeeRequestDto) {
        String message = employeeService.validator(createNewEmployeeRequestDto.getUsername(), createNewEmployeeRequestDto.getEmail(), createNewEmployeeRequestDto.getPhone());

        if (message != null) {
            return message;
        }

        Employee employee = new Employee(
                createNewEmployeeRequestDto.getName(),
                createNewEmployeeRequestDto.getUsername(),
                createNewEmployeeRequestDto.getPassword(),
                createNewEmployeeRequestDto.getEmail(),
                createNewEmployeeRequestDto.getPhone(),
                createNewEmployeeRequestDto.getRole(),
                createNewEmployeeRequestDto.getSalary(),
                createNewEmployeeRequestDto.getBonus()
        );

        return employeeService.createNewNpc(employee);
    }

    public EmployeeUpdateResponseDto getEmployeeDto(String username) {
        Employee employee = employeeService.getEmployee(username);

        if (employee == null) {
            return null;
        }

        return new EmployeeUpdateResponseDto(
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

    public String updateDataEmployeeValidator(EmployeeUpdateDataRequestDto employeeUpdateDataRequestDto, String oldUsername) {
        String message = employeeService.validator(employeeUpdateDataRequestDto.getUsername(), employeeUpdateDataRequestDto.getEmail(), employeeUpdateDataRequestDto.getPhone());

        if (message != null) {
            return message;
        }

        Employee employee = employeeService.getEmployee(oldUsername);

        if (employee == null) {
            return "Не удалось найти npc по никнейму: " + oldUsername + "!";
        }

        boolean changes = checkChangesEmployee(employee, employeeUpdateDataRequestDto);

        if (!changes) {
            return "Данные npc не были изменены!";
        }

        employeeService.updateEmployeeData(employee);
        return null;
    }

    public boolean checkChangesEmployee(Employee employee, EmployeeUpdateDataRequestDto employeeUpdateDataRequestDto) {
        boolean flag = false;

        if (employeeUpdateDataRequestDto.getName() != null && !employeeUpdateDataRequestDto.getName().isBlank() && !employeeUpdateDataRequestDto.getName().equals(employee.getName())) {
            employee.setName(employeeUpdateDataRequestDto.getName().trim());
            flag = true;
        }

        if (employeeUpdateDataRequestDto.getUsername() != null && !employeeUpdateDataRequestDto.getUsername().isBlank() && !employeeUpdateDataRequestDto.getUsername().equals(employee.getUsername())) {
            employee.setUsername(employeeUpdateDataRequestDto.getUsername().trim());
            flag = true;
        }

        if (employeeUpdateDataRequestDto.getEmail() != null && !employeeUpdateDataRequestDto.getEmail().isBlank() && !employeeUpdateDataRequestDto.getEmail().equals(employee.getEmail())) {
            employee.setEmail(employeeUpdateDataRequestDto.getEmail().trim());
            flag = true;
        }

        if (employeeUpdateDataRequestDto.getPhone() != null && !employeeUpdateDataRequestDto.getPhone().isBlank() && !employeeUpdateDataRequestDto.getPhone().equals(employee.getPhone())) {
            employee.setPhone(employeeUpdateDataRequestDto.getPhone().trim());
            flag = true;
        }

        if (employeeUpdateDataRequestDto.getPassword() != null && !employeeUpdateDataRequestDto.getPassword().isBlank()) {
            employee.setPassword(employeeUpdateDataRequestDto.getPassword().trim());
            flag = true;
        }

        if (employeeUpdateDataRequestDto.getRole() != null && !employeeUpdateDataRequestDto.getRole().equals(employee.getRole())) {
            employee.setRole(employeeUpdateDataRequestDto.getRole());
            flag = true;
        }

        if (employeeUpdateDataRequestDto.getSalary() != null && employeeUpdateDataRequestDto.getSalary() != employee.getSalary()) {
            employee.setSalary(employeeUpdateDataRequestDto.getSalary());
            flag = true;
        }

        if (employeeUpdateDataRequestDto.getBonus() != null && employeeUpdateDataRequestDto.getBonus() != employee.getBonus()) {
            employee.setBonus(employeeUpdateDataRequestDto.getBonus());
            flag = true;
        }

        return flag;
    }

    public String createNewDishValidator(CreateNewDishDto createNewDishDto) {
        String message = dishService.validator(createNewDishDto.getName());

        if (message != null) {
            return message;
        }

        Dish dish = new Dish();
        dish.setName(createNewDishDto.getName().trim());
        dish.setPrice(createNewDishDto.getPrice());
        dish.setCategory(createNewDishDto.getCategory());

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