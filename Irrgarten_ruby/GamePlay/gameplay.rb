#encoding=utf-8
require_relative "../controller/controller.rb"
require_relative "../textUI/textUI.rb"
require_relative "../Game.rb"
require_relative "../Labyrinth.rb"
require_relative "../Orientation.rb"
require_relative "../GameCharacter.rb"
require_relative "../Player.rb"
require_relative "../Monster.rb"
require_relative "../GameState.rb"
require_relative "../Dice.rb"
require_relative "../Weapon.rb"
require_relative "../Shield.rb"
class GamePlay
    N_PLAYERS = 1
    # Main method to start the game
    def self.main
        game = Irrgarten::Game.new(N_PLAYERS)
        text_ui = UI::TextUI.new
        controller = Control::Controller.new(game, text_ui)
        controller.play
    end


end

GamePlay.main