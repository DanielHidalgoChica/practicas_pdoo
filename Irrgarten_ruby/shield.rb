#encoding:utf-8
module Irrgarten
    # This class represents a shield from the game Irrgarten
    class Shield
        # Parameterized constructor
        #
        # @param protection [Integer] Protection offered by the shield
        # @param uses [Integer] Uses left by the shield
        def initialize(protection, uses)
            @protection = protection
            @uses = uses
        end

        # Return the shield protection. If there are no uses left, it is 0
        #
        # @return [Integer] The shield protection
        def protect
            if @uses > 0
                @uses -= 1
                return @protection
            else
                return 0
            end
        end

        # Decides if a shield must be discarded, using the method DiscardElement
        # of the Dice class
        #
        # @return [Boolean] true if the shield should be discarded, false otherwise
        def discard
            return Dice.discard_element(@uses)
        end

        # Makes a string that contains information about the object
        #
        # @return [String] A string with the info of the shield
        def to_s
            return "S[#{@protection},#{@uses}]"
        end
    end
end
