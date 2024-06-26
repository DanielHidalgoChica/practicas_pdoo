# encoding:utf-8
require_relative 'directions'
require_relative 'game'

class TestP3
    def self.main
        game = Irrgarten::Game.new(3)
        directions = [Irrgarten::Directions::DOWN, Irrgarten::Directions::UP, Irrgarten::Directions::LEFT, Irrgarten::Directions::RIGHT]

        end_game = false
        rounds = 0
        while !end_game && rounds < 10
            generated_direction = directions[rand(4)]


            game_state = game.game_state
            puts game_state.labyrinth.to_s
            puts "Current Player: #{game_state.current_player}"
            puts "Chosen direction: #{generated_direction}"
            puts game_state.log
            puts game_state.monsters
            puts game_state.players
            game.next_step(generated_direction)
            rounds += 1
        end
    end
end

TestP3.main