# encoding: utf-8
require_relative 'player'
require_relative 'dice'
module Irrgarten
# Class that represents a player with a fuzzy intelligence. The player
# will have a preferred direction to move, but it will have a probability
# to move in a different direction.
# @see Player
class FuzzyPlayer < Player
    # Copy constructor
    def initialize(player)
        copy(player)
    end

    # The player will move in the preferred direction with a probability
    # based on its intelligence.
    # @return [Directions] the direction where the player will move
    def move(direction, valid_moves)
        preferred_direction = super(direction, valid_moves)
        Dice.next_step(preferred_direction, valid_moves, @intelligence)
    end

    # @return [Float] the defensive energy of the player
    def attack
        Dice.intensity(@strength) + self.sum_weapons
    end

    protected

    # @return [Float] the defensive energy of the player
    def defensive_energy
        Dice.intensity(@intelligence) + self.sum_shields
    end

    public

    # @return [String] a string representation of the player
    def to_s
        "Fuzzy #{super}"
    end

end
end
