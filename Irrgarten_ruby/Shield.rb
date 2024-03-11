#encoding:utf-8
module Irrgarten
# This class represents a shield from the game Irrgarten
class Shield

    # Parameterized constructor
    # protection Protection offered by the shield
    # uses Uses left by the shield
    def initialize (protection, uses)
        @protection = protection
        @uses = uses
    end

    # Return the shield protection. If there are no uses left, it is 0
    def protect
        if @uses > 0
            @uses= @uses-1
            return @protection
        else
            return 0
        end
    end

    # Decides if a shield must be descarted, using the method DiscardElement
    # of the Dice class
    def discard 
        return Dice.discard_element(@uses)
    end 

    # Makes a string that contains information about the object
    # Returns a string with the info of the shield
    def to_s
        return "S["+@protection.to_s+","+@uses.to_s+"]"
    end
    

end

end 