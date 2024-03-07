package vcs.testvcs1;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.nio.charset.Charset;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

public class Controller {

    @FXML
    public TextField txtGuess;
    @FXML
    public Label lblTitle = new Label();
    @FXML
    private Label lblYear = new Label();
    @FXML
    private Label lblGenre = new Label();
    @FXML
    private Label lblOrigin = new Label();
    @FXML
    private Label lblDirector = new Label();
    @FXML
    private Label lblStar = new Label();

    @FXML
    public Label lblTitle1 = new Label();
    @FXML
    private Label lblYear1 = new Label();
    @FXML
    private Label lblGenre1 = new Label();
    @FXML
    private Label lblOrigin1 = new Label();
    @FXML
    private Label lblDirector1 = new Label();
    @FXML
    private Label lblStar1 = new Label();

    @FXML
    public Label lblTitle2 = new Label();
    @FXML
    private Label lblYear2 = new Label();
    @FXML
    private Label lblGenre2 = new Label();
    @FXML
    private Label lblOrigin2 = new Label();
    @FXML
    private Label lblDirector2 = new Label();
    @FXML
    private Label lblStar2 = new Label();

    @FXML
    public Label lblTitle3 = new Label();
    @FXML
    private Label lblYear3 = new Label();
    @FXML
    private Label lblGenre3 = new Label();
    @FXML
    private Label lblOrigin3 = new Label();
    @FXML
    private Label lblDirector3 = new Label();
    @FXML
    private Label lblStar3 = new Label();

    @FXML
    public Label lblTitle4 = new Label();
    @FXML
    private Label lblYear4 = new Label();
    @FXML
    private Label lblGenre4 = new Label();
    @FXML
    private Label lblOrigin4 = new Label();
    @FXML
    private Label lblDirector4 = new Label();
    @FXML
    private Label lblStar4 = new Label();


    @FXML
    private ListView<String> autoCompleteListView;
    private List<Movie> movies;
    private Movie selectedMovie;
    private List<List<Label>> tileLabelGroups;

    private int guessCount;

    public void initialize() {
        loadMovies();
        configureAutoComplete();
        tileLabelGroups = new ArrayList<>();


        List<Label> group1 = new ArrayList<>();
        group1.add(lblTitle);
        group1.add(lblYear);
        group1.add(lblGenre);
        group1.add(lblOrigin);
        group1.add(lblDirector);
        group1.add(lblStar);
        tileLabelGroups.add(group1);

        List<Label> group2 = new ArrayList<>();
        group2.add(lblTitle1);
        group2.add(lblYear1);
        group2.add(lblGenre1);
        group2.add(lblOrigin1);
        group2.add(lblDirector1);
        group2.add(lblStar1);
        tileLabelGroups.add(group2);

        List<Label> group3 = new ArrayList<>();
        group3.add(lblTitle2);
        group3.add(lblYear2);
        group3.add(lblGenre2);
        group3.add(lblOrigin2);
        group3.add(lblDirector2);
        group3.add(lblStar2);
        tileLabelGroups.add(group3);

        List<Label> group4 = new ArrayList<>();
        group4.add(lblTitle3);
        group4.add(lblYear3);
        group4.add(lblGenre3);
        group4.add(lblOrigin3);
        group4.add(lblDirector3);
        group4.add(lblStar3);
        tileLabelGroups.add(group4);

        List<Label> group5 = new ArrayList<>();
        group5.add(lblTitle4);
        group5.add(lblYear4);
        group5.add(lblGenre4);
        group5.add(lblOrigin4);
        group5.add(lblDirector4);
        group5.add(lblStar4);
        tileLabelGroups.add(group5);

        guessCount = 0;
        startNewGame();
    }

    private void loadMovies() {
        try {
            Path path = Path.of("src/main/resources/imdb_top_250.csv");
            movies = Files.lines(path,Charset.forName("ISO-8859-1"))
                    .skip(1)
                    .map(line -> {
                        String[] fields = line.split(";");
                        return new Movie(
                                Integer.parseInt(fields[0]),
                                fields[1],
                                Integer.parseInt(fields[2]),
                                fields[3],
                                fields[4],
                                fields[5],
                                fields[6]
                        );
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void startNewGame() {
        clearTileLabels();
        selectRandomMovie();
        updateTileLabels();

        guessCount = 0;

    }



    private void clearTileLabels() {
        for (List<Label> group : tileLabelGroups) {
            for (Label label : group) {
                label.setText("");

            }
        }
    }
    private void selectRandomMovie () {
        Random random = new Random();
        int index = random.nextInt(movies.size());
        selectedMovie = movies.get(index);
    }
    @FXML
    public void onGuessButton (ActionEvent event) {

        String guess = txtGuess.getText().trim();  //   trim() fonksiyonu kullan?larak ba??ndaki ve sonundaki bo?luklar kald?r?l?r.
        if (!guess.isEmpty()) {
            Movie guessedMovie = findMovieByTitle(guess); // findMovieByTitle(guess) fonksiyonu kullan?larak guess adl? tahminin kar??l?k geldi?i bir film bulunur ve guessedMovie adl? bir Movie nesnesine atan?r.
            int guessIndex = guessCount % tileLabelGroups.size(); //guessCount de?i?keni mod tileLabelGroups listesinin boyutuna al?narak guessIndex hesaplan?r. Bu, etiket gruplar?n? d?ng?sel olarak g?ncellemek i?in kullan?l?r.
            List<Label> group = tileLabelGroups.get(guessIndex);
            updateLabelsAndFadeTransition(group, guessedMovie, guessedMovie != null); // etiketlerin ve ge?i? animasyonlar?n?n g?ncellenmesi sa?lan?r,tahminin do?ru olup olmad???na g?re animasyonlar? uygular.

            group.get(0).setText(guessedMovie.getTitle());  // group.get(0) ifadesiyle ilk etikete guessedMovie'nin ba?l???n? (getTitle()) atar.
            group.get(1).setText(String.valueOf(guessedMovie.getYear()));
            group.get(2).setText(guessedMovie.getGenre());
            group.get(3).setText(guessedMovie.getOrigin());
            group.get(4).setText(guessedMovie.getDirector());
            group.get(5).setText(guessedMovie.getStar());

            guessCount++;




            if (guessedMovie != null) { // guessedMovie nesnesinin null olup olmad??? kontrol edilir. guessedMovie nesnesi, tahminin kar??l?k geldi?i bir filmi temsil eder. E?er guessedMovie null de?ilse if blo?una girilir.


                List<String> selectedParameters = new ArrayList<>(); // Bo? bir dizi lsitesi olan selectedParameters do?ru tahmin edilen filmle ayn? ?zelliklere sahip olan parametreleri tutar

                if (guessedMovie.getTitle().equalsIgnoreCase(selectedMovie.getTitle())) { //?lk if ifadesinde, tahmin edilen filmin ba?l??? (getTitle()) ve se?ilen filmin ba?l??? (selectedMovie.getTitle()) kar??la?t?r?l?r. E?er ba?l?klar e?itse if blo?una girilir.
                    setLabelStyle(group.get(0), true); // setLabelStyle(group.get(0), true) ifadesiyle, do?ru tahmin edilen film ba?l???n?n etiketin stilini do?ru olarak i?aretler.
                    selectedParameters.add("Title"); // "Title" ifadesi selectedParameters listesine eklenir.
                } else {
                    setLabelStyle(group.get(0), false); //E?er ba?l?klar e?it de?ilse else blo?u ?al???r ve setLabelStyle(group.get(0), false) ifadesiyle etiketin stilini yanl?? olarak i?aretler.
                }

                if (guessedMovie.getYear() == selectedMovie.getYear()) {
                    setLabelStyle(group.get(1), true);
                    selectedParameters.add("Year");
                } else {
                    setLabelStyle(group.get(1), false);
                }

                if (guessedMovie.getGenre().equalsIgnoreCase(selectedMovie.getGenre())) {
                    setLabelStyle(group.get(2), true);
                    selectedParameters.add("Genre");
                } else {
                    setLabelStyle(group.get(2), false);
                }

                if (guessedMovie.getOrigin().equalsIgnoreCase(selectedMovie.getOrigin())) {
                    setLabelStyle(group.get(3), true);
                    selectedParameters.add("Origin");
                } else {
                    setLabelStyle(group.get(3), false);
                }

                if (guessedMovie.getDirector().equalsIgnoreCase(selectedMovie.getDirector())) {
                    setLabelStyle(group.get(4), true);
                    selectedParameters.add("Director");
                } else {
                    setLabelStyle(group.get(4), false);
                }

                if (guessedMovie.getStar().equalsIgnoreCase(selectedMovie.getStar())) {
                    setLabelStyle(group.get(5), true);
                    selectedParameters.add("Star");
                } else {
                    setLabelStyle(group.get(5), false);
                }

                if (selectedParameters.size() == 6) {
                    showWinMessage();
                    return;
                }
            }
            if (guessCount >= 5) {
                showLoseMessage();
                startNewGame();
                return;
            }
            }


        txtGuess.clear();
        txtGuess.requestFocus();
    }


    private void updateLabelsAndFadeTransition(List<Label> group, Movie guessedMovie, boolean isCorrect) {
        for (int i = 0; i < group.size(); i++) {
            Label label = group.get(i);
            String newText = "";

            switch (i) {
                case 0:
                    newText = guessedMovie.getTitle();
                    break;
                case 1:
                    newText = String.valueOf(guessedMovie.getYear());
                    break;

                case 2:
                    newText = guessedMovie.getGenre();
                    break;
                case 3:
                    newText = guessedMovie.getOrigin();
                    break;
                case 4:
                    newText = guessedMovie.getDirector();
                    break;
                case 5:
                    newText = guessedMovie.getStar();
                    break;
            }

            if (!label.getText().equals(newText)) {
                // Yeni yaz?lan etikete fade transition uygula
                applyFadeTransition(label, isCorrect);
            }



            label.setText(newText);
        }
    }

    private void applyFadeTransition(Node node, boolean isCorrect) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setOnFinished(event -> {
        });
        fadeTransition.play();


    }



    private void setLabelStyle(Label label, boolean isCorrect) {
        String style = isCorrect ? "-fx-background-color: #15902a;" : "-fx-background-color: #d31b1b;";
        label.setStyle(style);
    }

    private Movie findMovieByTitle(String title) {
        for (Movie movie : movies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return movie;
            }
        }
        return null;
    }
    private void updateTileLabels () {
        List<Label> group = tileLabelGroups.get(guessCount); // Tahmin say?s?na g?re ilgili etiket grubunu se?tik

        group.get(0).setText("");
        group.get(1).setText("");
        group.get(2).setText("");
        group.get(3).setText("");
        group.get(4).setText("");
        group.get(5).setText("");
    }

    private void configureAutoComplete() {

        txtGuess.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                autoCompleteListView.requestFocus(); // ListView'e odakla
                autoCompleteListView.getSelectionModel().selectFirst(); // Varsay?lan ?ge

                // Bas?lan ok tu?una g?re ?geler aras? ge?i?
                if (event.getCode() == KeyCode.UP) {
                    autoCompleteListView.getSelectionModel().selectPrevious();
                } else if (event.getCode() == KeyCode.DOWN) {
                    autoCompleteListView.getSelectionModel().selectNext();
                }

                event.consume();
            }
        });

        autoCompleteListView.setOnKeyPressed(event -> { // Enter tu?una bas?ld???nda se?ili ??eyi metin giri?ine yerle?tirerek otomatik tamamlama i?lemini tamamlar.
            if (event.getCode() == KeyCode.ENTER) {
                String selectedItem = autoCompleteListView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    txtGuess.setText(selectedItem);
                    autoCompleteListView.setVisible(false);
                    txtGuess.requestFocus();
                }

                event.consume();
            }
        });

        txtGuess.textProperty().addListener((observable, oldValue, newValue) -> { //kullan?c?n?n metin giri?ine her karakter ekledi?inde veya sildi?inde ?al???r.
            if (!newValue.isEmpty()) {
                List<String> matchingTitles = movies.stream()
                        .map(Movie::getTitle)
                        .filter(title -> title.toLowerCase().startsWith(newValue.toLowerCase()))
                        .collect(Collectors.toList());

                autoCompleteListView.getItems().setAll(matchingTitles);
                autoCompleteListView.setVisible(!matchingTitles.isEmpty());
            } else {
                autoCompleteListView.getItems().clear();
                autoCompleteListView.setVisible(false);
            }
        });

        autoCompleteListView.setOnMouseClicked(event -> { // Fare t?klamas? ile se?ili ??eyi metin giri?ine yerle?tirerek otomatik tamamlama i?lemini tamamlar.
            String selectedItem = autoCompleteListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                txtGuess.setText(selectedItem);
                autoCompleteListView.setVisible(false);
            }
        });
    }

    private void showWinMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("CONGRATULATIONS");
        alert.setHeaderText(null);

        ButtonType playAgainButton = new ButtonType("RESTART");
        alert.getButtonTypes().setAll(playAgainButton);

        alert.setContentText("YOU WIN!"+ "\nMOVIE : "+ selectedMovie.getTitle());

        alert.showAndWait().ifPresent(response -> {
            if (response == playAgainButton) {
                restartApplication();
            }
         });
    }

    private void showLoseMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("YOU LOST!");
        alert.setHeaderText(null);
        alert.setContentText("YOU LOST!"+ "\n MOVIE : "+ selectedMovie.getTitle());

        ButtonType playAgainButton = new ButtonType("RESTART");
        alert.getButtonTypes().setAll(playAgainButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == playAgainButton) {
                restartApplication();
            }
        });
    }
    private void restartApplication() {
        // Yeni bir RestartApplication nesnesi ba?lat?l?yor
        RestartApplication restartApp = new RestartApplication();
        try {
            // Uygulama yeniden ba?lat?l?yor
            restartApp.start(new Stage());
            // Mevcut a?ama kapat?l?yor
            Stage currentStage = (Stage) lblTitle.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}