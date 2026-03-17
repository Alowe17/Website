package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.game.choice.ChoiceDto;
import com.example.Web_Service.model.dto.DialogDto;
import com.example.Web_Service.model.dto.GameCharacterDto;
import com.example.Web_Service.model.dto.game.dish.response.DishDto;
import com.example.Web_Service.model.dto.game.scene.SceneDto;
import com.example.Web_Service.model.dto.game.dish.request.DishBuyDto;
import com.example.Web_Service.model.entity.*;
import com.example.Web_Service.model.enums.SceneType;
import com.example.Web_Service.repository.ChoiceRepository;
import com.example.Web_Service.repository.SceneRepository;
import com.example.Web_Service.repository.UserGameStateRepository;
import com.example.Web_Service.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class GameService {
    private final SceneRepository sceneRepository;
    private final ChoiceService choiceService;
    private final DialogService dialogService;
    private final UserGameStateRepository userGameStateRepository;
    private final ChoiceRepository choiceRepository;
    private final UserGameAttributeService userGameAttributeService;
    private final DishService dishService;
    private final UserRepository userRepository;
    private final UserProgressService userProgressService;

    public GameService (SceneRepository sceneRepository, ChoiceService choiceService, DialogService dialogService, UserGameStateRepository userGameStateRepository,
                        ChoiceRepository choiceRepository, UserGameAttributeService userGameAttributeService, DishService dishService,
                        UserRepository userRepository, UserProgressService userProgressService) {
        this.sceneRepository = sceneRepository;
        this.choiceService = choiceService;
        this.dialogService = dialogService;
        this.userGameStateRepository = userGameStateRepository;
        this.choiceRepository = choiceRepository;
        this.userGameAttributeService = userGameAttributeService;
        this.dishService = dishService;
        this.userRepository = userRepository;
        this.userProgressService = userProgressService;
    }

    public ResponseEntity<?> startChapterGame (Chapter chapter, UserProgress userProgress) {
        User user = userProgress.getUser();

        if (user == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Не удалось найти пользователя!"));
        }

        if (userProgress.getChapter().getNumber() == chapter.getNumber()) {
            Scene startScene = sceneRepository.findBySceneId("ch1_s1_start").orElse(null);

            UserGameState state = userGameStateRepository.findByUser(user).orElse(new UserGameState());
            List<UserGameAttribute> userGameAttribute = userGameAttributeService.userGameAttributeList(user);

            if (!userGameAttribute.isEmpty()) {
                userGameAttributeService.clearUserData(userGameAttribute);
            }

            state.setUser(user);
            state.setCurrentScene(startScene);

            userGameStateRepository.save(state);

            return getSceneDto(startScene, user);
        } else if (userProgress.getChapter().getNumber() < chapter.getNumber()) {
            return ResponseEntity.status(403).body(Map.of("message", "Вам необходимо пройти главы ранее, чтобы начать проходить эту!"));
        } else {
            return ResponseEntity.status(403).body(Map.of("message", "Вы не можете пройти главу повторно!"));
        }
    }

    @Transactional
    public ResponseEntity<?> choose (User user, int choiceId) {
        UserGameState userGameState = userGameStateRepository.findByUser(user).orElse(null);
        UserProgress userProgress = userProgressService.getUserProgress(user);

        if (userProgress == null) {
            return ResponseEntity.status(400).body(Map.of("message", "Не удалось найти или загрузить игровой прогресс!"));
        }

        if (userGameState == null) {
            return ResponseEntity.status(400).body(Map.of("message", "Не удалось найти прогресс игрока"));
        }

        Scene currentScene = userGameState.getCurrentScene();
        Choice choice = choiceRepository.findById(choiceId).orElse(null);

        if (choice == null) {
            return ResponseEntity.status(400).body(Map.of("message", "Выбор не найден!"));
        }

        if (!(choice.getSceneFrom().getId() == currentScene.getId()) && currentScene.getId() != 7) {
            return ResponseEntity.status(400).body(Map.of("message", "Нельзя выбрать этот вариант!"));
        }

        if (!isChoiceAvailable(user, choice)) {
            return ResponseEntity.status(403).body(Map.of("message", "Условие выбора не выполнено!"));
        }

        if (choice.getEffectKey() != null) {
            userGameAttributeService.updateDataUserGameAttribute(user, choice.getEffectKey(), choice.getEffectValue());
        }

        Scene nextScene = choice.getSceneTo();
        Chapter currentChapter = currentScene.getChapter();
        Chapter chapter = nextScene.getChapter();

        if (nextScene.getSceneType() == SceneType.NEXTCHAPTER) {
            userProgress.setChapter(nextScene.getChapter());
            userProgressService.updateProgress(userProgress);
        }

        userGameState.setCurrentScene(nextScene);
        userGameStateRepository.save(userGameState);

        return getSceneDto(nextScene, user);
    }

    private ResponseEntity<?> getSceneDto (Scene nextScene, User user) {
        List<Dialog> dialogs = dialogService.getDialogByScene(nextScene);
        List<Choice> choices = choiceService.getChoice(nextScene);

        List<DialogDto> dialogDtoList = dialogs.stream()
                .map(dialog -> new DialogDto(
                        dialog.getText(),
                        new GameCharacterDto(
                                dialog.getCharacter().getName(),
                                dialog.getCharacter().getImage(),
                                dialog.getCharacter().getType(),
                                dialog.getCharacter().getDescription(),
                                dialog.getCharacter().getStatus()
                        )
                ))
                .toList();

        List<ChoiceDto> choiceDtoList = choices.stream()
                .filter(choice -> isChoiceAvailable(user, choice))
                .map(choice -> new ChoiceDto(
                        choice.getId(),
                        choice.getText(),
                        choice.getSceneTo().getId()
                ))
                .toList();

        return ResponseEntity.ok().body(new SceneDto(
                nextScene.getSceneId(),
                choiceDtoList,
                dialogDtoList,
                nextScene.getSceneType()
        ));
    }

    private boolean isChoiceAvailable (User user, Choice choice) {
        if (choice.getRequiredKey() == null) {
            return true;
        }

        UserGameAttribute userGameAttribute = userGameAttributeService.getAttribute(user, choice.getRequiredKey());
        int currentValue = userGameAttribute != null ? userGameAttribute.getEffectValue() : 0;

        return currentValue >= choice.getRequiredMinValue();
    }

    public ResponseEntity<?> listDish () {
        List<Dish> dishes = dishService.listDish();

        if (dishes.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Блюда не были найдены! Попробуйте позже!"));
        }

        List<DishDto> dishDtos = dishes.stream()
                .map(dish -> new DishDto(
                        dish.getId(),
                        dish.getName(),
                        dish.getCategory(),
                        dish.getPrice()
                ))
                .toList();

        return ResponseEntity.ok().body(dishDtos);
    }

    public ResponseEntity<?> buyDish (User user, DishBuyDto dishBuyDto) {
        List<Integer> dishList = dishBuyDto.getDishIds();
        boolean orderedCheesecake = false;

        if (dishList.isEmpty()) {
            return ResponseEntity.status(400).body(Map.of("message", "Вы ничего не добавили в заказ!"));
        }

        List<Dish> dishes = dishList.stream()
                .map(dishService::getDish)
                .filter(Objects::nonNull)
                .toList();

        for (Dish dish : dishes) {
            if (dish.getName().equals("Сырники")) {
                orderedCheesecake = true;
                break;
            }
        }

        int totalAmount = dishes.stream()
                .mapToInt(Dish::getPrice)
                .sum();

        if (user.getBalance() < totalAmount) {
            return ResponseEntity.status(402).body(Map.of("message", "Недостаточно средств для оплаты! Ваш баланс: " + user.getBalance() + ". Сумма заказа: " + totalAmount));
        }

        user.setBalance(user.getBalance() - totalAmount);
        userRepository.save(user);

        if (orderedCheesecake) {
            userGameAttributeService.updateDataUserGameAttribute(user, "relation_paimon", 1);
            return choose(user, 11);
        } else {
            userGameAttributeService.updateDataUserGameAttribute(user, "relation_paimon", -1);
            return choose(user, 12);
        }
    }
}