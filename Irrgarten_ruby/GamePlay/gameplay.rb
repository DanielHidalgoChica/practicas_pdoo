#encoding=utf-8
require_relative "../controller/controller.rb"
require_relative "../textUI/textUI.rb"
require_relative "../game.rb"
require_relative "../labyrinth.rb"
require_relative "../orientation.rb"
require_relative "../game_character.rb"
require_relative "../player.rb"
require_relative "../monster.rb"
require_relative "../game_state.rb"
require_relative "../dice.rb"
require_relative "../weapon.rb"
require_relative "../shield.rb"
class GamePlay
    @N_PLAYERS = 1
    # Main method to start the game
    def self.main
        game = Irrgarten::Game.new(@N_PLAYERS)
        text_ui = UI::TextUI.new
        controller = Control::Controller.new(game, text_ui)
        controller.play
    end
end

GamePlay.main