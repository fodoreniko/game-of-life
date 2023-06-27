package com.example

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import tornadofx.add

class Game : Application() {

    companion object {
        private const val WIDTH = 512
        private const val HEIGHT = 512
    }

    private lateinit var mainScene: Scene

    private var lastFrameTime: Long = System.nanoTime()


    private val X = 30
    private val Y = 20

    private val board = Board(X,Y)
    private var buttons = Array(X) {
        Array(Y) {
            Button()
        }
    }

    private var started: Boolean = false

    private val blackbutton: String = "-fx-background-color: #1f2021; -fx-border-color: #888a89;"
    private val greenbutton: String = "-fx-background-color: #338a28; -fx-border-color: #cbd0d5;"

    private fun update() {
        board.countNeighbours()

        for(i in 0 until X) {
            for (j in 0 until Y) {
                buttons[i][j].style = when {
                    (board.array[i][j]) -> greenbutton
                    else -> blackbutton
                }
            }
        }
    }


    override fun start(mainStage: Stage) {
        mainStage.title = "Game of life"

        val root = Group()
        mainScene = Scene(root)
        mainStage.scene = mainScene

        mainStage.isResizable = false
        mainStage.icons.add(Image("/icon.png"))

        val vbox = VBox()

        val hbox = HBox()
        hbox.spacing = 30.0
        hbox.padding = Insets(20.0,20.0,20.0,20.0)
        hbox.alignment = Pos.BOTTOM_CENTER
        hbox.style = "-fx-background-color: #262729"

        vbox.style = ("-fx-border-width: 6;"
                    + "-fx-border-color: #262729;")

        val gridpane = GridPane()

        board.create()

        val buttonStyle: String = "-fx-font: bold 20pt \"Arial\";\n" +
                                    " -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 );"


        for(i in 0 until X) {
            for (j in 0 until Y) {

                val b = Button()
                b.style = blackbutton
                b.setPrefSize(25.0, 25.0)

                b.onAction = EventHandler { _: ActionEvent? ->
                    if(!board.array[i][j]) {
                        board.array[i][j] = true
                        b.style = greenbutton
                    } else if(board.array[i][j]) {
                        board.array[i][j] = false
                        b.style = blackbutton
                    }
                }
                buttons[i][j] = b
                gridpane.add(buttons[i][j], i, j)
            }
        }

        val resetButton = Button("reset")
        val startButton = Button("start")
        val infoButton = Button("info")

        hbox.add(resetButton)
        hbox.add(startButton)
        hbox.add(infoButton)

        startButton.style = buttonStyle
        resetButton.style = buttonStyle
        infoButton.style = buttonStyle

        startButton.onAction = EventHandler { _: ActionEvent? ->
            if(startButton.text == "start") {
                started = true
                startButton.text = "stop"
            } else if(startButton.text == "stop") {
                startButton.text = "start"
                started = false
            }
        }

        infoButton.onAction = EventHandler { _: ActionEvent? ->
            Popup().display()
        }

        resetButton.onAction = EventHandler { _: ActionEvent? ->
            if(startButton.text == "stop") {
                startButton.text = "start"
            }
            started = false
            board.create()
            for(i in 0 until X) {
                for (j in 0 until Y) {
                    buttons[i][j].style = blackbutton
                }
            }
        }

        vbox.add(gridpane)
        vbox.add(hbox)

        val canvas = Canvas(WIDTH.toDouble(), HEIGHT.toDouble())
        root.children.add(canvas)
        root.children.add(vbox)

        // Main loop
        object : AnimationTimer() {
            override fun handle(currentNanoTime: Long) {
                tickAndRender(currentNanoTime)
            }
        }.start()

        mainStage.show()
    }

    private fun tickAndRender(currentNanoTime: Long) {
        // the time elapsed since the last frame, in nanoseconds
        val elapsedNanos = currentNanoTime - lastFrameTime
        if (elapsedNanos < 500_000_000) return
        lastFrameTime = currentNanoTime

        if(started) {
            update()
        }
    }

}
