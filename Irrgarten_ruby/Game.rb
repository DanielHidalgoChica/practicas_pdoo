module Irrgarten
class Game

@@MAX_ROUNDS = 10

def self.initialze(players, monsters) 
    @players = players
    @monsters = monsters
    @current_player_index = Dice.who_starts(players.size)
    @current_player = @players[@current_player_index]

    @log = ""

    @labyrinth = Labyrinth.new_emtpy
    self.configureLabyrinth
end

def finished
    @labyrinth.hava_a_winner
end

def next_step(preferred_direction)
end

def get_game_state
    labyrinth_s = @labyrinth.to_s
    players_s = @players.to_s
    monsters_s = @monsters.to_s
    current_p = current_player_index
    winner = self.finished
    state = GameState.new(labyrinth_s, players_s, monsters_s, current_p, winner, @log)
    state
end


private

def configure_labyrinth
end


def combat(monster)
# No expliado
end

def manage_reward(winner)
end

def manage_resurrection
end

def log_player_won
  @log += "EL JUGADOR HA GANADO EL COMBATE\n"
end

def log_monster_won
  @log += "EL MONSTRUO HA GANADO EL COMBATE\n"
end

def log_resurrected
  @log += "EL JUGADOR HA RESUCITADO\n"
end

def log_player_skip_turn
  @log += "EL JUGADOR HA PERDIDO EL TURNO POR ESTAR MUERTO\n"
end

def log_player_no_orders
  @log += "EL JUGADOR NO HA PODIDO SEGUIR LAS INSTRUCCIONES QUE SE LE HAN DADO\n"
end

def log_no_monster
  @log += "EL JUGADOR SE HA MOVIDO A UNA CELDA VACÍA O NO LE HA SIDO POSIBLE MOVERSE\n"
end

def log_rounds(rounds, max)
  @log += "SE HAN PRODUCIDO #{rounds} RONDAS DE UN TOTAL DE #{max}\n"
end



end
end
