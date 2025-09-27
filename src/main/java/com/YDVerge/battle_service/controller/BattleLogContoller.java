package com.YDVerge.battle_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.YDVerge.battle_service.service.BattleLogService;
import com.YDVerge.battle_service.service.BattleLogService.BattleLogDoc;

@RestController
@RequestMapping("/api/battles")
public class BattleLogContoller {

    private final BattleLogService battleLogService;

    public BattleLogContoller(BattleLogService battleLogService) {
        this.battleLogService = battleLogService;
    }

    // GET all battles
    @GetMapping
    public List<BattleLogDoc> getAllBattles() {
        return battleLogService.getAllBattles();
    }

    // GET battles by player
    @GetMapping("/player/{playerId}")
    public List<BattleLogDoc> getBattlesByPlayer(@PathVariable("playerId") String playerId) {
        return battleLogService.getBattlesByPlayer(playerId);
    }
    
    
    @GetMapping("/player/gold/{playerId}")
    public int getGoldByPlayer(@PathVariable("playerId") String playerId) {
        return battleLogService.getGoldByPlayer(playerId);
    }
}
