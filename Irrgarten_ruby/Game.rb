module Irrgarten
class Game

@@MAX_ROUNDS = 10

def initialize (nplayers)
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

def finished
    @labyrinth.have_a_winner
end

def next_step(preferred_direction)
end

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

def configure_labyrinth
    n_rows = 5
    n_cols = 5
    exit_row = 1
    exit_col = 0
    @labyrinth = Labyrinth.new(n_rows,n_cols,exit_row,exit_col)
    monster = Monster.new("Goblin", Dice.random_intelligence, Dice.random_strength)
    @labyrinth.add_monster(2,2,monster)
    @monsters << monster
end

def next_player
    if(@current_player_index == players.length - 1)
        @current_player_index = 0
    else
        @current_player_index = @current_player_index + 1
    end

    @current_player = @players[@current_player_index]
end

def actual_direction (preferred_direction)
    
end 

def combat(monster)
# No expliado
end


def manage_reward(winner)
end

def manage_resurrection
end

def log_player_won
    @log+= "Player number " + @current_player.number  + " has won. \n"
end

def log_monster_won
    @log += "The monster has won \n"
end

def log_resurrected
    @log += "The player " + @current_player.number +  " has resurrected.\n"
end

def log_player_skip_turn
    @log += "Player number " + @current_player.number + " has lost the turn "
    + "because he was dead. \n"
end

def log_players_no_orders
    @log += "Player number " + @current_player.number + " couldn't follow "
    + "the instructions from the human player. \n"
end

def log_no_monster
    @log += "Player number " + @current_player.number + " couldn't move or "
    + " moved to an empty box. \n"
end

def log_rounds (rounds, max)
    @log += "Rounds passed " + rounds.to_s + " / " + max.to_s + "\n"
end


end
end
