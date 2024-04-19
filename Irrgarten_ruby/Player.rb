#encoding:utf-8
module Irrgarten
    # The Player class represents a player in the Irrgarten game.
    class Player
        # The maximum number of weapons a player can have.
        @@MAX_WEAPONS = 2

        # The maximum number of shields a player can have.
        @@MAX_SHIELDS = 3

        # The initial health of a player.
        @@INITIAL_HEALTH = 10

        # The number of consecutive hits a player can take before losing.
        @@HITS2LOSE = 3

        # Initializes a new instance of the Player class.
        #
        # @param number [Integer] The player number.
        # @param intelligence [Integer] The player's intelligence level.
        # @param strength [Integer] The player's strength level.
        def initialize(number, intelligence, strength)
            @number = number
            @name = "Player #{@number}"
            @intelligence = intelligence
            @strength = strength
            set_pos(nil,nil)
            self.resurrect
        end

        # Resurrects the player, resetting their state.
        def resurrect
            @weapons = Array.new
            @shields = Array.new
            @health = @@INITIAL_HEALTH
            self.reset_hits
        end

        attr_reader :row, :col, :number

        # Sets the player's position.
        #
        # @param row [Integer] The row position.
        # @param col [Integer] The column position.
        def set_pos(row, col)
            @row = row
            @col = col
        end

        # Checks if the player is dead.
        #
        # @return [Boolean] True if the player is dead, false otherwise.
        def dead
            @health <= 0
        end

        # Moves the player in a given direction.
        # If the direction is not valid, the player will move in the first valid direction.
        # If there are no valid directions, the player will stay in the same position.
        # @param direction [Symbol] The direction to move.
        # @param valid_moves [Array<Symbol>] The valid directions to move.
        # @return [Symbol] The direction the player moved.
        def move(direction, valid_moves)
            size = valid_moves.size
            contained = valid_moves.include?(direction)
            if (size > 0 && !contained)
                valid_moves[0]
            else
                direction
            end
        end

        # Calculates the player's attack power.
        #
        # @return [Integer] The player's attack power.
        def attack
            @strength + self.sum_weapons
        end

        # Defends against an attack.
        #
        # @param received_attack [Integer] The attack power received.
        def defend(received_attack)
            manage_hit(received_attack)
        end

        # Receives a reward for winning a combat.
        # The reward consists of new weapons, shields, and health points.
        # The number of health points, weapons and shields is determined by the Dice class.
        def receive_reward
            w_reward = Dice.weapons_reward
            s_reward = Dice.shields_reward
            w_reward.times do
                wnew = new_weapon
                receive_weapon(wnew)
            end
            s_reward.times do
                snew = new_shield
                receive_shield(snew)
            end
            extra_health =Dice.health_reward
            @health += extra_health
        end

        # Returns a string representation of the player's state.
        #
        # @return [String] The player's state.
        def to_s
            ret = "\nPlayer State" +
                        "\nName: " + @name +
                        "\nIntelligence: " + @intelligence.to_s +
                        "\nStrength: " + @strength.to_s +
                        "\nHealth: " + @health.to_s +
                        "\nPosition: (" + @row.to_s + "," + @col.to_s + ")" +
                        "\nConsecutive Hits: " + @consecutive_hits.to_s +
                        "\nWeapons: "
            @weapons.each { |a_weapon| ret += "\n\t" + a_weapon.to_s }
            ret += "\nShields:"
            @shields.each { |a_shield| ret += "\n\t" + a_shield.to_s }
            ret
        end

        private

        # Receives a weapon.
        #
        # @param w [Weapon] The weapon to receive.
        def receive_weapon(w)
            @weapons.delete_if { |wi| wi.discard }
            size = @weapons.size
            if size < @@MAX_WEAPONS
                @weapons << w
            end
        end

        # Receives a shield.
        #
        # @param s [Shield] The shield to receive.
        def receive_shield(s)
            @shields.delete_if { |si| si.discard }
            size = @shields.size
            if size < @@MAX_SHIELDS
                @shields << s
            end
        end

        # Creates a new weapon.
        #
        # @return [Weapon] The new weapon.
        def new_weapon
            r_power = Dice.weapon_power
            r_uses = Dice.uses_left
            Weapon.new(r_power, r_uses)
        end

        # Creates a new shield.
        #
        # @return [Shield] The new shield.
        def new_shield
            r_protection = Dice.shield_power
            r_uses = Dice.uses_left
            Shield.new(r_protection, r_uses)
        end

        # Calculates the total attack power of all weapons.
        #
        # @return [Integer] The total attack power.
        def sum_weapons
            sum = 0
            @weapons.each { |a_weapon| sum += a_weapon.attack }
            sum
        end

        # Calculates the total protection power of all shields.
        #
        # @return [Integer] The total protection power.
        def sum_shields
            sum = 0
            @shields.each { |a_shield| sum += a_shield.protect }
            sum
        end

        # Calculates the defensive energy of the player.
        #
        # @return [Integer] The defensive energy.
        def defensive_energy
            self.sum_shields + @intelligence
        end

        # Manages the player's state when hit.
        # If the player is hit, the consecutive hits counter is increased.
        # If the player is not hit, the counter is reset.
        # If the player has received the maximum number of consecutive hits, the player loses.
        # @param received_attack [Integer] The attack power received.
        # @return [Boolean] True if the player has lost, false otherwise.
        def manage_hit(received_attack)
            defense = self.defensive_energy
            if defense < received_attack
                got_wounded
                inc_consecutive_hits
            else
                reset_hits
            end
            if (@consecutive_hits == @@HITS2LOSE) || self.dead
                reset_hits
                lose = true
            else
                lose = false
            end
            lose
        end

        # Resets the consecutive hits counter.
        def reset_hits
            @consecutive_hits = 0
        end

        # Decreases the player's health when wounded.
        def got_wounded
            @health = @health - 1
        end

        # Increases the consecutive hits counter.
        def inc_consecutive_hits
            @consecutive_hits += 1
        end
    end
end
