module me.farnam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.eclipse.jgit;
    requires zip4j;

    opens me.farnam.zeb to javafx.fxml;
    exports me.farnam.zeb;
    exports me.farnam.zeb.controller;
    opens me.farnam.zeb.controller to javafx.fxml;
    exports me.farnam.zeb.backup;
    opens me.farnam.zeb.backup to javafx.fxml;
}
