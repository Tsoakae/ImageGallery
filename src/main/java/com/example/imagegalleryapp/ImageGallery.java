package com.example.imagegalleryapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ImageGallery extends Application {
    private String[] thumbnailPaths = {
            "/Images/thumbnail.jpeg",
            "/Images/thumbnail2.jpg",
            "/Images/thumbnail3.jpg",
            "/Images/thumbnail4.jpeg"
    };
    private String[] fullImagePaths = {
            "/Images/image.jpeg",
            "/Images/image2.jpg",
            "/Images/image3.jpg",
            "/Images/image4.jpeg"
    };
    private int currentImageIndex = 0;
    private ImageView fullImageView;

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        // Center the thumbnails
        gridPane.setAlignment(Pos.CENTER);

        // Adding heading for thumbnails
        gridPane.add(new javafx.scene.control.Label("My Thumbnails"), 0, 0, 2, 1);

        for (int i = 0; i < thumbnailPaths.length; i++) {
            ImageView imageView = createThumbnail(thumbnailPaths[i]);
            int finalI = i;
            imageView.setOnMouseClicked(e -> showFullImage(finalI, primaryStage));
            gridPane.add(imageView, i % 2, i / 2 + 1);
        }

        Scene scene = new Scene(gridPane, 500, 400);
        // Inside your start() method
        scene.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Image Gallery");
        primaryStage.show();
    }

    private ImageView createThumbnail(String path) {
        try {
            Image image = new Image(getClass().getResourceAsStream(path));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(100);
            return imageView;
        } catch (NullPointerException e) {
            e.printStackTrace();
            // Return an empty ImageView if image loading fails
            return new ImageView();
        }
    }

    private void showFullImage(int index, Stage primaryStage) {
        currentImageIndex = index;
        try {
            Image image = new Image(getClass().getResourceAsStream(fullImagePaths[index]));
            fullImageView = new ImageView(image);
            fullImageView.setFitWidth(500); // Increase width
            fullImageView.setFitHeight(300); // Increase height
            Button backButton = new Button("Back");
            Button previousButton = new Button("Previous");
            Button nextButton = new Button("Next");

            // Return to thumbnails view
            backButton.setOnAction(e -> primaryStage.setScene(createThumbnailScene(primaryStage)));
            previousButton.setOnAction(e -> showPreviousImage());
            nextButton.setOnAction(e -> showNextImage());

            // Heading for full picture
            VBox vbox = new VBox(new javafx.scene.control.Label("My Full Picture"), fullImageView);
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10);
            vbox.setPadding(new Insets(10));

            // Horizontal layout for buttons
            HBox buttonBox = new HBox(backButton, previousButton, nextButton);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setSpacing(10);

            vbox.getChildren().add(buttonBox);

            // Set specific dimensions for full picture scene
            Scene fullImageScene = new Scene(vbox, 600, 400);
            primaryStage.setScene(fullImageScene);
            primaryStage.setTitle("Full Image");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private Scene createThumbnailScene(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        // Center the thumbnails
        gridPane.setAlignment(Pos.CENTER);

        // Adding heading for thumbnails
        gridPane.add(new javafx.scene.control.Label("My Thumbnails"), 0, 0, 2, 1);

        for (int i = 0; i < thumbnailPaths.length; i++) {
            ImageView imageView = createThumbnail(thumbnailPaths[i]);
            int finalI = i;
            imageView.setOnMouseClicked(e -> showFullImage(finalI, primaryStage));
            gridPane.add(imageView, i % 2, i / 2 + 1);
        }

        Scene scene = new Scene(gridPane, 500, 400);
        primaryStage.setTitle("Image Gallery");
        return scene;
    }

    private void showPreviousImage() {
        currentImageIndex = (currentImageIndex - 1 + fullImagePaths.length) % fullImagePaths.length;
        Image image = new Image(getClass().getResourceAsStream(fullImagePaths[currentImageIndex]));
        fullImageView.setImage(image);
    }

    private void showNextImage() {
        currentImageIndex = (currentImageIndex + 1) % fullImagePaths.length;
        Image image = new Image(getClass().getResourceAsStream(fullImagePaths[currentImageIndex]));
        fullImageView.setImage(image);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
