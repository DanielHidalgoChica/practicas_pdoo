# encoding:utf-8
require_relative 'monster'
require_relative 'player'
require_relative 'labyrinth'
require_relative 'game'
class TestP2
    def self.test_monster
        monster = Irrgarten::Monster.new('Piglin', Irrgarten::Dice.random_intelligence, Irrgarten::Dice.random_strength)
        puts monster.to_s
        puts "IS DEAD #{monster.dead}"
        5.times {monster.got_wounded}
        puts 'Monster after got wounded five times'
        puts monster.to_s
        puts "IS DEAD #{monster.dead}"
    end
    
    def self.test_player
        player = Irrgarten::Player.new('1', 9.0, 4)
        puts player.to_s
        player.set_pos(1, 1)
        puts "Fila del jugador: #{player.row}"
        puts 'Attack:'
        puts player.attack

    end
    
    def self.test_labyrinth
        monster = Irrgarten::Monster.new('Goblin', Irrgarten::Dice.random_intelligence, Irrgarten::Dice.random_strength)
        print monster.to_s
        labyrinth = Irrgarten::Labyrinth.new(5, 5, 0, 0)
        puts labyrinth.to_s
        
        labyrinth.add_monster(2, 2, monster)
        puts 'Added monster'
        print monster.to_s
        puts labyrinth.to_s
        

    end
    
    def self.test_game
        game = Irrgarten::Game.new(3)
        game_state = game.game_state
        puts 'Current player'
        puts game_state.current_player
        puts 'PLAYERS:'
        puts game_state.players
        puts 'MONSTERS'
        puts game_state.monsters
        puts 'LABYRINTH'
        puts game_state.labyrinth
    end
    
    def self.main
        test_monster
        test_player
        test_labyrinth
        test_game
    end
    
end

TestP2.main

