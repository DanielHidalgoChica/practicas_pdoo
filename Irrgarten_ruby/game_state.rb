#encoding:utf-8
module Irrgarten

class GameState
    # Initializes a new GameState instance.
    # @param labyrinth [Labyrinth] the labyrinth
    # @param players [Array<Player>] the players
    # @param monsters [Array<Monster>] the monsters
    # @param current_player [Player] the current player
    # @param winner [Player] the winner
    # @param log [String] the log
    # @return [GameState] the GameState instance
    def initialize (labyrinth, players,monsters,current_player,winner,log)
        @labyrinth = labyrinth
        @players = players
        @monsters = monsters
        @current_player = current_player
        @winner = winner
        @log = log
    end 

    # Create readers for each instance atribute
    attr_reader :labyrinth, :players, :monsters, :current_player, :winner, :log
end

end
