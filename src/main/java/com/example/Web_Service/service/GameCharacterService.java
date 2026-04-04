package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.game.character.GameCharacterDto;
import com.example.Web_Service.model.entity.GameCharacter;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.enums.Role;
import com.example.Web_Service.model.enums.StatusGame;
import com.example.Web_Service.model.enums.Type;
import com.example.Web_Service.repository.GameCharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameCharacterService {
    private final GameCharacterRepository gameCharacterRepository;

    public GameCharacterService (GameCharacterRepository gameCharacterRepository) {
        this.gameCharacterRepository = gameCharacterRepository;
    }

    public List<GameCharacterDto> getGameCharacters (User user) {
        List<GameCharacter> gameCharacters = gameCharacterRepository.findAll();

        if (gameCharacters.isEmpty()) {
            return List.of();
        }

        if (user.getRole() == Role.ADMINISTRATOR ||
                user.getRole() == Role.TESTER ||
                user.getRole() == Role.NARRATIVEDESIGNER) {
            List<GameCharacterDto> gameCharacterDtoList = gameCharacters.stream()
                    .filter(gameCharacter -> gameCharacter.getType() == Type.GUIDE)
                    .map(character -> new GameCharacterDto(
                            character.getName(),
                            character.getImage(),
                            character.getType(),
                            character.getDescription(),
                            character.getStatus()
                    ))
                    .toList();

            return gameCharacterDtoList;
        }

        List<GameCharacterDto> gameCharacterDtoList = gameCharacters.stream()
                .filter(character -> (StatusGame.PUBLISHED == character.getStatus()) && (character.getType() == Type.GUIDE))
                .map(character -> new GameCharacterDto(
                        character.getName(),
                        character.getImage(),
                        character.getType(),
                        character.getDescription(),
                        character.getStatus()
                ))
                .toList();

        return gameCharacterDtoList;
    }
}