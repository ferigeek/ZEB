module me.farnam {
    requires javafx.controls;
    requires javafx.fxml;

    opens me.farnam to javafx.fxml;
    exports me.farnam;
}
