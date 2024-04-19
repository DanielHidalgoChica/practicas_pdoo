#encoding:utf-8
module Irrgarten
    # The Game class represents a game of Irrgarten.
    class Game
        @@MAX_ROUNDS = 10

        # Initializes a new instance of the Game class.
        # @param nplayers [Integer] The number of players in the game.
        def initialize(nplayers)
            @players = Array.new(nplayers)
            nplayers.times { |i|
                @players[i] = Player.new(i, Dice.random_intelligence, Dice.random_strength)
            }
            @current_player_index = Dice.who_starts(nplayers)
            @current_player = @players[@current_player_index]
            @monsters = Array.new(0)
            @log = ""
            configure_labyrinth
        end

        # Checks if the game has finished.
        # @return [Boolean] True if the game has finished, false otherwise.
        def finished
            @labyrinth.have_a_winner
        end

        # Moves to the next step in the game.
        # @param preferred_direction [Symbol] The preferred direction to move.
        # @return [Boolean] True if the game has finished, false otherwise.
        def next_step(preferred_direction)
            @log=""
            dead=@current_player.dead
            if !dead
                direction = actual_direction(preferred_direction)
                if direction != preferred_direction
                    log_player_no_orders
                end

                monster = @labyrinth.put_player(direction, @current_player)

                if monster.nil?
                    log_no_monster
                else
                    winner=combat(monster)
                    manage_reward(winner)
                end
            else
                manage_resurrection
            end

            end_game=finished
            if !end_game
                next_player
            end

            end_game
        end

        # Gets the current state of the game.
        # @return [GameState] The current state of the game.
        def game_state
            labyrinth_s = @labyrinth.to_s
            players_s = ""
            @players.each do |player|
                players_s += player.to_s + "\n"
            end
            monsters_s = ""
            @monsters.each do |monster|
                monsters_s += monster.to_s + "\n"
            end
            current_p = @current_player_index
            winner = self.finished
            state = GameState.new(labyrinth_s, players_s, monsters_s, current_p, winner, @log)
            state
        end

        private

        # Configures the labyrinth for the game.
        def configure_labyrinth
            n_rows = 5
            n_cols = 5
            exit_row = 1
            exit_col = 1
            @labyrinth = Labyrinth.new(n_rows, n_cols, exit_row, exit_col)

            @labyrinth.add_block(Orientation::HORIZONTAL, 0, 0, 2)
            @labyrinth.add_block(Orientation::VERTICAL, 0, 1, 2)

            goblin = Monster.new("Goblin", Dice.random_intelligence, Dice.random_strength)
            @labyrinth.add_monster(2, 2, goblin)
            @monsters << goblin

            piglin = Monster.new("Piglin", Dice.random_intelligence, Dice.random_strength)
            @labyrinth.add_monster(4, 4, piglin)
            @monsters << piglin

            zombie = Monster.new("Zombie", Dice.random_intelligence, Dice.random_strength)
            @labyrinth.add_monster(4, 0, zombie)
            @monsters << zombie

            skeleton = Monster.new("Skeleton", Dice.random_intelligence, Dice.random_strength)
            @labyrinth.add_monster(0, 4, skeleton)
            @monsters << skeleton

            @labyrinth.spread_players(@players)
        end

        # Moves to the next player in the game.
        def next_player
            if (@current_player_index == @players.length - 1)
                @current_player_index = 0
            else
                @current_player_index = @current_player_index + 1
            end

            @current_player = @players[@current_player_index]
        end

        # Gets the actual direction to move.
        # @param preferred_direction [Symbol] The preferred direction to move.
        # @return [Symbol] The actual direction to move.
        def actual_direction(preferred_direction)
            current_row=@current_player.row
            current_col=@current_player.col
            valid_moves = @labyrinth.valid_moves(current_row, current_col)
            @current_player.move(preferred_direction, valid_moves)
        end

        # Manages the combat between the player and a monster.
        # @param monster [Monster] The monster to fight.
        # @return [Boolean] True if the player has won, false if the monster has won.
        # @note The combat is resolved in turns, with the player attacking first.
        def combat(monster)
            rounds = 0
            winner = GameCharacter::PLAYER
            player_attack= @current_player.attack
            lose = monster.defend(player_attack)
            while !lose && rounds < @@MAX_ROUNDS
                winner = GameCharacter::MONSTER
                rounds+=1
                monster_attack = monster.attack
                lose = @current_player.defend(monster_attack)
                if !lose
                    player_attack = @current_player.attack
                    winner = GameCharacter::PLAYER
                    lose = monster.defend(player_attack)
                end

            end
            log_rounds(rounds, @@MAX_ROUNDS)
            winner
        end

        # Manages the reward of the winner of a combat.
        # @param winner [Symbol] The winner of the combat.
        # @note The winner of the combat receives a reward.
        # @note The reward is a random weapon or shield.
        def manage_reward(winner)
            if winner==GameCharacter::PLAYER
                @current_player.receive_reward
                log_player_won
            else
                log_monster_won
            end
        end

        # Manages the resurrection of the current player.
        # @note The player has a chance to resurrect.
        def manage_resurrection
            resurrect = Dice.resurrect_player
            if resurrect
                @current_player.resurrect
                log_resurrected
            else
                log_player_skip_turn
            end
        end

        # Logs that the current player has won the game.
        def log_player_won
            @log += "Player number " + @current_player.number.to_s + " has won. \n"
        end

        # Logs that the monster has won the game.
        def log_monster_won
            @log += "The monster has won \n"
        end

        # Logs that the current player has been resurrected.
        def log_resurrected
            @log += "The player " + @current_player.number.to_s + " has resurrected.\n"
        end

        # Logs that the current player has skipped a turn because they were dead.
        def log_player_skip_turn
            @log += "Player number " + @current_player.number.to_s + " has lost the turn " +
                "because they were dead. \n"
        end

        # Logs that the current player couldn't follow the instructions from the human player.
        def log_player_no_orders
            @log += "Player number " + @current_player.number.to_s + " couldn't follow " +
                "the instructions from the human player. \n"
        end

        # Logs that the current player couldn't move or moved to an empty box.
        def log_no_monster
            @log += "Player number " + @current_player.number.to_s + " couldn't move or " +
                "moved to an empty box. \n"
        end

        # Logs the number of rounds passed in the game.
        # @param rounds [Integer] The number of rounds passed.
        # @param max [Integer] The maximum number of rounds.
        def log_rounds(rounds, max)
            @log += "Rounds passed " + rounds.to_s + " / " + max.to_s + "\n"
        end
    end
end
