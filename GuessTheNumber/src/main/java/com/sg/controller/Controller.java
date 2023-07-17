package com.sg.controller;

import com.sg.model.Game;
import com.sg.model.Round;
import com.sg.service.GuessTheNumberServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("api")
@Validated
public class Controller {
    private final GuessTheNumberServiceLayer serviceLayer;

    @Autowired
    public Controller(GuessTheNumberServiceLayer serviceLayer) {
        this.serviceLayer = serviceLayer;
    }

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int startGame() {
        return serviceLayer.start();
    }

    @PostMapping("/guess")
    public ResponseEntity<Round> guessAnswer(@RequestParam int gameID,
                                             @Valid @Pattern(regexp = "^[0-9]{4}$")
                                             @RequestParam String guess) {
        Round round = serviceLayer.checkGuess(gameID, guess);
        if(round == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(round, HttpStatus.CREATED);
    }

    @GetMapping("/game")
    public List<Game> getGames() {
        return serviceLayer.getAllGames();
    }

    @GetMapping("/game/{gameID}")
    public ResponseEntity<Game> findByID(@PathVariable int gameID) {
        Game game = serviceLayer.getGameByID(gameID);
        if(game == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(game);
    }

    @GetMapping("/round/{gameID}")
    public List<Round> getRounds(@PathVariable int gameID) {
        return serviceLayer.getRoundList(gameID);
    }
}
