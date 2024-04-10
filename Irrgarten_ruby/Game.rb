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

        # Performs the next step in the game.
        # @param preferred_direction [Symbol] The preferred direction to move.
        def next_step(preferred_direction)
            # TODO: Implement next_step method
        end

        # Gets the current state of the game.
        # @return [GameState] The current state of the game.
        def get_game_state
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
            exit_col = 0
            @labyrinth = Labyrinth.new(n_rows, n_cols, exit_row, exit_col)
            monster = Monster.new("Goblin", Dice.random_intelligence, Dice.random_strength)
            @labyrinth.add_monster(2, 2, monster)
            @monsters << monster
        end

        # Moves to the next player in the game.
        def next_player
            if (@current_player_index == players.length - 1)
                @current_player_index = 0
            else
                @current_player_index = @current_player_index + 1
            end

            @current_player = @players[@current_player_index]
        end

        # Determines the actual direction to move based on the preferred direction.
        # @param preferred_direction [Symbol] The preferred direction to move.
        # @return [Symbol] The actual direction to move.
        def actual_direction(preferred_direction)
            # TODO: Implement actual_direction method
        end

        # Performs combat with a monster.
        # @param monster [Monster] The monster to combat with.
        def combat(monster)
            # TODO: Implement combat method
        end

        # Manages the reward for the winner of the game.
        # @param winner [Boolean] True if the player has won, false if the monster has won.
        def manage_reward(winner)
            # TODO: Implement manage_reward method
        end

        # Manages the resurrection of a player.
        def manage_resurrection
            # TODO: Implement manage_resurrection method
        end

        # Logs that the current player has won the game.
        def log_player_won
            @log += "Player number " + @current_player.number + " has won. \n"
        end

        # Logs that the monster has won the game.
        def log_monster_won
            @log += "The monster has won \n"
        end

        # Logs that the current player has been resurrected.
        def log_resurrected
            @log += "The player " + @current_player.number + " has resurrected.\n"
        end

        # Logs that the current player has skipped a turn because they were dead.
        def log_player_skip_turn
            @log += "Player number " + @current_player.number + " has lost the turn " +
                "because they were dead. \n"
        end

        # Logs that the current player couldn't follow the instructions from the human player.
        def log_players_no_orders
            @log += "Player number " + @current_player.number + " couldn't follow " +
                "the instructions from the human player. \n"
        end

        # Logs that the current player couldn't move or moved to an empty box.
        def log_no_monster
            @log += "Player number " + @current_player.number + " couldn't move or " +
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
