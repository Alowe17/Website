package com.example.Web_Service.controller.api;

import com.example.Web_Service.model.dto.adminDto.*;
import com.example.Web_Service.model.dto.*;
import com.example.Web_Service.model.dto.adminDto.DishDto;
import com.example.Web_Service.model.dto.adminDto.promocode.PromoCodeCreateDto;
import com.example.Web_Service.model.dto.adminDto.promocode.PromoCodeDto;
import com.example.Web_Service.model.dto.adminDto.promocode.PromoCodeUpdateDto;
import com.example.Web_Service.model.dto.adminDto.reward.request.RewardUpdateDto;
import com.example.Web_Service.model.dto.adminDto.reward.response.RewardDto;
import com.example.Web_Service.model.dto.adminDto.support.SupportMessageResponseDto;
import com.example.Web_Service.model.dto.adminDto.user.UpdateDataUserRequestDto;
import com.example.Web_Service.model.dto.adminDto.user.UserUpdatePasswordRequestDto;
import com.example.Web_Service.model.entity.PromoCode;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.repository.PromoCodeRepository;
import com.example.Web_Service.service.AdminService;
import com.example.Web_Service.service.PromoCodeService;
import com.example.Web_Service.service.RewardService;
import com.example.Web_Service.users.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminApiController {
    private final AdminService adminService;
    private final PromoCodeService promoCodeService;
    private final RewardService rewardService;
    private final PromoCodeRepository promoCodeRepository;

    public AdminApiController(AdminService adminService, PromoCodeService promoCodeService, RewardService rewardService, PromoCodeRepository promoCodeRepository) {
        this.adminService = adminService;
        this.promoCodeService = promoCodeService;
        this.rewardService = rewardService;
        this.promoCodeRepository = promoCodeRepository;
    }

    @GetMapping
    public ResponseEntity<?> getRights (Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        if (user.getRole().name().equals("ADMINISTRATOR")) {
            return ResponseEntity.ok().body(Map.of("message", "Добро пожаловать, " + customUserDetails.getUsername()));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "У вас недостаточно прав для посещения данной страницы!"));
    }

    @GetMapping("/list/users")
    public ResponseEntity<?> getAdminListUser () {
        List<UserProgressDto> list = adminService.getListUserProgressDto();

        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("message", "В базе данных не найдено учетные записи пользователей!"));
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/list/employees")
    public ResponseEntity<?> getAdminListEmployee () {
        List<CreateNewEmployeeRequestDto.EmployeeDto> list = adminService.getListEmployeeDto();

        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("message", "В базе данных не найдено NPC!"));
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/list/dishes")
    public ResponseEntity<?> getAdminListDish () {
        List<DishDto> list = adminService.getListDishDto();

        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "В базе данных не найдено блюд!"));
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/list/products")
    public ResponseEntity<?> getAdminListProduct () {
        List<ProductDto> list = adminService.getListProductDto();

        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("message", "В базе данных не найдено товаров!"));
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/list/tickets")
    public ResponseEntity<?> getAdminListSupport () {
        List<SupportMessageResponseDto> list = adminService.getListSupportMessageDto();

        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/password/{username}")
    public ResponseEntity<?> getAdminUpdatePassword (@PathVariable String username) {
        UserUpdatePasswordRequestDto user = adminService.getUserUpdatePasswordDto(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/password")
    public ResponseEntity<?> updatePassword (@Valid @RequestBody UserUpdatePasswordRequestDto userUpdatePasswordRequestDto) {
        String message = adminService.updatePasswordUserValidator(userUpdatePasswordRequestDto);

        if (message != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Пароль был успешно изменен!"));
    }

    @GetMapping("/info-users/{username}")
    public ResponseEntity<?> getUserInfo (@PathVariable String username) {
        UserDto user = adminService.getUserData(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users/{username}")
    public ResponseEntity<?> updateUserData (@PathVariable String username, @Valid @RequestBody UpdateDataUserRequestDto updateDataUserRequestDto) {
        String message = adminService.updateDataUserValidator(updateDataUserRequestDto, username);

        if (message != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Данные аккаунта были успешно обновлены!"));
    }

    @PostMapping("/npcs")
    public ResponseEntity<?> createNewNpc (@Valid @RequestBody CreateNewEmployeeRequestDto createNewEmployeeRequestDto) {
        String message = adminService.createNewNpcValidator(createNewEmployeeRequestDto);

        if (message != null) {
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Успешно создан новый NPC!"));
    }

    @GetMapping("/info-npcs/{username}")
    public ResponseEntity<?> getNpcInfo (@PathVariable String username) {
        EmployeeUpdateResponseDto employeeUpdateResponseDto = adminService.getEmployeeDto(username);


        if (employeeUpdateResponseDto == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Не удалось найти NPC по никнейму: " + username + "!"));
        }

        return ResponseEntity.ok().body(employeeUpdateResponseDto);
    }

    @PostMapping("/npcs/{username}")
    public ResponseEntity<?> updateNpc (@PathVariable String username, @Valid @RequestBody EmployeeUpdateDataRequestDto employeeUpdateDataRequestDto) {
        String message = adminService.updateDataEmployeeValidator(employeeUpdateDataRequestDto, username);

        if (message != null) {
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Данные NPC были успешно обновлены!"));
    }

    @PostMapping("/dishes")
    public ResponseEntity<?> createNewDish (@Valid @RequestBody CreateNewDishDto createNewDishDto) {
        String message = adminService.createNewDishValidator (createNewDishDto);

        if (message != null) {
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Новое блюдо было успешно создано!"));
    }

    @GetMapping("/info-dishes/{dishId}")
    public ResponseEntity<?> getInfoDish (@PathVariable int dishId) {
        DishDto dishDto = adminService.getDishDto(dishId);

        if (dishDto == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Блюдо с таким ID не найдено!"));
        }

        return ResponseEntity.ok().body(dishDto);
    }

    @PostMapping("/dishes/{dishId}")
    public ResponseEntity<?> updateDish (@PathVariable int dishId, @Valid @RequestBody DishDto dishDto) {
        String message = adminService.updateDataDish(dishId, dishDto);

        if (message != null) {
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Данные блюда были успешно обновлены!"));
    }

    @PostMapping("/products")
    public ResponseEntity<?> createNewProduct (@Valid @RequestBody ProductDto productDto) {
        String message = adminService.createNewProductValidator (productDto);

        if (message != null) {
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Товар успешно зарегистрирован!"));
    }

    @GetMapping("/info-products/{id}")
    public ResponseEntity<?> getProductInfo (@PathVariable int id) {
        ProductDto productDto = adminService.getProductDto(id);

        if (productDto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(productDto);
    }

    @PostMapping("/products/{id}")
    public ResponseEntity <?> updateProduct (@PathVariable int id, @Valid @RequestBody ProductDto productDto) {
        String message = adminService.updateDataProduct (id, productDto);

        if (message != null) {
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Данные товара были успешно обновлены!"));
    }

    @PostMapping("/rejected-messages/{id}")
    public ResponseEntity<?> rejectedMessage (@PathVariable int id) {
        String message = adminService.rejectedMessage(id);

        if (message != null) {
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Обращение было успешно отклонено!"));
    }

    @PostMapping("/promo-codes")
    public ResponseEntity<?> createNewPromoCode (@Valid @RequestBody PromoCodeCreateDto promoCodeCreateDto, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String message = promoCodeService.create(promoCodeCreateDto, customUserDetails.getUser());

        if (message != null) {
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Промокод успешно создан!"));
    }

    @GetMapping("/list/promo-codes")
    public ResponseEntity<?> getPromoCode () {
        List<PromoCodeDto> list = promoCodeService.getPromoCodes();

        if (list.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Список промокодов пуст!"));
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/info-promo-codes/{id}")
    public ResponseEntity<?> getPromoCode (@PathVariable int id) {
        PromoCodeDto promoCodeDto = promoCodeService.getPromoCode(id);

        if (promoCodeDto == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Не удалось найти промокод по ID: " + id));
        }

        return ResponseEntity.ok().body(Map.of("message", promoCodeDto));
    }

    @PostMapping("/promo-codes/{id}")
    public ResponseEntity<?> updatePromoCode (@PathVariable int id, @Valid @RequestBody PromoCodeUpdateDto promoCodeUpdateDto) {
        return promoCodeService.updatePromoCode(id, promoCodeUpdateDto);
    }

    @GetMapping("/list/rewards/{id}")
    public ResponseEntity<?> getRewards (@PathVariable int id) {
        PromoCode promoCode = promoCodeRepository.findById(id).orElse(null);

        if (promoCode == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Не удалось найти промокод!"));
        }

        List<RewardDto> list = rewardService.getRewards(promoCode);

        if (list.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Не удалось найти награду для данного промокода!"));
        }

        return ResponseEntity.ok().body(Map.of("message", list));
    }

    @PostMapping("/rewards/{id}")
    public ResponseEntity<?> updateReward (@PathVariable int id, @Valid @RequestBody RewardUpdateDto rewardUpdateDto) {
        String message = rewardService.update(rewardUpdateDto, id);

        if (message != null) {
            return ResponseEntity.status(400).body(Map.of("message", message));
        }

        return ResponseEntity.ok().body(Map.of("message", "Награда успешно обновлена!"));
    }
}