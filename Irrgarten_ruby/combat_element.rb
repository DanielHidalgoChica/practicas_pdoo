# encoding:utf-8
require_relative 'dice'
module Irrgarten
class CombatElement
    # Initializes a new CombatElement object with the given effect and uses.
    def initialize(effect,uses)
        @effect = effect
        @uses = uses
    end

    # @return the effect of the CombatElement
    def produce_effect
        if @uses > 0
            @uses -= 1
            @effect
        else
            0.0
        end
    end

    # Discards the CombatElement from the player's inventory
    # @return [bool] true if the element was discarded, false otherwise
    def discard
        Dice.discard_element(@uses)
    end

    # @return [String] a string representation of the CombatElement
    def to_s
        "[#{@effect},#{@uses}]"
    end

    protected :produce_effect
end

end