#encoding:utf-8
module Irrgarten
    # The Irrgarten module contains classes related to weapons in the game.

    class Weapon
        # Represents a weapon in the game.

        # Parameterized constructor
        # @param power [Float] the power of the weapon
        # @param uses [Integer] the number of uses left for the weapon
        def initialize(power, uses)
            # Initializes the instance fields of the weapon.
            @power = power
            @uses = uses
        end

        # Attacks with the weapon.
        # @return [Float] the power of the weapon if it has uses left, otherwise 0
        def attack
            if @uses > 0
                @uses -= 1
                return @power
            else
                return 0
            end
        end

        # Discards the weapon.
        # @return [Boolean] true if the weapon should be discarded, false otherwise
        def discard
            return Dice.discard_element(@uses)
        end

        # Returns a string representation of the weapon.
        # @return [String] a string containing the power and uses of the weapon
        def to_s
            return "W[#{@power}, #{@uses}]"
        end
    end
end
