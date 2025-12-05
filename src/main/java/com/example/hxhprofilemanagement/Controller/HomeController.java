package com.example.hxhprofilemanagement.Controller;

import com.example.hxhprofilemanagement.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.time.LocalDate;

public class HomeController {

    @FXML private TextField givenNameField;
    @FXML private TextField middleNameField;
    @FXML private TextField lastNameField;
    @FXML private DatePicker birthdatePicker;
    @FXML private RadioButton maleRadio;
    @FXML private RadioButton femaleRadio;
    @FXML private ChoiceBox<String> nenTypeChoice;
    @FXML private TextField affiliationField;
    @FXML private ImageView profileImageView;
    @FXML private ToggleGroup genderGroup;

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, Integer> colId;
    @FXML private TableColumn<User, Image> colImage;
    @FXML private TableColumn<User, String> colName;
    @FXML private TableColumn<User, String> colAge;
    @FXML private TableColumn<User, String> colGender;
    @FXML private TableColumn<User, String> colNenType;
    @FXML private TableColumn<User, String> colAffiliation;

    @FXML private AnchorPane formPane;
    @FXML private Button addUserButton;

    private ObservableList<User> users = FXCollections.observableArrayList();
    private int currentId = 1;

    private File selectedImageFile = null;

    // For centering the inputted information on the table
    private <T> void centerColumn(TableColumn<User, T> column) {
        column.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                    setAlignment(javafx.geometry.Pos.CENTER);
                }
            }
        });
    }

    @FXML
    public void initialize() {
        nenTypeChoice.setItems(FXCollections.observableArrayList(
                "Enhancer", "Emitter", "Manipulator", "Specialist", "Conjurer", "Transmuter", "Unknown"
        ));

        // Table column bindings
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getId()));
        colImage.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getImage()));
        colName.setCellValueFactory(data -> {
            User user = data.getValue();
            String middle = (user.getMiddleName() != null && !user.getMiddleName().isBlank()) ? " " + user.getMiddleName() : "";
            String fullName = user.getGivenName() + middle + " " + user.getLastName();
            return new javafx.beans.property.SimpleStringProperty(fullName);
        });
        colAge.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getAge())
        );
        colGender.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getGender()));
        colNenType.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNenType()));
        colAffiliation.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAffiliation()));

        centerColumn(colId);
        centerColumn(colName);
        centerColumn(colGender);
        centerColumn(colAge);
        centerColumn(colNenType);
        centerColumn(colAffiliation);
        userTable.setItems(users);

        // Hide form initially
        formPane.setVisible(false);

        addUserButton.setOnAction(e -> {
            clearForm();          // clear previous input
            formPane.setVisible(true);  // show the form for new user
            userTable.getSelectionModel().clearSelection(); // deselect any row
        });

        // When a row is clicked, show form for editing
        userTable.setOnMouseClicked(e -> {
            User selected = userTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                formPane.setVisible(true); // show form for editing
                loadSelectedUser();
            }
        });
    }

    @FXML
    private void uploadImage() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png"));
        selectedImageFile = chooser.showOpenDialog(null);

        if (selectedImageFile != null) {
            profileImageView.setImage(new Image(selectedImageFile.toURI().toString()));
        }
    }

    @FXML
    private void saveProfile() {
        String givenName = givenNameField.getText();
        String gender = maleRadio.isSelected() ? "Male" : femaleRadio.isSelected() ? "Female" : null;
        String nenType = nenTypeChoice.getValue();
        Image image = profileImageView.getImage();
        LocalDate birthdate = birthdatePicker.getValue();

        if (givenName == null || givenName.isBlank() || gender == null || nenType == null || nenType.isBlank() ||
                image == null) {
            return; // don't save or edit
        }

        User selected = userTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            // for editing
            selected.setGivenName(givenNameField.getText());
            selected.setMiddleName(middleNameField.getText());
            selected.setLastName(lastNameField.getText());
            selected.setAffiliation(affiliationField.getText());
            selected.setGender(gender);
            selected.setBirthdate(birthdatePicker.getValue());
            selected.setNenType(nenTypeChoice.getValue());
            selected.setImage(profileImageView.getImage());
            userTable.refresh();
        } else {
            // adds new user
            User newUser = new User(currentId++, profileImageView.getImage(), givenNameField.getText(), middleNameField.getText(),
                    lastNameField.getText(), birthdate, gender, nenTypeChoice.getValue(), affiliationField.getText());
            users.add(newUser);
        }
        clearForm();
        formPane.setVisible(false);
    }

    // When a row is clicked, the form is loaded
    private void loadSelectedUser() {
        User user = userTable.getSelectionModel().getSelectedItem();
        if (user == null) return;

        givenNameField.setText(user.getGivenName());
        middleNameField.setText(user.getMiddleName());
        lastNameField.setText(user.getLastName());
        birthdatePicker.setValue(user.getBirthdate());
        affiliationField.setText(user.getAffiliation());
        nenTypeChoice.setValue(user.getNenType());
        profileImageView.setImage(user.getImage());

        if (user.getGender().equals("Male")) {
            maleRadio.setSelected(true);
        } else {
            femaleRadio.setSelected(true);
        }
    }

    // Clears everything after saving
    private void clearForm() {
        givenNameField.clear();
        middleNameField.clear();
        lastNameField.clear();
        affiliationField.clear();
        maleRadio.setSelected(false);
        femaleRadio.setSelected(false);
        profileImageView.setImage(null);
        nenTypeChoice.setValue(null);
        birthdatePicker.setValue(null);
        userTable.getSelectionModel().clearSelection();
    }
}