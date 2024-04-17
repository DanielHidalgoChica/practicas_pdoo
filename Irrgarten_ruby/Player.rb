module Irrgarten
    # The Player class represents a player in the Irrgarten game.
    class Player
        @@MAX_WEAPONS = 2
        @@MAX_SHIELDS = 3
        @@INITIAL_HEALTH = 10
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

        # Moves the player in the specified direction.
        #
        # @param direction [Symbol] The direction to move.
        # @param valid_moves [Array<Symbol>] The valid moves for the player.
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

        # Receives a reward.
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
            r_uses = Dice.weapon_uses
            new_weapon = Weapon.new(r_power, r_uses)
            new_weapon
        end

        # Creates a new shield.
        #
        # @return [Shield] The new shield.
        def new_shield
            r_protection = Dice.shield_power
            r_uses = Dice.uses_left
            new_shield = shield.new(shield_power, uses_left)
            new_shield
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

        # Manages the hit received by the player.
        #
        # @param received_attack [Integer] The attack power received.
        def manage_hit(received_attack)
            defense = defensive_energy
            if defense < received_attack
                got_wounded
                inc_consecutive_hits
            else
                reset_hits
            end
            if @consecutive_hits == @@HITS2LOSE || self.dead
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
            @health = @health - @@HEALTH_DECREMENT
        end

        # Increases the consecutive hits counter.
        def inc_consecutive_hits
            @consecutive_hits += 1
        end
    end
end
