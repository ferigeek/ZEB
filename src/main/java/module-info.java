module me.farnam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens me.farnam.zeb to javafx.fxml;
    exports me.farnam.zeb;
    exports me.farnam.zeb.controller;
    opens me.farnam.zeb.controller to javafx.fxml;
}
