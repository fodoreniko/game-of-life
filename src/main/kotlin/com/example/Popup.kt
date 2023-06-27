package com.example

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.paint.Color


class Popup {
    fun display() {
        val popupwindow = Stage()
        popupwindow.icons.add(Image("/info.jpg"))
        popupwindow.initModality(Modality.APPLICATION_MODAL)
        popupwindow.title = "info"
        popupwindow.isResizable = false

        val label = Label("Rules")
        label.style = "-fx-font: bold 16pt \"Arial\";"
        label.padding = Insets(10.0)

        val hboxes = Array(4) { HBox(20.0) }

        for(i in 0..3) {
            hboxes[i].alignment = Pos.CENTER_RIGHT
            hboxes[i].padding = Insets(20.0)
        }

        val rule1 = Label("Each cell with one or no neighbors dies, as if by solitude.")
        val rule2 = Label("Each cell with four or more neighbors dies, as if by overpopulation.")
        val rule3 = Label("Each cell with two or three neighbors survives.")
        val rule4 = Label("Each cell with three neighbors becomes populated.")

        val layout = VBox(20.0)

        val images = Array(8) { ImageView() }
        val nyil = Array(8) { ImageView() }
        for(i in 0..7) {
            images[i] = ImageView(Image("/im$i.png"))
            nyil[i] = ImageView(Image("/nyil.png"))
        }


        hboxes[0].children.addAll(rule1, images[0], nyil[0], images[1])
        hboxes[1].children.addAll(rule2, images[2], nyil[1], images[3])
        hboxes[2].children.addAll(rule3, images[4], nyil[2], images[5])
        hboxes[3].children.addAll(rule4, images[6], nyil[3], images[7])

        layout.children.addAll(label, hboxes[0], hboxes[1], hboxes[2], hboxes[3])
        layout.alignment = Pos.CENTER
        val scene1 = Scene(layout)
        popupwindow.scene = scene1
        popupwindow.showAndWait()
    }
}