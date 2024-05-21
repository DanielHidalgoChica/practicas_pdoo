# encoding: utf-8

module Irrgarten
# Class that represents a character in the labyrinth. It has a name,
# intelligence, strength, health and position in the labyrinth.
# @see Labyrinth
class LabyrinthCharacter

    @@INVALID_POS = -1

    # Constructor
    def initialize(name, intelligence, strength, health)
        @name = name
        @intelligence = intelligence
        @strength = strength
        @health = health
        set_pos(@@INVALID_POS, @@INVALID_POS)
    end

    # Copy constructor
    # @param other LabyrinthCharacter to copy
    def copy(other)
        @name = other.name
        @intelligence = other.intelligence
        @strength = other.strength
        @health = other.health
        @row = other.row
        @col = other.col
    end

    # @return [Boolean] true if the character is dead
    def dead
        @health <= 0
    end

    attr_reader :row, :col


    protected

    # Read only attributes
    def name
        @name
    end

    def intelligence
        @intelligence
    end

    def strength
        @strength
    end

    def health
        @health
    end

    def health=(health)
        @health = health
    end

    public

    def set_pos(row, col)
        @row = row
        @col = col
    end

    def to_s
        ret =   "#{@name}" +
                ", Intelligence: #{@intelligence}" +
                ", Strength: #{@strength}" +
                ", Health: #{@health}" +
                ", Pos(#{@row}, #{@col})"
        ret
    end

    protected

    # Decrease the health of the character
    def got_wounded
        @health -= 1
    end

end
end
