package com.YDVerge.battle_service.controller;

import com.YDVerge.battle_service.kafka.BattleCompletedEvent;
import com.YDVerge.battle_service.service.TriggerBattleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/battle")
public class BattleController {

    private final TriggerBattleService triggerBattleService;

    public BattleController(TriggerBattleService triggerBattleService) {
        this.triggerBattleService = triggerBattleService;
    }

    @PostMapping("/start")
    public BattleCompletedEvent startBattle(@RequestParam(name = "playerId") String playerId) {
	BattleCompletedEvent event = triggerBattleService.fight(playerId);
	System.out.println(event);
        return  event; 
    }
}
