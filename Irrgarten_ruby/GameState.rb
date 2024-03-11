#enconding:utf-8
module Irrgarten

class GameState
    # Parameterized constructor for the class
    def initialize (labyrinth, players,monsters,current_player,winner,log)
        @labyrinth = labyrinth
        @players = players
        @monsters = monsters
        @current_player = current_player
        @winner = winner
        @log = log
    end 

    # Create readers for each instance atribute
    attr_reader :labyrinth,:players,:monsters,:current_player,:winner,:log
end 

end